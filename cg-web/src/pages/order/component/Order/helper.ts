/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-20 14:41:58
 * @LastEditTime: 2025-02-20 15:06:00
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/order/component/Order/helper.ts
 */

import { ProColumns } from "@ant-design/pro-components";
import { formatMessage } from '@/utils';

export enum orderType {
  All,
  Abnormal,
  Refund,
  History,
  Realtime,
  Reservation,
  Seat,
};

export const getColumns = (type: orderType) => {

  const columns: ProColumns[] = [
    {
      title: formatMessage({ id: '', defaultMessage: '代理商' }),
      dataIndex: 'r',
      valueType: 'select',
      hideInTable: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '订单号' }),
      dataIndex: 'a',
      width: 150,
      ellipsis: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '充电站' }),
      dataIndex: 'b',
      width: 150,
      ellipsis: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '订单类型' }),
      dataIndex: 'c',
      width: 120,
      ellipsis: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '充电状态' }),
      dataIndex: 'd',
      width: 120,
      ellipsis: true,
      hideInSearch: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '订单状态' }),
      dataIndex: 'e',
      width: 120,
      ellipsis: true,
      hideInSearch: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '手机号' }),
      dataIndex: 'f',
      width: 150,
      ellipsis: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '充电桩编号' }),
      dataIndex: 'g',
      width: 150,
      ellipsis: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '枪口' }),
      dataIndex: 'h',
      width: 150,
      ellipsis: true,
      hideInSearch: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '充电时长' }),
      dataIndex: 'i',
      width: 150,
      ellipsis: true,
      hideInSearch: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '订单金额(元)' }),
      dataIndex: 'j',
      width: 120,
      ellipsis: true,
      hideInSearch: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '站点优惠金额(元)' }),
      dataIndex: 'j1',
      width: 150,
      ellipsis: true,
      hideInSearch: true,
      hideInTable: type == orderType.All || type == orderType.Realtime || type == orderType.Reservation || type == orderType.Seat,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '优惠卷优惠金额(元)' }),
      dataIndex: 'j2',
      width: 150,
      ellipsis: true,
      hideInSearch: true,
      hideInTable: type == orderType.All || type == orderType.Realtime || type == orderType.Reservation || type == orderType.Seat,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '占桩费(元)' }),
      dataIndex: 'j3',
      width: 120,
      ellipsis: true,
      hideInSearch: true,
      hideInTable: type != orderType.Seat,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '支付类型' }),
      dataIndex: 'k',
      width: 120,
      ellipsis: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '类型' }),
      dataIndex: 'l',
      width: 120,
      ellipsis: true,
      hideInSearch: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '结束原因' }),
      dataIndex: 'm',
      width: 150,
      ellipsis: true,
      hideInSearch: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '结束原因描述' }),
      dataIndex: 'n',
      width: 150,
      ellipsis: true,
      hideInSearch: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '预约时间' }),
      dataIndex: 'n1',
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
      hideInTable: type != orderType.Reservation,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '预约金额(元)' }),
      dataIndex: 'n2',
      width: 150,
      ellipsis: true,
      hideInSearch: true,
      hideInTable: type != orderType.Reservation,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '创建时间' }),
      dataIndex: 'o',
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
  ]
  return columns;
};