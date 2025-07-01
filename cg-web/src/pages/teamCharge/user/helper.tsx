/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-22 11:31:12
 * @LastEditTime: 2025-02-22 11:31:12
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/teamCharge/user/helper.tsx
 */

import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage, isEmpty } from "@/utils";
import { bindStatus, enableStatus, gunStatus, siteDimention } from "@/utils/dict";
import { YTProColumns } from "@/components/ProTable/typing";
import { QrcodeOutlined } from "@ant-design/icons";
import { verifyPhone } from "@/utils/reg";

export const columns: YTProColumns<any, any>[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '用户手机' }),
    dataIndex: 'c',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '姓名' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '用户昵称' }),
    dataIndex: 'e',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '卡号' }),
    dataIndex: 'g',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '团体名称' }),
    dataIndex: 'h',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '绑定状态' }),
    dataIndex: 'i',
    valueEnum: bindStatus,
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '最近绑定时间' }),
    dataIndex: 'j',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
];

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '联系方式' }),
    dataIndex: 'c',
    readonly: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '姓名' }),
    dataIndex: 'd1',
    readonly: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '备注' }),
    dataIndex: 'dimention',
    readonly: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '申请绑定团体' }),
    dataIndex: 'e',
    readonly: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '被申请团体卡号' }),
    dataIndex: 'f',
    readonly: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '审核意见' }),
    dataIndex: 'g',
    valueType: 'textarea',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
];