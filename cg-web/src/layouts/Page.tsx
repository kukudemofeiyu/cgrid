/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-18 17:17:29
 * @LastEditTime: 2023-12-22 15:51:57
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\layouts\Page.tsx
 */

import React from 'react';

export type PageProps = {
  top?: React.ReactNode;
  bottom?: React.ReactNode;
  hasProTable?: boolean;
  className?: string;
};

const Page: React.FC<PageProps> = (props) => {
  const { top, bottom, children, hasProTable = false, className = '' } = props;

  return (
    <>
      {top && <div className="card-wrap p24 mb12">{top}</div>}
      {<div className={`card-wrap ${className} ${hasProTable ? '' : 'p24'}`}>{children}</div>}
      {bottom && <div className="card-wrap p24 mt12">{bottom}</div>}
    </>
  );
};

export default Page;
