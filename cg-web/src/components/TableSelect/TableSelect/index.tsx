/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-23 09:51:58
 * @LastEditTime: 2023-09-21 14:55:48
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\TableSelect\TableSelect\index.tsx
 */
import React, { useState, useMemo, useCallback, CSSProperties } from 'react';
import { Tag } from 'antd';
import { CloseCircleFilled } from '@ant-design/icons';
import { cloneDeep } from 'lodash';
import styles from '../index.less';
import TableModal from './TableModal';
import type { ProRenderFieldPropsType, ProTableProps } from '@ant-design/pro-components';
import classnames from 'classnames';
import { formatMessage } from '@/utils';

export type TableSelectProps<V, T, P> = {
  value?: V[];
  onChange?: (value: V[]) => void;
  onFocus?: () => void | Promise<any>;
  title?: string;
  width?: string;
  multiple?: boolean;
  limit?: number;
  disabled?: boolean;
  valueId?: string;
  valueName?: string;
  tableId?: string;
  tableName?: string;
  clearable?: boolean;
  proTableProps?: ProTableProps<T, P>;
  placeholder?: string;
  inputClassName?: string;
  inputStyle?: CSSProperties;
};

const TableSelect = <
  ValueType extends Record<string, any>,
  DataType extends Record<string, any>,
  Params extends Record<string, any>,
>(
  props: TableSelectProps<ValueType, DataType, Params>,
) => {
  const {
    value: rawValue = [],
    onChange,
    onFocus,
    multiple = true,
    limit = 3,
    disabled = false,
    valueId = 'id',
    valueName = 'name',
    tableId = 'id',
    tableName = 'name',
    clearable = true,
    proTableProps,
    title,
    width,
    placeholder = formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }),
    inputClassName = '',
    inputStyle = {},
  } = props;

  const [open, setOpen] = useState(false);
  const value = !Array.isArray(rawValue) ? [rawValue] : rawValue;
  const valueLength = value?.length || 0;

  const onClose = useCallback((e: React.MouseEvent, index) => {
    e.stopPropagation();
    const result = cloneDeep(value || []);
    result.splice(index, 1);
    onChange?.(result);
  }, []);

  const onClearClick = useCallback((e: React.MouseEvent) => {
    e.stopPropagation();
    onChange?.([]);
  }, []);

  const onSwitchOpen = useCallback(() => {
    if (onFocus) {
      const result = onFocus();
      if (result) {
        result?.then?.(() => {
          setOpen(!open);
        });
      } else {
        setOpen(!open);
      }
    } else {
      setOpen(!open);
    }
  }, [open]);

  const tags = useMemo(() => {
    const result: React.ReactNode[] = [];
    if (value && valueLength) {
      for (let i = 0; i < valueLength; i++) {
        if (i < limit) {
          result.push(
            <Tag key={value[i][valueId]} closable={!disabled} onClose={(e) => onClose(e, i)}>
              <div className={styles.tag} title={value[i][valueName]}>
                {value[i][valueName]}
              </div>
            </Tag>,
          );
        } else {
          break;
        }
      }
    }
    return result;
  }, [value]);

  return (
    <>
      <div
        className={classnames(['ant-input-affix-wrapper', styles.input, inputClassName])}
        style={inputStyle}
        onClick={onSwitchOpen}
      >
        {tags.length ? tags : <span className={styles.placeholder}>{placeholder}</span>}
        {valueLength > limit ? '+' + (valueLength - limit) : ''}
        {clearable && (
          <CloseCircleFilled
            className={styles.clearable}
            style={{ display: valueLength ? 'block' : 'none' }}
            onClick={onClearClick}
          />
        )}
      </div>
      <TableModal<ValueType, DataType, Params>
        open={open}
        proTableProps={proTableProps}
        multiple={multiple}
        title={title}
        width={width}
        valueId={valueId}
        valueName={valueName}
        tableId={tableId}
        tableName={tableName}
        value={value || []}
        onChange={onChange}
        onCancel={onSwitchOpen}
      />
    </>
  );
};

const valueRender: ProRenderFieldPropsType['render'] = (value, props, dom) => {
  return (
    <>
      {[value]
        .flat(1)
        .map((item) => item?.name)
        .join(',')}
    </>
  );
};

const formRender: ProRenderFieldPropsType['renderFormItem'] = (value, props, dom) => {
  return (
    <>
      <TableSelect {...props.fieldProps} />
    </>
  );
};

export const TABLESELECT = 'tableselect';

export type TABLESELECTVALUETYPE = typeof TABLESELECT;

export const tableSelectValueTypeRender: ProRenderFieldPropsType = {
  render: valueRender,
  renderFormItem: formRender,
};

export const tableSelectValueTypeMap = {
  [TABLESELECT]: tableSelectValueTypeRender,
};

export default TableSelect;
