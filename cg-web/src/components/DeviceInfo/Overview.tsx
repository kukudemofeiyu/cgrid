/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-13 21:46:44
 * @LastEditTime: 2024-09-26 09:27:26
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceInfo\Overview.tsx
 */
import React, { useCallback, useEffect, useMemo, useState } from 'react';
import type { ChangeEvent } from 'react';
import { Button, Image, Input, Skeleton, message, Modal, Row, Col, Empty } from 'antd';
import type { InputProps } from 'antd';
import { EditOutlined, LoadingOutlined, CloudDownloadOutlined } from '@ant-design/icons';
import { useBoolean, useToggle } from 'ahooks';
import { useRequest } from 'umi';
import type { UploadFile } from 'antd';
import { editDeviceInfo, getFileUrl } from '@/services/equipment';
import type { DeviceDataType } from '@/services/equipment';

import Detail, { DetailItem } from '../Detail';
import styles from './index.less';
import Dialog from '@/components/Dialog';
import IconEmpty from '@/assets/image/device/empty.png';
import DeviceImg from './DeviceImg';
import DeviceNameDialog from './DeviceNameDialog';
import { formatMessage, formatModelValue, getPropsFromTree, isEmpty } from '@/utils';
import { aLinkDownLoad } from '@/utils/downloadfile';
import { DeviceMasterMode, DeviceProductTypeEnum } from '@/utils/dictionary';
import { topItems, bottomItems, getDetailItems } from './helper';
import { useAuthority, useDeviceModel, useSubscribe } from '@/hooks';
import IccidModal from './IccidModal';
import { merge } from 'lodash';

export type OverviewProps = {
  deviceData?: DeviceDataType;
  deviceTreeData?: DeviceDataType[];
  introImg?: string;
  loading?: boolean;
  onChange?: () => void;
  className?: string;
};

type DeviceNameInfoType = {
  name?: string;
  showEdit?: boolean;
  status?: InputProps['status'];
  masterSlaveMode: any; //判断是否为自研EMS
  masterSlaveSystemName: any;
};

