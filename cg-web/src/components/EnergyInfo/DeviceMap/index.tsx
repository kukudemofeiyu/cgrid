/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-08-12 09:43:55
 * @LastEditTime: 2024-11-11 14:41:48
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\DeviceMap\index.tsx
 */

import Detail from '@/components/Detail';
import DeviceContext from '@/components/Device/Context/DeviceContext';
import PositionSelect from '@/components/PositionSelect';
import { useSubscribe } from '@/hooks';
import { DeviceDataType } from '@/services/equipment';
import { getStation } from '@/services/station';
import { formatMessage, isEmpty } from '@/utils';
import { mapTypeOptions } from '@/utils/dict';
import { DeviceProductTypeEnum, MapTypeEnum } from '@/utils/dictionary';
import { Select } from 'antd';
import React, { memo, useCallback, useContext, useEffect, useMemo, useState } from 'react';
import { useRequest } from 'umi';
import Address from './Address';
import GoogleMapContain from '@/components/MapContain/Google';

type DeviceMapType = {
  deviceData?: DeviceDataType;
};

type PositionType = {
  lng?: number;
  lat?: number;
  high?: number;
};

const DeviceMap: React.FC<DeviceMapType> = (props) => {
  const { deviceData } = props;

  const { treeData } = useContext(DeviceContext);
  const [mapType, setMapType] = useState<MapTypeEnum>(MapTypeEnum.AMap);

  const { data: siteData, run } = useRequest(getStation, {
    manual: true,
  });

  const emsId = useMemo(() => {
    return treeData?.[0]?.children?.find?.(
      (item) => item.productTypeId == DeviceProductTypeEnum.Ems,
    )?.deviceId;
  }, [treeData]);

  const emsRealTImeData = useSubscribe(emsId, true);

  const position = useMemo(() => {
    const result: PositionType = {
      lng: emsRealTImeData?.longitude ?? siteData?.longitude,
      lat: emsRealTImeData?.latitude ?? siteData?.latitude,
      high: emsRealTImeData?.altitude ?? '',
    };
    return result;
  }, [emsRealTImeData, siteData]);

  const onMapChange = useCallback((value) => {
    setMapType(value);
  }, []);

  useEffect(() => {
    if (deviceData?.siteId) {
      run(deviceData?.siteId).then((res) => {
        setMapType(res?.map || MapTypeEnum.AMap);
      });
    }
  }, [deviceData?.siteId]);

  return (
    <>
      {!isEmpty(position?.lng) && !isEmpty(position?.lat) && (
        <div className="card-wrap shadow p20 mb20">
          <Detail.Label
            className="mb12"
            title={formatMessage({ id: 'device.1019', defaultMessage: '设备地址' })}
            bold={false}
            showLine={false}
          >
            <Select options={mapTypeOptions} value={mapType} onChange={onMapChange} />
          </Detail.Label>
          <PositionSelect
            type={mapType}
            value={{
              high: position?.high,
              address:
                mapType == MapTypeEnum.Google ? (
                  <GoogleMapContain>
                    <Address mapType={mapType} lat={position?.lat} lng={position?.lng} />
                  </GoogleMapContain>
                ) : (
                  <Address mapType={mapType} lat={position?.lat} lng={position?.lng} />
                ),
              point: {
                lng: position?.lng as number,
                lat: position?.lat as number,
              },
            }}
            showHigh
            readonly
          />
        </div>
      )}
    </>
  );
};

export default memo(DeviceMap);
