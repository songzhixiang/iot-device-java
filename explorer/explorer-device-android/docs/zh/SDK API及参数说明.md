* [API 接口说明](#API-接口说明)
  * [SDK 设计说明](#SDK-设计说明)
  * [SDK API 说明](#SDK-API-说明)

## API 接口说明

### SDK 设计说明

| 类名                 | 功能                                         |
| -------------------- | -------------------------------------------- |
| TXMqttConnection     | 连接物联网开发平台                           |
| TXDataTemplate       | 实现数据模板基本功能                         |
| TXDataTemplateClient | 实现直连设备根据数据模板连接物联网开发平台   |
| TXGatewayClient      | 实现网关设备根据数据模板连接物联网开发平台   |
| TXGatewaySubdev      | 实现网关子设备根据数据模板连接物联网开发平台 |

![](https://main.qcloudimg.com/raw/ea345acb67bd0f9ef20a7336704bd070.jpg)

### SDK API 说明

#### TXMqttConnection

| 方法名                        | 说明                                |
| ---------------------------- | -----------------------------------|
| connect                      |  MQTT 连接                          |
| reconnect                    |  MQTT 重连                          |
| disConnect                   |  断开 MQTT连接                       |
| publish                      |  发布 MQTT 消息                      |
| subscribe                    |  订阅 MQTT 主题                      |
| unSubscribe                  |  取消订阅 MQTT 主题                   |
| getConnectStatus             |  获取 MQTT 连接状态                   |
| setBufferOpts                |  设置断连状态 buffer 缓冲区              |
| initOTA                      |  初始化 OTA 功能                        |
| reportCurrentFirmwareVersion |  上报设备当前版本信息到后台服务器        |
| reportOTAState               |  上报设备升级状态到后台服务器            |

#### TXDataTemplate

| 方法名                   | 说明                     |
| ------------------------ | ------------------------ |
| subscribeTemplateTopic   | 订阅数据模板相关主题     |
| unSubscribeTemplateTopic | 取消订阅数据模板相关主题 |
| propertyReport           | 上报属性                 |
| propertyGetStatus        | 更新状态                 |
| propertyReportInfo       | 上报设备信息             |
| propertyClearControl     | 清除控制信息             |
| eventSinglePost          | 上报单个事件             |
| eventsPost               | 上报多个事件             |

#### TXDataTemplateClient

| 方法名                   | 说明                       |
| ------------------------ | -------------------------- |
| isConnected              | 是否已经连接物联网开发平台 |
| subscribeTemplateTopic   | 订阅数据模板相关主题       |
| unSubscribeTemplateTopic | 取消订阅数据模板相关主题   |
| propertyReport           | 上报属性                   |
| propertyGetStatus        | 更新状态                   |
| propertyReportInfo       | 上报设备信息               |
| propertyClearControl     | 清除控制信息               |
| eventSinglePost          | 上报单个事件               |
| eventsPost               | 上报多个事件               |

#### TXGatewayClient

| 方法名        | 说明                               |
| ------------- | ---------------------------------- |
| findSubdev    | 查找子设备（根据产品ID和设备名称） |
| removeSubdev  | 删除子设备                         |
| addSubdev     | 添加子设备                         |
| subdevOffline | 下线子设备                         |
| subdevOnline  | 上线子设备                         |

#### TXGatewaySubdev

| 方法名          | 说明               |
| --------------- | ------------------ |
| getSubdevStatus | 获取子设备连接状态 |
| setSubdevStatus | 设置子设备连接状态 |
