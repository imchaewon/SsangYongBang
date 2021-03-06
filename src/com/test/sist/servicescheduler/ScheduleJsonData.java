package com.test.sist.servicescheduler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sun.java2d.opengl.WGLSurfaceData.WGLVSyncOffScreenSurfaceData;

/**
 * 이 클래스는 /WEB-INF/views/servicescheduler/servicescheduler2.jsp 의 ajax 정보를 JSON 형식으로 전달하는 역할을 한다.
 * <br></br>
 * 풀캘린더 내 events 객체의 속성에 맞춰서 json형식의 data를 events 객채에 전달할 수 있다.
 * @see <a href="https://fullcalendar.io/docs/events-json-feed">풀캘린더 json 형식 data 파싱 Docs</a>
 * @author 김소리
 * */

@WebServlet("/servicescheduler/schedulejsondata.do")
public class ScheduleJsonData extends HttpServlet {

	
	@Override
	protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();

		//조인해서 가져온 테이블의 select문에서 빼온 정보 (다중값)를 json형식으로 써준다.
		//버튼을 누를 때마다 여기서 json 정보를 쓴다.

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		
		PrintWriter writer = resp.getWriter();
		
		//이 밑에 테이블에서 가져올 정보를 얻어오기 위한 매개변수 목록을 쓴다.
		String approvalFSeq = (String) session.getAttribute("approvalFSeq");
		//업체 로그인시 담은 세션의 업체승인번호
		
		//여기 null값인지 확인해보기
		System.out.println(approvalFSeq);
		System.out.println("위 숫자 확인-> 업체승인번호가 null인지 확인하기");
		
		//DAO와 list를 호출해서 뿌릴 정보의 list를 만든다.
		SchedulerDAO dao = new SchedulerDAO();
		ArrayList<SchedulerDTO> list = dao.listPlan(approvalFSeq);
		
		System.out.println(list.size());
		
		System.out.println("json형식으로 만드는 내용물확인해보기");
		System.out.println("풀캘린더 공식문서에서 보여주는 events 속성이랑 일치하는지, 오타 없는지 점검필요");

		
		String temp = "";
		
		//가져온 정보를 향상된for문을 통해 json 형식 문자열로 만들기
	
		temp += "[";
		
			for(SchedulerDTO dto : list) {
				
				//String servicedate = dto.getServiceDate();
				//일자는 시간을 잘라서 yyyy-mm-dd형태일 때만 event객체가 인식한다.
				
				temp += "{";
				temp += String.format("\"title\": \"%s님 %s\",", dto.getMemberName(), dto.getProgress()); //title: 김00님 완료
				temp += String.format("\"start\": \"%s\",", dto.getServiceDate()); //start: 오라클 내 저장된 시간
				temp += String.format("\"url\": \"/sybang/servicescheduler/view.do?EstimateURL=%s\"", dto.getEstimateURL());
				temp += "}";
				temp += ",";
			}
		
			
		temp = temp.substring(0, temp.length() - 1); //마지막에는 더 따라올 {내용물} 가 없기 때문에 ,출력을 자른다.
		temp += "]";
		System.out.println(temp);
		writer.print(temp);
		
		
		//다 출력 후, 해제하기
		writer.close();
		
	}

}

