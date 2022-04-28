package adatb.kristaly.domain;

import adatb.kristaly.interfaces.DbInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbOracle implements DbInterface {
    private String address;
    private String password;
    private String user;

    public DbOracle(String address, String password, String user){
        this.address = address;
        this.password = password;
        this.user = user;
    }

    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(address,user,password);
        return conn;
    }
}
