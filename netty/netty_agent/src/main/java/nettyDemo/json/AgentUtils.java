package nettyDemo.json;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AgentUtils {
	private final static Logger log = LoggerFactory.getLogger(AgentUtils.class);
	
	public static void main(String[] args) {
		boolean result = AgentUtils.rebootService("/opt/scripts/test/linux_var.sh", 
				"http://127.0.0.1:11211/test.jsp");
		if(result) {
			System.out.println("Service rebooted!");
		}
	}

	public static boolean rebootService(String restartCmd, String targetSrc) {
		Pattern pattern = Pattern.compile("\\d+[.]\\d+[.]\\d+[.]\\d+");
		Matcher matcher = pattern.matcher(targetSrc);
		String ipAddress = null;
		if(matcher.find()){
			ipAddress = matcher.group(0);
		}else{
			log.error("Can not get IP address from targetSrc!");
			return false;
		}
		byte[] args = RsaUtils.encript(RsaUtils.getPublickKey(), restartCmd);
		byte[] signatureBytes = RsaUtils.sign(RsaUtils.getSignPrivateKey(), args);
		JSONObject jsonMsg = new JSONObject();
		jsonMsg.put("command", "RESTART_TASK");
		jsonMsg.put("args", Base64.encodeBase64String(args));
		jsonMsg.put("signature", Base64.encodeBase64String(signatureBytes));
		byte[] request = jsonMsg.toString().getBytes();

		Client client = new Client();
		try {
			String response = client.run(ipAddress, "6698", request);
			System.out.println(response);
			JSONObject resultMsg = JSONObject.fromObject(response);
			if(resultMsg.getString("status").equals("200")) {
				return true;
			}else {
				log.error(response);
			}
		} catch (Exception e) {
			log.error("" + e.getMessage());
		}
		return false;
	}
}
