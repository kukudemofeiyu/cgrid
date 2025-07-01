/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-02 16:59:12
 * @LastEditTime: 2024-12-27 16:00:21
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/TableSelect/TableTreeSelect/TableTreeModal.tsx
 */
import React, { useCallback, useState, useEffect, useMemo, useRef } from 'react';
import { Tag, Tree, Row, Col, Empty as AntEmpty, Spin, Input } from 'antd';
import Dialog from '@/components/Dialog';
import type { TreeProps } from 'antd';
import type { SortOrder } from 'antd/lib/table/interface';
import type { BasicDataNode } from 'rc-tree/lib/interface';
import type { TableRowSelection } from 'antd/es/table/interface';
import RcTree from 'rc-tree';
import type { ProTableProps, ProFormInstance } from '@ant-design/pro-components';
import { debounce, mergeWith } from 'lodash';
import styles from '../index.less';
import { cloneDeep } from 'lodash';
import type { ResponsePromise, ResponsePageData } from '@/utils/request';
import Empty from '@/components/Empty';
import { useBoolean } from 'ahooks';
import { formatMessage } from '@/utils';
import { filterData, runDealTreeData, updateTreeData } from './helper';
import { SearchProps } from 'antd/lib/input';
import YTProTable from '@/components/ProTable';
import { Resizable } from 'react-resizable';

export enum SelectTypeEnum {
  Collect = 'collect',
  Device = 'device',
}

export type dealTreeDataType<TreeData = Record<string, any>> = {
  (value: TreeData & BasicDataNode, index: number): void;
};

export { BasicDataNode };

export type TableTreeModalProps<V, T, U, TreeData> = {
  model?: string; // screen：大屏样式
  value?: V[]; //
  onChange?: (value: V[]) => void;
  title?: string; // 弹窗标题
  open?: boolean;
  onFocus?: () => void | Promise<any>;
  onCancel?: () => void;
  width?: string; // 弹窗宽度
  proTableProps?: Omit<ProTableProps<T, U>, 'actionRef' | 'request'> & {
    // 表格属性
    request?: (
      params: U & {
        pageSize?: number;
        current?: number;
        keyword?: string;
      },
      sort: Record<string, SortOrder>,
      filter: Record<string, React.ReactText[] | null>,
    ) => ResponsePromise<ResponsePageData<T>, T>;
  };
  multiple?: boolean; // 是否多选
  disabled?: boolean; // 是否禁用
  limit?: number; //  表单输入框显示已选项的数量，多余的数字显示
  limitSelect?: number; // 限制选择的数量
  valueId?: string; // 数据字段id既表格id或者树id
  valueName?: string; // 数据字段name既表格name或者树name
  valueFormat?: (value: string, item: V) => string;
  clearable?: boolean; // 表单输入框是否可清空
  placeholder?: string; // 表单输入框placshoder
  treeSearch?: SearchProps & {
    filterData?: typeof filterData;
  };
  treeProps?: Omit<TreeProps, 'onSelect' | 'treeData' | 'blockNode'> & {
    //  树属性
    request: (params?: any) => Promise<any> | undefined;
  };
  selectType?: SelectTypeEnum; //  数据选择类型：选设备/选设备属性
  dealTreeData?: dealTreeDataType<TreeData>; //  处理树数据
  virtual?: boolean;
  oneEquipment?: boolean; //是否仅能选择一个设备，true时传入后会清空上一个设备的所有选项
};

const treeHeight = 441;
const treeHeightStyle = { height: treeHeight + 'px' };

const TableTreeModal = <
  ValueType extends Record<string, any>,
  DataType extends Record<string, any>,
  Params extends Record<string, any>,
  TreeData,
