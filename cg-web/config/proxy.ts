/**
 * 在生产环境 代理是无法生效的，所以这里没有生产环境的配置
 * -------------------------------
 * The agent cannot take effect in the production environment
 * so there is no configuration of the production environment
 * For details, please see
 * https://pro.ant.design/docs/deploy
 */
export default {
  dev: {
    '/api/': {
      target: 'http://192.168.3.52', //开发环境
      changeOrigin: true,
      secure: false,
      pathRewrite: { '^/api': '/prod-api' },
    },
    '/file/': {
      target: 'http://192.168.3.52', //开发环境
      changeOrigin: true,
      secure: false,
    },
    '/profile/avatar/': {
      target: 'http://192.168.3.52',
      changeOrigin: true,
      secure: false,
    },
  },
  pre: {
    '/api/': {
      target: 'your pre url',
      changeOrigin: true,
      pathRewrite: { '^': '' },
      secure: false,
    },
  },
};
