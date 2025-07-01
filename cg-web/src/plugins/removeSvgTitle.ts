/*
 * Description:当调用打包命令时，去除代码中所有svg图片的title内容,本地打包会改变源文件，提交会比较多，无其他影响
 * @Author: aoshilin
 * @Date : 2024-09-27 11:23:25
 * @LastEditTime: 2024-09-27 11:48:41
 * @LastEditors: aoshilin
 */

import type { IApi } from 'umi';
import { join, extname } from 'path';
import { readdirSync, readFileSync, writeFileSync } from 'fs';

export default (api: IApi) => {
  api.describe({
    key: 'removeSvgTitle',
    config: {
      schema(joi) {
        return joi.object();
      },
    },
  });

  api.onGenerateFiles(() => {
    if (api.env == 'development') {
      return;
    }
    const outputPath = api.paths.absSrcPath;
    // 递归读取目录中的所有文件
    function traverseDir(dir: string): string[] {
      const files: string[] = [];
      const items = readdirSync(dir, { withFileTypes: true });
      items.forEach((item) => {
        const fullPath = join(dir, item.name);
        if (item.isDirectory()) {
          files.push(...traverseDir(fullPath));
        } else if (extname(item.name).toLowerCase() === '.svg') {
          files.push(fullPath);
        }
      });
      return files;
    }

    const files = traverseDir(outputPath || '');

    // 处理普通 SVG 文件
    function processSvgFile(filePath: string) {
      let content = readFileSync(filePath, 'utf-8');
      // 使用正则表达式移除 <title> 标签及其内容
      content = content.replace(/<title[^>]*>[\s\S]*<\/title>/gi, '');
      // 保存修改后的内容
      writeFileSync(filePath, content, 'utf-8');
    }

    // 处理所有 .svg 文件
    for (const file of files) {
      processSvgFile(file);
    }
  });
};
