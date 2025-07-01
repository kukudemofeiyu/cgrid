import { createIcon } from '@/utils/IconUtil';
import request from '@/utils/request';
import type { MenuDataItem } from '@umijs/route-utils';

/** 获取当前的用户 GET /getUserInfo */
export async function getUserInfo(options?: Record<string, any>) {
  return Promise.resolve({
    "msg": "操作成功",
    "systemInfo": {
    },
    "code": 200,
    "permissions": [
      "*:*:*"
    ],
    "roles": [
      "admin"
    ],
    "user": {
      "createBy": "admin",
      "createTime": "2023-04-13 14:02:54",
      "updateBy": null,
      "updateTime": null,
      "remark": "管理员",
      "userId": 1,
      "orgId": 100,
      "userName": "admin",
      "nickName": "系统管理员",
      "email": "",
      "phone": "13233212522",
      "sex": "0",
      "status": "0",
      "delFlag": "0",
      "loginIp": "127.0.0.1",
      "loginDate": "2023-04-13T14:02:54.000+08:00",
      "defaultSiteId": 1,
      "orgType": 0,
      "sideType": null,
      "createByName": null,
      "updateByName": null,
      "webConfig": "[0]",
      "noneSiteTypeView": 0,
      "passwordUpdateTime": "2024-12-26 14:30:18",
      "orgName": "系统管理员",
      "permissions": null,
      "appPermissions": null,
      "lang": "zh-CN"
    }
  });
  return request<API.GetUserInfoResult>('/system/user/getInfo', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 退出登录接口 POST /login/outLogin */
export async function logout(options?: Record<string, any>) {
  return request<Record<string, any>>('/auth/logout', {
    method: 'POST',
    ...(options || {}),
  });
}

export async function getRouters(params?: any): Promise<API.GetRoutersResult> {
  // return request('/system/menu/getRouters', {
  //   method: 'GET',
  //   params,
  // });
  return Promise.resolve({
    "code": 200,
    "data": [
      {
        "name": "statistics",
        "path": "statistics",
        "meta": {
          "title": "充电桩统计",
          "icon": "BarChartOutlined"
        }
      }, {
        "name": "site",
        "path": "site",
        "meta": {
          "title": "站点管理",
          "icon": "ShopOutlined"
        }
      }, {
        "path": "/order",
        "meta": {
          "title": "订单管理",
          "icon": "CarryOutOutlined"
        },
        children: [
          {
            path: 'all',
            meta: {
              title: '全部订单',
            }
          },
          {
            path: 'abnormal',
            meta: {
              title: '异常订单',
            }
          },
          {
            path: 'refund',
            meta: {
              title: '退款订单',
            }
          },
          {
            path: 'history',
            meta: {
              title: '历史订单',
            }
          },
          {
            path: 'realtime',
            meta: {
              title: '实时订单',
            }
          },
          {
            path: 'reservation',
            meta: {
              title: '预约订单',
            }
          },
          {
            path: 'seat',
            meta: {
              title: '占位订单',
            }
          },
        ]
      },
      {
        path: 'charge-station',
        meta: {
          title: '充电桩管理',
          icon: 'RobotOutlined',
        },
        children: [
          {
            path: 'simulate',
            meta: {
              title: '模拟充电桩',
            }
          }, {
            path: 'charge',
            meta: {
              title: '充电桩管理',
            }
          }, {
            path: 'gun',
            meta: {
              title: '枪口管理',
            }
          }, {
            path: 'factory',
            meta: {
              title: '设备厂商信息',
            }
          }, {
            path: 'purchase',
            meta: {
              title: '设备采购记录',
            }
          },
        ],
      }, {
        path: "bill-policy",
        meta: {
          title: "计费策略",
          icon: "AccountBookOutlined"
        }
      }, {
        path: "operation",
        meta: {
          title: "经营管理",
          icon: "RadarChartOutlined"
        }
      }, {
        path: 'agent',
        meta: {
          title: '代理商管理',
          icon: 'UserSwitchOutlined',
        },
        children: [
          {
            path: 'agent',
            meta: {
              title: '代理商管理',
            }
          },
          {
            path: 'cash',
            meta: {
              title: '提现记录',
            }
          },
          {
            path: 'platform-seperate',
            meta: {
              title: '平台分账明细',
            }
          },
          {
            path: 'agent-seperate',
            meta: {
              title: '代理商分账明细',
            }
          },
        ]
      }, {
        path: 'financial',
        meta: {
          title: '财务管理',
          icon: 'MoneyCollectOutlined',
        },
        children: [
          {
            path: 'financial',
            meta: {
              title: '财务管理',
            }
          },
          {
            path: 'agent',
            meta: {
              title: '代理商分账汇总',
            }
          },
          {
            path: 'person',
            meta: {
              title: '分成者分账汇总',
            }
          },
          {
            path: 'charge',
            meta: {
              title: '充电桩分账汇总',
            }
          },
        ]
      }, {
        path: 'divider',
        meta: {
          title: '分成者管理',
          icon: 'ApartmentOutlined',
        },
        children: [
          {
            path: 'divider',
            meta: {
              title: '分成者管理',
            }
          },
          {
            path: 'proportion',
            meta: {
              title: '分成比例设置',
            }
          },
        ]
      }, {
        path: "invoice",
        meta: {
          title: "发票管理",
          icon: "ScheduleOutlined"
        }
      }, {
        path: 'register-user',
        meta: {
          title: '注册用户管理',
          icon: 'TeamOutlined',
        },
        children: [
          {
            path: 'divider',
            meta: {
              title: '注册用户',
            }
          },
          {
            path: 'recharge-record',
            meta: {
              title: '充值记录',
            }
          },
          {
            path: 'car',
            meta: {
              title: '车辆管理',
            }
          },
          {
            path: 'system-recharge',
            meta: {
              title: '系统充值',
            }
          },
          {
            path: 'withdraw',
            meta: {
              title: '提现管理',
            }
          },
          {
            path: 'backlist',
            meta: {
              title: '黑名单',
            }
          },
        ]
      }, {
        path: 'three-part',
        meta: {
          title: '互联互通',
          icon: 'DeploymentUnitOutlined',
        },
        children: [
          {
            path: 'operate',
            meta: {
              title: '运营商信息',
            }
          },
          {
            path: 'platform',
            meta: {
              title: '平台信息',
            }
          },
          {
            path: 'site',
            meta: {
              title: '站点信息',
            }
          },
          {
            path: 'interface',
            meta: {
              title: '接口信息',
            }
          },
          {
            path: 'charge',
            meta: {
              title: '充电桩',
            }
          },
          {
            path: 'order',
            meta: {
              title: '订单信息',
            }
          },
        ]
      }, {
        path: 'team-charge',
        meta: {
          title: '团充管理',
          icon: 'UsergroupAddOutlined',
        },
        children: [
          {
            path: 'team',
            meta: {
              title: '团队管理',
            }
          },
          {
            path: 'user',
            meta: {
              title: '团充用户',
            }
          },
          {
            path: 'order',
            meta: {
              title: '团充订单',
            }
          },
          {
            path: 'recharge',
            meta: {
              title: '团充充值明细',
            }
          },
        ]
      }, {
        path: 'log',
        meta: {
          title: '日志管理',
          icon: 'FileSearchOutlined',
        },
        children: [
          {
            path: 'interactive',
            meta: {
              title: '交互日志',
            }
          },
          {
            path: 'charge',
            meta: {
              title: '充电日志',
            }
          },
        ]
      }, {
        path: "alarm",
        meta: {
          title: "告警管理",
          icon: "AlertOutlined"
        }
      }, {
        path: 'market',
        meta: {
          title: '营销管理',
          icon: 'UsergroupAddOutlined',
        },
        children: [
          {
            path: 'site-template',
            meta: {
              title: '站点折扣模板',
            }
          },
          {
            path: 'site-activity',
            meta: {
              title: '站点折扣活动',
            }
          },
          {
            path: 'coupon-template',
            meta: {
              title: '优惠卷模板',
            }
          },
          {
            path: 'coupon-activity',
            meta: {
              title: '优惠卷活动',
            }
          },
          {
            path: 'coupon-detail',
            meta: {
              title: '优惠卷详情',
            }
          },
          {
            path: 'user-group',
            meta: {
              title: '用户组',
            }
          },
        ]
      }, {
        path: "feedback",
        meta: {
          title: "反馈管理",
          icon: "QuestionCircleOutlined"
        }
      }, {
        path: "article",
        meta: {
          title: "文章管理",
          icon: "FileTextOutlined"
        }
      }, {
        path: "website-config",
        meta: {
          title: "网站配置",
          icon: "InsertRowBelowOutlined"
        }
      }, {
        path: 'points',
        meta: {
          title: '积分管理',
          icon: 'HourglassOutlined',
        },
        children: [
          {
            path: 'user',
            meta: {
              title: '用户积分',
            }
          },
          {
            path: 'detail',
            meta: {
              title: '积分明细',
            }
          },
        ]
      }, {
        path: 'system',
        meta: {
          title: '系统管理',
          icon: 'SettingOutlined',
        },
        children: [
          {
            path: 'log',
            meta: {
              title: '操作日志',
            }
          },
          {
            path: 'user',
            meta: {
              title: '用户管理',
            }
          },
          {
            path: 'job',
            meta: {
              title: '岗位管理',
            }
          },
          {
            path: 'menu',
            meta: {
              title: '菜单管理',
            }
          },
        ]
      },
    ],
    "msg": ""
  });
}

export function convertCompatRouters(childrens: API.RoutersMenuItem[]): MenuDataItem[] {
  return (
    childrens &&
    childrens.map((item: API.RoutersMenuItem) => {
      return {
        path: item.path,
        icon: createIcon(item?.meta?.icon?.replace?.('#', 'YTDotOutlined') || 'YTDotOutlined'),
        name: item?.meta?.title,
        children: item.children ? convertCompatRouters(item.children) : undefined,
        hideChildrenInMenu: item.hidden,
        hideInMenu: item.hidden,
        component: item.component,
        authority: item.perms,
        meta: item?.meta || {},
      };
    })
  );
}

export async function getRoutersInfo(params?: any): Promise<MenuDataItem[]> {
  return getRouters(params).then((res) => {
    return convertCompatRouters(res.data);
  });
}

export function getMatchMenuItem(
  path: string,
  menuData: MenuDataItem[] | undefined,
): MenuDataItem[] {
  if (!menuData) return [];
  let items: MenuDataItem[] = [];
  menuData.forEach((item) => {
    if (item.path) {
      if (item.path === path) {
        items.push(item);
        return;
      }
      if (path.length >= item.path?.length) {
        const exp = `${item.path}/*`;
        if (path.match(exp)) {
          if (item.children) {
            const subpath = path.substr(item.path.length + 1);
            const subItem: MenuDataItem[] = getMatchMenuItem(subpath, item.children);
            items = items.concat(subItem);
          } else {
            const paths = path.split('/');
            if (paths.length >= 2 && paths[0] === item.path && paths[1] === 'index') {
              items.push(item);
            }
          }
        }
      }
    }
  });
  return items;
}

export const updateUserLang = (lang: string) => {
  return request(`/system/userLanguage`, {
    method: 'PUT',
    data: { lang: lang },
  });
};
