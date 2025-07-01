/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-11 10:21:19
 * @LastEditTime: 2024-01-26 14:16:20
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\TreeSelect\index.tsx
 */
import React, { useCallback, useState, useMemo } from 'react';
import { Tree, TreeProps, Space, Checkbox } from 'antd';
import type { CheckboxChangeEvent } from 'antd/es/checkbox';
import styles from './index.less';
import { merge } from 'lodash';
import { formatMessage } from '@/utils';

type TreeValueType = {
  checkedKeys?: React.Key[];
  halfCheckedKeys?: React.Key[];
};

export type TreeSelectProps = Omit<
  TreeProps<{ [key in string]: any }>,
  'checkedKeys' | 'onCheck' | 'checkStrictly'
> & {
  value?: TreeValueType;
  onChange?: (value: TreeValueType) => void;
  request?: Promise<any>;
};

const getDeepKeys = (
  data: TreeProps<{ [key in string]: any }>['treeData'],
  key: string,
): string[] => {
  const keys: string[] = [];
  data?.forEach?.((item) => {
    if (item?.[key]) {
      keys.push(item?.[key]);
    }
    if (item?.children && item?.children?.length) {
      keys.push(...getDeepKeys(item.children, key));
    }
  });
  return keys;
};

const TreeSelect: React.FC<TreeSelectProps> = (props) => {
  const { value, onChange, treeData, request } = props;

  const [expandedKeys, setExpandedKeys] = useState<React.Key[]>();
  const [currentTreeData, setCurrentTreeData] = useState(treeData || []);

  request?.then((data) => {
    if (data?.length) {
      setCurrentTreeData(data);
    }
  });
  const options = useMemo<TreeSelectProps>(() => {
    const defaultOptions: TreeSelectProps = {
      fieldNames: {
        key: 'id',
        title: 'label',
      },
    };
    return merge(defaultOptions, props);
  }, [props]);

  const treeKeys = useMemo(() => {
    return {
      oneLevelKeys:
        currentTreeData?.map((item) => item[options.fieldNames?.key || ''] as React.Key) || [],
      allKeys: getDeepKeys(currentTreeData, options.fieldNames?.key || 'id'),
    };
  }, [currentTreeData, options]);

  const onOpenChange = useCallback(
    (e: CheckboxChangeEvent) => {
      if (e?.target?.checked) {
        setExpandedKeys(treeKeys.oneLevelKeys);
      } else {
        setExpandedKeys([]);
      }
    },
    [treeKeys.oneLevelKeys],
  );

  const onAllSelectChange = useCallback(
    (e: CheckboxChangeEvent) => {
      if (e?.target?.checked) {
        onChange?.({
          checkedKeys: treeKeys.allKeys,
        });
      } else {
        onChange?.({ checkedKeys: [], halfCheckedKeys: [] });
      }
    },
    [treeKeys.allKeys],
  );

  const onExpand = useCallback((keys: React.Key[]) => {
    setExpandedKeys(keys);
  }, []);

  const onCheck = useCallback<Required<Pick<TreeProps, 'onCheck'>>['onCheck']>(
    (checkedKeys, { halfCheckedKeys }) => {
      if (Array.isArray(checkedKeys)) {
        onChange?.({
          checkedKeys,
          halfCheckedKeys,
        });
      } else {
        onChange?.({
          checkedKeys: checkedKeys?.checked,
          halfCheckedKeys: checkedKeys?.halfChecked,
        });
      }
    },
    [],
  );

  return (
    <>
      <Space>
        <Checkbox onChange={onOpenChange}>
          {formatMessage({ id: 'system.Notice.expandFold', defaultMessage: '展开/折叠' })}
        </Checkbox>
        <Checkbox onChange={onAllSelectChange}>
          {formatMessage({ id: 'system.Notice.selectAllNnone', defaultMessage: '全选/全不选' })}
        </Checkbox>
      </Space>
      <div className={'ant-input mt4 ' + styles.tree}>
        <Tree
          checkedKeys={value?.checkedKeys}
          checkable={true}
          defaultExpandAll={false}
          multiple={true}
          expandedKeys={expandedKeys}
          onExpand={onExpand}
          onCheck={onCheck}
          treeData={currentTreeData}
          {...options}
        />
      </div>
    </>
  );
};

export default TreeSelect;
