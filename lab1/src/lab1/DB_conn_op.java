package lab1;

import java.io.*;

import java.sql.*;
import java.text.SimpleDateFormat;



public class DB_conn_op {
	private String JDBC_DRIVER = PropertiesUtil.getValue("JDBC_DRIVER");
    private String DB_URL = PropertiesUtil.getValue("DB_URL");
    private String USER = PropertiesUtil.getValue("USER");
   	private String PASS = PropertiesUtil.getValue("PASS");
    private Connection con = null;
    private File file = new File("../sql_log.txt");
    private PreparedStatement pstmt = null;
	OutputStream out = new FileOutputStream(file);
    public DB_conn_op() throws Exception {
    	con = getConnection();
    }
    public Connection getConnection() {
    	Connection conn = null;
    	System.out.println("驱动加载中...");
    	try {
    		Class.forName(JDBC_DRIVER);
        	System.out.println("驱动加载成功！");
        	System.out.println("数据库链接中...");
        	conn = (Connection)DriverManager.getConnection(DB_URL,USER,PASS);
        	System.out.println("数据库链接成功！");
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	return conn;
    }
    
    public int executeUpdate(String sql,Object... params) {//插入返回操作的记录数
    	int rlt = 0;
        try{
            
            pstmt = con.prepareStatement(sql);
            set_Params(pstmt,params);
            rlt = pstmt.executeUpdate();
            pstmt.close();
            sql+="\n";
            java.util.Date date=new java.util.Date();
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
        	String sj = sdf.format(date);
        	out.write(sj.getBytes());
        	out.write(sql.getBytes());
        }catch (Exception e){
        	System.out.println("非法操作！");
            //e.printStackTrace();
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
        sql+="\n";
        java.util.Date date=new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
        String sj = sdf.format(date);
        out.write(sj.getBytes());
        out.write(sql.getBytes());
        return rs;	
    }
    
    public void close() throws Exception{
    	out.close();//关闭文件流
    	pstmt.close();
    	if(this.con!=null) {
    		this.con.close();
    		System.out.println("数据库已断开链接！");
    		
    	}
    }
}
