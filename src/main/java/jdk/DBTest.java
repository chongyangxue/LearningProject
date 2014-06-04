package jdk;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBTest {

	public static void main(String[] args) throws Exception {
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
