// 它会根据给定的模块名查找并返回该模块的实际文件路径，而不会加载或执行模块。
// 这个方法在需要获取模块的绝对路径时非常有用，尤其是在你需要将模块路径作为参数传递给其他函数或工具时。

export default [require.resolve('./removeSvgTitle')];
