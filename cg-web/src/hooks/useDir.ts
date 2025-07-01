/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-12-18 09:52:18
 * @LastEditTime: 2024-12-18 09:52:18
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/hooks/useDir.ts
 */

import { getLocale, rtlLocale } from "@/utils";
import { useState } from "react";

const useDir = () => {

  const [isRtl] = useState(rtlLocale.includes(getLocale().locale));

  return { isRtl };
};

export default useDir;