>(
  props: TableTreeModalProps<ValueType, DataType, Params, TreeData>,
) => {
  const {
    model,
    title = formatMessage({ id: 'taskManage.selectData', defaultMessage: '选择数据' }),
    open,
    onCancel,
    width = '1000px',
    multiple = true,
    limitSelect,
    proTableProps = {},
    valueId = 'id',
    valueName = 'name',
    valueFormat,
    value,
    onChange,
    selectType = SelectTypeEnum.Collect,
    dealTreeData,
    virtual = false,
    treeSearch,
    oneEquipment,
  } = props;

  const [selectedTags, setSelectedTags] = useState<ValueType[]>([]);
  const [treeData, setTreeData] = useState<any[]>();
  const [tableParams, setTableParams] = useState<any>({});
  const [selectedTree, setSelectedTree] = useState<TreeData>();
  const [tableIdSet, setTableIdSet] = useState<Set<string>>();
  const [loadingTreeData, { setTrue, setFalse }] = useBoolean(false);
  const colRef = useRef<HTMLDivElement>(null);
  const treeRef = useRef<RcTree>(null);
  const tableFormRef = useRef<ProFormInstance<Params>>();
  const [searchValue, setSearchValue] = useState<string>('');
  const [treeWidth, setTreeWidth] = useState(250);

  const filterTreeData = useMemo(() => {
    if (searchValue) {
      return (treeSearch?.filterData || filterData)(
        treeData || [],
        searchValue,
        props?.treeProps?.fieldNames?.title || 'title',
      );
    } else {
      return treeData;
    }
  }, [treeData, searchValue]);

  const treeSelectAndCheckData = useMemo(() => {
    if (selectType === SelectTypeEnum.Device) {
      if (multiple) {
        return {
          checkedKeys: selectedTags?.map?.((item) => item[valueId]),
        };
      } else {
        return {
          selectedKeys: selectedTags?.map?.((item) => item[valueId]),
        };
      }
    }
    return {};
  }, [selectType, multiple, selectedTags, valueId]);

  const onSelectedChange: TableRowSelection<DataType>['onChange'] = useCallback(
    (selectedRowKeys, selectedRows: DataType[]) => {
      setSelectedTags((prevData) => {
        const map = multiple
          ? prevData.reduce((result, item) => {
            result.set(item[valueId], item);
            return result;
          }, new Map())
          : new Map();
        if (multiple) {
          tableIdSet?.forEach((item) => {
            map.delete(item);
          });
        }
        selectedRows.forEach((item) => {
          if (item.checkable !== false) {
            map.set(item[valueId], {
              [valueId]: item[valueId],
              [valueName]: item[valueName],
              node: item,
              tree: selectedTree,
            });
          }
        });
        const result = [...map.values()];
        return limitSelect ? result.slice(0, limitSelect) : result;
      });
    },
    [valueId, valueName, tableIdSet, multiple, limitSelect],
  );

  const onCleanSelected = useCallback(() => {
    setSelectedTags([]);
  }, []);

  const onClose = useCallback(
    (index) => {
      const result = cloneDeep(selectedTags || []);
      result.splice(index, 1);
      setSelectedTags(result);
    },
    [selectedTags],
  );

  const onOk = useCallback(() => {
    onChange?.(selectedTags);
    onCancel?.();
  }, [selectedTags]);

  const onTreeSelect = useCallback(
    (selectedKeys, { selectedNodes }) => {
      setSelectedTree(selectedNodes[0]);
      if (selectedKeys && selectedKeys.length) {
        setTableParams({ deviceId: selectedKeys[0] });
      }
      if (selectType === SelectTypeEnum.Device && !multiple) {
        const result = selectedNodes.map(
          (item: TreeData) =>
          ({
            [valueId]: item[valueId as keyof TreeData],
            [valueName]: item[valueName as keyof TreeData],
            ['node' as string]: item,
          } as ValueType),
        );
        setSelectedTags(limitSelect ? result.slice(0, limitSelect) : result);
      }
    },
    [valueId, valueName, limitSelect],
  );

  const onTreeCheck = useCallback(
    (_, { checkedNodes }) => {
      const result = checkedNodes.map(
        (item: TreeData) =>
        ({
          [valueId]: item[valueId as keyof TreeData],
          [valueName]: item[valueName as keyof TreeData],
          ['node' as string]: item,
        } as ValueType),
      );
      setSelectedTags(result);
    },
    [valueId, valueName],
  );

  const requestTable = useCallback(
    (params, sort, filter) => {
      if (oneEquipment) {
        onCleanSelected(); //仅能选择一个设备的站点
      }
      if (proTableProps?.request && params.deviceId) {
        return proTableProps.request(params, sort, filter).then(({ data }) => {
          if (Array.isArray(data)) {
            setTableIdSet(
              new Set(
                data?.map?.((item) => {
                  item.selectable = false;
                  return item[valueId];
                }),
              ),
            );
            return {
              code: '200',
              data: {
                list: data,
                total: data?.length,
              },
              msg: '',
            };
          } else {
            setTableIdSet(new Set(data?.list?.map?.((item) => item[valueId])));
            return {
              code: '200',
              data: {
                list: data?.list,
                total: data?.total,
              },
              msg: '',
            };
          }
        });
      } else {
        return Promise.resolve({
          code: '200',
          data: {
            list: [],
            total: 0,
          },
          msg: '',
        });
      }
    },
    [proTableProps?.request, proTableProps?.pagination, valueId],
  );

  const onSearchChange = useCallback(
    debounce(() => {
      tableFormRef.current?.submit?.();
    }, 700),
    [],
  );

  useEffect(() => {
    if (open) {
      setSelectedTags(value || []);
      setSearchValue('');
      if (selectType === SelectTypeEnum.Device) {
        if (value && value.length) {
          setTableParams({ deviceId: value[0][valueId] });
        }
      } else {
        if (props?.treeProps?.defaultSelectedKeys) {
          setTableParams({ deviceId: props?.treeProps?.defaultSelectedKeys?.[0] });
        }
      }
      if (props?.treeProps?.request) {
        setTrue();
        props?.treeProps
          ?.request?.()
          ?.then?.(({ data }) => {
            runDealTreeData(data, dealTreeData);
            setTreeData(data);
          })
          .finally(() => {
            setFalse();
          });
      }
    }
  }, [open, value, props?.treeProps?.selectedKeys, props?.treeProps?.request, selectType, valueId]);

  const tags = useMemo(() => {
    return selectedTags?.map?.((item, index) => {
      let tagValue;
      if (selectType === SelectTypeEnum.Collect) {
        tagValue = valueFormat
          ? valueFormat(item[valueName], item)
          : item?.node?.[props.treeProps?.fieldNames?.title || 'name'] + '-' + item[valueName];
      } else {
        tagValue = valueFormat ? valueFormat(item[valueName], item) : item[valueName];
      }
      return (
        <Tag className="mb4" key={item[valueId]} closable onClose={() => onClose(index)}>
          <div className={styles.tag} title={tagValue}>
            {tagValue}
          </div>
        </Tag>
      );
    });
  }, [selectedTags, valueId, valueName, selectType, props.treeProps]);

  const tableProps = useMemo<ProTableProps<DataType, Params>>(() => {
    const defaultProps: ProTableProps<DataType, Params> = {
      ...(selectType === SelectTypeEnum.Collect
        ? {
          rowSelection: {
            type: multiple ? 'checkbox' : 'radio',
            selectedRowKeys: selectedTags?.map?.((item) => item[valueId]),
            onChange: onSelectedChange,
          },
        }
        : {}),
      search: {
        searchText: formatMessage({ id: 'common.search', defaultMessage: '搜索' }),
        labelWidth: 'auto',
        optionRender: () => [],
        span: 12,
      },
      form: {
        onValuesChange: onSearchChange,
        submitter: false,
      },
      rowKey: valueId,
      scroll: {
        y: proTableProps?.pagination === false ? 372 : 332,
      },
      pagination: {
        defaultPageSize: 10,
        showSizeChanger: true,
      },
      toolBarRender: false,
      tableAlertRender: false,
      tableAlertOptionRender: false,
      size: 'small',
    };
    return mergeWith(defaultProps, proTableProps, (objValue, srcValue) => {
      if (srcValue === false) {
        return false;
      }
    });
  }, [
    selectType,
    multiple,
    selectedTags,
    valueId,
    onSelectedChange,
    proTableProps,
    onSearchChange,
  ]);

  const onSearch = useCallback((data, e: React.MouseEvent) => {
    setSearchValue(data);
    e.preventDefault();
  }, []);

  const treeLoadData = useMemo(() => {
    if (props.treeProps?.loadData) {
      return {
        loadData: (node: any) => {
          const result = props.treeProps?.loadData?.(node);
          result?.then((data) => {
            if (data) {
              setTreeData((prevData) => {
                runDealTreeData(data, dealTreeData);
                updateTreeData(
                  prevData,
                  props.treeProps?.fieldNames?.key || 'key',
                  node[props.treeProps?.fieldNames?.key || 'key'],
                  data || [],
                );
                return [...(prevData || [])];
              });
            }
          });
          return result;
        },
      };
    } else {
      return {};
    }
  }, [props.treeProps?.loadData]);

  const onResize = useCallback(
    (e, { size }) => setTreeWidth(size.width > 700 ? 700 : size.width),
    [],
  );

  return (
    <>
      <Dialog
        model={model}
        title={title}
        open={open}
        width={width}
        onCancel={onCancel}
        onOk={onOk}
        destroyOnClose
        centered
      >
        <div className={`ant-alert ant-alert-info ant-alert-no-icon mb12 ${styles.alert}`}>
          <div className="flex mb8">
            <span className="flex1">
              {formatMessage({ id: 'common.1020', defaultMessage: '已选择' })}
              <span className="cl-primary mx8">{selectedTags?.length || 0}</span>
              {formatMessage({ id: 'common.1021', defaultMessage: '项' })}
              {!!limitSelect && (
                <>
                  (
                  <span className="cl-primary">
                    {formatMessage(
                      {
                        id: 'common.maxSelectItem',
                        defaultMessage: `最多选择${limitSelect}项`,
                      },
                      { num: limitSelect },
                    )}
                  </span>
                  )
                </>
              )}
            </span>
            <a onClick={onCleanSelected}>
              {formatMessage({ id: 'common.clear', defaultMessage: '清空' })}
            </a>
          </div>
          <div>
            <div className={`flex1 ${styles.tagContain}`}>{tags}</div>
          </div>
        </div>
        <Row gutter={24}>
          <Resizable width={treeWidth} onResize={onResize}>
            <Col className={styles.treeCol} flex={treeWidth + 'px'}>
              <Input.Search
                className={styles.search}
                onSearch={onSearch}
                placeholder={formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' })}
                {...treeSearch}
              />
              <div ref={colRef} className={styles.treeContain} style={treeHeightStyle}>
                {loadingTreeData ? (
                  <div className="flex h-full">
                    <Spin className="flex1" />
                  </div>
                ) : (
                  <>
                    <Tree
                      ref={treeRef}
                      className={`${styles.tree} ${virtual ? styles.virtualTree : ''}`}
                      treeData={filterTreeData}
                      onSelect={onTreeSelect}
                      onCheck={onTreeCheck}
                      blockNode
                      checkable={selectType === SelectTypeEnum.Device && multiple}
                      {...treeSelectAndCheckData}
                      checkStrictly
                      defaultExpandAll={true}
                      height={virtual ? treeHeight : undefined}
                      {...props?.treeProps}
                      {...treeLoadData}
                    />
                  </>
                )}
              </div>
            </Col>
          </Resizable>
          <Col className={styles.treeCol} flex="1">
            <YTProTable<DataType, Params>
              formRef={tableFormRef}
              className={styles.proTable}
              {...tableProps}
              params={tableParams}
              request={requestTable}
              locale={{
                emptyText: model == 'screen' ? <Empty /> : <AntEmpty />,
              }}
            />
          </Col>
        </Row>
      </Dialog>
    </>
  );
};

export default TableTreeModal;
