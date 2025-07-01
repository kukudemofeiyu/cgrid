import React from 'react';
import type { MenuDataItem } from '@umijs/route-utils';
import { formatMessage as umiFormatMessage, localeInfo, getLocale as umiGetLocale } from 'umi';
import { createIcon } from './IconUtil';
import FileSaver from 'file-saver';
import * as XLSX from 'xlsx';
import type { DeviceModelType, DevicePropsType } from '@/types/device';
import routers, { getPathLocaleMap } from '../../config/routes';
import moment from 'moment';
import type { Moment } from 'moment';
import defaultSettings from '../../config/defaultSettings';
import { parse } from 'querystring';
import { cloneDeep } from 'lodash';
import { getAccessToken } from '@/access';
import type { AllLargeScreenConfigType } from '@/services/station';
import { message } from 'antd';
import AreaConfig from '@/data/areaConfig';

export const configureInfo = {
  port: '3001',
  editPath: '/#/chart/home/',
  previewPath: '/#/chart/preview/',
};

export enum DeviceModelShowTypeEnum {
  // 1-平铺 2-服务名称隐藏 3-宫格 4-展示为radioButton 5-展示为select 6-展示为switch 7-展示为button 8-线 9-表单元素
  Tile = 1,
  HideName,
  Grid,
  RadioButton,
  Select,
  Switch,
  Button,
  Line,
  Form,
}

export enum DeviceModelDescribeTypeEnum {
  Page = 'page',
  Group = 'group',
  Tab = 'tab',
  TabItem = 'tabItem',
  Service = 'service',
  Component = 'component',
  Property = 'property',
  PropertyGroup = 'propertyGroup',
}

export enum DeviceModelTypeEnum {
  Int = 'int',
  Long = 'long',
  Double = 'double',
  String = 'string',
  Boolean = 'boolean',
  Enum = 'enum',
  Struct = 'struct',
  Array = 'array',
  TimeRange = 'timeRange',
  TimeStamp = 'timestamp',
  Button = 'button',
  TreeSelect = 'treeSelect',
}

export enum LocaleEnum {
  zh = 'zh-CN',
  en = 'en-US',
  fa = 'fa-IR',
  es = 'es-ES',
  default = 'default',
}

export const rtlLocale = ['fa-IR'];

export type AntMenuProps = {
  label: string;
  key: string;
  icon?: React.ReactNode;
  children?: AntMenuProps[];
};

export type ValueUnitType = {
  value: string | number;
  unit: string;
};

/*
 *@Author: aoshilin
 *@Date: 2025-02-19 14:48:43
 *@parms:
 *@Description: 如果没有翻译，默认使用英文替代
 */
type DescriptorType = {
  id: string;
  defaultMessage: string;
};
export const formatMessage = (descriptor: DescriptorType, values?: any) => {
  return descriptor.defaultMessage;
};

const routePathLocaleMap = getPathLocaleMap(routers);

export const getLocaleMenus = (data: MenuDataItem[], parentPath = ''): MenuDataItem[] => {
  data?.forEach((item) => {
    const path = item?.path?.startsWith?.('/') ? item?.path : parentPath + '/' + item?.path;
    const locale = routePathLocaleMap[path || ''];
    item.name = locale ? formatMessage({ id: locale, defaultMessage: item?.name }) : item?.name;
    if (item?.children && item?.children?.length) {
      getLocaleMenus(item.children, path);
    }
  });
  return data;
};

export const getMenus = (data: MenuDataItem[], prePath = ''): AntMenuProps[] => {
  const arr: AntMenuProps[] = [];
  data.forEach((item) => {
    const path = item?.path?.split('')[0] === '/' ? item.path : `${prePath}/${item.path}`;
    if (!item.hideInMenu) {
      arr.push({
        label: item?.name || '',
        key: path,
        icon: createIcon(item?.meta?.icon, { style: { fontSize: '20px' } }),
        ...(item.children ? { children: getMenus(item.children, path) } : {}),
      });
    }
  });
  return arr;
};

export const getPathTitleMap = (data?: AntMenuProps[]): Map<string, string> => {
  const map = new Map<string, string>();
  data?.forEach?.((item) => {
    map.set(item?.key || '', item.label);
    if (item.children && item.children.length) {
      const result = getPathTitleMap(item.children);
      result.forEach((value, key) => {
        map.set(key, value);
      });
    }
  });
  return map;
};

