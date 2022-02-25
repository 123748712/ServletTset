package kr.or.ddit.basic;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class T08_ServletFilter implements Filter { // Filter는 extends가 아닌 implements
/*
	ServletFilter
	
	=== 사용목적 ===
	- 클라이언트의 요청을 수행하기 전에 가로채 필요한 작업을 수행할 수 있다.
	- 클라이언트에 응답정보를 제공하기 전에 응답정보에 필요한 작업을 수행할 수 있다.
	
	
	== 사용 예 ===
	- 인증필터
	- 데이터 압출필터
	- 인코딩 필터
	- 로깅 및 감사처리 필터
	- 이미지 변환 필터 등
*/
	
	@Override
	public void destroy() {
		// 필터가 웹컨테이너에 의해 서비스로부터 제거되기 전에 호출됨
		System.out.println("T08_ServletFilter : destroy()" + "호출됨");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) // filter가 여러개 묶여 있는 것을 filter Chain 이라고 한다.
			throws IOException, ServletException {
		System.out.println("T08_ServletFilter 시작");
			
		// 클라이언트의 IP주소 가져오기
		String ipAddr = req.getRemoteAddr();
		
		System.out.println("IP주소 : " + ipAddr + "\n포트번호 : " + req.getRemotePort() + "\n현재 시간 : " + new Date());
		
		// 필터체인 실행(req, resp 객체 전달)
		fc.doFilter(req, resp);
		// filter가 몇개로 구성되어 있는지 알 수 없다.
		// filterChain은 다음 필터를 가리키고 doFilter()는 다음 filter를 호출한다.
		// 다음 filter가 없다면 내부적으로 서블릿의 service()를 호출한다.
		
		System.out.println("T08_ServletFilter 완료");
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("T08_ServletFilter : init() 호출됨");
		
		// 초기화 파라미터 정보 가져오기
		String initParam = config.getInitParameter("init-param");
		System.out.println("init-param : " + initParam);
	}
}