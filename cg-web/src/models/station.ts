/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-22 15:15:12
 * @LastEditTime: 2023-05-22 15:33:51
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\models\station.ts
 */
import React, { useReducer } from 'react';

export type StationDetailType = {
  id?: string;
  name?: string;
  icon?: string;
};

const useStationModel = () => {
  const initState = {};

  const reducer = (
    state: StationDetailType,
    action: { type: string; payload: StationDetailType },
  ) => {
    if (action.type == 'get') {
      return action.payload;
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

export default useStationModel;
