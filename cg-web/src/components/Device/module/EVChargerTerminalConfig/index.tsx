import Detail from '@/components/Detail';
import { Button, Modal, Switch, message } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import { useRef, useState } from 'react';
import { formatMessage } from '@/utils';
import YTProTable from '@/components/ProTable';
import type { ProColumns, ActionType } from '@ant-design/pro-components';
import type { ConfigDataType } from './data';
import { columns } from './config';
import {
  getChargeTerm,
  addTerminal,
  termBindMainServer,
  delTerm,
  updateTerm,
} from '@/services/equipment';
import AddForm from './AddForm/index';
import EditForm from './EditForm/index';

export type EVChargerOrderInfoType = {
  deviceId?: string;
};

const EVChargerOrderInfo: React.FC<EVChargerOrderInfoType> = (props) => {
  const { deviceId } = props;
  const [addVisible, setAddVisible] = useState<boolean>(false);
  const [editVisible, setEditVisible] = useState<boolean>(false);
  const [currentRow, setCurrentRow] = useState<any>();

  const actionRef = useRef<ActionType>();
  const handleRequest = () => {
    return getChargeTerm({
      deviceId,
    }).then((res) => {
      const result: any = [];
      res.data.forEach((i) => {
        result.push(i.id);
      });
      return {
        data: {
          list: res.data,
          total: res.data.length,
        },
      };
    });
  };

  const handleRemoveOne = async (rowData: ConfigDataType) => {
    try {
      await delTerm({ deviceId: rowData.id });
      return true;
    } catch {
      return false;
    }
  };

  const handleOpenAssociate = async (bindTermId: any, isBind: boolean) => {
    try {
      await termBindMainServer({ bindTermId, isBind });
      return true;
    } catch {
      return false;
    }
  };
  const handleAdd = async (rowData: ConfigDataType) => {
    try {
      await addTerminal({ ...rowData, chargingId: deviceId });
      return true;
    } catch {
      return false;
    }
  };
  const handleEdit = async (rowData: ConfigDataType) => {
    try {
      await updateTerm(rowData);
      message.success(
        formatMessage({ id: 'system.Notice.config_success', defaultMessage: '配置成功' }),
      );
      return true;
    } catch {
      return false;
    }
  };
  const actionColumn: ProColumns[] = [
    {
      title: formatMessage({ id: 'device.associatedHosts', defaultMessage: '关联主机' }),
      dataIndex: 'isBindMainServer',
      ellipsis: true,
      hideInSearch: true,
      render: (_, record) => {
        const rowData = record as ConfigDataType;
        return [
          <Switch
            checked={record.isBindMainServer == 0} //0--关联 1-未关联
            key="Checke"
            onClick={async (value) => {
              Modal.confirm({
                title: formatMessage({
                  id: 'device.associatedHosts',
                  defaultMessage: '关联主机',
                }),
                content: `${rowData.deviceName} ${value
                    ? formatMessage({
                      id: 'device.openTerminal',
                      defaultMessage: '开启关联',
                    })
                    : formatMessage({
                      id: 'device.closeTerminal',
                      defaultMessage: '关闭关联',
                    })
                  }`,
                okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
                cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
                onOk: async () => {
                  const success = await handleOpenAssociate(rowData.id, value);
                  console.log('success>>', success);
                  if (success) {
                    if (actionRef.current) {
                      actionRef.current.reload();
                    }
                  }
                },
              });
            }}
          />,
        ];
      },
    },
    {
      title: formatMessage({ id: 'alarmManage.operate', defaultMessage: '操作' }),
      valueType: 'option',
      width: 100,
      fixed: 'right',
      render: (_, record) => {
        const rowData = record as ConfigDataType;
        return [
          <Button
            type="link"
            size="small"
            key="edit"
            onClick={() => {
              setEditVisible(true);
              setCurrentRow(rowData);
            }}
          >
            {formatMessage({ id: 'common.edit', defaultMessage: '编辑' })}
          </Button>,
          <Button
            type="link"
            size="small"
            danger
            key="batchRemove"
            onClick={async () => {
              Modal.confirm({
                title: formatMessage({ id: 'common.delete', defaultMessage: '删除' }),
                content: `${formatMessage({
                  id: 'device.deleteTerminal',
                  defaultMessage: '确定删除终端',
                })}${rowData.deviceName}？`,
                okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
                cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
                onOk: async () => {
                  const success = await handleRemoveOne(rowData);
                  if (success) {
                    if (actionRef.current) {
                      actionRef.current.reload();
                    }
                  }
                },
              });
            }}
          >
            {formatMessage({ id: 'common.delete', defaultMessage: '删除' })}
          </Button>,
        ];
      },
    },
  ];
  return (
    <div>
      <Detail.Label
        title={formatMessage({ id: 'device.terminalConfig', defaultMessage: '终端配置' })}
        className="mt16"
      />
      <YTProTable<ConfigDataType>
        actionRef={actionRef}
        columns={[...columns, ...actionColumn]}
        request={handleRequest}
        search={false}
        scroll={{ y: 'auto' }}
        toolBarRender={() => [
          <Button
            type="primary"
            key="add"
            onClick={async () => {
              setAddVisible(true);
            }}
          >
            <PlusOutlined /> {formatMessage({ id: 'common.new', defaultMessage: '新建' })}
          </Button>,
        ]}
      />
      <AddForm
        onSubmit={async (values) => {
          let success = false;
          success = await handleAdd({ ...values });
          if (success) {
            setAddVisible(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
        onCancel={() => {
          setAddVisible(false);
        }}
        visible={addVisible}
      />
      <EditForm
        onSubmit={async (values) => {
          let success = false;
          success = await handleEdit({ ...values });
          if (success) {
            setEditVisible(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
        onCancel={() => {
          setEditVisible(false);
        }}
        values={currentRow || {}}
        visible={editVisible}
      />
    </div>
  );
};
export default EVChargerOrderInfo;
