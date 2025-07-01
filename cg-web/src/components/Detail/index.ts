/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-04-26 11:33:11
 * @LastEditTime: 2023-07-24 17:10:12
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Detail\index.ts
 */

import React from 'react';
import { default as DetailCom } from './Detail';
import type { DetailItem, DetailProps } from './Detail';
import Group from './Group';
import type { GroupItem, GroupProps } from './Group';
import { LineLabel } from './LineLabel';
import type { LineLabelProps } from './LineLabel';
import DotLabel from './DotLabel';
import type { DotLabelProps } from './DotLabel';

type CompoundedComponent = React.FC<DetailProps> & {
  Group: React.FC<GroupProps>;
  Label: React.FC<LineLabelProps>;
  DotLabel: React.FC<DotLabelProps>;
};

const Detail = DetailCom as CompoundedComponent;

Detail.Group = Group;
Detail.Label = LineLabel;
Detail.DotLabel = DotLabel;

export { DetailItem, DetailProps, GroupItem, GroupProps, LineLabelProps, DotLabelProps };

export default Detail;
