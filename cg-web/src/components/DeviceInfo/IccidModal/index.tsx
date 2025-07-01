/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-04-16 11:37:13
 * @LastEditTime: 2024-04-16 16:34:12
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceInfo\IccidModal\index.tsx
 */

import DetailDialog from '@/components/DetailDialog';
import React, { useEffect } from 'react';
import { baseInfoItems, packageItems } from './helper';
import { useRequest } from 'umi';
import { getSimInfo } from '@/services/equipment';
import Detail from '@/components/Detail';
import { formatMessage } from '@/utils';

type IccidModalType = {
  iccid?: string;
  open?: boolean;
  onCancel?: () => void;
};

const IccidModal: React.FC<IccidModalType> = (props) => {
  const { iccid, open, onCancel } = props;

  const { data, run } = useRequest(getSimInfo, {
    manual: true,
  });

  useEffect(() => {
    if (open && iccid) {
      run({ iccid });
    }
  }, [iccid, open]);

  return (
    <>
      <DetailDialog
        width={700}
        open={open}
        onCancel={onCancel}
        prepend={
          <Detail.Label
            title={formatMessage({
              id: 'iccid.basicCardInformation',
              defaultMessage: '卡基本信息',
            })}
          />
        }
        detailProps={{
          items: baseInfoItems,
          data,
          column: 2,
        }}
        append={
          <>
            <Detail.Label
              className="mt12"
              title={formatMessage({
                id: 'iccid.cardPackageInformation',
                defaultMessage: '卡套餐信息',
              })}
            />
            <Detail items={packageItems} data={data} column={2} />
          </>
        }
      />
    </>
  );
};

export default IccidModal;
