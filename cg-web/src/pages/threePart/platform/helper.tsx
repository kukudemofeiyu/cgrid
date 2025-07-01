/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-22 10:26:10
 * @LastEditTime: 2025-02-22 10:39:34
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/threePart/platform/helper.tsx
 */

import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage, isEmpty } from "@/utils";
import { enableStatus, gunStatus, pushOption, secretKeyOption, siteDimention } from "@/utils/dict";
import { YTProColumns } from "@/components/ProTable/typing";
import { QrcodeOutlined } from "@ant-design/icons";
import { verifyPhone } from "@/utils/reg";

export const columns: YTProColumns<any, any>[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '对接编码' }),
    dataIndex: 'c',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '对接平台名称' }),
    dataIndex: 'd',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '对接人姓名' }),
    dataIndex: 'e',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '对接人联系方式' }),
    dataIndex: 'g',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '对接方秘钥' }),
    dataIndex: 'h',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '对方签名秘钥' }),
    dataIndex: 'i',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '对方数据加密秘钥' }),
    dataIndex: 'j',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '对方初始化向量' }),
    dataIndex: 'k',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '对方接口地址' }),
    dataIndex: 'l',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '对接环境' }),
    dataIndex: 'm',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '我发平台ID' }),
    dataIndex: 'n',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '我方平台KEY' }),
    dataIndex: 'o',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '我方签名秘钥' }),
    dataIndex: 'p',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '我方数据加密秘钥' }),
    dataIndex: 'q',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '我方初始化向量' }),
    dataIndex: 'r',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '是否推送' }),
    dataIndex: 's',
    valueEnum: pushOption,
    width: 100,
    ellipsis: true,
  },
];

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '对接编码' }),
    dataIndex: 'a',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '对接平台名称' }),
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
    title: formatMessage({ id: '', defaultMessage: '对接人姓名' }),
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
    title: formatMessage({ id: '', defaultMessage: '对接人联系方式' }),
    dataIndex: 'd',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '对接方秘钥' }),
    dataIndex: 'e',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '签名秘钥' }),
    dataIndex: 'f',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '数据加密秘钥' }),
    dataIndex: 'g',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '初始化向量' }),
    dataIndex: 'h',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '接口地址' }),
    dataIndex: 'i',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '服务器IP' }),
    dataIndex: 'j',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '对接环境' }),
    dataIndex: 'k',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '我方平台ID' }),
    dataIndex: 'l',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '我放平台key' }),
    dataIndex: 'm',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '我方签名密钥' }),
    dataIndex: 'n',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '我方数据加密密钥' }),
    dataIndex: 'o',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '我方初始化向量' }),
    dataIndex: 'p',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '是否推送' }),
    dataIndex: 'q',
    valueEnum: pushOption,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '验证密钥' }),
    dataIndex: 'r',
    valueEnum: secretKeyOption,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
];