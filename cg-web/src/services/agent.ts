import request from '@/utils/request';

export const getAgent = (params: any) => {
  return request(`/agent`, {
    method: 'GET',
    params,
  });
};
