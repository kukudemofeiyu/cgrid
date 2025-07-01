/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-09-13 14:11:11
 * @LastEditTime: 2024-10-08 09:57:41
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\PVTable\index.tsx
 */

import YTProTable from '@/components/ProTable';
import React, { memo, useEffect, useMemo, useState } from 'react';
import useSubscribe from '@/pages/screen/useSubscribe';
import { ProColumns } from '@ant-design/pro-components';
import { PvInverterType } from '@/components/ScreenDialog/PvInverter/data';
import { formatMessage, isEmpty } from '@/utils';

export type PVProps = {
  id: string;
  open: boolean;
  loopNum?: number;
};
const PVTable: React.FC<PVProps> = (props) => {
  const { id, open, loopNum } = props;
  const equipmentData = useSubscribe(id, open);
  const [tableColumns, setTableColumns] = useState<ProColumns<PvInverterType>[]>();

  const tableData = useMemo(() => {
    const data: PvInverterType[] = [
      {
        title: formatMessage({ id: 'siteMonitor.voltage', defaultMessage: '电压' }) + '（V）',
        field: 'Upv',
      },
      {
        title: formatMessage({ id: 'siteMonitor.current', defaultMessage: '电流' }) + '（A）',
        field: 'Ipv',
      },
    ];
    return data;
  }, []);

  useEffect(() => {
    const columns: ProColumns<PvInverterType>[] = [
      {
        title: formatMessage({ id: 'siteMonitor.loop', defaultMessage: '回路' }),
        dataIndex: 'title',
        width: 90,
      },
    ];
    Array.from({ length: equipmentData.pvNum ? equipmentData.pvNum : loopNum }).forEach(
      (item, index) => {
        const num = index + 1;
        const column: ProColumns<PvInverterType> = {
          title: 'PV' + num,
          width: 50,
          align: 'center',
          render: (_, record) => {
            return isEmpty(equipmentData?.[record.field + num])
              ? '--'
              : equipmentData?.[record.field + num];
          },
        };
        columns.push(column);
      },
    );
    setTableColumns(columns);
  }, [loopNum, equipmentData, equipmentData.pvNum]);

  return (
    <>
      <YTProTable
        className="mb32"
        search={false}
        toolBarRender={false}
        columns={tableColumns}
        dataSource={tableData}
        pagination={false}
        rowKey="field"
        scroll={{ y: 'auto' }}
        showIndex={false}
      />
    </>
  );
};

export default memo(PVTable);
