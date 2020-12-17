package lab2;
import java.sql.*;
import java.util.*;
public class DB_conn_op {
    private String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private String DB_URL = PropertiesUtil.getValue("DB_URL");
    private String USER = PropertiesUtil.getValue("USER");
    private String PASS = PropertiesUtil.getValue("PASS");
    public Connection con = null;
    private PreparedStatement pstmt = null;
    public DB_conn_op() throws Exception {
        con = getConnection();
    }
    public Connection getConnection() throws Exception{

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
