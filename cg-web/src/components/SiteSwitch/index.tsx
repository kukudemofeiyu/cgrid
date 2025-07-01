/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-05 14:50:51
 * @LastEditTime: 2025-02-18 15:47:21
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/SiteSwitch/index.tsx
 */
import { useEffect, useMemo, useRef, useCallback } from 'react';
import { useModel } from 'umi';
import { ProFormColumnsType, ProFormInstance } from '@ant-design/pro-form';
import SchemaForm, { SchemaFormProps } from '@/components/SchemaForm';
import { useArea, useLocation, useSiteColumn } from '@/hooks';
import type { ProColumns } from '@ant-design/pro-table';
import { SiteDataType } from '@/services/station';
import { getRoutersInfo } from '@/services/session';
import { formatMessage, getLocaleMenus, getMenus, getPathTitleMap } from '@/utils';
import eventBus from '@/utils/eventBus';

type SiteType = {
  siteId?: string;
};

export type SiteSwitchProps<ValueType> = {
  initialValues?: SiteType;
  onChange?: (data: SiteType) => void;
  columnProps?: ProColumns<SiteType, ValueType>;
};

const SiteSwitch = <ValueType = 'text',>(
  props: Omit<SchemaFormProps<SiteType, ValueType>, 'layoutType' | 'columns'> &
    SiteSwitchProps<ValueType>,
) => {
  const { initialValues, onChange, columnProps, ...restProps } = props;

  const { dispatch } = useModel('site');
  const { setInitialState } = useModel('@@initialState');
  const { siteTypes } = useModel('siteTypes');
  const formRef = useRef<ProFormInstance>();
  const location = useLocation();
  const { change } = useModel('currentSiteType');
  const { state: areaOptions } = useArea();
  const { change: changeCurrentArea } = useModel('currentArea');

  const changeSite = useCallback(
    (data: SiteDataType, type?: string) => {
      const result = type ?? (data?.energyOptions || '');
      formRef?.current?.setFieldValue?.('siteType', data?.energyOptions);
      localStorage.setItem('siteId', data?.id || '');
      getRoutersInfo({ siteId: data?.id })
        .then((requestMenus) => {
          const menus = getLocaleMenus(requestMenus);
          const antMenus = menus && getMenus(menus);
          setInitialState((prevData: any) => {
            return {
              ...prevData,
              menus,
              antMenus,
              menuPathTitleMap: getPathTitleMap(antMenus),
            };
          });
        })
        .finally(() => {
          dispatch({
            type: 'change',
            payload: { ...data, siteType: result },
          });
        });
    },
    [formRef],
  );

  const siteColumnOption = useMemo<ProColumns<SiteType, ValueType>>(() => {
    return {
      title: formatMessage({ id: 'common.site.siteName', defaultMessage: '站点名称' }),
      width: 200,
      fieldProps: (form) => {
        return {
          allowClear: false,
          onChange: (_: any, option: any) => {
            changeSite(option);
          },
        };
      },
      hideInForm:
        location?.pathname?.indexOf?.('/index/station') > -1 ||
        location?.pathname?.indexOf?.('/station/station-list') > -1,
      ...(columnProps || {}),
    };
  }, [columnProps, location]);

  const [siteColumn, siteOptions] = useSiteColumn<SiteType, ValueType>(siteColumnOption);

  const siteOptionsMap = useMemo<Record<string, SiteDataType>>(() => {
    const result = {};
    siteOptions?.forEach((item) => {
      if (item.id) {
        result[item.id] = item;
      }
    });
    return result;
  }, [siteOptions]);

  const onSiteTypeChange = useCallback(
    (value, option) => {
      const result =
        siteOptions?.find?.((item) => item.energyOptions === value) || siteOptions?.[0];
      if (result && result.id) {
        formRef?.current?.setFieldValue?.('siteId', result.id);
        changeSite(siteOptionsMap[result.id], value);
      }
      change(value, option.monitor);
    },
    [siteOptions],
  );

  const changeSiteBus = useCallback(
    (id) => {
      formRef?.current?.setFieldValue?.('siteId', id);
      changeSite(siteOptionsMap[id]);
    },
    [siteOptionsMap],
  );

  useEffect(() => {
    if (siteOptions?.[0] && siteTypes) {
      const localSiteId = localStorage.getItem('siteId');
      const localSite = siteOptions?.find?.((item) => item.value == localSiteId);
      if (localSite) {
        formRef?.current?.setFieldValue?.('siteId', localSite.value);
        changeSite(localSite, siteTypes?.[0]?.value ?? '');
      } else {
        formRef?.current?.setFieldValue?.('siteId', siteOptions[0].value);
        changeSite(siteOptions[0], siteTypes?.[0]?.value ?? '');
      }
    }
  }, [siteOptions, siteTypes]);

  useEffect(() => {
    formRef?.current?.setFieldValue?.('type', siteTypes?.[0]?.value ?? '');
  }, [siteTypes]);

  useEffect(() => {
    eventBus.on('changeSite', changeSiteBus);
    return () => {
      eventBus.off('changeSite', changeSiteBus);
    };
  }, [changeSiteBus]);

  const formColumns = useMemo<ProFormColumnsType<SiteType, ValueType>[]>(() => {
    const options = areaOptions?.map?.((item) => {
      const result: Record<string, any> = { ...item, children: [] };
      delete result.children;
      return result;
    });
    options.unshift({
      label: formatMessage({ id: 'common.all', defaultMessage: '全部' }),
      id: '',
    });
    return [
      siteColumn,
      {
        title: formatMessage({ id: 'common.site.siteType', defaultMessage: '站点类型' }),
        dataIndex: 'siteType',
        valueType: 'select',
        readonly: true,
        fieldProps: {
          options: siteTypes,
        },
        hideInForm:
          location?.pathname?.indexOf?.('/index/station') > -1 ||
          location?.pathname?.indexOf?.('/station/station-list') > -1,
      },
      {
        title: formatMessage({ id: 'common.site.siteType', defaultMessage: '站点类型' }),
        dataIndex: 'type',
        valueType: 'select',
        width: 200,
        fieldProps: {
          allowClear: false,
          onChange: onSiteTypeChange,
          options: siteTypes,
        },
        hideInForm: location?.pathname?.indexOf?.('/site-monitor') > -1,
      },
      {
        title: formatMessage({ id: 'common.area', defaultMessage: '地区' }),
        dataIndex: 'area',
        valueType: 'select',
        width: 200,
        fieldProps: {
          allowClear: false,
          options,
          fieldNames: {
            value: 'id',
          },
          onChange: (_: any, option: any) => {
            formRef?.current?.setFieldValue?.('area', option.id);
            changeCurrentArea(option);
          },
          showSearch: true,
        },
        hideInForm: location?.pathname?.indexOf?.('/index/station') < 0,
      },
    ];
  }, [siteColumn, onSiteTypeChange, location, siteTypes, areaOptions, changeCurrentArea]);

  return (
    <>
      <SchemaForm<SiteType, ValueType>
        formRef={formRef}
        open={true}
        layoutType="Form"
        layout="inline"
        columns={formColumns}
        submitter={false}
        initialValues={initialValues}
        onValuesChange={onChange}
        {...restProps}
      />
    </>
  );
};

export default SiteSwitch;
