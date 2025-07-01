/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-02-28 14:47:39
 * @LastEditTime: 2024-04-07 17:22:14
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\Position\Google.tsx
 */

import React, { memo, useCallback, useContext, useEffect, useRef } from 'react';
import { Marker } from 'google-maps-react';
import MapContext from '@/components/MapContain/MapContext';
import GoogleMap from '@/components/Map/GoogleMap';

export type GooglePositionType = {
  point: google.maps.LatLngLiteral;
};

const GooglePosition: React.FC<GooglePositionType> = (props) => {
  const { point } = props;

  const { google } = useContext(MapContext);

  const onReady = useCallback(
    (map) => {
      map?.setCenter?.(point);
    },
    [point],
  );

  return (
    <>
      <GoogleMap
        google={google}
        center={point}
        zoom={15}
        style={{
          height: '700px',
        }}
        onReady={onReady}
      >
        {point && <Marker position={point} />}
      </GoogleMap>
    </>
  );
};

export default memo(GooglePosition);
