/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-13 14:43:50
 * @LastEditTime: 2023-07-31 15:48:58
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\hooks\useWebsocket.ts
 */
import { Connection } from '@/utils/connection';
import { useEffect } from 'react';

const useWebsocket = (autoClose?: boolean) => {
  const connection = Connection.getInstance({});
  connection.reconnect();

  const close = () => {
    connection.close();
  };

  useEffect(() => {
    window.addEventListener('onbeforeunload', close);
    return () => {
      if (autoClose) {
        connection.close();
      }
      window.removeEventListener('onbeforeunload', close);
    };
  }, [autoClose]);

  return {
    connection,
  };
};
export default useWebsocket;
