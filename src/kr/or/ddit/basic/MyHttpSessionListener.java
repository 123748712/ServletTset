package kr.or.ddit.basic;

import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MyHttpSessionListener implements HttpSessionListener, HttpSessionAttributeListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("[MyHttpSessionListener]" + "sessionCreated => " + " 세션ID : " + se.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("[MyHttpSessionListener]" + "sessionDestroyed => " + " 세션ID : " + se.getSession().getId());
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		System.out.println("[MyHttpSessionListener]" + "attributeAdded => " + event.getName() + " : " + event.getValue());
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		System.out.println("[MyHttpSessionListener]" + "attributeRemoved => " + event.getName() + " : " + event.getValue());
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		System.out.println("[MyHttpSessionListener]" + "attributeReplaced => " + event.getName() + " : " + event.getValue());
	}
}
