/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-11 17:39:54
 * @LastEditTime: 2024-07-10 11:04:03
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\YTProTable\useToolBarRender.tsx
 */
import React, { useMemo, useCallback } from 'react';
import type { MutableRefObject } from 'react';
import { Button } from 'antd';
import { PlusOutlined, ExportOutlined } from '@ant-design/icons';
import type { ProFormInstance } from '@ant-design/pro-components';
import type { ParamsType } from '@ant-design/pro-provider';
import type { YTProTableProps, toolBarRenderOptionsType } from './typing';
import { merge } from 'lodash';
import { isEmpty, saveFile } from '@/utils';
import { useBoolean } from 'ahooks';
import { formatMessage } from '@/utils';
import { formatData } from './helper';
import FilterSave from '../FilterSave';

enum optionsType {
  Add = 'add',
  Export = 'export',
}

const useToolBarRender = <
  DataType extends Record<string, any>,
  Params extends ParamsType = ParamsType,
  ValueType = 'text',
>(
  toolBarRender: YTProTableProps<DataType, Params, ValueType>['toolBarRender'],
  toolBarRenderOptions?: toolBarRenderOptionsType<Params>,
  formRef?: MutableRefObject<ProFormInstance<Params> | undefined>,
  columns?: any,
  search?: any,
  params?: any,
  clickSortRender?:React.ReactNode|false
) => {
  const [exportLoading, { setTrue, setFalse }] = useBoolean(false);

  const exportHandler = useCallback(
    (value?: any) => {
      if (toolBarRenderOptions?.export?.requestExport) {
        setTrue();
        toolBarRenderOptions?.export
          ?.requestExport?.(value as any)
          .then((res) => {
            saveFile(
              res,
              toolBarRenderOptions?.export?.getExportName?.(value as any) || '导出文件',
              toolBarRenderOptions?.export?.fileType,
            );
          })
          .finally(() => {
            setFalse();
          });
      }
    },
    [setFalse, setTrue, toolBarRenderOptions?.export],
  );
  const onExport = useCallback(() => {
    if (search == false) {
      exportHandler(params);
    } else {
      formRef?.current?.validateFields?.()?.then((value) => {
        formatData(value, columns);
        exportHandler(value);
      });
    }
  }, [search, exportHandler, params, formRef, columns]);

  const options = useMemo(() => {
    const defaultOptions: toolBarRenderOptionsType<Params> = {
      [optionsType.Add]: {
        show: true,
        text: formatMessage({ id: 'common.new', defaultMessage: '新建' }),
        icon: <PlusOutlined />,
      },
      [optionsType.Export]: {
        show: false,
        text: formatMessage({ id: 'common.export', defaultMessage: '导出' }),
        icon: <ExportOutlined />,
        onClick: onExport,
      },
    };

    return merge(defaultOptions, toolBarRenderOptions || {});
  }, [toolBarRenderOptions, onExport]);

  const toolBarRenderResult = useMemo(() => {
    if (isEmpty(toolBarRender)) {
      const result: React.ReactNode[] = [];
      if(clickSortRender) {
        result.push(clickSortRender)
      }
      if (options.prepend) {
        result.push(options.prepend);
      }
      if (options.filterSave) {
        result.push(<FilterSave filterKey={options.filterSave.filterKey} filterForm={formRef} />);
      }

      [optionsType.Add, optionsType.Export].forEach((item) => {
        if (options[item]?.show) {
          result.push(
            <Button
              key={item}
              type="primary"
              onClick={() => options[item]?.onClick?.(formRef)}
              loading={exportLoading}
              {...(options[item]?.buttonProps || {})}
            >
              {options[item]?.icon}
              {options[item]?.text}
            </Button>,
          );
        }
      });
      if (options.append) {
        result.push(options.append);
      }

      return () => result;
    } else {
      return toolBarRender;
    }
  }, [toolBarRender, options, formRef, exportLoading,clickSortRender]);

  return toolBarRenderResult;
};

export default useToolBarRender;
