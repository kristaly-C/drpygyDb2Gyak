package adatb.kristaly.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ProgramRepo {

    Connection conn;

    public ProgramRepo(Connection conn) {
        this.conn = conn;
    }

    public void createTable() {
        System.out.println("Program tábla létrehozása...");
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE PROGRAMOK(" +
                    "id number primary key ," +
                    "programname varchar2(100) not null ," +
                    "startDate date not null ," +
                    "duration number ," +
                    "maxperson number )");
            stmt.close();
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
            System.out.println(sqle.getSQLState() + "\n" + sqle.getErrorCode());
        }
        System.out.println("Kész!");
    }
    public void createConnectionTable() {
        System.out.println("Kapcsolótábla tábla létrehozása...");
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE PROGRAMEMBER(" +
                    "programID number not null," +
                    "emberID number not null," +
                    "FOREIGN KEY (programID) REFERENCES programok(id)," +
                    "FOREIGN KEY (emberID) REFERENCES emberek(id))");
            stmt.close();
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
            System.out.println(sqle.getSQLState() + "\n" + sqle.getErrorCode());
        }
        System.out.println("Kész!");
    }

    public void deleteProgramTable(){
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DROP TABLE PROGRAMEMBER");
            stmt.executeUpdate("DROP TABLE PROGRAMOK");
            stmt.close();
            System.out.println("PROGRAMOK tábla törölve");
        }catch (SQLException sqle){
            System.err.println(sqle.getMessage());
        }
    }

    public void deleteAllSzoba() {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("TRUNCATE TABLE PROGRAMEMBER");
            statement.executeUpdate("TRUNCATE TABLE PROGRAMOK");
            statement.close();
        }catch (SQLException e){
            System.err.println(e.getLocalizedMessage());
        }
    }
}
