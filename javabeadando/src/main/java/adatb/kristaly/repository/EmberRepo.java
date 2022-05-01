package adatb.kristaly.repository;

import adatb.kristaly.domain.DbOracle;
import adatb.kristaly.domain.Ember;
import adatb.kristaly.interfaces.DbInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmberRepo {

    Connection conn;

    public EmberRepo(Connection conn) {
        this.conn = conn;
    }

    public void createTable() {
    System.out.println("Ember table creating...");
    try {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE EMBEREK(" +
                "                id number primary key ," +
                "                firstname varchar2(40) not null ," +
                "                lastname varchar2(40) not null ," +
                "                rank number(2) not null ," +
                "                roomid number(5)," +
                "                male number(1) not null," +
                "                FOREIGN KEY (roomid) REFERENCES SZOBAK(id)" +
                "                )");
        stmt.close();
    }catch (SQLException sqle){
        System.out.println(sqle.getMessage());
        System.out.println(sqle.getSQLState() + "\n" + sqle.getErrorCode());
    }
    System.out.println("Kész!");
}

public List<Ember> getAllActivePerson(){
    List<Ember> emberek = new ArrayList<>();
    try {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM EMBEREK WHERE roomid <> -1 ORDER BY roomid ASC");
        while (rs.next()){
            Ember ember = new Ember(
                    rs.getInt("id"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getInt("rank"),
                    rs.getInt("roomid"),
                    rs.getInt("male")
            );
            emberek.add(ember);
        }
        rs.close();
        stmt.close();
    }catch (SQLException sqle){
        return emberek;
    }
    return emberek;
}

public int insertPerson(Ember person){
    int retnum = 0;
    try {
        PreparedStatement psInsert = conn.prepareStatement("INSERT INTO EMBEREK VALUES (?,?,?,?,?,?)");

        psInsert.setInt(1,person.getID());
        psInsert.setString(2,person.getFirstname());
        psInsert.setString(3,person.getLastname());
        psInsert.setInt(4,person.getRank());
        psInsert.setInt(5,person.getRoomID());
        psInsert.setInt(6,person.isMaleNumber());
        retnum = psInsert.executeUpdate();

    }catch (SQLException sqle){
        System.err.println(sqle.getMessage());
    }
    return retnum;
}

    public Ember getPersonbyID(int id){
        try {
            PreparedStatement prstmt = conn.prepareStatement("SELECT * FROM EMBEREK WHERE id = ?");
            prstmt.setInt(1, id);
            ResultSet rs = prstmt.executeQuery();
                rs.next();
                Ember ember = new Ember(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getInt("rank"),
                        rs.getInt("roomid"),
                        rs.getInt("male")
                );
                rs.close();
                prstmt.close();
                return ember;
        }catch (SQLException e){
            return null;
        }
    }

    public List<Ember> getPeopleWhereRoomID(int roomID) {
        try {
            List<Ember> emberList = new ArrayList<>();
            PreparedStatement prstmt = conn.prepareStatement("SELECT * FROM EMBEREK WHERE roomid = ?");
            prstmt.setInt(1,roomID);
            ResultSet rs = prstmt.executeQuery();
            while (rs.next()){
                Ember ember = new Ember(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getInt("rank"),
                        rs.getInt("roomid"),
                        rs.getInt("male")
                );
                emberList.add(ember);
            }
            if (emberList.isEmpty()){
            return null;
            }
            return emberList;
        }catch (SQLException e){
            return null;
        }
    }

    public int switchUserRoom(int id, int targetroom) {
        try {
            PreparedStatement prstmt = conn.prepareStatement("UPDATE EMBEREK SET roomid = ? WHERE id = ?");
            prstmt.setInt(1,targetroom);
            prstmt.setInt(2,id);
            return prstmt.executeUpdate();
        }catch (SQLException e){
            System.err.println(e.getLocalizedMessage());
            return 0;
        }
    }

    public void deleteAllEmber() {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("TRUNCATE TABLE EMBEREK");
            statement.close();
        }catch (SQLException e){
            System.err.println("Törlési hiba merült fel");
        }
    }

    public void dropEmber() {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DROP TABLE EMBEREK");
            statement.close();
            System.out.println("EMBEREK tábla törölve");
        }catch (SQLException e){
            //System.err.println("Törlési hiba merült fel");
        }
    }

    public int numberOfRow() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM EMBEREK");
            rs.next();
            int num = rs.getInt(1);
            return num;
        }catch (SQLException e){
            return 0;
        }
    }
}
