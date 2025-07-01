import React, { useCallback, useEffect, useMemo, useState, useRef } from 'react';
import { Card, Row, Col, Calendar, List, Radio, Divider } from 'antd';
import Detail from '@/components/Detail';
import { formatMessage, getLocale } from '@/utils';
import type { Moment } from 'moment';
import moment from 'moment';
import { barConfig, getoperatingMode, detailItem } from './config';
import TypeChart from '@/components/Chart/TypeChart';
import { chartTypeEnum } from '@/components/Chart/config';
import type { TypeChartDataType } from '@/components/Chart/TypeChart';
import { useRequest } from 'umi';
import { strategyList, strategyCurve } from '../../service';
import styles from './index.less';
import type { EChartsInstance } from 'echarts-for-react';
import { cloneDeep } from 'lodash';
import { isEmpty } from '@/utils';

const { monthDateFormat, hourMinuteFormat } = getLocale();
const dateFormat: string = `${monthDateFormat} ${hourMinuteFormat}`;

type StrategyDetailProps = {
  deviceId?: string | number;
};

const BarData: TypeChartDataType[] = [
  {
    name: formatMessage({ id: 'device.1079', defaultMessage: '策略曲线' }),
    color: '#FFC542',
    data: [],
  },
  {
    name: formatMessage({ id: 'device.1080', defaultMessage: '实际曲线' }),
    color: '#3DD598',
    data: [],
  },
];
const StrategyDetail: React.FC<StrategyDetailProps> = (props) => {
  const { deviceId } = props;
  const [panelValue, setPanelValue] = useState<Moment>(moment());
  const [deviceOption, setDeviceOption] = useState(barConfig());
  const [barData, setBarData] = useState<TypeChartDataType[]>([]);
  const [runDetailId, setRunDetailId] = useState<number>(0);

  const chartRef = useRef<EChartsInstance>();

  const { data: strategyListData, run } = useRequest(strategyList, {
    manual: true,
  });
  const { data: strategyCurveData, run: runStrategyCurve } = useRequest(strategyCurve, {
    manual: true,
  });
  useEffect(() => {
    run({ deviceId, time: panelValue.format('YYYY-MM') });
    runStrategyCurve({ deviceId, time: panelValue.format('YYYY-MM-DD') });
  }, [deviceId, panelValue, run, runStrategyCurve]);

  const onPanelChange = (value: Moment) => {
    setPanelValue(value);
    setRunDetailId(0);
  };

  useEffect(() => {
    chartRef?.current?.getEchartsInstance()?.clear?.();
    const curBarData = cloneDeep(BarData);
    const curAllLabel: string[] = [];
    const { plans = [], realities = [] } = strategyCurveData || {};
    if (plans.length) {
      curBarData[0].data = plans.map((item: any) => {
        const label = moment(item.eventTs).format(dateFormat);
        curAllLabel.push(label);
        return {
          label,
          value: Number(item.value) || '-',
        };
      });
    }
    if (realities.length) {
      curBarData[1].data = plans.map((item: any) => {
        return {
          label: moment(item.eventTs).format(dateFormat),
          value: Number(item.value) || '-',
        };
      });
    }
    if (panelValue.isAfter(moment())) {
      curBarData.pop();
    }
    setDeviceOption(barConfig(curBarData));
    setBarData(curBarData);
  }, [strategyCurveData, panelValue]);

  const paramsDetail = useMemo(() => {
    return strategyListData?.filter((item: any) => {
      return item.day == panelValue.format('YYYY-MM-DD');
    })[0];
  }, [strategyListData, panelValue]);
  const headerRender = useCallback(
    ({ onChange }) => {
      const onRadioChange = (e: any) => {
        let curValue = e.target.value;
        if (curValue == 1) {
          curValue = moment().subtract(1, 'months').startOf('month');
        } else if (curValue == 2) {
          curValue = moment();
        } else {
          curValue = moment().add(1, 'months').startOf('month');
        }
        setPanelValue(curValue);
        onChange(curValue);
      };

      return (
        <Row>
          <Col span={12}>{panelValue.format('YYYY年MM月DD日')}</Col>
          <Col span={12} style={{ textAlign: 'right' }}>
            <Radio.Group onChange={onRadioChange} optionType="button">
              <Radio.Button value={1}>
                {formatMessage({ id: 'device.1071', defaultMessage: '上个月' })}
              </Radio.Button>
              <Radio.Button value={2}>
                {formatMessage({ id: 'device.1072', defaultMessage: '今天' })}
              </Radio.Button>
              <Radio.Button value={3}>
                {formatMessage({ id: 'device.1073', defaultMessage: '下个月' })}
              </Radio.Button>
            </Radio.Group>
          </Col>
          <Col span={24}>
            <Divider />
          </Col>
        </Row>
      );
    },
    [panelValue],
  );

  const dateCellRender = useCallback(
    (value: Moment) => {
      return strategyListData?.map((item: any) => {
        if (item.day == moment(value).format('YYYY-MM-DD')) {
          return (
            <div key={item.templateId} className={styles.cellRender} title={item.templateName}>
              {item.templateName}
            </div>
          );
        }
        return <></>;
      });
    },
    [strategyListData],
  );

  const calculateMax = useMemo(() => {
    return !barData?.[0]?.data?.length && !barData?.[1]?.data?.length;
  }, [barData]);

  return (
    <>
      <Row gutter={[8, 24]}>
        <Col span={12}>
          <Card className={styles.card}>
            <Detail.Label
              showLine={false}
              className="mb14"
              title={formatMessage({ id: 'device.1067', defaultMessage: '策略日历' })}
            />
            <Calendar
              className={styles.calendar}
              headerRender={headerRender}
              dateCellRender={dateCellRender}
              value={panelValue}
              onChange={onPanelChange}
            />
          </Card>
        </Col>
        <Col span={12}>
          <Row gutter={[24, 8]} style={{ height: '100%' }}>
            <Col span={24}>
              <Card className={styles.card}>
                <Row>
                  <Col span={10}>
                    <Detail.Label
                      showLine={false}
                      className="mb14"
                      title={formatMessage({ id: 'device.1068', defaultMessage: '运行详情' })}
                    />
                    <List
                      style={{ height: '246px', overflow: 'auto' }}
                      dataSource={paramsDetail?.times || []}
                      renderItem={(item: any, index) => (
                        <Row
                          className={styles.listItem}
                          style={{ borderColor: runDetailId == index ? '#007dff' : '#f3f4f7' }}
                          onClick={() => setRunDetailId(index)}
                        >
                          <Col span={10}>
                            {item.startTime}-{item.endTime}
                          </Col>
                          <Col span={14} className={styles.itemName}>
                            {getoperatingMode(item.systemOperatingMode)}
                          </Col>
                        </Row>
                      )}
                    />
                  </Col>
                  <Col span={2} />
                  <Col span={12}>
                    <Detail.Label
                      showLine={false}
                      className="mb14"
                      title={formatMessage({ id: 'device.1069', defaultMessage: '参数详情' })}
                    />
                    {!isEmpty(paramsDetail?.times?.[runDetailId]?.systemOperatingMode) ? (
                      <Detail
                        column={2}
                        items={detailItem(paramsDetail?.times?.[runDetailId]?.systemOperatingMode)}
                        data={{
                          ...paramsDetail?.times?.[runDetailId]?.config,
                          systemOperatingMode:
                            paramsDetail?.times?.[runDetailId]?.systemOperatingMode,
                        }}
                      />
                    ) : (
                      <List />
                    )}
                  </Col>
                </Row>
              </Card>
            </Col>
            <Col span={24}>
              <Card className={styles.card}>
                <Detail.Label
                  showLine={false}
                  className="mb14"
                  title={formatMessage({ id: 'device.1070', defaultMessage: '策略计划' })}
                />
                <TypeChart
                  key="device"
                  chartRef={chartRef}
                  calculateMax={calculateMax}
                  style={{ height: '275px' }}
                  option={deviceOption}
                  type={chartTypeEnum.Day}
                  data={barData}
                  isZoom={true}
                />
              </Card>
            </Col>
          </Row>
        </Col>
      </Row>
    </>
  );
};

export default StrategyDetail;
