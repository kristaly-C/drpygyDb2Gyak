package org.example;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final String URL = "jdbc:oracle:thin:@193.6.5.58:1521:XE";

    public static void main( String[] args )
    {
        System.out.println( "Database handling in java" );
        try {
            Connection conn = connectDb("H22_drpygy","DRPYGY");
            //createTable(conn);
            //insertCar(conn);
            //setPriceOfCarByColor(conn,"piros",9000);
            //setPriceOfCarByColorPrep(conn,"piros",1500);
            String[] sqlString =  {"insert into car values(10,'Opel','fehér',300)",
                    "insert into car values(11,'Seat','zöld',700)",
                    "insert into car values(12,'Opel','fehér',200)"};
            //insertMultipleCar(conn,sqlString);
            //whatIsTheTable(conn,"fehér");
            //allcarsInTheTable(conn);
            //getMostExpensiveCar(conn);
            getAllCarMetadata(conn);
            System.out.println("End of program");
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    public static Connection connectDb(String username, String password)throws ClassNotFoundException, SQLException{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(URL,username,password);
        return conn;
    }

    public static void createTable(Connection conn) throws SQLException{
        System.out.println("Table creating...");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE CAR ("
                + "id number(4) primary key, "
                + "manufacturer varchar2(200) not null, "
                + "color varchar2(20) not null, "
                + "price number(5) not null "
                + ")");
        System.out.println("Table created");
    }

    public static void insertCar(Connection conn) throws SQLException{
        Statement stmt = conn.createStatement();

        System.out.println("Insert returned: " + stmt.executeUpdate(""
        + "insert into car values(3,'Skoda','piros',600)"));
    }
    public static void setPriceOfCarByColor(Connection conn, String color, int price) throws SQLException{
        System.out.println("Set price for "+ color +" cars to " + price);
        Statement statement = conn.createStatement();
        statement.executeUpdate("update car set price="+ price +"where color='"+ color +"'");
    }

    public static void setPriceOfCarByColorPrep(Connection conn, String color, int price)throws SQLException{
        PreparedStatement prstmt = conn.prepareStatement("update car set price=? where color=?");
        prstmt.setInt(1,price);
        prstmt.setString(2,color);
        prstmt.close();
        System.out.println("Prep test done");
    }

    public static void insertMultipleCar(Connection conn, String[] insertSql) throws SQLException{
        Statement stmt = conn.createStatement();
        for(String sql:insertSql){
            stmt.addBatch(sql);
        }
        System.out.println(stmt.executeBatch());
    }

    public static void allcarsInTheTable(Connection conn) throws SQLException{
        Cars car = new Cars();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM car");
        while (rs.next()){
            car.setId(rs.getInt(1));
            car.setManufacturer(rs.getString(2));
            car.setColor(rs.getString(3));
            car.setPrice(rs.getInt(4));
            System.out.println(car.toString());
        }
        rs.close();
        stmt.close();
    }

    public static void whatIsTheTable(Connection conn, String color) throws SQLException{
        System.out.println("Show "+ color + " cars in the table");
        Cars car = new Cars();
        PreparedStatement prstmt = conn.prepareStatement(
                "SELECT * FROM car where color=?"
        );
        prstmt.setString(1,color);
        ResultSet rs = prstmt.executeQuery();
        while (rs.next()){
            car.setId(rs.getInt(1));
            car.setManufacturer(rs.getString(2));
            car.setColor(rs.getString(3));
            car.setPrice(rs.getInt(4));
            System.out.println(car.toString());
        }
        rs.close();
        prstmt.close();

    }

    public static void deleteCarByID(Connection conn,int id) throws SQLException{
        PreparedStatement prstmt = conn.prepareStatement("DELETE car WHERE id=?");
        prstmt.setInt(1,id);
        System.out.println("deleted rows:" + prstmt.executeUpdate());
    }

    public static void  getDatabaseMetaData(Connection conn) throws SQLException{
        System.out.println("driver verzió: "+
                conn.getMetaData().getDriverVersion());
        String[] specifyTables= {"TABLE"};
        ResultSet rs = conn.getMetaData().getTables(null, null, "%", specifyTables);
        while(rs.next()) {
            System.out.println(rs.getString(3));
        }
    }

    public static void getMostExpensiveCar(Connection conn) throws SQLException{
        PreparedStatement prstmt = conn.prepareStatement("" +
                "SELECT * FROM car " +
                "WHERE price=(SELECT max(price) FROM car)"
        );
        ResultSet rs = prstmt.executeQuery();
        rs.next();
        Cars car = new Cars(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4)
        );
        System.out.println(car.toString());
        prstmt.close();
        conn.close();
    }

    public static void getAllCarMetadata(Connection conn) throws SQLException{
        PreparedStatement prstmt = conn.prepareStatement(
                "SELECT * FROM car"
        );
        ResultSetMetaData rsmd = prstmt.getMetaData();
        System.out.println("number of colums: "+ rsmd.getColumnCount());
        for (int i=1; i<rsmd.getColumnCount()+1;i++){
            System.out.println(rsmd.getColumnName(i) + " : " +
                    rsmd.getColumnTypeName(i) + "---");
        }
        prstmt.close();
        conn.close();
    }
}
