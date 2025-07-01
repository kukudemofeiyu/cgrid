export default {
  'GET /api/oss/authorization/list': {
    msg: '操作成功',
    code: 200,
    data: {
      total: 12,
      list: [
        {
          id: 1,
          applicationName: 'xxx平台',
          applicationID: '123',
          secret: 'asf123132132',
          status: 0,
          description: '1231321321321',
          createTime: '2023-4-12 12:00:00',
        },
      ],
    },
  },
};
