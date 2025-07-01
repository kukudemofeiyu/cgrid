/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-25 14:42:02
 * @LastEditTime: 2023-12-19 17:25:55
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\RemoteUpgrade\helper.ts
 */

import { DetailItem } from '@/components/Detail';
import { UpgradeDataType } from './typing';
import { formatMessage } from '@/utils';

export const upgradeItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'device.currentVersion', defaultMessage: '当前版本' }),
    field: 'nowVersion',
  },
  {
    label: formatMessage({ id: 'device.upgradableVersion', defaultMessage: '可升级版本' }),
    field: 'upgradeableVersionVOList',
    format: (value: UpgradeDataType[]) => {
      return value
        ?.map?.((item) => {
          return (
            item.version +
            (item?.status === 0
              ? `(${formatMessage({ id: 'common.disable', defaultMessage: '禁用' })})`
              : '')
          );
        })
        ?.join('，');
    },
    span: 2,
  },
];
