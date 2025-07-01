export type ProtectFormType = {
  deviceId?: string;
  realTimeData?: {
    SysEnableSelfStartFunction?: number;
    MunicipalChargingFunctionEnabled?: number;
    EnableOffGridOperationFunction?: number;
    EnableGridConnectionFunction?: number;
    correctionTime?: number;
  };
  onSuccess?: () => void;
};
export type RemoteSettingDataType<T = Record<string, any>> = {
  deviceId?: string;
  input?: T;
  serviceId?: string;
  productId?: string;
  paramConfigType?: string;
  config?: string;
};
