package lab2;

import java.sql.*;
import org.apache.log4j.*;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class DB_conn_op {
	Logger logger = LogManager.getLogger(DB_conn_op.class);
	private String JDBC_DRIVER = PropertiesUtil.getValue("JDBC_DRIVER");
    private String DB_URL = PropertiesUtil.getValue("DB_URL");
    private String USER = PropertiesUtil.getValue("USER");
   	private String PASS = PropertiesUtil.getValue("PASS");
   	private Connection con = null;
    private PreparedStatement pstmt = null;
    public DB_conn_op() throws Exception {
    	con = getConnection();
    }
    public Connection getConnection() throws Exception{
    	@SuppressWarnings("resource")
		BasicDataSource ds = new BasicDataSource();
    	ds.setUsername(USER);
        ds.setPassword(PASS);
        ds.setUrl(DB_URL);
        ds.setDriverClassName(JDBC_DRIVER);
        //设置最大连接数
        ds.setMaxIdle(20);
        //设置最大空闲连接数
        //设置初始化连接数
        ds.setInitialSize(10);
        //设置最小空闲连接数
        ds.setMinIdle(2);
        //设置最大等待时间，单位为毫秒
        ds.setMaxWaitMillis(1000);
        Connection con = ds.getConnection();
    	return con;
    }
    
    public int executeUpdate(String sql,Object... params) {//插入返回操作的记录数
    	int rlt = 0;
        try{
            
            pstmt = con.prepareStatement(sql);
            set_Params(pstmt,params);
            rlt = pstmt.executeUpdate();
            pstmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rlt;
    }
    //设置参数
    private void set_Params(PreparedStatement pstmt, Object[] params) throws Exception{
        if(params!=null){
            for(int i=0;i<params.length;i++){
                if(params[i] instanceof String) pstmt.setString(i+1,(String)params[i]);
                else if(params[i] instanceof Integer) pstmt.setInt(i+1,(Integer)params[i]);
                else if(params[i] == null) pstmt.setNull(i+1, Types.INTEGER);
            }
        }
    }
    
    public ResultSet query(String sql,Object... params) throws Exception{//返回是否找到
    
    	pstmt = con.prepareStatement(sql);
        set_Params(pstmt,params);
        ResultSet rs = pstmt.executeQuery();
        return rs;	
    }

    public void close() throws Exception{
    	pstmt.close();
    	if(this.con!=null) {
    		this.con.close();
    		System.out.println("数据库已断开链接！");
    		
    	}
    }
}
