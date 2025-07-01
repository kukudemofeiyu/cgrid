/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-29 10:36:01
 * @LastEditTime: 2024-02-28 10:00:12
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\hooks\useSiteColumn.ts
 */
import { useState, useMemo, useEffect, useCallback } from 'react';
import type { SiteDataType } from '@/services/station';
import { getSitesList } from '@/services/station';
import type { OptionType } from '@/types';
import { debounce } from 'lodash';
import type { ProColumns } from '@ant-design/pro-components';
import { formatMessage } from '@/utils';
import type { ResponsePromise } from '@/utils/request';

const useSiteColumn = <TableData = Record<string, any>, ValueType = 'text'>(
  props: Omit<ProColumns<TableData, ValueType>, 'request'> & {
    showAllOption?: boolean;
    searchRequest?: (params?: any) => ResponsePromise<any, any>;
  } = {},
): [siteColumn: ProColumns<TableData, ValueType>, siteOptions: SiteDataType[] | undefined] => {
  const [stationOptions, setStationOptions] = useState<OptionType[]>();
  const [siteOptions, setSiteOptions] = useState<SiteDataType[]>();

  const requestStation = useCallback(
    debounce((searchText, fun) => {
      return (props?.searchRequest || getSitesList)({
        ...(props?.params ?? {}),
        name: searchText,
      }).then(({ data }) => {
        let result =
          data?.map?.((item: any) => {
            return {
              label: item.name,
              value: item.id,
              ...item,
            };
          }) || [];
        if (props?.showAllOption) {
          result = [
            {
              label: formatMessage({ id: 'common.all', defaultMessage: '全部' }),
              value: '',
            },
          ].concat(result);
        }
        setStationOptions(result);
        fun?.(result);
        return result;
      });
    }, 700),
    [props?.params, props?.searchRequest],
  );

  useEffect(() => {
    requestStation('', setSiteOptions);
  }, [props?.params]);

  const siteColumn = useMemo<ProColumns<TableData, ValueType>>(() => {
    return {
      title: formatMessage({ id: 'common.site.siteName', defaultMessage: '站点名称' }),
      dataIndex: 'siteName',
      valueType: 'select',
      width: 150,
      ellipsis: true,
      ...(props || {}),
      formItemProps: (form, config) => {
        const defaultConfig = {
          name: 'siteId',
        };
        if (typeof props?.formItemProps === 'function') {
          const result = props.formItemProps(form, config);
          return { ...defaultConfig, ...(result || {}) };
        } else {
          return {
            ...defaultConfig,
            ...(props?.formItemProps || {}),
          };
        }
      },
      fieldProps: (form, config) => {
        const defaultConfig = {
          showSearch: true,
          filterOption: false,
          onSearch: requestStation,
          options: stationOptions,
          placeholder: formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }),
        };
        if (typeof props?.fieldProps === 'function') {
          const result = props.fieldProps(form, config);
          return { ...defaultConfig, ...(result || {}) };
        } else {
          return {
            ...defaultConfig,
            ...(props?.fieldProps || {}),
          };
        }
      },
    };
  }, [requestStation, stationOptions, props]);

  return [siteColumn, siteOptions];
};

export default useSiteColumn;
