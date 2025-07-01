/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-27 19:08:24
 * @LastEditTime: 2023-12-28 14:35:45
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceDetail\config.ts
 */
import type { BasicDataNode } from 'rc-tree';
import type { DataNode } from 'antd/lib/tree';
import { DeviceDataType } from '@/services/equipment';
import { ValueEnum } from '@/types';
import { OnlineStatusEnum } from '@/utils/dict';

export type TreeNode = BasicDataNode & DataNode & DeviceDataType;

export const networkStatusShows = [OnlineStatusEnum.Online, OnlineStatusEnum.Offline];

export const netWorkStatusEnum: ValueEnum = {
  [OnlineStatusEnum.Online]: {
    text: '',
    icon: 'green',
    status: 'Processing',
  },
  [OnlineStatusEnum.Offline]: {
    text: '',
    icon: 'red',
    status: 'Default',
  },
};
