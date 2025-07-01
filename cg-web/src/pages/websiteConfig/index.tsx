/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-24 10:40:57
 * @LastEditTime: 2025-02-24 11:17:05
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/websiteConfig/index.tsx
 */

import { formatMessage } from '@/utils';
import { Tabs, TabsProps } from 'antd';
import React, { memo, useMemo } from 'react';
import Transaction from './Transaction';
import Pay from './Pay';
import ThirdPart from './ThirdPart';

const Index: React.FC = () => {
  const items = useMemo<TabsProps['items']>(() => {
    const result: TabsProps['items'] = [
      {
        key: 'transaction',
        label: formatMessage({ id: '', defaultMessage: '交易设置' }),
        children: <Transaction />,
      }, {
        key: 'pay',
        label: formatMessage({ id: '', defaultMessage: '支付设置' }),
        children: <Pay />,
      }, {
        key: 'third-part',
        label: formatMessage({ id: '', defaultMessage: '第三方设置' }),
        children: <ThirdPart />,
      },
    ];
    return result;
  }, []);

  return <>
    <div className='px24'>
      <Tabs className="line-tabs" items={items} tabBarGutter={24} />
    </div>
  </>;
};

export default memo(Index);
