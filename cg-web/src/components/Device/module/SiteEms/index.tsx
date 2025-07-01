/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2024-12-26 10:56:03
 * @LastEditTime: 2024-12-26 19:22:36
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/Device/module/SiteEms/index.tsx
 */

import { getSiteEmsDevices } from '@/services/equipment';
import { Checkbox, Spin, Switch } from 'antd';
import React, { memo, useCallback, useEffect, useMemo, useState } from 'react';
import { useRequest } from 'umi';
import { ControlInterceptType } from '../typing';
import { CheckboxValueType } from 'antd/lib/checkbox/Group';


const SiteEms: React.FC<ControlInterceptType> = (props) => {
  const { deviceData, onChange } = props;

  const [allSn, setAllSn] = useState<CheckboxValueType[]>([]);
  const [selectedSn, setSelectedSn] = useState<CheckboxValueType[]>([]);
  const { data: emsDevices, run, loading } = useRequest(getSiteEmsDevices, { manual: true });

  const options = useMemo(() => {
    const sns: CheckboxValueType[] = [];
    const result = emsDevices?.map?.((item) => {
      if (item.cabinetName) {
        sns.push(item.emsSn || '');
      }
      return {
        label: item.cabinetName || item.emsSn || '',
        value: item.emsSn || 0,
        disabled: !item.cabinetName,
      };
    }) || [];
    setAllSn(sns);
    return result;
  }, [emsDevices]);

  const emitChange = useCallback((value: CheckboxValueType[]) => {
    const result = value.reduce((result, valueItem) => {
      result.push(emsDevices?.findIndex(item => item.emsSn == valueItem) || 0);
      return result;
    }, [] as number[]);
    onChange?.(result && result.length ? { sendDeviceIndexs: result } : undefined);
  }, [onChange, emsDevices]);

  const onCheckAllChange = useCallback((value: boolean) => {
    const result = [...(value ? allSn : [])];
    setSelectedSn(result);
    emitChange(result);
  }, [emitChange, allSn]);

  const onCheckboxChange = useCallback((value: CheckboxValueType[]) => {
    setSelectedSn(value);
    emitChange(value);
  }, [emitChange]);

  useEffect(() => {
    if (deviceData?.deviceId) {
      run({ deviceId: deviceData?.deviceId });
    }
  }, [deviceData]);

  return <>
    {loading ?
      <div className='tx-center'>
        <Spin />
      </div> :
      <>
        <div className='mb12'>
          <Switch checkedChildren='全选' unCheckedChildren='全不选' defaultChecked={false} onChange={onCheckAllChange} />
        </div>
        <Checkbox.Group value={selectedSn} options={options} onChange={onCheckboxChange} />
      </>
    }
  </>;
};

export default memo(SiteEms);