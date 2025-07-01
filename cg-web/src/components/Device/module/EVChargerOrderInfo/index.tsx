import Detail from '@/components/Detail';
import { Tabs, Button } from 'antd';
import type { TabsProps } from 'antd';
import styles from './index.less';
import { useMemo, useRef, useState } from 'react';
import { formatMessage } from '@/utils';
import YTProTable from '@/components/ProTable';
import type { ProColumns, ActionType } from '@ant-design/pro-components';
import type { OrderDataType } from './data';
import { columns as defaultColumns } from './config';
import { getytOrder } from '@/services/equipment';
import OrderCurve from './OrderCurve/index';
import { ProConfigProvider } from '@ant-design/pro-components';
import { YTDateRangeValueTypeMap } from '@/components/YTDateRange';
export type EVChargerOrderInfoType = {
  deviceId?: string;
};

const EVChargerOrderInfo: React.FC<EVChargerOrderInfoType> = (props) => {
  const { deviceId } = props;
  const [curveVisible, setCurveVisible] = useState<boolean>(false);
  const [currentRow, setCurrentRow] = useState<OrderDataType>({});
  const [orderType, setOrderType] = useState<string>('0');
  const [orderId, setOrderId] = useState<string>('');

  const [columns, setColumns] = useState<ProColumns<OrderDataType>[]>(defaultColumns(false));
  const actionRef = useRef<ActionType>();

  const handleRequest = (params: any) => {
    return getytOrder({
      ...params,
      deviceId,
      type: orderType,
    });
  };

  const tabItems = useMemo<TabsProps['items']>(() => {
    return [
      {
        key: '0',
        label: formatMessage({ id: 'device.todayOrder', defaultMessage: '今日订单' }),
      },
      {
        key: '1',
        label: formatMessage({ id: 'device.historyOrder', defaultMessage: '历史订单' }),
      },
    ];
  }, []);

  const onTabChange = (value: string) => {
    setOrderType(value);
    if (value == '1') {
      // @ts-ignore
      setColumns(defaultColumns(true));
    } else {
      // @ts-ignore
      setColumns(defaultColumns(false));
    }
    actionRef?.current?.reload?.();
  };
  const actionColumn: ProColumns = {
    title: formatMessage({ id: 'alarmManage.operate', defaultMessage: '操作' }),
    valueType: 'option',
    width: 100,
    fixed: 'right',
    render: (_, record) => {
      const rowData = record as OrderDataType;
      return [
        <Button
          type="link"
          size="small"
          key="edit"
          onClick={() => {
            setCurrentRow(rowData);
            setCurveVisible(true);
            setOrderId(rowData?.id || '');
          }}
        >
          {formatMessage({ id: 'common.detail', defaultMessage: '查看详情' })}
        </Button>,
      ];
    },
  };
  return (
    <div>
      <Detail.Label
        title={formatMessage({ id: 'device.orderInformation', defaultMessage: '订单信息' })}
        className="mt16"
      />
      <Tabs className={styles.tabs} items={tabItems} onChange={onTabChange} />

      <ProConfigProvider
        valueTypeMap={{
          ...YTDateRangeValueTypeMap,
        }}
      >
        <YTProTable<OrderDataType>
          actionRef={actionRef}
          columns={[...columns, actionColumn]}
          toolBarRender={false}
          // @ts-ignore
          request={handleRequest}
          scroll={{ y: 'auto' }}
        />

        <OrderCurve
          onCancel={() => {
            setCurveVisible(false);
          }}
          orderId={orderId}
          visible={curveVisible}
          values={currentRow}
        />
      </ProConfigProvider>
    </div>
  );
};
export default EVChargerOrderInfo;
