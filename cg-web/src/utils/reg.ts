/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-03 15:44:19
 * @LastEditTime: 2023-12-22 09:43:48
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\utils\reg.ts
 */
import { getLocale } from '@/utils';
const isZh = getLocale().isZh;
const isUS = getLocale().isEnUS;
const isES = getLocale().isEs;
const ZHPhoneReg = /^1\d{10}$/;
const USPhoneReg = /^[2-9]\d{2}-\d{3}-\d{4}$/;
const ESPhoneReg = /^[6,7,9]\d{2}-\d{3}-\d{3}$/;
const secretReg = /^(?![a-zA-Z]+$)(?!\d+$)(?![^\da-zA-Z\s]+$).{8,16}$/;
const hourRange = /^\d{2}:\d{2}-\d{2}:\d{2}$/;
const urlReg = new RegExp(
  '^(https?:\\/\\/)' + // protocol
    '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' + // domain name
    '((\\d{1,3}\\.){3}\\d{1,3}))' + // OR ip (v4) address
    '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*' + // port and path
    '(\\?[;&a-z\\d%_.~+=-]*)?' + // query string
    '(\\#[-a-z\\d_]*)?$', // fragment locator
  'i', // case insensitive
);
const loadErrorReg = /Loading.*failed.*/gi;
const emailReg = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;

export const verifyPhone = (phone: string) => {
  const str = phone ?? '';
  // if (isZh) return ZHPhoneReg.test(str);
  // else if (isUS) return USPhoneReg.test(str);
  // else if (isES) return ESPhoneReg.test(str);
  // else
  return ZHPhoneReg.test(str);
};

export const getPasswordLevel = (password: string) => {
  let Modes = 0;
  for (let i = 0; i < password.length; i++) {
    Modes |= CharMode(password.charCodeAt(i));
  }
  return bitTotal(Modes);

  //CharMode函数
  function CharMode(iN: number) {
    if (iN >= 48 && iN <= 57)
      //数字
      return 1;
    if (iN >= 65 && iN <= 90)
      //大写字母
      return 2;
    if ((iN >= 97 && iN <= 122) || (iN >= 65 && iN <= 90))
      //大小写
      return 4;
    else return 8; //特殊字符
  }

  //bitTotal函数
  function bitTotal(value: number) {
    let num = value;
    let result = 0;
    for (let i = 0; i < 4; i++) {
      if (num & 1) result++;
      num >>>= 1;
    }
    return result;
  }
};

export const verifyPassword = (password: string) => {
  const str = password ?? '';
  return secretReg.test(str);
};

export const isHourRange = (value: string) => {
  return hourRange.test(value ?? '');
};

export const isUrl = (value?: string) => {
  return urlReg.test(value ?? '');
};

export const isLoadError = (value?: string) => {
  return loadErrorReg.test(value ?? '');
};

export const verifyEmail = (email: string) => {
  const str = email ?? '';
  return emailReg.test(str);
};
