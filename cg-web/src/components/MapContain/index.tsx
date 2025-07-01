/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-04 18:51:22
 * @LastEditTime: 2023-05-10 17:19:11
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\MapContain\index.tsx
 */

import type { CSSProperties } from 'react';
import React, { useCallback, useMemo } from 'react';
import AmapLoader from '../AmapLoader';
import { MapTypeEnum, mapAks } from '@/utils/dictionary';
import styles from './index.less';
import { getLocale } from '@/utils';

export type MapProps = {
  className?: string;
  style?: CSSProperties;
  ready?: () => void;
};

const version = {
  zh: '2.0.5',
  en: '1.4.15',
};

const MapContain: React.FC<MapProps> = (props) => {
  const { style, children, className } = props;

  const mapVersion = useMemo(() => {
    return getLocale().isZh ? version.zh : version.en;
  }, []);

  return (
    <>
      <div className={`${styles.contain} ${className}`} style={style}>
        <AmapLoader
          version={mapVersion}
          akey={mapAks[MapTypeEnum.AMap][0].key}
          securityJsCode={mapAks[MapTypeEnum.AMap][0].securityJsCode}
        >
          {children}
        </AmapLoader>
      </div>
    </>
  );
};

export default MapContain;
