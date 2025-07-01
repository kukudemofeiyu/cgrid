/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-08 19:02:03
 * @LastEditTime: 2023-06-08 19:50:38
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\utils\weather.ts
 */
import clearDay from '@/assets/image/screen/weather/clear-day.svg';
import cloudy from '@/assets/image/screen/weather/cloudy.svg';
import partlyCloudyDay from '@/assets/image/screen/weather/partly-cloudy-day.svg';
import overcast from '@/assets/image/screen/weather/overcast.svg';
import windBeaufort0 from '@/assets/image/screen/weather/wind-beaufort-0.svg';
import mist from '@/assets/image/screen/weather/mist.svg';
import wind from '@/assets/image/screen/weather/wind.svg';
import tornado from '@/assets/image/screen/weather/tornado.svg';
import hurricane from '@/assets/image/screen/weather/hurricane.svg';
import dustWind from '@/assets/image/screen/weather/dust-wind.svg';
import fog from '@/assets/image/screen/weather/fog.svg';
import fogDay from '@/assets/image/screen/weather/fog-day.svg';
import partlyCloudyDayFog from '@/assets/image/screen/weather/partly-cloudy-day-fog.svg';
import haze from '@/assets/image/screen/weather/haze.svg';
import rain from '@/assets/image/screen/weather/rain.svg';
import thunderstormsRain from '@/assets/image/screen/weather/thunderstorms-rain.svg';
import drizzle from '@/assets/image/screen/weather/drizzle.svg';
import partlyCloudyDayDrizzle from '@/assets/image/screen/weather/partly-cloudy-day-drizzle.svg';
import partlyCloudyDayHail from '@/assets/image/screen/weather/partly-cloudy-day-hail.svg';
import hail from '@/assets/image/screen/weather/hail.svg';
import sleet from '@/assets/image/screen/weather/sleet.svg';
import snow from '@/assets/image/screen/weather/snow.svg';
import thermometerWarmer from '@/assets/image/screen/weather/thermometer-warmer.svg';
import iconUnknow from '@/assets/image/screen/weather/moon-new.svg';

const weatherMap = new Map([
  ['晴', clearDay],
  ['少云', cloudy],
  ['晴间多云', partlyCloudyDay],
  ['多云', overcast],
  ['阴', overcast],
  ['有风', windBeaufort0],
  ['平静', cloudy],
  ['微风', mist],
  ['和风', mist],
  ['清风', mist],
  ['强风/劲风', wind],
  ['疾风', wind],
  ['大风', wind],
  ['烈风', wind],
  ['风暴', tornado],
  ['狂爆风', hurricane],
  ['飓风', hurricane],
  ['热带风暴', dustWind],
  ['霾', fogDay],
  ['中度霾', partlyCloudyDayFog],
  ['重度霾', fog],
  ['严重霾', haze],
  ['阵雨', rain],
  ['雷阵雨', thunderstormsRain],
  ['雷阵雨并伴有冰雹', thunderstormsRain],
  ['小雨', partlyCloudyDayDrizzle],
  ['中雨', partlyCloudyDayHail],
  ['大雨', hail],
  ['暴雨', rain],
  ['大暴雨', rain],
  ['特大暴雨', rain],
  ['强阵雨', thunderstormsRain],
  ['强雷阵雨', thunderstormsRain],
  ['极端降雨', rain],
  ['毛毛雨/细雨', drizzle],
  ['雨', drizzle],
  ['小雨-中雨', drizzle],
  ['中雨-大雨', hail],
  ['大雨-暴雨', hail],
  ['暴雨-大暴雨', rain],
  ['大暴雨-特大暴雨', rain],
  ['雨雪天气', sleet],
  ['雨夹雪', sleet],
  ['阵雨夹雪', sleet],
  ['冻雨', sleet],
  ['雪', sleet],
  ['阵雪', snow],
  ['小雪', snow],
  ['中雪', snow],
  ['大雪', snow],
  ['暴雪', snow],
  ['小雪-中雪', snow],
  ['中雪-大雪', snow],
  ['大雪-暴雪', snow],
  ['浮尘', fogDay],
  ['扬沙', fogDay],
  ['沙尘暴', dustWind],
  ['强沙尘暴', dustWind],
  ['龙卷风', tornado],
  ['雾', fogDay],
  ['浓雾', fog],
  ['强浓雾', haze],
  ['轻雾', fogDay],
  ['大雾', fog],
  ['特强浓雾', haze],
  ['热', thermometerWarmer],
  ['wind', wind],
  ['未知', iconUnknow],
]);

export default weatherMap;

export { iconUnknow };
