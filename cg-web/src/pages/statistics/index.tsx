/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-20 11:32:54
 * @LastEditTime: 2025-02-20 13:39:21
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/statistics/index.tsx
 */
import { Card, Col, Row } from 'antd';
import React, { memo, useMemo } from 'react';
import { totalItems } from './helper';

const Statistics: React.FC = () => {

  const items = useMemo(() => {
    return totalItems.map((item, index) => {
      return <Col span={6} key={item.dataIndex}>
        <Card className='card-wrap shadow'>
          <label>{item.title}</label>
          <h2>{0}</h2>
        </Card>
      </Col>
    });
  }, []);

  return <>
    <div className='p24'>
      <Row gutter={24}>
        {items}
      </Row>
    </div>
  </>;
};

export default memo(Statistics);
