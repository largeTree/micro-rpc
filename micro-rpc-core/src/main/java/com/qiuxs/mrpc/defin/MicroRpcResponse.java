package com.qiuxs.mrpc.defin;

import java.io.Serializable;
import java.util.Map;

/**
 * 远程调用响应载体
 * 功能描述: <br/>  
 * 新增原因: TODO<br/>  
 * 新增日期: 2020年9月15日 下午6:20:31 <br/>  
 *  
 * @author qiuxs   
 * @version 1.0.0
 */
public class MicroRpcResponse implements Serializable {

	private static final long serialVersionUID = 7109862129432700572L;

	/** 响应ID */
	private String responseId;
	/** 请求ID */
	private String requestId;
	/** 响应数据 */
	private Object data;
	/** 扩展属性 */
	private Map<String, Object> extra;

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Map<String, Object> getExtra() {
		return extra;
	}

	public void setExtra(Map<String, Object> extra) {
		this.extra = extra;
	}

}
