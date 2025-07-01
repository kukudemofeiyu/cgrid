/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-23 14:52:00
 * @LastEditTime: 2024-03-23 14:52:00
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\PowerSwitch\helper.ts
 */

import { ProColumns } from '@ant-design/pro-components';
import { formatMessage } from '@/utils';

export const columns: ProColumns[] = [
  {
    title: formatMessage({ id: 'charge.module', defaultMessage: '模块' }),
    dataIndex: 'mode',
    width: 100,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: 'charge.moduleAddress', defaultMessage: '模块地址' }),
    dataIndex: 'mposadd',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({
      id: 'charge.moduleInherentInformation',
      defaultMessage: '模块固有信息',
    }),
    dataIndex: 'mmodinmes',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: 'charge.groupAddress', defaultMessage: '所在组地址' }),
    dataIndex: 'malladd',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: 'charge.onAndOffStatus', defaultMessage: '开关机状态' }),
    dataIndex: 'mnfs',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: 'charge.moduleOtherStatus', defaultMessage: '模块其他状态' }),
    dataIndex: 'mmodoths',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: 'charge.moduleStatusFlags', defaultMessage: '模块状态标志' }),
    dataIndex: 'mmodsid',
    width: 120,
    ellipsis: true,
  },
  {
    title:
      formatMessage({ id: 'charge.moduleOutputVoltage', defaultMessage: '模块输出电压' }) + '(V)',
    dataIndex: 'mmodopv',
    width: 120,
    ellipsis: true,
  },
  {
    title:
      formatMessage({ id: 'charge.moduleOutputCurrent', defaultMessage: '模块输出电流' }) + '(A)',
    dataIndex: 'mmodopi',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: 'charge.setVoltage', defaultMessage: '设定电压' }) + '(V)',
    dataIndex: 'msetv',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: 'charge.setCurrent', defaultMessage: '设定电流' }) + '(A)',
    dataIndex: 'mseti',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: 'charge.ambientTemperature', defaultMessage: '环境温度' }) + '(℃)',
    dataIndex: 'mamt',
    width: 120,
    ellipsis: true,
  },
];
