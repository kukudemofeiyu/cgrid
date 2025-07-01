/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-21 16:52:47
 * @LastEditTime: 2023-11-17 15:12:12
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\SiteLabel\index.tsx
 */
import React, { useEffect } from 'react';
import { useModel } from 'umi';
import type { SiteDataType } from '@/services/station';
import { formatMessage } from '@/utils';

export type SiteLabelProps = {
  onChange?: (value: SiteDataType) => void;
  className?: string;
  isShowSafeDay?: boolean;
};

const SiteLabel: React.FC<SiteLabelProps> = (props) => {
  const { onChange, className, children, isShowSafeDay = false } = props;

  const { state: siteData } = useModel('site');

  useEffect(() => {
    if (siteData?.isLoad) {
      setTimeout(() => {
        onChange?.(siteData);
      }, 100);
    }
  }, [siteData]);

  return (
    <>
      <div className={`page-label ${className}`}>
        {siteData?.name || '--'}
        {isShowSafeDay &&
          `（${formatMessage({ id: 'siteManage.1037', defaultMessage: '安全运行' })}：${
            siteData?.operationDays || 0
          }${formatMessage({ id: 'common.time.day1', defaultMessage: '天' })}）`}
        {children}
      </div>
    </>
  );
};

export default SiteLabel;
