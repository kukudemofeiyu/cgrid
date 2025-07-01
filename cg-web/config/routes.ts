/* *
 *
 * @author whiteshader@163.com
 * @datetime  2022/02/22
 *
 * */
import { MenuDataItem } from '@ant-design/pro-components';

export type RouterType = Omit<MenuDataItem, 'routes'> & {
  routes?: MenuDataItem[];
};

const routers: RouterType[] = [
  {
    path: '/user',
    layout: false,
    routes: [
      {
        path: 'login',
        layout: false,
        name: 'login',
        component: 'user/login',
      },
    ]
  },
  {
    path: '/order',
    component: '@/layouts/TabsLayout',
    routes: [
      {
        path: 'all',
        name: 'all',
        component: 'order/all',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'abnormal',
        name: 'abnormal',
        component: 'order/abnormal',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'refund',
        name: 'refund',
        component: 'order/refund',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'history',
        name: 'history',
        component: 'order/history',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'realtime',
        name: 'realtime',
        component: 'order/realtime',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'reservation',
        name: 'reservation',
        component: 'order/reservation',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'seat',
        name: 'seat',
        component: 'order/seat',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
    ],
  },
  {
    path: '/charge-station',
    component: '@/layouts/TabsLayout',
    routes: [
      {
        path: 'simulate',
        name: 'simulate',
        component: 'chargeStation/simulate',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      }, {
        path: 'charge',
        name: 'charge',
        component: 'chargeStation/charge',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      }, {
        path: 'gun',
        name: 'gun',
        component: 'chargeStation/gun',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      }, {
        path: 'factory',
        name: 'factory',
        component: 'chargeStation/factory',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      }, {
        path: 'purchase',
        name: 'purchase',
        component: 'chargeStation/purchase',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
    ],
  },
  {
    path: '/agent',
    component: '@/layouts/TabsLayout',
    routes: [
      {
        path: 'agent',
        name: 'agent',
        component: 'agent/agent',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'cash',
        name: 'cash',
        component: 'agent/cash',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'platform-seperate',
        name: 'platformSeperate',
        component: 'agent/platformSeperate',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'agent-seperate',
        name: 'agentSeperate',
        component: 'agent/agentSeperate',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
    ],
  },
  {
    path: '/financial',
    component: '@/layouts/TabsLayout',
    routes: [
      {
        path: 'financial',
        name: 'financial',
        component: 'financial/financial',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'agent',
        name: 'agent',
        component: 'financial/agent',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'person',
        name: 'person',
        component: 'financial/person',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'charge',
        name: 'charge',
        component: 'financial/charge',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
    ],
  },
  {
    path: '/divider',
    component: '@/layouts/TabsLayout',
    routes: [
      {
        path: 'divider',
        name: 'divider',
        component: 'divider/divider',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'proportion',
        name: 'proportion',
        component: 'divider/proportion',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
    ],
  },
  {
    path: '/register-user',
    component: '@/layouts/TabsLayout',
    routes: [
      {
        path: 'divider',
        name: 'userDivider',
        component: 'user/divider',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'recharge-record',
        name: 'rechargeRecord',
        component: 'user/rechargeRecord',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'car',
        name: 'car',
        component: 'user/car',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'system-recharge',
        name: 'systemRecharge',
        component: 'user/systemRecharge',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'withdraw',
        name: 'withdraw',
        component: 'user/withdraw',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'backlist',
        name: 'backlist',
        component: 'user/backlist',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
    ],
  },
  {
    path: '/three-part',
    component: '@/layouts/TabsLayout',
    routes: [
      {
        path: 'operate',
        name: 'operate',
        component: 'threePart/operate',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'platform',
        name: 'platform',
        component: 'threePart/platform',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'site',
        name: 'site',
        component: 'threePart/site',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'interface',
        name: 'interface',
        component: 'threePart/interface',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'charge',
        name: 'charge',
        component: 'threePart/charge',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'order',
        name: 'order',
        component: 'threePart/order',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
    ],
  },
  {
    path: '/team-charge',
    component: '@/layouts/TabsLayout',
    routes: [
      {
        path: 'team',
        name: 'team',
        component: 'teamCharge/team',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'user',
        name: 'user',
        component: 'teamCharge/user',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'order',
        name: 'order',
        component: 'teamCharge/order',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'recharge',
        name: 'recharge',
        component: 'teamCharge/recharge',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
    ],
  },
  {
    path: '/log',
    component: '@/layouts/TabsLayout',
    routes: [
      {
        path: 'interactive',
        name: 'interactive',
        component: 'log/interactive',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'charge',
        name: 'charge',
        component: 'log/charge',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
    ],
  },
  {
    path: '/market',
    component: '@/layouts/TabsLayout',
    routes: [
      {
        path: 'site-template',
        name: 'siteTemplate',
        component: 'market/siteTemplate',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'site-activity',
        name: 'siteActivity',
        component: 'market/siteActivity',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'coupon-template',
        name: 'couponTemplate',
        component: 'market/couponTemplate',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'coupon-activity',
        name: 'couponActivity',
        component: 'market/couponActivity',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'coupon-detail',
        name: 'couponDetail',
        component: 'market/couponDetail',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'user-group',
        name: 'userGroup',
        component: 'market/userGroup',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
    ],
  },
  {
    path: '/points',
    component: '@/layouts/TabsLayout',
    routes: [
      {
        path: 'user',
        name: 'user',
        component: 'points/user',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'detail',
        name: 'detail',
        component: 'points/detail',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
    ],
  },
  {
    path: '/system',
    component: '@/layouts/TabsLayout',
    routes: [
      {
        path: 'log',
        name: 'log',
        component: 'system/log',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'user',
        name: 'user',
        component: 'system/user',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'job',
        name: 'job',
        component: 'system/job',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'menu',
        name: 'menu',
        component: 'system/menu',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
    ],
  },
  {
    path: '/',
    component: '@/layouts/TabsLayout',
    routes: [
      {
        path: 'statistics',
        name: 'statistics',
        component: 'statistics',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,

      },
      {
        path: 'site',
        name: 'site',
        component: 'site',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'bill-policy',
        name: 'billPolicy',
        component: 'billPolicy',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'operation',
        name: 'operation',
        component: 'operation',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'invoice',
        name: 'invoice',
        component: 'invoice',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'alarm',
        name: 'alarm',
        component: 'alarm',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'feedback',
        name: 'feedback',
        component: 'feedback',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'article',
        name: 'article',
        component: 'article',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
      {
        path: 'website-config',
        name: 'websiteConfig',
        component: 'websiteConfig',
        wrappers: ['@/components/KeepAlive'],
        keepAlive: true,
      },
    ],
  },
  {
    component: '404',
  },
];

const getPathLocaleMap = (data: RouterType[], parentPath = ''): Record<string, string> => {
  let pathLocalMap: Record<string, string> = {};
  data?.forEach((item) => {
    const path = item?.path?.startsWith?.('/') ? item?.path : parentPath + '/' + item?.path;
    if (item?.locale) {
      pathLocalMap[path] = item?.locale;
    }
    if (item?.routes && item?.routes?.length) {
      const result = getPathLocaleMap(item.routes, path);
      Object.assign(pathLocalMap, result);
    }
  });
  return pathLocalMap;
};

export default routers;

export { getPathLocaleMap };
