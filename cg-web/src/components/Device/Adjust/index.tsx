/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-27 16:31:19
 * @LastEditTime: 2024-07-26 14:09:20
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Adjust\index.tsx
 */
import React, { useCallback, useEffect, useMemo, useRef, useState } from 'react';
import { Button, Switch, Tag, Input, message, Row, Col } from 'antd';
import { MessageEventType, RequestCommandEnum } from '@/utils/connection';
import { DeviceProductTypeEnum } from '@/utils/dictionary';
import styles from './index.less';
import { formatMessage, getLocale, saveFile } from '@/utils';
import { useSubscribe } from '@/hooks';
import moment from 'moment';
import useWebsocket from '@/pages/screen/useWebsocket';
import classnames from 'classnames';
import { sendDebug } from '@/services/equipment';
import { ExportOutlined } from '@ant-design/icons';

const { TextArea } = Input;

type AdjustType = {
  className?: string;
  deviceId?: string;
  productTypeId?: string;
};

const Index: React.FC<AdjustType> = (props) => {
  const { deviceId, productTypeId, className = '' } = props;
  const [list, setList] = useState([]);
  const [isScrolle, setIsScrolle] = useState(true);
  const [isSubscribe, setIsSubscribe] = useState(true);
  const [isOpen, setIsOpen] = useState(true);
  const [loading, setLoading] = useState(true);
  const [msg, setMsg] = useState('');
  const { connection } = useWebsocket();

  const speed = 1;
  const warper = useRef();
  const childDom = useRef();

  useEffect(() => {
    let timer;
    if (isScrolle) {
      timer = setInterval(() => {
        if (warper.current.scrollTop < childDom.current.scrollHeight) {
          warper.current.scrollTop = warper.current.scrollTop + 4;
        }
      }, speed);
    }
    return () => {
      clearTimeout(timer);
    };
  }, [isScrolle]);

  const data = useSubscribe(deviceId, isOpen, MessageEventType.DEVICEMSG);
  useEffect(() => {
    if (Object.keys(data).length > 1) {
      setLoading(false);
      const item = {
        msg: data.msg,
        topic: data.topic,
        type: data.type, // 0是下行， 1是上行
        time: moment().format(getLocale().dateTimeFormat + '.SSS'),
      };
      list.push(item);
      setList([...list]);
    }
  }, [data]);

  const hoverHandler = (flag: boolean) => setIsScrolle(flag);

  const clearList = () => {
    setList([]);
  };

  const stopGet = (flag: boolean) => {
    if (flag) {
      // 打开
      setLoading(true);
      setIsSubscribe(true);
      setIsOpen(true);
    } else {
      // 关闭
      connection.sendMessage({
        data: {
          command: RequestCommandEnum.UNSUBSCRIBE,
          params: [
            {
              device: deviceId,
            },
          ],
        },
        type: MessageEventType.DEVICEMSG,
      });
      setIsOpen(false);
      setIsSubscribe(false);
    }
  };

  const onChange = (e) => {
    setMsg(e.target.value);
  };
  const sendMessage = () => {
    if (!msg.trim()) return;
    const params = {
      deviceId,
      data: msg,
    };
    sendDebug(params).then((res) => {
      if (+res.code === 200) {
        message.success('发送成功！');
        setMsg('');
      }
    });
  };

  const items = useMemo(() => {
    return list.slice(list.length > 500 ? list.length - 500 : 0, list.length).map((item, index) => (
      <div
        key={index}
        className={classnames(styles.item, item.type ? styles.blue : '')}
        onMouseOver={() => hoverHandler(false)}
        onMouseOut={() => hoverHandler(true)}
      >
        <div>
          <Tag color={['#87d068', '#108ee9'][item.type]}>
            {item.type
              ? formatMessage({ id: 'device.1010', defaultMessage: '上行' })
              : formatMessage({ id: 'device.1011', defaultMessage: '下行' })}
          </Tag>
          <span>{item.time}</span>
        </div>
        <span className={styles.topic}>Topic: {item.topic}</span>
        <div className={styles.msg}>{item.msg}</div>
      </div>
    ));
  }, [list]);

  const exportList = useCallback(() => {
    const txt = list
      .map((item) => {
        return [
          (item.type
            ? formatMessage({ id: 'device.1010', defaultMessage: '上行' })
            : formatMessage({ id: 'device.1011', defaultMessage: '下行' })) +
            ' ' +
            item.time,
          'Topic:' + item.topic,
          item.msg,
        ].join('\n');
      })
      .join('\n\n');
    const blob = new Blob([txt], { type: 'text/plain;charset=utf-8' });
    saveFile(
      blob,
      formatMessage({ id: 'debug.communicationMessage', defaultMessage: '通信报文' }),
      '.txt',
    );
  }, [list]);

  useEffect(() => {
    clearList();
    return () => {
      connection.sendMessage({
        data: {
          command: RequestCommandEnum.UNSUBSCRIBE,
          params: [
            {
              device: deviceId,
            },
          ],
        },
        type: MessageEventType.DEVICEMSG,
      });
    };
  }, [deviceId]);

  return (
    <>
      <Row className={`${styles.adjust} mb24 ${className}`} gutter={24}>
        <Col
          span={
            [DeviceProductTypeEnum.ChargeMaster, DeviceProductTypeEnum.ChargeTerminal].includes(
              productTypeId,
            )
              ? 16
              : 24
          }
        >
          <div className={styles.message}>
            <div className={styles.title}>
              <div>{formatMessage({ id: 'device.systemMessage', defaultMessage: '监听' })}</div>
              <div className="flex1">
                {loading ? (
                  <Switch loading checked={isSubscribe} onChange={stopGet} />
                ) : (
                  <Switch checked={isSubscribe} onChange={stopGet} />
                )}
              </div>
              <Button type="primary" icon={<ExportOutlined />} onClick={exportList}>
                {formatMessage({ id: 'common.export', defaultMessage: '导出' })}
              </Button>
              <Button className="ml12" onClick={() => clearList()}>
                {formatMessage({ id: 'common.clear', defaultMessage: '清空' })}
              </Button>
            </div>
            <div className={styles.parent} ref={warper}>
              <div className={styles.child} ref={childDom}>
                {items}
              </div>
            </div>
          </div>
        </Col>
        {/* 充电桩 */}
        {[DeviceProductTypeEnum.ChargeMaster, DeviceProductTypeEnum.ChargeTerminal].includes(
          productTypeId,
        ) && (
          <Col span={8}>
            <div className={styles.send}>
              <div className={styles.title}>
                <div>{formatMessage({ id: 'device.sendMessage', defaultMessage: '发送信息' })}</div>
                <Button onClick={sendMessage}>
                  {formatMessage({ id: 'device.send', defaultMessage: '发送' })}
                </Button>
              </div>
              <div className={styles.data}>
                <TextArea rows={4} onChange={onChange} value={msg} />
              </div>
            </div>
          </Col>
        )}
      </Row>
    </>
  );
};

export default Index;
