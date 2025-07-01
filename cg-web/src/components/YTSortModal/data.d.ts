import { number } from "echarts";



export type SortModalProps<DataType> = {
    rowKey: string;
    rowName: string;
    open: boolean;
    dataSource?: DataType[];
    request: (data: any[]) => Promise<any>;
    onCancel?: () => void;
    onOk?: (success: boolean, data?: any[]) => void;
  };

export type SortableTagsprops<DataType> = {
    rowKey: string;
    rowName: string;
    dataSource?: DataType[];
    onChange: (dataSource: DataType[]) => void;
  };

export enum ScrollTypeEnum  {
  Top,
  Bottom
}
