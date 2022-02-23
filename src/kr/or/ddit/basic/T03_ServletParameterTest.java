package kr.or.ddit.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 서블릿을 이용한 파라미터 데이터 처리 예제
public class T03_ServletParameterTest extends HttpServlet {
	// 요청 객체로부터 파라미터 데이터를 가져오는 방법
	
	// getParameter() - 파라미터 값이 한개인 경우 | return => String
	// getParameterValues() - 파라미터 값이 여러개인 경우 | return => String[]
	// getParameterNames() - 요청메시지에 존재하는 모든 파라미터 정보를 가져옴
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String userName = req.getParameter("username");
		String password = req.getParameter("password");
		String gender = req.getParameter("gender");
		String hobby = req.getParameter("hobby");
		String rlgn = req.getParameter("rlgn");
		String[] food = req.getParameterValues("food");
		
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<p>username : " + userName + "</p>");
		out.println("<p>password : " + password + "</p>");
		out.println("<p>gender : " + gender + "</p>");
		out.println("<p>hobby : " + hobby + "</p>");
		out.println("<p>종교 : " + rlgn + "</p>");
	
		if(food != null) {
			for(String s : food) {
				out.print("<p>food : " + s + "</p>");
			}
		}
		Enumeration<String> params = req.getParameterNames();
		while(params.hasMoreElements()) {
			String param = params.nextElement();
			System.out.println("<p>파라미터 이름 : " + param + "</p>");
		}
		out.println("</body>");
		out.println("</html>");
	}

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
