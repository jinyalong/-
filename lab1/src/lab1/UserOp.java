package lab1;

import java.sql.ResultSet;

public class UserOp {
	public void creatTable(DB_conn_op sjk) {//������
		String sql = "create table if not exists users"
					+ "( "
					+ "username varchar(10) not null,"
					+ "pass varchar(8) not null,"
					+ "primary key (username)"
					+ ")";
		int t = sjk.executeUpdate(sql);
		if(t == 0)System.out.println("users ���Ѵ��ڣ�");
		else System.out.println("���� users ��ɹ���");
	}
	
	public void addUser(User u,DB_conn_op sjk) throws Exception{//��������
		String sql = "insert into users(username,pass) values(?,?)";
		Object[] obj = {u.getUsername(),u.getPassword()};
		sjk.executeUpdate(sql,obj);
	}
	
	public void deleteUser_Username(String username,DB_conn_op sjk) throws Exception {//ɾ��
		String sql = "delete from users where username like ?";
		sjk.executeUpdate(sql,username);
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
