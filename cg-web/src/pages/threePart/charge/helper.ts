/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-22 10:54:37
 * @LastEditTime: 2025-02-22 11:03:36
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/threePart/charge/helper.ts
 */

import { ProColumns } from "@ant-design/pro-components";
import { formatMessage } from '@/utils';
import { getAgentCol } from "@/utils/column";
import { connectStatus, deviceType, logSelect, siteStatus, supportOption } from "@/utils/dict";


export const columns: ProColumns[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '充电设备唯一标识' }),
    dataIndex: 'a',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '是否支持即插即充' }),
    dataIndex: 'b',
    valueEnum: supportOption,
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '是否支持自动调节功率' }),
    dataIndex: 'c',
    valueEnum: supportOption,
    width: 180,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备分类' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备唯一编码' }),
    dataIndex: 'e',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备类型' }),
    dataIndex: 'f',
    valueEnum: deviceType,
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电设备总功率' }),
    dataIndex: 'g',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备生产商组织机构代码' }),
    dataIndex: 'h',
    valueEnum: siteStatus,
    width: 180,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备生产商名称' }),
    dataIndex: 'i',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备型号' }),
    dataIndex: 'j',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备生产日期' }),
    dataIndex: 'k',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电设备经度' }),
    dataIndex: 'l',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电设备维度' }),
    dataIndex: 'm',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电设备名称' }),
    dataIndex: 'n',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '额定电压上线' }),
    dataIndex: 'o',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '额定电压下线' }),
    dataIndex: 'p',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '额定电流' }),
    dataIndex: 'q',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电站' }),
    dataIndex: 'r',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
];