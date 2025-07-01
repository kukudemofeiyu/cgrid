import { Button, Checkbox, Col, message, Modal, Row, Space, Tag } from 'antd';
import {
  arrayMove,
  SortableContainer,
  SortableElement,
  SortableHandle,
  SortEnd,
} from 'react-sortable-hoc';
import styleClasses from './index.less';
import { useCallback, useMemo, useState } from 'react';
import { ProForm, ProFormItem, ProFormSelect, ProFormSwitch } from '@ant-design/pro-components';
import { formatMessage } from '@/utils';
import { VerticalAlignBottomOutlined, VerticalAlignTopOutlined } from '@ant-design/icons';
import { CheckboxValueType } from 'antd/lib/checkbox/Group';
import { useToggle } from 'ahooks';
import {  SortableTagsprops } from './data';;

const SortableTags = <DataType extends Record<string, any>>(props: SortableTagsprops<DataType>) => {
  const { dataSource, onChange, rowKey, rowName } = props;
  const [findItem, setFindItem] = useState<string>();
  const [selectKeys, setSelectKeys] = useState<string[]>([]);
  const [isMultipleSelected, { set: setIsMultipleSelected }] = useToggle(false);

  const isMultiple = useMemo((

  )=>{
    return selectKeys.length > 1;
  }, [selectKeys])

  const scrollToAnchor = (anchorName: string) => {
    const anchorElement = document.getElementById(anchorName);

    if (anchorElement) {
      anchorElement.scrollIntoView({ behavior: 'smooth' });
    }
  };


  const onSearch = async (value: any) => {
    if (value) {
      setFindItem(value[rowKey]);
      scrollToAnchor(value[rowKey] as string);
    } else {
      setFindItem(undefined);
    }
  };
  const onSortEnd = ({ oldIndex, newIndex }: SortEnd) => {
    if(isMultiple) {
      message.warning(
        formatMessage({
          id: 'physicalModel.1081',
          defaultMessage: '多选模式下无法拖动排序',
        }),
      );

    } else {
      if (oldIndex != newIndex) {
        onChange(arrayMove(dataSource!, oldIndex, newIndex));
      }
    }
  };

  const sortKey = (keys: string[]): number[] => {
    const indexArray: { id: string; index: number }[] = [];
    keys.forEach((key) => {
      const index = dataSource?.findIndex((data) => data[rowKey] === key);
      if (index != undefined && index > -1) {
        indexArray.push({ id: key, index: index! });
      }
    });
    return indexArray.sort((a, b) => a.index - b.index).map(({ index }) => index);
  };

  const topHandle = useCallback(() => {
    if (dataSource && dataSource.length) {
      var sortData = dataSource.slice();
      sortKey(selectKeys).forEach((oldIndex, index) => {
        sortData = arrayMove(sortData!, oldIndex, index);
      });
      onChange(sortData);
      scrollToAnchor(dataSource[0][rowKey]);
    }
  }, [selectKeys,dataSource]);

  const bottomHandle = useCallback(() => {
    if (dataSource && dataSource.length) {
      var sortData = dataSource?.slice();
      const len = dataSource.length - 1;
      const keys = sortKey(selectKeys).reverse();

      keys.forEach((oldIndex, index) => {
        sortData = arrayMove(sortData!, oldIndex, len - index);
      });
      onChange(sortData);
      scrollToAnchor(dataSource[len][rowKey]);
    }
  }, [selectKeys,dataSource]);
  const onCheckBoxGroupChange = (checkedValue: CheckboxValueType[]) => {
    setSelectKeys(checkedValue as string[]);
  };

  const oMultipleSwitchChange = (checked: boolean) => {
    if(!checked) {
      setSelectKeys([]);
    }
    setIsMultipleSelected(checked);
  };

  const DragHandle = SortableHandle(({ data }: { data: DataType }) => (
    <a className={styleClasses.aLine} id={data[rowKey]} onMouseDown={(e) => e.preventDefault()}>
      <Tag color={findItem === data[rowKey] ? 'blue' : 'default'} className={styleClasses.tagText}>
        {data[rowName]}
      </Tag>
    </a>
  ));

  const GridItem = SortableElement(({ data }: { data: DataType }) => (
    <Space style={{ zIndex: 1001 }} align="center">
      {isMultipleSelected && <Checkbox value={data[rowKey]} />}
      <DragHandle data={data} />
    </Space>
  ));

  const Grid = SortableContainer(({ items }: { items?: DataType[] }) => (
    <Checkbox.Group
      value={selectKeys}
      onChange={onCheckBoxGroupChange}
      className={styleClasses.sortContainer}
    >
      {items &&
        items.map((value: any, index: number) => (
          <GridItem key={value[rowKey]} data={value} index={index} />
        ))}
    </Checkbox.Group>
  ));

  return (
    <div className={styleClasses.container}>
      <ProForm
        className={styleClasses.searchContainer}
        name="searchForm"
        layout="horizontal"
        submitter={false}
        onFinish={onSearch}
      >
        <Row align="bottom">
          <Col span={9}>
            <ProFormSelect
              name={rowKey}
              label={formatMessage({ id: 'siteManage.1048', defaultMessage: '标签名称' })}
              formItemProps={{
                required: true,
              }}
              fieldProps={{
                options: dataSource?.map((item) => ({
                  label: item[rowName],
                  value: item[rowKey],
                })),
                showSearch: true,
              }}
            />
          </Col>
          <Col span={3} offset={1}>
            <ProFormItem>
              <Button type="primary" htmlType="submit">
                {formatMessage({ id: 'common.search', defaultMessage: '搜索' })}
              </Button>
            </ProFormItem>
          </Col>
          <Col span={9} offset={2}>
            <Space align="end">
              <ProFormSwitch
                name="isMultipleSelected"
                label={formatMessage({ id: 'physicalModel.1078', defaultMessage: '多选' })}
                fieldProps={{
                  checked: isMultipleSelected,
                  onChange: oMultipleSwitchChange,
                }}
              />
              {isMultipleSelected && (
                <>
                  <ProFormItem>
                    <Button icon={<VerticalAlignTopOutlined />} onClick={topHandle}>
                      {formatMessage({ id: 'common.Topping', defaultMessage: '置顶' })}
                    </Button>
                  </ProFormItem>
                  <ProFormItem>
                    <Button icon={<VerticalAlignBottomOutlined />} onClick={bottomHandle}>
                      {formatMessage({ id: 'common.1019', defaultMessage: '置底' })}
                    </Button>
                  </ProFormItem>
                </>
              )}
            </Space>
          </Col>
        </Row>
      </ProForm>
      <Grid
        items={dataSource}
        onSortEnd={onSortEnd}
        axis="xy"
        lockToContainerEdges={true}
      />
    </div>
  );
};

export default SortableTags;
