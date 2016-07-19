#Mini-SmartHome基于CodeIgniter框架开发的Restful接口风格的小型物联网云平台
智能家居云平台API文档
=====================

更新日期：2014-06-01

基本说明
--------

1、所有对用户的信息、设备、传感器、数据点的操作都需要验证APIKEY，请在HTTP请求（http request）的头部（header）添加APIKEY字段，并附加相应地值。

如：Apikey: 1a39ad4c87ba09ef861ead97f010df7b

2、接口的URL由HOST和URI共同组成。其中URL，Universal Resource Location，统一资源定位符；URI，Universal Resource Identifier，统一资源标识符；HOST，服务器IP地址+端口号。

URI与URL的区别：

URI用于标记资源；

URL提供了找到该资源的方法，且包含了URI，可以说URL=HOST+URI；

在HTTP请求中，是没有URL的，只有URI和HOST，其中URI包含在请求的第一行。

3、云平台测试<span id="OLE_LINK1" class="anchor"><span id="OLE_LINK2" class="anchor"></span></span>服务器地址与账号：

云平台服务器地址（HOST）：<span id="OLE_LINK10" class="anchor"><span id="OLE_LINK11" class="anchor"></span></span>218.244.138.146:8000

云平台测试用账号及密码：linpcloud + 1234

4、请求成功与失败的标识：以HTTP响应码来标识请求成功与失败；以200为成功的标识，400为云平台系统的错误标识，其它错误参考HTTP协议；返回信息以及错误提示信息均以JSON格式传回，置于info键下。

5、HTTP响应：正常的系统输出和系统已捕捉的错误，都是以JSON格式字符串返回，即：Content-Type: application/json

服务器（Apache或Nginx）与编程环境（PHP）的错误，根据环境的配置返回响应的数据，这是云平台系统以外的错误，不会以JSON格式返回错误信息。客户端应根据响应的HTTP相应码给出响应的错误信息。

API接口及说明
-------------

1.  用户类

<!-- -->

1.  用户登录

使用用户名和密码进行登录，登录成功返回用户ID和APIKEY，用于后续操作。

**请求**

方法：POST

URI：/api/login

参数：

| 参数名   | 必需 | 类型   | 说明     |
|----------|------|--------|----------|
| username | true | string | 用户名   |
| password | true | string | 用户密码 |

请求正文示例：

username=&lt;username&gt;&password=&lt;password&gt;

**响应**

返回值：关键的用户信息

参数：

| 参数名   | 类型   | 说明           |
|----------|--------|----------------|
| userid   | string | 用户ID         |
| username | string | 用户名         |
| apikey   | string | 用户授权APIKEY |

响应正文实例：

{

"info": "Login Success",

"userid": "10000",

"username": "linpcloud",

"apikey": "1a39ad4c87ba09ef861ead97f010df7b"

}

1.  获取用户信息

获取个人用户信息。

**请求**

方法：GET

URI：/api/user

参数：无

**响应**

返回值：JSON格式的用户信息

参数：

| 参数名   | 类型   | 说明           |
|----------|--------|----------------|
| id       | int    | 用户ID         |
| username | string | 用户名         |
| password | string | 加密后的密码   |
| email    | string | Email          |
| apikey   | string | APIKEY         |
| regtime  | int    | 用户注册时间戳 |
| about    | string | 用户的描述信息 |

响应正文实例：

{

"id": "10000",

"username": "linpcloud",

"password": "81dc9bdb52d04dc20036dbd8313ed055",

"email": "linpcloud@linpcloud.com",

"token": "1234567890",

"token\_exptime": "0",

"status": "1",

"regtime": "0",

"apikey": "1a39ad4c87ba09ef861ead97f010df7b",

"about": "linpcloud test user"

}

1.  修改用户信息

2.  获取APIKEY

3.  更换APIKEY

<!-- -->

1.  设备类

<!-- -->

1.  创建设备

<span id="OLE_LINK5" class="anchor"><span id="OLE_LINK6" class="anchor"></span></span>根据所填信息，创建一个新的设备。

**请求**

方法：POST

URI：/api/devices

参数：

