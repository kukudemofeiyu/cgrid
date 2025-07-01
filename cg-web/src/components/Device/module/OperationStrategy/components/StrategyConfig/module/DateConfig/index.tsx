import React, { useState, useCallback, useEffect, useMemo } from 'react';
import { formatMessage } from '@/utils';
import moment from 'moment';
import type { RangePickerProps } from 'antd/es/date-picker/generatePicker';
import {
  DatePicker,
  Row,
  Col,
  Card,
  Empty,
  Pagination,
  Button,
  Space,
  Select,
  message,
  Modal,
  Spin,
} from 'antd';
import styles from '../../index.less';
import { CloseOutlined } from '@ant-design/icons';
import { TimeEnum, schemaColumns, dayOfWeekMap } from '../../config';
import { cloneDeep } from 'lodash';
import SchemaForm from '@/components/SchemaForm';
import { useBoolean } from 'ahooks';
import {
  dayStrategyPage,
  weekStrategyList,
  monthStrategyList,
  addDayStrategy,
  templateList,
  updataDayStrategy,
  updataWeekStrategy,
  updataMonthStrategy,
  deleteDayStrategy,
} from '../../service';
import { FormTypeEnum } from '@/components/SchemaForm';
import { useRequest } from 'umi';

type RangePickerValue = RangePickerProps<moment.Moment>['value'];
const { RangePicker } = DatePicker;

type DateConfigProps = {
  deviceId?: string | number;
  timeType: TimeEnum;
  passEditAuth?: boolean;
};

