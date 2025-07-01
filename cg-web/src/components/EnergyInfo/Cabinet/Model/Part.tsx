/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-01-29 10:50:47
 * @LastEditTime: 2024-07-01 10:17:46
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Cabinet\Model\Part.tsx
 */

import React, { useCallback, useContext, useMemo } from 'react';
import { ConfigType, ProductIdMapType } from '../type';
import { DeviceTypeEnum } from '@/utils/dictionary';
import { EnergySourceEnum } from '../..';
import { useDeviceModel, useSubscribe } from '@/hooks';
import { message } from 'antd';
import { useHistory } from 'umi';
import { arrayToMap, formatMessage, formatModelValue, getPropsFromTree, isEmpty } from '@/utils';
import { merge } from 'lodash';
import styles from '../../index.less';
import Detail, { DetailItem } from '@/components/Detail';
import DeviceContext from '@/components/Device/Context/DeviceContext';
import { OnlineStatusEnum } from '@/utils/dict';

type PartType = {
  config: ConfigType;
  deviceId?: string;
  productId?: DeviceTypeEnum;
  productIdMap?: ProductIdMapType;
  source?: EnergySourceEnum;
  detailProps?: DetailItem;
};

const Part: React.FC<PartType> = (props) => {
  const { config, deviceId, productId, productIdMap, source, detailProps } = props;

  const { onSelect, treeData } = useContext(DeviceContext);

  const networkStatus = useMemo(() => {
    return getPropsFromTree(
      treeData,
      'networkStatus',
      'children',
      (item) => item.deviceId == deviceId,
    )?.[0];
  }, [deviceId, treeData]);

  const dataDeviceIds = useMemo(() => {
    const result: string[] = [];
    if (deviceId) {
      result.push(deviceId);
    }
    config?.dataProductTypeIds?.forEach?.((item) => {
      const id = productIdMap?.[item]?.deviceId;
      if (id) {
        result.push(id);
      }
    });
    const productIdDeviceIds = arrayToMap(
      Object.values(productIdMap || {}),
      'productId',
      'deviceId',
    );
    config?.dataProductIds?.forEach?.((item) => {
      if (productIdDeviceIds[item]) {
        result.push(productIdDeviceIds[item]);
      }
    });
    return result;
  }, [deviceId, config, productIdMap]);

  const productIds = useMemo(() => {
    const result: DeviceTypeEnum[] = [];
    if (productId) {
      result.push(productId);
    }
    config?.dataProductTypeIds?.forEach?.((item) => {
      const id = productIdMap?.[item]?.productId;
      if (id) {
        result.push(id);
      }
    });
    return result;
  }, [productId, productIdMap, config]);

  const history = useHistory();
  const realTimeData = useSubscribe(dataDeviceIds, true);
  const { modelMap: modelMap } = useDeviceModel({
    productId: productIds,
    isGroup: true,
  });

  const onMoreClick = useCallback(() => {
    if (deviceId) {
      if (onSelect) {
        onSelect?.(deviceId);
      } else {
        history.push({
          pathname:
            source === EnergySourceEnum.SiteMonitor
              ? '/site-monitor/device-detail'
              : '/equipment/device-detail',
          search: `?id=${deviceId}`,
        });
      }
    } else {
      message.error(formatMessage({ id: 'common.noData', defaultMessage: '暂无数据' }));
    }
  }, [deviceId, source]);

  const mergedConfig = useMemo(() => {
    const result = merge({}, config || {});
    result?.data?.forEach?.((item) => {
      const showUnit = !!item.label;
      item.format =
        item.customFormat ||
        ((value) => formatModelValue(value, modelMap?.[item?.field || ''], showUnit));
      if (!item.label) {
        item.label = modelMap?.[item?.field || '']?.name;
        item.unit = modelMap?.[item?.field || '']?.specs?.unit;
      }
    });
    return result;
  }, [config, modelMap]);

  return (
    <>
      <div
        key={mergedConfig.label}
        className={styles.unit}
        style={{
          backgroundImage: `url(${mergedConfig.icon})`,
          ...mergedConfig.position,
        }}
      >
        <img className={styles.line} src={mergedConfig.line} style={mergedConfig.linePosition} />
        {mergedConfig.showLabel === false ? (
          <></>
        ) : (
          <label title={mergedConfig.label} className={`ellipsis ${styles.unitTitle}`}>
            {mergedConfig.label}
          </label>
        )}
        <Detail
          className={`${styles.detail} ${
            isEmpty(networkStatus)
              ? ''
              : (networkStatus as any) == OnlineStatusEnum.Online
              ? 'device-online'
              : 'device-offline'
          }`}
          items={mergedConfig.data}
          data={{ ...realTimeData, productId }}
          column={1}
          labelStyle={{
            maxWidth: '92px',
          }}
          unitInLabel
          {...detailProps}
        />
        {mergedConfig.showLabel === false ? (
          <></>
        ) : (
          <span className={`cl-primary cursor ${styles.field}`} onClick={onMoreClick}>
            {formatMessage({ id: 'common.more', defaultMessage: '更多' })}
            {'>'}
          </span>
        )}
      </div>
    </>
  );
};

export default Part;
