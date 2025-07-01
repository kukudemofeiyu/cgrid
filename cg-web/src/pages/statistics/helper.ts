/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-20 11:39:25
 * @LastEditTime: 2025-02-20 11:39:25
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/statistics/helper.ts
 */

import { DetailItem } from "@/components/Detail";
import { formatMessage } from "@/utils";

export const totalItems: DetailItem[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '累计金额(元)' }),
    dataIndex: 'a',
  }, {
    title: formatMessage({ id: '', defaultMessage: '累计次数(次)' }),
    dataIndex: 'b',
  }, {
    title: formatMessage({ id: '', defaultMessage: '累计电量(度)' }),
    dataIndex: 'c',
  }, {
    title: formatMessage({ id: '', defaultMessage: '累计时长(小时)' }),
    dataIndex: 'd',
  },
];