const DateConfig: React.FC<DateConfigProps> = (props) => {
  const { deviceId, timeType, passEditAuth } = props;
  const [rangeValue, setRangeValue] = useState<RangePickerValue>();
  const [loading, setLoading] = useState(false);
  const [open, { set, setTrue }] = useBoolean(false);
  const [formType, setFormType] = useState<FormTypeEnum>(FormTypeEnum.Add);
  const [pageInfo, setPageInfo] = useState({ current: 1, pageSize: 10 });
  const [strategyList, setStrategyList] = useState([]);
  const [total, setTotal] = useState<number>(10);

  const { data: templateListData, run } = useRequest(templateList, {
    manual: true,
  });

  useEffect(() => {
    run({ deviceId, dimension: timeType });
  }, [deviceId, run, timeType, open]);

  const getDayStrategyPage = useCallback(() => {
    const params = {
      deviceId,
      ...pageInfo,
      startTime: rangeValue?.[0]?.format('YYYY-MM-DD'),
      endTime: rangeValue?.[1]?.format('YYYY-MM-DD'),
    };
    dayStrategyPage(params).then((res: any) => {
      setLoading(false);
      if (res.code === 200) {
        const resData = res.data;
        setStrategyList(resData.list);
        setTotal(resData.total);
      }
    });
  }, [deviceId, pageInfo, rangeValue]);

  const getWeekStrategyList = useCallback(() => {
    weekStrategyList({
      deviceId,
    }).then((res: any) => {
      if (res.code === 200) {
        setLoading(false);
        setStrategyList(res.data);
      }
    });
  }, [deviceId]);

  const getMonthStrategyList = useCallback(() => {
    monthStrategyList({
      deviceId,
    }).then((res: any) => {
      if (res.code === 200) {
        setLoading(false);
        setStrategyList(res.data);
      }
    });
  }, [deviceId]);

  const getData = useCallback(() => {
    setLoading(true);
    setStrategyList([]);
    if (timeType === TimeEnum.DAY) {
      getDayStrategyPage();
    } else if (timeType === TimeEnum.WEEK) {
      getWeekStrategyList();
    } else {
      getMonthStrategyList();
    }
  }, [timeType, getDayStrategyPage, getWeekStrategyList, getMonthStrategyList]);

  useEffect(() => {
    getData();
  }, [getData]);

  const onPaginationChange = useCallback(
    (page: number, pageSize: number) => {
      const curPageInfo = cloneDeep(pageInfo);
      curPageInfo.current = page;
      curPageInfo.pageSize = pageSize;
      setPageInfo(curPageInfo);
    },
    [pageInfo],
  );

  const onBeforeSubmit = useCallback(
    (data) => {
      data.deviceId = deviceId;
      return data;
    },
    [deviceId],
  );

  const onPickerChange = useCallback((value: RangePickerValue) => {
    setRangeValue(value);
  }, []);

  const addDayStrategyFn = useCallback(() => {
    setFormType(FormTypeEnum.Add);
    setTrue();
  }, [setTrue]);

  const columns = useMemo(() => {
    const templateOptions = templateListData?.map?.((item: any) => {
      return {
        label: item.name,
        value: item.id,
      };
    });
    return schemaColumns(templateOptions);
  }, [templateListData]);

  const onTemplateSelect = useCallback(
    (e: any, item: any) => {
      item.templateId = e;
      let api: any = '';
      if (timeType == TimeEnum.DAY) {
        api = updataDayStrategy;
      } else if (timeType == TimeEnum.WEEK) {
        item.deviceId = deviceId;
        api = updataWeekStrategy;
      } else {
        item.deviceId = deviceId;
        api = updataMonthStrategy;
      }
      api(item).then((res: any) => {
        if (res.code === 200) {
          message.success(formatMessage({ id: 'common.editSuccess', defaultMessage: '修改成功' }));
          getData();
        }
      });
    },
    [deviceId, getData, timeType],
  );

  const onTemplateDelete = useCallback(
    (id) => {
      Modal.confirm({
        title: formatMessage({ id: 'common.delete', defaultMessage: '删除' }),
        content: formatMessage({
          id: 'dataManage.1094',
          defaultMessage: '你确定要删除吗？删除之后无法恢复！',
        }),
        okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
        cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
        onOk: () => {
          deleteDayStrategy({ id }).then((res) => {
            if (res.code === 200) {
              message.success(formatMessage({ id: 'common.del', defaultMessage: '删除成功' }));
              getData();
            }
          });
        },
      });
    },
    [getData],
  );

  const getTitle = useCallback(
    (item: any) => {
      if (timeType == TimeEnum.DAY) {
        return `${item.startTime}-${item.endTime}`;
      } else if (timeType == TimeEnum.WEEK) {
        return dayOfWeekMap[item.dayOfWeek as keyof typeof dayOfWeekMap];
      } else {
        return item.day + formatMessage({ id: 'common.1015', defaultMessage: '号' });
      }
    },
    [timeType],
  );
  const curSpan = useMemo(() => {
    return timeType == TimeEnum.DAY
      ? { lg: 12, xl: 8, xxl: 6 }
      : timeType == TimeEnum.WEEK
      ? { xl: 6, xxl: 4 }
      : { xl: 4, xxl: 3 };
  }, [timeType]);

  const dateItem = useMemo(() => {
    return (
      <Spin spinning={loading}>
        <div className={styles.container}>
          <Row gutter={[16, 24]} className="mt24">
            {strategyList?.length ? (
              strategyList.map((item: any) => {
                return (
                  <Col key={item.id || item.dayOfWeek || item.day} {...curSpan}>
                    <Card
                      className={`${styles.card} ${timeType == TimeEnum.DAY ? styles.dayCard : ''}`}
                      title={getTitle(item)}
                      extra={
                        timeType == TimeEnum.DAY && passEditAuth ? (
                          <CloseOutlined onClick={() => onTemplateDelete(item.id)} />
                        ) : (
                          <></>
                        )
                      }
                    >
                      <Select
                        style={{ width: '100%' }}
                        value={item.templateId || ''}
                        allowClear={timeType !== TimeEnum.DAY}
                        disabled={!passEditAuth}
                        onChange={(e) => onTemplateSelect(e, item)}
                        options={templateListData}
                        onFocus={() => run({ deviceId, dimension: timeType })}
                        fieldNames={{ label: 'name', value: 'id' }}
                      />
                    </Card>
                  </Col>
                );
              })
            ) : (
              <Col span={24}>{!loading && <Empty />}</Col>
            )}
          </Row>
        </div>
      </Spin>
    );
  }, [
    curSpan,
    deviceId,
    getTitle,
    loading,
    onTemplateDelete,
    onTemplateSelect,
    passEditAuth,
    run,
    strategyList,
    templateListData,
    timeType,
  ]);

  return (
    <div>
      {timeType === TimeEnum.DAY ? (
        <div className={styles.header}>
          <Space>
            {formatMessage({ id: 'common.time', defaultMessage: '时间' })}：
            <RangePicker value={rangeValue} onChange={onPickerChange} />
          </Space>
          <Button type="primary" onClick={addDayStrategyFn} disabled={!passEditAuth}>
            {formatMessage({ id: 'common.add', defaultMessage: '新增' })}
          </Button>
        </div>
      ) : (
        <></>
      )}
      {dateItem}
      {timeType === TimeEnum.DAY ? (
        <Pagination
          style={{ textAlign: 'end' }}
          onChange={onPaginationChange}
          showSizeChanger
          showQuickJumper
          current={pageInfo.current}
          total={total}
          pageSize={pageInfo.pageSize}
        />
      ) : (
        <></>
      )}
      <SchemaForm
        width={552}
        onOpenChange={set}
        open={open}
        type={formType}
        columns={columns}
        addData={addDayStrategy}
        editData={updataDayStrategy}
        beforeSubmit={onBeforeSubmit}
        onSuccess={() => getData() as any}
        grid={true}
      />
    </div>
  );
};

export default DateConfig;
