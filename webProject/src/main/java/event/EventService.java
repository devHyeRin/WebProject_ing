package event;

import java.util.List;

public class EventService {

	EventDAO dao = new EventDAO();
	
	//이벤트 전체 조회
	public List<Event> getEventList(int currentPage, int pageSize){
		return dao.findAllEvents(currentPage, pageSize);
	}
	
	//카테고리별 이벤트 조회
	public List<Event> getEventListByCategory(int categoryId){
		return dao.findEventsByCategory(categoryId);
	}
	
	
	//총 레코드 수 가져오기
		public int getTotalCount() {
			return dao.countAll();
			
		}
}