export const arrayToMap = (array: any[], key = 'value', value = 'label') => {
  const map: Record<string, any> = {};
  array?.forEach?.((item) => {
    map['' + item[key]] = item[value];
  });
  return map;
};

export const isEmpty = (value: any) => {
  return value === null || value === undefined || value === '';
};

export const getValue = (value: any, unit = '') => {
  return isEmpty(value) ? '' : value + unit;
};

export const getUnit = (unit = '') => {
  return unit ? `(${unit})` : '';
};

export const getLocale = () => {
  const locale = localStorage.getItem('umi_locale') || '';

  const result = {
    isZh: true,
    isZhCN: true,
    isZhTW: false,
    isEn: false,
    isEnUS: false,
    isJaJP: false,
    isEs: false,
    dateFormat: 'YYYY-MM-DD',
    dateTimeFormat: 'YYYY-MM-DD HH:mm:ss',
    dateTimeNoSecondFormat: 'YYYY-MM-DD HH:mm',
    monthDateFormat: 'MM-DD',
    monthYearFormat: 'YYYY-MM',
    yearFormat: 'YYYY',
    hourMinuteFormat: 'HH:mm',
  };
  switch (locale) {
    case LocaleEnum.zh:
      result.isZh = true;
      result.isZhCN = true;
      result.dateFormat = 'YYYY-MM-DD';
      result.dateTimeFormat = 'YYYY-MM-DD HH:mm:ss';
      result.dateTimeNoSecondFormat = 'YYYY-MM-DD HH:mm';
      result.monthDateFormat = 'MM-DD';
      result.monthYearFormat = 'YYYY-MM';
      result.yearFormat = 'YYYY';
      result.hourMinuteFormat = 'HH:mm';
      break;
    case LocaleEnum.en:
      result.isZh = false;
      result.isZhCN = false;
      result.isEn = true;
      result.isEnUS = true;
      result.dateFormat = 'MM/DD/YYYY';
      result.dateTimeFormat = 'MM/DD/YYYY HH:mm:ss';
      result.dateTimeNoSecondFormat = 'MM/DD/YYYY HH:mm';
      result.monthDateFormat = 'MM/DD';
      result.monthYearFormat = 'MM/YYYY';
      result.yearFormat = 'YYYY';
      result.hourMinuteFormat = 'HH:mm';
      break;
    case LocaleEnum.fa:
      result.isZh = false;
      result.isZhCN = false;
    case LocaleEnum.es:
      result.isZh = false;
      result.isZhCN = false;
      result.isEs = true;
    default:
  }
  return {
    moment: (stringTime: string, dateFormat: string = result.dateTimeFormat) =>
      stringTime ? moment(stringTime).format(dateFormat) : '-',
    locale,
    ...result,
  };
};

export type valueType = {
  value?: string;
  type?: string;
};

export const colorMap = {
  success: 'cl-success',
  error: 'cl-error',
};

export const valueFormat = (value: valueType) => {
  const dom = React.createElement(
    'span',
    {
      className: colorMap[value?.type || ''] || '',
    },
    value?.value,
  );
  return dom;
};

export const getAreaCodeByAdCode = (code: string): string[] => {
  const result = code + '';
  if (result == '900000') {
    return ['900000', '', ''];
  } else if (result) {
    const province = result.substring(0, 2).padEnd(6, '0');
    const city = result.substring(0, 4).padEnd(6, '0');
    return ['100000', province, city];
  } else {
    return ['', '', ''];
  }
};

export const getPlaceholder = (value: any, placeholder = '--') => {
  return isEmpty(value) ? placeholder : value;
};

/**
 * @param path string;
 * @returns path[];
 * @example '/a/b/c'=>['/a','/a/b','/a/b/c']
 */
export const getPathArrary = (path: string): string[] => {
  const result: string[] = [];
  if (path) {
    (path + '').split('/').forEach((item, index) => {
      if (index) {
        result.push([result[result.length - 1], item].join('/'));
      } else {
        result.push(item);
      }
    });
  }
  result.shift();
  return result;
};

export const saveFile = (data: Blob | string, name = '导出文件', ext = '.xlsx') => {
  // let blob = new Blob([data], { type: fileType.Xlxs })
  FileSaver.saveAs(data, name + ext);
};
/*
 *@Author: aoshilin
 *@Date: 2024-09-10 08:54:13
 *@parms: data 数据源 table数据格式
 *@parms: columns 表格配置
 *@Description: 将数据处理成excel表格数据
 */
