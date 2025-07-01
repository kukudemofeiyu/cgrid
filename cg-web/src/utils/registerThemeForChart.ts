import { registerTheme } from 'bizcharts';
// 注册自己的主题
registerTheme('screen-theme', {
  defaultColor: '#6DC8EC',
  geometries: {
    interval: {
      rect: {
        default: { style: { fill: '#6DC8EC', fillOpacity: 0.95 } },
        active: { style: { stroke: '#5AD8A6', lineWidth: 1 } },
        inactive: { style: { fillOpacity: 0.3, strokeOpacity: 0.3 } },
        selected: {},
      },
    },
  },
});
