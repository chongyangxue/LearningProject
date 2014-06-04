package ClassLoader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyClassLoader extends ClassLoader{
	private String classDir;
	
	public  static void cypher(InputStream fis, OutputStream fos) throws IOException {
		int b = -1;
		while((b = fis.read()) != -1) {
			fos.write(b ^ 0xff);
		}
	}
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String fileName = classDir + "\\" + name + ".class";
		try{
			System.out.println("Loading class from " + classDir + " ...");
			FileInputStream fis = new FileInputStream(fileName);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			cypher(fis, bos);
			fis.close();
			byte[] bytes = bos.toByteArray();
			return defineClass(bytes, 0, bytes.length);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public MyClassLoader(String classDir) {
		this.classDir = classDir;
	}
	
	public static void main(String[] args) {
		FileInputStream fis;
		try{
			fis = new FileInputStream("E:\\develop\\workspace\\Test\\bin\\ClassLoader\\AttachmentClass.class");
			FileOutputStream fos = new FileOutputStream("classLoaderLib\\AttachmentClass.class");
			cypher(fis, fos);
			fis.close();
			fos.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
}
