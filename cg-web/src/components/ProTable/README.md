ProTable api 基本一致，多了三个点

1. 添加 toolbar 默认的新建按钮，可以修改文案和添加回调
2. 新建四种默认的基本操作，进入，详情，编辑，删除，添加相关回调即可显示

```{ts}
const customConfig: CustomTableProps<AccountListDataType, any> = {
  toolBarRenderOptions: {
    onChange() {},
  },
  option: {
    onDeleteChange() {},
  },
};
```

3. 支持选项式的请求

```{ts}
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
}
```
