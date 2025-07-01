/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-04-07 10:34:43
 * @LastEditTime: 2024-04-07 10:34:53
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\hooks\map\useGeocoder copy.ts
 */

import { useContext, useEffect, useState } from 'react';
import MapContext from '@/components/MapContain/MapContext';

type useAutocompleteType = {
  onError?: () => void;
  onSuccess?: (result: google.maps.AutocompleteResponse) => void;
  params?: google.maps.AutocompletionRequest;
};

const useAutocomplete = (props: useAutocomplete | undefined) => {
  const [result, setResult] = useState<google.maps.AutocompletePrediction>();
  const { google } = useContext(MapContext);

  const autocomplete = google ? new google.maps.places.AutocompleteService() : null;

  useEffect(() => {
    if (props?.params?.input) {
      autocomplete?.getQueryPredictions?.(props?.params, (response, status) => {
        if (status == 'OK') {
          props?.onSuccess?.(response);
        } else {
          props?.onSuccess?.([]);
        }
      });
    }
  }, [props?.params, props?.onSuccess, props?.onError]);

  return {
    result,
  };
};

export default useAutocomplete;
