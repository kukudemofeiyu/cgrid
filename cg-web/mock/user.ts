import { Request, Response } from 'express';
import captchapng from 'captchapng3';

const waitTime = (time: number = 100) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(true);
    }, time);
  });
};

async function getFakeCaptcha(req: Request, res: Response) {
  await waitTime(2000);
  return res.json('captcha-xxx');
}

function guid() {
  return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
    var r = (Math.random() * 16) | 0,
      v = c == 'x' ? r : (r & 0x3) | 0x8;
    return v.toString(16);
  });
}

async function getCaptchaImage(req: Request, res: Response) {
  await waitTime(1000);
  let rand = (Math.random() * 9000 + 1000).toFixed();
  var p = new captchapng(100, 30, rand);
  const img = p.getBase64();
  res.status(200).send({
    code: 200,
    msg: 'success',
    img: img,
    uuid: guid(),
  });
}

const { ANT_DESIGN_PRO_ONLY_DO_NOT_USE_IN_YOUR_PRODUCTION } = process.env;

/**
 * 当前用户的权限，如果为空代表没登录
 * current user access， if is '', user need login
 * 如果是 pro 的预览，默认是有权限的
 */
let access = ANT_DESIGN_PRO_ONLY_DO_NOT_USE_IN_YOUR_PRODUCTION === 'site' ? 'admin' : '';

const getAccess = () => {
  return access;
};

