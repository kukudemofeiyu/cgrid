import type { MutableRefObject } from 'react';
import type { ProFormInstance } from '@ant-design/pro-components';
import type { ProColumns, ProTableProps } from '@ant-design/pro-components';
import type { SortOrder } from 'antd/lib/table/interface';
import type { ResponsePromise, ResponsePageData } from '@/utils/request';
import type React from 'react';
import type { ButtonProps, TableProps } from 'antd';
import { LocaleEnum } from '@/utils';

export type YTProTableProps<D, P, V = 'text'> = YTProTableCustomProps<D, P, V> &
  Omit<ProTableProps<D, P, V>, 'columns' | 'request'>;


export type toolBarRenderOptionType<Params> = {
  show?: boolean;
  text?: string;
  filterKey?: string | number;
  onClick?: (formRef?: MutableRefObject<ProFormInstance<Params> | undefined>) => void;
  icon?: React.ReactNode;
  buttonProps?: ButtonProps;
  requestExport?: (params: Params) => Promise<Blob | string>;
  getExportName?: (params: Params) => string;
  fileType?: string;
};

export type toolBarRenderOptionsType<Params> = {
  prepend?: React.ReactNode;
  add?: toolBarRenderOptionType<Params>;
  export?: toolBarRenderOptionType<Params>;
  append?: React.ReactNode;
  filterSave?: toolBarRenderOptionType<Params>;
};

export type DragSortType = {
  visable?: boolean;
  triggerType?: TriggerSortType;
  request?: (params: any) => void;
}

export type YTProTableCustomProps<D, P, V = 'text'> = {
  tableRef?: React.MutableRefObject<HTMLDivElement | undefined>;
  toolBarRenderOptions?: toolBarRenderOptionsType<P>;
  option?: {
    columnsProp?: Omit<ProColumns<D, V>, 'render'>;
    modalDeleteText?: string;
    // 渲染拦截器，决定是否继续渲染，主要用于对于每一行决定是否需要显示操作
    renderInterceptor?: (entity: D) => boolean;
    btnInterceptor?: (entity: D, buttonKey: string) => boolean | void;
    onDeleteChange?: ProColumns<D, V>['render'];
    onEditChange?: ProColumns<D, V>['render'];
    onDetailChange?: ProColumns<D, V>['render'];
    onEnterChange?: ProColumns<D, V>['render'];
    render?: ProColumns<D, V>['render'];
    //在基础操作后进行新增的操作渲染
    renderBack?: ProColumns<D, V>['render'];
  };
  columns?: YTProColumns<D, V>[];
  request?: (
    params: P & {
      pageSize?: number;
      current?: number;
      keyword?: string;
    },
    sort: Record<string, SortOrder>,
    filter: Record<string, React.ReactText[] | null>,
  ) => ResponsePromise<ResponsePageData<D>, D>;
  resizable?: boolean;
  resizableOptions?: {
    minWidth?: number;
    maxWidth?: number;
  };
  showIndex?: boolean;
  dragSort?: DragSortType;
  onEvent?: (eventName?: string, params?: any) => void;
  extraHeight?: number;
};

export type EmitType = {
  emit?: (eventName?: string, params?: any) => void;
};

export type YTProColumns<D, V = 'text'> = ProColumns<D, V> & {
  requestOption?: {
    url: string;
    methods?: 'post' | 'get';
    mapKey?: Record<string, string>;
    dataIndex?: string;
  };
  renderWithEmit?: ProColumns<D & EmitType, V>['render'];
  renderFormat?: string;
  widthLocale?: Omit<{
    [key in LocaleEnum]?: string | number;
  }, LocaleEnum.default> & {
    default: string | number;
  }
};

export enum SortOperators {
  Top,
  Bottom,
  Up,
  Down,

}

export enum TriggerSortType {
  Drag,
  Click
}

export type DragComponentsProps = {
  request: any;
  dataSource: any[];
  rowKey: any;
  baseSort: number;
  getSortData: any;
};
