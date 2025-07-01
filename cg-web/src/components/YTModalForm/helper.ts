import { FormOperations } from './typing';

export const isRead = (operations: FormOperations) => operations === FormOperations.READ;
export const isCreate = (operations: FormOperations) => operations === FormOperations.CREATE;
