#### 知识分享小App后端

主要功能：

- 分页查询分享资源列表
- 模糊搜索分享资源
- 兑换资源
- 投稿
- 签到
- 微信登录
- 我的投稿
- 积分明细
- 我的兑换
- 投稿审核

##### 微服务模块：

- 用户中心：user-center
- 内容中心：content-center
- 网关：gateway

##### 使用的技术栈：

- 服务注册与发现：Spring Cloud Alibaba Nacos
- 负载均衡：Spring Cloud Ribbon
- 服务容错：限流、降级：Spring Cloud Alibaba Sentinel
- 服务调用：Spring Cloud OpenFeign
- 异步消息队列：Apache RocketMQ
- 服务网关：Spring Cloud Gateway
- 服务调用链：Spring Cloud Zipkin & Slueth
- 认证鉴权：JWT & Spring AOP
- 数据库持久层：tk mybatis
- 服务器操作系统：CentOS7
- 服务器数据库：Mysql 8
  	