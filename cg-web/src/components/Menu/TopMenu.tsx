/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-22 15:03:38
 * @LastEditTime: 2023-07-04 15:43:18
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Menu\TopMenu.tsx
 */
import React, { useEffect, useCallback, useMemo } from 'react';
import { Tabs } from 'antd';
import styles from './index.less';
import { useLocation, useModel, useHistory } from 'umi';
import YtIcon from '@/assets/image/icon-yt.png';
import { getStation } from '@/services/station';
import type { LocationType } from '@/types';
import { formatMessage } from '@/utils';

const menuMap = new Map([
  [
    '/station-manage/operation-monitor',
    formatMessage({ id: 'device.overview', defaultMessage: '概览' }),
  ],
  ['/station-manage/station', formatMessage({ id: 'device.site', defaultMessage: '站点' })],
  ['/station-manage/device', formatMessage({ id: 'device.device', defaultMessage: '设备' })],
  ['/station-manage/alarm', formatMessage({ id: 'common.warning', defaultMessage: '告警' })],
  ['/station-manage/report', formatMessage({ id: 'device.data', defaultMessage: '数据' })],
  ['/station-manage/stat', formatMessage({ id: 'device.task', defaultMessage: '任务' })],
  ['/station-manage/setting', formatMessage({ id: 'device.set', defaultMessage: '设置' })],
]);

const TopMenu: React.FC = () => {
  const { state, dispatch } = useModel('station');
  const location = useLocation<LocationType>();
  const id = useMemo(
    () => (location as LocationType).query?.id,
    [(location as LocationType).query?.id],
  );
  const history = useHistory();

  const menus = [...menuMap].map(([key, label]) => ({ key, label }));

  const onChange = useCallback(
    (key) => {
      history.push({
        pathname: `${key}`,
        search: `id=${id}`,
      });
    },
    [id],
  );

  useEffect(() => {
    if (id) {
      getStation(id).then(({ data }) => {
        dispatch({ type: 'get', payload: { ...(data || {}), id } });
      });
    }
    return () => {
      dispatch({ type: 'get', payload: { id: '' } });
    };
  }, [id]);

  return (
    <>
      <div className={`${styles.topMenu} flex`}>
        <img className={`${styles.icon} mr8`} src={state?.icon || YtIcon} />
        <span className={`${styles.topTitle} flex1`}>{state?.name}</span>
        <Tabs
          className={styles.tabs}
          items={menus}
          activeKey={menuMap.has(location.pathname) ? location.pathname : menuMap.keys()[0]}
          onChange={onChange}
        />
      </div>
    </>
  );
};

export default TopMenu;
