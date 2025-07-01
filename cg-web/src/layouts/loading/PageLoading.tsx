/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-05-23 10:40:18
 * @LastEditTime: 2024-12-17 16:46:24
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/layouts/loading/PageLoading.tsx
 */

import { PageLoading as AntPageLoading } from '@ant-design/pro-layout';
import React, { memo, useEffect } from 'react';
import { useModel } from 'umi';
import defaultSettings from '../../../config/defaultSettings';
import moment from 'moment';

const PageLoading: React.FC = () => {
  const { refresh } = useModel('siteTypes');

  useEffect(() => {
    moment.locale('zh-cn');
    if (!defaultSettings.authorityWhiteList?.includes(window.location.pathname)) {
      refresh();
    }
  }, []);

  return (
    <>
      <AntPageLoading />
    </>
  );
};

export default memo(PageLoading);
