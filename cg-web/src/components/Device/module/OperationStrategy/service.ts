import request from '@/utils/request';
export const strategyPush = (data: any) => {
  return request(`/iot/deviceStrategy/strategy/push`, {
    method: 'POST',
    data,
  });
};

export const strategyList = (params: any) => {
  return request(`/iot/deviceStrategy/strategy/list`, {
    method: 'GET',
    params,
  });
};

export const strategyCurve = (params: any) => {
  return request(`/iot/deviceStrategy/strategy/curve`, {
    method: 'GET',
    params,
  });
};

export const paramsTemplateList = (params: any) => {
  return request(`/iot/deviceStrategy/paramsTemplate/list`, {
    method: 'GET',
    params,
  });
};

export const addTemplate = (data: any) => {
  return request(`/iot/deviceStrategy/paramsTemplate`, {
    method: 'POST',
    data,
  });
};
export const updataTemplate = (data: any) => {
  return request(`/iot/deviceStrategy/paramsTemplate`, {
    method: 'PUT',
    data,
  });
};

export const deleteTemplate = (data: any) => {
  return request(`/iot/deviceStrategy/paramsTemplate`, {
    method: 'DELETE',
    data,
  });
};
