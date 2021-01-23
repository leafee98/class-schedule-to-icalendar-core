# JSON 格式课程表描述文件

描述文件遵守 JSON 文件格式, 完整的例子见 [例子2](/doc/example/e2/e2.json)

## 文件形式

```
{
    "global": {},
    "lessons": []
}
```

global: [global 对象](#global-对象)

lessons: [lessons 数组](#lessons-数组)

### global 对象

```
{
    "event-summary-format": "${lessonName}-${location}",
    "event-description-format": "name:${lessonName}\nlocation:${location}\nteacher:${teacher}\ntype:${lessonType}\nremark:${remark}\nschedule:${scheduleFull}",
    "timezone": "Asia/Shanghai",
    "first-day-of-week": 1,
    "semester-start-date": "2020-02-24",
    "reminder-time": ["-15m"],
    "lesson-ranges": [
        "1=08:00:00-08:45:00",
        "2=08:50:00-09:35:00",
    ]
}
```

描述课程表的全局配置, 包括生成日历的事件描述格式和时区以及课程时间相关的配置.


|属性|描述|
|---|---|
| `event-summary-format` | 字符串, 描述生成的事件的名称, 支持[变量代换](#变量代换).
| `event-description-format` | 字符串, 描述生成的事件的详细描述, 支持[变量代换](#变量代换).
| `timezone` | 字符串, 描述时区, 要求是能够被 Java 识别的字符串.|
| `first-day-of-week` | 整数, 范围 `[0-7]`, `0` 和 `7` 都表示星期日.|
| `semester-start-date` | 字符串, 以 `yyyy-mm-dd` 的格式描述开学的那一天, 这一天会所在的周会被作为学期的第一周并开始安排课程.|
| `reminder-time` | 事件的提醒时间, 使用 `+` 或 `-` 号表示在**事件开始**之后或之前的一段时间提醒, `-` 号表示之前.|
| `lesson-ranges` | JSON 数组, 每一个元素均为字符串, 描述每一节课程的起止时间, 格式为 `课程节号=上课时间-下课时间`, 每天有多节课程则需要多个字符串描述.|

> `lesson-ranges` 中一个字符串的样例:
> 
> `1=08:00:00-08:45:00`, 表示每天的第 `1` 节课自 `08:00:00` 开始, 直到 `08:45:00` 时下课.

### lessons 数组

数组中每一个元素均为一个描述课程信息的对象, 该对象格式如下:

```
{
    "name": "软件测试技术",
    "type": "专业必修",
    "teacher": "某教师",
    "location": "某位置",
    "remark": "暂无",
    "schedule": [
        "1-14|1|6-9"
    ]
}
```
|属性|描述
|---|---|
| `name` | 字符串, 描述课程名称.|
| `type` | 字符串, 描述课程的类型, 如专业选修课/专业必修课.|
| `teacher` | 字符串, 该课程的任课教师.|
| `location` | 字符串, 描述上课位置.|
| `remark` | 字符串, 备注, 用来记录一些其他的信息.|
| `schedule` | JSON 数组, 每个元素为一个字符串, 描述上课的周次/星期几/课程的起止节数. 具体格式为 `周次|星期几|课程起止节数` , 三个区域分别可以使用逗号分隔符表示单独添加, 或使用连字符表示范围. 字符串可以有多个, 描述多个不同的规则.|

> 字符串中使用逗号或连字符的例子:
> 
>  `1,3-5|2,4|1-3,9` 表示上课周次为第 1 周和第 3,4,5 周, 每周的周二和周四, 上课的时间为第 1,2,3 节, 还有第 9 节.
>
> 整个数组有多个字符串的例子:
>
> `["1-3,6-9|1|1-3", "4,5|2|4-5"]` 表示第 1 到第 3 周, 第 6 周到第 9 周, 每周一在第 1 到 3 节上课, 还有第 4 到第 5 周, 每周二在第 4 到第 5节上课

## 变量代换

```
${lessonName}   课程的 name 属性
${location}     课程的 location 属性
${teacher}      课程的 teacher 属性
${lessonType}   课程的 type 属性
${remark}       课程的 remark 属性
${scheduleFull} 课程的 schedule 完整属性
```

暂未实现的变量替换

```
${weekInfo}     本节课程发生时的当前周次
${schedule}     本节课程的起止课程节
````

## 格式定义中特殊字符转义

建议遵守以下转义规则, 否则可能会出现意料之外的结果

除开换行有 JSON 进行转义以外, 其他几个需要额外使用一次斜杠来将斜杠成功传达到变量代换阶段

- 换行符号需要使用 `\n` 进行转义 (由 JSON 进行处理)
- `$` 符号需要使用 `\\$` 进行转义
- `{` 符号需要使用 `\\{` 进行转义
- `}` 符号需要使用 `\\}` 进行转义 [^1]
