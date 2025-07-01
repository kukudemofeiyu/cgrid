import { Button, Divider, Space, TableProps } from 'antd';
import { DragComponentsProps, DragSortType, SortOperators, TriggerSortType } from './typing';
import { useCallback, useEffect, useMemo, useState } from 'react';
import { cloneDeep, merge } from 'lodash';
import { arrayMoveImmutable } from 'array-move';
import { RowSelectMethod } from 'antd/lib/table/interface';

const useClickSort = <DataType extends Record<string, any>>(
  tableRef: React.MutableRefObject<HTMLDivElement | undefined>,
  dragSort?: DragSortType,
  dragQuery?: DragComponentsProps,
  rowSelection?:
    | (TableProps<DataType>['rowSelection'] & {
        alwaysShowAlert?: boolean;
      })
    | false,
) => {
  const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);
  const [mergedRowSelection, setMergedRowSelection] = useState<
    | (TableProps<DataType>['rowSelection'] & {
        alwaysShowAlert?: boolean;
      })
    | false
  >();

  useEffect(() => {
    const keys = rowSelection && rowSelection.selectedRowKeys ? rowSelection.selectedRowKeys : [];
    setSelectedRowKeys(keys);
  }, [rowSelection]);

  const isClickSort = useMemo(() => {
    return dragSort?.visable && dragSort.triggerType == TriggerSortType.Click;
  }, [dragSort]);

  useEffect(() => {
    if (isClickSort) {
      const type = rowSelection && rowSelection.type ? rowSelection.type : 'checkbox';
      setMergedRowSelection(
        merge({}, rowSelection, {
          selectedRowKeys,
          type,
          onChange: (
            keys: React.Key[],
            selectedRows: DataType[],
            info: {
              type: RowSelectMethod;
            },
          ) => {
            setSelectedRowKeys(keys);
            if (rowSelection && rowSelection.onChange) {
              rowSelection.onChange?.(keys, selectedRows, info);
            }
          },
          alwaysShowAlert: false,
        }),
      );
    } else {
      setMergedRowSelection(rowSelection);
    }
  }, [rowSelection, selectedRowKeys, isClickSort]);

  const getRowKey = useCallback(
    (item: DataType) => {
      return typeof dragQuery?.rowKey === 'function'
        ? dragQuery.rowKey(item)
        : item[dragQuery?.rowKey];
    },
    [dragQuery],
  );

  const moveData = useCallback(
    (dataSource: DataType[], from: number, to: number) => {
      return arrayMoveImmutable(dataSource.slice(), from, to)
        .filter((el: any) => !!el)
        .map((item: any, index) => ({ ...item, sortOrder: index }));
    },

    [],
  );

  const scrollTable = (operation: SortOperators) => {
    if (tableRef?.current) {
      const dom = tableRef.current.querySelector('.ant-table-body');
      if (dom) {
        switch (operation) {
          case SortOperators.Top:
            dom.scrollTop = 0;
            break;
          case SortOperators.Bottom:
            dom.scrollTop = dom.scrollHeight - dom.clientHeight;
            break;
          default:
        }
      }
    }
  };

  const sortTrigger = useCallback(
    (type: SortOperators) => {
      let data: DataType[] = cloneDeep(dragQuery!.dataSource);
      const positionList = selectedRowKeys
        .map((key) => data.findIndex((item) => getRowKey(item) === key))
        .sort((a, b) => a - b);
      if (type == SortOperators.Down || type == SortOperators.Bottom) {
        positionList.reverse();
      }
      positionList.map((pos, index) => {
        let to: number;
        switch (type) {
          case SortOperators.Top:
            to = index;

            break;
          case SortOperators.Bottom:
            to = data.length - 1 - index;
            break;
          case SortOperators.Up:
            to = pos - 1;
            break;
          case SortOperators.Down:
            to = pos + 1;

            break;
        }
        if (pos != to && to >= 0 && to < data.length) {
          data = moveData(data, pos, to);
          scrollTable(type);
        }
      });

      const queryData = [] as any;
      data = data
        .filter((el: any) => !!el)
        .map((item, index) => {
          queryData.push({
            [`${dragQuery!.rowKey}`]: item[`${dragQuery!.rowKey}`],
            sort: index + dragQuery!.baseSort,
          });
          return item;
        });
      dragQuery?.request?.(queryData);
      dragQuery?.getSortData?.(data);
    },
    [selectedRowKeys, dragQuery, tableRef],
  );

  return {
    sortToolRender: isClickSort && (
      <Space style={{ border: '1px solid rgba(0, 0, 0, 0.06)' }}>
        <Button type="text" onClick={() => sortTrigger(SortOperators.Top)}>
          置顶
        </Button>
        <Divider type="vertical" />
        <Button type="text" onClick={() => sortTrigger(SortOperators.Bottom)}>
          置底
        </Button>
        <Divider type="vertical" />
        <Button type="text" onClick={() => sortTrigger(SortOperators.Up)}>
          上移
        </Button>
        <Divider type="vertical" />
        <Button type="text" onClick={() => sortTrigger(SortOperators.Down)}>
          下移
        </Button>
      </Space>
    ),
    mergedRowSelection,
  };
};

export default useClickSort;
