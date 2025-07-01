/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-25 14:50:53
 * @LastEditTime: 2023-11-24 09:30:57
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\RemoteUpgrade\UpgradeForm\index.tsx
 */
import { Button, message } from 'antd';
import React, { useCallback, useContext, useMemo } from 'react';
import SchemaForm, { FormTypeEnum } from '@/components/SchemaForm';
import { useBoolean } from 'ahooks';
import { upgradeDevice } from '@/services/equipment';
import { UpgradeFormType } from '../typing';
import { ProFormColumnsType } from '@ant-design/pro-components';
import { OptionType } from '@/types';
import { OnlineStatusEnum } from '@/utils/dictionary';
import { formatMessage } from '@/utils';
import DeviceContext from '@/components/Device/Context/DeviceContext';

const UpgradeForm: React.FC<UpgradeFormType> = (props) => {
  const { deviceId, versionItems } = props;

  const { data: deviceData } = useContext(DeviceContext);
  const [open, { set, setTrue }] = useBoolean(false);

  const columns = useMemo<ProFormColumnsType[]>(() => {
    const options: OptionType[] = [];
    versionItems?.forEach?.((item) => {
      options.push({
        value: item?.id || '',
        label: item?.version || '',
        disabled: item?.status === 0,
      });
    });
    return [
      {
        title: formatMessage({ id: 'device.upgradeVersion', defaultMessage: '升级版本' }),
        dataIndex: 'packageId',
        valueType: 'select',
        formItemProps: {
          rules: [
            {
              required: true,
              message:
                formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }) +
                formatMessage({ id: 'device.upgradeVersion', defaultMessage: '升级版本' }),
            },
          ],
        },
        fieldProps: {
          options,
        },
      },
    ];
  }, [versionItems]);

  return (
    <>
      <Button
        className="mr12"
        type="primary"
        onClick={setTrue}
        disabled={deviceData?.networkStatus === OnlineStatusEnum.Offline}
      >
        {formatMessage({ id: 'device.upgrades', defaultMessage: '升级' })}
      </Button>
      <SchemaForm
        open={open}
        onOpenChange={set}
        title={formatMessage({ id: 'device.remoteUpgrade', defaultMessage: '远程升级' })}
        width={552}
        type={FormTypeEnum.Edit}
        columns={columns}
        editData={upgradeDevice}
        extraData={{ deviceId }}
      />
    </>
  );
};

export default UpgradeForm;
