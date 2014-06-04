package jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;
/**
 * 先创建了一个MBeanServer，用来做MBean的容器 
 * 将Hello这个类注入到MBeanServer中，注入需要创建一个ObjectName类 
 * 创建一个AdaptorServer，这个类将决定MBean的管理界面，这里用最普通的Html型界面。AdaptorServer其实也是一个MBean。 
 * ghl:name=HelloWorld的名字是有一定规则的，格式为：“域名:name=MBean名称”，域名和MBean名称都可以任意取。
 * 运行HelloAgent，然后打开网页：http://localhost:8082/,看效果!
 * @author Sachiel
 *
 */
public class HelloAgent {
	public static void main(String[] args) throws Exception {   
//      MBeanServer server = MBeanServerFactory.createMBeanServer();   
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();   
        ObjectName helloName = new ObjectName("Sachiel:name=HelloWorld");   
        server.registerMBean(new Hello(), helloName);
        
        ObjectName adapterName = new ObjectName(   
                "HelloAgent:name=htmladapter,port=8082");   
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();   
        server.registerMBean(adapter, adapterName);   
        adapter.start();   
        System.out.println("start.....");   
    }   

}
