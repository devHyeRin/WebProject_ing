package event;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

	// DB 연결
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:testdb";
	String user = "scott";
	String password = "tiger";

	public Connection dbcon() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			if (con != null)
				System.out.println("ok");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	// 이벤트 전체 조회
	public List<Event> findAllEvents(int currentPage, int pageSize) {
		
		int grpStart = (currentPage - 1) * pageSize + 1;
		int grpEnd = currentPage * pageSize;
		
		Connection con = dbcon();

		String sql =
			    "SELECT * FROM (" +
			    "   SELECT ROWNUM AS num, inner_table.* " +
			    "   FROM (" +
			    "       SELECT e.*, c.category_name " +
			    "       FROM category c " +
			    "       JOIN event e ON c.category_id = e.category_id " +
			    "       ORDER BY e.created_at DESC" +
			    "   ) inner_table" +
			    ") " +
			    "WHERE num BETWEEN ? AND ?";

		List<Event> allList = new ArrayList<Event>();

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = con.prepareStatement(sql);
			
			pst.setInt(1, grpStart);
			pst.setInt(2, grpEnd);
			
			rs = pst.executeQuery();

			while (rs.next()) {
				int eventId = rs.getInt("event_id");
				int authorId = rs.getInt("author_id");
				int categoryId = rs.getInt("category_id");
				String title = rs.getString("title");
				String region = rs.getString("region");
				Date eventDate = rs.getDate("event_date");
				int capacity = rs.getInt("capacity");
				String description = rs.getString("description");
				String status = rs.getString("status");
				Date createdAt = rs.getDate("created_at");
				String uploadImg = rs.getString("upload_img");
				String categoryName = rs.getString("category_name");

				Event event = new Event(eventId, authorId, categoryId, categoryName, title, region, eventDate, capacity,
						description, status, createdAt, uploadImg);

				allList.add(event);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return allList;

	}

	// 카테고리별 이벤트 조회
	public List<Event> findEventsByCategory(int category_id) {
		Connection con = dbcon();

		String sql = "select e.*, c.category_name " + "from category c "
				+ "join event e on c.category_id = e.category_id " + " where e.category_id = ?";

		List<Event> categoryList = new ArrayList<Event>();

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, category_id);

			rs = pst.executeQuery();

			while (rs.next()) {
				int eventId = rs.getInt("event_id");
				int authorId = rs.getInt("author_id");
				int categoryId = rs.getInt("category_id");
				String title = rs.getString("title");
				String region = rs.getString("region");
				Date eventDate = rs.getDate("event_date");
				int capacity = rs.getInt("capacity");
				String description = rs.getString("description");
				String status = rs.getString("status");
				Date createdAt = rs.getDate("created_at");
				String uploadImg = rs.getString("upload_img");
				String categoryName = rs.getString("category_name");

				Event event = new Event(eventId, authorId, categoryId, categoryName, title, region, eventDate, capacity,
						description, status, createdAt, uploadImg);

				categoryList.add(event);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return categoryList;

	}
}
