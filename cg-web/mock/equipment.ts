import { Request, Response } from 'express';

export default {
  'GET /api/equipment/tabs': {
    msg: '操作成功',
    code: 200,
    data: [
      { label: '全部', value: 0 },
      { label: '储能', value: 0 },
      { label: '光伏', value: 0 },
      { label: '充电桩', value: 0 },
      { label: '通信', value: 0 },
      { label: '电气', value: 0 },
    ],
  },
  'GET /api/equipments': {
    msg: '操作成功',
    code: 200,
    rows: [
      {
        id: '1',
        name: '1#储能',
        sn: '12345',
        model: 'Ener Smart 215-P100A',
        type: '工商业储能',
        childSystem: '储能',
        station: '光储充示范站',
        createTime: '2023-05-04 11:00:12',
        onlineTime: '2023-05-05 14:04:12',
        status: 1,
      },
    ],
    total: 1,
  },
  'DELETE /api/station/1': {
    msg: '操作成功',
    code: 200,
  },
  'POST /api/station': {
    msg: '操作成功',
    data: 1,
  },
  'GET /api/operation-logs/page': {
    msg: '操作成功',
    code: 200,
    data: {
      total: 1,
      list: [
        {
          id: 1,
          content: '系统停止指令下发',
          deviceName: '储能',
          siteName: '永泰光储充示范站',
          operator: '王欢喜',
          createTime: '2023-4-12 12:00:00',
        },
      ],
    },
  },
  'GET /api/operation-logs': {
    msg: '操作成功',
    code: 200,
    data: {
      id: 1,
      content: '系统停止指令下发',
      deviceName: '储能',
      siteName: '永泰光储充示范站',
      operator: '王欢喜',
      createTime: '2023-4-12 12:00:00',
    },
  },
};
