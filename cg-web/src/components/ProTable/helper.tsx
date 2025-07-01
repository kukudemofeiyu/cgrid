import { get as requestGet, post as requestPost } from '@/utils/request';
import type { ProColumns, ProTableProps } from '@ant-design/pro-components';
import { get, isEmpty } from 'lodash';
import type { EmitType, YTProColumns, YTProTableCustomProps } from './typing';
import { dateFormatMap } from '@/utils/dictionary';
import moment from 'moment';
import { getLocale, LocaleEnum, rtlLocale } from '@/utils';

const dateTypes = ['dateMonth', 'date', 'dateTime'];
const dateRangeTypes = ['dateMonthRange', 'dateRange', 'dateTimeRange'];

const locale = getLocale();
const isRtl: boolean = rtlLocale.includes(locale.locale);

const typeFormatMap: Record<string, string> = {
  dateMonth: locale.monthYearFormat,
  date: locale.dateFormat,
  dateTime: locale.dateTimeNoSecondFormat,
};

export const formatColumns = <D, V>(columns: ProColumns[]) => {
  if (!Array.isArray(columns) || !columns.length) {
    return [];
  }

  const result = columns.map((col) => {
    if (col?.valueType && typeof col.valueType == 'string') {
      if (dateTypes.includes(col.valueType) || dateRangeTypes.includes(col.valueType)) {
        const type = col.valueType.replace('Range', '');
        const fieldProps: any = col.fieldProps;
        const className: string = fieldProps?.className || '';
        if (fieldProps) {
          if (typeof fieldProps == 'function') {
            col.fieldProps = (...params: any[]) => {
              return {
                format: typeFormatMap[type],
                ...fieldProps?.(...params),
              };
            };
          } else if (typeof fieldProps == 'object') {
            col.fieldProps = {
              format: typeFormatMap[type],
              ...fieldProps,
            };
          }
        } else {
          col.fieldProps = {
            format: typeFormatMap[type],
          };
        }
        if (isRtl) {
          col.fieldProps = {
            className: className + ' ant-picker-rtl',
            ...col.fieldProps,
          };
        }
        if (col?.dataIndex && typeof col.dataIndex == 'string' && col.renderFormat) {
          const render = col.render;
          col.render = (...params) => {
            const record = params[1];
            const value = record[col.dataIndex as string];
            if (value) {
              try {
                record[col.dataIndex as string] = moment(value).format(col.renderFormat);
              } catch {
                record[col.dataIndex as string] = value;
              }
            }
            return render ? render?.(...params) : record[col.dataIndex as string];
          };
        }
      }
    }
    return col;
  });

  return result;
};

const buildColumns = (cols: YTProColumns<any, any>[]) => {
  cols.forEach((col) => {
    if (typeof col.title == 'string') {
      col.title = <span title={col.title}>{col.title}</span>;
    }
    if (col.widthLocale) {
      col.width =
        col.widthLocale[getLocale().locale as LocaleEnum] ||
        col.widthLocale[LocaleEnum.default] ||
        col.width;
    }
    if (col.children && col.children.length) {
      buildColumns(col.children);
    }
  });
};

export const normalizeRequestOption = <D, V>(
  columns: YTProColumns<D & EmitType, V>[] | undefined,
  onEvent: YTProTableCustomProps<D, V>['onEvent'],
) => {
  if (!Array.isArray(columns) || !columns.length) {
    return [];
  }

  let result = columns.map((col) => {
    if (!isEmpty(col.requestOption) && !col.request) {
      const {
        url,
        mapKey = { label: '', value: '' },
        dataIndex = '',
        methods = 'get',
      } = col.requestOption;
      const request = methods === 'get' ? requestGet : requestPost;
      col.request = async (params) => {
        try {
          const { data } = await request(url, params);
          let rawData: Record<string, string>[] = [];
          if (dataIndex) {
            rawData = get(data, dataIndex);
          }

          if (isEmpty(rawData)) {
            throw new Error('path解析失败 或 返回数据为空');
          }

          if (!Array.isArray(rawData)) {
            rawData = [rawData];
          }

          return rawData.map((item) => {
            return {
              label: item[mapKey.label],
              value: item[mapKey.value],
            };
          });
        } catch (error) {
          console.log('[YTProTable]: 请求数据解析异常 ', error);
          return [];
        }
      };
    }
    if (col.renderWithEmit) {
      col.render = (...params) => {
        params[1].emit = (...emitParams) => {
          onEvent?.(...emitParams);
        };
        return col?.renderWithEmit?.(...params);
      };
    }
    return col;
  });

  buildColumns(result);

  result = formatColumns(result as ProColumns[]) as any;
  return result;
};

