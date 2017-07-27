package cn.itcast.bos.interceptor;

import org.apache.struts2.ServletActionContext;

import cn.itcast.bos.domain.user.User;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class MyLoginInterceptor extends MethodFilterInterceptor{

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		User existUser=(User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		if(existUser==null){
			return "no_login";
		}
		return invocation.invoke();
		
	}

}
