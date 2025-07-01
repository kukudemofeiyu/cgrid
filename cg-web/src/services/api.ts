// @ts-ignore
/* eslint-disable */
import request, { ResponseCommonData } from '@/utils/request';

export type RoleDataType = {
  roleId?: string;
  roleName?: string;
};

export const uploadFile = (formData: any) => {
  return request(`/uc/upload`, {
    method: 'POST',
    data: formData,
  });
};

export const getDeviceSubsystem = () => {
  return request(`/iot/product/subsystemList`, {
    method: 'GET',
  });
};

export const getAreaData = () => {
  return request(`/uc/common/areaCode`, {
    method: 'GET',
  });
};

export const getMaintenancePeople = (params: any) => {
  return request('/oss/site/getMaintenanceStaff', {
    method: 'GET',
    params,
  });
};

export const getRoles = (params: any) => {
  return request<ResponseCommonData<RoleDataType[]>>('/uc/user/role/list', {
    method: 'GET',
    params,
  });
};

/** 此处后端没有提供注释 GET /notices */
export async function getNotices(options?: { [key: string]: any }) {
  return request<API.NoticeIconList>('/notices', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取规则列表 GET /rule */
export async function rule(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.RuleList>('/rule', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 新建规则 PUT /rule */
export async function updateRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/rule', {
    method: 'PUT',
    ...(options || {}),
  });
}

/** 新建规则 POST /rule */
export async function addRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/rule', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 删除规则 DELETE /rule */
export async function removeRule(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/rule', {
    method: 'DELETE',
    ...(options || {}),
  });
}
