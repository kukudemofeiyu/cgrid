/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-20 16:11:25
 * @LastEditTime: 2025-02-20 16:11:25
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/chargeStation/gun/helper.tsx
 */

import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage } from "@/utils";
import { gunStatus } from "@/utils/dict";
import { YTProColumns } from "@/components/ProTable/typing";
import { QrcodeOutlined } from "@ant-design/icons";

export const columns: YTProColumns<any, any>[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '充电桩类型' }),
    dataIndex: 'c',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '枪口名称' }),
    dataIndex: 'd',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '枪口编号' }),
    dataIndex: 'e',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '状态' }),
    dataIndex: 'g',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '所属代理商' }),
    dataIndex: 'h',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电桩编号' }),
    dataIndex: 'i',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '二维码' }),
    dataIndex: 'k',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
    renderWithEmit(_, { code, emit }) {
      return <QrcodeOutlined onClick={() => emit('qrcode', { code })} />
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '创建时间' }),
    dataIndex: 'j',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
];

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '充电桩' }),
    dataIndex: 'a',
    valueType: 'select',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '枪口编号' }),
    dataIndex: 'b',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '枪口名称' }),
    dataIndex: 'c',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '枪口状态' }),
    dataIndex: 'd',
    valueType: 'select',
    valueEnum: gunStatus,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
];