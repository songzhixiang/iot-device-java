import com.tencent.iot.hub.device.java.core.common.Status;
import com.tencent.iot.hub.device.java.core.gateway.TXGatewayConnection;
import com.tencent.iot.hub.device.java.core.mqtt.TXMqttActionCallBack;
import com.tencent.iot.hub.device.java.core.mqtt.TXMqttConstants;
import com.tencent.iot.hub.device.java.core.mqtt.TXOTACallBack;
import com.tencent.iot.hub.device.java.core.mqtt.TXOTAConstansts;
import com.tencent.iot.hub.device.java.core.util.AsymcSslUtils;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class GatewaySample {

	private static final Logger LOG = LoggerFactory.getLogger(GatewaySample.class);

	private static String path2Store = System.getProperty("user.dir");

	private static String mBrokerURL = null;  //传入null，即使用腾讯云物联网通信默认地址 "${ProductId}.iotcloud.tencentdevices.com:8883"  https://cloud.tencent.com/document/product/634/32546

	private static final String GW_OPERATION_RES_PREFIX = "$gateway/operation/result/";

	private static String mProductID = "PRODUCT_ID";
	private static String mDevName = "DEVICE_NAME";
	private static String mDevPSK = "DEVICE_PSK";
	private static String mSubProductID = "SUB_PRODUCT_ID";
	private static String mSubDevName = "SUB_DEV_NAME";
	private static String mSubDevPsk = "SUB_DEV_PSK";
	private static String mTestTopic = "TEST_TOPIC";
	private static String mCertFilePath = "DEVICE_CERT_FILE_NAME";           // Device Cert File Name
	private static String mPrivKeyFilePath = "DEVICE_PRIVATE_KEY_FILE_NAME";            // Device Private Key File Name
	private static JSONObject jsonObject = new JSONObject();

	private static TXGatewayConnection mqttconnection;
	private static MqttConnectOptions options;

	private static void gatewaySubdevOnline() {
		try {
			Thread.sleep(2000);
			// set subdev online
			mqttconnection.gatewaySubdevOnline(mSubProductID, mSubDevName);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void gatewaySubdevOffline() {
		try {
			Thread.sleep(2000);
			mqttconnection.gatewaySubdevOffline(mSubProductID, mSubDevName);//切换子设备下线
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void disconnect() {
		try {
			Thread.sleep(2000);
			mqttconnection.disConnect(null);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void subscribeTopic() {
		try {
			Thread.sleep(2000);
			mqttconnection.subscribe(mTestTopic, 1, null);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void unSubscribeTopic() {
		try {
			Thread.sleep(2000);
			mqttconnection.unSubscribe(mTestTopic, null);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void publishTopic() {
		try {
			Thread.sleep(2000);
			// 要发布的数据
			Map<String, String> data = new HashMap<String, String>();
			// 车辆类型
			data.put("car_type", "suv");
			// 车辆油耗
			data.put("oil_consumption", "6.6");
			// 车辆最高速度
			data.put("maximum_speed", "205");
			// 温度信息
			data.put("temperature", "25");
			// MQTT消息
			MqttMessage message = new MqttMessage();

			JSONObject jsonObject = new JSONObject();
			try {
				for (Map.Entry<String, String> entrys : data.entrySet()) {
					jsonObject.put(entrys.getKey(), entrys.getValue());
				}
			} catch (JSONException e) {
				LOG.error(e.getMessage()+"pack json data failed!");
			}
			message.setQos(TXMqttConstants.QOS1);
			message.setPayload(jsonObject.toString().getBytes());

			// 用户上下文（请求实例）

			LOG.debug("pub topic " + mTestTopic + message);
			// 发布主题
			mqttconnection.publish(mTestTopic, message, null);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void setSubDevBinded() {
		try {
			Thread.sleep(2000);
			mqttconnection.gatewayBindSubdev(mSubProductID, mSubDevName, mSubDevPsk);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void setSubDevUnbinded() {
		try {
			Thread.sleep(2000);
			mqttconnection.gatewayUnbindSubdev(mSubProductID, mSubDevName);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void checkSubdevRelation() {
		try {
			Thread.sleep(2000);
			mqttconnection.getGatewaySubdevRealtion();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		LogManager.resetConfiguration();
		LOG.isDebugEnabled();
		PropertyConfigurator.configure(MqttSample.class.getResource("/log4j.properties"));


		String workDir = System.getProperty("user.dir") + "/hub/hub-device-java/src/test/resources/";

		// init connection
		options = new MqttConnectOptions();
		options.setConnectionTimeout(8);
		options.setKeepAliveInterval(60);
		options.setAutomaticReconnect(true);
		//客户端证书文件名  mDevPSK是设备秘钥

		if (mDevPSK != null) {

		} else {
			options.setSocketFactory(AsymcSslUtils.getSocketFactoryByFile(workDir + mCertFilePath, workDir + mPrivKeyFilePath));
		}
		mqttconnection = new TXGatewayConnection(mProductID, mDevName, mDevPSK, new MqttSample.callBack());
		mqttconnection.setSubDevName(mSubDevName);
		mqttconnection.setSubDevProductKey(mSubDevPsk);
		mqttconnection.setSubProductID(mSubProductID);
		mqttconnection.connect(options, null);

		gatewaySubdevOnline();

		gatewaySubdevOffline();

//		setSubDevBinded();
//
//		checkSubdevRelation();
//
//		setSubDevUnbinded();
//
//		subscribeTopic();
//
//        publishTopic();
//
//		checkFirmware();
//
//        unSubscribeTopic();
//
//        disconnect();
//
	}

	public static class callBack extends TXMqttActionCallBack {

		@Override
		public void onConnectCompleted(Status status, boolean reconnect, Object userContext, String msg) {
			String userContextInfo = "";

			String logInfo = String.format("onConnectCompleted, status[%s], reconnect[%b], userContext[%s], msg[%s]",
					status.name(), reconnect, userContextInfo, msg);
			LOG.info(logInfo);
		}

		private TXOTACallBack oTACallBack = new TXOTACallBack() {

			@Override
			public void onReportFirmwareVersion(int resultCode, String version, String resultMsg) {
				LOG.error("onReportFirmwareVersion:" + resultCode + ", version:" + version + ", resultMsg:" + resultMsg);
			}

			@Override
			public boolean onLastestFirmwareReady(String url, String md5, String version) {
				LOG.error("onLastestFirmwareReady url=" + url + " version " + version);
				return false; // false 自动触发下载升级文件  true 需要手动触发下载升级文件
			}

			@Override
			public void onDownloadProgress(int percent, String version) {
				LOG.error("onDownloadProgress:" + percent);
			}

			@Override
			public void onDownloadCompleted(String outputFile, String version) {
				LOG.error("onDownloadCompleted:" + outputFile + ", version:" + version);

				mqttconnection.reportOTAState(TXOTAConstansts.ReportState.DONE, 0, "OK", version);
			}

			@Override
			public void onDownloadFailure(int errCode, String version) {
				LOG.error("onDownloadFailure:" + errCode);

				mqttconnection.reportOTAState(TXOTAConstansts.ReportState.FAIL, errCode, "FAIL", version);
			}
		};

		@Override
		public void onConnectionLost(Throwable cause) {
			String logInfo = String.format("onConnectionLost, cause[%s]", cause.toString());
			LOG.info(logInfo);

		}

		@Override
		public void onDisconnectCompleted(Status status, Object userContext, String msg) {
			String userContextInfo = "";

			String logInfo = String.format("onDisconnectCompleted, status[%s], userContext[%s], msg[%s]", status.name(), userContextInfo, msg);
			LOG.info(logInfo);
		}

		@Override
		public void onPublishCompleted(Status status, IMqttToken token, Object userContext, String errMsg) {
			String userContextInfo = "";

			String logInfo = String.format("onPublishCompleted, status[%s], topics[%s],  userContext[%s], errMsg[%s]",
					status.name(), Arrays.toString(token.getTopics()), userContextInfo, errMsg);
			LOG.debug(logInfo);
		}

		@Override
		public void onSubscribeCompleted(Status status, IMqttToken asyncActionToken, Object userContext, String errMsg) {
			String userContextInfo = "";

			String logInfo = String.format("onSubscribeCompleted, status[%s], topics[%s], userContext[%s], errMsg[%s]",
					status.name(), Arrays.toString(asyncActionToken.getTopics()), userContextInfo, errMsg);
			if (Status.ERROR == status) {
				LOG.error(logInfo);
			} else {
				LOG.debug(logInfo);
			}
		}

		@Override
		public void onUnSubscribeCompleted(Status status, IMqttToken asyncActionToken, Object userContext, String errMsg) {
			String userContextInfo = "";

			String logInfo = String.format("onUnSubscribeCompleted, status[%s], topics[%s], userContext[%s], errMsg[%s]",
					status.name(), Arrays.toString(asyncActionToken.getTopics()), userContextInfo, errMsg);
			LOG.debug(logInfo);
		}

		@Override
		public void onMessageReceived(final String topic, final MqttMessage message) {
			String logInfo = String.format("receive message, topic[%s], message[%s]", topic, message.toString());
			LOG.debug(logInfo);
		}
	}
}