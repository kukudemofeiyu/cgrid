/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-19 17:33:48
 * @LastEditTime: 2023-06-19 17:48:13
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EmptyPage\index.tsx
 */
import React from 'react';
import { Empty, EmptyProps } from 'antd';
import { formatMessage } from '@/utils';

const EmptyPage: React.FC<EmptyProps> = (props) => {
  return (
    <>
      <div className="flex" style={{ marginTop: '25vh' }}>
        <Empty
          className="flex1"
          description={formatMessage({
            id: 'common.noneYet',
            defaultMessage: '页面开发中，敬请期待',
          })}
          {...props}
        />
      </div>
    </>
  );
};

export default EmptyPage;
