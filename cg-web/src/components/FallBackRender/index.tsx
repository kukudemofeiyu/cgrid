/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-01-11 11:11:57
 * @LastEditTime: 2024-06-25 14:10:07
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\FallBackRender\index.tsx
 */
import { formatMessage } from '@/utils';
import React from 'react';
import { FallbackProps } from 'react-error-boundary';

type FallBackRenderType = FallbackProps & {
  showMsg?: boolean;
  tipContent?: string;
};

const refresh = () => {
  window.location.reload();
};

const FallBackRender: React.FC<FallBackRenderType> = (props) => {
  const { error, showMsg = true, tipContent } = props;

  return (
    <>
      <div className="px24" role="alert">
        <p>
          <span className="cl-error">
            {tipContent || formatMessage({ id: 'system.1023', defaultMessage: '出错了' })}{' '}
          </span>
          <a onClick={refresh}>
            {formatMessage({ id: 'system.1024', defaultMessage: '请刷新页面' })}
          </a>
        </p>
        {showMsg && <pre className="cl-error">{error.message}</pre>}
      </div>
    </>
  );
};

export default FallBackRender;
