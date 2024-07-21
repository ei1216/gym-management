# gym-management
提供健身房的基础管理功能。

整个系统采用SpringBoot3+Vue3+TypeScript构建，涉及的技术栈包括MySQL、Redis、MybatisPlus、JWT。 



# 1、管理员模块

## 管理员注册

输入用户名、密码、电话，采用MD5技术加密密码，使用MybatisPlus将管理员的信息存储到数据库中。

## 管理员登录

采用MD5技术加密密码，使用MybatisPlus实现了对管理员信息的查询和验证，使用JWT生成登录令牌并返回。



# 2、私教模块

使用MybatisPlus实现对私教的增加、删除、修改以及分页条件查询。

# 3、器材模块

使用MybatisPlus实现对器材的增加、删除、修改以及分页条件查询。

# 4、会员模块

使用MybatisPlus实现对会员的增加、删除、修改以及分页条件查询。

# 5、课程模块

使用MybatisPlus实现对课程的增加、删除、修改以及分页条件查询。

# 6、课程报名模块

使用MybatisPlus实现对课程报名的增加、删除、修改以及分页条件查询。
