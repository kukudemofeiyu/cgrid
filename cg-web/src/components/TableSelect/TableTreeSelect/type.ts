/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-06-13 09:09:01
 * @LastEditTime: 2024-06-13 09:09:01
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\TableSelect\TableTreeSelect\type.ts
 */

import type { BasicDataNode } from 'rc-tree';
import type { DataNode } from 'antd/lib/tree';

export type TreeNode = BasicDataNode &
  DataNode & {
    id?: string | number;
    label?: string;
    children?: TreeNode[];
    [key: string]: any;
  };
