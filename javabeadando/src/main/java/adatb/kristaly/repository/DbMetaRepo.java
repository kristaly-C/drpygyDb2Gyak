package adatb.kristaly.repository;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbMetaRepo {

    public boolean checkTableStatus(Connection connection, String tablename){
        int counter = 0;
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet rs = databaseMetaData.getTables(null, null, tablename.toUpperCase(), new String[]{"TABLE"});
            while (rs.next()){
                counter++;
            }
            rs.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        if (counter == 0){
            return false;
        }
        return true;
    }
}
