/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-02-28 16:40:25
 * @LastEditTime: 2024-03-01 16:45:44
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Map\GoogleMap.tsx
 */

import React, { useState, forwardRef, useImperativeHandle, useRef, useCallback } from 'react';
import { GoogleAPI, IMapProps, Map } from 'google-maps-react';
import styles from './index.less';

type GoogleMapType = Partial<IMapProps> & {
  children?: React.ReactNode;
};

const GoogleMap = forwardRef<React.ReactNode, GoogleMapType>((props, ref) => {
  const { style, onReady } = props;

  const [mapInstance, setMapInstance] = useState<google.maps.Map>();

  useImperativeHandle(
    ref,
    () => {
      return mapInstance;
    },
    [mapInstance],
  );

  const onMapReady = useCallback((_, map) => {
    setMapInstance(map);
    onReady?.(map);
  }, []);

  return (
    <>
      <div className={styles.map} style={style}>
        <Map {...props} onReady={onMapReady} />
      </div>
    </>
  );
});

export default GoogleMap;
