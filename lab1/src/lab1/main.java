package lab1;


public class main {

	public static void main(String[] argc)throws Exception{
		DB_conn_op sjk = new DB_conn_op();
		UserOp uo = new UserOp();
		PersonOp po = new PersonOp();
		
		System.out.println("��һ����");
		uo.creatTable(sjk);
		po.creatTable(sjk);
		uo.Print_Table(sjk);
		po.Print_Table(sjk);
		System.out.println("�ڶ�����");
		
		uo.addUser(new User("ly", "123456"),sjk);
		uo.addUser(new User("liming", "345678"), sjk);
		uo.addUser(new User("test", "11111"), sjk);
		uo.addUser(new User("test1", "12345"), sjk);
		
		po.addPerson(new Person("ly","����"), sjk);
		po.addPerson(new Person("liming","����",25), sjk);
		po.addPerson(new Person("test","�����û�",20,"13388449933"), sjk);
		uo.Print_Table(sjk);
		po.Print_Table(sjk);
		
		System.out.println("��������");
		po.addPerson(new Person("ly","����"), sjk);
		po.addPerson(new Person("test2","�����û�2"), sjk);
		po.addPerson(new Person("test1","�����û�1",33), sjk);
		po.addPerson(new Person("test","����",23,"18877009966"), sjk);
		po.addPerson(new Person("admin","admin"), sjk);
		uo.Print_Table(sjk);
		po.Print_Table(sjk);
		
		System.out.println("���Ĳ���");
		po.deletePerson("test%", sjk);
		uo.deleteUser_Username("test%", sjk);
		uo.Print_Table(sjk);
		po.Print_Table(sjk);
		sjk.close();
	}
}
