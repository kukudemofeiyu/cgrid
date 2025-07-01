/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-04-27 18:57:33
 * @LastEditTime: 2023-05-31 17:12:06
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\layouts\index.tsx
 */

import React, { useMemo } from 'react';
import { Layout } from 'antd';
import MyHeader from '@/components/header/MyHeader';
import styles from './index.less';
import PageMenu from '@/components/Menu/PageMenu';
import TopMenu from '@/components/Menu/TopMenu';
import Breadcrumb from '@/components/Breadcrumb';

const MyLayout: React.FC = (props: any) => {
  const { route = {} } = props;

  const layoutMenu = useMemo(() => {
    if (route?.menu == 'sider') {
      return (
        <Layout.Sider className={styles.sider} theme="light">
          <PageMenu />
        </Layout.Sider>
      );
    } else if (route?.menu == 'top') {
      return (
        <Layout.Header className={`my-top-header ${styles.topHeader}`}>
          <TopMenu />
        </Layout.Header>
      );
    } else {
      return <></>;
    }
  }, [route?.menu]);

  return (
    <Layout className={styles.myLayout}>
      <Layout.Header className={`my-header ${styles.header}`}>
        <MyHeader />
      </Layout.Header>
      <Layout id="myLayoutContain">
        {layoutMenu}
        <Layout.Content className={`my-content ${styles.content}`}>
          {route?.menu == 'sider' && <Breadcrumb />}
          {props.children}
        </Layout.Content>
      </Layout>
    </Layout>
  );
};

export default MyLayout;
