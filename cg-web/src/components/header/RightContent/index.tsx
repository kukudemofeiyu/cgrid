import { Space } from 'antd';
import React, { useMemo } from 'react';
import { useModel } from 'umi';
import Avatar from './AvatarDropdown';
import styles from './index.less';
import App from '../App';
import OfficialAccount from '../OfficialAccount';
import SelectLang from '../../SelectLang';

export type SiderTheme = 'light' | 'dark';

const GlobalHeaderRight: React.FC = () => {
  const { initialState } = useModel('@@initialState');
  const systemInfo = useMemo(
    () => initialState?.currentUser?.systemInfo || {},
    [initialState?.currentUser?.systemInfo],
  );

  if (!initialState || !initialState.settings) {
    return null;
  }

  const { navTheme, layout } = initialState.settings;
  let className = styles.right;

  if ((navTheme === 'dark' && layout === 'top') || layout === 'mix') {
    className = `${styles.right}  ${styles.dark}`;
  }

  return (
    <Space className={className} size={16}>
      {/* {systemInfo.appDownloadStatus ? <App systemInfo={systemInfo} /> : <></>}
      {systemInfo.officialAccountsStatus ? <OfficialAccount systemInfo={systemInfo} /> : <></>} */}
      <Avatar menu />
      <SelectLang className={`${styles.action} p0`} />
    </Space>
  );
};

export default GlobalHeaderRight;
