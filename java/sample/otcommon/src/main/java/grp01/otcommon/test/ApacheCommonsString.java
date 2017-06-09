package grp01.otcommon.test;

import java.security.MessageDigest;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.MathUtils;

import grp01.otcommon.common.OtCommon;

public class ApacheCommonsString extends OtCommon{

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		ApacheCommonsString acs = new ApacheCommonsString();

		try{
			acs.test4();
		}catch(Exception e){
			out(e.getMessage());
		}
	}

	public void test1(){
		if(StringUtils.isEmpty(null)){
			out("empty");
		}
	}

	public void test2(){
		String test = UUID.randomUUID().toString();
		out(test);
	}

	public void test3() throws Exception{
		String param = UUID.randomUUID().toString();

		MessageDigest algorithm = MessageDigest.getInstance("MD5");
        algorithm.reset();
        algorithm.update(param.getBytes());
        byte[] messageDigest = algorithm.digest();

//        out(algorithm.digest()[0]);
//
//        for(byte b: algorithm.digest()){
//        	out(b);
//        }
	}

	public void test4(){
		int hash = MathUtils.hash(1);
		out(hash);
	}

}
