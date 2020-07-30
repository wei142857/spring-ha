package com.sbp.app.component;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

@Component // 组件注解
public class DefaultHealthIndicator extends AbstractHealthIndicator{
	/**
	 * 会在/actuator/health中添加自定义的属性信息
	 * 如果需要自定义访问路径则需要使用到 @Endpoint @ReadOperation两个注解
	 * @Endpoint 放在类上：定义 /actuator/？
	 * @ReadOperation 放在方法上：定义返回json信息，方法返回值是list
	 */
	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		builder.up().withDetail("type", 0);
	}

}
