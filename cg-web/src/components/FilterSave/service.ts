import request from '@/utils/request';
import type { FilterSaveData } from './helper';

export const getData = (params: FilterSaveData) => {
  return request(`/uc/queryCondition`, {
    method: 'get',
    params,
  });
};

export const editData = (data: FilterSaveData) => {
  return request(`/uc/queryCondition`, {
    method: 'put',
    data,
  });
};
export const addData = (data: FilterSaveData) => {
  return request(`/uc/queryCondition`, {
    method: 'post',
    data,
  });
};

export const deleteData = (data: FilterSaveData) => {
  return request(`/uc/queryCondition`, {
    method: 'delete',
    data,
  });
};