| <span id="OLE_LINK3" class="anchor"><span id="OLE_LINK4" class="anchor"></span></span>参数名 | 必需 | 类型   | 说明                     |
|----------------------------------------------------------------------------------------------|------|--------|--------------------------|
| name                                                                                         | true | string | 设备名称                 |
| tags                                                                                         |      | string | 设备标签，建议以逗号间隔 |
| about                                                                                        |      | string | 设备描述                 |
| locate                                                                                       |      | string | 设备位置，格式待定       |

请求正文示例：

name=device\_instance&tags=tags\_instance&locate=Wuhan&about=This+is+a+example+device

**响应**

返回值：新创建的设备的ID

参数：

| 参数名     | 类型 | 说明             |
|------------|------|------------------|
| device\_id | int  | 新创建的设备的ID |

响应正文示例：

{

"device\_id": 10007

}

1.  获取设备列表

获取该用户下所有的设备信息。

**请求**

方法：GET

URI：/api/devices

参数：无

**响应**

返回值：JSON对象数组

参数：

响应正文示例：

\[

{

"id": "10006",

"name": "device from Postman",

"tags": "any tags?",

"about": "This is a device created by Postman",

"locate": "Wuhan",

"user\_id": "10000",

"create\_time": "1399717930",

"update\_time": "1399717930",

"status": "1"

},

{

"id": "10007",

"name": "示例设备",

"tags": "示例标签",

"about": "这是示例设备",

"locate": "武汉",

"user\_id": "10000",

"create\_time": "1399738659",

"update\_time": "1399738659",

"status": "1"

}

\]

1.  获取设备信息

获取某个具体设备的详细信息。

**请求**

方法：GET

URI：/api/device/&lt;device\_id&gt;

参数：

| 参数名     | 必需 | 类型 | 说明   |
|------------|------|------|--------|
| device\_id | true | int  | 设备ID |

**响应**

返回值：JSON格式的设备信息

参数：

| 参数名       | 类型      | 说明                                                                                    |
|--------------|-----------|-----------------------------------------------------------------------------------------|
| id           | int       | 设备的ID                                                                                |
| name         | string    | 设备名称                                                                                |
| tags         | string    | 设备标签                                                                                |
| about        | string    | 设备描述                                                                                |
| locate       | string    | 设备位置                                                                                |
| user\_id     | int       | 设备所属用户的ID，不可更改                                                              |
| create\_time | timestamp | 创建时间                                                                                |
| update\_time | timestamp | 更新时间，待定                                                                          |
| status       | int       | 用于标记删除状态的字段，1表示正常，0表示已删除；一般而言，status为0时，查看不到它的信息 |

返回正文示例：

{

"id": "10007",

"name": "示例设备",

"tags": "示例标签",

"about": "这是示例设备",

"locate": "武汉",

"user\_id": "10000",

"create\_time": "1399738659",

"update\_time": "1399738659",

"status": "1"

}

1.  修改设备信息

先获取设备信息，再进行少量修改。

**请求**

方法：PUT

URI：/api/device/&lt;device\_id&gt;

参数：

| 参数名     | 必需 | 类型   | 说明                     |
|------------|------|--------|--------------------------|
| device\_id | true | int    | 设备ID，置于URI中        |
| name       | true | string | 设备名称                 |
| tags       |      | string | 设备标签，建议以逗号间隔 |
| about      |      | string | 设备描述                 |
| locate     |      | string | 设备位置，格式待定       |

**响应**

返回值：修改成功或失败的信息

响应示例：

{

"info": "update info success"

}

1.  删除设备

删除某个具体设备。

**请求**

方法：DELETE

URI：/api/device/&lt;device\_id&gt;

参数：

| 参数名     | 必需 | 类型 | 说明              |
|------------|------|------|-------------------|
| device\_id | true | int  | 设备ID，附在URI中 |

**响应**

返回值：成功或失败的信息

响应示例：

{

"info": "delete success",

"device\_id": "10007"

}

1.  传感器类

<!-- -->

1.  添加传感器

根据所填信息，创建一个新的传感器。

**请求**

方法：POST

URI：/api/sensors

参数：

| 参数名     | 必需 | 类型   | 说明                       |
|------------|------|--------|----------------------------|
| name       | true | string | 名称                       |
| type       | true | int    | 传感器所属类型的ID         |
| tags       |      | string | 标签，建议以逗号间隔       |
| about      |      | string | 描述                       |
| device\_id | true | int    | 所创建的传感器将属于的设备 |