const getExcelData = (data: any[], columns: any[]) => {
  let curColumns: any[] = [];
  columns.forEach((column) => {
    if (column.hideInTable) return;
    if (typeof column.title !== 'string') {
      column.title = column.exportTitle;
    }
    if (column?.children?.length) {
      column.children = column?.children.map((childColumn: any) => {
        if (childColumn.hideInTable) return;
        if (typeof childColumn.title !== 'string') {
          childColumn.title = childColumn.exportTitle;
        }
        childColumn.title = `${column.title}_${childColumn.title}`;
        return childColumn;
      });
      curColumns = curColumns.concat(column?.children);
    } else {
      curColumns.push(column);
    }
  });
  return data.map((item) => {
    const obj: any = {};
    curColumns.forEach((i) => {
      obj[i.title] = item[i.dataIndex];
    });
    return obj;
  });
};
/*
 *@Author: aoshilin
 *@Date: 2024-08-16 17:01:17
 *@parms: data 数据源 table数据格式
 *@parms: columns 表格配置
 *@parms: fileName 下载文件名称，判断设置了文件名，就执行下载
 *@Description: 前端将数据转换为excel文件格式
 */
export const tabelDataToExcel = (data: any[], columns: any, fileName?: string) => {
  try {
    const excelData = getExcelData(data, cloneDeep(columns));
    const worksheet = XLSX.utils.json_to_sheet(excelData);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Sheet1');
    const excelBuffer = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
    const fileData = new Blob([excelBuffer], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8',
    });
    if (fileName) {
      saveFile(fileData, fileName);
    }
    return Promise.resolve(fileData);
  } catch (error) {
    return Promise.reject(error);
  }
};

export const strToArray = (value: number | string): number[] => {
  let result: number[] = [];
  try {
    result = JSON.parse(value + '');
    if (!Array.isArray(result)) {
      result = [result];
    }
  } catch (e) {
    result = [value];
  }
  return result;
};

export const parseToArray = (value: string) => {
  if (Array.isArray(value)) {
    return value;
  }
  let result = [];
  try {
    result = JSON.parse(value + '');
    if (!Array.isArray(result)) {
      result = [];
    }
  } catch (e) {
    result = [];
  }
  return result;
};

export const parseToObj = (value: any): Record<string, any> => {
  let result = {};
  if (typeof value === 'object') {
    if (Array.isArray(value)) {
      return {};
    }
    return value;
  }
  try {
    result = JSON.parse(value + '');
    if (typeof result !== 'object' || Array.isArray(result)) {
      result = {};
    }
  } catch (e) {
    result = {};
  }
  return result;
};

export const formatModelValue = (
  value: any,
  model: DeviceModelType,
  showUnit = true,
  options?: {
    showEnumValue?: boolean;
    placeholder?: string;
  },
): string => {
  if (isEmpty(value)) {
    return getPlaceholder('', options?.placeholder ?? '--');
  }
  let specs: Record<string, any> = {};
  if (typeof model?.specs !== 'object') {
    try {
      specs = JSON.parse(model?.specs);
      if (typeof specs !== 'object') {
        specs = {};
      }
    } catch {
      specs = {};
    }
  } else {
    specs = model?.specs;
  }
  let result = '';
  switch (model?.type) {
    case DeviceModelTypeEnum.Int:
    case DeviceModelTypeEnum.Long:
    case DeviceModelTypeEnum.Double:
      result = value + (showUnit ? specs?.unit ?? '' : '');
      break;
    case DeviceModelTypeEnum.Boolean:
    case DeviceModelTypeEnum.Enum:
      result = options?.showEnumValue ? `${value}(${specs[value]})` : specs[value];
      break;
    case DeviceModelTypeEnum.Array:
      try {
        result =
          parseToArray(value)
            ?.map?.((item: string, index: number) => {
              let itemValue = '--';
              if (!isEmpty(item)) {
                itemValue = formatModelValue(item, specs?.item);
              }
              if (specs?.aliseRule) {
                return specs?.aliseRule?.replace('$array.{index+1}', index + 1) + '：' + itemValue;
              } else {
                return itemValue;
              }
            })
            .join('，') || '--';
      } catch {
        result = value;
      }
      break;
    case DeviceModelTypeEnum.Struct:
      try {
        const map = arrayToMap(specs, 'id', 'name');
        map[0] = '正常';
        result = map[value] ?? value;
      } catch {
        result = value;
      }
      break;
    case DeviceModelTypeEnum.TimeStamp:
      try {
        result = moment(value)?.format?.(getLocale().dateTimeFormat);
        result = result ?? value;
      } catch {
        result = value;
      }
      break;
    case DeviceModelTypeEnum.String:
    default:
      result = value;
      break;
  }
  result = result ?? value;
  if (typeof result == 'function' || typeof result == 'object' || typeof result == 'symbol') {
    result = JSON.stringify(result);
  }
  return result;
};

