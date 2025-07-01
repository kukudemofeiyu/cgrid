/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-04-16 14:15:43
 * @LastEditTime: 2024-04-16 16:59:02
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceInfo\IccidModal\helper.tsx
 */

import React from 'react';
import Detail, { DetailItem } from '@/components/Detail';
import { formatMessage } from '@/utils';
import { Typography, Divider, Descriptions, Tag } from 'antd';

const trafficUnitMap: Record<string, any> = {
  1: 'M',
  1024: 'G',
};

export const baseInfoItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'iccid.cardNumber', defaultMessage: '卡号' }),
    field: 'cardNumber',
  },
  {
    label: 'ICCID',
    field: 'iccid',
  },
  {
    label: 'IMSI',
    field: 'imsi',
  },
  {
    label: 'IMEI',
    field: 'imei',
  },
  {
    label: formatMessage({ id: 'iccid.activationDate', defaultMessage: '激活日期' }),
    field: 'activationDate',
  },
  {
    label: formatMessage({ id: 'iccid.cardExpiryDate', defaultMessage: '卡到期日期' }),
    field: 'packageEndTime',
  },
  {
    label: formatMessage({ id: 'iccid.networkType', defaultMessage: '网络类型' }),
    field: 'networkGradeStr',
  },
  {
    label: formatMessage({ id: 'iccid.servicePassword', defaultMessage: '服务密码' }),
    field: 'servicePassword',
  },
  {
    label: formatMessage({ id: 'iccid.realNamePhoneNumber', defaultMessage: '实名手机号' }),
    field: 'phone',
    span: 2,
  },
  {
    custom: (
      <Descriptions.Item span={2} className="pb0">
        <Divider className="mt0 mb12" />
      </Descriptions.Item>
    ),
  },
  {
    label: formatMessage({ id: 'iccid.trafficStatus', defaultMessage: '流量状态' }),
    field: 'networkStatusStr',
    format: (value, data) => (
      <Tag color={data?.networkStatus == 1 ? 'processing' : 'default'}>{value}</Tag>
    ),
  },
  {
    label: formatMessage({ id: 'iccid.voiceStatus', defaultMessage: '语音状态' }),
    field: 'voiceStatusStr',
    format: (value, data) => (
      <Tag color={data?.voiceStatus == 1 ? 'processing' : 'default'}>{value}</Tag>
    ),
  },
  {
    label: formatMessage({ id: 'iccid.realNameStatus', defaultMessage: '实名状态' }),
    field: 'realnameStatusStr',
    format: (value, data) => (
      <Tag color={data?.realnameStatus == 1 ? 'processing' : 'default'}>{value}</Tag>
    ),
  },
  {
    label: formatMessage({ id: 'iccid.cardStatus', defaultMessage: '卡状态' }),
    field: 'statusStr',
    format: (value, data) => (
      <Tag color={data?.status == 1 ? 'processing' : 'default'}>{value}</Tag>
    ),
    span: 2,
  },
  {
    custom: (
      <Descriptions.Item span={2} className="pb0">
        <Divider className="mt0 mb12" />
      </Descriptions.Item>
    ),
  },
  {
    label: formatMessage({ id: 'iccid.supplier', defaultMessage: '供应商' }),
    field: 'supplierName',
  },
];

export const packageItems: DetailItem[] = [
  {
    label: (
      <Typography.Link>
        {formatMessage({ id: 'iccid.currentPackage', defaultMessage: '当前套餐' })}
      </Typography.Link>
    ),
    field: 'packageName',
    format: (value) => <Typography.Link>{value}</Typography.Link>,
  },
  {
    label: formatMessage({ id: 'iccid.packageDuration', defaultMessage: '套餐时长' }),
    field: 'validityDuration',
    unit: '/' + formatMessage({ id: 'common.time.month', defaultMessage: '月' }),
  },
  {
    label: formatMessage({ id: 'iccid.packageStartTime', defaultMessage: '套餐开始时间' }),
    field: 'pkgActiveTime',
  },
  {
    label: formatMessage({ id: 'iccid.packageEndTime', defaultMessage: '套餐结束时间' }),
    field: 'pkgEndTime',
  },
  {
    custom: (
      <Descriptions.Item span={2} className="pb0">
        <Divider className="mt0 mb12" />
      </Descriptions.Item>
    ),
  },
  {
    label: formatMessage({ id: 'iccid.showTraffic', defaultMessage: '显示流量' }),
    field: 'packageTraffic',
    span: 2,
    format: (value, data) => value + trafficUnitMap[data?.trafficUnit || 1],
  },
  {
    label: formatMessage({ id: 'iccid.used', defaultMessage: '已用' }),
    field: 'useTraffic',
    format: (value, data) => value + trafficUnitMap[data?.trafficUnit || 1],
  },
  {
    label: formatMessage({ id: 'iccid.remaining', defaultMessage: '剩余' }),
    field: 'surplusTraffic',
    format: (value, data) => value + trafficUnitMap[data?.trafficUnit || 1],
  },
  {
    custom: (
      <Descriptions.Item span={2} className="pb0">
        <Divider className="mt0 mb12" />
      </Descriptions.Item>
    ),
  },
  {
    label: formatMessage({ id: 'iccid.packageVoice', defaultMessage: '套餐语音' }),
    field: 'packageVoice',
    span: 2,
    unit: formatMessage({ id: 'common.minute', defaultMessage: '分钟' }),
  },
  {
    label: formatMessage({ id: 'iccid.used', defaultMessage: '已用' }),
    field: 'useVoice',
    unit: formatMessage({ id: 'common.minute', defaultMessage: '分钟' }),
  },
  {
    label: formatMessage({ id: 'iccid.remaining', defaultMessage: '剩余' }),
    field: 'surplusVoice',
    unit: formatMessage({ id: 'common.minute', defaultMessage: '分钟' }),
  },
];
