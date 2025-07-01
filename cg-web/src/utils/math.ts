import { isNaN } from 'lodash';
import { isEmpty } from '.';

export function keepAnyDecimal(value: any, defaultValue: any = 0, decimal = 2) {
  if (isEmpty(value)) {
    return defaultValue;
  }
  const num = Number(value);
  if (isNaN(num)) {
    return defaultValue;
  }
  return Math.floor(num * Math.pow(10, decimal)) / Math.pow(10, decimal);
}

export function keepTwoDecimalWithUnit(value: any, unit?: string) {
  const processedValue = keepAnyDecimal(value, '--');
  return unit ? processedValue + unit : processedValue;
}

export function keepIntegerWithUnit(value: any, unit?: string) {
  const processedValue = keepAnyDecimal(value, '--', 0);
  return unit ? processedValue + unit : processedValue;
}
