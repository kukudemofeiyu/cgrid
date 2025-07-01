import request from '@/utils/request';

export const dayStrategyPage = (params: any) => {
  return request(`/iot/deviceStrategy/dayStrategy/page`, {
    method: 'GET',
    params,
  });
};

export const addDayStrategy = (data: any) => {
  return request(`/iot/deviceStrategy/dayStrategy`, {
    method: 'POST',
    data,
  });
};

export const updataDayStrategy = (data: any) => {
  return request(`/iot/deviceStrategy/dayStrategy`, {
    method: 'PUT',
    data,
  });
};

export const deleteDayStrategy = (data: any) => {
  return request(`/iot/deviceStrategy/dayStrategy`, {
    method: 'DELETE',
    data,
  });
};

export const weekStrategyList = (params: any) => {
  return request(`/iot/deviceStrategy/weekStrategy/list`, {
    method: 'GET',
    params,
  });
};

export const updataWeekStrategy = (data: any) => {
  return request(`/iot/deviceStrategy/weekStrategy`, {
    method: 'POST',
    data,
  });
};

export const monthStrategyList = (params: any) => {
  return request(`/iot/deviceStrategy/monthStrategy/list`, {
    method: 'GET',
    params,
  });
};

export const updataMonthStrategy = (data: any) => {
  return request(`/iot/deviceStrategy/monthStrategy`, {
    method: 'POST',
    data,
  });
};

export const templateList = (params: any) => {
  return request(`/iot/deviceStrategy/template/list`, {
    method: 'GET',
    params,
  });
};

export const addTemplate = (data: any) => {
  return request(`/iot/deviceStrategy/template`, {
    method: 'POST',
    data,
  });
};

export const updataTemplate = (data: any) => {
  return request(`/iot/deviceStrategy/template`, {
    method: 'PUT',
    data,
  });
};

export const deleteTemplate = (data: any) => {
  return request(`/iot/deviceStrategy/template`, {
    method: 'DELETE',
    data,
  });
};
