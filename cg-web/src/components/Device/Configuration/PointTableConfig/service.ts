import { downLoadZip } from '@/utils/downloadfile';
import request from '@/utils/request';
export const download = (deviceId: string) => {
  return downLoadZip(`/iot/jx/exportConfig?deviceId=${deviceId}`);
};

export const importConfig = (data: any) => {
  return request('/iot/jx/importConfig', {
    method: 'POST',
    data,
  });
};
