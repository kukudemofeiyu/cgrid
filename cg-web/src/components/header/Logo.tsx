/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-05 19:22:37
 * @LastEditTime: 2025-02-19 19:28:52
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/components/header/Logo.tsx
 */
import React from 'react';
import { useModel } from 'umi';
import styles from './index.less';
import { formatMessage } from '@/utils';

const Logo: React.FC = () => {
  const { initialState } = useModel('@@initialState');
  return (
    <>
      <a>
        {initialState?.collapsed
          ? initialState?.currentUser?.systemInfo?.icon && (
            <img className={styles.logo} src={initialState?.currentUser?.systemInfo?.icon} />
          )
          : initialState?.currentUser?.systemInfo?.logo && (
            <img className={styles.logo} src={initialState?.currentUser?.systemInfo?.logo} />
          )}
        {!initialState?.collapsed && formatMessage({ id: '', defaultMessage: '充电运营平台' })}
      </a>
    </>
  );
};

export default Logo;
