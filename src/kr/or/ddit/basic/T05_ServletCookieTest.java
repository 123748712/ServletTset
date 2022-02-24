package kr.or.ddit.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class T05_ServletCookieTest extends HttpServlet {
/*
	쿠키(Cookie)
	서버가 클라이언트에 저장하는 정보
	클라이언트와 연결이 끊어져도 클라이언트에 저장된 정보가 유지되어 서버에 재 방문할 때 요청정보의 헤더 안에 포함되어 서버로 전달
	
	
	=== 구성요소 ===
	- 이름
	- 값
	- 유효시간(초)
	- 도메인 : ex)www.somehost.com .somehost.com
		     => 쿠키의 도메인이 쿠키를 생성한서버의 도메인을 벗어나면 브라우저는 쿠키를 저장(생성)하지 않는다.(보안)
	- 경로 : 쿠키를 공유할 기준 경로를 지정한다. (도메인 이후부분으로 디렉토리 수준) => 지정하지 않으면 실행한 URL의 경로부분이 사용됨
	
	
	=== 동작방식 ===
	- 쿠키생성단계 : 생성한 쿠키를 응답데이터의 헤더에 저장하여 웹브라우저에 전송한다.
	- 쿠키저장단계 : 브라우저는 응답데이터에 포함된 쿠키를 쿠키저장소에 보관한다. (쿠키종류에 따라 메모리나 파일에 저장됨)
	- 쿠키전송단계 : 웹브라우저는저장한 쿠키를 요청이 있을때마다 웹서버에 전송한다. (삭제되기 전까지)
	-- 웹서버는 브라우저가 전송한쿠키를 사용해서 필요한 작업을 수행함 --
*/

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		setCookieExam(req, resp); // 쿠키 설정 예제
//		readCookieExam(req, resp); // 쿠키 정보 읽기 예제
		deleteCookieExam(req, resp);
	}

	private void deleteCookieExam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	/*
		사용중인 쿠키정보를 삭제하는 방법
			
		1. 사용중인 쿠키정보를 이용하여 쿠키객체를 생성한다.	
		2. 쿠키객체의 최대 지속 시간을 0으로 설정한다.
		3. 설정한 쿠키객체를 응답헤더에 추가하여 전송한다.
	*/
		Cookie[] cookies = req.getCookies();
		
		// 응답헤더에 인코딩 및 Content-Type 설정
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		String title = "쿠키정보 삭제 예제";
		
		out.println("<!DOCTYPE html><html><head>"
					+ "<title>" + title + "</title></head>"
					+ "<body>");
		
		if(cookies != null) {
			out.println("<h2>" + title + "</h2>");
			
			for(Cookie cookie : cookies) {
				if((cookie.getName()).equals("userId")) {
					// 쿠키 제거하기
					cookie.setMaxAge(0); // 0 => 삭제하는 의미 포함
					resp.addCookie(cookie);
					out.print("삭제한 쿠키 : " + cookie.getName() + "<br>");
				}
				out.print("쿠키이름 : " + cookie.getName() + ", ");
				out.print("쿠키값 : " + URLDecoder.decode(cookie.getValue(), "UTF-8") + "<br>");
			}
		} else {
			out.print("<h2>쿠키정보가 없습니다.</h2>");
		}
		out.print("</body></html>");
		}

	private void readCookieExam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// 현재 도메인에서 사용중인 쿠키저오 배열 가져오기
		Cookie[] cookies = req.getCookies(); // 요청헤더에 담겨있는 cookie 정보를 가져와 담아준다.
		
		// 응답헤더에 인코딩 및 Content-type 설정
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		String title = "쿠키정보 읽기 예제";
		out.println("<!DOCTYPE html>"
					+ "<head><title>" + title
					+ "</title></head>"
					+ "<body>");
		
		if(cookies != null) {
			out.println("<h2>" + title + "</h2>");
			
			for(Cookie cookie : cookies) {
				out.println("name : " + cookie.getName() + "<br>");
				out.println("value : " + URLDecoder.decode(cookie.getValue(), "UTF-8") + "<br>");
				out.println("<hr>");
			}
		} else {
			out.println("<h2>쿠키정보가 없습니다.</h2>");
		}
		out.println("</body></html>");
	}

	// 쿠키 설정 예제
	private void setCookieExam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	/*
		쿠키 정보를 설정하는 방법
			
		1. 쿠키 객체 생성 | 사용불가문자 ( 공백 [] () = , " / ? @ : ; ) = RFC2965
		Cookie cookie = new Cookie("key", "value");
		쿠키값은 사용불가문자를 제외한 나머지 출력가능한 아스키문자 사용 가능
		=> 이외의 값(한글)을 사용시에는 URLEncoder.encode 사용하여 인코딩 처리를 해준다.
		
		 2. 쿠키 최대 지속시간 설정 (초단위)
		 => 지정하지 않으면 브라우저를 종료할 때 쿠키를 함께 삭제한다.
		 cookie.setMaxAge(60*60*24); // 24시간
		 
		 3. 응답헤더에 쿠키 객체를 추가한다.
		 response.addCookie(cookie);
		 => 출력버퍼가 flush된 이후엔 응답헤더를 통해서 웹브라우저에 전달하기 때문에 쿠키를 추가할 수 없다.
	*/
		
		// 쿠키 추가하기
		Cookie userId = new Cookie("userId", req.getParameter("userId"));
		
		// 쿠키값에 한글을 사용시 인코딩처리를 해준다.
		Cookie name = new Cookie("name", URLEncoder.encode(req.getParameter("name"), "UTF-8"));
		
		// 쿠키 소멸시간 설정(초단위) => 지정하지 않으면 브라우저 종료시 쿠키 함께 삭제
		userId.setMaxAge(60*60*24);
		
		// javascript 직접 접근금지
		userId.setHttpOnly(true);
		
		name.setMaxAge(60*60*48);
		
		// 응답헤더에 쿠키 추가하기
		// 클라이언트에 저장
		resp.addCookie(userId);
		resp.addCookie(name);
		
		// 응답헤더에 인코딩 및 Content-type 설정
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		String title = "쿠키 설정 예제";
		
		out.println("<DOCTYPE html>"
					+ "<html>"
					+ "<head><title>" + title
					+ "</title></head>"
					+ "<body><h1 align=\"center\">"
					+ title + "</h1>"
					+ "<ul><li><b>ID</b>:"
					+ req.getParameter("userId") + "</li>"
					+ "<li><b>이름</b>:"
					+ req.getParameter("name") + "</li></ul>"
					+ "</body></html>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp); // GET, POST 어느 방식이던 GET 방식이 사용된다.
	}
}
