/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-05-14 15:37:16
 * @LastEditTime: 2024-05-15 17:45:13
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\VideoMonitor\index.tsx
 */

import React, { memo, useCallback, useEffect } from 'react';
import { YTVideoOutlined } from '@/components/YTIcons';
import { useRequest } from 'umi';
import { getVideoMonitorData } from '@/services/station';
import { StatusEnum } from '@/utils/dictionary';
import { Button, message } from 'antd';
import { formatMessage } from '@/utils';
import { isUrl } from '@/utils/reg';

type VideoMonitorType = {
  siteId?: number;
  className?: string;
};

const VideoMonitor: React.FC<VideoMonitorType> = (props) => {
  const { siteId, className } = props;

  const { data, run } = useRequest(getVideoMonitorData, {
    manual: true,
  });

  const onClick = useCallback(() => {
    if (data?.jumpMethod == '0') {
      if (data?.url) {
        if (isUrl(data?.url)) {
          window.open(data?.url, '_blank');
        } else {
          message.error(formatMessage({ id: 'common.1003', defaultMessage: 'Url格式错误' }));
        }
        return;
      }
    } else if (data?.jumpMethod == '1') {
      window.open(`/video-monitor?id=${siteId}`);
      return;
    }
    message.error(formatMessage({ id: 'common.noData', defaultMessage: '暂无数据' }));
  }, [data]);

  useEffect(() => {
    if (siteId) {
      run({ siteId });
    }
  }, [siteId]);

  return (
    <>
      {data?.monitorStatus == StatusEnum.Enable && (
        <Button shape="circle" icon={<YTVideoOutlined />} className={className} onClick={onClick} />
      )}
    </>
  );
};

export default memo(VideoMonitor);
