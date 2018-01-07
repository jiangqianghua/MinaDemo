package com.jiang.cn.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * mina服务器
 */
public class MinaServer {

	public static void main(String[] args) {
		// 创建监听对象
		IoAcceptor acceptor = new NioSocketAcceptor();
		//添加我们日志过滤器
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		//接受和发送的数据都是对象序列化类型
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		//设置事件处理类
		acceptor.setHandler(new MyHandler());
		// 设置读缓存区大小
		acceptor.getSessionConfig().setReadBufferSize(2048);
		//10s 不操作，让mina处理空闲状态
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		try {
			// 绑定监听端口
			acceptor.bind(new InetSocketAddress(9125));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("start service");
	}
	/**
	 * 负责Session创建监听和消息发送和接受
	 * @author jiangqianghua
	 *
	 */
	private static class MyHandler extends IoHandlerAdapter
	{
		// session创建
		@Override
		public void sessionCreated(IoSession session) throws Exception {
			super.sessionCreated(session);
		}
		
		//  session 打开
		@Override
		public void sessionOpened(IoSession session) throws Exception {
			super.sessionOpened(session);
		}
		//  消息接受
		@Override
		public void messageReceived(IoSession session, Object message) throws Exception {
			super.messageReceived(session, message);
			String str = message.toString();
			Date date = new Date();
			session.write(date.toString());
			System.out.println("接受的数据:"+str);
		}
		// 消息发送
		@Override
		public void messageSent(IoSession session, Object message) throws Exception {
			super.messageSent(session, message);
		}
		
		// session关闭和移除
		@Override
		public void sessionClosed(IoSession session) throws Exception {
			// TODO Auto-generated method stub
			super.sessionClosed(session);
		}
	}
}
