import { Button, Modal } from 'antd';
import type { ParamsType } from '@ant-design/pro-provider';
import { isFunction, isEmpty } from 'lodash';
import type { ReactNode } from 'react';
import type { YTProTableCustomProps } from './typing';
import styles from './index.less';
import type { ProColumns } from '@ant-design/pro-components';
import { formatMessage } from '@/utils';
import { FormattedMessage } from 'umi';

const operationsMap = new Map([
  [
    'onEnterChange',
    (onChange: React.MouseEventHandler<HTMLElement>) => (
      <Button type="link" size="small" key="in" onClick={onChange}>
        <FormattedMessage id="common.enter" defaultMessage="进入" />
      </Button>
    ),
  ],
  [
    'onDetailChange',
    (onChange: React.MouseEventHandler<HTMLElement>) => (
      <Button type="link" size="small" key="detail" onClick={onChange}>
        <FormattedMessage id="common.viewDetail" defaultMessage="查看详情" />
      </Button>
    ),
  ],

  [
    'onEditChange',
    (onChange: React.MouseEventHandler<HTMLElement>) => (
      <Button type="link" size="small" key="edit" onClick={onChange}>
        <FormattedMessage id="common.edit" defaultMessage="编辑" />
      </Button>
    ),
  ],
  [
    'onDeleteChange',
    (
      onChange: React.MouseEventHandler<HTMLElement>,
      content = `${formatMessage({
        id: 'common.confirmDelete',
        defaultMessage: '请确认是否删除',
      })}?`,
    ) => (
      <Button
        type="link"
        size="small"
        key="delete"
        onClick={() => {
          Modal.confirm({
            title: (
              <strong>
                {/* <FormattedMessage id="common.deleteConfirm" defaultMessage="删除确认" /> */}
                {formatMessage({ id: 'common.deleteConfirm', defaultMessage: '删除确认' })}
              </strong>
            ),
            content,
            okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
            cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
            onOk: onChange,
          });
        }}
      >
        <FormattedMessage id="common.delete" defaultMessage="删除" />
      </Button>
    ),
  ],
]);

export default function genDefaultOperation<
  DataType extends Record<string, any>,
  Params extends ParamsType = ParamsType,
  ValueType = 'text',
>(props: YTProTableCustomProps<DataType, Params, ValueType>) {
  const { option = {} } = props;
  const {
    columnsProp = {},
    modalDeleteText,
    renderInterceptor,
    btnInterceptor,
    onEditChange,
    onDetailChange,
    onDeleteChange,
    onEnterChange,
    render,
    renderBack,
  } = option;

  const operationsCollection = [onEnterChange, onDetailChange, onEditChange, onDeleteChange].filter(
    isFunction,
  );
  const widthLength = operationsCollection.length;

  // 设置默认的option
  const defaultOptionConfig: ProColumns<DataType, ValueType> = {
    title: formatMessage({ id: 'common.operate', defaultMessage: '操作' }),
    dataIndex: 'option',
    valueType: 'option',
    width: widthLength * 50 + 50,
    fixed: 'right',
    ...columnsProp,
    render: (...renderProp) => {
      const renderButtonGroup: ReactNode[] = [];
      if (render) {
        renderButtonGroup.push(render(...renderProp));
      }
      if (isFunction(renderInterceptor)) {
        const showIContinue = renderInterceptor(renderProp[1]);
        if (!showIContinue) {
          return <></>;
        }
      }
      ['onEnterChange', 'onDetailChange', 'onEditChange', 'onDeleteChange'].forEach((buttonKey) => {
        if (btnInterceptor?.(renderProp[1], buttonKey) === false) {
          return;
        }
        const fn = option[buttonKey];
        if (buttonKey && isFunction(fn)) {
          const renderButton = operationsMap.get(buttonKey);
          if (!renderButton) {
            return;
          }

          // // 插入分割线
          // if (renderButtonGroup.length > 0) {
          //   renderButtonGroup.push(<div key={buttonKey} className={styles.divider} />);
          // }

          // 插入需要显示的元素
          renderButtonGroup.push(renderButton(() => fn(...renderProp), modalDeleteText));
        }
      });
      if (renderBack) {
        renderButtonGroup.push(renderBack(...renderProp));
      }
      return <div className={styles.operationWrapper}>{renderButtonGroup}</div>;
    },
  };
  return isEmpty(option) ? '' : defaultOptionConfig;
}
