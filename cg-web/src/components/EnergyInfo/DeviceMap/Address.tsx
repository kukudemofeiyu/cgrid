/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-08-12 14:17:10
 * @LastEditTime: 2024-08-14 11:21:15
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\DeviceMap\Address.tsx
 */

import { useGeocoder } from '@/hooks';
import { formatMessage, getPlaceholder, isEmpty } from '@/utils';
import { MapTypeEnum } from '@/utils/dictionary';
import { getGeocoder, getPoint } from '@/utils/map';
import { useBoolean } from 'ahooks';
import { Button, Spin } from 'antd';
import React, { memo, useCallback, useEffect, useState } from 'react';

type AddressType = {
  lng?: number;
  lat?: number;
  mapType?: MapTypeEnum;
};

const Address: React.FC<AddressType> = (props) => {
  const { lng, lat, mapType } = props;

  const [geocoderParams, setGeocoderParams] = useState<google.maps.GeocoderRequest>();
  const [address, setAddress] = useState<string>();
  const [show, { set: setShow }] = useBoolean(false);
  const [loading, { set: setLoading }] = useBoolean(false);

  const onGeoSuccess = useCallback(
    (result: google.maps.GeocoderResult) => {
      setAddress?.(result?.formatted_address || '');
      setLoading(false);
    },
    [geocoderParams],
  );
  const onGeoError = useCallback(() => {
    setLoading(false);
  }, []);

  useGeocoder({
    params: geocoderParams,
    onSuccess: onGeoSuccess,
    onError: onGeoError,
  });

  const onShowClick = useCallback(() => {
    setShow(true);
    if (!isEmpty(lng) && !isEmpty(lat)) {
      if (mapType == MapTypeEnum.AMap) {
        setLoading(true);
        getPoint(Number(lng), Number(lat))
          .then((point) => {
            getGeocoder()
              .then(({ getAddress }) => {
                getAddress(point)
                  .then((res) => {
                    if (res) {
                      if (res.regeocode && res.regeocode.formattedAddress) {
                        setAddress?.(res.regeocode.formattedAddress || '');
                      }
                    }
                  })
                  .finally(() => {
                    setLoading(false);
                  });
              })
              .finally(() => {
                setLoading(false);
              });
          })
          .finally(() => {
            setLoading(false);
          });
      } else {
        setLoading(true);
        setGeocoderParams({
          location: {
            lng: lng as number,
            lat: lat as number,
          },
        });
      }
    }
  }, [lng, lat, mapType]);

  return (
    <>
      {show ? (
        loading ? (
          <Spin />
        ) : (
          getPlaceholder(address)
        )
      ) : (
        <Button className="pl0" type="link" onClick={onShowClick}>
          {formatMessage({ id: 'device.1022', defaultMessage: '查看地址' })}
        </Button>
      )}
    </>
  );
};

export default memo(Address);
