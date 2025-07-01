/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-02-28 16:21:01
 * @LastEditTime: 2024-02-28 16:21:01
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\MapContain\MapContext.tsx
 */

import { GoogleAPI } from 'google-maps-react';
import { createContext } from 'react';

export type MapContextType = {
  google?: GoogleAPI;
};

const MapContext = createContext<MapContextType>({
  google: null as any,
});

export default MapContext;
