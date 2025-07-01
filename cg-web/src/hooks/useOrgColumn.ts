import { OrgDataType } from '@/pages/system/UserManage/Account.tsx/service';
import { getServiceOrgList } from '@/pages/user-manager/accounts/Customer/service';
import { formatMessage } from '@/utils';
import { ResponsePromise } from '@/utils/request';
import { ProColumns } from '@ant-design/pro-components';
import { debounce } from 'lodash';
import { useCallback, useEffect, useMemo, useState } from 'react';

const useOrgColumn = <TableData = Record<string, any>, ValueType = 'text'>(
  props: Omit<ProColumns<TableData, ValueType>, 'request'> & {
    searchRequest?: (params?: any) => ResponsePromise<any, any>;
  } = {},
  isHide?: boolean,
): [orgColumn: ProColumns<TableData, ValueType>, siteOptions: OrgDataType[] | undefined] => {
  const [orgOption, setOrgOption] = useState();

  const requestOrg = useCallback(
    debounce(({ text }) => {
      const query = { orgName: text, type: 1 } as any;
      getServiceOrgList(query).then(({ data }) => {
        setOrgOption(
          data?.map?.((item: any) => {
            return {
              label: item.orgName,
              value: item.orgId,
            };
          }),
        );
      });
    }, 700),
    [orgOption],
  );

  useEffect(() => {
    requestOrg('');
  }, []);

  const orgColumn = useMemo<ProColumns<TableData, ValueType>>(() => {
    return {
      title: formatMessage({ id: 'siteManage.1086', defaultMessage: '安装商' }),
      dataIndex: 'orgNames',
      width: 180,
      ellipsis: true,
      hideInTable: true,
      hideInSearch: isHide,
      valueType: 'select',
      formItemProps: {
        name: 'orgId',
      },
      fieldProps: {
        showSearch: true,
        filterOption: false,
        options: orgOption,
        onSearch: (text: string) => requestOrg({ text }),
      },
    };
  }, [requestOrg, orgOption, props]);

  return [orgColumn, orgOption];
};
export default useOrgColumn;
