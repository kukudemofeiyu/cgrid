/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-20 19:30:01
 * @LastEditTime: 2025-02-21 09:30:41
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/operation/helper.ts
 */

import { ProColumns } from "@ant-design/pro-components";
import { formatMessage } from '@/utils';
import { getAgentCol } from "@/utils/column";
import { connectStatus } from "@/utils/dict";
import { DetailItem } from "@/components/Detail";
import Icon1 from '@/assets/image/operation/1.png';
import Icon2 from '@/assets/image/operation/2.png';
import Icon3 from '@/assets/image/operation/3.png';
import Icon4 from '@/assets/image/operation/4.png';
import Icon5 from '@/assets/image/operation/5.png';
import Icon6 from '@/assets/image/operation/6.png';
import Icon7 from '@/assets/image/operation/7.png';
import Icon8 from '@/assets/image/operation/8.png';
import Icon9 from '@/assets/image/operation/9.png';
import Icon10 from '@/assets/image/operation/10.png';
import Icon11 from '@/assets/image/operation/11.png';
import Icon12 from '@/assets/image/operation/12.png';


export const columns: ProColumns[] = [
  getAgentCol({
    hideInTable: true,
  }),
  {
    title: formatMessage({ id: '', defaultMessage: '站点名称' }),
    dataIndex: 'a',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电桩编号' }),
    dataIndex: 'b',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电桩名称' }),
    dataIndex: 'c',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '订单数量' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电时长' }),
    dataIndex: 'e',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '累计电量' }),
    dataIndex: 'f',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '累计费用' }),
    dataIndex: 'g',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '累计电费' }),
    dataIndex: 'h',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '累计服务费' }),
    dataIndex: 'i',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电次数' }),
    dataIndex: 'j',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '运行状态' }),
    dataIndex: 'j1',
    valueEnum: connectStatus,
    width: 100,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '选择日期' }),
    dataIndex: 'o',
    valueType: 'dateRange',
    width: 150,
    ellipsis: true,
    search: {
      transform: (value) => {
        return {
          startTime: value[0],
          endTime: value[1],
        };
      },
    },
    hideInTable: true,
  },
];

export const totalItems: DetailItem[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '总收益(元)' }),
    dataIndex: 'a',
    icon: Icon1,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '实际收益(元)' }),
    dataIndex: 'b',
    icon: Icon2,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '订单退款(元)' }),
    dataIndex: 'c',
    icon: Icon3,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '电费(元)' }),
    dataIndex: 'd',
    icon: Icon4,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '服务费(元)' }),
    dataIndex: 'e',
    icon: Icon5,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '订单数量' }),
    dataIndex: 'f',
    icon: Icon6,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电次数' }),
    dataIndex: 'g',
    icon: Icon7,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电时长(小时)' }),
    dataIndex: 'h',
    icon: Icon8,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '总耗电量(kw/h)' }),
    dataIndex: 'i',
    icon: Icon9,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备总数' }),
    dataIndex: 'j',
    icon: Icon10,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '运行设备' }),
    dataIndex: 'k',
    icon: Icon11,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '离线设备' }),
    dataIndex: 'l',
    icon: Icon12,
  },
];
