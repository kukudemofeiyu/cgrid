import type { ModalFormProps } from '@ant-design/pro-form';
import type { FormSchema } from '@ant-design/pro-form/lib/components/SchemaForm';

export const enum FormOperations {
  CREATE = 'CREATE',
  READ = 'READ',
  UPDATE = 'UPDATE',
}

export type CustomModalForm = {
  operations: FormOperations;
};

export type YTModalFormProps<T, ValueType> =
  | ModalFormChildProps<T>
  | SchemaModalFormChildProps<T, ValueType>;

export type SchemaModalFormChildProps<T, ValueType> = FormSchema<T, ValueType> & {
  layoutType: 'ModalForm';
} & CustomModalForm;

export type ModalFormChildProps<T = Record<string, any>> = ModalFormProps<T> & {
  layoutType?: '';
} & CustomModalForm;
