import { Modal, message, Row, Col, Space, Empty, Switch, DatePicker } from 'antd';
import { useRef, useState, useContext, useEffect, useCallback } from 'react';
import { formatMessage, getLocale } from '@/utils';
import YTProTable from '@/components/ProTable';
import type { ActionType } from '@ant-design/pro-components';
import type { ConfigDataType, Authority } from './data';
import { columns } from './config';
import type { RangePickerProps } from 'antd/es/date-picker';
import { ProConfigProvider } from '@ant-design/pro-components';
import { YTDateRangeValueTypeMap } from '@/components/YTDateRange';
import {
  getDeviceReportLog,
  exportDeviceReportLog,
  setPostDeviceLogTime,
} from '@/services/equipment';
import DeviceContext from '@/components/Device/Context/DeviceContext';
import { useSubscribe, useAuthority } from '@/hooks';
import styles from './index.less';
import moment from 'moment';
import type { Moment } from 'moment';

const DeviceLogReport: React.FC = () => {
  const [reportStatus, setReportStatus] = useState<0 | 1>(0);
  const { data: deviceData } = useContext(DeviceContext);
  const realTimeData = useSubscribe(deviceData?.deviceId, true);
  const actionRef = useRef<ActionType>();
  const [authority, setAuthority] = useState<Authority>();
  const [curEndTime, setCurEndTime] = useState<Moment | null>(); //编辑时间

  const { authorityMap } = useAuthority([
    'deviceManage:detail:debug:report:page',
    'deviceManage:detail:debug:report:download',
    'deviceManage:detail:debug:report:statusControl',
    'deviceManage:detail:debug:report:duration',
  ]);

  useEffect(() => {
    setReportStatus(realTimeData?.reportStatus || 0);
  }, [realTimeData]);

  useEffect(() => {
    setAuthority({
      page: Boolean(authorityMap.get('deviceManage:detail:debug:report:page')),
      download: Boolean(authorityMap.get('deviceManage:detail:debug:report:download')),
      statusControl: Boolean(authorityMap.get('deviceManage:detail:debug:report:statusControl')),
      duration: Boolean(authorityMap.get('deviceManage:detail:debug:report:duration')),
    });
  }, [authorityMap]);

  const handleRequest = (params: any) => {
    return getDeviceReportLog({
      ...params,
    });
  };

  const requestExport = useCallback(
    (params) => {
      const query: any = { deviceId: deviceData?.deviceId };
      if (params?.time?.length) {
        query.startTime = params.time[0];
        query.endTime = params.time[1];
      }
      return exportDeviceReportLog(query);
    },
    [deviceData?.deviceId],
  );

  const onLogRecord = (value: boolean) => {
    const localLog = value ? 1 : 0;
    if (value && !curEndTime) {
      message.info(formatMessage({ id: 'device.1048', defaultMessage: '请先设置上报到期时间' }));
      return;
    }
    const title = value
      ? formatMessage({ id: 'device.1030', defaultMessage: '开启' })
      : formatMessage({ id: 'device.1031', defaultMessage: '关闭' });
    Modal.confirm({
      title,
      content: `${title}${formatMessage({ id: 'device.1042', defaultMessage: '上报状态' })}？`,
      okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
      cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
      onOk: () => {
        setPostDeviceLogTime({
          deviceId: deviceData?.deviceId,
          reportStatus: localLog,
          postEndTime:
            curEndTime && value ? moment(curEndTime).format(getLocale().dateTimeFormat) : null,
        }).then((res: any) => {
          if (res.code == 200) {
            setReportStatus(localLog);
            message.success(
              `${title}${formatMessage({ id: 'device.1027', defaultMessage: '成功' })}`,
            );
          }
        });
      },
    });
  };

  const onDatePickerChange = useCallback(
    (date: Moment | null) => {
      setCurEndTime(date);
      if (date && reportStatus) {
        setPostDeviceLogTime({
          deviceId: deviceData?.deviceId,
          reportStatus,
          postEndTime: moment(date).format(getLocale().dateTimeFormat),
        });
      }
    },
    [deviceData?.deviceId, reportStatus],
  );

  const disabledDate: RangePickerProps['disabledDate'] = (current) => {
    return current && !current.isSame(moment(), 'day');
  };
  const range = (start: number, end: number) => {
    const result = [];
    for (let i = start; i < end; i++) {
      result.push(i);
    }
    return result;
  };

  const disabledDateTime = (current = moment()) => {
    const curTime = moment();
    const curHour = curTime.hour();
    const curMinute = curTime.minute();
    const isMinHour = curTime.isSame(current, 'hour');

    return {
      disabledHours: () => range(0, curHour).concat(range(curHour + 4, 24)),
      disabledMinutes: () => isMinHour && range(0, curMinute),
    };
  };

  return authority?.page ? (
    <div>
      <Row gutter={[8, 24]}>
        <Col span={24}>
          <Space size="middle">
            <span className={styles.logTitle}>
              {formatMessage({ id: 'device.1042', defaultMessage: '上报状态' })}
            </span>
            <span>
              {realTimeData.localLog
                ? formatMessage({ id: 'device.1030', defaultMessage: '开启' })
                : formatMessage({ id: 'device.1031', defaultMessage: '关闭' })}
            </span>
          </Space>
        </Col>
        {authority?.duration && (
          <Col span={12}>
            <Space size="middle">
              <span className={styles.logTitle}>
                {formatMessage({ id: 'device.1044', defaultMessage: '上报到期时间' })}
              </span>
              <DatePicker
                format={getLocale().dateTimeFormat}
                showTime
                onChange={onDatePickerChange}
                showNow={false}
                defaultValue={realTimeData.reportExpirationTim}
                disabledDate={disabledDate}
                disabledTime={disabledDateTime as any}
              />
            </Space>
          </Col>
        )}

        {authority?.statusControl && (
          <Col span={12}>
            <Space size="middle">
              <span className={styles.logTitle}>
                {formatMessage({ id: 'device.1043', defaultMessage: '上报状态控制' })}
              </span>
              <Switch
                onChange={onLogRecord}
                checked={Boolean(reportStatus)}
                checkedChildren={formatMessage({ id: 'device.1030', defaultMessage: '开启' })}
                unCheckedChildren={formatMessage({ id: 'device.1031', defaultMessage: '关闭' })}
              />
            </Space>
          </Col>
        )}
      </Row>
      <ProConfigProvider
        valueTypeMap={{
          ...YTDateRangeValueTypeMap,
        }}
      >
        <YTProTable<ConfigDataType>
          actionRef={actionRef}
          columns={columns as any}
          className={styles.logTable}
          params={{ deviceId: deviceData?.deviceId }}
          request={handleRequest}
          scroll={{ y: 'auto' }}
          toolBarRenderOptions={{
            add: {
              show: false,
            },
            export: {
              show: authority?.download,
              requestExport: requestExport,
              getExportName: () =>
                `${formatMessage({
                  id: 'device.1039',
                  defaultMessage: '上报日志',
                })}~${deviceData?.deviceId}`,
              fileType: '.txt',
            },
          }}
        />
      </ProConfigProvider>
    </div>
  ) : (
    <Empty />
  );
};
export default DeviceLogReport;
