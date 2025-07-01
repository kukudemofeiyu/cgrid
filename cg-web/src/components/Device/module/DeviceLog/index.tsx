import { Button, Modal, message, Row, Col, Space, Empty, Switch } from 'antd';
import { useRef, useState, useContext, useEffect, useCallback } from 'react';
import { formatMessage } from '@/utils';
import { DeviceProductTypeEnum } from '@/utils/dictionary';
import YTProTable from '@/components/ProTable';
import type { ProColumns, ActionType } from '@ant-design/pro-components';
import type { ConfigDataType, Authority } from './data';
import { columns, schemaColumns, emsCcolumns } from './config';
import { ProConfigProvider } from '@ant-design/pro-components';
import { YTDateRangeValueTypeMap } from '@/components/YTDateRange';
import {
  getDeviceLocalLog,
  getFileUrl,
  getLocalLog,
  deleteDeviceLocalLog,
  editSetting,
  getLocalLogList,
} from '@/services/equipment';
import moment from 'moment';
import { aLinkDownLoad } from '@/utils/downloadfile';
import DeviceContext from '@/components/Device/Context/DeviceContext';
import { useSubscribe, useAuthority } from '@/hooks';
import { useBoolean } from 'ahooks';
import styles from './index.less';
import SchemaForm from '@/components/SchemaForm';
import { FormTypeEnum } from '@/components/SchemaForm';

