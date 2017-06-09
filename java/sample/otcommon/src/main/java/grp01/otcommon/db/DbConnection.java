package grp01.otcommon.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import grp01.otcommon.common.OtCommon;

public class DbConnection extends OtCommon{
	Connection conn;
	Statement state;
	ResultSet rs;

	public DbConnection() throws Exception{
		conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/testdb7"
					,"test"
					,"test");

		state = conn.createStatement();
	}

	public ResultSet getResutlSet(String sql) throws Exception{
		rs = state.executeQuery(sql);

		return rs;
	}

	public void close(){
		try{
			out("finalize start");
			rs.close();
			state.close();
			conn.close();
			out("finalize end");
		}catch(Exception e){
			// ｽﾙｰ
			out(e.getMessage());
		}
	}

	@Override
	protected void finalize(){
		try{
			out("finalize start");
			rs.close();
			state.close();
			conn.close();
			out("finalize end");
		}catch(Exception e){
			// ｽﾙｰ
			out(e.getMessage());
		}
	}
}
