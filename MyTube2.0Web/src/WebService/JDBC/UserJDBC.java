package WebService.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import WebService.BO.UserBO;

public class UserJDBC extends PostgreSQLJDBC{

	public List<UserBO> getAll() {
		return selectQuery();
	}
	
	public int insert(UserBO user){
    	openConnection();
        Statement stmt;
        try {
            stmt = c.createStatement();
            String sql = "INSERT INTO mytube_user (username, password) "
                    + "VALUES ('"+user.getUsername()+"', '"+user.getPassword()+"');";
            stmt.executeUpdate(sql);
            			
            closeConnection();
        } catch (SQLException e) {
            System.err.println("problem executing the query");
            closeConnection();
        }
        return getNewstUseRID();
    }
    
    public UserBO getById(int id){
    	return selectQuery("id="+String.valueOf(id)).get(0);
    }
    
    private int getNewstUseRID(){
    	int id = -1;
    	openConnection();
        Statement stmt;
        try {
        	stmt = c.createStatement();
        	String sql = "SELECT timestamp,user_id from mytube_user order by timestamp desc limit 1";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
            	id = rs.getInt("user_id");
            }
            closeConnection();
            

        } catch (SQLException e) {
            System.err.println("problem executing the query");
            closeConnection();
        }
        return id;
    }
    
    private List<UserBO> selectQuery(){
    	return selectQuery("1=1");
    }
    
    private List<UserBO> selectQuery(String whereConditions) {
		openConnection();
        Statement stmt;
        List<UserBO> users = new ArrayList<>();
        try {
        	stmt = c.createStatement();
            String sql = "SELECT * FROM mytube_user WHERE "+whereConditions+";";
            ResultSet rs =stmt.executeQuery(sql);
            
            while (rs.next()) {
            	UserBO userBO = new UserBO();
                userBO.setId(Integer.parseInt(rs.getString("id")));
                userBO.setUsername(rs.getString("username"));
                userBO.setPassword(rs.getString("password"));
                users.add(userBO);
            }
            closeConnection();
            

        } catch (SQLException e) {
            System.err.println("problem executing the query");
            closeConnection();
        }
        return users;
	}

	
}