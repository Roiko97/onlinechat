package com.jung.onlinechat.websocket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jung.onlinechat.dao.UserLoginDao;
import com.jung.onlinechat.dao.UserchatDao;
import com.jung.onlinechat.service.OnlinechatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/***
 * Web Scoket服务.
 * <ul>
 * URL expression as
 * <li>not use SSL , ws://host/{yourProjectName}/websocket/{principal}
 * <li>if use SSL , wss://host/{yourProjectName}/websocket/{principal}
 * <li>观察者模式
 * 
 *
 *
 */
@Component
@Service
@ServerEndpoint(value = "/websocket/{principal}")
public class DefaultWebSocket {

	@Autowired
	OnlinechatServer onlinechatServer;
	@Autowired
	UserchatDao userchatDao;
	/** Web Socket连接建立成功的回调方法 */
	@OnOpen
	public void onOpen(@PathParam("principal") String principal, Session session) {

		// create observer
		WebSocketObserver observer = new WebSocketObserver(session);
		// get subject
		WebSocketSubject subject = WebSocketSubject.Holder.getSubject(principal);
		// register observer into subject
		subject.addObserver(observer);
	}

	/** 服务端收到客户端发来的消息 */
	@OnMessage
	public void onMessage(@PathParam("principal") String principal, String message, Session session) {
		//principal = 发送者名
		// message EX:{"type":"","data":""}
		JSONObject json = JSON.parseObject(message);
		String ToUser = json.getString("type");
		String Msg = json.getString("data");

		JSONObject childJson = JSON.parseObject(Msg);
		String info = childJson.getString("msg");
		String types = childJson.getString("type");

		// 对消息的处理,根据义务自行定义。
		// 这里以打印消息为例
		String log = "receive msg from client,principal : " + principal + ", Touser = " + ToUser + ", Msg = " + Msg;
		System.out.println(log);

		if(!types.equals("recall")){ //不为删除
			SendMessage(principal, ToUser,info,types);
		}else{ //将要撤回相关信息
			SendDeleteMessage(ToUser,info);
		}

//		// 如果Msg不为空，代表发送信息
//		if(info !=null){
//			SendMessage(principal, ToUser,info,types);
//		}
//		else{ //如果Msg为空，代表撤回信息
//			String id = ToUser.substring(ToUser.indexOf("&")+1);
//			System.out.println(id);
//			ToUser =ToUser.substring(0,ToUser.indexOf("&"));
//			SendDeleteMessage(ToUser,id);
//		}
	}

	@OnClose
	public void onClose(@PathParam("principal") String principal, Session session) {

		// get subject
		WebSocketSubject subject = WebSocketSubject.Holder.getSubject(principal);

		// get observer
		WebSocketObserver observer = new WebSocketObserver(session);
		// delete observer from subject
		subject.deleteObserver(observer);

		// close session and close Web Socket connection
		try {
			if (session.isOpen()) {
				session.close();
			}
		} catch (IOException e) {
			throw new RuntimeException("close web socket session error.", e);
		}

	}

	@OnError
	public void onError(@PathParam("principal") String principal, Session session, Throwable error) {
		throw new RuntimeException("web socket error.", error);
	}

	/**
	 * 向客户端发送信息
	 * @param FromUser
	 * @param ToUser
	 * @param msg
	 */
	public void SendMessage(String FromUser,String ToUser,String msg,String types){
		//接收浏览器
		String principal = ToUser;
		JSONObject jsonObject = new JSONObject();
		String type="radio"; //和前端对应
		//存储发送信息
		jsonObject.put("fromuser",FromUser);
		jsonObject.put("content",msg);
		jsonObject.put("type",types);
		WebSocketSubject webSocketSubject = WebSocketSubject.Holder.getSubject(principal);
		//服务器发送信息 发送者，发送信息
		webSocketSubject.notify(type,jsonObject.toJSONString());
	}

	public void SendDeleteMessage(String ToUser,String id){
		String principal = ToUser;
		JSONObject jsonObject = new JSONObject();
		String type="notice";
		jsonObject.put("id",id);
		WebSocketSubject webSocketSubject = WebSocketSubject.Holder.getSubject(principal);
		//服务器发送信息 发送者，发送信息
		webSocketSubject.notify(type,jsonObject.toJSONString());
	}
}
