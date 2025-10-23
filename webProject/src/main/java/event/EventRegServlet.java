package event;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet("/letsgu/event/reg")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class EventRegServlet extends HttpServlet {

	private static final String UPLOAD_DIR = "upload";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 로그인 기능 구현 완료 후
//		HttpSession session = req.getSession();
//		Object userObj = session.getAttribute("loginUser");
//		
//		if(userObj == null) {
//			resp.sendRedirect(req.getContextPath() + "/letsgu/login");
//		}
		
		//테스트
		HttpSession session = req.getSession();
	    if (session.getAttribute("loginUser") == null) {
	        Users dummyUser = new Users();
	        dummyUser.setUser_id(1);
	        dummyUser.setUsername("테스트유저1");
	        session.setAttribute("loginUser", dummyUser);
	    }
	    
	    CategoryService c_service = new CategoryService();
        List<Category> categoryList = c_service.getCategoryList();
        req.setAttribute("categoryList", categoryList);

        List<String> regionList = Arrays.asList("강남구", "마포구", "서초구", "종로구", "용산구", "은평구");
        req.setAttribute("regionList", regionList);

		req.getRequestDispatcher("/WEB-INF/views/event/eventReg.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");

		HttpSession session = req.getSession();
		Object userObj = session.getAttribute("loginUser");

		if (userObj == null) {
			
			//임시 로그인 사용자 세션 설정( 기능 완료 후 삭제 부분)
			Users dummyUser = new Users();
	        dummyUser.setUser_id(1); 
	        dummyUser.setUsername("테스트유저1");
	        session.setAttribute("loginUser", dummyUser);
			
	        System.out.println("임시 로그인 세션 생성됨: user_id=1, user_name=테스트유저1");
//			resp.sendRedirect(req.getContextPath() + "/letsgu/login");
		}

		// 로그인한 경우
		Users loginUser = (Users) userObj;
		int authorId = loginUser.getUser_id();

		// 업로드 폴더 설정 (webapp/upload)
		String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		// 파일 처리
		Part filePart = req.getPart("uploadImg"); // form의 name 속성
		String fileName = extractFileName(filePart);
		if (fileName != null && !fileName.isEmpty()) {
			File file = new File(uploadPath, fileName);
			Files.copy(filePart.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}

		int categoryId = Integer.parseInt(req.getParameter("category_id"));
		String title = req.getParameter("title");
		String region = req.getParameter("region");
		String eventDateStr = req.getParameter("event_date");
		int capacity = Integer.parseInt(req.getParameter("capacity"));
		String description = req.getParameter("description");
		String uploadImg = fileName;

		Date eventDate = null;
		if (eventDateStr != null && !eventDateStr.isEmpty()) {
			eventDate = Date.valueOf(eventDateStr);
		}
		
		Event event = new Event(authorId, categoryId, title, region, eventDate, capacity, description, uploadImg);
		
		CategoryService c_service = new CategoryService();
		EventService e_service = new EventService();
		
		List<Category> categoryList = c_service.getCategoryList();
		//임시로 가져오기
		List<String> regionList = Arrays.asList("강남구", "마포구", "서초구", "종로구", "용산구", "은평구");

		
		req.setAttribute("categoryList", categoryList);
		req.setAttribute("regionList", regionList);
		
		boolean result = e_service.regEvent(event);
		

		if (result) {
			resp.sendRedirect(req.getContextPath() + "/letsgu/event/list");
		} else {
			req.getRequestDispatcher("/WEB-INF/views/event/eventReg.jsp").forward(req, resp);
		}
	}

	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		for (String token : contentDisp.split(";")) {
			if (token.trim().startsWith("filename")) {
				return token.substring(token.indexOf('=') + 2, token.length() - 1);
			}
		}
		return null;
	}
}
