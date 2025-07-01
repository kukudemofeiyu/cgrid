/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-31 11:23:15
 * @LastEditTime: 2023-09-21 13:42:36
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\YTProTable\useTableSize.ts
 */
import { useEffect, useState } from 'react';
import { useSize } from 'ahooks';
import { debounce } from 'lodash';
import type { TableProps as RcTableProps } from 'rc-table/lib/Table';
import { isEmpty } from '@/utils';

export function getTableScroll(contain: HTMLDivElement, extraHeight = 78) {
  const tHeader = contain.querySelector('.ant-table-thead');
  let offsetToTopForTHeader = 0;
  if (tHeader) {
    offsetToTopForTHeader = tHeader.getBoundingClientRect().bottom;
  }
  return `calc(100vh - ${offsetToTopForTHeader + extraHeight}px)`;
}

const useTableSize = (
  contain: React.MutableRefObject<HTMLDivElement | undefined>,
  scroll?: RcTableProps['scroll'],
  collapsed?: boolean,
  extraHeight?: number,
) => {
  const [scrollX, setScrollX] = useState<number | undefined>();

  const size = useSize(() => {
    return contain?.current?.querySelector('.ant-table-header');
  });

  useEffect(
    debounce(() => {
      setScrollX((size?.width ?? 1366) - 6);
      const tableBody = contain?.current?.querySelector('.ant-table-body') as any;
      if (isEmpty(scroll?.y)) {
        if (contain?.current && tableBody) {
          const height = getTableScroll(contain.current, extraHeight);
          tableBody.style.height = height;
          tableBody.style.maxHeight = height;
          tableBody.style.overflowY = 'scroll';
        }
      } else {
        if (tableBody) {
          if (typeof scroll?.y === 'number' || typeof scroll?.y === 'undefined') {
            tableBody.style.height = scroll?.y + 'px';
          } else {
            tableBody.style.height = scroll?.y;
          }
          tableBody.style.overflowY = 'scroll';
        }
      }
    }, 300),
    [contain, scroll, size, collapsed, extraHeight],
  );

  return {
    scrollX,
  };
};

export default useTableSize;