// 代码中会兼容本地 service mock 以及部署站点的静态数据
export default {
  // 支持值为 Object 和 Array
  'GET /api/system/user/getInfo': (req: Request, res: Response) => {
    // if (!getAccess()) {
    //   res.status(401).send({
    //     data: {
    //       isLogin: false,
    //     },
    //     errorCode: '401',
    //     errorMessage: '请先登录！',
    //     success: true,
    //   });
    //   return;
    // }
    res.send({
      msg: '操作成功',
      code: 200,
      permissions: ['*:*:*'],
      roles: ['admin'],
      user: {
        searchValue: null,
        createBy: 'admin',
        createTime: '2021-09-09 17:25:28',
        updateBy: null,
        updateTime: null,
        remark: '管理员',
        params: {},
        userId: 1,
        deptId: 103,
        userName: 'admin',
        nickName: '若依',
        email: 'ry@163.com',
        phonenumber: '15888888888',
        sex: '1',
        avatar: '/static/img/profile.473f5971.jpg',
        status: '0',
        delFlag: '0',
        loginIp: '61.140.198.155',
        loginDate: '2021-11-11T14:03:07.723+0800',
        dept: {
          searchValue: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          remark: null,
          params: {},
          deptId: 103,
          parentId: 101,
          ancestors: null,
          deptName: '研发部门',
          orderNum: '1',
          leader: '若依',
          phone: null,
          email: null,
          status: '0',
          delFlag: null,
          parentName: null,
          children: [],
        },
        roles: [
          {
            searchValue: null,
            createBy: null,
            createTime: null,
            updateBy: null,
            updateTime: null,
            remark: null,
            params: {},
            roleId: 1,
            roleName: '超级管理员',
            roleKey: 'admin',
            roleSort: '1',
            dataScope: '1',
            menuCheckStrictly: false,
            deptCheckStrictly: false,
            status: '0',
            delFlag: null,
            flag: false,
            menuIds: null,
            deptIds: null,
            admin: true,
          },
        ],
        roleIds: null,
        postIds: null,
        roleId: null,
        admin: true,
      },
    });
  },
  // GET POST 可省略
  'GET /api/users': [
    {
      key: '1',
      name: 'John Brown',
      age: 32,
      address: 'New York No. 1 Lake Park',
    },
    {
      key: '2',
      name: 'Jim Green',
      age: 42,
      address: 'London No. 1 Lake Park',
    },
    {
      key: '3',
      name: 'Joe Black',
      age: 32,
      address: 'Sidney No. 1 Lake Park',
    },
  ],
  'POST /api/auth/login': async (req: Request, res: Response) => {
    const { password, username, type } = req.body;
    await waitTime(2000);
    if (password === 'admin123' && username === 'admin') {
      res.send({
        code: 200,
        type,
        currentAuthority: 'admin',
        data: {
          access_token: guid(),
          homeMenu: { menu: 1, siteId: 3 },
        },
      });
      access = 'admin';
      return;
    }
    if (password === '123456' && username === 'user') {
      res.send({
        code: 200,
        type,
        currentAuthority: 'user',
        data: {
          access_token: guid(),
        },
      });
      access = 'user';
      return;
    }
    if (type === 'mobile') {
      res.send({
        code: 200,
        type,
        currentAuthority: 'admin',
        data: {
          access_token: guid(),
        },
      });
      access = 'admin';
      return;
    }

    res.send({
      status: 'error',
      type,
      currentAuthority: 'guest',
    });
    access = 'guest';
  },
  'POST /api/logout': (req: Request, res: Response) => {
    access = '';
    res.send({ data: {}, success: true });
  },

  'GET /api/system/menu/getRouters': {
    msg: '操作成功',
    code: 200,
    data: [
      {
        name: 'Index',
        path: '/index',
        hidden: false,
        component: 'Layout',
        meta: {
          title: '首页',
          icon: 'YTHomeOutlined',
          noCache: false,
          link: null,
        },
      },
      {
        name: 'Monitor',
        path: '/monitor',
        hidden: true,
        redirect: 'noRedirect',
        component: 'Layout',
        alwaysShow: true,
        meta: {
          title: '系统监控',
          icon: 'monitor',
          noCache: false,
          link: null,
        },
        children: [
          {
            name: 'Online',
            path: 'online',
            hidden: false,
            component: 'monitor/online/index',
            meta: {
              title: '在线用户',
              icon: 'online',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Job',
            path: 'job',
            hidden: false,
            component: 'monitor/job/index',
            meta: {
              title: '定时任务',
              icon: 'job',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Http://localhost:8718',
            path: 'http://localhost:8718',
            hidden: false,
            component: 'Layout',
            meta: {
              title: 'Sentinel控制台',
              icon: 'sentinel',
              noCache: false,
              link: 'http://localhost:8718',
            },
          },
          {
            name: 'Http://localhost:8848/nacos',
            path: 'http://localhost:8848/nacos',
            hidden: false,
            component: 'Layout',
            meta: {
              title: 'Nacos控制台',
              icon: 'nacos',
              noCache: false,
              link: 'http://localhost:8848/nacos',
            },
          },
          {
            name: 'Http://localhost:9100/login',
            path: 'http://localhost:9100/login',
            hidden: false,
            component: 'Layout',
            meta: {
              title: 'Admin控制台',
              icon: 'server',
              noCache: false,
              link: 'http://localhost:9100/login',
            },
          },
        ],
      },
      {
        name: 'Tool',
        path: '/tool',
        hidden: true,
        redirect: 'noRedirect',
        component: 'Layout',
        alwaysShow: true,
        meta: {
          title: '系统工具',
          icon: 'tool',
          noCache: false,
          link: null,
        },
        children: [
          {
            name: 'Build',
            path: 'build',
            hidden: false,
            component: 'tool/build/index',
            meta: {
              title: '表单构建',
              icon: 'build',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Gen',
            path: 'gen',
            hidden: false,
            component: 'tool/gen/index',
            meta: {
              title: '代码生成',
              icon: 'code',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Http://localhost:8080/swagger-ui/index.html',
            path: 'http://localhost:8080/swagger-ui/index.html',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '系统接口',
              icon: 'swagger',
              noCache: false,
              link: 'http://localhost:8080/swagger-ui/index.html',
            },
          },
        ],
      },
      {
        name: 'Station',
        path: '/station',
        hidden: false,
        redirect: 'noRedirect',
        component: 'Layout',
        alwaysShow: true,
        meta: {
          title: '站点管理',
          icon: 'YTStationOutlined',
          noCache: false,
          link: null,
        },
        children: [
          {
            name: 'Station-list',
            path: 'station-list',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '站点列表',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Custom-page',
            path: 'custom-page',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '站点定制页',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
        ],
      },
      {
        name: 'Equipment',
        path: '/equipment',
        hidden: false,
        redirect: 'noRedirect',
        component: 'Layout',
        alwaysShow: true,
        meta: {
          title: '设备管理',
          icon: 'YTDeviceOutlined',
          noCache: false,
          link: null,
        },
        children: [
          {
            name: 'Equipment-list',
            path: 'equipment-list',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '设备列表',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Alarm',
            path: 'alarm',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '告警管理',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Operation-log',
            path: 'operation-log',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '设备运行日志',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
        ],
      },
      {
        name: 'Work-order',
        path: '/work-order',
        hidden: false,
        redirect: 'noRedirect',
        component: 'Layout',
        alwaysShow: true,
        meta: {
          title: '任务管理',
          icon: 'YTWorkOrderOutlined',
          noCache: false,
          link: null,
        },
        children: [
          {
            name: 'Install',
            path: 'install',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '安装工单',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Maintenance',
            path: 'maintenance',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '维护工单',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Fault',
            path: 'fault',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '客户报障单',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'ServiceTask',
            path: 'serviceTask',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '服务任务单',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
        ],
      },
      {
        name: 'Data-manage',
        path: '/data-manage',
        hidden: false,
        redirect: 'noRedirect',
        component: 'Layout',
        alwaysShow: true,
        meta: {
          title: '数据管理',
          icon: 'YTDataOutlined',
          noCache: false,
          link: null,
        },
        children: [
          {
            name: 'Search',
            path: 'search',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '数据查询',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Report',
            path: 'report',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '数据报表',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Inspect',
            path: 'inspect',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '智能巡检',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Fault-tracing',
            path: 'fault-tracing',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '故障追溯',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Prediction',
            path: 'prediction',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '数据预测',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Refresh',
            path: 'refresh',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '数据刷新',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'price',
            path: 'price',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '电价中心',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
        ],
      },
      {
        name: 'Partner',
        path: '/partner',
        hidden: false,
        redirect: 'noRedirect',
        component: 'Layout',
        alwaysShow: true,
        meta: {
          title: '安装商管理',
          icon: 'YTPartnerOutlined',
          noCache: false,
          link: null,
        },
        children: [
          {
            name: 'Service',
            path: 'service',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '安装商',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
        ],
      },
      {
        name: 'User-manager',
        path: '/user-manager',
        hidden: false,
        redirect: 'noRedirect',
        component: 'Layout',
        alwaysShow: true,
        meta: {
          title: '用户管理',
          icon: 'YTUserOutlined',
          noCache: false,
          link: null,
        },
        children: [
          {
            name: 'Authority',
            path: 'authority',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '权限管理',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Accounts',
            path: 'accounts',
            hidden: false,
            component: 'accounts/Customer/index',
            meta: {
              title: '账号管理',
              icon: '',
              noCache: false,
              link: null,
            },
          },
        ],
      },
      {
        name: 'Remote-upgrade',
        path: '/remote-upgrade',
        hidden: false,
        redirect: 'noRedirect',
        component: 'Layout',
        alwaysShow: true,
        meta: {
          title: '远程升级',
          icon: 'YTRemoteUpgradeOutlined',
          noCache: false,
          link: null,
        },
        children: [
          {
            name: 'Index',
            path: 'index',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '远程升级',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
        ],
      },
      {
        name: 'System',
        path: '/system',
        hidden: false,
        redirect: 'noRedirect',
        component: 'Layout',
        alwaysShow: true,
        meta: {
          title: '系统管理',
          icon: 'YTSettingOutlined',
          noCache: false,
          link: null,
        },
        children: [
          {
            name: 'Authorization',
            path: 'authorization',
            hidden: false,
            component: 'Layout',
            meta: {
              title: '应用授权',
              icon: '#',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'User',
            path: 'user',
            hidden: false,
            component: 'system/user/index',
            meta: {
              title: '用户管理',
              icon: 'user',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Role',
            path: 'role',
            hidden: false,
            component: 'system/role/index',
            meta: {
              title: '角色管理',
              icon: 'peoples',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Menu',
            path: 'menu',
            hidden: false,
            component: 'system/menu/index',
            meta: {
              title: '菜单管理',
              icon: 'tree-table',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Org',
            path: 'org',
            hidden: false,
            component: 'system/org/index',
            meta: {
              title: '组织管理',
              icon: 'tree',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Post',
            path: 'post',
            hidden: false,
            component: 'system/post/index',
            meta: {
              title: '岗位管理',
              icon: 'post',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Dict',
            path: 'dict',
            hidden: false,
            component: 'system/dict/index',
            meta: {
              title: '字典管理',
              icon: 'dict',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Config',
            path: 'config',
            hidden: false,
            component: 'system/config/index',
            meta: {
              title: '参数设置',
              icon: 'edit',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Notice',
            path: 'notice',
            hidden: false,
            component: 'system/notice/index',
            meta: {
              title: '通知公告',
              icon: 'message',
              noCache: false,
              link: null,
            },
          },
          {
            name: 'Log',
            path: 'log',
            hidden: false,
            redirect: 'noRedirect',
            component: 'ParentView',
            alwaysShow: true,
            meta: {
              title: '日志管理',
              icon: 'log',
              noCache: false,
              link: null,
            },
            children: [
              {
                name: 'Operlog',
                path: 'operlog',
                hidden: false,
                component: 'system/operlog/index',
                meta: {
                  title: '操作日志',
                  icon: 'form',
                  noCache: false,
                  link: null,
                },
              },
              {
                name: 'Logininfor',
                path: 'logininfor',
                hidden: false,
                component: 'system/logininfor/index',
                meta: {
                  title: '登录日志',
                  icon: 'logininfor',
                  noCache: false,
                  link: null,
                },
              },
            ],
          },
        ],
      },
    ],
  },
  'POST /api/register': (req: Request, res: Response) => {
    res.send({ status: 'ok', currentAuthority: 'user', success: true });
  },
  'GET /api/500': (req: Request, res: Response) => {
    res.status(500).send({
      timestamp: 1513932555104,
      status: 500,
      error: 'error',
      message: 'error',
      path: '/base/category/list',
    });
  },
  'GET /api/404': (req: Request, res: Response) => {
    res.status(404).send({
      timestamp: 1513932643431,
      status: 404,
      error: 'Not Found',
      message: 'No message available',
      path: '/base/category/list/2121212',
    });
  },
  'GET /api/403': (req: Request, res: Response) => {
    res.status(403).send({
      timestamp: 1513932555104,
      status: 403,
      error: 'Forbidden',
      message: 'Forbidden',
      path: '/base/category/list',
    });
  },
  'GET /api/401': (req: Request, res: Response) => {
    res.status(401).send({
      timestamp: 1513932555104,
      status: 401,
      error: 'Unauthorized',
      message: 'Unauthorized',
      path: '/base/category/list',
    });
  },

  'GET  /api/login/captcha': getFakeCaptcha,

  'GET  /api/captchaImage': getCaptchaImage,
};
