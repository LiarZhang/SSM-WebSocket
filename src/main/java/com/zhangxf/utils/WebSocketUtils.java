package com.zhangxf.utils;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * websocket的服务器端
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 *                 客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket")
public class WebSocketUtils {

	/**
	 * 客户端通过下面建立连接
	 * websocket = new WebSocket("ws://10.2.8.116:8080/SSM/websocket"); 
	 * 建立连接之后，则将信息传递到这里，进行处理之后，传递给客户端
	 */
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private static ConcurrentMap<String, WebSocketUtils> map = new ConcurrentHashMap<String, WebSocketUtils>();

	
	private Session session;

	/**
	 * 传递两个信息，一个是IP，推送消息，用*作为分割符
	 * 该方法会调用WebSocket的onmessage方法，将对应页面传递到客户端，客户端发出相应的请求
	 */
	public void flushPage(String message) {
		onMessage(message);
	}

	/**
	 * 连接建立成功调用的方法
	 * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("有客户端进来了----------------"+session.getId());
		map.put(session.getId(), this);
		this.session = session;
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		// 根据当前对象去获取key，然后删除
		// map.remove(this); //从set中删除 //在线数减1
	}

	/**
	 * 收到客户端消息后调用的方法
	 * @param message  客户端发送过来的消息,ip地址，推送消息，用*作为分割符
	 * @param session  可选的参数
	 */
	@OnMessage
	public void onMessage(String message) {
		if (message.split("\\*").length == 2) {// 主动刷新页面
			String[] str = message.split("\\*");
		
			String ip = str[0];// 
			String result = str[1];
			WebSocketUtils item = map.get(ip);// 对对象为空的情况主动处理,对象为空
			try {// 异常处理
				System.out.println("--------websocket发送的对象----------" + item);
				item.session.getBasicRemote().sendText(result);
				System.out.println("----发送成功----" + result+ "------------");
			} catch (IOException e) {
				System.out.println("没有找到相应的websocket客户端");
			}
		} else {// 发送的只是ip，用来识别客户端地址
			System.out.println("来自客户端的消息:" + message);
			map.put(message, this);//ip
			
			//连接上即发送
			try {
				map.get(message).session.getBasicRemote().sendText("推送消息");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发生错误时调用
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("websocket-----------发生错误");
	}

}
