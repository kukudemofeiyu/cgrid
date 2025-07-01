import { useRef, useState, useCallback, useEffect, useMemo } from 'react';
import { EditOutlined, DeleteOutlined, PlusOutlined } from '@ant-design/icons';
import type { MenuProps } from 'antd';
import type { MutableRefObject } from 'react';
import { Dropdown, Input, Button, Row, Col, Modal, message, Empty, Space } from 'antd';
import { formatMessage } from '@/utils';
import SchemaForm, { FormTypeEnum } from '@/components/SchemaForm';
import type { ProFormInstance } from '@ant-design/pro-components';
import { editData, addData, getData, deleteData } from './service';
import type { ProColumns } from '@ant-design/pro-components';
import type { ButtonProps } from 'antd';
import type { FilterSaveData } from './helper';
import { useRequest } from 'umi';
import moment from 'moment';
import styles from './index.less';

type FilterSaveProps = {
  filterForm?: MutableRefObject<ProFormInstance | undefined>;
  filterKey?: string | number; // 同一个页面唯一，如果需要共用筛选条件则设置一致
  onFilterValuesChange?: (values: any) => void;
  children?: React.ReactNode;
} & ButtonProps;

const FilterSave = (props: FilterSaveProps) => {
  const { filterForm, filterKey, onFilterValuesChange, children, ...resetButtonProps } = props;
  const pageId = window.location.pathname + `:${filterKey}`;
  const formRef = useRef<ProFormInstance>(null);
  const [open, setOpen] = useState<boolean>(false);
  const [formType, setFormType] = useState(FormTypeEnum.Add);
  const [initialValues, setInitialValues] = useState<FilterSaveData>({});
  const { data, run } = useRequest(getData, {
    manual: true,
  });

  useEffect(() => {
    run({ pageId });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const openAddModal = () => {
    setFormType(FormTypeEnum.Add);
    setOpen(true);
  };

  const openEditModal = (e: any, row: FilterSaveData) => {
    e.stopPropagation();
    setFormType(FormTypeEnum.Edit);
    setInitialValues(row);
    setOpen(true);
  };

  const onDelete = (e: any, id?: number) => {
    e.stopPropagation();
    Modal.confirm({
      title: formatMessage({ id: 'common.delete', defaultMessage: '删除' }),
      content: formatMessage({
        id: 'system.Notice.delete_item_confirm',
        defaultMessage: '确定删除该项吗？',
      }),
      okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
      cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
      onOk: async () => {
        const success = await deleteData({ id });
        if (success) {
          message.success(formatMessage({ id: 'common.del', defaultMessage: '删除成功' }));
          run({ pageId });
        }
      },
    });
  };

  const beforeSubmit = useCallback(
    (formData: FilterSaveData) => {
      if (formType == FormTypeEnum.Add) {
        let param = filterForm?.current?.getFieldsValue() || {};
        const _momentKey: string[] = [];
        Object.keys(param).forEach((key) => {
          if (moment.isMoment(param[key])) {
            // 将 Moment 对象转换为普通 JavaScript 对象
            param[key] = param[key].toObject();
            _momentKey.push(key);
          }
        });
        param._momentKey = _momentKey;
        const transformData = filterForm?.current?.getFieldsFormatValue?.();
        if (Object.keys(transformData).length) {
          param = { ...param, ...transformData };
        }
        formData.param = JSON.stringify(param);
        formData.pageId = pageId;
      } else if (formType == FormTypeEnum.Edit) {
        formData.id = initialValues.id;
      }
    },
    // eslint-disable-next-line react-hooks/exhaustive-deps
    [formType, initialValues],
  );

  const showFilterData = (param: string = '{}') => {
    const filterParams = JSON.parse(param);
    if (filterParams._momentKey.length) {
      filterParams._momentKey.forEach((key: string) => {
        filterParams[key] = moment(filterParams[key]);
      });
      delete filterParams._momentKey;
    }
    filterForm?.current?.setFieldsValue(filterParams);
    onFilterValuesChange && onFilterValuesChange(filterParams);
  };

  const onSuccess = () => {
    run({ pageId });
  };

  const onSearch = (e: any) => {
    const value = e.target.value;
    run({ pageId, name: value });
  };

  const items: MenuProps['items'] = useMemo(() => {
    const searchItem = [
      {
        key: -2,
        label: (
          <Row onClick={(e) => e.stopPropagation()}>
            <Col span={24}>
              <Button icon={<PlusOutlined />} onClick={openAddModal}>
                {formatMessage({ id: 'common.filterSave', defaultMessage: '保存为快捷查询' })}
              </Button>
            </Col>
          </Row>
        ),
      },
      {
        key: -1,
        label: (
          <Row onClick={(e) => e.stopPropagation()}>
            <Col span={24}>
              <Input
                onChange={onSearch}
                allowClear
                placeholder={formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' })}
              />
            </Col>
          </Row>
        ),
      },
    ];
    let filterData: FilterSaveData[] = [];
    if (data && data.length) {
      filterData = data.map((i: FilterSaveData) => {
        i.key = i.id;
        i.label = (
          <Row className={styles.item} align="middle">
            <Col span={18} className={styles.lable} onClick={() => showFilterData(i.param)}>
              {i.name}
            </Col>
            <Col span={6} className={styles.action}>
              <Space size={1}>
                <Button
                  type="link"
                  size="small"
                  icon={<EditOutlined />}
                  onClick={(e) => openEditModal(e, i)}
                />
                <Button
                  type="link"
                  size="small"
                  icon={<DeleteOutlined />}
                  onClick={(e) => onDelete(e, i.id)}
                />
              </Space>
            </Col>
          </Row>
        );
        return i;
      });
    } else {
      filterData = [
        {
          key: -1,
          label: <Empty />,
        },
      ];
    }
    return [...searchItem, ...filterData] as MenuProps['items'];
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [data]);

  const columns: ProColumns[] = [
    {
      title: formatMessage({ id: 'common.name', defaultMessage: '名称' }),
      dataIndex: 'name',
      formItemProps: {
        rules: [
          {
            required: true,
            message: formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }),
          },
        ],
      },
    },
  ];
  return (
    <>
      <Dropdown.Button {...resetButtonProps} menu={{ items }}>
        {children}
      </Dropdown.Button>
      <SchemaForm
        formRef={formRef}
        open={open}
        layoutType="ModalForm"
        onOpenChange={setOpen}
        type={formType}
        columns={columns}
        initialValues={initialValues}
        editData={editData}
        addData={addData}
        beforeSubmit={beforeSubmit}
        onSuccess={onSuccess}
        grid={true}
      />
    </>
  );
};

export default FilterSave;
