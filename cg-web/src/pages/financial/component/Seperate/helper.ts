/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-21 11:45:37
 * @LastEditTime: 2025-02-21 11:45:37
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/financial/component/Seperate/helper.ts
 */

import { ProColumns } from "@ant-design/pro-components";
import { formatMessage } from '@/utils';
import { getAgentCol } from "@/utils/column";
import { connectStatus, logSelect } from "@/utils/dict";
import { SeperateTypeEnum } from "@/utils/enum";


export const getColumns = (type: SeperateTypeEnum) => {
  const columns: ProColumns[] = [
    {
      title: formatMessage({ id: '', defaultMessage: '充电站' }),
      dataIndex: 'a3',
      width: 120,
      ellipsis: true,
      hideInTable: type != SeperateTypeEnum.Platform,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '分成者' }),
      dataIndex: 'a1',
      width: 120,
      ellipsis: true,
      hideInTable: type != SeperateTypeEnum.Person,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '手机号' }),
      dataIndex: 'a2',
      width: 150,
      ellipsis: true,
      hideInSearch: true,
      hideInTable: type != SeperateTypeEnum.Person,
    },
    getAgentCol(),
    {
      title: formatMessage({ id: '', defaultMessage: '手机号' }),
      dataIndex: 'a',
      width: 150,
      ellipsis: true,
      hideInSearch: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '月份' }),
      dataIndex: 'b',
      width: 120,
      ellipsis: true,
      hideInSearch: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '月总分成额(元)' }),
      dataIndex: 'c',
      width: 150,
      ellipsis: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '分成金额(元)' }),
      dataIndex: 'd',
      width: 120,
      ellipsis: true,
      hideInSearch: true,
      hideInTable: type == SeperateTypeEnum.Platform,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '代理商分成金额(元)' }),
      dataIndex: 'e',
      width: 150,
      ellipsis: true,
      hideInSearch: true,
      hideInTable: type == SeperateTypeEnum.Agent,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '平台分成金额(元)' }),
      dataIndex: 'e',
      width: 150,
      ellipsis: true,
      hideInSearch: true,
      hideInTable: type == SeperateTypeEnum.Person,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '订单创建时间' }),
      dataIndex: 'b',
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
  return columns;
};