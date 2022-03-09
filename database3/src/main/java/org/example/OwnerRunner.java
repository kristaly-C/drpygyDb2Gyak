package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OwnerRunner {

    private static final String URL = "jdbc:oracle:thin:@193.6.5.58:1521:XE";

    public static void main( String[] args )
    {
     try {
         Connection conn = connectDb("H22_drpygy","DRPYGY");
         createOwnerTable(conn);
         alterCaarTable(conn);
     }catch (Exception e){
         e.printStackTrace();
     }
    }


    public static Connection connectDb(String username, String password)throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(URL,username,password);
        return conn;
    }

    public static void createOwnerTable(Connection conn) throws SQLException
    {
        PreparedStatement prstmt = conn.prepareStatement(
                "CREATE TABLE owners (id int primary key , name varchar2(40) not null ,birth date);"
        );
        prstmt.executeUpdate();
        prstmt.close();
        conn.close();
    }

    public static void alterCaarTable(Connection conn) throws SQLException
    {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("ALTER TABLE car ADD ownerID int  CONSTRAINT owner_car REFERENCES owner(id) ");
        stmt.close();
        conn.close();
    }

    public static void fillOwerTable(Connection conn, int id, String name, Date date) throws SQLException{
        PreparedStatement prstmt = conn.prepareStatement("INNER INTO owners VALUES(?,?,?)");
        prstmt.setInt(1,id);
        prstmt.setString(2,name);
        prstmt.setDate(3,date);
        prstmt.executeUpdate();
        prstmt.close();
        conn.close();
    }

    public static void updateOwnerId(Connection conn, int id ,String manufacturer) throws SQLException{
        PreparedStatement prstmt = conn.prepareStatement("UPDATE car SET owner_id=? where manufacturer=?");
        prstmt.setInt(1,id);
        prstmt.setString(2, manufacturer);
        prstmt.executeUpdate();
        prstmt.close();
        conn.close();
    }

    public static void getAllData(Connection conn) throws SQLException{
        PreparedStatement prstmt = conn.prepareCall("" +
                "SELECT * FROM car INNER JOIN owners ON (car.owner_id = owners.id)");
        ResultSet rs = prstmt.executeQuery();
        while (rs.next()){
            System.out.println(rs.getInt("name") + rs.getString("manufacturer") + rs.getInt("price"));
        }
        prstmt.close();
        conn.close();
    }

    public static void fillOwerTable2(Connection conn, int id,String birth) throws SQLException{
        PreparedStatement prstmt = conn.prepareStatement("INNER INTO owners VALUES(?,?,{d ?})");
        prstmt.setInt(1,id);
        prstmt.setString(2,"Barnus");
        prstmt.setString(3, birth);
        prstmt.executeUpdate();
        prstmt.close();
        conn.close();
    }

    public static void getCar(Connection conn, String manufact) throws SQLException {
        PreparedStatement prstmt = conn.prepareStatement("" +
                "SELECT * FROM car WHERE manufacturer=?", ResultSet.TYPE_SCROLL_SENSITIVE);
        prstmt.setString(1,manufact);
        ResultSet rs = prstmt.executeQuery();
        List<Cars> carList = new ArrayList<>();
        while (rs.next()){
            rs.updateString("color","hupilila");
            rs.updateRow();
            carList.add(new Cars(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)));
        }
        for (Cars asd: carList)
        {
          System.out.println(asd.toString());
        }
    }

    public static  void callableTest(Connection conn) throws SQLException{
        CallableStatement cstmt = conn.prepareCall(
                "{?=call cos(?)}"
        );
        cstmt.registerOutParameter(1,Types.NUMERIC);
        cstmt.setDouble(2,3.14);
        cstmt.execute();
    }
}
