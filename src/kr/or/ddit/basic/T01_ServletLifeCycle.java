package kr.or.ddit.basic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 서블릿의 라이프사이클을 확인하기 위한 예제
// 서블릿 => 컨테이너(서블릿 엔진)에 의해 관리되는 자바기반 웹 컴포넌트, 동적인 웹컨텐츠 생성 가능하게 해줌
public class T01_ServletLifeCycle extends HttpServlet{
	
	// 사용자(클라이언트)가 URL을 입력하면 HTTP Request가 Servlet Container로 전송한다.
	// 요청을 전송받은 Servlet Container는 HttpServletRequest(요청), HttpServletResponse(응답) 객체를 생성한다.
	// web.xml을 기반으로 사용자가 요청한 URL이 어느 서블릿에 대한 요청인지 찾는다.
	// 해당 서블릿에서 service메소드를 호출한 후 클리아언트의 GET, POST여부에 따라 doGet() 또는 doPost()를 호출한다.
	// doGet() or doPost() 메소드는 동적 페이지를 생성한 후 HttpServletResponse객체에 응답을 보낸다.
	// 응답이 끝나면 HttpServletRequest, HttpServletResponse 두 객체를 소멸시킨다.

	@Override
	public void init() throws ServletException {
		//초기화 코드 작성
		// Servlet이 처음 요청될때 초기화 하는 메서드 (new class 와 동일)
		System.out.println("init() 호출됨...");
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 실제적인 작업 수행이 시작되는 지점 (자바의 메인메서드 역할)
		// 요청을 받고 응답을 내려줄때 필요한 메서드
		System.out.println("service() 호출됨...");
		super.service(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 메서드 방식이 GET방식인 경우 호출됨
		System.out.println("doGet() 호출됨...");
		
		throw new ServletException("일부로 발생시킨 예외");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 메서드 방식이 POST방식인 경우 호출됨
		System.out.println("doPost() 호출됨...");
	}
	
	@Override
	public void destroy() {
		// 객체 소멸시(컨테이너로부터 서블릿객체 제거시) 필요한 코드 작성
		// 더이상 사용되지 않으면 자동으로 호출하여 자동 제거(다시 실행하려면 init 해야함)
		System.out.println("destroy() 호출됨...");
	}
}