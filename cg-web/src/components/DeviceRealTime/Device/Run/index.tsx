/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-08-10 09:34:45
 * @LastEditTime: 2024-09-26 10:51:20
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\Device\Run\index.tsx
 */
import React, { useState, useCallback, useEffect, Suspense, lazy, useContext } from 'react';
import { Spin, Tabs, TabsProps } from 'antd';
import Detail, { DetailItem, GroupItem } from '@/components/Detail';
import Button from '@/components/CollectionModal/Button';
import { formatModelValue, isEmpty, parseToArray } from '@/utils';
import { DeviceModelDataType, DeviceModelType, DevicePropsType } from '@/types/device';
import { DeviceModelTypeEnum } from '@/utils';
import Empty from 'antd/es/empty';
import DeviceContext from '@/components/Device/Context/DeviceContext';

const getShowExtral = (type?: DeviceModelTypeEnum) => {
  return !(
    [DeviceModelTypeEnum.Array, DeviceModelTypeEnum.Struct, DeviceModelTypeEnum.String] as any
  ).includes(type);
};

export type RunProps = {
  deviceId?: string;
  realTimeData?: Record<string, any>;
  groupData?: DeviceModelDataType;
  modelMap?: Record<string, DeviceModelType>;
};

const Run: React.FC<RunProps> = (props) => {
  const { deviceId, realTimeData, groupData, modelMap } = props;
  const { data: deviceData } = useContext(DeviceContext);

  const [detailGroup, setDetailGroup] = useState<GroupItem[]>([]);
  const [collectionInfo, setCollectionInfo] = useState({
    title: '',
    collection: '',
  });

  const onClick = useCallback((item: DetailItem) => {
    if (item.field) {
      setCollectionInfo({
        title: item.label as any,
        collection: item.field,
      });
    }
  }, []);

  const extral = (
    <Button
      deviceId={deviceId}
      title={collectionInfo.title}
      collection={collectionInfo.collection}
      model={modelMap?.[collectionInfo.collection]}
      onClick={onClick}
    />
  );

  const getDetailByProps = useCallback(
    (data: DevicePropsType[], parentField = '') => {
      const items: DetailItem[] = [];
      const tabItems: TabsProps['items'] = [];
      data?.forEach?.((item) => {
        const field = parentField ? parentField + '.' + item?.id : item?.id;
        if (item?.dataType?.type == DeviceModelTypeEnum.Struct) {
          const child = parseToArray(item?.dataType?.specs);
          const result = getDetailByProps(child, field);
          tabItems.push({
            key: field || Math.random() + '',
            label: item?.name,
            children: (
              <>
                {result.items?.length ? (
                  <Detail
                    items={result.items}
                    data={realTimeData}
                    extral={extral}
                    colon={false}
                    labelStyle={{ width: 140 }}
                  />
                ) : (
                  <></>
                )}
                {result.tabItems?.length ? <Tabs items={result.tabItems} /> : <></>}
              </>
            ),
          });
        } else {
          items.push({
            label: item?.name,
            field: field || Math.random() + '',
            showPlaceholder: false,
            format: (value: string, allData) =>
              formatModelValue(
                item?.sourceId && !isEmpty(allData[item?.sourceId])
                  ? allData[item?.sourceId]
                  : value,
                modelMap?.[field || ''] || {},
              ),
            showExtra: getShowExtral(modelMap?.[field || '']?.type) && item?.showHistory !== false,
          });
        }
      });
      return { items, tabItems };
    },
    [modelMap, realTimeData, extral],
  );

  useEffect(() => {
    const group: GroupItem[] = [];
    groupData?.properties?.forEach?.((item) => {
      if (item.component) {
        const Component = lazy(() => import('@/components/Device/module/' + item.component));
        group.push({
          component: (
            <Suspense
              fallback={
                <div className="tx-center">
                  <Spin />
                </div>
              }
            >
              <Component deviceId={deviceId} />
            </Suspense>
          ),
        });
      } else {
        const result = getDetailByProps(item?.properties || []);
        if (result.items && result.items.length > 1) {
          result.items[result.items.length - 1].span = 4 - (result.items.length % 3);
        }
        group.push({
          label: <Detail.Label title={item?.groupName} />,
          items: result.items,
          tabItems: result.tabItems,
        });
      }
    });

    setDetailGroup(group);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [groupData, modelMap, collectionInfo, realTimeData]);

  return (
    <>
      {detailGroup.length ? (
        <Detail.Group
          data={realTimeData}
          items={detailGroup}
          detailProps={{
            extral,
            colon: false,
            labelStyle: { width: 140 },
          }}
        />
      ) : (
        <Empty />
      )}
    </>
  );
};

export default Run;
