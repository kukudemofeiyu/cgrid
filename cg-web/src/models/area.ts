import React, { useCallback, useEffect, useReducer } from 'react';
import { api } from '@/services';

export type AreaType = {
  id: string;
  parentId: string;
  label: string;
  children: AreaType[];
};

export type AreaDataType = AreaType[];

const useStationModel = () => {
  const initState: AreaDataType = [];

  const reducer = (state: AreaDataType, action: { payload: AreaDataType; type?: string }) => {
    if (action.payload) {
      return action.payload;
    } else {
      return state;
    }
  };

  const [state, dispatch] = useReducer(reducer, initState);

  const refresh = useCallback(() => {
    // api.getAreaData().then(({ data }) => {
    //   dispatch({ payload: data });
    // });
  }, []);

  useEffect(() => {
    refresh();
  }, []);

  return {
    state,
    refresh,
  };
};

export default useStationModel;