**响应**

返回值：新创建的传感器的ID

参数：

| 参数名     | 类型 | 说明               |
|------------|------|--------------------|
| sensor\_id | int  | 新创建的传感器的ID |

响应正文示例：

{

"sensor\_id": 10004

}

1.  获取传感器列表

获取某个设备下所属的所有传感器的信息，返回一个JSON对象数组。

**请求**

方法：GET

URI：/api/sensors/&lt;device\_id&gt;

参数：

| 参数名     | 必需 | 类型 | 说明                              |
|------------|------|------|-----------------------------------|
| device\_id | true | int  | 需要列出传感器的设备ID，附在URI中 |

**响应**

参数：见获取传感器信息

响应正文示例：

\[

{

"id": "100004",

"name": "传感器实例",

"type": "1",

"tags": "标签",

"about": "这是一个示例",

"device\_id": "10007",

"last\_update": "1399740484",

"last\_data": null,

"status": "1"

}

\]

1.  获取传感器信息

获取某个指定的传感器的信息，后续可用于修改信息。

**请求**

方法：GET

URI：/api/sensor/&lt;sensor\_id&gt;

参数：

| <span id="OLE_LINK7" class="anchor"><span id="OLE_LINK8" class="anchor"><span id="OLE_LINK9" class="anchor"></span></span></span>参数名 | 必需 | 类型 | 说明                          |
|-----------------------------------------------------------------------------------------------------------------------------------------|------|------|-------------------------------|
| sensor\_id                                                                                                                              | true | int  | 需要查看的传感器ID，附在URI中 |

**响应**

返回值：JSON格式的对象，内容为传感器的信息

参数：

| 参数名       | 类型      | 说明                                                                                    |
|--------------|-----------|-----------------------------------------------------------------------------------------|
| id           | int       | 传感器的ID                                                                              |
| name         | string    | 传感器名称                                                                              |
| type         | int       | 传感器类型的ID                                                                          |
| tags         | string    | 传感器标签                                                                              |
| about        | string    | 设备描述                                                                                |
| device\_id   | int       | 所属设备的ID，不可更改                                                                  |
| last\_update | timestamp | 上次数据更新时间                                                                        |
| last\_data   | string    | json格式组织的上次数据                                                                  |
| status       | int       | 用于标记删除状态的字段，1表示正常，0表示已删除；一般而言，status为0时，查看不到它的信息 |

响应实例：

{

"id": "100004",

"name": "传感器实例",

"type": "1",

"tags": "标签",

"about": "这是一个示例",

"device\_id": "10007",

"last\_update": "1399740484",

"last\_data": null,

"status": "1"

}

1.  修改传感器信息

先获取传感器信息，再修改其基础的信息，如同类型以及所属设备这样的字段一般是不允许更改的。

**请求**

方法：PUT

URI：/api/sensor/&lt;sensor\_id&gt;

参数：

| 参数名     | 必需 | 类型   | 说明                          |
|------------|------|--------|-------------------------------|
| sensor\_id | true | int    | 需要查看的传感器ID，附在URI中 |
| name       | true | string |                               |
| type       | true | int    |                               |
| device\_id | true | int    | 必须附在表单中，但不要更改它  |
| tags       |      | string |                               |
| about      |      | string |                               |

**响应**

返回值：成功或失败的信息

响应实例：

{

"info": "sensor \`100004\` update success"

}

1.  删除传感器

删除某个具体的传感器，（或许会添加回收功能）

**请求**

方法：DELETE

URI：/api/sensor/&lt;sensor\_id&gt;

参数：

| 参数名     | 必需 | 类型 | 说明                          |
|------------|------|------|-------------------------------|
| sensor\_id | true | int  | 需要查看的传感器ID，附在URI中 |

<span id="OLE_LINK12" class="anchor"></span>

**响应**

返回值：删除成功或失败的信息

响应实例：

{

"info": "delete sensor \`100004\` success"

}

1.  数据点类

<!-- -->

1.  上传数据

为指定的传感器创建一个数据点（上传一个数据）。

**请求**

方法：POST

URI：/api/datapoint

参数：

| 参数名     | 必需 | 类型   | 说明                   |
|------------|------|--------|------------------------|
| sensor\_id | true | int    | 需要创建数据的传感器ID |
| value      | true | string | 携带数值的字符串       |

