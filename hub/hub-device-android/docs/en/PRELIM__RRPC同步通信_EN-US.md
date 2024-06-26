* [RRPC Sync Communication](#RRPC-Sync-Communication)
  * [Overview](#Overview)
  * [How communication works](#How-communication-works)
  * [Communication process](#Communication-process)
  * [Running demo for RRPC sync communication](#Running-demo-for-RRPC-sync-communication)

# RRPC Sync Communication
## Overview
Because of the async communication mode of the MQTT protocol based on the publish/subscribe pattern, after the server controls a device, it cannot synchronously get the result returned by the device. To solve this problem, IoT Hub uses the Revert RPC (RRPC) technology to implement a sync communication mechanism. For more information, please see [RRPC Communication](https://cloud.tencent.com/document/product/634/47334).

## How communication works
* The subscription message topic `$rrpc/rxd/{productID}/{deviceName}/+` is used to subscribe to RRPC request messages sent by the cloud (downstream).
* The request message topic `$rrpc/rxd/{productID}/{deviceName}/{processID}` is used for the cloud to publish (downstream) RRPC request messages.
* The response message topic `$rrpc/txd/{productID}/{deviceName}/{processID}` is used to publish (upstream) RRPC response messages.

## Communication process
1. The device subscribes to the RRPC subscription message topic.
2. The server publishes an RRPC request message by calling the [PublishRRPCMessage](https://cloud.tencent.com/document/product/634/47078) API.
3. After receiving the message, the device extracts the `processID` distributed by the cloud in the request message topic, sets it as the `processID` of the response message topic, and publishes a return message of the device to the response message topic.
4. After IoT Hub receives the return message from the device, it matches the message according to the `processID` and sends the return message to the server.
>! **RRPC requests time out in 4s**, that is, if the device doesn't respond within 4s, the request will be considered to have timed out.

## Running demo for RRPC sync communication

Step 1. Subscribe to the RRPC message topic in the device

Please connect the device to MQTT for authenticated connection as instructed in [Device Connection Through MQTT over TCP](../../../hub-device-android/docs/en/PRELIM__基于TCP的MQTT设备接入_EN-US.md) first.
Run the demo and click **Subscribe to RRPC Topic** in the basic feature module to subscribe to the RRPC message topic as instructed in [How communication works](#How-communication-works). Below is the sample code:

```
mMQTTSample.subscribeRRPCTopic(); // Subscribe to the RRPC message topic
```

The following log represents the process in which the device subscribes to the RRPC message topic successfully.
```
I/com.tencent.iot.hub.device.java.core.mqtt.TXMqttConnection: Starting subscribe topic: $rrpc/rxd/AP9ZLEVFKT/gateway1/+
D/TXMQTT: onSubscribeCompleted, status[OK], topics[[$rrpc/rxd/AP9ZLEVFKT/gateway1/+]], userContext[MQTTRequest{requestType='subscribeTopic', requestId=1}], errMsg[subscribe success]
```

Step 2. Call TencentCloud API `PublishRRPCMessage` to send an RRPC request message
Go to [API Explorer](https://console.cloud.tencent.com/api/explorer?Product=iotcloud&Version=2018-06-14&Action=PublishRRPCMessage&SignVersion=), enter the personal key and device parameter information, select **Online Call**, and send the request.

Step 3. Observe the logcat log representing the process in which the device receives the published RRPC request message to get the `**processID**` 

```
I/TXMQTT_1.2.3: Received topic: $rrpc/rxd/AP9ZLEVFKT/gateway1/8209, id: 0, message: hello
```
The above log represents the process in which the device receives the published RRPC request message successfully. As can be seen, the `**processID**` is `8209`.

Step 4. The device sets the extracted `processID` as the `processID` of the response message topic, and publishes a return message of the device to the response message topic

The `TXMqttConnection` class in the SDK will publish a response message after it successfully receives the published RRPC request message.
```
/**
 * Received MQTT message
 *
 * @param topic   Message topic
 * @param message Message content structure
 * @throws Exception
 */
@Override
public void messageArrived(String topic, MqttMessage message) throws Exception {
    if (topic != null && topic.contains("rrpc/rxd")) {
       publishRRPCToCloud(null, processId, replyMessage);
    }
}
```

The following log represents the process in which the RRPC response message topic is published to successfully.
```
I/com.tencent.iot.hub.device.java.core.mqtt.TXMqttConnection: Starting publish topic: $rrpc/txd/AP9ZLEVFKT/gateway1/8209 Message: {"test-key":"test-value"}
D/TXMQTT: onPublishCompleted, status[OK], topics[[$rrpc/txd/AP9ZLEVFKT/gateway1/8209]],  userContext[], errMsg[publish success]
```
