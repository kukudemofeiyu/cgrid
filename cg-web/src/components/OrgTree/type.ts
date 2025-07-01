/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-21 11:00:05
 * @LastEditTime: 2023-07-10 11:45:07
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\OrgTree\type.ts
 */

import type { BasicDataNode } from 'rc-tree';
import type { DataNode } from 'antd/lib/tree';

export enum OrgTypeEnum {
  System = 0,
  Install = 1,
  Operator = 2,
  Owner = 3,
}

export type TreeNode = BasicDataNode &
  DataNode & {
    id?: string | number;
    label?: string;
    type?: OrgTypeEnum;
    siteId?: string;
    siteName?: string;
    parentId?: string;
    children?: TreeNode[];
  };

export type TreeData = TreeNode[];
