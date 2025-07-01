import { DatePicker } from 'antd';
import type { ProRenderFieldPropsType } from '@ant-design/pro-components';
import moment from 'moment';

const { RangePicker } = DatePicker;

const YTDateRange = (props: any) => {
  const { format, dateFormat, onChange, ...resetProps } = props;
  const handleRangeChange = (dates: any) => {
    if (dates?.length) {
      onChange([moment(dates[0]?.format(format)), moment(dates[1]?.format(format))]);
    } else {
      onChange([]);
    }
  };
  return <RangePicker format={dateFormat} {...resetProps} onChange={handleRangeChange} />;
};
const valueRender: ProRenderFieldPropsType['render'] = (value) => {
  return value;
};
const formRender: ProRenderFieldPropsType['renderFormItem'] = (value, props) => {
  return (
    <>
      <YTDateRange {...props.fieldProps} />
    </>
  );
};
export const YTDATERANGE = 'YTDateRange';
export type YTDATERANGEVALUETYPE = typeof YTDATERANGE;
export const YTDateRangeValueTypeRender: ProRenderFieldPropsType = {
  render: valueRender,
  renderFormItem: formRender,
};
export const YTDateRangeValueTypeMap = {
  [YTDATERANGE]: YTDateRangeValueTypeRender,
};
export default YTDateRange;
