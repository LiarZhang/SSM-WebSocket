package com.zhangxf.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangxf.utils.WebSocketUtils;


@Controller
public class WebSocKetController {
	
	private static WebSocketUtils ws = new WebSocketUtils();

	/**消息推送：
	 * 方法一：通过网页触发websocket,实现消息推送
	 * 方法二：通过定时器检测数据变化,然后再触发推送
	 */
	@RequestMapping("/webSocKet")
	@ResponseBody
	private String triggerWebsocket() {
		//ws.flushPage("192.168.15.1"+ "*"+"user/success");
		ws.onMessage("10.2.8.116"+ "*"+"user/success");
		return "success";
	}
	/**
	 * 用户先打开页面,向客户端发送存储客户端ip信息
	 * @param request 请求
	 * @param response 
	 * @return
	 */
	@RequestMapping("/index")
	private String index(HttpServletRequest request, HttpServletResponse response){
		// 向客户端输出cookie
		Cookie cookie = new Cookie("ip", request.getRemoteAddr());
		cookie.setMaxAge(24 * 60 * 60 * 7);// 七天
		response.addCookie(cookie);
		
		return "/WEB-INF/jsp/index.jsp";  
	}
}
