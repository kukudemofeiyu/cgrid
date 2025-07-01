import React, { useCallback, useState } from 'react';
import { Radio, Space } from 'antd';
import { timeOptions, TimeEnum } from './config';
import { formatMessage } from '@/utils';
import Detail from '@/components/Detail';
import styles from './index.less';
import DateConfig from './module/DateConfig';
import TemplateConfig from './module/TemplateConfig';

type StrategyConfigProps = {
  deviceId?: string | number;
  passEditAuth?: boolean;
};
const StrategyConfig: React.FC<StrategyConfigProps> = (props) => {
  const { deviceId, passEditAuth } = props;
  const [timeType, setTimeType] = useState(TimeEnum.WEEK);
  const onTimeChange = useCallback((e: any) => {
    setTimeType(e.target.value);
  }, []);
  return (
    <>
      <Space size="large">
        <Radio.Group
          options={timeOptions}
          onChange={onTimeChange}
          value={timeType}
          optionType="button"
          buttonStyle="solid"
        />
        <div className={styles.timeTip}>
          <div>
            {formatMessage({
              id: 'device.1101',
              defaultMessage: '周循环:定义每周七日策略，设备按照每周循环自动运行预定策略',
            })}
          </div>
          <div>
            {formatMessage({
              id: 'device.1102',
              defaultMessage: '月循环:定义每月31日策略，设备按照每月循环自动运行预定策略',
            })}
          </div>
          <div>
            {formatMessage({
              id: 'device.1103',
              defaultMessage:
                '自定义日:定义单日或多日策略，设备在该日运行预定策略(一般用于特定节假日)',
            })}
          </div>

          <div>
            {formatMessage({
              id: 'device.1104',
              defaultMessage:
                '策略优先级：在当日同时存在多种策略时，优先执行自定义日策略；无自定义日策略时，优先执行周循环策略；只有月循环策略时，执行月循环策略',
            })}
          </div>
        </div>
      </Space>
      <Detail.Label
        showLine={false}
        className="mt24 mb20"
        title={formatMessage({ id: 'device.1062', defaultMessage: '策略配置' })}
      />
      <DateConfig deviceId={deviceId} timeType={timeType} passEditAuth={passEditAuth} />
      <Detail.Label
        showLine={false}
        title={formatMessage({ id: 'device.1094', defaultMessage: '策略模版' })}
        className="mt24 mb20"
      />
      <TemplateConfig deviceId={deviceId} timeType={timeType} passEditAuth={passEditAuth} />
    </>
  );
};

export default StrategyConfig;
