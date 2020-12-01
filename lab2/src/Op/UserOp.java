package Op;

import java.sql.ResultSet;
import lab2.*;
import bean.*;

public class UserOp {
	public void creatTable(DB_conn_op sjk) {//������
		String sql = "create table if not exists users"
					+ "( "
					+ "username varchar(10) not null,"
					+ "pass varchar(8) not null,"
					+ "primary key (username)"
					+ ")";
		sjk.executeUpdate(sql);
	}
	
	public void addUser(User u,DB_conn_op sjk) throws Exception{//��������
		String sql = "insert into users(username,pass) values(?,?)";
		Object[] obj = {u.getUsername(),u.getPassword()};
		sjk.executeUpdate(sql,obj);
	}
	
	public int deleteUser_Username(String username,DB_conn_op sjk) throws Exception {//ɾ��
		String sql1 = "delete from person where username like ?";
		sjk.executeUpdate(sql1, username);
		String sql = "delete from users where username like ?";
		return sjk.executeUpdate(sql,username);
	}
	
	public boolean findUser(String username,DB_conn_op sjk) throws Exception {//����
		String sql = "select * from users where username = ?";
		return sjk.query(sql,username).next();
	}
	
	public void updateUser(User u,DB_conn_op sjk) throws Exception{
		String sql = "update person set pass = ? where username = ?";
		Object[] obj = {u.getPassword(),u.getUsername()};
		sjk.executeUpdate(sql,obj);
	}
	public void Print_Table(DB_conn_op sjk) throws Exception{
		System.out.println("��users");
		System.out.println("�ֶ���username\t�ֶ���pass");
		System.out.println("-------------------------");
		String sql = "select * from users";
		ResultSet rs = sjk.query(sql);
		while(rs.next()) {
			String username = rs.getString("username");
		    String password = rs.getString("pass");//����
		    System.out.println(username + "\t\t" + password);
		}
	}
	
	public void dropTable(DB_conn_op sjk) throws Exception{
		String sql = "DROP TABLE users";
		sjk.executeUpdate(sql);
	}
}
