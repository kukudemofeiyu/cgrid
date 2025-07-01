/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-15 14:50:06
 * @LastEditTime: 2024-12-31 15:39:41
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/CollectionModal/index.tsx
 */
import React, { useCallback, useEffect, useMemo, useRef, useState } from 'react';
import { Modal } from 'antd';
import { ProFormColumnsType } from '@ant-design/pro-components';
import { CollectionSearchType } from '@/services/data';
import moment, { Moment } from 'moment';
import SchemaForm from '@/components/SchemaForm';
import { ProFormInstance } from '@ant-design/pro-components';
import { DeviceModelTypeEnum, formatMessage } from '@/utils';
import { parseToObj } from '@/utils';
import CollectionChart from './CollectionChart';
import { CollectionModalType, columns } from './helper';
import { useBoolean } from 'ahooks';
import { formatData } from '../ProTable/helper';
type Searchtype = CollectionSearchType & {
  date: string[];
  aggregationPeriod: string;
  polymerizationType: string;
  breakConnect: boolean;
  extralData: any;
};

const CollectionModal: React.FC<Omit<CollectionModalType, 'date'>> = (props) => {
  const { deviceId, collection, model, title, open, ...restProps } = props;
  const formRef = useRef<ProFormInstance>(null);
  const [searchParams, setsearchParams] = useState<Searchtype>();
  const [loading, { set, setTrue }] = useBoolean(false);

  const modelData = useMemo(() => {
    const enumObj: any = parseToObj(model?.specs);
    let enumKeys: string[] = [];
    if (model?.type === DeviceModelTypeEnum.Enum) {
      enumKeys = ['wahaha'].concat(Object.keys(enumObj));
    }
    return {
      type: model?.type,
      keys: enumKeys,
      data: enumObj,
      unit: enumObj?.unit,
    };
  }, [model]);

  const onFinish = useCallback((formData: Searchtype) => {
    formatData(formData, columns);
    formData.aggregationPeriod = formData?.extralData?.timeBucket;
    formData.polymerizationType = formData?.extralData?.polymerizationType;
    formData.breakConnect = formData?.extralData?.breakConnect;
    setsearchParams(formData);
    setTrue();
    return Promise.resolve(true);
  }, []);

  const onLoadingChange = useCallback((value: boolean) => {
    set(value);
  }, []);

  useEffect(() => {
    if (open && deviceId) {
      formRef?.current?.submit?.();
    }
  }, [deviceId, collection, open]);

  return (
    <>
      <Modal
        width="1100px"
        footer={false}
        title={title + (modelData.unit ? `（${modelData.unit}）` : '')}
        open={open}
        maskClosable={false}
        centered
        {...restProps}
      >
        <SchemaForm<Searchtype>
          className="p0 mb24"
          formRef={formRef}
          columns={columns}
          layout="inline"
          layoutType="QueryFilter"
          onFinish={onFinish}
          onReset={onFinish}
          loading={loading}
          title={undefined}
        />
        <CollectionChart
          title={title}
          deviceId={deviceId}
          collection={collection}
          model={model}
          searchParams={searchParams}
          onLoadingChange={onLoadingChange}
        />
      </Modal>
    </>
  );
};

export default CollectionModal;
