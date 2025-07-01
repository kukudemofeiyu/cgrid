/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-01 10:27:59
 * @LastEditTime: 2023-12-08 10:03:30
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\DeviceConfig\ConfigForm\index.tsx
 */
import {
  SelectTypeEnum,
  TABLETREESELECT,
  TABLETREESELECTVALUETYPE,
  TableTreeModalProps,
  dealTreeDataType,
  tableTreeSelectValueTypeMap,
} from '@/components/TableSelect';
import {
  DeviceDataType,
  getAssociateDevice,
  getChargeStack,
  getDeviceCollection,
  getDeviceTree,
  updateAssociateDevice,
} from '@/services/equipment';
import React, { useCallback, useMemo } from 'react';
import { TreeDataType, tableSelectColumns } from './config';
import { ProConfigProvider, ProFormColumnsType } from '@ant-design/pro-components';
import { ConfigDataType } from './config';
import { ConfigTypeEnum } from '../config';
import { Button } from 'antd';
import { useBoolean } from 'ahooks';
import SchemaForm, { FormTypeEnum } from '@/components/SchemaForm';
import { OnlineStatusEnum } from '@/utils/dictionary';
import { formatMessage } from '@/utils';

export type ConfigFormProps = {
  deviceData: DeviceDataType;
  onSuccess?: () => void;
};

const dealTreeData: dealTreeDataType<TreeDataType> = (item) => {
  item.selectable = !!item.productId;
};

const ConfigForm: React.FC<ConfigFormProps> = (props) => {
  const { deviceData, onSuccess } = props;

  const [open, { set, setTrue }] = useBoolean(false);

  const requestTree = useCallback(() => {
    if (deviceData?.siteId) {
      return getDeviceTree({ siteId: deviceData.siteId });
    }
  }, [deviceData]);

  const requestChargeStack = useCallback(() => {
    if (deviceData?.siteId) {
      return getChargeStack({ siteId: deviceData?.siteId }).then(({ data }) => {
        return data?.map?.((item) => {
          return {
            label: item.name,
            value: item.deviceId,
          };
        });
      });
    } else {
      return Promise.resolve([]);
    }
  }, [deviceData]);

  const tableTreeSelectProps = useMemo<
    TableTreeModalProps<Record<string, any>, Record<string, any>, Record<string, any>, TreeDataType>
  >(() => {
    return {
      selectType: SelectTypeEnum.Device,
      title: formatMessage({ id: 'device.selectDevice', defaultMessage: '选择设备' }),
      treeProps: {
        fieldNames: {
          title: 'deviceName',
          key: 'id',
          children: 'children',
        },
        request: requestTree,
      },
      proTableProps: {
        pagination: false,
        columns: tableSelectColumns,
        request: getDeviceCollection,
      },
      valueId: 'id',
      valueName: 'deviceName',
      dealTreeData: dealTreeData,
    };
  }, [requestTree]);

  const columns = useMemo(() => {
    const result: ProFormColumnsType<ConfigDataType, TABLETREESELECTVALUETYPE>[] = [];
    switch (deviceData?.productConfigType) {
      case ConfigTypeEnum.Device:
        result.push({
          title: formatMessage({ id: 'device.associatedDevice', defaultMessage: '关联设备' }),
          dataIndex: 'associateDevices',
          valueType: TABLETREESELECT,
          formItemProps: {
            rules: [
              {
                required: true,
                message:
                  formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }) +
                  formatMessage({ id: 'device.associatedDevice', defaultMessage: '关联设备' }),
              },
            ],
          },
          fieldProps: tableTreeSelectProps,
          colProps: {
            span: 24,
          },
        });
        break;
      case ConfigTypeEnum.ChangeStack:
        result.push({
          title: formatMessage({
            id: 'device.associatedChargingHost',
            defaultMessage: '关联充电堆主机',
          }),
          dataIndex: 'associateId',
          valueType: 'select',
          request: requestChargeStack,
          formItemProps: {
            rules: [
              {
                required: true,
                message:
                  formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }) +
                  formatMessage({
                    id: 'device.associatedChargingHost',
                    defaultMessage: '关联充电堆主机',
                  }),
              },
            ],
          },
          colProps: {
            span: 24,
          },
        });
        break;
      default:
    }

    return result;
  }, [deviceData, tableTreeSelectProps]);

  const afterRequest = useCallback((formData) => {
    const result: ConfigDataType = {};
    switch (deviceData?.productConfigType) {
      case ConfigTypeEnum.Device:
        result.associateDevices = formData?.map?.((item: DeviceDataType) => {
          return {
            ...item,
            id: item.deviceId,
            deviceName: item?.name,
          };
        });
        break;
      case ConfigTypeEnum.ChangeStack:
        result.associateId = formData?.[0]?.deviceId;
        break;
      default:
    }
    return result;
  }, []);

  const beforeSubmit = useCallback(
    (formData: ConfigDataType) => {
      formData.associateIds = formData?.associateDevices?.map?.((item) => item?.id || '');
      formData.productConfigType = deviceData?.productConfigType;
    },
    [deviceData],
  );

  return (
    <>
      <Button
        type="primary"
        onClick={setTrue}
        disabled={deviceData?.networkStatus === OnlineStatusEnum.Offline}
      >
        {formatMessage({ id: 'common.modify', defaultMessage: '修改' })}
      </Button>
      <ProConfigProvider valueTypeMap={tableTreeSelectValueTypeMap}>
        <SchemaForm<ConfigDataType, TABLETREESELECTVALUETYPE>
          width="400px"
          type={FormTypeEnum.Edit}
          columns={columns}
          open={open}
          onOpenChange={set}
          id={deviceData?.deviceId}
          idKey="deviceId"
          getData={getAssociateDevice}
          afterRequest={afterRequest}
          editData={updateAssociateDevice}
          beforeSubmit={beforeSubmit}
          onSuccess={onSuccess}
        />
      </ProConfigProvider>
    </>
  );
};

export default ConfigForm;
