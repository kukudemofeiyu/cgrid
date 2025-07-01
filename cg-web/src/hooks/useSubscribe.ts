/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-13 14:44:03
 * @LastEditTime: 2025-01-14 10:51:18
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/hooks/useSubscribe.ts
 */
import { useEffect, useCallback, useState, useMemo } from 'react';
import useWebsocket from './useWebsocket';
import { MessageEventType, RequestCommandEnum } from '@/utils/connection';
import type { EquipPropType, AnyMapType } from '@/utils/dictionary';
import { flatObj, parseToObj } from '@/utils';

const useSubscribe = (
  id: undefined | string | string[],
  open: boolean,
  type = MessageEventType.DEVICE_REAL_TIME_DATA,
) => {

  const ids = useMemo(() => {
    return Array.isArray(id) ? id : id ? [id] : [];
  }, [id]);
  const [data, setData] = useState<AnyMapType>({ ids });
  const { connection } = useWebsocket();

  const onReceivedMessage = useCallback(
    (res: any) => {
      if (type === res?.type && ids.find((item) => item == res?.data?.deviceId)) {
        const { data: msgData } = res;
        try {
          let obj: Record<string, any> = {};
          msgData?.keyValues?.forEach?.((item: EquipPropType) => {
            if (item.type == 'JSON') {
              const value = parseToObj(item.value + '');
              obj = { ...obj, ...flatObj(value, item?.key) };
            }
            obj[item.key] = item.value;
          });
          if (Object.entries(obj).length) {
            setData((prevData) => ({
              ...prevData,
              ...obj,
              [msgData?.deviceId]: {
                ...prevData?.[msgData?.deviceId],
                ...obj,
              },
            }));
          }
        } catch (e) { }
      }
    },
    [ids, type],
  );

  useEffect(() => {
    setData({
      ids,
    });
    connection.onOpen(() => {
      setData({ ids }); //初始化数据，防止告警数量叠加
    });
    if (open && ids.length) {
      connection.sendMessage({
        data: {
          command: RequestCommandEnum.SUBSCRIBE,
          params: ids.map((item) => ({
            device: item,
          })),
        },
        type,
      });
      connection.addReceivedMessageCallback(onReceivedMessage);
    }
    return () => {
      if (ids.length) {
        connection.sendMessage({
          data: {
            command: RequestCommandEnum.UNSUBSCRIBE,
            params: ids.map((item) => ({
              device: item,
            })),
          },
          type,
        });
      }
      connection.removeReceivedMessageCallback(onReceivedMessage);
    };
    // 待优化
  }, [open, ids]);

  return data;
};

export default useSubscribe;
