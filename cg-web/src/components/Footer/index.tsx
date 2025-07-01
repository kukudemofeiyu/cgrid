import { useIntl } from 'umi';
import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-layout';
import { formatMessage } from '@/utils';

export type FooterType = {
  className: string;
};

export default (props: FooterType) => {

  const currentYear = new Date().getFullYear();

  return (
    <DefaultFooter className={props.className} copyright={`${currentYear}`} />
  );
};
