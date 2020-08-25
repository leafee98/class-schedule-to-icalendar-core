# Class Schedule To Icalendar (Core)

潜在的毕业设计项目, 在此之前会限制源码级别的使用.

根据指定格式的课程表定义文件的信息, 生成可供日历软件导入的`ics`文件. 本项目是逻辑实现的核心部分.

## 课程表定义文件(试验阶段, 未来有可能会进行格式上的修改)

见[csti-define.md](/doc/csti-define.md)

## 特征

- 项目生成结果符合[rfc 5545](https://tools.ietf.org/html/rfc5545)标准
- 生成结果中, 事件包括事件前的提醒
- 生成结果中, 支持事件名称和事件描述的自定义
- 支持Java库内已有的所有时区

## 使用

编译好的主程序有为命令行程序, 有两个参数可以使用.

```
-i <input file>
-o <output file>
```

如果 `<input file>` 或 `<output file>` 值为短横线或未设置命令行参数, 则表示采用标准输入/输出流.

## 示例

见目录[`/doc/example/`](!https://github.com/leafee98/class-schedule-to-icalendar-core/tree/master/doc/example)

## 待办

[ ] 修正转义不完善的问题(针对`\$`等转义字符)
[ ] 对于可选的留空的配置选项, 使用默认值而不是空字符串作为配置结果

## 许可

允许编译和使用, 但暂时不开放对源码的直接复制使用
