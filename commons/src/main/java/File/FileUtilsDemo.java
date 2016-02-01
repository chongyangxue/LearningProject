/** 
 * File: FileUtilsDemo.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package File;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 * Description: Author: Sachiel Date: 2016-1-22
 */
public class FileUtilsDemo {

    private static File file = new File("/opt/demo.txt");

    @Test
    public void write() throws IOException {
        FileUtils.write(file, "程序换api", "UTF-8", true);

        List<String> lines = new ArrayList<String>();
        lines.add("欢迎访问:");
        lines.add("www.cxyapi.com");
        FileUtils.writeLines(file, lines, true);

        FileUtils.writeStringToFile(file, "作者：cxy", "UTF-8", true);
    }

    public void read() throws IOException {
        System.out.println(FileUtils.readFileToString(file, "UTF-8"));
        System.out.println(FileUtils.readLines(file, "UTF-8")); //返回一个list  
    }

    public void delete() throws IOException {
        FileUtils.deleteDirectory(file);
        FileUtils.deleteQuietly(file); //文件夹不是空任然可以被删除，永远不会抛出异常  
    }

    /**
     * moveDirectory：D:/cxyapi2里的内容是D:/cxyapi1的内容。
     * moveDirectoryToDirectory：D:/cxyapi2文件夹移动到到D:/cxyapi3里
     */
    public void move() throws IOException {
        //移动文件 或 文件夹  
        FileUtils.moveDirectory(file, new File("D:/cxyapi2")); //注意这里 第二个参数文件不存在会引发异常  
        FileUtils.moveDirectoryToDirectory(new File("D:/cxyapi2"), new File("D:/cxyapi3"), true);
    }

    public void copy() throws IOException {
        //结果是cxyapi和cxyapi1在同一目录  
        FileUtils.copyDirectory(new File("D:/cxyapi"), new File("D:/cxyapi1"));
        //结果是将cxyapi拷贝到cxyapi2下  
        FileUtils.copyDirectoryToDirectory(new File("D:/cxyapi"), new File("D:/cxyapi2"));

        //拷贝文件  
        FileUtils.copyFile(new File("d:/cxyapi.xml"), new File("d:/cxyapi.xml.bak"));
        //拷贝文件到目录中  
        FileUtils.copyFileToDirectory(new File("d:/cxyapi.xml"), new File("d:/cxyapi"));
        //拷贝url到文件  
        FileUtils.copyURLToFile(new URL("http://www.cxyapi.com/rss/cxyapi.xml"), new File("d:/cxyapi.xml"));
    }

    public void other() throws IOException {
        //判断是否包含文件或者文件夹  
        boolean b = FileUtils.directoryContains(new File("D:/cxyapi"), new File("D:/cxyapi/cxyapi.txt"));
        System.out.println(b);

        //获得临时目录 和 用户目录  
        System.out.println(FileUtils.getTempDirectoryPath());
        System.out.println(FileUtils.getUserDirectoryPath());

        //打开流，如果不存在创建文件及其目录结构  
        //第二个参数表示 文件流是否是追加方式  
        FileOutputStream fos = FileUtils.openOutputStream(new File("D:/cxyapi/cxyapi.txt"), true);
        fos.write(new String("欢迎访问：www.cxyapi.com\r\n").getBytes());
        fos.close();

        //文件 或 文件夹大小  
        System.out.println(FileUtils.sizeOf(new File("D:/cxyapi")));
        System.out.println(FileUtils.sizeOfDirectory(new File("D:/cxyapi")));
    }
}
