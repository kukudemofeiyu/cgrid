import { Settings as LayoutSettings } from '@ant-design/pro-layout';

const isDev = process.env.NODE_ENV === 'development';
const Settings: LayoutSettings & {
  pwa?: boolean;
  logo?: string;
  tabsLayout?: boolean;
  apiBasePath?: string;
  locale?: string;
  authorityWhiteList?: string[];
} = {
  navTheme: 'light',
  headerTheme: 'light',
  primaryColor: '#3b86ff',
  layout: 'side',
  // splitMenus: true,
  contentWidth: 'Fluid',
  fixedHeader: true,
  fixSiderbar: true,
  colorWeak: false,
  title: '充电运营平台',
  pwa: false,
  iconfontUrl: '',
  tabsLayout: true,
  apiBasePath: isDev ? '/api' : '/prod-api',
  locale: 'zh-CN',
  authorityWhiteList: [],
};

export default Settings;