export const standardRequestTableData = <D, P>(
  request?: YTProTableCustomProps<D, P>['request'],
  expandable?: ProTableProps<D, P>['expandable'],
  getDragList?: (params: any) => void,
) => {
  if (!request) {
    return;
  }
  const childrenKey = expandable?.childrenColumnName || 'children';

  const simpleRequest: ProTableProps<D, P>['request'] = async (...props) => {
    if (props[1]) {
      const sortParams = props[1] || {};
      const searchParams: any = { ...props[0], sortMode: {} };
      Object.entries(sortParams)?.forEach?.(([key, value]) => {
        searchParams.sortMode[key] = value == 'ascend' ? 0 : 1;
      });
      props[0] = searchParams;
    }
    const { data } = await request(...props);
    data?.list?.forEach?.((item: any) => {
      if (
        item?.[childrenKey] &&
        Array.isArray(item?.[childrenKey]) &&
        !item?.[childrenKey]?.length
      ) {
        item[childrenKey] = null;
      }
    });
    const result = {
      data: data?.list || [],
      total: data?.total,
    };
    getDragList?.(data);
    return result;
  };
  return simpleRequest;
};

export const calculateColumns = <D, P>(
  columns: YTProColumns<D, P>[],
  contain: React.MutableRefObject<HTMLDivElement | undefined>,
) => {
  // const columns = merge([],);
  const tableWidth =
    contain?.current?.querySelector?.('.ant-table')?.getBoundingClientRect()?.width || 1640;
  let totalColumnWidth = 0,
    lastNotFixedColumnIndex = 0,
    hasEmptyWidth = false;

  columns?.forEach?.((item, index) => {
    if (!item.hideInTable) {
      if (item.width) {
        item.width = parseInt(item.width as string);
        if (isNaN(item.width)) {
          hasEmptyWidth = true;
        } else {
          totalColumnWidth += item.width;
        }
      } else {
        hasEmptyWidth = true;
      }
      if (!item.fixed) {
        lastNotFixedColumnIndex = index;
      }
    }
  });

  if (!hasEmptyWidth) {
    if (totalColumnWidth < tableWidth) {
      columns?.forEach?.((item, index) => {
        if (!item.hideInTable) {
          if (item.width) {
            if (!['index', 'checkbox', 'radio'].includes(item.valueType as string)) {
              item.width = ((item.width as number) * tableWidth) / totalColumnWidth;
            }
          }
        }
      });
    }
    delete columns[lastNotFixedColumnIndex].width;
    // 因为拖动必须有一列为自适应列，所以默认最后一个非固定列为不可拖动自适应列
    // 如果最后一列出现了换行情况，可以找一个字段长度很短且不需要拖动查看的列，把这个列的宽度删除即可
  }
};

export const formatData = (data: Record<string, any>, columns?: ProColumns<any, any>[]) => {
  columns?.forEach?.((col) => {
    if (col?.valueType && col.dataIndex && typeof col.dataIndex == 'string') {
      if (dateTypes.includes(col.valueType)) {
        const format = dateFormatMap[col.valueType];
        let value = data?.[col.dataIndex];
        if (format && value) {
          if (col.valueType == 'dateMonth' && !getLocale().isZhCN) {
            value = value.split('/').reverse().join('-');
          }
          data[col.dataIndex] = moment(value).format(format);
        }
      } else if (dateRangeTypes.includes(col.valueType)) {
        const type = col.valueType;
        const format = dateFormatMap[type.replace('Range', '')];

        if (format && data?.[col.dataIndex]) {
          const valueArr = data?.[col.dataIndex]?.map?.((value: any) => {
            let result = value;
            if (result && typeof result === 'string') {
              if (col.valueType == 'dateMonthRange' && !getLocale().isZhCN) {
                result = result.split('/').reverse().join('-');
              }
              result = moment(result).format(format);
            }
            return result;
          });
          data[col.dataIndex] = valueArr;
        }

        if (
          ((typeof col?.search !== 'boolean' && col?.search?.transform) ||
            (col as any)?.transform) &&
          format
        ) {
          let result: any =
            (col as any)?.search?.transform?.([], '', []) || (col as any)?.transform?.([], '', []);
          if (typeof result == 'object') {
            const valueArr: string[] = [];
            Object.keys(result).forEach((key) => {
              let value = data?.[key];
              if (value) {
                if (col.valueType == 'dateMonthRange' && !getLocale().isZhCN) {
                  value = value.split('/').reverse().join('-');
                }
                valueArr.push(moment(value).format(format));
              }
            });
            result =
              (col as any)?.search?.transform?.(valueArr, '', []) ||
              (col as any)?.transform?.(valueArr, '', []);
            Object.keys(result).forEach((key) => {
              const value = result?.[key];
              if (value) {
                data[key] = value;
              }
            });
          }
        }
      }
    }
  });
  return data;
};
