import React, { useMemo, useContext } from 'react';
import { formatMessage } from '@/utils';
import styles from './index.less';
import type { TabsProps } from 'antd';

import { Button, Tabs, Modal, message } from 'antd';
import Detail from '@/components/Detail';
import StrategyConfig from './components/StrategyConfig';
import StrategyDetail from './components/StrategyDetail';
import StrategyTemplate from './components/StrategyTemplate';
import { strategyPush } from './service';
import DeviceContext from '@/components/Device/Context/DeviceContext';
import { OnlineStatusEnum } from '@/utils/dictionary';

type OperationStrategyProps = {
  passEditAuth?: boolean;
  networkStatus?: OnlineStatusEnum;
};

const OperationStrategy: React.FC<OperationStrategyProps> = (props) => {
  const { data: deviceData } = useContext(DeviceContext);
  const { passEditAuth, networkStatus } = props;

  const items = useMemo(() => {
    const result: TabsProps['items'] = [
      {
        key: '1',
        label: formatMessage({ id: 'device.1061', defaultMessage: '策略详情' }),
        children: <StrategyDetail deviceId={deviceData?.deviceId} />,
      },
      {
        key: '2',
        label: formatMessage({ id: 'device.1062', defaultMessage: '策略配置' }),
        children: <StrategyConfig passEditAuth={passEditAuth} deviceId={deviceData?.deviceId} />,
      },
      {
        key: '3',
        label: formatMessage({ id: 'device.1063', defaultMessage: '参数模版配置' }),
        children: <StrategyTemplate passEditAuth={passEditAuth} deviceId={deviceData?.deviceId} />,
      },
    ];
    return result;
  }, [deviceData?.deviceId, passEditAuth]);

  const onIssued = () => {
    Modal.confirm({
      title: formatMessage({ id: 'device.1065', defaultMessage: '下发策略' }),
      content: formatMessage({
        id: 'device.1066',
        defaultMessage: '点击确定将下发今日以及未来六天的策略至设备！',
      }),
      okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
      cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
      onOk: () => {
        strategyPush({ deviceId: deviceData?.deviceId }).then((res: any) => {
          if (res.code == 200) {
            message.success(
              formatMessage({ id: 'device.issueSuccess', defaultMessage: '下发成功' }),
            );
          }
        });
      },
    });
  };
  return (
    <div className="mb16">
      <Detail.Label
        showLine={false}
        title={formatMessage({ id: 'device.1060', defaultMessage: '运行策略设置' })}
        className="mt16"
      >
        <Button
          type="primary"
          onClick={onIssued}
          disabled={!(passEditAuth && networkStatus !== OnlineStatusEnum.Offline)}
        >
          {formatMessage({ id: 'device.1064', defaultMessage: '下发' })}
        </Button>
      </Detail.Label>
      <Tabs
        destroyInactiveTabPane={true}
        className={`${styles.tabsWrapper}`}
        tabBarGutter={34}
        defaultActiveKey="1"
        items={items}
      />
    </div>
  );
};

export default OperationStrategy;
