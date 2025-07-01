import { Modal } from 'antd';

import { useEffect, useState } from 'react';
import { formatMessage } from '@/utils';
import styleClasses from './index.less';
import { SortModalProps } from './data';
import SortableTags from './sortbleTags';



const SortModal = <DataType extends Record<string, any>>(props:SortModalProps<DataType>) => {
  const {  request, onCancel, onOk } = props;
  const [dataSource, setDataSource] = useState<DataType[]>(props.dataSource || []);
  const [sortLoading, setSortLoading] = useState<boolean>(false);

  useEffect(() => {
    setDataSource(props.dataSource || []);
  }, [props.dataSource]);

  const handleOk = () => {
    const queryData = [] as any[]; // 排序后的数据
    const sortData = dataSource
      .filter((el: any) => !!el)
      .map((item, index) => {
        queryData.push({
          [`${props.rowKey}`]: item[`${props.rowKey}`],
          sort: index,
        });
        return item;
      });
    setSortLoading(true);
    request(queryData)
      .then(() => {
        onOk && onOk(true, sortData);
      })
      .catch((e) => {
        setDataSource(props.dataSource || []);
        throw e;
      })
      .finally(() => {
        setSortLoading(false);
      });
  };

  return (
    <Modal
      destroyOnClose
      width={816}
      title={
        <>
        <text className='ant-modal-title'>
        {formatMessage({ id: 'common.sort', defaultMessage: '排序' })}
        </text>
        <text className={styleClasses.tip}>
          （{formatMessage({ id: 'physicalModel.1074', defaultMessage: '拖动标签进行排序' })}）
        </text>
        </>
      }
      open={props.open}
      onOk={handleOk}
      onCancel={onCancel}
      confirmLoading={sortLoading}
    >
      <SortableTags
        dataSource={dataSource}
        rowKey={props.rowKey}
        rowName={props.rowName}
        onChange={setDataSource}
      />
    </Modal>
  );
};

export default SortModal;
