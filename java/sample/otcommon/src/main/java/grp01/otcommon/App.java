package grp01.otcommon;

import java.sql.ResultSet;

import grp01.otcommon.common.OtCommon;
import grp01.otcommon.db.DbConnection;

/**
 * Hello world!
 *
 */
public class App extends OtCommon
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        test01();
    }

    public static void test01(){
    	String sql = "select now()";
    	try{
    		DbConnection dc = new DbConnection();
    		ResultSet rs = dc.getResutlSet(sql);

    		while(rs.next()){
    			out(rs.getString(1));
    		}

    		dc.close();
    	}catch(Exception e){
    		out(e);
    	}
    }
}
