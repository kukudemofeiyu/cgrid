import { useState, useEffect } from 'react';
import { useModel, useRequest } from 'umi';
import { api } from '@/services';
import { get } from '@/utils/request';

export const getDefaultSite = () => {
  return get<{
    id: number;
    name: string;
  }>('/uc/user/defaultSite');
};

const useFetchDefaultSiteId = () => {
  const { data } = useRequest(getDefaultSite);

  return {
    siteId: data?.id,
  };
};
