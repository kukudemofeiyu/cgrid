/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-12-18 11:01:34
 * @LastEditTime: 2024-12-18 14:01:13
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/SelectLang/index.tsx
 */

import { updateUserLang } from '@/services/session';
import { formatMessage, initLocale } from '@/utils';
import { GlobalOutlined } from '@ant-design/icons';
import { message } from 'antd';
import React, { memo, useCallback } from 'react';
import { localeInfo, setLocale, SelectLang as UmiSelectLang } from 'umi';

type SelectLangType = {
  hasLogin?: boolean;
};

const SelectLang: React.FC<React.ComponentProps<typeof UmiSelectLang> & SelectLangType> = (
  props,
) => {
  const { hasLogin = true, ...resetProps } = props;

  const onLangClick = useCallback(
    ({ key }) => {
      if (hasLogin) {
        const messageKey = 'localLoading';
        message.loading({
          content:
            localeInfo[key]?.messages?.['user.localeLoading'] ||
            formatMessage({ id: 'user.localeLoading', defaultMessage: '切换语言中...' }),
          key: messageKey,
        });
        updateUserLang(key)
          .then(({ data }) => {
            if (data) {
              message.success({
                content:
                  localeInfo[key]?.messages?.['user.localeDone'] ||
                  formatMessage({ id: 'user.localeLoading', defaultMessage: '切换完成...' }),
                key: messageKey,
              });
              setLocale(key, true);
            } else {
              message.destroy(messageKey);
            }
          })
          .catch(() => {
            message.destroy(messageKey);
          });
      } else {
        initLocale(key);
      }
    },
    [hasLogin],
  );

  return (
    <>
      {UmiSelectLang && (
        <UmiSelectLang icon={<GlobalOutlined />} onItemClick={onLangClick} {...resetProps} />
      )}
    </>
  );
};

export default memo(SelectLang);
