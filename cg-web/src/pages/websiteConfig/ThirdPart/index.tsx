/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-24 10:51:40
 * @LastEditTime: 2025-02-24 10:51:40
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/websiteConfig/ThirdPart/index.tsx
 */

import SchemaForm from '@/components/SchemaForm';
import { ProFormInstance } from '@ant-design/pro-components';
import React, { memo, useRef } from 'react';
import { formColumns } from './helper';
import { Button } from 'antd';
import { formatMessage } from '@/utils';

const Index: React.FC = () => {

  const formRef = useRef<ProFormInstance>(null);

  return <>
    <div className={`page-form`}>
      <SchemaForm
        formRef={formRef}
        layoutType="Form"
        columns={formColumns}
        submitter={{
          render(props: any) {
            return [
              <Button
                key="submit"
                type="primary"
                onClick={() => {
                  formRef.current?.submit();
                }}
              >
                {formatMessage({ id: '', defaultMessage: '保存' })}
              </Button>,
            ];
          },
        }}
      />
    </div>
  </>;
};

export default memo(Index);