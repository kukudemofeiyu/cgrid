/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-04-28 09:15:39
 * @LastEditTime: 2023-04-28 16:00:03
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Menu\index.tsx
 */
import React, { useMemo } from 'react';
import { Menu as AntMenu } from 'antd';
import type { MenuProps } from 'antd';
import { useModel, history, useLocation, useRouteMatch } from 'umi';
import styles from './index.less';

export type MyMenuProps = {
  onClick?: () => void;
};

const Menu: React.FC<MyMenuProps> = (props) => {
  const { initialState } = useModel('@@initialState');
  const location = useLocation();
  const route = useRouteMatch();

  const keys = useMemo(() => {
    return {
      selected: [location.pathname],
      opened: [route.path],
    };
  }, [location.pathname, route.path]);

  const menuClick: MenuProps['onClick'] = ({ key }) => {
    history.push(key);
    if (props.onClick) {
      props.onClick();
    }
  };

  return (
    <>
      <AntMenu
        className={styles.modalMenu}
        selectedKeys={keys.selected}
        items={initialState?.antMenus}
        mode="inline"
        onClick={menuClick}
      />
    </>
  );
};

export default Menu;
