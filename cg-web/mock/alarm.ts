import { Request, Response } from 'express';

export default {
  'GET /api/alarms/page': {
    msg: '操作成功',
    code: 200,
    data: {
      total: 12,
      list: [
        {
          id: 1,
          content: '130号电池温度越限',
          device: 'smart-215',
          station: '光储充示范站',
          status: 1,
          source: '平台告警',
          createTime: '2023-4-12 12:00:00',
          recoveryTime: '2023-4-12 12:00:00',
        },
      ],
    },
  },
  'GET /api/alarms': {
    msg: '操作成功',
    code: 200,
    data: {
      id: 1,
      content: '130号电池温度越限',
      device: 'smart-215',
      station: '光储充示范站',
      status: 1,
      source: '平台告警',
      createTime: '2023-4-12 12:00:00',
      recoveryTime: '2023-4-12 12:00:00',
    },
  },
};
