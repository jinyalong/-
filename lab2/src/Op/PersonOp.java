package Op;

import java.sql.*;
import lab2.*;
import bean.*;

public class PersonOp {
	public void creatTable(DB_conn_op sjk) throws Exception{//������
		String sql = "create table if not exists person"
	           	+ "("
	           	+ "username varchar(10) not null,"
	           	+ "name varchar(20) not null,"
	           	+ "age int default 18,"
	           	+ "teleno char(11) default '18570253175',"
	           	+ "primary key (name)"
	           	+ ")";
		sjk.executeUpdate(sql);
	}
	
	public int addPerson(Person p,DB_conn_op sjk) throws Exception{//���Ӷ���
		UserOp uo = new UserOp();
		if(findPerson(p,sjk)==true) {//person���д���
			updatePerson(p,sjk);
			return 1;
		}
		else {
			//person���в�����,�Ȳ���user��
			if(!uo.findUser(p.getUsername(), sjk))
				uo.addUser(new User(p.getUsername(), "888888"),sjk);
			//����person����
			String sql = "insert into person(username,name,age,teleno) values(?,?,?,?)";
			Object[] obj = {p.getUsername(),p.getName(),p.getAge(),p.getTeleno()};
			sjk.executeUpdate(sql,obj);
			return 2;
		}	
	}
	
	public void deletePerson(String username,DB_conn_op sjk) throws Exception{//ɾ������
		String sql = "delete from person where username like ? ";
		sjk.executeUpdate(sql,username);
	}
	
	public boolean findPerson(Person p,DB_conn_op sjk) throws Exception{//��ѯ
		String sql4 = "select * from person where name = ?";
		ResultSet rs = sjk.query(sql4,p.getName());
		return rs.next();
	}
	
	
	public void updatePerson(Person p,DB_conn_op sjk) throws Exception{//����
		String sql = "update person set name = ?,age = ?,teleno = ? where username = ?";
		Object[] obj = {p.getName(),p.getAge(),p.getTeleno(),p.getUsername()};
		sjk.executeUpdate(sql,obj);
	}

	public void Print_Table(DB_conn_op sjk) throws Exception{
		System.out.println("��person");
		System.out.println("�ֶ���username\t�ֶ���name\t�ֶ���age\t\t�ֶ���teleno");
		System.out.println("-----------------------------------------------------------");
		String sql = "select * from person";
		ResultSet rs = sjk.query(sql);
		while(rs.next()) {
			String username = rs.getString("username");
		    String name = rs.getString("name");//����
		    Integer age = rs.getInt("age");
		    if(age==0) age = null;
		    String teleno = rs.getString("teleno");
		    if(teleno.length()==0) teleno = null;
		    System.out.println(username + "\t\t" + name + "\t\t" + age +"\t\t"+teleno);
		}
		rs.close();
	}
		
	public void dropTable(DB_conn_op sjk) throws Exception{
		String sql = "DROP TABLE person";
		sjk.executeUpdate(sql);
	}
}
