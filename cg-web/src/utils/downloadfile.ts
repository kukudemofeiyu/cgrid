import { chartTypeEnum } from '@/components/Chart/config';
import Log from '@/pages/equipment/remote-upgrade/log';
import { SubTypeEnum } from '@/pages/site-monitor/Overview/components/TimeButtonGroup';
import request from '@/utils/request';
import { isArray } from 'lodash';
import { Moment } from 'moment';
import * as XLSX from 'xlsx';

const mimeMap = {
  xlsx: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
  zip: 'application/zip',
};

export function downLoadZip(url: string) {
  request(url, {
    method: 'GET',
    responseType: 'blob',
    getResponse: true,
  }).then((res) => {
    resolveBlob(res, mimeMap.zip);
  });
}

/**
 * 解析blob响应内容并下载
 * @param {*} res blob响应内容
 * @param {String} mimeType MIME类型
 */
export function resolveBlob(res: any, mimeType: string) {
  const aLink = document.createElement('a');
  const blob = new Blob([res.data], { type: mimeType });
  // //从response的headers中获取filename, 后端response.setHeader("Content-disposition", "attachment; filename=xxxx.docx") 设置的文件名;
  const patt = new RegExp('filename=([^;]+\\.[^\\.;]+);*');
  const contentDisposition = decodeURI(res.response.headers.get('content-disposition'));
  const result = patt.exec(contentDisposition);
  let fileName = result ? result[1] : 'file';
  fileName = fileName.replace(/"/g, '');
  aLink.style.display = 'none';
  aLink.href = URL.createObjectURL(blob);
  aLink.setAttribute('download', fileName); // 设置下载文件名称
  document.body.appendChild(aLink);
  aLink.click();
  URL.revokeObjectURL(aLink.href); // 清除引用
  document.body.removeChild(aLink);
}

/**
 * 通过a链接下载
 * @param {*} url 下载地址
 * @param {String} name 文件名
 */
export const aLinkDownLoad = (url: string, fileName: string) => {
  const aLink = document.createElement('a');
  aLink.setAttribute('download', fileName); // 设置下载文件名称
  aLink.style.display = 'none';
  aLink.href = url; // 设置下载文件路径
  document.body.appendChild(aLink);
  aLink.click();
  URL.revokeObjectURL(aLink.href); // 清除引用
  document.body.removeChild(aLink);
};

export async function downLoadXlsx(url: string, params: any, fileName: string, method = 'POST') {
  return request(url, {
    ...params,
    method,
    responseType: 'blob',
  }).then((data) => {
    const aLink = document.createElement('a');
    const blob = data; // new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    aLink.style.display = 'none';
    aLink.href = URL.createObjectURL(blob);
    aLink.setAttribute('download', fileName); // 设置下载文件名称
    document.body.appendChild(aLink);
    aLink.click();
    URL.revokeObjectURL(aLink.href); // 清除引用
    document.body.removeChild(aLink);
  });
}

/**
 * 前端下载 Excel 文件
 */
interface DataPoint {
  label: string;
  value: number | null;
}

interface ChartDataItem {
  name: string;
  data: DataPoint[];
}

/**
 * 根据类型和时间类型获取Excel列标题名称
 * @param type - 数据子类型，可以是数字或字符串
 * @param timeType - 时间类型，枚举类型 chartTypeEnum 的值
 * @returns excel标题名称中文部分
 */
const getExcelTypeName = (type: number | string, timeType: chartTypeEnum): string => {
  if (timeType === chartTypeEnum.Day) {
    switch (type) {
      case SubTypeEnum.Power:
        return '功率';
      case SubTypeEnum.Electricity:
        return '电量';
      case SubTypeEnum.Custom:
        return '测试标签';
      default:
        return '';
    }
  } else if (timeType === chartTypeEnum.Label) {
    return '累计电量';
  } else {
    return '电量';
  }
};

/**
 * 根据给定的类型、时间类型和日期获取Excel文件名称中的时间部分字符串。
 *
 * @param type - 表示某种类型的数字或字符串。
 * @param timeType - 表示时间类型的一个枚举值。
 * @param date - 用于计算的时间或日期。
 * @returns 返回对应的时间名称字符串或void。
 */
const getExcelTimeName = (
  type: number | string,
  timeType: chartTypeEnum,
  date: any,
): string | void => {
  // 日功率 日测试标签
  if (
    (timeType === chartTypeEnum.Day && type === SubTypeEnum.Power) ||
    (timeType === chartTypeEnum.Day && type === SubTypeEnum.Custom)
  ) {
    return date.map((item: Moment) => item.format('YYYY-MM-DD')).join('-');
  }

  // 日电量
  if (timeType === chartTypeEnum.Day && type === SubTypeEnum.Electricity) {
    return date.format('YYYY-MM-DD');
  }

  // 月
  if (timeType === chartTypeEnum.Month) {
    return date.format('YYYY-MM');
  }

  // 年 累计
  if (timeType === chartTypeEnum.Year || timeType === chartTypeEnum.Label) {
    return date.format('YYYY');
  }

  return '';
};

/**
 * 将图表数据导出到Excel文件。
 *
 * @param chartData - 包含图表数据的对象，键为字符串，值为ChartDataItem类型。
 * @param type - 子类型枚举，用于确定导出文件的命名。
 * @param allLabels - 可选参数，所有时间标签数组。
 *
 */
export const exportToExcel = (
  chartData: Record<string, ChartDataItem>,
  type: SubTypeEnum,
  timeType: chartTypeEnum,
  date: any,
): void => {
  // Excel文件名称
  const nameType = getExcelTypeName(type, timeType);
  const nameTime = getExcelTimeName(type, timeType, date);

  // Excel表格表头
  const labels = new Set<string>();
  Object.values(chartData).forEach((item) => {
    if (typeof item === 'string') {
      labels.add(item);
    } else {
      item.data.forEach((dataPoint) => labels.add(dataPoint.label));
    }
  });

  // 构建表头
  console.log('chartData', chartData);
  const headers = ['时间', ...Object.keys(chartData).map((key) => chartData[key].name)];

  // 构建表格数据
  const sortedLabels = Array.from(labels).sort((a, b) => a.localeCompare(b));
  const rows: any[][] = [];
  sortedLabels.forEach((label) => {
    const row: (number | string)[] = [label];
    Object.keys(chartData).forEach((key) => {
      let value: any = chartData[key].data.find((dataPoint) => dataPoint.label === label)?.value;
      if (value === null || value === undefined) {
        value = '';
      }
      row.push(value);
    });
    rows.push(row);
  });

  // 创建工作表
  const ws = XLSX.utils.aoa_to_sheet([headers, ...rows]);
  const wb = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

  // 导出为 Excel 文件设置名称
  XLSX.writeFile(wb, `${nameType}-${nameTime}.xlsx`);
};
