/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-13 23:36:42
 * @LastEditTime: 2023-12-20 19:23:51
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\SelfEmsIndex\ConverterSetting\index.tsx
 */
import React, { useMemo } from 'react';
import Detail from '@/components/Detail';
import type { GroupItem } from '@/components/Detail';
import {
  protectParamsItems,
  powerParamsItems,
  protectParamsColumns,
  powerParamsColumns,
} from './config';
import ConfigModal from '../../../ConfigModal';
import { useAuthority } from '@/hooks';
import { formatMessage } from '@/utils';

export type StackProps = {
  deviceId: string;
  deviceData?: Record<string, any>;
  productId: string;
  realTimeData?: Record<string, any>;
};
const SystemSetting: React.FC<StackProps> = (props) => {
  const { realTimeData, deviceId, deviceData, productId } = props;

  const { authorityMap } = useAuthority([
    'iot:device:config:converterSetting:converterProtectSetting',
    'iot:device:config:converterSetting:powerParamsSetting',
  ]);

  const detailGroup = useMemo<GroupItem[]>(() => {
    const result: GroupItem[] = [];
    if (authorityMap.get('iot:device:config:converterSetting:converterProtectSetting')) {
      result.push({
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'device.converterProtectionParameterSettings',
              defaultMessage: '变流器保护参数设置',
            })}
          >
            <ConfigModal
              width={'816px'}
              title={formatMessage({
                id: 'device.converterProtectionParameterSettings',
                defaultMessage: '变流器保护参数设置',
              })}
              deviceId={deviceId}
              deviceData={deviceData}
              realTimeData={realTimeData}
              columns={protectParamsColumns}
              serviceId={'converterProtectionParameterSettings'}
              colProps={{
                span: 8,
              }}
              authority="iot:device:config:converterSetting:converterProtectSetting:distribute"
            />
          </Detail.Label>
        ),
        items: protectParamsItems,
      });
    }
    if (authorityMap.get('iot:device:config:converterSetting:powerParamsSetting')) {
      result.push({
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'device.gridParameterSetting',
              defaultMessage: '电网参数设置',
            })}
          >
            <ConfigModal
              title={formatMessage({
                id: 'device.gridParameterSetting',
                defaultMessage: '电网参数设置',
              })}
              deviceId={deviceId}
              deviceData={deviceData}
              realTimeData={realTimeData}
              columns={powerParamsColumns}
              serviceId={'GridParameterSettings'}
              authority="iot:device:config:converterSetting:powerParamsSetting:distribute"
            />
          </Detail.Label>
        ),
        items: powerParamsItems,
      });
    }
    return result;
  }, [deviceId, deviceData, productId, realTimeData, authorityMap]);

  return (
    <>
      <div className="px24">
        <Detail.Group
          data={realTimeData}
          items={detailGroup}
          detailProps={{
            colon: false,
            labelStyle: { width: 200 },
          }}
        />
      </div>
    </>
  );
};

export default SystemSetting;
