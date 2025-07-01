/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-05-16 15:11:27
 * @LastEditTime: 2024-05-16 17:56:29
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Control\components\TreeSelect\index.tsx
 */

import React, { memo, useMemo } from 'react';
import { FieldModeEnum, FieldType } from '../../type';
import { DeviceModelShowTypeEnum, formatModelValue } from '@/utils';
import { TreeSelect as AntTreeSelect, TreeSelectProps } from 'antd';
import { DeviceTreeSelectType } from '@/types/device';

type TreeSelectType = TreeSelectProps & FieldType;

const TreeSelect: React.FC<TreeSelectType> = (props) => {
  const { value, onChange, field, type = FieldModeEnum.Read, data, ...restProps } = props;

  const isFormMode = useMemo(() => {
    if (type == FieldModeEnum.Read) {
      return field?.showType == DeviceModelShowTypeEnum.Form;
    } else {
      return !field?.form?.showType || field?.form?.showType == DeviceModelShowTypeEnum.Form;
    }
  }, [field, type]);

  const treeData = useMemo<TreeSelectProps['treeData']>(() => {
    const result: TreeSelectProps['treeData'] = [
      {
        label: '全部',
        value: '',
        children: [],
      },
    ];
    (field?.dataType as DeviceTreeSelectType)?.specs?.forEach?.((item) => {
      if (item.id) {
        result[0]?.children?.push({
          label: item.name,
          value: item.id,
        });
      }
    });
    return result;
  }, [field]);

  return (
    <>
      {isFormMode ? (
        <AntTreeSelect
          treeCheckable
          treeData={treeData}
          value={value}
          onChange={onChange}
          {...restProps}
        />
      ) : (
        formatModelValue(value, field?.dataType || {}, false)
      )}
    </>
  );
};

export default memo(TreeSelect);
