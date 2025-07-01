export type ConfigDataType = {
  id?: string | number;
  deviceId?: string;
  type?: string;
  logName?: string;
  logUrl?: string;
  deviceName?: string;
  createTime?: string;
  time?: string;
  logDate?: any;
  downLoadUrl?: string;
};

export type Authority = {
  page: boolean;
  getLog: boolean;
  download: boolean;
  delete: boolean;
  runDelete?: boolean;
  logRecord?: boolean;
};
