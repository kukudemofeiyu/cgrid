/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-24 11:17:58
 * @LastEditTime: 2025-02-24 11:49:05
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/websiteConfig/Pay/helper.tsx
 */

import Detail from "@/components/Detail";
import { api } from "@/services";
import { formatMessage } from "@/utils";
import { wxInterfaceType } from "@/utils/dict";
import { ProFormColumnsType } from "@ant-design/pro-components";
import { Button, FormInstance, Upload } from "antd";


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
    renderFormItem: () => {
      return (
        <Detail.DotLabel
          title={formatMessage({ id: '', defaultMessage: '微信支付', })}
          className="mb0"
        />
      );
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '商户号' }),
    dataIndex: 'c',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '支付接口类型' }),
    dataIndex: 'interfaceType',
    valueEnum: wxInterfaceType,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    valueType: 'dependency',
    name: ['interfaceType'],
    columns: ({ interfaceType }) => {
      return interfaceType ? [
        {
          title: formatMessage({ id: '', defaultMessage: 'APIv2密钥' }),
          dataIndex: 'e',
          formItemProps: {
            rules: [
              {
                required: true,
              },
            ],
          },
        },
        {
          title: formatMessage({ id: '', defaultMessage: '支付证书cert' }),
          dataIndex: 'f',
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
              name='cert'
              accept=".pem"
              beforeUpload={(file) => beforeUpload(form, file, 'cert')}
            >
              <Button>
                {formatMessage({ id: '', defaultMessage: '上传apiclient_cert.pem文件' })}
              </Button>
            </Upload>
          },
        },
      ] : [
        {
          title: formatMessage({ id: '', defaultMessage: 'APIv3密钥' }),
          dataIndex: 'e1',
          formItemProps: {
            rules: [
              {
                required: true,
              },
            ],
          },
        },
        {
          title: formatMessage({ id: '', defaultMessage: '商户证书序列号' }),
          dataIndex: 'e2',
          formItemProps: {
            rules: [
              {
                required: true,
              },
            ],
          },
        },
        {
          title: formatMessage({ id: '', defaultMessage: '支付证书cert' }),
          dataIndex: 'f',
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
              name='cert'
              accept=".pem"
              beforeUpload={(file) => beforeUpload(form, file, 'cert')}
            >
              <Button>
                {formatMessage({ id: '', defaultMessage: '上传apiclient_cert.pem文件' })}
              </Button>
            </Upload>
          },
        },
        {
          title: formatMessage({ id: '', defaultMessage: '支付证书key' }),
          dataIndex: 'f',
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
              name='key'
              accept=".pem"
              beforeUpload={(file) => beforeUpload(form, file, 'key')}
            >
              <Button>
                {formatMessage({ id: '', defaultMessage: '上传apiclient_key.pem文件' })}
              </Button>
            </Upload>
          },
        },
      ];
    }
  },
  {
    title: formatMessage({ id: '', defaultMessage: '是否启用支付' }),
    dataIndex: 'g',
    valueType: 'switch',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '是否启用退款' }),
    dataIndex: 'h',
    valueType: 'switch',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
];