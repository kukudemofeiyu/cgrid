/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-18 11:51:31
 * @LastEditTime: 2024-04-22 17:06:27
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Detail\Detail\index.tsx
 */
import React, { cloneElement, memo, useCallback, useMemo } from 'react';
import { Descriptions } from 'antd';
import type { DescriptionsProps } from 'antd';
import { isEmpty } from '@/utils';
import type { ProFormColumnsType } from '@ant-design/pro-components';
import styles from './index.less';
import MonetaryUnit from '@/components/MonetaryUnit';

export type EventType = (eventName?: string, eventData?: Record<string, any>) => void;

export type DetailItem = {
  className?: string;
  title?: React.ReactNode;
  label?: React.ReactNode;
  dataIndex?: any;
  field?: string;
  showPlaceholder?: boolean;
  valueInterceptor?: (value: any, data?: any) => any;
  format?: (value: any, data?: any, emit?: EventType) => React.ReactNode;
  span?: number;
  labelStyle?: React.CSSProperties;
  contentStyle?: React.CSSProperties;
  valueStyle?: React.CSSProperties;
  show?: boolean | ((value: any, data?: any) => boolean);
  showExtra?: boolean;
  unit?: React.ReactNode;
  extral?: React.ReactElement;
  custom?: React.ReactNode;
  [key: string]: any;
};

export type FormAndDetailType = ProFormColumnsType & DetailItem;

export type DetailProps = DescriptionsProps & {
  className?: string;
  items: DetailItem[];
  data: any;
  format?: (value: any, data?: any, emit?: EventType) => React.ReactNode;
  extral?: React.ReactElement;
  valueStyle?: React.CSSProperties;
  unitInLabel?: boolean;
  ellipsis?: boolean;
  showExtra?: boolean;
  onEvent?: EventType;
  disEmpty?: boolean;
};

const Detail: React.FC<DetailProps> = (props) => {
  const {
    className = '',
    items,
    column = 3,
    labelStyle = {},
    contentStyle = {},
    colon = true,
    format,
    extral,
    valueStyle = {},
    unitInLabel = false,
    ellipsis = true,
    showExtra,
    onEvent,
    disEmpty,
    ...restProps
  } = props;

  const data = useMemo(() => {
    return props?.data || {};
  }, [props?.data]);

  const emit = useCallback(
    (...params) => {
      onEvent?.(...params);
    },
    [onEvent],
  );

  const descriptionItems = useMemo(() => {
    const content: React.ReactNode[] = [];
    items.forEach((item) => {
      let fieldValue = data[(item?.field ?? item?.dataIndex) || ''];
      if (item?.valueInterceptor) {
        fieldValue = item?.valueInterceptor?.(fieldValue, data);
      }
      let show;
      if (typeof item.show == 'function') {
        show = item?.show?.(fieldValue, data);
      } else {
        show = item.show;
      }
      if (show !== false) {
        if (item.custom) {
          content.push(item.custom);
        } else {
          let extralNode = <></>;
          if (extral) {
            extralNode = cloneElement(extral, {
              onClick: () => extral.props?.onClick?.(item, fieldValue, data),
            });
          }
          const value =
            !isEmpty(fieldValue) || item.showPlaceholder === false
              ? item.format
                ? item.format(fieldValue ?? '', data, emit)
                : format
                ? format(fieldValue ?? '', data, emit)
                : fieldValue ?? ''
              : '--';
          content.push(
            <Descriptions.Item
              className={item.className || ''}
              label={
                ellipsis ? (
                  <>
                    <span className={styles.label} title={(item.label ?? item.title) as string}>
                      {item.label ?? item.title}
                    </span>
                    {unitInLabel &&
                      (item?.unit && (typeof item?.unit !== 'string' || item?.unit?.trim?.()) ? (
                        item?.unit === 1 ? (
                          <>
                            (<MonetaryUnit />)
                          </>
                        ) : (
                          `(${item?.unit})`
                        )
                      ) : (
                        ''
                      ))}
                  </>
                ) : (
                  <span title={(item.label ?? item.title) as string}>
                    {item.label ?? item.title}
                    {unitInLabel &&
                      (item?.unit && (typeof item?.unit !== 'string' || item?.unit?.trim?.()) ? (
                        item?.unit === 1 ? (
                          <>
                            (<MonetaryUnit />)
                          </>
                        ) : (
                          `(${item?.unit})`
                        )
                      ) : (
                        ''
                      ))}
                  </span>
                )
              }
              labelStyle={item.labelStyle}
              contentStyle={item.contentStyle}
              span={item.span || 1}
              key={item?.field ?? item?.dataIndex}
            >
              <div className="flex w-full detail-value">
                <span
                  className={styles.value}
                  style={item.valueStyle || valueStyle}
                  title={
                    ['function', 'object', 'symbol'].includes(typeof value) ? undefined : value
                  }
                >
                  {value}
                </span>
                {/* {!unitInLabel && <span>{item.unit || ''}</span>} */}
                {!unitInLabel && (
                  <span>
                    {item.unit === 1 ? (
                      <>
                        (<MonetaryUnit />)
                      </>
                    ) : (
                      item.unit
                    )}
                  </span>
                )}
                {item.extral}
                {showExtra !== false && item.showExtra !== false && extralNode}
              </div>
            </Descriptions.Item>,
          );
        }
      }
    });
    return content;
  }, [
    items,
    contentStyle,
    valueStyle,
    data,
    extral,
    unitInLabel,
    showExtra,
    format,
    ellipsis,
    emit,
  ]);

  const emptyColumn = useMemo(() => {
    if (typeof column === 'number') {
      return Array.from({ length: column }).map((_, index) => {
        return (
          <Descriptions.Item className={styles.empty} key={index}>
            <span></span>
          </Descriptions.Item>
        );
      });
    }
  }, [column]);

  return (
    <Descriptions
      className={`${styles.detail} ${className}`}
      column={column}
      labelStyle={labelStyle}
      contentStyle={contentStyle}
      colon={colon}
      {...restProps}
    >
      {!disEmpty && emptyColumn}
      {descriptionItems}
    </Descriptions>
  );
};

export default memo(Detail);
