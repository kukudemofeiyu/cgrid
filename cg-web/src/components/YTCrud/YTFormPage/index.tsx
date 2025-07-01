import { useCallback, useRef, useState } from 'react';
import YTProTable from '@/components/ProTable';
import type {
  YTProColumns,
  YTProTableCustomProps,
  YTProTableProps,
} from '@/components/ProTable/typing';
import { YTFormUpdate } from '../YTFormUpdate';
import { FormOperations } from '@/components/YTModalForm/typing';
import { useToggle } from 'ahooks';
import { message } from 'antd';
import type { ActionType, ProColumns } from '@ant-design/pro-components';
import type { FormUpdateProps } from '../YTFormUpdate';
import { FormReadComposeProps, FormReadProps, YTFormRead } from '../YTFormRead';

export type FormListProps<
  FormListItemType,
  FormListParamsType,
  FormUpdateType,
  FormUpdateParamsType,
> = {
  formListField: {
    requestList: YTProTableCustomProps<FormListItemType, FormListItemType>['request'];
    requestDelete: (entity: FormListItemType) => any;
    createButton: {
      buttonText: string;
    };
    modalDeleteText: string;
    rowKey?: string;
    columns: YTProColumns<FormListItemType, FormListParamsType>[];
    YTProTableProps?: YTProTableProps<FormListItemType, FormListParamsType>;
  };
  formUpdateField: FormUpdateProps<FormUpdateType, FormUpdateParamsType>;
  formReadField: FormReadProps<FormUpdateType, FormUpdateParamsType>;
};

export const YTFormList = <
  FormListItemType extends Record<string, any> = any,
  FormListParamsType extends Record<string, any> = any,
  UD extends Record<string, any> = any,
  UP extends Record<string, any> = any,
>(
  props: FormListProps<FormListItemType, FormListParamsType, UD, UP>,
) => {
  const {
    formListField: {
      requestList,
      requestDelete,
      createButton,
      modalDeleteText,
      rowKey = 'id',
      columns,
      YTProTableProps,
    },
    formUpdateField,
    formReadField,
  } = props;
  const [updateModal, { set: setUpdateModal }] = useToggle<boolean>(false);
  const [readModal, { set: setReadModal }] = useToggle<boolean>(false);
  const [operations, setOperations] = useState(FormOperations.CREATE);
  const actionRef = useRef<ActionType>(null);

  const customConfig: YTProTableCustomProps<FormListItemType, any> = {
    toolBarRenderOptions: {
      add: {
        onClick() {
          // setInitialValues({} as FormListItemType);
          setOperations(FormOperations.CREATE);
          setUpdateModal(true);
        },
        text: createButton?.buttonText,
      },
      ...YTProTableProps?.toolBarRenderOptions,
    },
    option: {
      onDeleteChange(_, entity) {
        requestDelete(entity)?.then?.(({ code, data }) => {
          if (code === 200 || data) {
            message.success('删除成功');
            actionRef?.current?.reload?.();
          }
        });
      },
      onEditChange(_, entity) {
        // setInitialValues({ ...entity });
        setOperations(FormOperations.UPDATE);
        setUpdateModal(true);
      },
      onDetailChange(_, entity) {
        // setInitialValues({ ...entity });
        setOperations(FormOperations.READ);
        setReadModal(true);
      },
      modalDeleteText: `您确认要删除该${modalDeleteText}吗？删除之后无法恢复！`,
    },
  };
  const visibleUpdated = operations !== FormOperations.READ;

  const onSuccess = useCallback(() => {
    actionRef?.current?.reload?.();
  }, [actionRef]);

  return (
    <>
      <YTProTable<FormListItemType, FormListParamsType>
        columns={columns}
        actionRef={actionRef}
        request={requestList}
        rowKey={rowKey}
        {...customConfig}
        {...YTProTableProps}
      />
      <YTFormUpdate
        {...{
          operations: operations,
          visible: visibleUpdated && updateModal,
          onVisibleChange: setUpdateModal,
          onSuccess: onSuccess,
          ...formUpdateField,
        }}
      />
      <YTFormRead
        {...{
          operations: operations,
          visible: visibleUpdated && readModal,
          onVisibleChange: setReadModal,
          ...formReadField,
        }}
      />
    </>
  );
};
