package event;

import java.util.List;

public class EventService {

	EventDAO dao = new EventDAO();
	
	//이벤트 전체 조회
	public List<Event> findAllEvents(int currentPage, int pageSize){
		return dao.findAllEvents(currentPage, pageSize);
	}
	
	//카테고리별 이벤트 조회
	public List<Event> findEventsByCategory(int categoryId){
		return dao.findEventsByCategory(categoryId);
	}
}