export const formatNum = (num: number, separator = '--', floatLength = 2): ValueUnitType => {
  if (isEmpty(num)) {
    return { value: separator, unit: '' };
  } else {
    let unit = '',
      value = num;
    if (-100000000 > num || num > 100000000) {
      unit = formatMessage({ id: 'common.tenMillion', defaultMessage: '千万' });
      value = num / 100000000;
    } else if (-10000 > num || num > 10000) {
      unit = formatMessage({ id: 'common.tenThousand', defaultMessage: '万' });
      value = num / 10000;
    }
    return {
      value: value?.toFixed?.(floatLength) || value,
      unit,
    };
  }
};

export const formatWattNum = (num: number, separator = '--', floatLength = 2): ValueUnitType => {
  if (isEmpty(num)) {
    return { value: separator, unit: '' };
  } else {
    let unit = '',
      value = num;
    if (-1000000000 > num || num > 1000000000) {
      unit = 'T';
      value = num / 1000000000;
    } else if (-1000000 > num || num > 1000000) {
      unit = 'G';
      value = num / 1000000;
    } else if (-1000 > num || num > 1000) {
      unit = 'M';
      value = num / 1000;
    } else {
      unit = 'k';
    }
    return {
      value: value?.toFixed?.(floatLength) || value,
      unit,
    };
  }
};

export const getBrowserLang = () => {
  const language = (navigator.language || navigator.browserLanguage).toLowerCase();
  if (language.indexOf('zh') != -1) {
    return 'zh-CN';
  } else if (language.indexOf('en') != -1) {
    return 'en-US';
  } else {
    return 'zh-CN';
  }
};

export const getModelByProps = (items: DevicePropsType[], parentField = '') => {
  let result: any = {};
  items?.forEach?.((item) => {
    const field = parentField ? parentField + '.' + item?.id : item?.id;
    if (item?.dataType?.type == DeviceModelTypeEnum.Struct) {
      result = { ...result, ...getModelByProps(parseToArray(item?.dataType?.specs), field) };
    } else {
      result[field || ''] = { ...item?.dataType, name: item.name };
    }
  });
  return result;
};

export const startExchangeTime = () => {
  if (!window.exchangeData) {
    window.exchangeData = {
      exchangeCount: 3599,
    };
    setInterval(() => {
      window.exchangeData = {
        exchangeCount: (window.exchangeData?.exchangeCount ?? 3599) + 1,
      };
    }, 1000 * 60 * 5);
  }
};

export const flatObj = (data: Record<string, any>, parentField = '') => {
  let result: Record<string, any> = {};
  if (typeof data !== 'object' || Array.isArray(data)) {
    result = {};
  } else {
    for (const key in data) {
      const field = parentField ? parentField + '.' + key : key;
      if (typeof data[key] === 'object' && !Array.isArray(data[key])) {
        result = { ...result, ...flatObj(data[key], field) };
      } else {
        result[field] = data[key];
      }
    }
  }
  return result;
};

export const getPropsFromTree = <T extends Record<string, any>, U = string>(
  data?: T[],
  key?: string | string[],
  children = 'children',
  interceptor = (params: T) => true,
): U[] => {
  const keys = key || 'id';
  const isArray = Array.isArray(keys);
  const result: U[] = [];
  data?.forEach?.((item) => {
    if (isArray) {
      if (!interceptor || interceptor(item)) {
        const obj: any = {};
        keys?.forEach?.((childKey) => {
          if (!isEmpty(item?.[childKey])) {
            obj[childKey] = item?.[childKey];
          }
        });
        result.push(obj);
      }
    } else {
      if (!isEmpty(item?.[keys]) && (!interceptor || interceptor(item))) {
        result.push(item?.[keys]);
      }
    }
    if (item?.[children] && item?.[children]?.length) {
      const childrenResult: U[] = getPropsFromTree(item?.[children], keys, children, interceptor);
      result.push(...childrenResult);
    }
  });
  return result;
};

