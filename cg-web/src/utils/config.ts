/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-17 15:45:18
 * @LastEditTime: 2025-02-19 17:05:45
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/utils/config.ts
 */
import YTIcon from '@/assets/image/icon-yt.png';
import YTLogo from '@/assets/image/logo-yt.png';
import YTLogoUS from '@/assets/image/logo-yt-us.png';
import { getLocale } from '@/utils';
const isZhCN = getLocale().isZhCN;

export const adminAuthority = '*:*:*';

export const defaultSystemInfo = {
  title: isZhCN ? '充电运营平台' : 'YT EMS Cloud',
  icon: YTIcon,
  appStore: 'https://apps.apple.com/app/%E6%B0%B8%E6%B3%B0%E6%96%B0%E8%83%BD%E6%BA%90/id6473716201',
  euChargingAppStore: 'https://apps.apple.com/app/Go%20Charging/id6737306733',
  euChargingGoogleStore: 'https://play.google.com/store/apps/details?id=com.yotai.gocharging',
  logo: isZhCN ? YTLogo : YTLogoUS,
};
