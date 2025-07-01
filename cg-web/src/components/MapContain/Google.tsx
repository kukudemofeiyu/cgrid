/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-02-28 16:04:35
 * @LastEditTime: 2024-04-07 16:45:39
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\MapContain\Google.tsx
 */

import React, { CSSProperties, useEffect } from 'react';
import { GoogleApiWrapper } from 'google-maps-react';
import { MapTypeEnum, mapAks } from '@/utils/dictionary';
import MapContext from './MapContext';
import styles from './index.less';
import { getLocale } from '@/utils';

export type MapContainType = {
  className?: string;
  style?: CSSProperties;
  [key: string]: any;
};

const MapContain: React.FC<MapContainType> = (props) => {
  const { google, className, style, children } = props;

  useEffect(() => {
    window.google = google;
  }, [google]);

  return (
    <>
      <MapContext.Provider
        value={{
          google,
        }}
      >
        {children}
      </MapContext.Provider>
    </>
  );
};

export default GoogleApiWrapper(
  {
    apiKey: mapAks[MapTypeEnum.Google][0].key,
    libraries: ['places'],
    language: getLocale().locale,
  },
  'wahaha',
)(MapContain);