export const initLocale = (userLocale?: string) => {
  const localLocale = localStorage.getItem('umi_locale');
  const queryLang = parse(window.location.search.replace('?', '')) as { lang: string };
  const areaConfig = AreaConfig.getInstance();
  const locale =
    userLocale ||
    queryLang.lang ||
    localLocale ||
    // areaConfig.locale ||
    getBrowserLang() ||
    defaultSettings?.locale ||
    'zh-CN';
  if (localLocale != locale) {
    localStorage.setItem('umi_locale', locale);
    if (queryLang.lang != locale && queryLang.lang) {
      window.location.href = window.location.href.replace(queryLang.lang, locale);
    } else {
      window.location.reload();
    }
  } else {
    if (rtlLocale.includes(locale)) {
      window.document.body.classList.add('body-rtl');
    }
  }
};

export const deleteEmptyKey = (data: Record<string, any>) => {
  try {
    Object.keys(data)?.forEach?.((key) => {
      if (isEmpty(data[key])) {
        delete data[key];
      }
    });
  } catch (err) {
    console.error('删除key', err);
  }
};

export const getUniqueNumber = (digit = 13) =>
  Math.random()
    .toString(36)
    .substring(2, 2 + digit);

export const getConfigureEditPage = (thirdId: string, siteId: string) => {
  const location = window.location;
  const herf = `${location.protocol}//${location.hostname}:${configureInfo.port}${configureInfo.editPath
    }${thirdId}?lang=${getLocale().locale}&siteId=${siteId}&token=Bearer ${getAccessToken()}`;
  return herf;
};

export const getConfigurePreviewPage = (thirdId: string, siteId: string) => {
  const location = window.location;
  const herf = `${location.protocol}//${location.hostname}:${configureInfo.port}${configureInfo.previewPath
    }${thirdId}?lang=${getLocale().locale}&siteId=${siteId}&token=Bearer ${getAccessToken()}`;
  return herf;
};

export const getUrlParams = () => {
  const urlSearchParams = new URLSearchParams(window.location.search);
  return Object.fromEntries(urlSearchParams.entries());
};

export function openScreen(
  screenConfig: AllLargeScreenConfigType | undefined,
  siteId: string | number | undefined,
) {
  let url: string = '';
  if (siteId && screenConfig) {
    const templates = screenConfig?.config?.templates || [];
    if (templates.length) {
      //默认大屏
      if (screenConfig?.active == 1) {
        // 默认大屏的默认标准架构图
        url = '/screen/demo-station?id=' + siteId;
        const isDefalue = templates.some((i) => i.thirdId == '-1');
        if (!isDefalue) {
          // 默认大屏的自定义标准架构图
          const curTemplate = templates.find((i) => i.energyFlowDiagramId == 1);
          url += `&thirdId=${curTemplate?.thirdId}`;
        }
        // 默认大屏的定制能流图
        const customScreen = templates.find((i) => i.energyFlowDiagramId == 2);
        if (customScreen) {
          url += `&geometry=${customScreen?.url}`;
        }
      } else {
        //自定义大屏
        const curTemplate = templates[0];
        if (curTemplate.url) {
          url = curTemplate.url + `?siteId=${siteId}`;
        } else {
          url = getConfigurePreviewPage(curTemplate.thirdId, String(siteId));
        }
      }
    }
  }
  if (url) {
    window.open(url);
  } else {
    message.success(formatMessage({ id: 'siteMonitor.selectSite', defaultMessage: '请选择站点' }));
  }
}

/*
 *@Author: aoshilin
 *@Date: 2025-01-16 11:38:38
 *@parms:
 *@Description: 获取从指定时间前几天数据
 */
export const getDateList = (appointDate: Moment, dateTimes: number, format: string) => {
  const dateList = [];
  let now = appointDate;
  for (let i = 0; i < dateTimes; i++) {
    // 格式化日期为 MM-DD 并添加到数组中
    dateList.push(now.format(format));
    // 减去一天
    now = now.subtract(1, 'days');
  }
  dateList.reverse();
  return dateList;
};
