/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-06-13 09:06:55
 * @LastEditTime: 2024-06-13 09:06:55
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\TableSelect\TableTreeSelect\helper.ts
 */

import { TreeNodeProps, TreeProps } from 'antd';
import { dealTreeDataType } from './TableTreeModal';
import { TreeNode } from './type';

export const runDealTreeData = <TreeData>(
  data: TreeProps['treeData'],
  dealTreeData?: dealTreeDataType<TreeData>,
) => {
  if (data && data.length) {
    data?.forEach?.((item, index) => {
      if (item.children && item.children.length) {
        item.selectable = false;
      }
      dealTreeData?.(item as any, index);
      if (item.children && item.children.length) {
        runDealTreeData(item.children, dealTreeData);
      }
    });
  }
};

export const filterData = (data: TreeNode[], searchValue: string, labelKey: string) => {
  const result: TreeNode[] = [];
  data.forEach((item) => {
    const obj = { ...item };
    if (obj?.[labelKey]?.indexOf && obj?.[labelKey]?.indexOf?.(searchValue) > -1) {
      result.push(obj);
    } else {
      if (obj.children && obj.children.length) {
        const arr = filterData(obj.children, searchValue, labelKey);
        if (arr && arr.length) {
          obj.children = arr;
          result.push(obj);
        }
      }
    }
  });
  return result;
};

export const updateTreeData = (
  sourceData: TreeProps['treeData'],
  key: string,
  keyValue: string,
  appendData: TreeProps['treeData'],
) => {
  sourceData?.forEach?.((item: TreeNodeProps & Record<string, any>) => {
    if (item[key] == keyValue) {
      item.children = appendData;
      item.loaded = true;
      if (!appendData?.length) {
        item.isLeaf = true;
      }
    } else if (item.children) {
      updateTreeData(item.children as any, key, keyValue, appendData);
    }
  });
};
