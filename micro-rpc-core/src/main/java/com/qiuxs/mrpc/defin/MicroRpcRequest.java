package com.qiuxs.mrpc.defin;

import java.io.Serializable;
import java.util.Map;

/**
 * 远程调用请求载体
 * 功能描述: <br/>  
 * 新增原因: TODO<br/>  
 * 新增日期: 2020年9月15日 下午6:16:10 <br/>  
 *  
 * @author qiuxs   
 * @version 1.0.0
 */
public class MicroRpcRequest implements Serializable {

	private static final long serialVersionUID = 4807028714885102474L;

	/** 请求ID */
	private String requestId;
	/** 接口类名 */
	private String interfaceName;
	/** 方法名 */
	private String methodName;
	/** 扩展属性 */
	private Map<String, Object> extra;
	/** 参数类型数组 */
	private Class<?>[] paramaterTypes;
	/** 参数数组 */
	private Object[] paramaters;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Map<String, Object> getExtra() {
		return extra;
	}

	public void setExtra(Map<String, Object> extra) {
		this.extra = extra;
	}

	public Class<?>[] getParamaterTypes() {
		return paramaterTypes;
	}

	public void setParamaterTypes(Class<?>[] paramaterTypes) {
		this.paramaterTypes = paramaterTypes;
	}

	public Object[] getParamaters() {
		return paramaters;
	}

	public void setParamaters(Object[] paramaters) {
		this.paramaters = paramaters;
	}

}
