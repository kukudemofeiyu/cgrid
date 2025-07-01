/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-21 10:57:01
 * @LastEditTime: 2024-03-13 15:30:34
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\OrgTree\index.tsx
 */

import React, { useEffect, useCallback, useState, useMemo } from 'react';
import { Tree, Spin, Input } from 'antd';
import type { TreeProps } from 'antd';
import { useRequest } from 'umi';
import { getOrgTree } from './service';
import { TreeNode, OrgTypeEnum } from './type';
import styles from './index.less';
import {
  YTInstallerOutlined,
  YTCompanyOutlined,
  YTOwnerOutlined,
  YTOperatorOutlined,
  YTOrganizeOutlined,
} from '@/components/YTIcons';

const orgIconMap = new Map([
  [OrgTypeEnum.System, YTOrganizeOutlined],
  [OrgTypeEnum.Install, YTInstallerOutlined],
  [OrgTypeEnum.Operator, YTOperatorOutlined],
  [OrgTypeEnum.Owner, YTOwnerOutlined],
]);

export type OrgTreeProps = Omit<TreeProps, 'treeData' | 'onSelect'> & {
  type?: OrgTypeEnum;
  onSelect?: TreeProps<TreeNode>['onSelect'];
  afterRequest?: (data: any) => void;
};

const dealData = (data: any, siteParentItem?: any, parentItem?: any) => {
  data?.map?.((item: any) => {
    item.icon = orgIconMap.get(item.type) || YTCompanyOutlined;
    if (siteParentItem) {
      item.siteId = item.id;
      item.siteName = item.name;
      item.id = `${item.id}-${siteParentItem.id}`;
      item.label = item.name;
      item.type = siteParentItem.type;
      item.parentId = siteParentItem.id;
    }
    if (parentItem) {
      item.parentId = parentItem.id;
    }
    if (item?.sites && item?.sites?.length) {
      dealData(item.sites, item);
      item.children = item.sites;
    } else if (item?.children && item?.children?.length) {
      dealData(item.children, undefined, item);
    }
  });
};

const filterData = (data: TreeNode[], searchValue: string) => {
  const result: TreeNode[] = [];
  data.forEach((item) => {
    const obj = { ...item };
    if (obj.label?.indexOf && obj.label?.indexOf?.(searchValue) > -1) {
      result.push(obj);
    } else {
      if (obj.children && obj.children.length) {
        const arr = filterData(obj.children, searchValue);
        if (arr && arr.length) {
          obj.children = arr;
          result.push(obj);
        }
      }
    }
  });
  return result;
};

const OrgTree: React.FC<OrgTreeProps> = (props) => {
  const { type, onSelect, afterRequest, ...restProps } = props;

  const [searchValue, setSearchValue] = useState<string>('');

  const {
    loading,
    data: treeData,
    run,
  } = useRequest(getOrgTree, {
    manual: true,
    formatResult: ({ data }) => {
      dealData(data);
      afterRequest?.(data);
      return data;
    },
  });

  const filterTreeData = useMemo(() => {
    if (searchValue) {
      return filterData(treeData, searchValue);
    } else {
      return treeData;
    }
  }, [treeData, searchValue]);

  const onSearch = useCallback((value) => {
    setSearchValue(value);
  }, []);

  useEffect(() => {
    run({ type });
  }, [type]);

  return (
    <>
      {loading ? (
        <div className="flex h-full">
          <Spin className="flex1" />
        </div>
      ) : (
        <>
          <Input.Search className={styles.search} onSearch={onSearch} />
          <Tree<TreeNode>
            className={styles.tree}
            treeData={filterTreeData}
            defaultExpandAll={true}
            fieldNames={{
              title: 'label',
              key: 'id',
              children: 'children',
            }}
            onSelect={onSelect}
            showIcon
            {...restProps}
          />
        </>
      )}
    </>
  );
};

export default OrgTree;
