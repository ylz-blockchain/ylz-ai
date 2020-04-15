package com.ylz.ai.auth.server.modules.user.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 自定义处理器
 * @Author haifeng.lv
 * @Date 2019/12/20 9:50
 */
@Component
public class AccessHandler implements AccessDeniedHandler {
	@Autowired
	private ObjectMapper objectMapper;
 
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		Map<String, Object> map = new HashMap<>();
		map.put("error", "403");
		map.put("message", accessDeniedException.getMessage());
		map.put("path", request.getServletPath());
		map.put("timestamp", LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getWriter().write(objectMapper.writeValueAsString(map));
	}
}