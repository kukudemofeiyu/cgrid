/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-21 16:57:16
 * @LastEditTime: 2025-02-21 17:55:58
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/user/systemRecharge/index.tsx
 */

import SchemaForm, { FormTypeEnum } from '@/components/SchemaForm';
import { ProFormInstance } from '@ant-design/pro-components';
import React, { memo, useRef } from 'react';
import { formColumns } from './helper';
import { Button } from 'antd';
import { formatMessage } from '@/utils';

const Index: React.FC = () => {

  const formRef = useRef<ProFormInstance>(null);

  return <>
    <div className={`p24 page-form`}>
      <SchemaForm
        // layout='horizontal'
        formRef={formRef}
        layoutType="Form"
        columns={formColumns}
        // submitter={false}
        idKey="siteId"
        // editData={editData}
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
                {formatMessage({ id: '', defaultMessage: '立即充值' })}
              </Button>,
            ];
          },
        }}
      />
    </div>
  </>;
};

export default memo(Index);
