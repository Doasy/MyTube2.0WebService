package WebService.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import WebService.BO.ContentBO;

public class ContentJDBC extends PostgreSQLJDBC{
	
	public int insert(){
		return 1;
	}
	
	public List<ContentBO> getAll() {
		return selectQuery();
		
	}
	
	public ContentBO getByID(int id) {
		return selectQuery("id="+String.valueOf(id)).get(0);
	}
	
	private List<ContentBO> selectQuery() {
		return selectQuery("1=1");
	}
	
	public int insert(ContentBO contentBO) {
		openConnection();
        Statement stmt;
        try {
            stmt = c.createStatement();
            String sql = "INSERT INTO mytube_content (title, description, user_id, server_id) "
                    + "VALUES ('"+contentBO.getTitle()+"', '"+contentBO.getDescription()+"', '"+contentBO.getUploader()+"', '"+contentBO.getServerId()+"');";
            stmt.executeUpdate(sql);
            			
            closeConnection();
        } catch (SQLException e) {
            System.err.println("problem executing the query");
            closeConnection();
        }
        return getNewstContentID();
	}


	private List<ContentBO> selectQuery(String whereConditions) {
		openConnection();
        Statement stmt;
        List<ContentBO> contents = new ArrayList<>();
        try {
            stmt = c.createStatement();
            String sql = "SELECT * FROM mytube_content WHERE "+whereConditions+";";
            ResultSet rs =stmt.executeQuery(sql);
            while (rs.next()) {
            	ContentBO contentBO = new ContentBO();
                contentBO.setId(Integer.parseInt(rs.getString("id")));
                contentBO.setTitle(rs.getString("title"));
                contentBO.setDescription(rs.getString("description"));
                contentBO.setServerId(rs.getInt("server_id"));
                contentBO.setUploader(rs.getInt("user_id"));
                contents.add(contentBO);
            }
            closeConnection();

        } catch (SQLException e) {
            System.err.println("problem executing the query");
            closeConnection();
        }
        return contents;
	}

	private int getNewstContentID(){
    	int id = -1;
    	openConnection();
        Statement stmt;
        try {
        	stmt = c.createStatement();
        	String sql = "SELECT timestamp,id FROM mytube_content order by timestamp desc limit 1";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
            	id = rs.getInt("id");
            }
            closeConnection();
            

        } catch (SQLException e) {
            System.err.println("problem executing the query");
            closeConnection();
        }
        return id;
    }

	
}