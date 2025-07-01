/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-22 10:47:18
 * @LastEditTime: 2025-02-22 10:53:28
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/threePart/interface/helper.ts
 */

import { ProColumns } from "@ant-design/pro-components";
import { formatMessage } from '@/utils';
import { getAgentCol } from "@/utils/column";
import { connectStatus, logSelect, operateStatus, siteStatus } from "@/utils/dict";


export const columns: ProColumns[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '充电设备接口名称' }),
    dataIndex: 'h',
    valueEnum: siteStatus,
    width: 180,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电设备接口编码' }),
    dataIndex: 'a',
    width: 180,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电设备接口类' }),
    dataIndex: 'b',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '额定电压上线' }),
    dataIndex: 'c',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '额定电压下线' }),
    dataIndex: 'd',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '额定电流' }),
    dataIndex: 'e',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '额定功率' }),
    dataIndex: 'f',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '国家标准' }),
    dataIndex: 'g',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '车位号' }),
    dataIndex: 'i',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '恒功率电压上线' }),
    dataIndex: 'j',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '恒功率电压下线' }),
    dataIndex: 'k',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '恒功率电流上线' }),
    dataIndex: 'l',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '恒功率电流下线' }),
    dataIndex: 'm',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '辅助电源' }),
    dataIndex: 'n',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '运营状态' }),
    dataIndex: 'o',
    valueEnum: operateStatus,
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '运营时间' }),
    dataIndex: 'p',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '最大充电功率' }),
    dataIndex: 'q',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备接口分类' }),
    dataIndex: 'r',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '归属设备' }),
    dataIndex: 's',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
];