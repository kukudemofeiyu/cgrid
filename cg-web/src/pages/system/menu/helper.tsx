/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-24 14:30:23
 * @LastEditTime: 2025-02-24 14:30:23
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/system/menu/helper.tsx
 */

import { ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage, isEmpty } from "@/utils";
import { YTProColumns } from "@/components/ProTable/typing";
import { verifyPhone } from "@/utils/reg";
import { connectEnum, enableStatus, menuType } from "@/utils/dict";
import { createIcon } from "@/utils/IconUtil";

export const columns: YTProColumns<any, any>[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '菜单名称' }),
    dataIndex: 'c',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '权限标识' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '路由地址' }),
    dataIndex: 'e',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '菜单类型' }),
    dataIndex: 'f',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '图标' }),
    dataIndex: 'g',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '顺序' }),
    dataIndex: 'h',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '状态' }),
    dataIndex: 'i',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
];

export const getFormColumn = (showIconModal: any) => {
  const formColumns: ProFormColumnsType[] = [
    {
      title: formatMessage({ id: '', defaultMessage: '父级菜单' }),
      dataIndex: 'i',
      valueType: 'treeSelect',
      formItemProps: {
        rules: [
          {
            required: true,
          },
        ],
      },
    },
    {
      title: formatMessage({ id: '', defaultMessage: '菜单类型' }),
      dataIndex: 'e',
      valueEnum: menuType,
      formItemProps: {
        rules: [
          {
            required: true,
          },
        ],
      },
    },
    {
      title: formatMessage({ id: '', defaultMessage: '菜单名称' }),
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
      title: formatMessage({ id: '', defaultMessage: '权限标识' }),
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
      title: formatMessage({ id: '', defaultMessage: '路由地址' }),
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
      title: formatMessage({ id: '', defaultMessage: '图标' }),
      dataIndex: 'icon',
      fieldProps: (form, config) => {
        return {
          addonAfter: createIcon(form?.getFieldValue?.('icon')),
          onClick: () => {
            showIconModal?.(true);
          }
        };
      },
    },
    {
      title: formatMessage({ id: '', defaultMessage: '顺序' }),
      dataIndex: 'g',
      valueType: 'digit',
    },
    {
      title: formatMessage({ id: '', defaultMessage: '状态' }),
      dataIndex: 'h',
      valueType: 'switch',
      valueEnum: enableStatus,
    },
  ];
  return formColumns;
};