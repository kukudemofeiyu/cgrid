/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-23 16:33:24
 * @LastEditTime: 2024-10-09 10:34:19
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\TableSelect\TableSelect\TableModal.tsx
 */
import React, { useCallback, useState, useEffect, useMemo } from 'react';
import { Modal, Tag } from 'antd';
import type { TableProps } from 'antd';
import type { TableRowSelection } from 'antd/es/table/interface';
import { ProTable } from '@ant-design/pro-components';
import { defaultsDeep, mergeWith } from 'lodash';
import styles from '../index.less';
import { cloneDeep } from 'lodash';
import { ProTableProps } from '@ant-design/pro-components';
import { formatMessage } from '@/utils';

export type TableModalProps<V, T, U> = {
  title?: string;
  open?: boolean;
  onFocus?: () => void | Promise<any>;
  onCancel?: () => void;
  width?: string;
  proTableProps?: ProTableProps<T, U>;
  multiple?: boolean;
  valueId?: string;
  valueName?: string;
  tableId?: string;
  tableName?: string;
  value?: V[];
  onChange?: (value: V[]) => void;
  address?: string;
  longitude?: string;
  latitude?: string;
};

const TableModal = <
  ValueType extends Record<string, any>,
  DataType extends Record<string, any>,
  Params extends Record<string, any>,
>(
  props: TableModalProps<ValueType, DataType, Params>,
) => {
  const {
    title = formatMessage({ id: 'taskManage.selectData', defaultMessage: '选择数据' }),
    open,
    onCancel,
    width = '1000px',
    multiple = true,
    proTableProps,
    valueId = 'id',
    valueName = 'name',
    tableId = 'id',
    tableName = 'name',
    value = [],
    onChange,
    address,
    longitude,
    latitude,
  } = props;

  const [selectedTags, setSelectedTags] = useState<ValueType[]>([]);
  const [tableIdSet, setTableIdSet] = useState<Set<string>>();

  const onSelectedChange: TableRowSelection<DataType>['onChange'] = useCallback(
    (selectedRowKeys, selectedRows: DataType[]) => {
      setSelectedTags((prevData) => {
        if (multiple) {
          const map = prevData.reduce((result, item) => {
            result.set(item[valueId], item);
            return result;
          }, new Map());
          tableIdSet?.forEach((item) => {
            map.delete(item);
          });
          selectedRows.forEach((item) => {
            map.set(item[tableId], {
              [valueId]: item[tableId],
              [valueName]: item[tableName],
            });
          });
          return [...map.values()];
        } else {
          return [
            {
              [valueId]: selectedRows?.[0]?.[tableId],
              [valueName]: selectedRows?.[0]?.[tableName],
              //@ts-ignore
              [latitude]: selectedRows?.[0]?.[latitude],
              //@ts-ignore
              [address]: selectedRows?.[0]?.[address],
              //@ts-ignore
              [longitude]: selectedRows?.[0]?.[longitude],
            },
          ];
        }
      });
    },
    [tableIdSet, valueId, tableId, valueName, tableName, multiple],
  );

  const onClose = useCallback(
    (index) => {
      const result = cloneDeep(selectedTags || []);
      result.splice(index, 1);
      setSelectedTags(result);
    },
    [selectedTags],
  );

  const onOk = () => {
    onChange?.(selectedTags);
    onCancel?.();
  };

  const requestTable = useCallback(
    (params, sort, filter) => {
      if (proTableProps?.request) {
        return proTableProps.request(params, sort, filter).then((res) => {
          setTableIdSet(new Set(res?.data?.map?.((item: any) => item[tableId])));
          return res;
        });
      } else {
        return Promise.resolve({});
      }
    },
    [proTableProps?.request, proTableProps?.pagination, tableId],
  );

  useEffect(() => {
    if (open) {
      setSelectedTags(value);
    }
  }, [open]);

  const tableAlertRender: ProTableProps<DataType, Params>['tableAlertRender'] = useCallback(
    ({ selectedRowKeys, selectedRows, onCleanSelected }) => {
      const tags = selectedTags?.map?.((item, index) => {
        return (
          <Tag className="mb4" key={item[valueId]} closable onClose={() => onClose(index)}>
            <div className={styles.tag} title={item[valueName]}>
              {item[valueName]}
            </div>
          </Tag>
        );
      });
      return (
        <>
          <div className="flex mb8">
            <span className="flex1">
              <span className="cl-primary mx8">{selectedTags?.length || 0}</span>
              {formatMessage({ id: 'taskManage.1006', defaultMessage: '项已选择' })}
            </span>
            <a onClick={onCleanSelected}>
              {formatMessage({ id: 'common.clear', defaultMessage: '清空' })}
            </a>
          </div>
          <div>
            <div className={`flex1 ${styles.tagContain}`}>{tags}</div>
          </div>
        </>
      );
    },
    [selectedTags],
  );

  const defaultTableProps = useMemo<ProTableProps<DataType, Params>>(() => {
    return {
      rowSelection: {
        type: multiple ? 'checkbox' : 'radio',
        alwaysShowAlert: true,
        selectedRowKeys: selectedTags?.map?.((item) => item[valueId]),
        onChange: onSelectedChange,
      },
      search: {
        labelWidth: 'auto',
        searchText: formatMessage({ id: 'common.search', defaultMessage: '搜索' }),
      },
      rowKey: tableId,
      pagination: {
        defaultPageSize: 10,
        showSizeChanger: true,
      },
      toolBarRender: false,
      tableAlertRender,
      tableAlertOptionRender: false,
      size: 'small',
    };
  }, [multiple, selectedTags, valueId, onSelectedChange, tableId, tableAlertRender]);

  const tableProps = useMemo<ProTableProps<DataType, Params>>(() => {
    return mergeWith(defaultTableProps, proTableProps, (objValue, srcValue) => {
      if (srcValue === false) {
        return false;
      }
    });
  }, [defaultTableProps, proTableProps]);

  return (
    <>
      <Modal
        title={title}
        open={open}
        width={width}
        onCancel={onCancel}
        onOk={onOk}
        maskClosable={false}
        destroyOnClose
        centered
      >
        <ProTable
          className={styles.proTable}
          {...tableProps}
          request={requestTable}
          scroll={{
            y: 382,
          }}
        />
      </Modal>
    </>
  );
};

export default TableModal;
