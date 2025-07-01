/*
 * @Author: wangYe
 * @Date: 2020-12-18 13:25:08
 * @Last Modified by: WhiteShader
 * @Last Modified time: 2022-02-21 11:12:12
 */

import React, { useEffect } from 'react';
import { KeepAlive, useIntl, useModel } from 'umi';
import { useLocation } from '@/hooks';

export default function KeepAlivePage(props: any) {
  const intl = useIntl();
  const { dispatch, tabList } = useModel('system');
  const { initialState } = useModel('@@initialState');
  const { query } = useLocation();

  useEffect(() => {
    const localTablist = JSON.parse(JSON.stringify(tabList));
    const isExit = localTablist.findIndex((item: any) => item.pathname === props.location.pathname);
    if (isExit < 0) {
      const obj = {
        ...props.location,
        title: props.route?.locale
          ? intl.formatMessage({ id: props.route?.locale })
          : initialState?.menuPathTitleMap?.get?.(props.route?.path),
        keepAliveName: props.route.name || props.route.path,
      };
      localTablist.push(obj);
      dispatch({
        type: 'CHANGESTATE',
        payload: { tabList: localTablist, active: (localTablist.length - 1).toString() },
      });
    } else {
      dispatch({ type: 'CHANGESTATE', payload: { active: isExit.toString() } });
    }
  }, []);

  if (props.route.keepAlive) {
    return (
      <KeepAlive
        saveScrollPosition={props.route.saveScrollPosition ?? 'screen'}
        id={query?.id}
        name={props.route.name || props.route.path}
      >
        {props.children}
      </KeepAlive>
    );
  }
  return props.children;
}
