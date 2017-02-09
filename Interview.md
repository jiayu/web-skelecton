# 面试题目

## 准备阶段
* 将本代码库克隆到本地。
* 根据项目的配置在本地将服务器运行起来。
* 根据README.md熟悉本服务器的基本架构。

## 题目
1. 在数据库为User表提供两个新的字段，active和update_time。其中，active为布尔型，update_time为时间日前类型。
    * 当添加一个新用户时，active字段应该设置为true，update_time应该为当前时间
    * 当更新／删除一个用户信息时，update_time应该改为当前更新时间
    * 当删除一个用户时，不应该删除该用户在数据库中的记录，应该将active字段设置为false
2. 所有用户相关接口的返回，应该有统一的返回格式，具体应该参考AddUser，格式如下：
```json
{
  "success": true,
  "body": {
    "user": {
      "id": 1,
      "firstName": "Albert",
      "lastName": "Einstein"
    }
  }
}
```
请将其余接口更新至与此一致，其中success为操作是否成功，body为具体应该返回的内容。
3. 修改返回所有用户的接口，使其只会返回active为true的用户。要求从数据库层面做筛选。
4. __加分题__ 为接口提供校验签名的能力，具体的算法如下：
```specification
Signature = MD5(URL + METHOD + TIMESTAMP)

URL: 接口的请求路径，例如：adduser的请求路径为"/user/Albert/Eintein"
METHOD: 接口的HTTP请求动作，例如：adduser的请求动作为"POST"
TIMESTAMP：置于HTTP的Header中，为请求发起时的Unix时间戳，一般以long型表示，例如：1481344374（代表2016/12/10 12:32:54）
```
 
 * Signature应该置于HTTP的header内，在每个请求里面进行检验。只有签名通过，才会进行业务逻辑处理。
 * 假如验签不通过，则返回一个错误提示，错误提示如下：
 ```json
  {
    "success": false,
    "errorCode": 1000,
    "errorMessage": "Failed on signature check"
  }
 ```

## 要求
* 选择一个公开的git库，建立你的账户，并建立一个新的项目，将这些代码提交上去。
* 每个git commit的粒度应不大于一个问题所需要修改的代码量。
* 每个git commit应该保证程序能够编译通过。


## 提示
* 大部分使用到的技术都可以从[这里](http://docs.spring.io/platform/docs/1.1.4.RELEASE/reference/htmlsingle/)了解到。
* 利用Swagger可以完成基本的接口测试工作，签名相关的测试可能需要使用[POSTMAN](https://www.getpostman.com/)。

