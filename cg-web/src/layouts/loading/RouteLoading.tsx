/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-05-23 10:40:18
 * @LastEditTime: 2024-06-25 11:46:47
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\layouts\loading\RouteLoading.tsx
 */

import React, { memo } from 'react';
import { Spin } from 'antd';
import FallBackRender from '@/components/FallBackRender';
import { formatMessage } from '@/utils';
import { isLoadError } from '@/utils/reg';

type RouteLoadingType = {
  error?: any;
};

const RouteLoading: React.FC<RouteLoadingType> = (props) => {
  const { error } = props;

  const loadError = isLoadError(error?.message);

  return (
    <>
      <div className="tx-center mt32 pt32">
        {!!error ? (
          <FallBackRender
            error={error}
            resetErrorBoundary={() => {}}
            tipContent={
              loadError ? formatMessage({ id: 'system.1022', defaultMessage: '发现新版本' }) : ''
            }
            showMsg={!loadError}
          />
        ) : (
          <Spin />
        )}
      </div>
    </>
  );
};

export default memo(RouteLoading);
