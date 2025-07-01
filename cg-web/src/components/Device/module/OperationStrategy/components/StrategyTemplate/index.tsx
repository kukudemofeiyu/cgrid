import React, { useEffect, useCallback, useState } from 'react';
import { Card, Row, Col, Space, Button, Modal, Empty, Spin } from 'antd';
import { useRequest } from 'umi';
import { paramsTemplateList, addTemplate, updataTemplate, deleteTemplate } from '../../service';
import { DeleteOutlined, EditOutlined } from '@ant-design/icons';
import Detail from '@/components/Detail';
import { detailItem } from './config';
import styles from './index.less';
import { ModeEnum } from '../StrategyDetail/config';
import addIcon from '@/assets/image/device/add.svg';
import SchemaForm from '@/components/SchemaForm';
import { useBoolean } from 'ahooks';
import { formatMessage } from '@/utils';

import { FormTypeEnum } from '@/components/SchemaForm';
import { schemaColumns } from './config';

type StrategyTemplateProps = {
  deviceId?: string | number;
  passEditAuth?: boolean;
};
const StrategyTemplate: React.FC<StrategyTemplateProps> = (props) => {
  const { deviceId, passEditAuth } = props;
  const [open, { set, setTrue }] = useBoolean(false);
  const [initialValues, setInitialValues] = useState<any>({});
  const [formType, setFormType] = useState<FormTypeEnum>(FormTypeEnum.Add);

  const {
    data: templateList,
    run,
    loading,
  } = useRequest(paramsTemplateList, {
    manual: true,
  });
  useEffect(() => {
    run({ deviceId });
  }, [deviceId, run]);

  const onBeforeSubmit = useCallback(
    (data) => {
      data.deviceId = deviceId;
      if (formType === FormTypeEnum.Edit) {
        data.id = initialValues.id;
      }
      if (data.systemOperatingMode === ModeEnum.SCHEDUL) {
        data.config = {};
      }
      return data;
    },
    [deviceId, formType, initialValues.id],
  );

  const addTemplateFn = useCallback(() => {
    setFormType(FormTypeEnum.Add);
    setInitialValues({});
    setTrue();
  }, [setTrue]);

  const editTemplateFn = useCallback(
    (item) => {
      setFormType(FormTypeEnum.Edit);
      setInitialValues(item);
      setTrue();
    },
    [setTrue],
  );

  const deleteTemplateFn = useCallback(
    (id) => {
      Modal.confirm({
        title: formatMessage({ id: 'common.delete', defaultMessage: '删除' }),
        content: formatMessage({ id: 'device.1091', defaultMessage: '确定删除该策略模板？' }),
        okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
        cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
        onOk: () => {
          return deleteTemplate({ id }).then(() => {
            run({ deviceId });
          });
        },
      });
    },
    [deviceId, run],
  );

  return (
    <Spin spinning={loading}>
      <div style={{ minHeight: '800px' }}>
        <Row gutter={[24, 24]}>
          {templateList?.length ? (
            templateList.map((item: any) => {
              return (
                <Col xxl={8} xl={12} lg={24} key={item.id}>
                  <Card
                    className={styles.card}
                    title={item.name}
                    extra={
                      <Space size="small">
                        <Button
                          className={styles.editButton}
                          disabled={!passEditAuth}
                          onClick={() => editTemplateFn(item)}
                          icon={<EditOutlined />}
                          type="link"
                        />
                        <Button
                          disabled={!passEditAuth}
                          className={styles.deleteButton}
                          onClick={() => deleteTemplateFn(item.id)}
                          icon={<DeleteOutlined />}
                          type="link"
                        />
                      </Space>
                    }
                  >
                    <Detail
                      column={2}
                      items={detailItem(item?.systemOperatingMode)}
                      data={{
                        ...item?.config,
                        systemOperatingMode: item?.systemOperatingMode,
                      }}
                    />
                  </Card>
                </Col>
              );
            })
          ) : (
            <Col span={24}>{!loading && <Empty />}</Col>
          )}

          {passEditAuth ? (
            <Col xxl={8} xl={12} lg={24} key="add">
              <Card className={styles.addCard} onClick={addTemplateFn}>
                <Space>
                  <img className={styles.addIcon} src={addIcon} />
                  <span>{formatMessage({ id: 'device.1095', defaultMessage: '添加模版' })}</span>
                </Space>
              </Card>
            </Col>
          ) : (
            <></>
          )}
        </Row>

        <SchemaForm
          width={552}
          onOpenChange={set}
          open={open}
          initialValues={initialValues}
          type={formType}
          columns={schemaColumns}
          addData={addTemplate}
          editData={updataTemplate}
          beforeSubmit={onBeforeSubmit}
          onSuccess={() => run({ deviceId }) as any}
          grid={true}
          colProps={{
            span: 12,
          }}
        />
      </div>
    </Spin>
  );
};

export default StrategyTemplate;
