import { Request, Response } from 'express';

export default {
  'GET /api/agent': {
    msg: '操作成功',
    code: 200,
    data: {
      total: 12,
      list: [
        {
          id: 1,
          name: '深圳安装商',
          service: [
            { id: 1, name: '深圳安装商' },
            { id: 2, name: '深圳安装商1' },
          ],
        },
        { id: 2, name: '深圳龙华安装商' },
        { id: 3, name: '深圳光明安装商' },
        { id: 4, name: '深圳安装商' },
        { id: 5, name: '深圳龙华安装商' },
        { id: 6, name: '深圳光明安装商' },
        { id: 7, name: '深圳安装商' },
        { id: 8, name: '深圳龙华安装商' },
        { id: 9, name: '深圳光明安装商' },
        { id: 10, name: '深圳安装商' },
        { id: 11, name: '深圳龙华安装商' },
        { id: 12, name: '深圳光明安装商' },
      ],
    },
  },
};
