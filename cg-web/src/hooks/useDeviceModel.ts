/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-11-27 14:53:12
 * @LastEditTime: 2024-05-17 11:00:01
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\hooks\useDeviceModel.ts
 */

import { useEffect, useState } from 'react';
import { useRequest } from 'umi';
import { getDeviceModel, getDeviceGroupModel } from '@/services/equipment';
import { DeviceModelDescribeType, DeviceModelType } from '@/types/device';
import { getModelByProps, getPropsFromTree } from '@/utils';
import { isEmpty } from 'lodash';
import { DeviceServicePageEnum, DeviceTypeEnum } from '@/utils/dictionary';

export type useDeviceModelProps = {
  productId?: DeviceTypeEnum | DeviceTypeEnum[];
  deviceId?: string;
  isGroup?: boolean;
  page?: DeviceServicePageEnum;
};

const useDeviceModel = (props: useDeviceModelProps) => {
  const { productId, deviceId, isGroup, page } = props;

  const [modelMap, setModelMap] = useState<Record<string, DeviceModelType>>({});
  const [detailGroup, setDetailGroup] = useState<DeviceModelDescribeType[]>([]);
  const [serviceGruop, setServiceGruop] = useState<DeviceModelDescribeType[]>([]);
  const { loading, run, data } = useRequest(isGroup ? getDeviceGroupModel : getDeviceModel, {
    manual: true,
  });

  useEffect(() => {
    if (productId || deviceId) {
      let productIds: DeviceTypeEnum[] = [];
      if (productId) {
        if (Array.isArray(productId)) {
          productIds = productId;
        } else {
          productIds = [productId];
        }
      }
      let requestNum = 0;
      const request = (params: Record<string, any>) => {
        run(params).then((res) => {
          let result = {};
          if (isGroup) {
            const serviceGroupData = res?.data?.find?.((item) => item?.id == page);
            setServiceGruop(serviceGroupData?.children || []);
            const detailGroupData = res?.data?.find?.(
              (item) => item?.id == DeviceServicePageEnum.RunningData,
            );
            setDetailGroup(detailGroupData?.children || []);

            if (detailGroupData?.children && detailGroupData?.children?.length) {
              const childrens = getPropsFromTree<
                DeviceModelDescribeType,
                DeviceModelDescribeType[]
              >(detailGroupData?.children, 'children')?.reduce?.((arr, item) => {
                arr.push(...item);
                return arr;
              }, []);
              result = getModelByProps(childrens || []);
            } else {
              res?.properties?.forEach?.((item) => {
                result = { ...result, ...getModelByProps(item?.properties || []) };
              });
            }
          } else {
            result = getModelByProps(res?.properties || []);
          }
          if (!isEmpty(result)) {
            setModelMap((prevData) => {
              return {
                ...prevData,
                ...result,
              };
            });
          }
          requestNum++;
          if (requestNum < productIds.length) {
            request({ productId: productIds[requestNum] });
          }
        });
      };

      if (deviceId) {
        request({ deviceId });
      } else {
        if (productIds.length) {
          request({ productId: productIds[requestNum] });
        }
      }
    }
  }, [productId, deviceId, isGroup]);

  return {
    loading,
    data,
    detailGroup,
    modelMap,
    serviceGruop,
  };
};

export default useDeviceModel;
