/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-21 16:37:17
 * @LastEditTime: 2023-07-21 16:39:09
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\models\site.ts
 */
import { useReducer } from 'react';
import { SiteDataType } from '@/services/station';

const useSiteModel = () => {
  const initState = {
    isLoad: false,
  };

  const reducer = (state: SiteDataType, action: { type: string; payload?: SiteDataType }) => {
    if (action.type == 'change') {
      return { ...action.payload, isLoad: true };
    } else if (action.type == 'init') {
      return { ...initState };
    } else {
      return state;
    }
  };

  const [state, dispatch] = useReducer(reducer, initState);

  return {
    state,
    dispatch,
  };
};

export default useSiteModel;
