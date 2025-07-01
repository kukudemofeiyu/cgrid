/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-21 11:00:05
 * @LastEditTime: 2023-06-21 11:00:09
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\StationTree\type.ts
 */

import type { BasicDataNode } from 'rc-tree';
import type { DataNode } from 'antd/lib/tree';

export type TreeNode = BasicDataNode &
  DataNode & {
    id: string;
    name?: string;
    type?: number;
    parentId?: number;
    productId: number;
    children?: TreeNode[];
    key?: string;
  };

export type TreeData = TreeNode[];
