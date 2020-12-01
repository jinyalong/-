package lab1;

import java.sql.ResultSet;

public class UserOp {
	public void creatTable(DB_conn_op sjk) {//创建表
		String sql = "create table if not exists users"
					+ "( "
					+ "username varchar(10) not null,"
					+ "pass varchar(8) not null,"
					+ "primary key (username)"
					+ ")";
		int t = sjk.executeUpdate(sql);
		if(t == 0)System.out.println("users 表已存在！");
		else System.out.println("创建 users 表成功！");
	}
	
	public void addUser(User u,DB_conn_op sjk) throws Exception{//增加数据
		String sql = "insert into users(username,pass) values(?,?)";
		Object[] obj = {u.getUsername(),u.getPassword()};
		sjk.executeUpdate(sql,obj);
	}
	
	public void deleteUser_Username(String username,DB_conn_op sjk) throws Exception {//删除
		String sql = "delete from users where username like ?";
		sjk.executeUpdate(sql,username);
	}
	
	public boolean findUser(String username,DB_conn_op sjk) throws Exception {//查找
		String sql = "select * from users where username = ?";
		return sjk.query(sql,username).next();
	}
	
	public void updateUser(User u,DB_conn_op sjk) throws Exception{
		String sql = "update person set pass = ? where username = ?";
		Object[] obj = {u.getPassword(),u.getUsername()};
		sjk.executeUpdate(sql,obj);
	}
	public void Print_Table(DB_conn_op sjk) throws Exception{
		System.out.println("表users");
		System.out.println("字段名username\t字段名pass");
		System.out.println("-------------------------");
		String sql = "select * from users";
		ResultSet rs = sjk.query(sql);
		while(rs.next()) {
			String username = rs.getString("username");
		    String password = rs.getString("pass");//主键
		    System.out.println(username + "\t\t" + password);
		}
	}
	
	public void dropTable(DB_conn_op sjk) throws Exception{
		String sql = "DROP TABLE users";
		sjk.executeUpdate(sql);
	}
}
