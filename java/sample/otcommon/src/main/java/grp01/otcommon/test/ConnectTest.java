package grp01.otcommon.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectTest {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		try{
			test01();

			out("end");
		}catch(Exception e){
			out(e.getMessage());
		}
	}

	public static void test01() throws Exception{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "create table t01(no varchar(10))";

		try{
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/sonar"
					,"sonar"
					,"sonar");

			st = conn.createStatement();
			st.executeUpdate(sql);

		}catch(Exception e){
			out(e.getMessage());
		}finally{
			rs.close();
			st.close();
			conn.close();
		}

	}

	public static void out(Object obj){
		System.out.println(obj);
	}
}
