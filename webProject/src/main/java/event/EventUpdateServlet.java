package event;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/letsgu/event/update")
public class EventUpdateServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int eventId = Integer.parseInt(req.getParameter("eventId"));
		
		EventService e_service = new EventService();
		CategoryService c_service = new CategoryService(); 
		
		Event event = e_service.getEventById(eventId);
		List<Category> categoryList = c_service.getCategoryList();
		List<String> regionList = e_service.getRegionList();
		
		req.setAttribute("event", event);
		req.setAttribute("categoryList", categoryList);
		req.setAttribute("regionList", regionList);
		
		req.getRequestDispatcher("/WEB-INF/views/event/eventUpdate.jsp").forward(req, resp);
	}

}
