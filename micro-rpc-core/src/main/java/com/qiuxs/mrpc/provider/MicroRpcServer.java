package com.qiuxs.mrpc.provider;

import java.io.IOException;
import java.net.ServerSocket;

import com.qiuxs.mrpc.ex.ExceptionUtils;

/**
 * 服务端总入口
 * 功能描述: <br/>  
 * 新增原因: TODO<br/>  
 * 新增日期: 2020年9月15日 下午5:04:29 <br/>  
 *  
 * @author qiuxs   
 * @version 1.0.0
 */
public class MicroRpcServer {

	private final int port;
	private final ServerSocket ss;
	private final Thread accpetWorker;
	private final Configuration configuration;

	public MicroRpcServer(Configuration configuration) {
		this.configuration = configuration;
		this.port = this.configuration.getPort();
		try {
			this.ss = new ServerSocket(this.port);
		} catch (IOException e) {
			throw ExceptionUtils.unchecked(e);
		}
		this.accpetWorker = new AcceptWorker(this.ss, this.configuration);
	}

	public void start() {
		this.accpetWorker.start();
	}

}
