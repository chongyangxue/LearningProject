import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.junit.Test;


public class MyUnitTest {

	@Test
	public void testArrayList() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(null);
		list.add("1");
		list.add("2");
			
		for(String str : list) {
			if(str != null) {
				System.out.println(str);
			}
		}
	}
	
	@Test
	public void testDB() throws Exception {
		String sql = "insert into student values(?,?,?)";
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hibernate", "root", "090017");
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setObject(1, null);
		ps.setObject(2, "yang");
		ps.setObject(3, 23);
		ps.executeUpdate();
	}
	
	
}
