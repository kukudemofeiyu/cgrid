import { getAccessToken } from '@/access';
import { eq, remove, uniqueId } from 'lodash';
import { getWebSocketHost } from './location';
import { inflate, deflate, ungzip } from 'pako';

export type receivedMessageCallback = (msg: any) => void;

const enum ExitCode {
  CLOSE = 3001,
  NETWORK_ERROR,
  OTHER_ERROR,
}

const EXIT_REASON = {
  [ExitCode.CLOSE]: 'closed by user',
};

const enum ConnectStatus {
  UNCONNECTED,
  OPEN,
  CLOSE,
  CLOSING,
  CONNECTING,
  SUCCESS,
}

export const enum MessageEventType {
  // 设备实时数据
  DEVICE_REAL_TIME_DATA = 1,
  // 站点告警数据
  DEVICE_EVENT_DATA,
  // 心跳消息
  HEARTBEAT,
  // 提示信息, 连接成功
  TIPS,
  // 设备日志数据
  DEVICE_LOG,
  MONITORDATA,
  NETWORKSTSTUS,
  DEVICEMSG,
  // 设备告警数据
  DeviceAlarm,
}

export enum RequestCommandEnum {
  SUBSCRIBE = 'subscribe',
  UNSUBSCRIBE = 'unsubscribe',
}

export enum RequestMsgType {
  PROPERTY = 'post_properties',
  EVENT = 'post_event',
}

type RequestMessageBodyType = {
  command: RequestCommandEnum;
  device?: string;
  keys?: string[];
  msgType?: RequestMsgType;
  product?: string;
  [key: string]: any;
};

type RequestMessageType = {
  type: MessageEventType;
  data: RequestMessageBodyType;
};

type MessageType<T = string> = {
  type: MessageEventType;
  data: T;
};

const MAX_RETRY = 5;
const RETRY_INTERVAL = 5000;

type InitialOption = {
  url?: string;
  onConnectedSuccess?: () => void;
  onConnectedError?: () => void;
};

type OnOpenType = () => void;

const unzip = (data: string) => {
  const gzipStr = atob(data);
  const originStr = inflate(new Uint8Array(gzipStr.split('').map(char => char.charCodeAt(0))), { to: 'string' });
  return originStr;
};

export class Connection {
  static retryCount = 0;

  static instance: Connection | null = null;

  static connectedStatus: ConnectStatus = ConnectStatus.UNCONNECTED;

  private client: WebSocket | null = null;

  private receivedMessageCallbacks: receivedMessageCallback[] = [];

  private url?: string = '';

  private onConnectedSuccess?: () => void;

  private onConnectedError?: () => void;

  private subscribeServices: RequestMessageType[] = [];

  private clientResolve: any = null;

  private clientReady: Promise<any> | null = null;

  private onOpenCallbacks: OnOpenType[] = [];

  private heartCheck: NodeJS.Timer;

  static getInstance(option?: InitialOption) {
    if (!this.instance) {
      return (this.instance = new Connection(option));
    }
    return this.instance;
  }

  constructor({ url, onConnectedSuccess, onConnectedError }: InitialOption = {}) {
    this.url = url;
    this.onConnectedSuccess = onConnectedSuccess;
    this.onConnectedError = onConnectedError;
    this.initClientReady();
  }

  private initClientReady() {
    this.clientReady = new Promise((resolve) => {
      this.clientResolve = resolve;
    });
  }

  private tryConnectedUntilSuccess() {
    try {
      const token = getAccessToken();
      if (!token) {
        throw Error('Connection: token is invalid');
      }
      Connection.connectedStatus = ConnectStatus.CONNECTING;
      const url = this.url;
      this.client = new WebSocket(
        `${url ? url : `${getWebSocketHost()}/websocket`}/${token}?compress=true`,
      );

      this.client.onerror = (e) => {
        this.initClientReady();
        this.onConnectedError?.();
      };

      this.client.onclose = ({ code, reason }) => {
        clearInterval(this.heartCheck);
        this.initClientReady();
        Connection.connectedStatus = ConnectStatus.CLOSE;
        if (code === ExitCode.CLOSE) {
          this.reset();
        } else if (Connection.retryCount < MAX_RETRY) {
          setTimeout(() => {
            Connection.retryCount++;
            this.tryConnectedUntilSuccess();
          }, RETRY_INTERVAL);
        }
      };

      this.client.onopen = (e) => {
        this.reStartHeartCheck();
        this.onOpenCallbacks?.forEach?.((cb) => cb?.());
        this.clientResolve();
        this.resendSubscribeService();
        this.onConnectedSuccess?.();
      };

      this.client.onmessage = (rawData: MessageEvent<string>) => {
        const data = unzip(rawData.data);
        this.reStartHeartCheck();
        if (!this.receivedMessageCallbacks.length) {
          return;
        }
        let message: MessageType;
        let type;
        try {
          message = JSON.parse(data ?? '{}');
          type = message.type;
        } catch (e) { }
        if (type === MessageEventType.HEARTBEAT || type === MessageEventType.TIPS) {
          // return;
        }
        this.receivedMessageCallbacks.forEach((cb) => {
          cb(message || data);
        });
      };
    } catch (error) { }
  }

  reStartHeartCheck() {
    clearInterval(this.heartCheck);
    this.heartCheck = setInterval(() => {
      this.client?.send?.(JSON.stringify({ data: 'ping', type: MessageEventType.HEARTBEAT }));
    }, 1000 * 30);
  }

  isConnecting() {
    return Connection.connectedStatus !== ConnectStatus.UNCONNECTED;
  }

  reconnect() {
    if (this.isConnecting()) {
      return;
    }
    this.tryConnectedUntilSuccess();
  }

  reset() {
    this.receivedMessageCallbacks.length = 0;
    this.subscribeServices.length = 0;
    Connection.retryCount = 0;
    Connection.connectedStatus = ConnectStatus.UNCONNECTED;
  }

  close() {
    this.client?.close?.(ExitCode.CLOSE, EXIT_REASON[ExitCode.CLOSE]);
  }

  onOpen(cb: OnOpenType) {
    this.onOpenCallbacks.push(cb);
  }

  mock(data: any, time?: number) {
    this.receivedMessageCallbacks.forEach((cb) => {
      cb(data);
    });

    setInterval(
      () => {
        this.receivedMessageCallbacks.forEach((cb) => {
          cb(data);
        });
      },
      time ? time : 15000,
    );
  }

  private recordSubscribeService(curService: RequestMessageType) {
    const hasCache = this.subscribeServices.some((service) => eq(service, curService));
    if (curService?.data?.command === RequestCommandEnum.SUBSCRIBE && !hasCache) {
      this.subscribeServices.push(curService);
    }
    if (curService?.data?.command === RequestCommandEnum.UNSUBSCRIBE && hasCache) {
      remove(this.subscribeServices, (service) => eq(service, curService));
    }
  }

  private resendSubscribeService() {
    if (this.subscribeServices.length) {
      this.subscribeServices.forEach((service) => {
        this.sendMessage(service);
      });
    }
  }

  sendMessage(data: RequestMessageType) {
    this.clientReady?.then(() => {
      this.recordSubscribeService(data);
      this.client?.send(JSON.stringify(data));
    });
  }

  addReceivedMessageCallback(callback: receivedMessageCallback) {
    this.receivedMessageCallbacks.push(callback);
  }

  removeReceivedMessageCallback(callback: receivedMessageCallback) {
    remove(this.receivedMessageCallbacks, (fn) => {
      return fn === callback;
    });
  }
}
