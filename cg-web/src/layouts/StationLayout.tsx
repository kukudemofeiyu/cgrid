/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-04-23 16:55:31
 * @LastEditTime: 2023-05-26 10:24:08
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\layouts\StationLayout.tsx
 */
import React from 'react';
import { Layout } from 'antd';
import MyHeader from '@/components/header/MyHeader';
import styles from './index.less';
import QueueAnim from 'rc-queue-anim';
import { useModel } from 'umi';

const StationLayout: React.FC = (props) => {
  const { outlined } = useModel('screen', (model) => ({ outlined: model.outlined }));
  return (
    <Layout className={styles.myLayout}>
      <Layout.Header className={styles.headerScreen}>
        <QueueAnim duration={1000} delay={30} type={['top', 'bottom']} ease="easeInOutQuart">
          {!outlined && <MyHeader />}
        </QueueAnim>
      </Layout.Header>
      <div id="myLayoutContain" style={{ overflow: 'hidden' }}>
        {props.children}
      </div>
    </Layout>
  );
};

export default StationLayout;
