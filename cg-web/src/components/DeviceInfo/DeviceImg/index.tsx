/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-11 09:28:09
 * @LastEditTime: 2023-09-12 16:44:24
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceInfo\DeviceImg\index.tsx
 */
import React, { useCallback, useEffect, useMemo, useState } from 'react';
import { useRequest } from 'umi';
import { useBoolean } from 'ahooks';
import { Button, Empty, Image, Modal, Space, Upload } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import { DeviceDataType, editDeviceInfo } from '@/services/equipment';
import { api } from '@/services';
import { formatMessage } from '@/utils';

export type DeviceImgProps = {
  open?: boolean;
  onOpenChange?: (show: boolean) => void;
  onSuccess?: (img: string) => void;
  deviceData?: DeviceDataType;
};

const DeviceImg: React.FC<DeviceImgProps> = (props) => {
  const { open, onOpenChange, onSuccess, deviceData } = props;
  const [deviceImg, setDeviceImg] = useState<string>();
  const [upLoadLoding, { setTrue, setFalse }] = useBoolean(false);
  const { run, loading } = useRequest(editDeviceInfo, {
    manual: true,
  });

  const closeModal = useCallback(() => {
    onOpenChange?.(false);
  }, []);

  const beforeUpload = useCallback((file) => {
    const formData = new FormData();
    formData.append('file', file);
    setTrue();
    api
      .uploadFile(formData)
      .then(({ data }) => {
        if (data.url) {
          setDeviceImg(data.url);
        }
      })
      .finally(() => {
        setFalse();
      });
    return false;
  }, []);

  const onConfirmClick = useCallback(() => {
    if (deviceImg) {
      run({ photos: deviceImg, deviceId: deviceData?.deviceId }).then((data) => {
        if (data) {
          onSuccess?.(deviceImg);
          closeModal();
        }
      });
    }
  }, [deviceImg, deviceData]);

  useEffect(() => {
    if (open) {
      setDeviceImg('');
    }
  }, [open]);

  return (
    <>
      <Modal
        title={formatMessage({ id: 'device.equipmentPhoto', defaultMessage: '设备照片' })}
        width={700}
        open={open}
        confirmLoading={loading}
        onCancel={closeModal}
        footer={[
          <Space key="btn">
            <Button onClick={closeModal}>
              {formatMessage({ id: 'common.cancel', defaultMessage: '取消' })}
            </Button>
            {deviceImg ? (
              <Button type="primary" loading={upLoadLoding || loading} onClick={onConfirmClick}>
                {formatMessage({ id: 'common.confirm', defaultMessage: '确定' })}
              </Button>
            ) : (
              <></>
            )}
            <Upload name="file" beforeUpload={beforeUpload} accept="image/*" showUploadList={false}>
              <Button type="primary" icon={<UploadOutlined />} loading={upLoadLoding}>
                {formatMessage({ id: 'device.uploadPhotos', defaultMessage: '上传照片' })}
              </Button>
            </Upload>
          </Space>,
        ]}
      >
        <Image src={deviceImg || deviceData?.photos} preview={false} width={'100%'} />
        {deviceImg || deviceData?.photos ? (
          <></>
        ) : (
          <Empty
            image={Empty.PRESENTED_IMAGE_SIMPLE}
            description={formatMessage({ id: 'device.noPhotos', defaultMessage: '暂无照片' })}
          />
        )}
      </Modal>
    </>
  );
};

export default DeviceImg;
