/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2024-12-26 10:29:45
 * @LastEditTime: 2024-12-26 10:29:45
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/Device/Control/hooks/useComponents.tsx
 */

import { DeviceModelDescribeType } from "@/types/device";
import { DeviceModelDescribeTypeEnum, getPropsFromTree } from "@/utils";
import { lazy, useMemo } from "react";

const useComponents = (groupData?: DeviceModelDescribeType[]) => {

  const controllInterceptComponents = useMemo<
    Record<string, React.LazyExoticComponent<React.ComponentType<any>>>
  >(() => {
    const ids = getPropsFromTree(
      groupData,
      'controlIntercept',
      'children',
    );
    return ids.reduce((result, item) => {
      return {
        ...result,
        [item]: lazy(() => import('@/components/Device/module/' + item)),
      };
    }, {});
  }, [groupData]);

  const customComponents = useMemo<
    Record<string, React.LazyExoticComponent<React.ComponentType<any>>>
  >(() => {
    const ids = getPropsFromTree(
      groupData,
      'id',
      'children',
      (item) => item.type == DeviceModelDescribeTypeEnum.Component,
    );
    return ids.reduce((result, item) => {
      return {
        ...result,
        [item]: lazy(() => import('@/components/Device/module/' + item)),
      };
    }, {});
  }, [groupData]);

  return {
    controllInterceptComponents,
    customComponents,
  };
};

export default useComponents;