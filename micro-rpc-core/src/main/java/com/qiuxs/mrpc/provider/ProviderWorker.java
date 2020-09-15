package com.qiuxs.mrpc.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.qiuxs.mrpc.defin.MicroRpcRequest;
import com.qiuxs.mrpc.defin.MicroRpcResponse;
import com.qiuxs.mrpc.ex.ExceptionUtils;
import com.qiuxs.mrpc.protoc.ProtocolHandler;
import com.qiuxs.mrpc.util.SerializeUtil;

public class ProviderWorker extends Thread {

	private final Socket s;
	private final InputStream is;
	private final OutputStream os;
	private boolean isRunning;

	private final Configuration configuration;
	private ProtocolHandler protocolHandler;

	public ProviderWorker(Socket s, Configuration configuration) {
		this.s = s;
		this.configuration = configuration;
		try {
			this.is = this.s.getInputStream();
			this.os = this.s.getOutputStream();
		} catch (IOException e) {
			throw ExceptionUtils.unchecked(e);
		}
		protocolHandler = new ProtocolHandler(this.configuration.getBuffer());
	}

	@Override
	public void run() {
		while (this.isRunning) {
			try {
				MicroRpcRequest request = this.parseRequest();
				MicroRpcResponse response = this.dispatch(request);
				this.sendResponse(response);
			} catch (IOException | ClassNotFoundException e) {
				this.sendError(e);
			}
		}
	}

	/**
	 * 分发处理请求，获取响应结果
	 *  
	 * @author qiuxs  
	 * @param request
	 * @return
	 */
	private MicroRpcResponse dispatch(MicroRpcRequest request) {

		return null;
	}

	/**
	 * 解析请求信息
	 *  
	 * @author qiuxs  
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private MicroRpcRequest parseRequest() throws IOException, ClassNotFoundException {
		byte[] data = this.protocolHandler.readData(is);
		MicroRpcRequest request = null;
		try {
			request = SerializeUtil.unserial(data);
		} catch (ClassNotFoundException | IOException e) {
			throw e;
		}
		return request;
	}

	/**
	 * 发送正常响应
	 *  
	 * @author qiuxs  
	 * @param response
	 */
	private void sendResponse(MicroRpcResponse response) {
		try {
			byte[] data = SerializeUtil.serial(response);
			this.protocolHandler.write(this.os, data);
		} catch (IOException e) {
			this.sendError(e);
		}
	}

	/**
	 * 发送异常返回
	 *  
	 * @author qiuxs  
	 * @param e
	 */
	private void sendError(Throwable e) {
		
	}

	@Override
	public synchronized void start() {
		this.isRunning = true;
		super.start();
	}

	public void shutdown() {
		this.isRunning = false;
	}

}