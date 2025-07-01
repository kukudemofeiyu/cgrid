import { ModalForm } from '@ant-design/pro-form';
import type { ModalFormChildProps } from './typing';

const ModalFormChild = <T = Record<string, any>,>(props: ModalFormChildProps<T>) => {
  const { children, onVisibleChange, visible, ...restProps } = props;

  return (
    <ModalForm onVisibleChange={onVisibleChange} visible={visible} {...restProps}>
      {children}
    </ModalForm>
  );
};
export default ModalFormChild;
