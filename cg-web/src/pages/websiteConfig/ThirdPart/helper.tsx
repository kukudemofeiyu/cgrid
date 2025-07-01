/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-24 13:50:32
 * @LastEditTime: 2025-02-24 13:53:23
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/websiteConfig/ThirdPart/helper.tsx
 */

import Detail from "@/components/Detail";
import { formatMessage } from "@/utils";
import { ProFormColumnsType } from "@ant-design/pro-components";

export const formColumns: ProFormColumnsType[] = [
  {
    renderFormItem: () => {
      return (
        <Detail.DotLabel
          title={formatMessage({ id: '', defaultMessage: '地图设置', })}
          className="mb0"
        />
      );
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '高德地图Key' }),
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
    title: formatMessage({ id: '', defaultMessage: '腾讯地图Key' }),
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
    renderFormItem: () => {
      return (
        <Detail.DotLabel
          title={formatMessage({ id: '', defaultMessage: '短信设置', })}
          className="mb0"
        />
      );
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: 'APP_KEY' }),
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
    title: formatMessage({ id: '', defaultMessage: 'SECRET_KEY' }),
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
    title: formatMessage({ id: '', defaultMessage: '短信内容签名' }),
    dataIndex: 'f',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
];