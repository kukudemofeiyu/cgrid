/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-20 17:00:20
 * @LastEditTime: 2025-02-20 17:00:20
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/utils/column.ts
 */

import { ProColumns } from "@ant-design/pro-components";
import { formatMessage } from ".";
import { merge } from "lodash";

export const getAgentCol = (col?: ProColumns) => {
  const column: ProColumns = {
    title: formatMessage({ id: '', defaultMessage: '代理商' }),
    dataIndex: 'e',
    valueType: 'select',
    width: 150,
    ellipsis: true,
    render: (_, { agent }) => {
      return agent;
    }
  };
  return merge(column, col);
};

export const getSiteCol = (col?: ProColumns) => {
  const column: ProColumns = {
    title: formatMessage({ id: '', defaultMessage: '站点' }),
    dataIndex: 'e',
    valueType: 'select',
    width: 150,
    ellipsis: true,
    render: (_, { agent }) => {
      return agent;
    }
  };
  return merge(column, col);
};