const DeviceLog: React.FC = () => {
  const [isLogRecord, setIsLogRecord] = useState<boolean>(false);
  const [loading, setLoading] = useState<boolean>(false);
  const { data: deviceData } = useContext(DeviceContext);
  const realTimeData = useSubscribe(deviceData?.deviceId, true);
  const actionRef = useRef<ActionType>();
  const [open, { set, setTrue }] = useBoolean(false);
  const [visible, setVisible] = useState<boolean>(false);
  const [authority, setAuthority] = useState<Authority>();

  const { authorityMap } = useAuthority([
    'log:exchangeCabinet:page',
    'log:exchangeCabinet:page:getLog',
    'log:exchangeCabinet:page:download',
    'log:exchangeCabinet:page:delete',
    'log:charge:page',
    'log:charge:page:getLog',
    'log:charge:page:download',
    'log:charge:page:delete',
    'log:ems:page',
    'log:ems:page:getLog',
    'log:ems:page:download',
    'log:ems:page:delete',
    'log:ems:page:run:delete',
    'log:ems:page:logRecord',
  ]);

  useEffect(() => {
    switch (deviceData?.productTypeId) {
      case DeviceProductTypeEnum.Ems:
        setAuthority({
          page: Boolean(authorityMap.get('log:ems:page')),
          getLog: Boolean(authorityMap.get('log:ems:page:getLog')),
          download: Boolean(authorityMap.get('log:ems:page:download')),
          delete: Boolean(authorityMap.get('log:ems:page:delete')),
          runDelete: Boolean(authorityMap.get('log:ems:page:run:delete')),
          logRecord: Boolean(authorityMap.get('log:ems:page:logRecord')),
        });
        break;
      case DeviceProductTypeEnum.ChargeMaster:
      case DeviceProductTypeEnum.ChargeTerminal:
        setAuthority({
          page: Boolean(authorityMap.get('log:charge:page')),
          getLog: Boolean(authorityMap.get('log:charge:page:getLog')),
          download: Boolean(authorityMap.get('log:charge:page:download')),
          delete: Boolean(authorityMap.get('log:charge:page:delete')),
        });
        break;
      case DeviceProductTypeEnum.ExchangeCabinet:
        setAuthority({
          page: Boolean(authorityMap.get('log:exchangeCabinet:page')),
          getLog: Boolean(authorityMap.get('log:exchangeCabinet:page:getLog')),
          download: Boolean(authorityMap.get('log:exchangeCabinet:page:download')),
          delete: Boolean(authorityMap.get('log:exchangeCabinet:page:delete')),
        });
        break;
      default:
        setAuthority({
          page: false,
          getLog: false,
          download: false,
          delete: false,
        });
    }
  }, [authorityMap, deviceData?.productTypeId]);

  const handleRequest = (params: any) => {
    return getDeviceLocalLog({
      deviceId: deviceData?.deviceId,
      ...params,
    });
  };

  const handleRequestlLogList = () => {
    return getLocalLogList({
      deviceId: deviceData?.deviceId,
      context: '',
    }).then((res) => {
      const data = res?.data || [];
      return {
        data: {
          total: data.length,
          list: data,
        },
      };
    });
  };

  const downloadLog = (rowData: ConfigDataType) => {
    getFileUrl({ url: rowData.logUrl || '', platform: rowData.type || '' }).then((res) => {
      if (res) {
        aLinkDownLoad(res.data, rowData.logName || '');
      }
    });
  };

  const deleteLog = (rowData: ConfigDataType) => {
    Modal.confirm({
      title: formatMessage({ id: 'common.delete', defaultMessage: '删除' }),
      content: formatMessage({
        id: 'dataManage.1094',
        defaultMessage: '你确定要删除吗？删除之后无法恢复！',
      }),
      okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
      cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
      onOk: () => {
        deleteDeviceLocalLog({ id: rowData.id }).then((res: any) => {
          if (res.code == 200) {
            message.success('删除成功');
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        });
      },
    });
  };

  const handleFinish = async (rowData?: ConfigDataType) => {
    setLoading(true);
    getLocalLog({
      deviceId: deviceData?.deviceId,
      context: {
        logDate: rowData ? moment(rowData.logDate || rowData.createTime).format('YYYY-MM-DD') : '',
        logName: rowData?.logName || '',
      },
    })
      .then((res: any) => {
        if (res?.data) {
          message.success(
            res?.data?.message || formatMessage({ id: 'common.success', defaultMessage: '成功' }),
          );
          if (actionRef.current) {
            actionRef.current.reload();
          }
        }
      })
      .finally(() => setLoading(false));
  };

  const onBeforeSubmit = useCallback(
    (data) => {
      data.deviceId = deviceData?.deviceId;
      data.context = { logDate: data.logDate ? moment(data.logDate).format('YYYY-MM-DD') : '' };
      delete data.logDate;
    },
    [deviceData?.deviceId],
  );

  const onRunDelete = () => {
    Modal.confirm({
      title: formatMessage({ id: 'common.delete', defaultMessage: '删除' }),
      content: formatMessage({
        id: 'dataManage.1094',
        defaultMessage: '你确定要删除吗？删除之后无法恢复！',
      }),
      okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
      cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
      onOk: () => {
        editSetting({
          deviceId: deviceData?.deviceId,
          input: {
            logDeletion: 1,
          },
          serviceId: 'logDeletion',
        }).then((res: any) => {
          if (res.code == 200) {
            message.success('删除成功');
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        });
      },
    });
  };

  const onLogRecord = (value: boolean) => {
    const localLog = value ? 1 : 0;
    const title = value
      ? formatMessage({ id: 'device.1030', defaultMessage: '开启' })
      : formatMessage({ id: 'device.1031', defaultMessage: '关闭' });
    const content = value
      ? formatMessage({ id: 'device.1113', defaultMessage: '开启本地日志记录' })
      : formatMessage({ id: 'device.1114', defaultMessage: '关闭本地日志记录' });
    Modal.confirm({
      title,
      content: `${content}？`,
      okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
      cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
      onOk: () => {
        editSetting({
          deviceId: deviceData?.deviceId,
          input: {
            localLog,
          },
          serviceId: 'localLog',
        }).then((res: any) => {
          if (res.code == 200) {
            setIsLogRecord(value);
            message.success(
              `${title}${formatMessage({ id: 'device.1027', defaultMessage: '成功' })}`,
            );
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        });
      },
    });
  };

  const actionColumn: ProColumns[] = [
    {
      title: formatMessage({ id: 'alarmManage.operate', defaultMessage: '操作' }),
      valueType: 'option',
      width: 150,
      render: (_, record) => {
        const rowData = record as ConfigDataType;
        return (
          <Space>
            {record.status == 1 && authority?.download && (
              <Button
                type="link"
                size="small"
                key="doload"
                onClick={() => {
                  downloadLog(rowData);
                }}
              >
                {formatMessage({ id: 'common.download', defaultMessage: '下载' })}
              </Button>
            )}
            {authority?.delete && (
              <Button
                type="link"
                size="small"
                key="delete"
                onClick={() => {
                  deleteLog(rowData);
                }}
              >
                {formatMessage({ id: 'common.delete', defaultMessage: '删除' })}
              </Button>
            )}
          </Space>
        );
      },
    },
  ];

  const emsActionColumn: ProColumns[] = [
    {
      title: formatMessage({ id: 'alarmManage.operate', defaultMessage: '操作' }),
      valueType: 'option',
      width: 150,
      render: (_, record) => {
        const rowData = record as ConfigDataType;
        return (
          <Button
            type="link"
            size="small"
            key="doload"
            onClick={() => {
              handleFinish(rowData);
            }}
          >
            {formatMessage({ id: 'common.download', defaultMessage: '下载' })}
          </Button>
        );
      },
    },
  ];

  return authority?.page ? (
    <div>
      {DeviceProductTypeEnum.Ems == deviceData?.productTypeId && (
        <Row gutter={[8, 24]}>
          <Col span={24}>
            <Space size="middle">
              <span className={styles.logTitle}>
                {formatMessage({ id: 'device.1029', defaultMessage: '本地日志记录' })}
              </span>
              <span>
                {realTimeData.localLog
                  ? formatMessage({ id: 'device.1030', defaultMessage: '开启' })
                  : formatMessage({ id: 'device.1031', defaultMessage: '关闭' })}
              </span>
            </Space>
          </Col>
          {authority?.runDelete && (
            <Col span={8}>
              <Space size="middle">
                <span className={styles.logTitle}>
                  {formatMessage({ id: 'device.1032', defaultMessage: '日志删除' })}
                </span>
                <Button onClick={onRunDelete} type="primary">
                  {formatMessage({ id: 'device.1033', defaultMessage: '执行' })}
                </Button>
              </Space>
            </Col>
          )}
          {authority?.logRecord && (
            <Col span={8}>
              <Space size="middle">
                <span className={styles.logTitle}>
                  {formatMessage({ id: 'device.1029', defaultMessage: '本地日志记录' })}
                </span>
                <Switch
                  onChange={onLogRecord}
                  checked={isLogRecord}
                  checkedChildren={formatMessage({ id: 'device.1030', defaultMessage: '开启' })}
                  unCheckedChildren={formatMessage({ id: 'device.1031', defaultMessage: '关闭' })}
                />
              </Space>
            </Col>
          )}
        </Row>
      )}
      <ProConfigProvider
        valueTypeMap={{
          ...YTDateRangeValueTypeMap,
        }}
      >
        <YTProTable<ConfigDataType>
          actionRef={actionRef}
          className={styles.logTable}
          columns={[...columns, ...actionColumn] as any}
          request={handleRequest}
          scroll={{ y: 'auto' }}
          toolBarRender={() =>
            authority?.getLog &&
            ([
              <Button
                type="primary"
                loading={loading}
                key="get"
                onClick={async () => {
                  if (DeviceProductTypeEnum.ExchangeCabinet == deviceData?.productTypeId) {
                    setTrue();
                  } else if (DeviceProductTypeEnum.Ems == deviceData?.productTypeId) {
                    setVisible(true);
                  } else {
                    handleFinish();
                  }
                }}
              >
                {formatMessage({ id: 'device.getDeviceLog', defaultMessage: '获取设备日志' })}
              </Button>,
            ] as any)
          }
        />
      </ProConfigProvider>

      <SchemaForm
        title={formatMessage({ id: 'device.exportLog', defaultMessage: '日志导出' })}
        onOpenChange={set}
        open={open}
        type={FormTypeEnum.Add}
        columns={schemaColumns}
        addData={getLocalLog}
        beforeSubmit={onBeforeSubmit}
        grid={true}
      />
      <Modal
        width={552}
        title={formatMessage({ id: 'device.1047', defaultMessage: '日志列表' })}
        visible={visible}
        destroyOnClose
        onOk={() => {
          setVisible(false);
        }}
        onCancel={() => {
          setVisible(false);
        }}
      >
        <YTProTable
          search={false}
          toolBarRender={false}
          params={{ deviceId: deviceData?.deviceId, context: '' }}
          request={handleRequestlLogList as any}
          columns={[...emsCcolumns, ...emsActionColumn] as any}
        />
      </Modal>
    </div>
  ) : (
    <Empty />
  );
};
export default DeviceLog;
