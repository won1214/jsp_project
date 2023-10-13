package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login.me")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	// 1) 전달값에 한글이 있을 경우 인코딩 처리해야된다.(post일 경우)	
		request.setCharacterEncoding("UTF-8");
		
	// 2) 요청시 전달값 추출해서 변수 또는 객체에 기록하기
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
	// 3) 요청하기 (db에 sql문 활용해서 조회해줘)
	// 	  해당 요청을 처리하는 서비스 클래스의 메소드 호출
		
		Member loginUser = new MemberService().loginMember(userId, userPwd );
		
		System.out.println(loginUser);
		
		/**
		 * 
		 * 응답페이지에 전달할 값이 있을 경우 어딘가에 담아야한다.(담을 수 있는 영역 == JSP 내장객체 4종류)
		 * 1) application : 여기에 담긴 데이터는 웹 애플리케이션 전역에서 다 꺼내서 쓸 수 있음
		 * 2) session : 여기에 담긴 데이터는 직접 지우기 전까지, 세션이 만료(서버가 멈추거나, 브라우저가 종료)되기 전까지 
		 * 				어떤 jsp든, 어떤 servlet이든 꺼내서 사용할 수 있음.
		 * 3) request : 해당 영역에 담긴 데이터는 현재 이 request객체를 "포워딩한 응답 jsp에서만" 꺼내쓸 수 있음
		 * 4) page : 해당 jsp담고 해당 jsp에서만 사용할 수 있음.
		 * 
		 * 공통적으로 데이터를 담고자 한다면 특정객체.setAttribute("키", 벨류)
		 * 		   데이터를 꺼내고자 한다면 특정객체.getAttribute("키") : Object 타입 반환
		 * 		   데이터를 지우고자 한다면 특정객체.removeAttribute("키")
		 * 
		 */
		
		// 4) 처리된 결과를 가지고 사용자가 보게될 응답뷰를 지정해서 포워딩 또는 url 재요청
		if(loginUser == null) {
			//조회결과가 없음 => 로그인 실패 =>에러문구가 보여지는 에러페이지 응답
			request.setAttribute("errorMsg", "로그인에 실패하였습니다.");
			
			//응답페이지(jsp)에게 위임시 필요한 객체(RequestDispatcher)
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			//응답객체, 요청객체 포워딩
			view.forward(request, response);
			
		} else {
			// 조회 결과 있음 => 로그인 성공 !!
			
			// 로그인한 회원정보(loginUser)를 session에 담아버리기 (왜? 여기저기서 다 갖다쓰도록)
			// Servlet에서는 session에 접근하고자한다면 우선 세션 객체를 얻어와야한다.
			HttpSession session =  request.getSession();
			session.setAttribute("loginUser", loginUser);
			
			//포워딩
			//응답페이지(jsp)에게 위임시 필요한 객체(RequestDispatcher)
			RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			//응답객체, 요청객체 포워딩
			view.forward(request, response);
			
		}
		
		
		
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
