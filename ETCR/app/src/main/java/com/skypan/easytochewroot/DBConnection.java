package com.skypan.easytochewroot;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class DBConnection {

   /* StringDriver="com.mysql.jdbc.Driver";
    StringUrl="jdbc:mysql://localhost:3306/xml";//xml为数据源
    String Name="Mysql用户名";
    StringPwd="Mysql密码";

    public ConnectiongetConnection() throws SQLException, ClassNotFoundException
    {
        Connectionconn=null;

        try
        {
            Class.forName(Driver);
            conn=DriverManager.getConnection(Url,Name,Pwd);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        returnconn;
    }

    public ResultSet sqlUser()throws SQLException, ClassNotFoundException{
        getConnection();
        Stringsql="select * from testxml";
        ResultSetrs=null;
        PreparedStatementps=null;
        Connectionconn=null;
        conn=DriverManager.getConnection(Url,Name,Pwd);
        ps =conn.prepareStatement(sql);
        rs =ps.executeQuery(sql);
        returnrs;
    }*/
}
