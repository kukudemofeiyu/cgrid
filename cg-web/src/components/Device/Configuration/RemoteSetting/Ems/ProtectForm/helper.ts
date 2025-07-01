/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-12 10:38:41
 * @LastEditTime: 2023-09-12 11:12:58
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\RemoteSetting\Ems\ProtectForm\helper.ts
 */

import { formatMessage } from '@/utils';
import { ProFormColumnsType } from '@ant-design/pro-components';

export const columns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: 'siteMonitor.overchargeProtection', defaultMessage: '过充保护' }),
    dataIndex: 'OverchargeProtection',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'siteMonitor.overchargeProtection', defaultMessage: '过充保护' }),
        },
      ],
    },
    fieldProps: {
      addonAfter: 'V',
      className: 'w-full',
    },
  },
  {
    title: formatMessage({ id: 'siteMonitor.overchargeRelease', defaultMessage: '过充释放' }),
    dataIndex: 'OverchargeRelease',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'siteMonitor.overchargeRelease', defaultMessage: '过充释放' }),
        },
      ],
    },
    fieldProps: {
      addonAfter: 'V',
      className: 'w-full',
    },
  },
  {
    title: formatMessage({ id: 'siteMonitor.overDischargeProtection', defaultMessage: '过放保护' }),
    dataIndex: 'OverdischargeProtection',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'siteMonitor.overDischargeProtection',
              defaultMessage: '过放保护',
            }),
        },
      ],
    },
    fieldProps: {
      addonAfter: 'V',
      className: 'w-full',
    },
  },
  {
    title: formatMessage({ id: 'siteMonitor.overrelease', defaultMessage: '过放释放' }),
    dataIndex: 'Overrelease',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'siteMonitor.overrelease', defaultMessage: '过放释放' }),
        },
      ],
    },
    fieldProps: {
      addonAfter: 'V',
      className: 'w-full',
    },
  },
];
