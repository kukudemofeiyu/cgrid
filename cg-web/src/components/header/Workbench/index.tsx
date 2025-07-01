/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-10-10 14:49:28
 * @LastEditTime: 2023-10-12 11:53:43
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\header\Workbench\index.tsx
 */
import React, { memo, useCallback } from 'react';
import { Tooltip } from 'antd';
import { useHistory } from 'umi';
import { YTCellFourOutlined } from '@/components/YTIcons';
import { formatMessage } from '@/utils';

const Workbench: React.FC = memo(() => {
  const history = useHistory();

  const onClick = useCallback(() => {
    history.push({
      pathname: '/workbench',
    });
  }, []);

  return (
    <>
      <Tooltip
        placement="bottom"
        title={formatMessage({ id: 'common.workbentch', defaultMessage: '工作台' })}
      >
        <div className="head-icon" onClick={onClick}>
          <YTCellFourOutlined />
        </div>
      </Tooltip>
    </>
  );
});

export default Workbench;
