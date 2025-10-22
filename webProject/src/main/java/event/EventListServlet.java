package event;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/letsgu/event/list")
public class EventListServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int currentPage = 1;		
		int pageSize = 5;
		int grpSize = 5;
		
		if(req.getParameter("p") != null) {
			currentPage = Integer.parseInt(req.getParameter("p"));
		}
		
		String categoryParam = req.getParameter("category");
		
		List<Event> eventList = null;
		List<Category> categoryList = null;
		
		EventService e_service = new EventService();
		CategoryService c_service = new CategoryService();
		
		//페이징 처리
		int totalRecords = e_service.getTotalCount();
		System.out.println("전체 이벤트 데이터 수 : " + totalRecords);
		PageHandler page = new PageHandler(pageSize, grpSize, totalRecords, currentPage);
		
		//전체 or 카테고리 선택
		if(categoryParam != null && !categoryParam.isEmpty()) {
			int categoryId = Integer.parseInt(categoryParam);
			eventList = e_service.getEventListByCategory(categoryId);
		}else {
			eventList = e_service.getEventList(currentPage, pageSize);
		}
		
		 categoryList = c_service.getCategoryList();
		
		
		req.setAttribute("categoryList", categoryList);
		req.setAttribute("eventList", eventList);
		req.setAttribute("page", page);
		
		
		req.getRequestDispatcher("/WEB-INF/views/event/eventList.jsp").forward(req, resp);
		
	}
}
