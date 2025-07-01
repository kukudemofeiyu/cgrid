/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-30 09:30:58
 * @LastEditTime: 2024-06-20 09:43:21
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\KeepAliveTabs\index.tsx
 */

import React, { memo, useCallback, useMemo } from 'react';
import styles from './index.less';
import { useAliveController, useHistory, useModel } from 'umi';
import { Tabs } from 'antd';
import { merge } from 'lodash';
import { TabItem } from '@/models/system';

const KeepAliveTabs: React.FC = () => {
  const { tabList, dispatch, active } = useModel('system');
  const history = useHistory();
  const { dropScope } = useAliveController();

  const items = useMemo(() => {
    return tabList.map((item, index) => {
      return {
        key: index + '',
        label: item.title,
      };
    });
  }, [tabList]);

  const onChange = useCallback(
    (key) => {
      dispatch({
        type: 'CHANGESTATE',
        payload: {
          active: key,
        },
      });
      history.push({ ...tabList[Number(key)] });
    },
    [tabList],
  );

  const onEdit = useCallback(
    (key) => {
      const tabListResult: TabItem[] = merge([], tabList);
      const keyNum = Number(key);
      dropScope(tabListResult[keyNum].keepAliveName);
      if (active == key) {
        const nextKey = keyNum > 0 ? keyNum - 1 : 0;
        tabListResult.splice(keyNum, 1);
        dispatch({
          type: 'CHANGESTATE',
          payload: {
            active: nextKey.toString(),
            tabList: tabListResult,
          },
        });
        const timer = setTimeout(() => {
          clearTimeout(timer);
          history.push({ ...tabListResult[nextKey] });
        }, 10);
      } else {
        tabListResult.splice(keyNum, 1);
        dispatch({
          type: 'CHANGESTATE',
          payload: {
            ...(keyNum < Number(active) ? { active: (Number(active) - 1).toString() } : {}),
            tabList: tabListResult,
          },
        });
      }
    },
    [tabList, active],
  );

  return (
    <>
      <div className={styles.tabs}>
        <Tabs
          activeKey={active}
          items={items}
          type="editable-card"
          size="small"
          hideAdd
          onEdit={onEdit}
          onChange={onChange}
        />
      </div>
    </>
  );
};

export default memo(KeepAliveTabs);
