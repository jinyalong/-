package lab1;


public class main {

	public static void main(String[] argc)throws Exception{
		DB_conn_op sjk = new DB_conn_op();
		UserOp uo = new UserOp();
		PersonOp po = new PersonOp();
		
		System.out.println("第一步：");
		uo.creatTable(sjk);
		po.creatTable(sjk);
		uo.Print_Table(sjk);
		po.Print_Table(sjk);
		System.out.println("第二步：");
		
		uo.addUser(new User("ly", "123456"),sjk);
		uo.addUser(new User("liming", "345678"), sjk);
		uo.addUser(new User("test", "11111"), sjk);
		uo.addUser(new User("test1", "12345"), sjk);
		
		po.addPerson(new Person("ly","雷力"), sjk);
		po.addPerson(new Person("liming","李明",25), sjk);
		po.addPerson(new Person("test","测试用户",20,"13388449933"), sjk);
		uo.Print_Table(sjk);
		po.Print_Table(sjk);
		
		System.out.println("第三步：");
		po.addPerson(new Person("ly","王五"), sjk);
		po.addPerson(new Person("test2","测试用户2"), sjk);
		po.addPerson(new Person("test1","测试用户1",33), sjk);
		po.addPerson(new Person("test","张三",23,"18877009966"), sjk);
		po.addPerson(new Person("admin","admin"), sjk);
		uo.Print_Table(sjk);
		po.Print_Table(sjk);
		
		System.out.println("第四步：");
		po.deletePerson("test%", sjk);
		uo.deleteUser_Username("test%", sjk);
		uo.Print_Table(sjk);
		po.Print_Table(sjk);
		sjk.close();
	}
}
