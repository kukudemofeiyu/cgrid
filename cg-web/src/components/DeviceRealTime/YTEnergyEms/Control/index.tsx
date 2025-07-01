/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-10-27 09:55:28
 * @LastEditTime: 2024-01-09 16:42:04
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\YTEnergyEms\Control\index.tsx
 */

import React, { useCallback, useEffect } from 'react';
import { Button, Space, message, Modal, Radio } from 'antd';
import { DeviceTypeEnum, OnlineStatusEnum } from '@/utils/dictionary';
import { default as LineLabel } from '@/components/Detail/LineLabel';
import styles from '../index.less';
import { controlItems } from './config';
import { useRequest } from 'umi';
import { editSetting } from '@/services/equipment';
import { formatMessage } from '@/utils';
import { useAuthority } from '@/hooks';

type SettingProps = {
  id?: string;
  settingData?: Record<string, any>;
  isLineLabel?: boolean;
  isDeviceChild?: boolean;
  type?: DeviceTypeEnum;
  deviceData?: Record<string, any>;
};

const Setting: React.FC<SettingProps> = (props) => {
  const { id, deviceData } = props;
  const settingData = props.settingData || {}; //实时数据
  const { loading, run } = useRequest(editSetting, {
    manual: true,
  });
  const { passAuthority } = useAuthority([
    'iot:device:remoteControl:systemStatusControl:distribute',
  ]);

  const btnClick = useCallback((item, btnItem) => {
    Modal.confirm({
      title: item.label || formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
      content: formatMessage({
        id: 'device.whetherExecuteCurrentParameter',
        defaultMessage: '是否执行当前参数下发',
      }),
      okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
      cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
      onOk: () =>
        run({
          deviceId: id,
          input: { [item.field]: btnItem.value },
          serviceId: item?.field,
        }).then((data: any) => {
          if (data) {
            message.success(
              formatMessage({ id: 'device.issueSuccess', defaultMessage: '下发成功' }),
            );
          }
        }),
    });
  }, []);

  return (
    <>
      <LineLabel
        title={formatMessage({ id: 'device.systemStateControl', defaultMessage: '系统状态控制' })}
      />
      <div className={styles.control}>
        {controlItems.map((item: any) => {
          return (
            <div className={styles.labelBox}>
              <Space wrap>
                <div className={styles.label}>{item.label}</div>
                {item.btnParam.map((btnItem: any) => {
                  return (
                    <>
                      <Button
                        type={
                          settingData[item.field] == btnItem.value &&
                          !(item.disabled && settingData?.systemOperatingMode != 2)
                            ? 'primary'
                            : 'default'
                        }
                        ghost={settingData[item.field] == btnItem.value}
                        loading={loading}
                        onClick={() => {
                          if (passAuthority) {
                            btnClick(item, btnItem);
                          }
                        }}
                        disabled={
                          deviceData?.networkStatus === OnlineStatusEnum.Offline ||
                          (item.disabled && settingData?.systemOperatingMode != 2)
                        }
                      >
                        <Radio
                          name={item.field}
                          className="mr8"
                          checked={
                            settingData[item.field] == btnItem.value &&
                            !(item.disabled && settingData?.systemOperatingMode != 2)
                          }
                          onClick={(e) => {
                            if (passAuthority) {
                              btnClick(item, btnItem);
                            }
                            e.stopPropagation();
                          }}
                        />
                        {btnItem.text}
                      </Button>
                    </>
                  );
                })}
              </Space>
            </div>
          );
        })}
      </div>
    </>
  );
};

export default Setting;
