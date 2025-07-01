/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-04-07 11:49:24
 * @LastEditTime: 2024-04-07 11:51:28
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\Position\AMap.tsx
 */
import React, { useState, useEffect, useMemo, memo } from 'react';
import MapContain from '@/components/MapContain';
import { Map, Marker } from '@uiw/react-amap';
import { getPoint } from '@/utils/map';
import { getLocale } from '@/utils';
import { AmapLang } from '@/utils/dictionary';

export type PositionProps = {
  point: AMap.LngLat;
};

const AMapPosition: React.FC<PositionProps> = (props) => {
  const { point } = props;
  const [zoom] = useState(15);
  const [center, setCenter] = useState<AMap.LngLat>();

  const lang = useMemo(() => {
    return getLocale().isZh ? '' : AmapLang.En;
  }, []);

  useEffect(() => {
    if (point && point.lng && point.lat) {
      getPoint(point.lng, point.lat).then((res) => {
        setCenter(res);
      });
    }
  }, [point]);

  return (
    <>
      <MapContain style={{ height: '100%' }}>
        <Map center={center} zoom={zoom} lang={lang}>
          {center && <Marker position={center} />}
        </Map>
      </MapContain>
    </>
  );
};

export default memo(AMapPosition);
