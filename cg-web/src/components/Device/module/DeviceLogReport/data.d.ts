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
  download: boolean;
  statusControl: boolean;
  duration: boolean;
};
