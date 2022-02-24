package kr.or.ddit.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class T06_ServletSessionTest extends HttpServlet {
/*
	세션(HttpSession) 객체
	
	- 세션을 통해서 사용자(웹브라우저)별로 구분하여 정보를 관리할 수 있다. (세션ID이용)
	- 쿠키를 사용할때 보다 보안이 향상된다. (정보가 서버에 저장됨)
	
	- 세션객체를 가져오는 방법
	HttpSession session = req.getSession(Boolean값)
	Boolean값 : true => 세션객체가 존재하지 않으면 새로 생성
			   false => 세션객체가 존재하지 않으면 null 리턴
			   

	- 세션객체 삭제 방법
	1. invalidate() 호출
	2. setMaxInactiveInterval(int interval) 호출
	=> 일정시간(초) 동안 요청이 없으면 세션객체 삭제
	3. web.xml에 <session-config> 설정하기 (분단위)
*/
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 세션을 가져오는데 없으면 새로 생성한다.
		HttpSession session = req.getSession(true);
		
//		session.invalidate(); // 세션이 삭제된다.
		
		
		// 생성시간 가져오기
		Date createTime = new Date(session.getCreationTime());
		
		// 마지막으로 접근한 시간 가져오기
		Date lastAccessTime = new Date(session.getLastAccessedTime());
		
		String title = "재방문을 환영합니다.";
		int visitCount = 0; // 방문횟수
		String userId = "sem"; // 사용자 ID
		
		if(session.isNew()) { // 새로만든 세션인지
			title = "첫방문을 환영합니다.";
			session.setAttribute("userId", userId);
		} else {
			visitCount = (int) session.getAttribute("visitCount");
			visitCount++;
			userId = (String) session.getAttribute("userId");
		}
		System.out.println("visitCount : " + visitCount);
		session.setAttribute("visitCount", visitCount);
		
		// 응답헤더에 인코딩 및 Content-Type 설정
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		out.println("<!DOCTYPE html><html>"
					+ "<head><title>" + title
					+ "</title></head>"
					+ "<body><h1 align=\"center\">"
					+ title + "</h1>"
					+ "<h2 align=\"center\">세션정보</h2>"
					+ "<table border=\"1\" align=\"center\">"
					+ "<tr bgcolor=\"hotpink\">"
					+ "<th>구분</th><th>값</th>" + "</tr>"
					+ "<tr><td>세션ID</td><td>" + session.getId()
					+ "</td></tr>"
					+ "<tr><td>생성시간</td><td>" + createTime
					+ "</td></tr>"
					+ "<tr><td>마지막 접근시간</td><td>" + lastAccessTime
					+ "</td></tr>"
					+ "<tr><td>User ID</td><td>" + userId
					+ "</td></tr>"
					+ "<tr><td>방문횟수</td><td>" + visitCount
					+ "</td></tr></table>"
					+ "</body></html>");
		
//		session.setMaxInactiveInterval(30);
		}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}