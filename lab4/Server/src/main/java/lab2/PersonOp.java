package lab2;

import java.sql.*;

public class PersonOp {
    public void creatTable(DB_conn_op sjk) throws Exception{//创建表
        String sql = "create table if not exists person"
                + "("
                + "username varchar(10) not null,"
                + "name varchar(20) not null,"
                + "age int default 18,"
                + "teleno char(11) default '18570253175',"
                + "primary key (username)"
                + ")";
        sjk.executeUpdate(sql);
    }

    public int addPerson(Person p,DB_conn_op sjk) throws Exception{//增加对象
        UserOp uo = new UserOp();
        if(findPerson(p,sjk)==true) {//person表中存在
            updatePerson(p,sjk);
            return 1;
        }
        else {
            //person表中不存在,先插入user表
            if(!uo.findUser(p.getUsername(), sjk))
                uo.addUser(new User(p.getUsername(), "888888"),sjk);
            //插入person表中
            String sql = "insert into person(username,name,age,teleno) values(?,?,?,?)";
            Object[] obj = {p.getUsername(),p.getName(),p.getAge(),p.getTeleno()};
            sjk.executeUpdate(sql,obj);
            return 2;
        }
    }

    public void deletePerson(String username,DB_conn_op sjk) throws Exception{//删除对象
        String sql = "delete from person where username like ? ";
        sjk.executeUpdate(sql,username);
    }

    public boolean findPerson2(String username,String teleno,DB_conn_op sjk) throws Exception{//查询
        String sql4 = "select * from person where username = '"+username+"' and teleno = '"+teleno+"'";
        System.out.println(sql4);
        ResultSet rs = sjk.query(sql4);
        return rs.next();
    }

    public Person findPerson1(String username,DB_conn_op sjk) throws Exception{//查询
        String sql4 = "select * from person where username = ?";
        ResultSet rs = sjk.query(sql4,username);
        String name = null;
        Integer age = null;
        String teleno = null;
        while(rs.next()) {
            name = rs.getString("name");
            age = rs.getInt("age");
            if (age == 0) age = null;
            teleno = rs.getString("teleno");
            if (teleno.length() == 0) teleno = null;

        }
        return new Person(username,name,age,teleno);
    }

    public boolean findPerson(Person p,DB_conn_op sjk) throws Exception{//查询
        String sql4 = "select * from person where username = ?";
        ResultSet rs = sjk.query(sql4,p.getUsername());
        return rs.next();
    }

    public int updatePerson(Person p,DB_conn_op sjk) throws Exception{//更新
        String sql = "update person set name = ?,age = ?,teleno = ? where username = ?";
        Object[] obj = {p.getName(),p.getAge(),p.getTeleno(),p.getUsername()};
        return sjk.executeUpdate(sql,obj);
    }

    public void Print_Table(DB_conn_op sjk) throws Exception{
        System.out.println("表person");
        System.out.println("字段名username\t字段名name\t字段名age\t\t字段名teleno");
        System.out.println("-----------------------------------------------------------");
        String sql = "select * from person";
        ResultSet rs = sjk.query(sql);
        while(rs.next()) {
            String username = rs.getString("username");
            String name = rs.getString("name");//主键
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
