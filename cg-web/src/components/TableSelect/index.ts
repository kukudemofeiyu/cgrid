/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-08 09:12:16
 * @LastEditTime: 2023-06-08 10:09:38
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\TableSelect\index.ts
 */
import TableSelect, {
  TABLESELECT,
  tableSelectValueTypeRender,
  tableSelectValueTypeMap,
} from './TableSelect';
import type { TABLESELECTVALUETYPE } from './TableSelect';
import TableTreeSelect, {
  TABLETREESELECT,
  tableTreeSelectValueTypeRender,
  tableTreeSelectValueTypeMap,
} from './TableTreeSelect';
import type { TABLETREESELECTVALUETYPE } from './TableTreeSelect';
import TableTreeModal, { SelectTypeEnum } from './TableTreeSelect/TableTreeModal';
import type {
  dealTreeDataType,
  BasicDataNode,
  TableTreeModalProps,
} from './TableTreeSelect/TableTreeModal';

export { TABLESELECT, tableSelectValueTypeRender, tableSelectValueTypeMap };
export type { TABLESELECTVALUETYPE };

export {
  TableTreeSelect,
  TABLETREESELECT,
  tableTreeSelectValueTypeRender,
  tableTreeSelectValueTypeMap,
  TableTreeModal,
  SelectTypeEnum,
};

export type { TABLETREESELECTVALUETYPE, dealTreeDataType, BasicDataNode, TableTreeModalProps };
export default TableSelect;
