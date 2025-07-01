import { LockOutlined, MobileOutlined, UserOutlined } from '@ant-design/icons';
import { Alert, message, Modal, Row } from 'antd';
import React, { useEffect, useState } from 'react';
import { ProFormCheckbox, ProFormText, LoginForm } from '@ant-design/pro-form';
import { useIntl, history, useModel, useAliveController } from 'umi';
import { login } from '@/services/login';
import BGImg from '@/assets/image/login-bg.svg';
import styles from './index.less';
import { clearSessionToken, setSessionToken } from '@/access';
import { useLocation } from '@/hooks';
import request from '@/utils/request';
//import { getRoutersInfo } from '@/services/session';
import { AntMenuProps, formatMessage, getLocale, getLocaleMenus, getMenus } from '@/utils';
import SelectLang from '@/components/SelectLang';
import AreaConfig from '@/data/areaConfig';

export type QueryParams = {
  redirect?: string;
  lang?: string;
};

const LoginMessage: React.FC<{
  content: string;
}> = ({ content }) => (
  <Alert
    style={{
      marginBottom: 24,
    }}
    message={content}
    type="error"
    showIcon
  />
);
//获取用户权限列表
const getRoutersList = () => {
  return request('/system/menu/getRouters', {
    method: 'GET',
  });
};

const hasPath = (menus: AntMenuProps[], path: string): boolean => {
  let result = false;
  for (let i = 0; i < menus.length; i++) {
    if (menus[i].key == path) {
      result = true;
    } else if (menus[i].children && menus[i].children?.length) {
      result = hasPath(menus[i].children!, path);
    }
    if (result) {
      break;
    }
  }
  return result;
};

const Login: React.FC = () => {
  const location = useLocation<QueryParams>();
  const { refresh: refreshSiteType } = useModel('siteTypes');
  const { dispatch } = useModel('site');
  const { change } = useModel('currentSiteType');

  const [userLoginState, setUserLoginState] = useState<any>({});
  const [type, setType] = useState<string>('account');
  const { initialState, refresh } = useModel('@@initialState');
  const { clear } = useAliveController();
  const { refresh: refreshArea } = useModel('area');

  const [uuid, setUuid] = useState<string>('');

  const intl = useIntl();

  const handleSubmit = async (values: API.LoginParams) => {
    try {
      history.push({
        pathname: '/site',
      });
      return;
      // 登录
      const {
        code,
        data: { access_token: accessToken, homePath = '' },
        msg,
      } = await login({ ...values, uuid, lang: getLocale().locale });
      if (code === 200) {
        localStorage.removeItem('siteId');
        const defaultLoginSuccessMessage = intl.formatMessage({
          id: 'login.1001',
          defaultMessage: '登录成功！',
        });
        const current = new Date();
        const expireTime = current.setTime(current.getTime() + 1000 * 12 * 60 * 60);

        setSessionToken(accessToken, accessToken, expireTime);
        message.success(defaultLoginSuccessMessage);

        let redirectPath =
          location.query?.redirect || homePath || '/site-monitor' || '/index/station';
        const routesList = await getRoutersList();
        const resList = routesList.data;
        const menus = getLocaleMenus(resList);
        const antMenus = menus && getMenus(menus);
        const hasRedirectPath = hasPath(antMenus, redirectPath?.split?.('?')?.[0]);
        if (!hasRedirectPath) {
          redirectPath = antMenus?.[0]?.key || '/index';
        }

        const pathArr = redirectPath.split('?');
        dispatch({ type: 'init' });
        await clear();
        history.push({
          pathname: pathArr[0],
          search: pathArr[1] ? '?' + pathArr[1] : '',
        });
        refresh();
        refreshArea();
        refreshSiteType().then((data) => {
          const result = data?.[0];
          change(result?.value ?? '', result?.monitor ?? '');
        });
        const areaConfig = AreaConfig.getInstance();
        areaConfig.getMoneyUnit();
        return;
      } else {
        clearSessionToken();
        // 如果失败去设置用户错误信息
        setUserLoginState({ status: 'error', type: 'account', massage: msg });
      }
    } catch (error) {
      clearSessionToken();
    }
  };

  const { status, type: loginType, massage } = userLoginState;

  useEffect(() => {
    Modal.destroyAll();
  }, []);

  return (
    <div className={styles.container} style={{ backgroundImage: `url(${BGImg})` }}>
      <div className={styles.lang} data-lang>
        {SelectLang && <SelectLang hasLogin={false} />}
      </div>
      <div className={styles.content}>
        <LoginForm
          // logo={
          //   initialState?.currentUser?.systemInfo?.icon ? (
          //     <img alt="logo" src={initialState?.currentUser?.systemInfo?.icon} />
          //   ) : (
          //     ''
          //   )
          // }
          title={formatMessage({ id: 'l10001', defaultMessage: '登录' })}
          initialValues={{
            autoLogin: true,
          }}
          onFinish={async (values) => {
            await handleSubmit(values as API.LoginParams);
          }}
        >
          {status === 'error' && loginType === 'account' && <LoginMessage content={massage} />}
          {type === 'account' && (
            <>
              <ProFormText
                name="username"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined className={styles.prefixIcon} />,
                }}
                placeholder={intl.formatMessage({
                  id: 'login.1002',
                  defaultMessage: '请输入用户名',
                })}
                rules={[
                  {
                    required: true,
                    message: intl.formatMessage({
                      id: 'login.1002',
                      defaultMessage: '请输入用户名',
                    }),
                  },
                ]}
              />
              <ProFormText.Password
                name="password"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined className={styles.prefixIcon} />,
                }}
                placeholder={intl.formatMessage({
                  id: 'login.1003',
                  defaultMessage: '请输入密码',
                })}
                rules={[
                  {
                    required: true,
                    message: intl.formatMessage({
                      id: 'login.1003',
                      defaultMessage: '请输入密码',
                    }),
                  },
                ]}
              />
              <Row />
            </>
          )}

          {status === 'error' && loginType === 'mobile' && <LoginMessage content="验证码错误" />}
          {type === 'mobile' && (
            <>
              <ProFormText
                fieldProps={{
                  size: 'large',
                  prefix: <MobileOutlined className={styles.prefixIcon} />,
                }}
                name="mobile"
                placeholder={intl.formatMessage({
                  id: 'login.1004',
                  defaultMessage: '请输入手机号！',
                })}
                rules={[
                  {
                    required: true,
                    message: intl.formatMessage({
                      id: 'login.1004',
                      defaultMessage: '请输入手机号！',
                    }),
                  },
                  {
                    pattern: /^1\d{10}$/,
                    message: intl.formatMessage({
                      id: 'login.1005',
                      defaultMessage: '手机号格式错误！',
                    }),
                  },
                ]}
              />
            </>
          )}
          <div
            style={{
              marginBottom: 24,
            }}
          >
            <ProFormCheckbox noStyle name="autoLogin">
              {intl.formatMessage({
                id: 'login.1006',
                defaultMessage: '记住密码',
              })}
            </ProFormCheckbox>
          </div>
        </LoginForm>
      </div>
      {/* <Footer className={styles.footer} /> */}
    </div>
  );
};

export default Login;
