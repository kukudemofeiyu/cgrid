import { Modal, Button } from 'antd';
import TypeChart from '@/components/Chart/TypeChart';
import { chartTypeEnum } from '@/components/Chart/config';
import { useRef, useState, useEffect, useCallback } from 'react';
import { getytOrdercurve } from '@/services/equipment';
import Detail from '@/components/Detail';
import moment from 'moment';
import { option } from '../config';
import { formatMessage } from '@/utils';
import { cloneDeep } from 'lodash';
import type { OrderDataType } from '../data';
import type { DetailItem } from '@/components/Detail';
import { useMemo } from 'react';
import { ChargingStrategy, GunType, SourceType, ChargingStrategyParam } from '@/utils/dict';
import { columns } from '../config';

const defaultChartData = [
  { data: [] as any, name: formatMessage({ id: 'device.SOC', defaultMessage: 'SOC' }) },
  {
    data: [] as any,
    name: formatMessage({ id: 'device.chargeAmount', defaultMessage: '已充电量' }),
  },
  {
    data: [] as any,
    name: formatMessage({ id: 'device.demandVoltage', defaultMessage: '需求电压' }),
  },
  {
    data: [] as any,
    name: formatMessage({ id: 'device.chargeOutputVoltage', defaultMessage: '充电输出电压' }),
  },
  {
    data: [] as any,
    name: formatMessage({ id: 'device.demandCurrent', defaultMessage: '需求电流' }),
  },
  {
    data: [] as any,
    name: formatMessage({ id: 'device.outputCurrent', defaultMessage: '充电输出电流' }),
  },
];
export type DetailProps = {
  onCancel: () => void;
  visible: boolean;
  orderId: number | string;
  values: OrderDataType;
};

const columnstitle: Partial<{ string: string }> = {};
columns(true).forEach((item) => {
  columnstitle[item.dataIndex] = item.title;
});
const OrderCurve: React.FC<DetailProps> = (props) => {
  const { visible, onCancel, orderId, values } = props;
  const chartRef = useRef() as any;
  const [ChartData, setChartData] = useState(defaultChartData);
  const [allLabel, setAllLabel] = useState([]);

  const handleCancel = () => {
    setAllLabel([]);
    onCancel();
  };

  const collectRequestParams = useCallback(() => {
    const deviceId = values.deviceId;
    const startTime = values.startTime;
    const endTime = values.endTime;
    const keyValue = [
      {
        key: 'SOC',
        deviceId,
        type: 'double',
        name: 'SOC',
      },
      {
        key: 'mq',
        deviceId,
        type: 'double',
        name: '已充电量',
      },
      {
        key: 'gxqu',
        deviceId,
        type: 'double',
        name: '需求电压',
      },
      {
        key: 'gcu',
        deviceId,
        type: 'double',
        name: '充电输出电压',
      },
      {
        key: 'gxqi',
        deviceId,
        type: 'double',
        name: '需求电流',
      },
      {
        key: 'gci',
        deviceId,
        type: 'double',
        name: '充电输出电流',
      },
    ];
    return { startTime, endTime, keyValue };
  }, [values]);

  const getChartData = useCallback(
    (id: number | string) => {
      if (id) {
        const params = collectRequestParams();
        getytOrdercurve(params).then(({ data }) => {
          if (!data || !data.length) return;
          const currentAllLabel: any = [];
          const currentVChartData = cloneDeep(defaultChartData);
          data.reverse().forEach((item, index) => {
            if (!item.devices || !item.devices.length) return;
            // @ts-ignore
            const currentLabel = moment(item.time).format('HH:mm:ss');
            currentAllLabel.push(currentLabel);
            item.devices.forEach((device: any) => {
              switch (device.key) {
                case 'SOC':
                  currentVChartData[0].data.push({ label: currentLabel, value: device.value });
                  break;
                case 'mq':
                  let value;
                  if (index === 0) {
                    value = 0;
                  } else {
                    const val = device.value - data[0].devices[0].value;
                    Number.isInteger(val) ? (value = val) : (value = val.toFixed(2));
                  }
                  currentVChartData[1].data.push({ label: currentLabel, value: value });
                  break;
                case 'gxqu':
                  currentVChartData[2].data.push({ label: currentLabel, value: device.value });
                  break;
                case 'gcu':
                  currentVChartData[3].data.push({ label: currentLabel, value: device.value });
                  break;
                case 'gxqi':
                  currentVChartData[4].data.push({ label: currentLabel, value: device.value });
                  break;
                case 'gci':
                  currentVChartData[5].data.push({ label: currentLabel, value: device.value });
                  break;
                default:
                  break;
              }
            });
          });
          setAllLabel(currentAllLabel);
          setChartData(currentVChartData);
        });
      }
    },
    [collectRequestParams],
  );

  useEffect(() => {
    if (orderId && visible) {
      getChartData(orderId || '');
      const timer = setInterval(() => {
        getChartData(orderId || '');
      }, 5 * 60 * 1000);
      return () => {
        clearTimeout(timer);
      };
    } else {
      return;
    }
  }, [getChartData, orderId, visible]);

  const detailItems: DetailItem[] = useMemo(() => {
    const result: DetailItem[] = [];
    Object.keys(values).forEach((item) => {
      const label = columnstitle[item as keyof typeof columnstitle] || '';
      switch (item) {
        case 'chargingStrategy':
          result.push({
            label,
            field: item,
            format: (value) => ChargingStrategy[Number(value)]?.text || ('' as any),
          });
          break;
        case 'gumType':
          result.push({
            label,
            field: item,
            format: (value) => GunType[Number(value)]?.text || ('' as any),
          });
          break;
        case 'sourceType':
          result.push({
            label,
            field: item,
            format: (value) => SourceType[Number(value)]?.text || ('' as any),
          });
          break;
        case 'chargingStrategyParam': //根据充电策略动态改变单位
          result.push({
            label:
              label + ChargingStrategyParam[Number(values.chargingStrategy)]?.text || ('' as any),
            field: item,
          });
          break;
        default:
          if (label) {
            result.push({
              label,
              field: item,
            });
          }
      }
    });
    return result;
  }, [values]);

  return (
    <Modal
      width={900}
      title={formatMessage({ id: 'common.detail', defaultMessage: '查看详情' })}
      open={visible}
      destroyOnClose
      onCancel={handleCancel}
      footer={[
        <Button key="back" onClick={handleCancel}>
          {formatMessage({ id: 'common.close', defaultMessage: '关闭' })}
        </Button>,
      ]}
    >
      <Detail.DotLabel
        title={formatMessage({ id: 'device.basicInformation', defaultMessage: '基本信息' })}
      />
      <Detail data={values} items={detailItems} column={2} labelStyle={{ width: '120px' }} />
      <div style={{ margin: '24px 0' }}>
        <Detail.DotLabel
          title={formatMessage({ id: 'device.chargingCurve', defaultMessage: '充电曲线' })}
        />
      </div>

      <TypeChart
        allLabel={allLabel}
        chartRef={chartRef}
        type={chartTypeEnum.Label}
        option={option}
        style={{ height: '400px' }}
        data={ChartData}
      />
    </Modal>
  );
};
export default OrderCurve;
