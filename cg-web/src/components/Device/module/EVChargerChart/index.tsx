import moment from 'moment';
import { useRef, useEffect, useContext, useState } from 'react';
import TypeChart from '@/components/Chart/TypeChart';
import { chartTypeEnum } from '@/components/Chart/config';
import Detail from '@/components/Detail';
import { Voption, Coption } from './config';
import { formatMessage } from '@/utils';
import { getChargeDayCurve, getChargeTCurve } from '@/services/equipment';
import DeviceContext from '@/components/Device/Context/DeviceContext';
import { cloneDeep } from 'lodash';

const defaultVChartData = [
  {
    name: formatMessage({ id: 'device.maxAllowVoltage', defaultMessage: '最高允许充电电压' }),
    data: [],
  }, //最高允许充电电压
  {
    name: formatMessage({ id: 'device.maxAllowCurrent', defaultMessage: '最高允许充电电流' }),
    data: [],
  }, //最高允许充电电流

  { name: formatMessage({ id: 'device.demandVoltage', defaultMessage: '需求电压' }), data: [] }, //需求电压
  { name: formatMessage({ id: 'device.demandCurrent', defaultMessage: '需求电流' }), data: [] }, //需求电流

  {
    name: formatMessage({ id: 'device.chargeOutputVoltage', defaultMessage: '充电输出电压' }),
    data: [],
  }, //充电输出电压
  { name: formatMessage({ id: 'device.outputCurrent', defaultMessage: '充电输出电流' }), data: [] }, //充电输出电流

  {
    name: formatMessage({ id: 'device.bMSMeasureVoltage', defaultMessage: 'BMS测量电压' }),
    data: [],
  }, //BMS测量电压
  {
    name: formatMessage({ id: 'device.bMSMeasureCurrent', defaultMessage: 'BMS测量电流' }),
    data: [],
  }, //BMS测量电流
];
const defaultCChartData = [
  { name: formatMessage({ id: 'device.gunTemp', defaultMessage: '枪温度' }), data: [] }, //枪温度
  {
    name: formatMessage({ id: 'device.singleBatteryMaxtemp', defaultMessage: '单体电池最高温度' }),
    data: [],
  }, //单体电池最高温度
  {
    name: formatMessage({ id: 'device.singleBatteryMintemp', defaultMessage: '单体电池最低温度' }),
    data: [],
  }, //单体电池最低温度
  {
    name: formatMessage({ id: 'device.chargeGunAnodetemp', defaultMessage: '充电枪正极温度' }),
    data: [],
  }, //充电枪正极温度
  {
    name: formatMessage({ id: 'device.chargeGunCathodetemp', defaultMessage: '充电枪负极温度' }),
    data: [],
  }, //充电枪负极负温度
];
const EVChargerChart = () => {
  const chartRef = useRef() as any;
  const { data: deviceData } = useContext(DeviceContext);
  const [VChartData, setVChartData] = useState(defaultVChartData);
  const [CChartData, setCChartData] = useState(defaultCChartData);

  const getChartData = (deviceId: number | string) => {
    if (deviceId) {
      getChargeDayCurve({ deviceId }).then(({ data }) => {
        if (!data || !data.length) return;
        const currentVChartData = cloneDeep(defaultVChartData);
        data.forEach((item) => {
          if (!item.values || !item.values.length) return;
          const currentValue = item.values.map((i) => ({
            label: i.eventTs,
            value: Number(i.val),
          })) as never[];
          switch (item.key) {
            case 'gmaxyxcaU': //最高允许充电电压
              currentVChartData[0].data = currentValue;
              break;
            case 'gmaxyxcaA': //最高允许充电电流
              currentVChartData[1].data = currentValue;
              break;
            case 'gxqu': //需求电压
              currentVChartData[2].data = currentValue;
              break;
            case 'gxqi': //需求电流
              currentVChartData[3].data = currentValue;
              break;
            case 'gcu': //充电出电压
              currentVChartData[4].data = currentValue;
              break;
            case 'gci': //充电输出电流
              currentVChartData[5].data = currentValue;
              break;
            case 'gcut': //BMS测量电压
              currentVChartData[6].data = currentValue;
              break;
            case 'gcit': //BMS测量电流
              currentVChartData[7].data = currentValue;
              break;
            default:
          }
        });
        setVChartData(currentVChartData);
      });
      getChargeTCurve({ deviceId: deviceData?.deviceId }).then(({ data }) => {
        if (!data || !data.length) return;
        const currentCChartData = cloneDeep(defaultCChartData);
        data.forEach((item) => {
          if (!item.values || !item.values.length) return;
          const currentValue = item.values.map((i) => ({
            label: i.eventTs,
            value: i.val,
          })) as never[];
          switch (item.key) {
            case 'gdct': //枪温度、充电枪正极温度
              currentCChartData[0].data = currentValue;
              currentCChartData[3].data = currentValue;
              break;
            case 'ght': //单体电池最高温度
              currentCChartData[1].data = currentValue;
              break;
            case 'glt': //单体电池最低温度
              currentCChartData[2].data = currentValue;
              break;
            case 'gdct2': //充电枪负极负温度
              currentCChartData[4].data = currentValue;
              break;
            default:
          }
        });
        setCChartData(currentCChartData);
      });
    }
  };
  useEffect(() => {
    getChartData(deviceData?.deviceId || '');
    const timer = setInterval(() => {
      getChartData(deviceData?.deviceId || '');
    }, 5 * 60 * 1000);
    return () => {
      clearTimeout(timer);
    };
  }, [deviceData?.deviceId]);

  return (
    <div>
      <Detail.Label
        title={formatMessage({ id: 'device.todayCurve', defaultMessage: '今日曲线' })}
        className="mt16"
      />
      <Detail.Label
        title={formatMessage({
          id: 'device.voltageAndCurrentCurves',
          defaultMessage: '电压电流曲线',
        })}
        className="mt16"
      />
      <TypeChart
        type={chartTypeEnum.Day}
        step={1}
        chartRef={chartRef}
        date={moment()}
        option={Voption}
        style={{ height: '400px' }}
        data={VChartData}
      />
      <Detail.Label
        title={formatMessage({ id: 'device.tempCurve', defaultMessage: '温度曲线' })}
        className="mt16"
      />
      <TypeChart
        type={chartTypeEnum.Day}
        step={1}
        chartRef={chartRef}
        date={moment()}
        option={Coption}
        style={{ height: '400px' }}
        data={CChartData}
      />
    </div>
  );
};
export default EVChargerChart;
