/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-22 11:04:16
 * @LastEditTime: 2025-02-22 11:10:22
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/threePart/order/helper.ts
 */

import { ProColumns } from "@ant-design/pro-components";
import { formatMessage } from '@/utils';
import { getAgentCol } from "@/utils/column";
import { connectStatus, deviceType, logSelect, siteStatus, supportOption } from "@/utils/dict";


export const columns: ProColumns[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '充电订单号' }),
    dataIndex: 'a',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电接口唯一标识' }),
    dataIndex: 'b',
    valueEnum: supportOption,
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '运营商ID' }),
    dataIndex: 'c',
    valueEnum: supportOption,
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电桩ID' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电设备ID' }),
    dataIndex: 'e',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备所属方ID' }),
    dataIndex: 'f',
    valueEnum: deviceType,
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '车牌号' }),
    dataIndex: 'g',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '车牌唯一标识码' }),
    dataIndex: 'h',
    valueEnum: siteStatus,
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电开始SOC' }),
    dataIndex: 'i',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电结束SOC' }),
    dataIndex: 'j',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '开始充电时间' }),
    dataIndex: 'k',
    valueType: 'dateRange',
    width: 150,
    ellipsis: true,
    render: (_, record) => record.createTime,
    search: {
      transform: (value) => {
        return {
          startTime: value[0],
          endTime: value[1],
        };
      },
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '结束充电时间' }),
    dataIndex: 'l',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '尖阶段电量' }),
    dataIndex: 'm',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '峰阶段电量' }),
    dataIndex: 'n',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '平阶段电量' }),
    dataIndex: 'o',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '谷阶段电量' }),
    dataIndex: 'p',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '推送时间' }),
    dataIndex: 'q',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '总电费' }),
    dataIndex: 'r',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '总服务费' }),
    dataIndex: 'r',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '累计总金额' }),
    dataIndex: 'r',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电结束原因' }),
    dataIndex: 'r',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电结束原因描述' }),
    dataIndex: 'r',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '总耗电量' }),
    dataIndex: 'r',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
];