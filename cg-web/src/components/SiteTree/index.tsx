/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-21 10:57:01
 * @LastEditTime: 2023-06-25 15:14:29
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\SiteTree\index.tsx
 */

import React, { useEffect, useCallback, useState } from 'react';
import { Tree, Spin } from 'antd';
import type { TreeProps } from 'antd';
import { useRequest } from 'umi';
import { getSiteTree } from './service';
import type { TreeNode } from './type';
import { ChildSystemEnum } from '@/utils/dictionary';
import {
  YTHomeOutlined,
  YTPhotovoltaicOutlined,
  YTPVInverterOutlined,
  YTEnergyOutlined,
  YTEmsOutlined,
  YTBmsOutlined,
  YTAirOutlined,
  YTMeterOutlined,
  YTChargeOutlined,
  YTElectricOutlined,
  YTBatteryOutlined,
  YTChargeStackOutlined,
  YTCabinetOutlined,
} from '@/components/YTIcons';
import styles from './index.less';

export type SiteTreeProps = Omit<TreeProps, 'treeData' | 'onSelect'> & {
  siteId?: string;
  onSelect?: TreeProps<TreeNode>['onSelect'];
};

const childSystemMap = new Map([
  [ChildSystemEnum.Charge, YTChargeOutlined],
  [ChildSystemEnum.Energy, YTEnergyOutlined],
  [ChildSystemEnum.Pv, YTPhotovoltaicOutlined],
  [ChildSystemEnum.Electric, YTElectricOutlined],
  [ChildSystemEnum.Cabinet, YTCabinetOutlined],
]);

const deviceMap = new Map([
  [1, YTEmsOutlined],
  [2, YTBmsOutlined],
  [3, YTEnergyOutlined],
  [6, YTMeterOutlined],
  [7, YTAirOutlined],
  [11, YTPVInverterOutlined],
  [13, YTChargeOutlined],
  [14, YTChargeOutlined],
  [16, YTChargeOutlined],
  [17, YTMeterOutlined],
  [18, YTMeterOutlined],
  [19, YTChargeStackOutlined],
  [20, YTChargeOutlined],
  [21, YTChargeOutlined],
  [22, YTChargeOutlined],
  [24, YTChargeOutlined],
  [25, YTChargeOutlined],
  [26, YTMeterOutlined],
  [28, YTPVInverterOutlined],
  [30, YTMeterOutlined],
  [31, YTMeterOutlined],
  [32, YTCabinetOutlined],
  [33, YTChargeStackOutlined],
]);

const dealTreeData = (data: TreeNode[]) => {
  const keys: string[] = [];
  data?.forEach?.((item) => {
    if (item.type === 3) {
      item.id = 'site' + item.id;
      item.icon = YTHomeOutlined;
      keys.push(item.id);
    } else if (item.type === 2) {
      item.icon = childSystemMap.get(item.id as any) || null;
      keys.push(item.id);
    } else {
      item.icon = deviceMap.get(item.productId as number) || null;
    }
    if (item.children && item.children.length) {
      keys.push(...dealTreeData(item.children));
    }
  });
  return keys;
};

const SiteTree: React.FC<SiteTreeProps> = (props) => {
  const { siteId, onSelect, ...restProps } = props;

  const [expandedKeys, setExpandedKeys] = useState<string[]>();

  const {
    loading,
    data: treeData,
    run,
  } = useRequest(getSiteTree, {
    manual: true,
    formatResult: ({ data }) => {
      if (data) {
        const result = [data];
        setExpandedKeys(dealTreeData(result));
        return result;
      }
      return [];
    },
  });

  useEffect(() => {
    if (siteId) {
      run({ siteId });
    }
  }, [siteId]);

  return (
    <>
      {loading ? (
        <div className="flex h-full">
          <Spin className="flex1" />
        </div>
      ) : (
        <Tree<TreeNode>
          className={styles.tree}
          treeData={treeData}
          defaultExpandedKeys={expandedKeys}
          fieldNames={{
            title: 'name',
            key: 'id',
            children: 'children',
          }}
          onSelect={onSelect}
          showIcon
          {...restProps}
        />
      )}
    </>
  );
};

export default SiteTree;
