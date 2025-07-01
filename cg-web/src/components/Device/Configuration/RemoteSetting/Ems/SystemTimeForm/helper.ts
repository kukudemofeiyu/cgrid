/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-12 11:16:06
 * @LastEditTime: 2023-09-12 11:16:10
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\RemoteSetting\Ems\SystemTimeForm\helper.ts
 */

import { formatMessage } from '@/utils';
import { ProFormColumnsType } from '@ant-design/pro-components';

export const columns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: 'common.systemTime', defaultMessage: '系统时间' }),
    dataIndex: 'sysTem',
    valueType: 'dateTime',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }) +
            formatMessage({ id: 'common.systemTime', defaultMessage: '系统时间' }),
        },
      ],
    },
    fieldProps: {
      className: 'w-full',
    },
  },
];
