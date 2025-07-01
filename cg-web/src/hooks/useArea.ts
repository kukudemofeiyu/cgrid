/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-29 09:44:36
 * @LastEditTime: 2025-02-12 11:24:29
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/hooks/useArea.ts
 */
import { useModel } from 'umi';

const useArea = () => {
  const { state } = useModel('area');

  return {
    state,
  };
};

export default useArea;
