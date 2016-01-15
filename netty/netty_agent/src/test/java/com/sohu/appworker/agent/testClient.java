package com.sohu.appworker.agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import nettyDemo.protobuf.AgentProtocol.CommandRequest;
import nettyDemo.protobuf.AgentProtocol.CommandResponse;
import nettyDemo.protobuf.AgentProtocol.CommandType;
import nettyDemo.protobuf.ProtobufClient;
import nettyDemo.protobuf.RsaUtils;

import org.junit.Test;

import com.google.protobuf.ByteString;

public class testClient {
	private ProtobufClient client = new ProtobufClient();
	
	//@Test
	public void testProtobufClient() {
		byte[] cmd = RsaUtils.encript(RsaUtils.getPublickKey(), "/opt/restart.sh");
		byte[] signatureBytes = RsaUtils.sign(RsaUtils.getSignPrivateKey(), cmd);
		ByteString args = ByteString.copyFrom(cmd);
		ByteString signature = ByteString.copyFrom(signatureBytes);
		System.out.println("cmd: " + cmd +" | sign: " + signatureBytes);
		/*
		//认证和解密代码
		boolean signSuccess = false;
		try{
			signSuccess = RsaUtils.verify(RsaUtils.getSignPublicKey(), cmd, signatureBytes);
			if(signSuccess) {
				byte[] result = RsaUtils.decript(RsaUtils.getPrivateKey(), cmd);
				System.out.println(new String(result));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		*/
		
		//向agent发出重启命令
		CommandRequest request = CommandRequest.newBuilder()
				.setCommand(CommandType.RESTART_TASK)
				.setArgs(args)
				.setSignature(signature)
				.build();
		String targetSrc = "localhost:11211";
		String ipAddress = targetSrc.substring(0, targetSrc.indexOf(":"));
		CommandResponse response = client.run(ipAddress, "6698", request);
		System.out.println(response.getResult());
	}
	
	@Test
	public void testSocketClient() {
		try{
			Socket socket = new Socket("localhost", 6698);
			//由系统标准输入设备构造BufferedReader对象
			BufferedReader sin =  new BufferedReader(new InputStreamReader(System.in));
			//由Socket对象得到输出流，并构造PrintWriter对象
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			//由Socket对象得到输入流，并构造相应的BufferedReader对象
			BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String readline;
			readline = sin.readLine(); 					//从系统标准输入读入一字符串
			while(!readline.equals("bye")){
				os.println(readline);					//将从系统标准输入读入的字符串输出到Server
				os.flush(); 							//刷新输出流，使Server马上收到该字符串
				System.out.println("Client:"+readline); //从Server读入一字符串，并打印到标准输出上
				System.out.println("Server:"+is.readLine());
				readline=sin.readLine();
			}
			os.close();
			is.close();
			socket.close();
		}catch (UnknownHostException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

}
