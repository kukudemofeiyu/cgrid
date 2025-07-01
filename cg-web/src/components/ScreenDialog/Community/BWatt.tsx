/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-28 09:02:14
 * @LastEditTime: 2024-03-26 17:21:18
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\Community\BWatt.tsx
 */

import React, { useCallback, useState, useRef, useEffect, useMemo, useContext } from 'react';
import { message } from 'antd';
import { BetaSchemaForm } from '@ant-design/pro-components';
import type { ProFormColumnsType, ProFormInstance } from '@ant-design/pro-components';
import type { CommunityType } from './data.d';
import type { EquipFormType } from '@/components/EquipForm/data.d';
import { getEquipInfo, editEquipConfig, getThirdStation } from '@/services/equipment';
import { getModalProps } from '@/components/Dialog';
import { CommunityProps } from './';
import { OptionType } from '@/types';
import { formatMessage } from '@/utils';
import { DeviceTypeEnum } from '@/utils/dictionary';
import DeviceContext from '@/components/Device/Context/DeviceContext';

const BWatt: React.FC<CommunityProps> = (props) => {
  const { id, type, deviceData, productConfigType, open, onOpenChange, model } = props;
  const formRef = useRef<ProFormInstance>();
  const [equipData, setEquipData] = useState<EquipFormType>();
  const { updateData } = useContext(DeviceContext);

  const modalProps = getModalProps(model);

  const requestStation = useCallback(
    (params) => {
      if (params?.loadDevice) {
        return getThirdStation({
          current: 1,
          pageSize: 10000,
          productId: equipData?.productId,
        }).then(({ data }) => {
          return data?.list?.map?.((item: any) => {
            return {
              label: item?.siteName,
              value: item?.id,
            };
          });
        });
      } else {
        return Promise.resolve([]);
      }
    },
    [equipData?.productId],
  );

  const onFinish = useCallback(
    (formData) => {
      return editEquipConfig({
        deviceId: id,
        productId: equipData?.productId,
        paramConfigType: type,
        config: JSON.stringify(formData),
        productConfigType,
      }).then(({ data }) => {
        if (data) {
          message.success(formatMessage({ id: 'common.successSaved', defaultMessage: '保存成功' }));
          updateData?.();
          return true;
        }
      });
    },
    [id, equipData, productConfigType, updateData],
  );

  useEffect(() => {
    if (open) {
      formRef?.current?.resetFields?.();
      getEquipInfo({ deviceId: id }).then(({ data }) => {
        setEquipData(data || {});
        let config = (data || {})?.config || '{}';
        try {
          config = JSON.parse(config);
        } catch (e) {
          config = {};
        }
        formRef?.current?.setFieldsValue?.({ ...config, loadDevice: true });
      });
    }
  }, [open]);

  const columns = useMemo<ProFormColumnsType<CommunityType>[]>(() => {
    const result: ProFormColumnsType<CommunityType>[] = [
      {
        dataIndex: 'loadDevice',
        hideInForm: true,
      },
      {
        title: formatMessage({ id: 'device.item', defaultMessage: '项目' }) + ' ID',
        dataIndex: 'projectId',
      },
      {
        title: formatMessage(
          { id: 'device.masterSentence', defaultMessage: '主机' },
          { name: 'EMS ID' },
        ),
        dataIndex: 'masterEmsId',
        hideInForm: deviceData?.productId != DeviceTypeEnum.React100WEmsEnergy,
      },
      {
        title: formatMessage(
          { id: 'device.masterSentence', defaultMessage: '主机' },
          {
            name: formatMessage(
              { id: 'device.systemClock', defaultMessage: '系统时钟' },
              { name: 'ID' },
            ),
          },
        ),
        dataIndex: 'masterClockDeviceId',
        hideInForm: deviceData?.productId != DeviceTypeEnum.React100WEmsEnergy,
      },
      {
        title: 'BMS ID',
        dataIndex: 'bmsId',
      },
      {
        title:
          deviceData?.productId == DeviceTypeEnum.React100WEmsEnergy
            ? formatMessage(
                { id: 'device.slaveSentence', defaultMessage: '从机' },
                { name: 'EMS ID' },
              )
            : 'EMS ID',
        dataIndex: 'emsId',
      },
      {
        title: 'PCS ID',
        dataIndex: 'pcsId',
      },
      {
        title: formatMessage({ id: 'device.cellTemperature', defaultMessage: '单体温度' }) + ' ID',
        dataIndex: 'temperatureDeviceId',
      },
      {
        title: formatMessage({ id: 'device.cellVoltage', defaultMessage: '单体电压' }) + ' ID',
        dataIndex: 'voltageDeviceId',
      },
      {
        title: formatMessage({ id: 'device.airConditioner', defaultMessage: '空调' }) + ' ID',
        dataIndex: 'airConditionerId',
      },
      {
        title:
          deviceData?.productId == DeviceTypeEnum.React100WEmsEnergy
            ? formatMessage(
                { id: 'device.slaveSentence', defaultMessage: '从机' },
                {
                  name: formatMessage(
                    { id: 'device.systemClock', defaultMessage: '系统时钟' },
                    { name: 'ID' },
                  ),
                },
              )
            : formatMessage(
                { id: 'device.systemClock', defaultMessage: '系统时钟' },
                { name: 'ID' },
              ),
        dataIndex: 'clockDeviceId',
      },
      {
        title:
          formatMessage({ id: 'device.energyConsumptionStatistics', defaultMessage: '能耗统计' }) +
          ' ID',
        dataIndex: 'statisticsDeviceId',
      },
      {
        title: formatMessage({ id: 'device.thirdPartySite', defaultMessage: '第三方站点' }) + ' ID',
        dataIndex: 'thirdSiteId',
        valueType: 'select',
        dependencies: ['loadDevice'],
        request: requestStation,
        fieldProps: (form) => {
          return {
            onChange: (_: any, option: OptionType) => {
              form?.setFieldValue('thirdSiteName', option?.label);
            },
            getPopupContainer: (triggerNode: any) => triggerNode?.parentElement,
          };
        },
      },
      {
        title: formatMessage({ id: 'device.gridMeter', defaultMessage: '电网侧电表' }) + ' ID',
        dataIndex: 'powerGridSideId',
      },
      {
        title: formatMessage({ id: 'device.inverterMeter', defaultMessage: '逆变侧电表' }) + ' ID',
        dataIndex: 'inverterSideId',
      },
      {
        dataIndex: 'thirdSiteName',
        formItemProps: {
          hidden: true,
        },
      },
    ];
    return result;
  }, [requestStation, productConfigType, deviceData]);

  return (
    <>
      <BetaSchemaForm<CommunityType>
        formRef={formRef}
        layoutType="ModalForm"
        title={formatMessage({
          id: 'device.setCommunicationInformation',
          defaultMessage: '设置通信信息',
        })}
        width={552}
        visible={open}
        onVisibleChange={onOpenChange}
        columns={columns}
        onFinish={onFinish}
        modalProps={{
          centered: true,
          destroyOnClose: true,
          ...modalProps,
        }}
        grid={true}
        colProps={{
          span: 12,
        }}
      />
    </>
  );
};

export default BWatt;
