package com.qiuxs.mrpc.protoc;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.qiuxs.mrpc.util.StreamUtils;

/**
 * 自定义协议处理器
 * 功能描述: <br/>  
 * 新增原因: TODO<br/>  
 * 新增日期: 2020年9月15日 下午4:32:34 <br/>  
 *  
 * @author qiuxs   
 * @version 1.0.0
 */
public class ProtocolHandler {

	private static final int DEFAULT_BUFFER = 1024;

	private final int buffer;

	/**
	 * 
	 * Creates a new instance of ProtocolHandler.  
	 *  
	 * @param buffer
	 */
	public ProtocolHandler(int buffer) {
		if (buffer < 0 || buffer > 65535) {
			buffer = DEFAULT_BUFFER;
		}
		this.buffer = buffer;
	}

	/**
	 * 发送数据
	 *  
	 * @author qiuxs  
	 * @param os
	 * @param data
	 * @throws IOException
	 */
	public void write(OutputStream os, byte[] data) throws IOException {
		// 长度位数
		int sizeBits = (int) Math.ceil((double) data.length / 255);
		do {
			int len = 0;
			if (sizeBits > 254) {
				len = 255;
				sizeBits -= 254;
			} else {
				len = sizeBits;
				sizeBits -= len;
			}

			os.write(len);
		} while (sizeBits > 0);

		// 长度
		int length = data.length;
		do {
			int len = 0;
			if (length > 255) {
				len = 255;
			} else {
				len = length;
			}

			length -= len;
			os.write(len);
		} while (length > 0);

		// 数据
		os.write(data);
		os.flush();
	}

	/**
	 * 读取数据
	 *  
	 * @author qiuxs  
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public byte[] readData(InputStream is) throws IOException {
		
		BufferedInputStream bis = new BufferedInputStream(is, this.buffer);
		
		// 数据长度位数
		int totalSizeBits = StreamUtils.getTotalSizeBits(bis);
		if (totalSizeBits > 0) {

			// 数据长度
			int length = StreamUtils.getLengthByBits(bis, totalSizeBits);

			byte[] data = new byte[length];
			int offset = 0;
			int len = length > this.buffer ? this.buffer : length;

			while (len > 0) {
				int readLen = bis.read(data, offset, len);
				if (readLen <= 0) {
					break;
				}
				length -= len;
				if (length < len) {
					len = length;
				}
			}
			return data;
		}
		return new byte[0];
	}

}
