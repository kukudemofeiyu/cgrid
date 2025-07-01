/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-02-28 14:48:10
 * @LastEditTime: 2024-08-12 17:08:48
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\PositionSelect\AMap.tsx
 */

import React, { useState, useEffect, useCallback, useMemo } from 'react';
import { AutoComplete, Row, Col, Input, message } from 'antd';
import MapContain from '@/components/MapContain';
import { Map, Marker } from '@uiw/react-amap';
import type { OptionType } from '@/types';
import { getAutoComplete, getGeocoder, getPoint } from '@/utils/map';
import { debounce } from 'lodash';
import { formatMessage, getAreaCodeByAdCode, getLocale, getPlaceholder, isEmpty } from '@/utils';
import { AmapLang } from '@/utils/dictionary';
import { PositionSelectType } from '.';
import './Amap.less';
const AMapPositionSelect: React.FC<PositionSelectType> = (props) => {
  const { value, onChange, disabled, readonly, className, showHigh = false } = props;

  const [address, setAddress] = useState<React.ReactNode>();
  const [options, setOptions] = useState<OptionType[]>([]);
  const [point, setPoint] = useState<AMap.LngLat>();
  const [inputPoint, setInputPoint] = useState('');
  const [center, setCenter] = useState<AMap.LngLat>();
  const [zoom, setZoom] = useState(11);

  const lang = useMemo(() => {
    return getLocale().isZh ? '' : AmapLang.En;
  }, []);

  useEffect(() => {
    if (point && !isEmpty(point.lat) && !isEmpty(point.lng)) {
      setInputPoint(`${point?.lng},${point?.lat}`);
    }
  }, [point]);

  useEffect(() => {
    setAddress(value?.address || '');
    if (value?.point && !isEmpty(value?.point?.lng) && !isEmpty(value?.point?.lat)) {
      getPoint(value.point?.lng, value.point?.lat).then((res) => {
        if (res) {
          setPoint(res);
          setCenter(res);
          setZoom(17);
        }
      });
    }
  }, [value]);

  const autoSearch = useCallback(
    debounce((data: string) => {
      getAutoComplete().then(({ search }) => {
        search(data).then((result: any) => {
          setOptions(result);
        });
      });
    }, 700),
    [],
  );

  const onSelect = (_: any, data: any) => {
    if (!data.location) {
      message.success(
        formatMessage({ id: 'device.invalidCoordinates', defaultMessage: '坐标无效' }),
      );
      return;
    }
    setPoint(data.location);
    const [countryCode, provinceCode, cityCode] = getAreaCodeByAdCode(data?.adcode || '');
    onChange?.({
      address: data.label,
      point: {
        lng: data.location.lng,
        lat: data.location.lat,
      },
      countryCode,
      provinceCode,
      cityCode,
      adcode: data.adcode,
    });
  };

  const getAddressByPoint = (pointObj?: AMap.LngLat) => {
    if (!pointObj) {
      onChange?.({
        address: '',
        point: {
          lng: '',
          lat: '',
        },
      });
      return;
    }
    getGeocoder().then(({ getAddress }) => {
      getAddress(pointObj).then((res) => {
        if (res) {
          if (res.regeocode && res.regeocode.formattedAddress) {
            const adcode = res?.regeocode?.addressComponent?.adcode || '';
            const [countryCode, provinceCode, cityCode] = getAreaCodeByAdCode(adcode);
            setPoint(pointObj);
            setAddress(res.regeocode.formattedAddress);
            onChange?.({
              address: res.regeocode.formattedAddress,
              point: {
                lng: pointObj.lng,
                lat: pointObj.lat,
              },
              countryCode,
              provinceCode,
              cityCode,
              adcode: res?.regeocode?.addressComponent?.adcode,
            });
          } else {
            message.success(
              formatMessage({ id: 'device.invalidCoordinates', defaultMessage: '坐标无效' }),
            );
          }
        } else {
          message.success(
            formatMessage({ id: 'device.invalidCoordinates', defaultMessage: '坐标无效' }),
          );
        }
      });
    });
  };

  const onSearch = (data: string) => {
    autoSearch(data);
  };

  const onAutoCompleteChange = (data: string) => {
    setAddress(data);
    if (!data) {
      setInputPoint('');
      getAddressByPoint();
    }
  };

  const onClick = (e: any) => {
    if (!disabled && !readonly && e && e.lnglat) {
      getAddressByPoint(e.lnglat);
    }
  };

  const onPointChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value: inputValue } = e.target;
    setInputPoint(inputValue);
  };

  const onBlur = () => {
    const pointObj = inputPoint.split(',');
    if (pointObj.length > 1) {
      try {
        getPoint(Number(pointObj[0]), Number(pointObj[1])).then((res) => {
          getAddressByPoint(res);
        });
      } catch (e) { }
    } else {
      setAddress('');
      getAddressByPoint();
    }
  };

  return (
    <>
      <div className={className}>
        <Row gutter={12}>
          <Col flex="auto">
            {readonly ? (
              // address + inputPoint
              getPlaceholder(address)
            ) : (
              <AutoComplete
                className="mb8 w-full"
                value={address}
                options={options}
                onSelect={onSelect}
                onSearch={onSearch}
                onChange={onAutoCompleteChange}
                placeholder={
                  formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
                  formatMessage({ id: 'common.address', defaultMessage: '地址' })
                }
                disabled={disabled}
              />
            )}
          </Col>
          {readonly ? (
            `${formatMessage({ id: 'device.1020', defaultMessage: '经纬度' })}：${getPlaceholder(
              inputPoint,
            )}`
          ) : (
            <Col flex="200px">
              <Input
                value={inputPoint}
                placeholder={
                  formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
                  formatMessage({ id: 'common.coordinate', defaultMessage: '坐标' }) +
                  ':lng,lat'
                }
                onChange={onPointChange}
                onBlur={onBlur}
                disabled={disabled}
              />
            </Col>
          )}
          {showHigh && (
            <Col className="tx-right">
              {readonly
                ? `${formatMessage({
                  id: 'device.1021',
                  defaultMessage: '海拔高度(米)',
                })}：${getPlaceholder(value?.high)}`
                : ''}
            </Col>
          )}
        </Row>
        <MapContain>
          <Map center={center} zoom={zoom} onClick={onClick} lang={lang}>
            {point && <Marker position={point} />}
          </Map>
        </MapContain>
      </div>
    </>
  );
};

export default AMapPositionSelect;
