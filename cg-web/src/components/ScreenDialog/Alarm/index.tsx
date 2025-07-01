/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-08 18:59:32
 * @LastEditTime: 2023-12-25 18:12:44
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\Alarm\index.tsx
 */

import React, { useEffect, useRef, useCallback, useMemo } from 'react';
import { Empty as AntEmpty } from 'antd';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import Dialog from '@/components/Dialog';
import type { BusinessDialogProps } from '@/components/ScreenDialog';
import YTProTable from '@/components/ProTable';
import { getPage } from './service';
import styles from './index.less';
import Empty from '@/components/Empty';
import { formatMessage } from '@/utils';

const levelMap = new Map([
  [
    'info',
    <span className={styles.info}>
      {formatMessage({ id: 'alarmManage.tips', defaultMessage: '提示' })}
    </span>,
  ],
  [
    'warn',
    <span className={styles.warn}>
      {formatMessage({ id: 'alarmManage.secondary', defaultMessage: '次要' })}
    </span>,
  ],
  [
    'alarm',
    <span className={styles.alarm}>
      {formatMessage({ id: 'alarmManage.importance', defaultMessage: '重要' })}
    </span>,
  ],
  [
    'error',
    <span className={styles.error}>
      {formatMessage({ id: 'alarmManage.emergent', defaultMessage: '紧急' })}
    </span>,
  ],
]);

const PvInverter: React.FC<BusinessDialogProps> = (props) => {
  const { id, open, onCancel, model } = props;

  const actionRef = useRef<ActionType>();

  const requestList = useCallback((params) => {
    return getPage(params);
  }, []);

  useEffect(() => {
    if (open) {
      actionRef?.current?.reloadAndRest?.();
    }
  }, [open, actionRef]);

  const columns = useMemo<ProColumns[]>(
    () => [
      {
        title: formatMessage({ id: 'alarmManage.alarmContent', defaultMessage: '告警内容' }),
        dataIndex: 'content',
        width: 200,
        ellipsis: true,
      },
      {
        title: formatMessage({ id: 'alarmManage.alarmLevel', defaultMessage: '告警级别' }),
        dataIndex: 'level',
        valueEnum: levelMap,
      },
      {
        title: formatMessage({ id: 'alarmManage.alarm', defaultMessage: '告警' }) + 'ID',
        dataIndex: 'id',
      },
      {
        title: formatMessage({ id: 'alarmManage.deviceName', defaultMessage: '设备名称' }),
        dataIndex: 'deviceName',
      },
      {
        title: formatMessage({ id: 'alarmManage.productType', defaultMessage: '产品类型' }),
        dataIndex: 'productTypeName',
      },
      {
        title: formatMessage({ id: 'alarmManage.generateTime', defaultMessage: '产生时间' }),
        dataIndex: 'alarmTime',
      },
    ],
    [],
  );

  return (
    <>
      <Dialog
        model={model}
        title={formatMessage({ id: 'alarmManage.alarmList', defaultMessage: '告警列表' })}
        open={open}
        onCancel={onCancel}
        footer={null}
        bodyStyle={{
          height: '608px',
        }}
      >
        <YTProTable
          actionRef={actionRef}
          search={false}
          toolBarRender={() => []}
          params={{ siteId: id, pageSize: 10 }}
          pagination={{ pageSize: 10 }}
          columns={columns}
          request={requestList}
          locale={{
            emptyText: model == 'screen' ? <Empty /> : <AntEmpty />,
          }}
        />
      </Dialog>
    </>
  );
};

export default PvInverter;
