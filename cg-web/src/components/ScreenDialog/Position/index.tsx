/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-13 13:50:09
 * @LastEditTime: 2024-04-07 17:16:53
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\Position\index.tsx
 */

import React from 'react';
import Dialog from '@/components/Dialog';
import type { BusinessDialogProps } from '@/components/ScreenDialog';
import { formatMessage } from '@/utils';
import { MapTypeEnum } from '@/utils/dictionary';
import AMapPosition from './AMap';
import GooglePosition from './Google';
import GoogleMapContain from '@/components/MapContain/Google';

export type PositionProps = BusinessDialogProps & {
  point: AMap.LngLat;
  mapType?: MapTypeEnum;
};

const Position: React.FC<PositionProps> = (props) => {
  const { point, open, onCancel, model, mapType } = props;

  return (
    <>
      <Dialog
        model={model}
        title={formatMessage({ id: 'siteManage.siteList.siteAddress', defaultMessage: '站点地址' })}
        open={open}
        onCancel={onCancel}
        footer={null}
        destroyOnClose
      >
        {mapType == MapTypeEnum.Google ? (
          <GoogleMapContain>
            <GooglePosition point={point} />
          </GoogleMapContain>
        ) : (
          <AMapPosition point={point} />
        )}
      </Dialog>
    </>
  );
};

export default Position;
