package com.sbp.app.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "user") // 手动添加 actuator 的端点，通过访问/actuator/{id}即可访问 配合@ReadOperation使用
public class UserEndpoint {
	/**
	 * 不加这个注解会访问不到
	 * 不在配置文件设置 management.endpoints.web.exposure.include=？ 也会访问不到
	 * 手动添加actuator端点的方法
	 * 方法名称随意，方法返回值是list，会自动解析成JSON
	 * @return list数据
	 */
	@ReadOperation
	public List<Map<String,Object>> health(){
		List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
		Map<String,Object> m = new HashMap<String, Object>();
		m.put("user-id", 0);
		m.put("name", "Non");
		m.put("message", "default info");
		l.add(m);
		return l;
	}

}