const Overview: React.FC<OverviewProps> = (props) => {
  const { deviceData, deviceTreeData, loading = false, onChange, introImg, className = '' } = props;

  const { authorityMap } = useAuthority(['device:detail:productIntro']);

  const middleItems = useMemo(() => {
    return getDetailItems(deviceData);
  }, [deviceData]);

  const middleExtraDeviceIds = useMemo(() => {
    const productTypeIds = getPropsFromTree<DetailItem, DeviceProductTypeEnum>(
      middleItems,
      'productTypeId',
    );
    const result = getPropsFromTree(
      deviceTreeData,
      'deviceId',
      'children',
      (item) => !!(item?.productTypeId && productTypeIds.includes(item?.productTypeId)),
    );
    return result;
  }, [middleItems, deviceTreeData]);

  const [openIntro, { setFalse, setTrue }] = useBoolean(false);
  const [openImg, { set: setOpenImg }] = useBoolean(false);
  const [openIccid, { setFalse: setIccidFalse, setTrue: setIccidTrue }] = useBoolean(false);
  const [deviceNameInfo, setDeviceNameInfo] = useState<DeviceNameInfoType>({
    name: '',
    showEdit: false,
    status: '',
    masterSlaveMode: '',
    masterSlaveSystemName: '',
  });
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);

  const realTimeData = useSubscribe(deviceData?.deviceId, true);
  const middleExtraRealTimeData = useSubscribe(middleExtraDeviceIds, true);
  const { modelMap: deviceModelMap } = useDeviceModel({
    deviceId: deviceData?.deviceId,
    isGroup: false,
  });
  const { modelMap } = useDeviceModel({
    deviceId: middleExtraDeviceIds?.[0],
    isGroup: false,
  });

  const [editNameOpen, { set: setEditNameOpen }] = useToggle<boolean>(false);
  const [emsNameValues, setEmsNameValues] = useState({});
  const { run, loading: editNameloading } = useRequest(editDeviceInfo, {
    manual: true,
  });

  const middleDetailItems = useMemo(() => {
    const result: DetailItem[] = merge([], middleItems);
    result.forEach((item) => {
      item.label =
        item.label || modelMap[item?.field || '']?.name || deviceModelMap[item?.field || '']?.name;
      item.format =
        item.format ||
        ((value) =>
          formatModelValue(
            value,
            modelMap?.[item?.field || ''] || deviceModelMap?.[item?.field || ''],
          ));
    });
    return result;
  }, [middleItems, modelMap, deviceModelMap]);

  const onEditNameClick = useCallback(() => {
    setDeviceNameInfo((prevData) => ({ ...prevData, showEdit: true })); //input输入框出现
    if (deviceNameInfo.masterSlaveMode !== '' && deviceNameInfo.masterSlaveMode == 0) {
      //ems修改名字弹窗出现
      setEditNameOpen(true);
      setEmsNameValues(deviceNameInfo);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [deviceNameInfo]);

  const onDeviceNameChange = useCallback((e: ChangeEvent) => {
    setDeviceNameInfo((prevData) => ({
      ...prevData,
      status: '',
      name: (e.target as HTMLInputElement).value.trim(),
    }));
  }, []);

  const editName = useCallback(() => {
    if (deviceNameInfo.name) {
      if (deviceNameInfo.name != deviceData?.name) {
        run({ name: deviceNameInfo.name, deviceId: deviceData?.deviceId }).then((data: any) => {
          if (data) {
            message.success(
              formatMessage({ id: 'common.successSaved', defaultMessage: '保存成功' }),
            );
            setDeviceNameInfo((prevData) => ({ ...prevData, showEdit: false }));
            onChange?.();
          }
        });
      } else {
        setDeviceNameInfo((prevData) => ({ ...prevData, showEdit: false }));
      }
    } else {
      setDeviceNameInfo((prevData) => ({ ...prevData, status: 'error' }));
    }
  }, [deviceNameInfo, deviceData]);

  const onVisibleChange = useCallback(() => {
    setOpenImg(true);
  }, []);

  const onEditImgSuccess = useCallback((img) => {
    message.success(formatMessage({ id: 'common.successSaved', defaultMessage: '保存成功' }));
  }, []);

  //更新ems名字
  const onEditEmsNameSuccess = useCallback(() => {
    setDeviceNameInfo((prevData) => ({ ...prevData, showEdit: false }));
    onChange?.();
  }, [deviceData]);

  const beforeSubmitEditName = useCallback(
    (params) => {
      params.id = deviceData?.deviceId;
      params.deviceId = deviceData?.deviceId;
      return params;
    },
    [deviceData],
  );

  const onEvent = useCallback((eventName) => {
    if (eventName == 'iccidClick') {
      setIccidTrue();
    }
  }, []);

  useEffect(() => {
    setDeviceNameInfo({
      name: deviceData?.name,
      showEdit: false,
      status: '',
      masterSlaveMode: deviceData?.masterSlaveMode,
      masterSlaveSystemName: deviceData?.masterSlaveSystemName,
    });
  }, [deviceData]);

  const downloadFile = (file: UploadFile) => {
    if (file.url) {
      getFileUrl({ url: file.url, platform: 1 }).then((res) => {
        if (res.data) {
          aLinkDownLoad(res.data, file.name);
        }
      });
    }
  };
  const openModal = () => {
    const product = deviceData?.productIntroduce
      ? JSON.parse(deviceData?.productIntroduce || '')
      : [];
    if (product.length) {
      setIsModalOpen(true);
    } else {
      message.warning(formatMessage({ id: 'common.noData', defaultMessage: '暂无数据' }));
    }
  };
  const closeModal = () => {
    setIsModalOpen(false);
  };
  const title = useMemo(() => {
    if (deviceNameInfo.showEdit) {
      return (
        <Input
          prefix={editNameloading ? <LoadingOutlined className="cl-primary" /> : <></>}
          value={deviceNameInfo?.name}
          onChange={onDeviceNameChange}
          onBlur={editName}
          onPressEnter={editName}
        />
      );
    } else {
      return (
        <div className={styles.device_title}>
          <div>
            {!isEmpty(deviceData?.masterSlaveMode) &&
              `(${
                deviceData?.masterSlaveMode === DeviceMasterMode.Master
                  ? formatMessage({ id: 'device.host', defaultMessage: '主机' })
                  : formatMessage({ id: 'device.slave', defaultMessage: '从机' })
              })`}
            {deviceNameInfo?.name}
            {deviceData?.forShort && `（${deviceData?.forShort}）`}
            <EditOutlined className="ml8 cl-primary" onClick={onEditNameClick} />
          </div>
          {authorityMap.get('device:detail:productIntro') && (
            <span onClick={openModal} className="ant-btn ant-btn-primary">
              {formatMessage({ id: 'siteMonitor.productIntroduction', defaultMessage: '产品介绍' })}
            </span>
          )}
        </div>
      );
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [deviceNameInfo, editNameloading, deviceData, authorityMap]);

  return (
    <>
      {loading ? (
        <>
          <Skeleton active paragraph={{ rows: 4 }} />
        </>
      ) : (
        <div className={`${styles.overview} ${className}`}>
          <Image
            rootClassName={styles.productImg}
            src={deviceData?.photos || IconEmpty}
            width={135}
            height={135}
            preview={{
              visible: false,
              onVisibleChange: onVisibleChange,
            }}
          />
          <Detail.Label className="mb16" title={title} showLine={false}>
            {introImg && (
              <Button className="pr0" type="link" onClick={setTrue}>
                {formatMessage({
                  id: 'siteMonitor.productIntroduction',
                  defaultMessage: '产品介绍',
                })}
              </Button>
            )}
          </Detail.Label>
          <Detail
            items={[...topItems, ...middleDetailItems, ...bottomItems]}
            data={{ ...deviceData, deviceTreeData, ...middleExtraRealTimeData, ...realTimeData }}
            column={{
              xxl: 4,
              xl: 3,
              lg: 2,
              sm: 1,
              xs: 1,
            }}
            labelStyle={{ maxWidth: '130px' }}
            onEvent={onEvent}
          />
        </div>
      )}
      <Dialog
        title={formatMessage({ id: 'siteMonitor.productIntroduction', defaultMessage: '产品介绍' })}
        open={openIntro}
        onCancel={setFalse}
        footer={null}
      >
        <img className="w-full" src={introImg} />
      </Dialog>
      <DeviceImg
        open={openImg}
        onOpenChange={setOpenImg}
        deviceData={deviceData}
        onSuccess={onEditImgSuccess}
      />
      {/* 修改设备名字弹窗 */}
      <DeviceNameDialog
        id={deviceData?.id}
        open={editNameOpen}
        onOpenChange={setEditNameOpen}
        onSuccess={onEditEmsNameSuccess}
        initialValues={emsNameValues}
        beforeSubmit={beforeSubmitEditName}
      />
      <Modal
        title={formatMessage({ id: 'siteMonitor.productInfo', defaultMessage: '产品资料' })}
        open={isModalOpen}
        onOk={closeModal}
        onCancel={closeModal}
        maskClosable={false}
      >
        {deviceData?.productIntroduce ? (
          <Row gutter={[20, 8]}>
            {(JSON.parse(deviceData?.productIntroduce) || []).map((item: UploadFile) => (
              <Col span={24} key={item.uid} className={styles.device_title}>
                <span>{item.name}</span>
                <Button
                  type="link"
                  icon={<CloudDownloadOutlined />}
                  onClick={() => downloadFile(item)}
                />
              </Col>
            ))}
          </Row>
        ) : (
          <Empty />
        )}
      </Modal>
      <IccidModal open={openIccid} onCancel={() => setIccidFalse()} iccid={realTimeData?.iccid} />
    </>
  );
};

export default Overview;
