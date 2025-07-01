/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-01 10:00:03
 * @LastEditTime: 2024-08-12 16:59:11
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\hooks\map\useGeocoder.ts
 */

import { useContext, useEffect, useState } from 'react';
import MapContext from '@/components/MapContain/MapContext';

type useGeocoderType = {
  onError?: () => void;
  onSuccess?: (result: google.maps.GeocoderResult) => void;
  params?: google.maps.GeocoderRequest;
};

type LevelInfoType = {
  countryName?: string;
  provinceName?: string;
  cityName?: string;
};

export const getLevelInfo = (data: google.maps.GeocoderResult): LevelInfoType => {
  const result: LevelInfoType = {};
  data?.address_components?.forEach?.((item) => {
    if (item?.types?.includes?.('country')) {
      result.countryName = item?.long_name;
    }
    if (item?.types?.includes?.('administrative_area_level_1')) {
      result.provinceName = item?.long_name;
    }
    if (item?.types?.includes?.('administrative_area_level_2')) {
      result.cityName = item?.long_name;
    }
  });
  return result;
};

const useGeocoder = (props: useGeocoderType | undefined) => {
  const [geocoderResult, setGeocoderResult] = useState<google.maps.GeocoderResult>();
  const { google } = useContext(MapContext);

  const geocoder = google ? new google.maps.Geocoder() : null;

  useEffect(() => {
    if (props?.params?.location || props?.params?.address || props?.params?.placeId) {
      geocoder
        ?.geocode?.(props?.params)
        ?.then?.((response) => {
          const result = response?.results?.[0]; //address_components
          if (result) {
            setGeocoderResult(result);
            props?.onSuccess?.(result);
          } else {
            props?.onError?.();
          }
        })
        ?.catch?.(() => {
          props?.onError?.();
        });
    }
  }, [props?.params, props?.onSuccess, props?.onError]);

  return {
    geocoderResult,
  };
};

export default useGeocoder;
