export default {
  'GET /api/oss/remote-upgrade/list': {
    msg: '操作成功',
    code: 200,
    data: {
      total: 12,
      list: [
        {
          id: 1,
          version: '1.1',
          deviceType: 'smart-215',
          softwarePackage: 'v1.1',
          description: '1231313',
          uploadTime: '2023-4-12 12:00:00',
          uploader: 'zcg',
        },
        {
          id: 2,
          version: '1.2',
          deviceType: 'smart-215',
          softwarePackage: 'v1.2',
          description: '升级了',
          uploadTime: '2023-4-12 12:00:00',
          uploader: 'zcg1',
        },
      ],
    },
  },
};
