package com.qiuxs.mrpc.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * 流处理工具类
 * 功能描述: <br/>  
 * 新增原因: TODO<br/>  
 * 新增日期: 2020年9月15日 下午4:46:00 <br/>  
 *  
 * @author qiuxs   
 * @version 1.0.0
 */
public class StreamUtils {
	
	/**
	 * 获取数据长度
	 *  所有位数加在一起
	 * @author qiuxs  
	 * @param is
	 * @param bits
	 * @return
	 * @throws IOException
	 */
	public static int getLengthByBits(InputStream is, int bits) throws IOException {
		int size = 0;
		for (int i = 0; i < bits; i++) {
			int read = is.read();
			size += read;
		}
		return size;
	}
	
	/**
	 * 获取数据长度位数
	 * 
	 * 读取1位，如果等于255 则其中254是位数，1表示后面还有
	 *  循环读取到第一个不是255的位，前面254+最后一位 则为数据长度位数
	 * @author qiuxs  
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static int getTotalSizeBits(InputStream is) throws IOException {
		int totalSizeBits = 0;
		int sizeLength = 0;
		boolean hasNext = false;
		
		do {
			sizeLength = is.read();
			if (sizeLength == -1) {
				throw new RuntimeException("socket closed");
			}
			if (sizeLength == 255) {
				hasNext = true;
				totalSizeBits += 254;
			} else {
				hasNext = false;
				totalSizeBits += sizeLength;
			}
		} while (hasNext);
		
		return totalSizeBits;
	}

}
