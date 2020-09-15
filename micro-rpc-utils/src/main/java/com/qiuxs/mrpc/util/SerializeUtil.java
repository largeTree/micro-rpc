package com.qiuxs.mrpc.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 序列化与反序列化,如果输入null,输出也为null
 * @author qiuxs
 * 2019年6月15日 下午9:56:48
 */
public class SerializeUtil {

	private static final Logger log = LoggerFactory.getLogger(SerializeUtil.class);

	/**
	 * 序列化。与serialize方法的区别是，该方法序列化失败时，将抛出异常。
	 * @author lsh  
	 * @param object 待序列化对象
	 * @return 序列化后字节数组
	 * @throws IOException 
	 */
	public static byte[] serial(Object object) throws IOException {
		byte[] bytes = null;
		if (object != null) {
			ObjectOutputStream oos = null;
			ByteArrayOutputStream baos = null;
			try {
				baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(object);
				bytes = baos.toByteArray();
			} catch (IOException e) {
				throw e;
			} finally {
				close(baos);
				close(oos);
			}
		}
		return bytes;
	}

	/**
	 * 序列化
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		try {
			return serial(object);
		} catch (Exception e) {
			log.error("ex=" + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 反序列化。与unserialize方法的区别是，反序列化失败时，将抛出异常。
	 * @author lsh  
	 * @param bytes 对象序列化后的字节数组
	 * @return 反序列化后的对象
	 * @throws IOException,ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public static <V> V unserial(byte[] bytes) throws IOException, ClassNotFoundException {
		V o = null;
		if (bytes != null) {
			ByteArrayInputStream bais = null;
			ObjectInputStream ois = null;
			try {
				bais = new ByteArrayInputStream(bytes);
				ois = new ObjectInputStream(bais);
				o = (V) ois.readObject();
			} catch (IOException | ClassNotFoundException e) {
				throw e;
			} finally {
				close(ois);
				close(bais);
			}
		}
		return o;
	}

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 */
	public static <V> V unserialize(byte[] bytes) {
		try {
			return unserial(bytes);
		} catch (Exception e) {
			log.error("ex=" + e.getMessage(), e);
			return null;
		}
	}

	private static void close(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				//
			}
		}
	}

	private static void close(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				//
			}
		}
	}

}
