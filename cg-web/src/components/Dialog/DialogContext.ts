/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-26 14:11:26
 * @LastEditTime: 2023-06-26 14:11:30
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Dialog\DialogContext.ts
 */
import { createContext } from 'react';

type DialogContextType = {
  model?: string;
};

const DialogContext = createContext<DialogContextType>({ model: '' });

export default DialogContext;
