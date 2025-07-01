/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-20 19:29:54
 * @LastEditTime: 2025-02-21 11:17:24
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/agent/platformSeperate/index.tsx
 */

import ProTable from '@/components/ProTable';
import React, { memo, useMemo, useRef } from 'react';
import { getColumns, getItems } from './helper';
import { ActionType } from '@ant-design/pro-components';
import { Avatar, Button, Card, Col, Row } from 'antd';
import styles from './index.less';
import { formatMessage } from '@/utils';
import { SeperateTypeEnum } from '@/utils/enum';

type PlatformSeperateType = {
  type: SeperateTypeEnum;
};

const PlatformSeperate: React.FC<PlatformSeperateType> = (props) => {
  const { type } = props;

  const actionRef = useRef<ActionType>(null);

  const columns = useMemo(() => {
    return getColumns(type);
  }, []);

  const headerTitle = useMemo(() => {
    const items = getItems(type).map((item, index) => {
      return item.show !== false && <Col span={item.span} key={item.dataIndex}>
        <Card className='card-wrap shadow'>
          <Card.Meta
            avatar={<Avatar src={item.icon} />}
            title={item.title}
            description={'0'}
          />
        </Card>
      </Col>
    });
    return <Row className='w-full' gutter={[16, 16]}>
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
      option={type == SeperateTypeEnum.Agent ? {
        renderBack: (_, record) => <Button type="link" size="small" key="edit">
          {formatMessage({ id: '', defaultMessage: '分成者' })}
        </Button>
      } : {}}
    />
  </>;
};

export default memo(PlatformSeperate);
