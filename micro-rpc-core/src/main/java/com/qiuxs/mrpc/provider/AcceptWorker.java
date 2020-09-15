package com.qiuxs.mrpc.provider;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 请求接受工作器
 * 功能描述: <br/>  
 * 新增原因: TODO<br/>  
 * 新增日期: 2020年9月15日 下午5:33:05 <br/>  
 *  
 * @author qiuxs   
 * @version 1.0.0
 */
public class AcceptWorker extends Thread {

	private static Logger log = LoggerFactory.getLogger(AcceptWorker.class);

	private final ServerSocket ss;
	private boolean isRunning = false;
	private final ThreadPoolExecutor pool;
	private final Configuration configuration;

	public AcceptWorker(ServerSocket ss, Configuration configuration) {
		this.ss = ss;
		this.configuration = configuration;
		this.pool = new ThreadPoolExecutor(this.configuration.getThreadPoolCoreSize(), this.configuration.getThreadPoolMaxSize(), this.configuration.getKeepAliveTimeout(), TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(this.configuration.getMaxQueueSize()));
	}

	@Override
	public void run() {
		while (this.isRunning) {
			try {
				Socket socket = this.ss.accept();
				if (this.isBusy()) {
					this.reject(socket);
				}

				this.pool.execute(new ProviderWorker(socket, this.configuration));
			} catch (IOException e) {
				log.error("Accept Socket Error, ext = " + e.getLocalizedMessage(), e);
			}
		}
	}

	/**
	 * 拒绝请求
	 *  
	 * @author qiuxs  
	 * @param socket
	 */
	private void reject(Socket socket) {
		
	}

	private boolean isBusy() {
		return this.pool.getActiveCount() == this.pool.getMaximumPoolSize();
	}

	@Override
	public synchronized void start() {
		this.isRunning = true;
		super.start();
	}

}
