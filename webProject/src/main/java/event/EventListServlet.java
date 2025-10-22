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
		
		if(req.getParameter("p") != null) {
			currentPage = Integer.parseInt(req.getParameter("p"));
		}
		
		int pageSize = 5;
		int grpSize = 5;
		
		String categoryParam = req.getParameter("category");
		
		List<Event> eventList = null;
		
		EventService service = new EventService();
		
		//전체 or 카테고리 선택
		if(categoryParam != null && !categoryParam.isEmpty()) {
			int categoryId = Integer.parseInt(categoryParam);
			eventList = service.getEventListByCategory(categoryId);
		}else {
			eventList = service.getEventList(currentPage, pageSize);
		}
		
		req.setAttribute("eventList", eventList);
		req.getRequestDispatcher("/WEB-INF/views/event/eventList.jsp").forward(req, resp);
		
	}
}
