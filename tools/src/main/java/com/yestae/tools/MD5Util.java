package com.yestae.tools;

import java.security.MessageDigest;

public class MD5Util {
	/**
	 * 生成 MD5
	 *
	 * @param data
	 *            待处理数据
	 * @return MD5结果
	 */
	public static String MD5(String data) throws Exception {
		java.security.MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] array = md.digest(data.getBytes("UTF-8"));
//		StringBuilder sb = new StringBuilder();
//		for (byte item : array) {
//			sb.append(Integer.toHexString((item & 0xFF) | 0x100)
//					.substring(1, 3));
//		}
//		return sb.toString();
		return toHex(array);
	}

	
	  private static final String toHex(byte[] paramArrayOfByte){
	    if (paramArrayOfByte == null) {
	      return null;
	    }
	    StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length * 2);
	    for (int i = 0; i < paramArrayOfByte.length; i++){
	      if ((paramArrayOfByte[i] & 0xFF) < 16) {
	        localStringBuffer.append("0");
	      }
	      localStringBuffer.append(Long.toString(paramArrayOfByte[i] & 0xFF, 16));
	    }
	    return localStringBuffer.toString();
	  }
}
