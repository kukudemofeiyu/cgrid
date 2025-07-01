/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-21 10:49:03
 * @LastEditTime: 2025-02-21 11:23:44
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/agent/platformSeperate/helper.ts
 */

import { ProColumns } from "@ant-design/pro-components";
import { formatMessage } from '@/utils';
import { getAgentCol } from "@/utils/column";
import { seperateTypeOption } from "@/utils/dict";
import { DetailItem } from "@/components/Detail";
import Icon1 from '@/assets/image/operation/1.png';
import Icon2 from '@/assets/image/operation/2.png';
import Icon4 from '@/assets/image/operation/4.png';
import Icon5 from '@/assets/image/operation/5.png';
import { SeperateTypeEnum } from "@/utils/enum";

export const getColumns = (type: SeperateTypeEnum) => {
  const columns: ProColumns[] = [
    getAgentCol(),
    {
      title: formatMessage({ id: '', defaultMessage: '订单编号' }),
      dataIndex: 'a',
      width: 150,
      ellipsis: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '订单创建时间' }),
      dataIndex: 'b',
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
      title: formatMessage({ id: '', defaultMessage: '订单总金额' }),
      dataIndex: 'c',
      width: 120,
      ellipsis: true,
      hideInSearch: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '站点名称' }),
      dataIndex: 'd',
      width: 120,
      ellipsis: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '充电桩编号' }),
      dataIndex: 'e',
      width: 150,
      ellipsis: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '分账类型' }),
      dataIndex: 'f',
      valueEnum: seperateTypeOption,
      ellipsis: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '平台分账金额(元)' }),
      dataIndex: 'g',
      width: 150,
      ellipsis: true,
      hideInSearch: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '平台分成比例' }),
      dataIndex: 'h',
      width: 120,
      ellipsis: true,
      hideInSearch: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '代理商分账总金额(元)' }),
      dataIndex: 'i',
      width: 180,
      ellipsis: true,
      hideInSearch: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '代理商分成比例' }),
      dataIndex: 'j',
      width: 150,
      ellipsis: true,
      hideInSearch: true,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '分成者分成总金额(元)' }),
      dataIndex: 'g',
      width: 180,
      ellipsis: true,
      hideInSearch: true,
      hideInTable: type != SeperateTypeEnum.Agent,
    },
  ];
  return columns;
};

export const getItems = (type: SeperateTypeEnum) => {
  const totalItems: DetailItem[] = [
    {
      title: formatMessage({ id: '', defaultMessage: '订单分账总金额(元)' }),
      dataIndex: 'a',
      icon: Icon1,
      span: type != SeperateTypeEnum.Agent ? 8 : 6,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '平台分账总金额(元)' }),
      dataIndex: 'b',
      icon: Icon2,
      span: type != SeperateTypeEnum.Agent ? 8 : 6,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '代理商分账总金额((元)' }),
      dataIndex: 'c',
      icon: Icon4,
      span: type != SeperateTypeEnum.Agent ? 8 : 6,
    },
    {
      title: formatMessage({ id: '', defaultMessage: '分成者分账总金额((元)' }),
      dataIndex: 'c',
      icon: Icon5,
      span: type != SeperateTypeEnum.Agent ? 8 : 6,
      show: type == SeperateTypeEnum.Agent,
    },
  ];
  return totalItems;
};
