import { useCallback, useEffect, useMemo, useRef, useState } from 'react';
import { ProTable } from '@ant-design/pro-components';
import type { ProFormInstance } from '@ant-design/pro-components';
import type { ParamsType } from '@ant-design/pro-provider';
import {
  type DragComponentsProps,
  TriggerSortType,
  type YTProColumns,
  type YTProTableProps,
} from './typing';

import genDefaultOperation from './operation';
import {
  formatData,
  calculateColumns,
  normalizeRequestOption,
  standardRequestTableData,
} from './helper';
import styles from './index.less';
import useToolBarRender from './useToolBarRender';
import useTableSize from './useTableSize';
import { useBoolean } from 'ahooks';
import { formatMessage } from '@/utils';
import { useAntdColumnResize } from '@yangjianfei/react-antd-column-resize';
import { merge } from 'lodash';
import dragComponents, { DragHandle } from './dragSort';
import useClickSort from './useClickSort';
import { ConfigProvider } from 'antd';

const YTProTable = <
  DataType extends Record<string, any>,
  Params extends ParamsType = ParamsType,
  ValueType = 'text',
>(
  props: YTProTableProps<DataType, Params, ValueType>,
) => {
  const {
    toolBarRender,
    columns,
    dragSort = {
      triggerType: TriggerSortType.Drag,
    },
    actionRef,
    components: realityComponents = {},
    formRef,
    toolBarRenderOptions,
    request,
    rowKey = 'id',
    tableRef,
    className,
    resizable = false,
    resizableOptions,
    onEvent,
    extraHeight,
    beforeSearchSubmit,
    onSubmit,
    pagination,
    showIndex = true,
    rowSelection,
    ...restProps
  } = props;
  const tableFormRef = useRef<ProFormInstance<Params>>();
  const myTableRef = useRef<HTMLDivElement>();
  const [dragConfig, setDragConfig] = useState({});
  const [collapsed, { set: setCollapse }] = useBoolean(false);
  const [dataSource, setDataSource] = useState([]);
  const [curBaseSort, setCurBaseSortataSource] = useState(0);
  const [dragQuery, setDragQuery] = useState<DragComponentsProps>();
  const [adaptionColumns, setAdaptionColumns] = useState<YTProColumns<DataType, ValueType>[]>(
    columns || [],
  );

  const dragcolumns = [
    {
      title: (
        <span
          title={formatMessage({ id: 'common.sort', defaultMessage: '排序' })}
          className={styles.dragTitle}
        >
          {formatMessage({ id: 'common.sort', defaultMessage: '排序' })}
        </span>
      ),
      dataIndex: 'sort',
      hideInSearch: true,
      // width: 60,
      render: (_: any, record: any) => {
        if (!record.parentId) {
          // 父节点显示拖拽功能
          return <DragHandle />;
        }
        return '';
      },
    },
  ];

  const indexColumns = [
    {
      title: formatMessage({ id: 'common.index', defaultMessage: '序号' }),
      dataIndex: 'index',
      valueType: 'index',
      hideInSearch: true,
      width: 60,
    },
  ];

  const mergedFormRef = useMemo(() => {
    return formRef || tableFormRef;
  }, [formRef, tableFormRef]);

  const mergedTableRef = useMemo<any>(() => {
    return tableRef || myTableRef;
  }, [tableRef, myTableRef]);

  const mergedOnCollapse = useCallback(
    (value: boolean) => {
      if (restProps?.search) {
        restProps?.search?.onCollapse?.(value);
      }
      setCollapse(value);
    },
    [restProps?.search, setCollapse],
  );

  const { sortToolRender, mergedRowSelection } = useClickSort<DataType>(
    mergedTableRef,
    dragSort,
    dragQuery,
    rowSelection,
  );

  const toolBarRenderResult = useToolBarRender<DataType, Params, ValueType>(
    toolBarRender,
    toolBarRenderOptions,
    mergedFormRef,
    columns,
    restProps.search,
    restProps.params,
    sortToolRender,
  );

  const getDragList = (params: any) => {
    if (dragSort?.visable) {
      setDataSource(params.list);
      setCurBaseSortataSource((params.pageNum - 1) * params.pageSize);
    }
  };

  useEffect(() => {
    const query = {
      request: dragSort?.request,
      dataSource,
      rowKey,
      baseSort: curBaseSort,
      getSortData: (sortData: any) => {
        setDataSource(sortData);
      },
    };
    setDragQuery(query);
    setDragConfig(dragComponents(query));
  }, [dataSource, curBaseSort, dragSort?.request, rowKey]);

  // 对request请求方法进行封装，解构表格数据格式
  const standardRequest = standardRequestTableData<DataType, Params>(
    request,
    props.expandable,
    getDragList,
  );

  const mergedBeforeSearchSubmit = useCallback(
    (data) => {
      const result = formatData(data, columns);
      beforeSearchSubmit?.(data);
      return result;
    },
    [columns, beforeSearchSubmit],
  );

  const mergedOnSubmit = useCallback(
    (data) => {
      formatData(data, columns);
      onSubmit?.(data);
    },
    [columns, onSubmit],
  );

  const expandable = useMemo(() => {
    let expandIconColumnIndex = 0;
    if (showIndex) {
      expandIconColumnIndex++;
    }
    if (dragSort?.visable) {
      expandIconColumnIndex++;
    }
    expandIconColumnIndex = props.expandable?.expandIconColumnIndex ?? expandIconColumnIndex;

    return {
      ...props.expandable,
      ...{
        expandIconColumnIndex,
      },
    };
  }, [props.expandable, showIndex, dragSort]);

  const { scrollX } = useTableSize(mergedTableRef, restProps.scroll, collapsed, extraHeight);

  const { resizableColumns, components, tableWidth } = useAntdColumnResize(() => {
    return { minWidth: 48, ...resizableOptions, columns: resizable ? adaptionColumns : [] };
  }, [resizable, resizableOptions, adaptionColumns]);

  useEffect(() => {
    // TODO: 支持选项式的请求
    const result = normalizeRequestOption<DataType, ValueType>(columns, onEvent);

    // 合并默认的操作(删除，编辑，进入)
    const defaultOperation = genDefaultOperation<DataType, Params, ValueType>(props);
    if (defaultOperation) {
      result?.push(defaultOperation);
    }
    const indexIdx = result.findIndex((item) => item.valueType === 'index');
    if (showIndex && indexIdx === -1) {
      result?.unshift(...indexColumns);
    } else if (!showIndex && indexIdx > -1) {
      /// 控制了不展示序列号，但column里仍然传入了则移除
      result.splice(indexIdx, 1);
    }
    if (dragSort?.visable) {
      result?.unshift(...dragcolumns);
    }
    if (resizable) {
      calculateColumns(result, mergedTableRef);
    }
    setAdaptionColumns(result);
  }, [columns, resizable, onEvent, props, dragSort?.visable, mergedTableRef]);

  return (
    <div ref={mergedTableRef}>
      <ProTable<DataType, Params, ValueType>
        actionRef={actionRef}
        formRef={mergedFormRef}
        options={{
          density: false,
          fullScreen: false,
          reload: false,
          setting: true,
        }}
        expandable={expandable}
        dataSource={dragSort?.visable ? dataSource : restProps.dataSource}
        columns={resizable ? (resizableColumns as any) : adaptionColumns}
        components={merge(components, realityComponents, dragSort?.visable ? dragConfig : {})}
        toolBarRender={toolBarRenderResult}
        pagination={
          pagination == false
            ? false
            : {
                showSizeChanger: true,
                showQuickJumper: true,
                ...pagination,
              }
        }
        request={standardRequest}
        rowKey={rowKey}
        className={styles.ytTable + ' ' + className}
        beforeSearchSubmit={mergedBeforeSearchSubmit}
        onSubmit={mergedOnSubmit}
        {...restProps}
        scroll={{
          x: resizable ? tableWidth : scrollX,
          y: 100,
          ...restProps?.scroll,
        }}
        search={
          restProps?.search === false
            ? false
            : {
                labelWidth: 'auto',
                searchText: formatMessage({ id: 'common.search', defaultMessage: '搜索' }),
                showHiddenNum: true,
                ...(restProps?.search || {}),
                onCollapse: mergedOnCollapse,
              }
        }
        rowSelection={mergedRowSelection}
      />
    </div>
  );
};

export default YTProTable;
