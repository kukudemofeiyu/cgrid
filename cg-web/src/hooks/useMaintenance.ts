/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-30 17:13:23
 * @LastEditTime: 2023-07-11 14:19:14
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\hooks\useMaintenance.ts
 */
import { useCallback } from 'react';
import { api } from '@/services';
import type { ProColumns } from '@ant-design/pro-components';
import useSearchSelect from './useSearchSelect';
import type { UseSearchSelectProps, SearchParams } from './useSearchSelect';
import type { OptionType } from '@/types';

const useMaintenance = <TableData = Record<string, any>, ValueType = 'text'>(
  props?: UseSearchSelectProps<TableData, ValueType>,
): [maintenanceColumn: ProColumns<TableData, ValueType>, options: OptionType[] | undefined] => {
  const request = useCallback((params: SearchParams) => {
    return api
      .getMaintenancePeople({ ...params, maintenanceStaff: params.name })
      .then(({ data }) => {
        return data?.map?.((item: any) => {
          return {
            label: item.maintenanceStaff,
            value: item.maintenanceStaffId,
          };
        });
      });
  }, []);

  const [maintenanceColumn, options] = useSearchSelect<TableData, ValueType>({
    proColumns: {
      title: '维护人员',
      dataIndex: 'maintenanceStaff',
      formItemProps: {
        name: 'maintenanceStaffId',
      },
    },
    request: request,
    ...(props || {}),
  });

  return [maintenanceColumn, options];
};

export default useMaintenance;
