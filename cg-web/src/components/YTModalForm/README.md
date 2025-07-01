## 永泰高级弹窗组件（基于阿里 pro-component 二次封装）

> 该组件具备 schema 的方式使用，也支持 ModalForm 的方式使用区别在于是否传入了 layoutType， 如果是 schema，该值应该为“modalForm”。 封装组件的目的是简化重复且风格一直的弹窗表格，方便后续统一维护

### 使用 schema

schema 支持选项式的请求方式

```{ts}
// 使用schema
const columns = [
  {
    title: '初始密码',
    formItemProps: {
      rules: [
        {
          required: true,
          message: '此项为必填项',
        },
      ],
    },
    valueType: 'password',
    dataIndex: 'pw',
    colProps: {
      span: 24,
    },
    width: 'xl',
  },
  {
    title: '安装商',
    dataIndex: 'provider',
    valueType: 'select',
    hideInTable: true,
    requestOption: {
      url: '/accounts/get/provider',
      mapKey: {
        label: 'name',
        value: 'id',
      },
      dataIndex: 'provider',
    },
    width: 150,
  },
]
<YTModalForm<CustomerInfo>
  layoutType={'ModalForm'}
  title={'新建账号'}
  columns={列}
  onFinish={async (values) => {
    await saveCustomerInfo(values);
    return true;
  }}
  request={(param) => {
    return getCustomerInfo(param).then((res) => {
      return res.data;
    });
  }}
  {...props}
/>
```

### 使用 modalForm

与 ModalForm api 一致

```{ts}
// 使用modalForm
<YTModalForm<any>
  title={'test'}
  visible={state}
  onVisibleChange={toggle}
  columns={[]}
  operations={operations}
>
  <ProFormText
    name="name"
    label="签约客户名称"
    tooltip="最长为 24 位"
    placeholder="请输入名称"
  />
  <ProFormText name="company" label="我方公司名称" placeholder="请输入名称" />
</YTModalForm>
```
