/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-20 19:29:54
 * @LastEditTime: 2025-02-21 10:01:02
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/operation/index.tsx
 */

import ProTable from '@/components/ProTable';
import React, { memo, useMemo, useRef } from 'react';
import { columns, totalItems } from './helper';
import { ActionType } from '@ant-design/pro-components';
import { Avatar, Card, Col, Row } from 'antd';
import styles from './index.less';

const Order: React.FC = (props) => {

  const actionRef = useRef<ActionType>(null);

  const headerTitle = useMemo(() => {
    const items = totalItems.map((item, index) => {
      return <Col span={4} key={item.dataIndex}>
        <Card className='card-wrap shadow'>
          <Card.Meta
            avatar={<Avatar src={item.icon} />}
            title={item.title}
            description={'0'}
          />
        </Card>
      </Col>
    });
    return <Row gutter={[16, 16]}>
      {items}
    </Row>
  }, []);

  return <>
    <ProTable
      className={styles.table}
      actionRef={actionRef}
      columns={columns}
      resizable={true}
      options={false}
      toolBarRender={() => []}
      headerTitle={headerTitle}
    />
  </>;
};

export default memo(Order);
