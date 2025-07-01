/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-03-13 19:26:34
 * @LastEditTime: 2024-08-12 16:47:16
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\utils\map.ts
 */
import { isNil } from 'lodash';
import { AmapLang, AutoComStatusEnum, mapAks, MapTypeEnum } from './dictionary';
import { getAreaCodeByAdCode, getLocale, isEmpty } from './index';
import { getLevelInfo } from '@/hooks/map/useGeocoder';
import { getAllLocales } from 'umi';
import { LanguageContentType } from '@/pages/station/stationList/data';
const locales = getAllLocales();
interface IAutoComResult {
  autoComplete: AMap.AutoComplete;
  search: (keyword: string, limit?: number) => Promise<any[]>;
}

interface IGeocoderResult {
  geocoder: AMap.Geocoder;
  getAddress: (lngLat: AMap.LngLat) => Promise<any>;
}

let mapLoadResolve: (value: unknown) => void;
const mapLoadPromise = new Promise((resolve) => {
  mapLoadResolve = resolve;
});
export { mapLoadResolve };
export const mapLoad = () => {
  return mapLoadPromise;
};

let autoComplete: any;
export function getAutoComplete() {
  return new Promise<IAutoComResult>((autoresolve) => {
    mapLoad().then(() => {
      window.AMap.plugin(['AMap.AutoComplete'], () => {
        autoComplete = autoComplete || new window.AMap.Autocomplete({});
        const search = (keyword: string, limit: number = 41) => {
          return new Promise<any[]>((resolve, reject) => {
            autoComplete.search(keyword, (status: AutoComStatusEnum, result: any) => {
              if (status === AutoComStatusEnum.Complete) {
                const arr = result.tips.map((item: any) => {
                  const address = item.district + item.address;
                  return {
                    ...item,
                    label: address.length > limit ? address.substring(0, limit) + '...' : address,
                    value: address,
                    key: item.id,
                  };
                });
                resolve(arr);
              } else if (status === AutoComStatusEnum.NoData) {
                resolve([]);
              } else {
                reject();
              }
            });
          });
        };

        autoresolve({
          autoComplete,
          search,
        });
      });
    });
  });
}

let geocoder: any;
export function getGeocoder(lang?: string) {
  const langProp = isNil(lang) ? lang : lang == 'zh-CN' ? '' : AmapLang.En;
  const amapLang = isNil(langProp) ? (getLocale().isZh ? '' : AmapLang.En) : langProp;
  return new Promise<IGeocoderResult>(async (geoResolve) => {
    await mapLoad().then(() => {
      window.AMap.plugin(['AMap.Geocoder'], () => {
        const getAddress = (point: AMap.LngLat) => {
          return new Promise<any>((resolve, reject) => {
            if (!isEmpty(point.lng) && !isEmpty(point.lat)) {
              getPoint(point.lng, point.lat).then((resPoint) => {
                geocoder = new window.AMap.Geocoder({ lang: amapLang });
                geocoder.getAddress(resPoint, (status: AutoComStatusEnum, result: any) => {
                  if (status === AutoComStatusEnum.Complete) {
                    resolve(result);
                  } else if (status === AutoComStatusEnum.NoData) {
                    resolve({});
                  } else {
                    reject();
                  }
                });
              });
            } else {
              resolve({});
            }
          });
        };

        geoResolve({
          geocoder,
          getAddress,
        });
      });
    });
  });
}

export function getMoveAnimation() {
  return new Promise<void>((moveResolve) => {
    mapLoad().then(() => {
      window.AMap.plugin(['AMap.MoveAnimation'], () => {
        moveResolve();
      });
    });
  });
}

export function getIcon(icon: AMap.IconOptions) {
  const myIcon = new window.AMap.Icon({
    size: icon.size && new window.AMap.Size(icon.size[0], icon.size[1]),
    image: icon.image,
    imageSize: icon.imageSize && new window.AMap.Size(icon.imageSize[0], icon.imageSize[1]),
    imageOffset:
      icon.imageOffset && new window.AMap.Pixel(icon.imageOffset[0], icon.imageOffset[1]),
  });

  return myIcon;
}

export function getMarker(marker: AMap.MarkerOptions, icon?: AMap.IconOptions) {
  return new Promise((resolve) => {
    mapLoad().then(() => {
      const myIcon = icon && getIcon(icon);
      getPoint(marker?.position?.[0], marker?.position?.[1]).then((resPoint) => {
        const myMarker = new window.AMap.Marker({
          position: resPoint,
          icon: marker.icon || myIcon,
          offset: marker.offset && new window.AMap.Pixel(marker.offset[0], marker.offset[1]),
        });
        resolve({ marker: myMarker, icon: myIcon });
      });
    });
  });
}

export function getPoint(lng: number, lat: number) {
  return new Promise<AMap.LngLat>((resolve) => {
    mapLoad().then(() => {
      resolve(new window.AMap.LngLat(lng, lat));
    });
  });
}

export function googleGeoByLanguage(location: { lng: number; lat: number }, language: string) {
  const curGeocoder = google ? new google.maps.Geocoder() : null;
  return curGeocoder
    ?.geocode?.({
      location,
      language,
    })
    ?.then?.((response) => {
      const result = response?.results?.[0]; //address_components
      if (result) {
        const levelInfo = getLevelInfo(result);

        return {
          address: result?.formatted_address,
          point: location,
          countryName: levelInfo.countryName,
          provinceName: levelInfo.provinceName,
          cityName: levelInfo.cityName,
        };
      } else {
        throw new Error('geocoder error');
      }
    });
}

export async function amapGeoByLanguage(location: { lng: number; lat: number }, language: string) {
  return getPoint(location.lng, location.lat).then(async (point) => {
    return getGeocoder(language).then(async ({ getAddress }) => {
      return getAddress(point).then((res) => {
        if (res) {
          if (res.regeocode && res.regeocode.formattedAddress) {
            const adcode = res?.regeocode?.addressComponent?.adcode || '';
            const [countryCode, provinceCode, cityCode] = getAreaCodeByAdCode(adcode);

            return {
              address: res.regeocode.formattedAddress,
              point: location,
              countryCode,
              provinceCode,
              cityCode,
              adcode: res?.regeocode?.addressComponent?.adcode,
            };
          }
        }
      });
    });
  });
}

export async function geoLocalsAddress(
  map: MapTypeEnum,
  location: { lng: number; lat: number },
): Promise<LanguageContentType[]> {
  const funcMap = {
    [MapTypeEnum.AMap]: amapGeoByLanguage,
    [MapTypeEnum.Google]: googleGeoByLanguage,
  };
  const requests: any[] = [];
  locales.map((item: string) => {
    requests.push(funcMap[map](location, item));
  });
  const addrs = await Promise.all(requests);
  return locales.map((item: string, index: number) => ({
    language: item,
    content: addrs[index]?.address,
  }));
}
