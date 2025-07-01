/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-21 14:39:51
 * @LastEditTime: 2024-04-02 16:51:09
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\components\Debug\VPN\index.tsx
 */

import React, { memo, useContext, useEffect, useRef } from 'react';
import Detail, { GroupItem } from '@/components/Detail';
import DeviceContext from '@/components/Device/Context/DeviceContext';
import { getVpn, openVpn } from '@/services/equipment';
import { formatMessage } from '@/utils';
import { openFormat } from '@/utils/format';
import { RedoOutlined } from '@ant-design/icons';
import { Button, message, Select, SelectProps, Typography } from 'antd';
import { useRequest } from 'umi';
import styles from './index.less';
import moment from 'moment';

const timeOption: SelectProps['options'] = [
  {
    label: '1' + formatMessage({ id: 'debug.hour', defaultMessage: '小时' }),
    value: 1,
  },
  {
    label: '2' + formatMessage({ id: 'debug.hour', defaultMessage: '小时' }),
    value: 2,
  },
  {
    label: '3' + formatMessage({ id: 'debug.hour', defaultMessage: '小时' }),
    value: 3,
  },
  {
    label: '4' + formatMessage({ id: 'debug.hour', defaultMessage: '小时' }),
    value: 4,
  },
];

const VPN: React.FC = () => {
  const timeRef = useRef(1);
  const { data: deviceData } = useContext(DeviceContext);

  const {
    run,
    loading,
    data: tunnelData,
  } = useRequest(getVpn, {
    manual: true,
    formatResult(res) {
      if (res?.data) {
        if (res?.data?.vpnSupport != 1) {
          res.data.vpnExpireTime = undefined;
          res.data.vip = undefined;
        }
      }
      return res?.data;
    },
  });

  const { run: runEdit, loading: editLoading } = useRequest(openVpn, {
    manual: true,
  });

  const onControl = (value: number) => {
    runEdit({
      deviceId: deviceData?.deviceId,
      vpnSwitch: value,
      vpnRetentionTime: timeRef.current,
    }).then((data) => {
      if (data) {
        message.success(formatMessage({ id: 'common.operateSuccess', defaultMessage: '操作成功' }));
        run({ deviceId: deviceData?.deviceId });
      }
    });
  };

  useEffect(() => {
    if (deviceData?.deviceId) {
      run({ deviceId: deviceData?.deviceId });
    }
  }, [deviceData?.deviceId]);

  const items: GroupItem[] = [
    {
      label: (
        <Detail.Label
          title={formatMessage({ id: 'debug.tunnelInformation', defaultMessage: '隧道信息' })}
        >
          <RedoOutlined
            className={`cl-primary cursor ${styles.refresh}`}
            onClick={() =>
              run({ deviceId: deviceData?.deviceId }).then((res) => {
                if (res) {
                  message.success(
                    formatMessage({ id: 'common.refreshSuccess', defaultMessage: '刷新成功' }),
                  );
                }
              })
            }
          />
        </Detail.Label>
      ),
      items: [
        {
          label: formatMessage({ id: 'debug.tunnelStatus', defaultMessage: '隧道状态' }),
          field: 'vpnSwitch',
          format: openFormat,
          show: (value, data) => data.vpnSupport == 1,
        },
        {
          label: formatMessage({ id: 'debug.tunnelIp', defaultMessage: '隧道IP' }),
          field: 'vip',
        },
        {
          label: formatMessage({
            id: 'debug.syncTimeDevice',
            defaultMessage: '同步时间(设备时间)',
          }),
          field: 'deviceTs',
          format: (value) => moment(value).format('YYYY-MM-DD HH:mm:ss'),
        },
        {
          label: formatMessage({
            id: 'debug.syncTimePlatform',
            defaultMessage: '同步时间(平台时间)',
          }),
          field: 'serverTs',
          format: (value) => moment(value).format('YYYY-MM-DD HH:mm:ss'),
        },
      ],
    },
    {
      label: (
        <Detail.Label
          title={formatMessage({ id: 'debug.tunnelStatusControl', defaultMessage: '隧道状态控制' })}
        />
      ),
      items: [
        {
          label: formatMessage({
            id: 'debug.tunnelHoldTime',
            defaultMessage: '隧道保持时间',
          }),
          field: 'a',
          showPlaceholder: false,
          labelStyle: {
            lineHeight: '32px',
          },
          span: 3,
          format: (_, data) => (
            <>
              <Select
                defaultValue={1}
                options={timeOption}
                onChange={(value) => {
                  timeRef.current = value;
                }}
              />
              <Button
                loading={loading || editLoading}
                className="mx12"
                onClick={() => {
                  onControl(1);
                }}
                disabled={data.vpnSupport != 1}
              >
                {formatMessage({ id: 'things.open1', defaultMessage: '开启' })}
              </Button>
              <Button
                loading={loading || editLoading}
                onClick={() => {
                  onControl(0);
                }}
                disabled={data.vpnSupport != 1}
              >
                {formatMessage({ id: 'things.closeDown', defaultMessage: '关闭' })}
              </Button>
            </>
          ),
        },
        {
          label: formatMessage({
            id: 'debug.tunnelExpirationTime',
            defaultMessage: '隧道到期时间',
          }),
          field: 'vpnExpireTime',
          span: 3,
          format: (value) => {
            return (
              <>
                {moment(value).format('YYYY-MM-DD HH:mm:ss')}
                <div>
                  <Typography.Text type="secondary">
                    {formatMessage({
                      id: 'debug.tunnelTip',
                      defaultMessage:
                        '到期后会自动断开隧道，快到期时可以再次点击开启，延迟隧道时间。',
                    })}
                  </Typography.Text>
                </div>
              </>
            );
          },
        },
      ],
    },
  ];

  return (
    <>
      <Detail.Group
        items={items}
        data={tunnelData}
        detailProps={{
          labelStyle: { width: 140 },
        }}
      />
    </>
  );
};

export default memo(VPN);
