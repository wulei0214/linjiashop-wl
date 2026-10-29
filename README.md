 # 前言
[![Spring Boot](https://img.shields.io/badge/spring--boot-2.1.1.1.RELEASE-brightgreen)](https://github.com/spring-projects/spring-boot)
[![vue](https://img.shields.io/badge/vue-2.6.10-brightgreen.svg)](https://github.com/vuejs/vue)
[![element-ui](https://img.shields.io/badge/element--ui-2.11.0-brightgreen.svg)](https://github.com/ElemeFE/element)
[![Vant](https://img.shields.io/badge/vant-2.2.0-brightgreen.svg)](https://youzan.github.io/vant/#/zh-CN/intro)
[![Vant-weapp](https://img.shields.io/badge/vant--weapp-1.0.1-brightgreen)](https://youzan.github.io/vant-weapp/#/intro)
[![Flutter](https://img.shields.io/badge/Flutter-1.9.6-brightgreen)](https://flutter.dev/)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/enilu/linjiashop/blob/master/LICENSE)
[![Gitee stars](https://gitee.com/microapp/linjiashop/badge/star.svg?theme=social)](https://gitee.com/microapp/linjiashop)
[![GitHub stars](https://img.shields.io/github/stars/microapp-store/linjiashop.svg?style=social&label=Stars)](https://github.com/microapp-store/linjiashop)



- linjiashop 是一个基于[Spring Boot](https://spring.io/projects/spring-boot/)和[Vue.js](https://cn.vuejs.org)的web商城系统
- linjiashop 包含了商城的后台管理系统,手机h5，小程序版本
- linjiashop 包含了app版本（基于flutter) 仓库地址[gitee](https://gitee.com/microapp/linjiashop-flutter),[github](https://github.com/microapp-store/linjiashop-flutter) 
- linjiashop 包含了uniapp版本（基于uniapp通过一套代码实现H5,Android，IOS，小程序等多平台 [项目地址](http://linjiashop.enilu.cn/linjiashop/ecosystem/uniapp.html)
- linjiashop 采用[web-flash](http://webflash.enilu.cn/)作为底层基础框架搭建，开发过程遇到问题请多阅读该项目文档。
- linjiashop 是一个采用MIT协议的开源商城系统，任何人任何单位可以免费使用该商城和基于该项目开发搭建自己的商城系统。
## 演示
### 演示环境(没有配置下单发短信等功能)
- 用户端H5(基于vue.js)：[http://linjiashop-vue.microapp.store](http://linjiashop-vue.microapp.store) 演示账号/密码：15011112222/admin 
- 用户端H5(基于uniapp)[http://linjiashop.microapp.store/](http://linjiashop.microapp.store/) 演示账号/密码：15011112222/admin
- App(基于uniapp)[http://fir.microapp.store/linjiashop](http://fir.microapp.store/linjiashop) 演示账号/密码：15011112222/admin
- 后台管理：[http://linjiashop-admin.microapp.store](http://linjiashop-admin.microapp.store) 管理员账号/密码：admin/admin
- 演示环境在线swagger文档：
    - 后台管理：http://linjiashop-admin.microapp.store/prod-api/swagger-ui.html
    - 用户端：http://linjiashop.microapp.store/prod-api/swagger-ui.html
## 真实环境，支持下单支付
- 公众号演示：关注公众号“外滩一号店"进行体验，或者扫描下面二维码关注

    ![外滩一号店](doc/img/online/wt1hd.jpg)


## 功能模块
linjiashop包含了后台管理功能和手机端商城业务功能
- 基础模块
    - 部门管理
    - 用户管理
    - 角色管理
    - 菜单管理
    - 权限分配
    - 参数管理
    - 数据字典管理
    - 定时任务管理
    - 操作日志
    - 登录日志
    - cms内容管理
    - 消息管理：配置消息模板，发送短信，邮件消息
    - 基于idea插件的代码生成
- 商城功能
    - 会员管理
    - 商品类别
    - 商品管理
    - 订单管理
    - 购物车
    - banner管理
    - 收藏列表
- 手机端
    -完整的商城购物功能        

## 运行效果图
_商品素材取自小米商城_
### 手机端H5
![手机端](doc/guide/img/mobile.gif)

### IOS
<table>
<tr>
<td>
<img src="doc/img/app/home.png" width="300"/></td>
<td>
<img src="doc/img/app/find.png" width="300"/></td>
</tr>
<tr>
<td>
<img src="doc/img/app/guige.jpg" width="300"/></td>
<td>
<img src="doc/img/app/login.png" width="300"/></td>
</tr>
<tr>
<td>
<img src="doc/img/app/mine.png" width="300"/></td>
<td>
<img src="doc/img/app/cart.png" width="300"/></td>
</tr>
</table>

### Android
![效果](doc/img/app/mobile.gif)

### 后台管理
![后台管理](doc/guide/img/admin.gif)

## 技术选型
- 核心框架：Spring Boot
- 数据库层：Spring data jpa
- 数据库连接池：Druid
- 缓存：Ehcache
- 前端：后台管理基于[element](http://element-cn.eleme.io)，手机端界面基于[vant](https://youzan.github.io/vant/#/zh-CN/intro)


## 目录说明
- linjiashop-admin PC端后台管理的前端网页
- linjiashop-admin-api PC端后台管理的api服务
- linjiashop-mobile 手机商城的前端网页
- linjiashop-mobile-api 移动端商城的api服务（h5,小程序，app都用改api服务作为后台接口）
- linjiasho-wxapp 微信小程序商城
- linjiashop-core 基础模块，包括工具类，dao，service，bean等内容
- linjiashop-generator 代码生成模块,根据实体生成dao,service,后台管理的controller和页面,配合IDEA 代码生成插件[webflash-generator](https://plugins.jetbrains.com/plugin/12648-webflash-generator)使用效果更好，使用前请仔细阅读该[文档](http://enilu.gitee.io/web-flash/ecosystem/code-generator.html)


## 快速开始

### 一、环境要求

| 工具 | 版本要求 |
|------|----------|
| JDK | 1.8+ |
| Maven | 3.6+ |
| Node.js | 14.x 或 16.x |
| MySQL | 5.7+ |
| IDE | IntelliJ IDEA / VSCode |

### 二、目录结构

```
linjiashop/                          # 项目根目录
├── linjiashop-admin/                # 后台管理前端（Vue + Element UI）
├── linjiashop-admin-api/             # 后台管理后端 API（Spring Boot）
├── linjiashop-mobile/                # 手机端前端（Vue + Vant）
├── linjiashop-mobile-api/            # 手机端后端 API（Spring Boot）
├── linjiashop-wxapp/                 # 微信小程序
├── linjiashop-core/                  # 核心模块（公共代码）
├── linjiashop-generator/             # 代码生成模块
└── pom.xml                           # Maven 父项目配置
```

### 三、本地部署步骤

#### 1. 初始化数据库

```sql
-- 登录 MySQL
mysql -u root -p

-- 创建数据库
CREATE DATABASE IF NOT EXISTS linjiashop DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建用户（可选，也可以直接用 root 用户）
CREATE USER 'linjiashop'@'localhost' IDENTIFIED BY 'linjiashop';
GRANT ALL PRIVILEGES ON linjiashop.* TO 'linjiashop'@'localhost';
FLUSH PRIVILEGES;
```

#### 2. 修改数据库配置

编辑文件：`linjiashop-admin-api/src/main/resources/application-dev.properties`

```properties
# 修改以下配置为你的数据库信息
spring.datasource.url=jdbc:mysql://localhost:3306/linjiashop?useUnicode=true&characterEncoding=UTF8&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456
```

同理修改 `linjiashop-mobile-api/src/main/resources/application-dev.properties` 中的数据库配置。

#### 3. 修改文件上传路径（重要！）

**如果你移动了项目位置，必须修改此项，否则图片无法显示！**

编辑文件：
- `linjiashop-admin-api/src/main/resources/application-dev.properties`
- `linjiashop-mobile-api/src/main/resources/application-dev.properties`

添加或修改以下配置（路径根据你的实际项目位置调整）：

```properties
# 文件上传路径（Windows 本地部署）
# 格式：盘符:/项目路径/runtime/upload
system.file.upload.path=F:/Program Files/linjiashop/runtime/upload
```

> **注意**：如果数据库已经初始化过，还需要更新数据库中的配置。执行以下 SQL：
> ```sql
> UPDATE t_sys_cfg SET cfg_value = 'F:/Program Files/linjiashop/runtime/upload' WHERE cfg_name = 'system.file.upload.path';
> ```
> 或者登录后台管理系统，进入「系统管理」→「参数管理」页面，修改 `system.file.upload.path` 的值为新路径。

#### 4. 构建后端项目

在项目根目录执行：

```bash
cd F:\Program Files\linjiashop
mvn install -DskipTests -Dspring-boot.repackage.skip=true -pl linjiashop-core,linjiashop-mobile-api,linjiashop-admin-api
```

**重要说明**：不能直接使用 `mvn install -DskipTests` 全量构建。父 pom 对所有模块统一应用了
spring-boot-maven-plugin 的 repackage 目标，而 `linjiashop-generator`、`linjiashop-core`
模块里存在多个 main 类，repackage 阶段会报 "Unable to find a single main class" 导致构建失败。
因此只构建运行所需的 3 个模块，并用 `-Dspring-boot.repackage.skip=true` 跳过 repackage
（本地用 `mvn spring-boot:run` 启动，不需要打 fat jar）。

#### 5. 启动后端服务

**后台管理 API（端口 8082）**：

```bash
cd linjiashop-admin-api
mvn spring-boot:run
```

**手机端 API（端口 8081）**：

```bash
cd linjiashop-mobile-api
mvn spring-boot:run
```

#### 6. 启动前端

本机使用 Node.js 16.20.2 绿色版（解压在 `F:\DevCache\nodejs\node-v16.20.2-win-x64`，未加入系统 PATH，
启动前在终端里把该目录加入 PATH 即可）。

**后台管理前端（端口 9528）**：

```bash
cd linjiashop-admin
# 注意：package.json 的 dev 脚本写死了 NODE_OPTIONS=--openssl-legacy-provider，
# 该参数只有 Node 17+ 支持，Node 16 会直接报错退出，因此绕过脚本直接启动：
./node_modules/.bin/vue-cli-service serve
```

**手机端前端（端口 8080）**：

```bash
cd linjiashop-mobile
npm run dev
```

> Node 17+ 的处理方式：admin 前端可直接 `npm run dev`（脚本自带的 openssl-legacy-provider 参数正是为 17+ 准备的）；
> mobile 前端在 Node 17+ 下需先 `set NODE_OPTIONS=--openssl-legacy-provider` 再 `npm run dev`。

### 四、访问地址

| 服务 | 地址 | 默认账号 |
|------|------|----------|
| 后台管理前端 | http://localhost:9528 | admin / admin |
| 后台管理 API | http://localhost:8082 | - |
| 手机端前端 | http://localhost:8080 | 15011112222 / admin |
| 手机端 API | http://localhost:8081 | - |
| API 文档 | http://localhost:8082/swagger-ui.html | - |

### 五、运维管理模块

后台「运维管理」菜单提供以下功能（对齐主流后台如 RuoYi 的系统监控设计）：

| 功能 | 说明 | 实现位置 |
|------|------|----------|
| 服务器监控 | CPU / 内存 / 操作系统 / JVM / 磁盘使用情况，10 秒自动刷新 | 后端 `/monitor/server`，前端 `views/operation/server` |
| 在线用户 | 展示当前登录会话（账号/部门/角色/登录时间/有效期），支持按账号搜索、**强制下线** | 后端 `/monitor/online/list`、`/monitor/online/kick`，前端 `views/operation/online` |
| 缓存监控 | Ehcache 各缓存区条目数、内存占用、命中统计，30 秒自动刷新 | 后端 `/monitor/cache`，前端 `views/operation/cache` |
| 数据库监控 | 内嵌 Druid 监控台（账号 `druiduser` / `druidpassword`，见 application.properties） | 前端 `views/operation/druid`，代理配置见 vue.config.js `/druid` |
| 接口文档 | 内嵌 Swagger UI，标签页切换后台管理 API（8082）/ 移动端 API（8081） | 前端 `views/operation/api`，代理配置 `/dev-api`、`/mobile-api` |

**强制下线实现说明**：项目为 JWT 无状态认证，被踢出的 token 本身仍然签名有效。
因此在 `TokenCache` 中引入 KICK 黑名单缓存（保留 2 小时，大于 token 有效期+刷新窗口），
`ApiRealm` 认证时校验黑名单，被强制下线的 token 立即失效。新增缓存区需在
`linjiashop-admin-api` 与 `linjiashop-mobile-api` 的 `ehcache.xml` 中同时声明。

**生产部署提示**：接口文档与数据库监控页面通过前端代理访问后端，生产环境 nginx 需要
配置对应的转发规则：`/prod-api`（后台 API）、`/druid`（Druid 监控台，Druid 的 302 跳转
按 Host 头构造绝对地址，需保留前端 Host 转发）、`/mobile-api`（移动端 API）。

### 六、常见问题

**Q1: npm install 报 EPERM 权限错误？**

- 以管理员身份运行终端
- 或修改 npm 缓存目录：`npm config set cache “C:\Users\你的用户名\AppData\Local\npm-cache”`

**Q2: 前端启动报 crypto / openssl 错误？**

- Node.js 17+：执行 `set NODE_OPTIONS=--openssl-legacy-provider` 后再运行
- Node.js 16：admin 前端不要用 `npm run dev`（其脚本写死了该参数，Node 16 不支持），改用
  `./node_modules/.bin/vue-cli-service serve` 直接启动，详见「快速开始 → 启动前端」

**Q3: 后端启动报 linjiashop-core 找不到？**

- 在根目录执行 `mvn install -DskipTests` 重新构建

**Q4: 数据库连接失败？**

- 检查 MySQL 是否启动
- 检查 application-dev.properties 中的用户名密码是否正确

**Q5: 数据库中文乱码问题？**

问题描述：通过 Navicat 导入 SQL 文件后，数据库数据正常，但前端页面显示乱码。

解决方案：

1. 重新导入数据库（推荐）：
```sql
-- 登录 MySQL
mysql -u root -p

-- 重建数据库（确保使用 utf8mb4 字符集）
DROP DATABASE IF EXISTS linjiashop;
CREATE DATABASE linjiashop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE linjiashop;

-- 设置客户端编码
SET NAMES utf8mb4;

-- 导入 SQL 文件（路径根据实际情况修改）
source F:/ProgramFiles/linjiashop/doc/database.sql
```

2. 配置修改：确保 application-dev.properties 中的字符集配置正确：
```properties
# JDBC 连接字符集
spring.datasource.url=jdbc:mysql://localhost:3306/linjiashop?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai

# Hibernate 字符集配置
spring.jpa.properties.hibernate.connection.characterEncoding=utf-8
spring.jpa.properties.hibernate.connection.useUnicode=true
```

**Q6: 图片不显示问题？**

问题描述：将项目从 C 盘移动到其他盘后，图片无法显示。

解决方案：

1. 修改文件上传路径配置（见上文「三、本地部署步骤」中的「3. 修改文件上传路径」）
2. 更新数据库中的配置：
```sql
UPDATE t_sys_cfg SET cfg_value = '新路径/runtime/upload' WHERE cfg_name = 'system.file.upload.path';
```
或登录后台管理系统，进入「系统管理」→「参数管理」页面修改。

## 本地修改与清理记录

> 以下为本机（2026-10-30）部署调试时的代码修改与文件清理记录，供后续维护参考。

### 1. 修复：Shiro 1.11 拦截中文路径接口，用户管理页弹 "Invalid request"

**现象**：进入后台「系统管理 → 用户管理」页面时，右上角弹出红色 `Invalid request` 提示，
性别下拉框无选项；用户列表等其他功能正常。

**根因**：父 pom 中 Shiro 版本为 1.11.0。Shiro 1.10+ 默认对所有请求启用 `InvalidRequestFilter`
（官方安全加固），URI 包含非 ASCII 字符（如中文）时直接拒绝并返回 400 "Invalid request"。
用户管理页的性别下拉框（DictSelect 组件）加载时会请求 `GET /dict/getDicts/性别`，
路径中的中文「性别」恰好命中该规则，前端 axios 拦截器把这个 400 的 message 弹成了提示。

**修改**：`linjiashop-admin-api/src/main/java/cn/enilu/flash/api/config/ShiroConfig.java`
在 shiroFilter 的 filterMap 中注册同名 `invalidRequest` 过滤器覆盖默认全局行为，
仅关闭非 ASCII 拦截，分号/反斜杠拦截保持开启：

```java
InvalidRequestFilter invalidRequestFilter = new InvalidRequestFilter();
invalidRequestFilter.setBlockNonAscii(false);
filterMap.put("invalidRequest", invalidRequestFilter);
```

**验证**：重启 admin-api 后，`/dict/getDicts/性别` 返回 200 及字典数据（男/女），
页面红色提示消失，下拉框选项正常。

**影响范围**：仅 admin-api。全项目只有用户管理页使用 dict-select（中文路径字典接口），
mobile-api 未做改动。

### 2. 清理：删除冗余文件与目录

以下内容不参与 Maven 构建和前端运行，属于误留的冗余文件，已删除
（git 索引中的对应 gitlink 条目已一并移除，尚未提交，需要时可 `git commit` 固化）：

| 文件/目录 | 说明 |
|-----------|------|
| `linjiashop/`（内层目录） | 上游项目的完整克隆（gitlink 子模块引用），构建不引用，还会干扰 IDE |
| `linjiashop-wl/`、`linjiashop-wl-1/` | 误留的项目说明副本（gitlink 引用，各含一个 README.md） |
| `hello.py` | 与项目无关的测试脚本 |
| `package-lock.json`（根目录） | 空的 lockfile（packages 为空），且已被 .gitignore 忽略 |

### 3. 本机运行环境速查

| 组件 | 位置/版本 |
|------|-----------|
| JDK | 1.8.0_162（系统默认 java） |
| Maven | 3.9.1，`D:\maven\apache-maven-3.9.1`，本地仓库 `F:\DevCache\.m2\repository` |
| MySQL | 8.0.33，`D:\MYSQL\mysql-8.0.33-winx64`，端口 3306，root/123456，库名 `linjiashop` |
| Node.js | 16.20.2 绿色版，`F:\DevCache\nodejs\node-v16.20.2-win-x64`（未加入系统 PATH） |

## 技术交流
- qq群仅为方便网友互相交流，作者基本不会在群里回复，如果需要跟作者提问题，可以通过项目主页提issue；qq群禁止发广告，发者立删;为了提高加群门槛，请先通过下面链接打赏后加项目作者qq(82552623)，并将打赏截图发过去，作者将邀请你进群，[打赏链接](http://linjiashop.enilu.cn/linjiashop/donate.html),打赏金额随缘

## 在线文档
- 项目文档：[http://linjiashop.enilu.cn/](http://linjiashop.enilu.cn/)
- 该项目克隆并扩展自web-flash,所以开发的时候多看看web-flash的[在线文档](http://webflash.enilu.cn)
- 该项目手机端linjiashop-mobile使用有赞Vant组件库，开发过程可以参考Vant的[在线文档](https://youzan.github.io/vant/#/zh-CN/intro)
- 该项目微信小程序使用mpvue+vant-weapp，开发过程请参考相关文档：[mpvue文档](http://mpvue.com/),[vant-weapp文档](https://youzan.github.io/vant-weapp/#/intro)
- 该项目的业务功能和部分功能代码参考复用了[litemall](https://github.com/linlinjava/litemall)，感谢[linlinjava](https://github.com/linlinjava)
- [视频教程](https://space.bilibili.com/525325274/channel/detail?cid=115895)