**响应**

返回值：添加成功或失败的信息

响应实例：

{

"info": "create datapoint success",

"datapoint\_id": 1044

}

1.  获取数据

获取某个传感器的最新数据（last\_data）。

**请求**

方法：GET

URI：/api/datapoint/&lt;sensor\_id&gt;

参数：

| 参数名     | 必需 | 类型 | 说明                   |
|------------|------|------|------------------------|
| sensor\_id | true | int  | 需要获取数据的传感器ID |

**响应**

返回值：传感器最新数据

响应实例：

{

"sensor\_id": "100005",

"timestamp": "1401553001",

"value": "123456"

}

1.  批量上传数据（适用于网关）

批量上传某个设备下传感器的数据，并激活该设备（设置设备的最后活动时间，通过判断这个值来确定设备是否在线）。

**请求**

方法：POST

URI：/api/datapoints/&lt;device\_id&gt;

参数：

| 参数名     | 必需 | 类型       | 说明                                                   |
|------------|------|------------|--------------------------------------------------------|
| device\_id | true | int        | 需要激活的设备                                         |
| json       | true | JSON ARRAY | 使用JSON组织的对象数组，每个对象都包含传感器ID和其数据 |

请求示例：json=\[{"sensor\_id":100005,"value":"000000"}\]

urlencode之后：json=%5B%7B%22sensor\_id%22%3A100005%2C%22value%22%3A%22000000%22%7D%5D

**响应**

返回值：添加成功或失败的信息

响应实例：

{

"info": "Datapoint created success"

}

4、批量获取数据（设计用于控制终端）

用于获取某个设备下所有传感器的最新数据。

**请求**

方法：GET

URI：/api/datapoints/device/&lt;device\_id&gt;

参数：

| 参数名     | 必需 | 类型 | 说明           |
|------------|------|------|----------------|
| device\_id | true | int  | 需要激活的设备 |

**响应**

返回值：JSON格式的对象数组，数组每一项包含一个传感器的最新数据。

响应实例：

\[

{

"id": "100005",

"last\_update": "1401553452",

"last\_data": "000000"

},

{

"id": "100006",

"last\_update": "1400502382",

"last\_data": "30%"

},

{

"id": "100007",

"last\_update": "1400502382",

"last\_data": "1"

},

{

"id": "100009",

"last\_update": "1400595887",

"last\_data": "hello world"

}

\]

5、获取历史数据

用于获取某个传感器的历史数据。

**请求**

方法：GET

URI：/api/datapoints/sensor/&lt;sensor\_id&gt;

参数：

| 参数名             | 必需   | 类型 | 说明                     |
|--------------------|--------|------|--------------------------|
| sensor\_id         | true   | int  | 需要获取历史数据的传感器 |
| URL参数（GET参数） |
| start              | option | int  | 开始时间（时间戳）       |
| end                | option | int  | 结束时间                 |
| interval           | option | int  | 数据时间间隔             |

所有参数在不设置时，云平台会使用默认值：开始时间为现在时间的一小时前，结束时间为当前的时间，时间间隔为30s。

请求URL示例：/api/datapoints/sensor/100005?start=12345&interval=60

**响应**

返回值：JSON格式的对象数组，数组每一项包含传感器的一个数据点。

响应实例：

\[

{

"id": "1020",

"sensor\_id": "100005",

"timestamp": "1399906997",

"value": "66ffcc"

},

{

"id": "1035",

"sensor\_id": "100005",

"timestamp": "1400502382",

"value": "ffffff"

},

{

"id": "1045",

"sensor\_id": "100005",

"timestamp": "1401553452",

"value": "000000"

}

\]

1.  辅助类

<!-- -->

1.  获取MD5

获取一个字符串的MD5值。

**请求**

方法：GET

URI：/helper/md5/&lt;string&gt;

参数：

| 参数名 | 必需 | 类型   | 说明                |
|--------|------|--------|---------------------|
| string | true | string | 需要获取MD5的字符串 |

1.  获取SHA1

使用参考“获取MD5”，返回制定字符串的SHA1值。

方法：GET

URI：/helper/sha1/&lt;string&gt;

1.  获取当前时间戳

获取当前时间的时间戳。

**请求**

方法：GET

URI：/helper/timestamp
