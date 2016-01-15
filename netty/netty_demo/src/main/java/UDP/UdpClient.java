package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {
	public static void main(String[] args) throws IOException {
		// 创建发送方的套接字，IP默认为本地，端口号随机
		DatagramSocket sendSocket = new DatagramSocket();
		String msg = "My name is chongyang";
		byte[] buf = msg.getBytes("UTF-8");

		int port = 9999;
		InetAddress ip = InetAddress.getByName("localhost");
		// 创建发送类型的数据报：
		DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, ip,	port);

		// 通过套接字发送数据：
		sendSocket.send(sendPacket);
		sendSocket.close();
	}
}
