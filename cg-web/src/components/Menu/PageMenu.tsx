/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-04-28 14:37:35
 * @LastEditTime: 2023-04-28 15:41:49
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Menu\PageMenu.tsx
 */
import React, { useMemo } from 'react';
import { Menu, Typography } from 'antd';
import type { MenuProps } from 'antd';
import { useModel, history, useRouteMatch, useLocation } from 'umi';
import styles from './index.less';

const PageMenu: React.FC = () => {
  const { initialState } = useModel('@@initialState');
  const route = useRouteMatch();
  const location = useLocation();
  const menu = initialState?.antMenus?.find((item) => item?.key === route.path) || {};

  const keys = useMemo(() => {
    return {
      selected: [location.pathname],
      opened: [route.path],
    };
  }, [location.pathname, route.path]);

  const menuClick: MenuProps['onClick'] = ({ key }) => {
    history.push(key);
  };

  return (
    <>
      <Typography.Title className={styles.title} level={5}>
        {menu.label}
      </Typography.Title>
      <Menu selectedKeys={keys.selected} items={menu.children} mode="inline" onClick={menuClick} />
    </>
  );
};

export default PageMenu;
