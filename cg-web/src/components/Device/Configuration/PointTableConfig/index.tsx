/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-08-31 16:22:51
 * @LastEditTime: 2023-08-31 18:48:02
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceDetail\Configuration\Community\index.tsx
 */
import React, { useMemo } from 'react';
import Detail from '@/components/Detail';
import type { GroupItem } from '@/components/Detail';
import { Button, Upload, message } from 'antd';
import type { DeviceDataType } from '@/services/equipment';
import { formatMessage } from '@/utils';
import { download, importConfig } from './service';
import { ExportOutlined, ImportOutlined } from '@ant-design/icons';
import { useAuthority } from '@/hooks';

export type CommunityProps = {
  deviceData: DeviceDataType;
};

const CommunityDetail: React.FC<CommunityProps> = (props) => {
  const { deviceData } = props;

  const { authorityMap } = useAuthority([
    'device:detail:config:communicationMeterConfig:detail',
    'device:detail:config:communicationMeterConfig:edit',
  ]);

  const templatedownload = () => {
    const deviceId = deviceData?.deviceId || '';
    download(deviceId);
  };
  const importFile = async (file: any) => {
    const hide = message.loading(formatMessage({
      id: 'device.1035',
      defaultMessage: '正在导入',
    }));
    try {
      const formData = new FormData();
      formData.append('file', file);
      await importConfig(formData);
      hide();
      message.success(formatMessage({
        id: 'device.1036',
        defaultMessage: '导入成功',
      }));
    } catch (error) {
      hide();
      message.error(formatMessage({
        id: 'device.1037',
        defaultMessage: '导出失败，请重试'
      }));
    }
    return false;
  };
  const communityItems = useMemo(() => {
    const groupItem: GroupItem[] = [];
    if (deviceData?.paramConfigType && deviceData?.deviceId) {
      groupItem.push({
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'device.pointTableConfig',
              defaultMessage: '通信点表配置',
            })}
          >
            {authorityMap.get('device:detail:config:communicationMeterConfig:edit') && (
              <>
                <Button type="primary" className="mr12" onClick={templatedownload}>
                  <ImportOutlined />
                  {formatMessage({ id: 'device.templateDownload', defaultMessage: '模版下载' })}
                </Button>
                <Upload key="upload" beforeUpload={importFile} showUploadList={false}>
                  <Button type="primary">
                    <ExportOutlined />
                    {formatMessage({ id: 'device.importFile', defaultMessage: '点表文件导入' })}
                  </Button>
                </Upload>
              </>
            )}
          </Detail.Label>
        ),
      });
    }
    return groupItem;
  }, [deviceData, authorityMap]);

  return (
    <>
      {authorityMap.get('device:detail:config:communicationMeterConfig:detail') &&
        !!deviceData?.paramConfigType && <Detail.Group items={communityItems} />}
    </>
  );
};

export default CommunityDetail;
