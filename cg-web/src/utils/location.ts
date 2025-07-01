import proxy from '../../config/proxy';

const isDev = process.env.NODE_ENV === 'development';
const getWsProtocol = () => {
  const { protocol, hostname } = location;
  if (hostname == 'localhost') {
    return 'wss://';
  }
  if (protocol.match(/https/gi)) {
    return 'wss://';
  }
  return 'ws://';
};
export const getWebSocketHost = () => {
  const host = isDev ? proxy.dev['/api/'].target.split('//')[1] : location.host;
  return getWsProtocol() + host + '/prod-api';
};
