/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-17 19:08:26
 * @LastEditTime: 2025-02-19 16:50:28
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/Authority/index.tsx
 */

import React from 'react';
import useAuthority, { AuthorityModeEnum } from '@/hooks/useAuthority';

export type AuthorityProps = {
  code?: string | string[];
  mode?: AuthorityModeEnum;
};

const Authority: React.FC<AuthorityProps> = (props) => {
  const { code, mode, children } = props;

  const { passAuthority, authorityMap } = useAuthority(code, { mode });

  return (
    <>
      {typeof children == 'function' ? (
        children(passAuthority, authorityMap)
      ) : passAuthority ? (
        children
      ) : (
        <></>
      )}
    </>
  );
};

export default Authority;
