package adatb.kristaly.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbInterface {
    public Connection getConnection() throws SQLException, ClassNotFoundException;
}
