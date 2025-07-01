/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-19 18:00:14
 * @LastEditTime: 2025-02-20 16:05:22
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/site/helper.tsx
 */

import PositionSelect from "@/components/PositionSelect";
import { api } from "@/services";
import { formatMessage, isEmpty } from "@/utils";
import { feeOption } from "@/utils/dict";
import { MapTypeEnum } from "@/utils/dictionary";
import { verifyPhone } from "@/utils/reg";
import { PlusOutlined } from "@ant-design/icons";
import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { FormInstance, Upload } from "antd";

const invoiceOption = {
  0: formatMessage({ id: '', defaultMessage: '不可以' }),
  1: formatMessage({ id: '', defaultMessage: '可以' }),
};

const reservationOption = {
  0: formatMessage({ id: '', defaultMessage: '不支持' }),
  1: formatMessage({ id: '', defaultMessage: '支持' }),
};

const facilityOption = {
  0: formatMessage({ id: '', defaultMessage: '免费WiFi' }),
  1: formatMessage({ id: '', defaultMessage: '便利店' }),
  2: formatMessage({ id: '', defaultMessage: '洗车' }),
  3: formatMessage({ id: '', defaultMessage: '厕所' }),
};

const stationTypeOption = {
  0: formatMessage({ id: '', defaultMessage: '公共充电站' }),
  1: formatMessage({ id: '', defaultMessage: '商业充电站' }),
  2: formatMessage({ id: '', defaultMessage: '居住区充电站' }),
  3: formatMessage({ id: '', defaultMessage: '高速公路充电站' }),
  4: formatMessage({ id: '', defaultMessage: '智能充电站' }),
};

const operationOption = {
  0: formatMessage({ id: '', defaultMessage: '停业' }),
  1: formatMessage({ id: '', defaultMessage: '在营' }),
};

const showOption = {
  0: formatMessage({ id: '', defaultMessage: '隐藏' }),
  1: formatMessage({ id: '', defaultMessage: '显示' }),
};

export const columns: ProColumns[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '站点名称' }),
    dataIndex: 'siteName',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '代理商' }),
    dataIndex: 'a',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '区域' }),
    dataIndex: 'b',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '地址' }),
    dataIndex: 'c',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '小程序显示' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '运营状态' }),
    dataIndex: 'e',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电桩总数量' }),
    dataIndex: 'f',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '枪口总数' }),
    dataIndex: 'g',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '使用中枪口' }),
    dataIndex: 'h',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '空闲枪口' }),
    dataIndex: 'i',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '故障枪口' }),
    dataIndex: 'j',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
];

const getValueFromEvent = (e: any) => {
  if (Array.isArray(e)) {
    return e;
  }
  return e?.fileList;
};

const beforeUpload = (form: FormInstance, file: File, field: string) => {
  const formData = new FormData();
  formData.append('file', file);
  const fieldList = field + 'List';
  let photosList: string[];
  if (field === 'photos') {
    photosList = form.getFieldValue(fieldList) || [];
  }
  api.uploadFile(formData).then(({ data }) => {
    if (data?.url) {
      if (field === 'photos') {
        form.setFieldValue(fieldList, [...photosList, { url: data.url }]);
      } else {
        form.setFieldValue(fieldList, [{ url: data.url }]);
      }
    }
  });
  return false;
};

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '站点名称' }),
    dataIndex: 'a',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '站点编号' }),
    dataIndex: 'b',
  },
  {
    title: formatMessage({ id: '', defaultMessage: '地区' }),
    dataIndex: 'c',
    valueType: 'cascader',
    fieldProps: {
      options: [],
      fieldNames: {
        value: 'id',
      },
      changeOnSelect: true,
      showSearch: true,
    },
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
    colProps: {
      span: 24,
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '地址' }),
    dataIndex: 'd',
    renderFormItem: () => {
      return <PositionSelect type={MapTypeEnum.AMap} />;
    },
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
    colProps: {
      span: 24,
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '停车收费信息' }),
    dataIndex: 'e',
    valueType: 'textarea',
    colProps: {
      span: 24,
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '停车收费类型' }),
    dataIndex: 'f',
    valueType: 'radio',
    valueEnum: feeOption,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '是否可以开发票' }),
    dataIndex: 'g',
    valueType: 'radio',
    valueEnum: invoiceOption,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '是否支持预约' }),
    dataIndex: 'h',
    valueType: 'radio',
    valueEnum: reservationOption,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '配套设施' }),
    dataIndex: 'i',
    valueType: 'checkbox',
    valueEnum: facilityOption,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
    colProps: {
      span: 24,
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '负责人' }),
    dataIndex: 'j',
  },
  {
    title: formatMessage({ id: '', defaultMessage: '联系电话' }),
    dataIndex: 'k',
    formItemProps: {
      rules: [
        () => {
          return {
            validator: (_: any, value: string) => {
              if (isEmpty(value)) {
                return Promise.resolve();
              } else if (verifyPhone(value)) {
                return Promise.resolve();
              } else {
                return Promise.reject(
                  formatMessage({ id: '', defaultMessage: '电话格式错误' }),
                );
              }
            },
          };
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电站类型' }),
    dataIndex: 'l',
    valueType: 'select',
    valueEnum: stationTypeOption,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '小程序是否显示' }),
    dataIndex: 'o',
    valueType: 'radio',
    valueEnum: showOption,
    initialValue: 1,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '运营状态' }),
    dataIndex: 'm',
    valueType: 'radio',
    valueEnum: operationOption,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '站点运营时间' }),
    dataIndex: 'n',
    valueType: 'timeRange',
  },
  {
    title: formatMessage({ id: '', defaultMessage: '站点图片' }),
    dataIndex: 'p',
    valueType: 'image',
    formItemProps: {
      valuePropName: 'fileList',
      getValueFromEvent: getValueFromEvent,
      rules: [
        {
          required: true,
        },
      ],
    },
    renderFormItem: (_, __, form) => {
      return <Upload
        name='file'
        accept="image/*"
        maxCount={3}
        beforeUpload={(file) => beforeUpload(form, file, 'photos')}
        listType="picture-card"
      >
        <div>
          <PlusOutlined />
          <div style={{ marginTop: 8 }}>{formatMessage({ id: '', defaultMessage: '上传图片' })}</div>
        </div>
      </Upload>
    },
    colProps: {
      span: 24,
    },
  },
];
