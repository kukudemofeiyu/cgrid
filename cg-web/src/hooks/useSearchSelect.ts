/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-30 17:15:32
 * @LastEditTime: 2023-06-30 17:15:32
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\hooks\useSearchSelect.ts
 */

import { useState, useMemo, useEffect, useCallback } from 'react';
import type { OptionType } from '@/types';
import { debounce } from 'lodash';
import type { ProColumns } from '@ant-design/pro-components';

export type SearchParams = {
  name?: string;
};

export type UseSearchSelectProps<TableData, ValueType> = {
  proColumns: Omit<
    ProColumns<TableData, ValueType>,
    'valueType' | 'fieldProps' | 'title' | 'dataIndex' | 'formItemProps'
  > &
    Required<Pick<ProColumns<TableData, ValueType>, 'title' | 'dataIndex' | 'formItemProps'>>;
  request: (params: SearchParams) => Promise<OptionType[]>;
};

const useSearchSelect = <TableData = Record<string, any>, ValueType = 'text'>(
  props: UseSearchSelectProps<TableData, ValueType>,
): [selectColumn: ProColumns<TableData, ValueType>, options: OptionType[] | undefined] => {
  const { proColumns, request } = props;

  const [options, setOptions] = useState<OptionType[]>();

  const requestStation = useCallback(
    debounce((searchText) => {
      request({ name: searchText }).then((data = []) => {
        setOptions(data);
      });
    }, 700),
    [request],
  );

  useEffect(() => {
    requestStation('');
  }, []);

  const selectColumn: ProColumns<TableData, ValueType> = useMemo(() => {
    return {
      valueType: 'select',
      fieldProps: {
        showSearch: true,
        filterOption: false,
        onSearch: requestStation,
        options: options,
      },
      width: 150,
      ellipsis: true,
      ...(proColumns || {}),
    };
  }, [options, proColumns, requestStation]);

  return [selectColumn, options];
};

export default useSearchSelect;
