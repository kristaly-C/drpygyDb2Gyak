package adatb.kristaly.repository;

import adatb.kristaly.domain.Ember;
import adatb.kristaly.domain.Szoba;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SzobaRepo {

    Connection conn;

    public SzobaRepo(Connection conn) {
        this.conn = conn;
    }

    public void createTable() {
        System.out.println("Szoba tábla létrehozása...");
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE SZOBAK(" +
                    "  id number PRIMARY KEY," +
                    "  maxpeople number NOT NULL," +
                    "  incwc number(1) NOT NULL," +
                    "  incshower number(1) NOT NULL," +
                    "  freeslot number NOT NULL," +
                    "  CONSTRAINT chk_free CHECK (freeslot <= maxpeople)\n" +
                    ")");
            stmt.close();

        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
            System.out.println(sqle.getSQLState() + " " + sqle.getErrorCode());
        }
        System.out.println("Kész!");
    }

    public int insertRoom(Szoba szoba){
        int retnum = 0;
        try {
            PreparedStatement prstmt = conn.prepareStatement("INSERT INTO SZOBAK VALUES (?,?,?,?,?)");
            prstmt.setInt(1,szoba.getId());
            prstmt.setInt(2,szoba.getMaxperson());
            prstmt.setInt(3,szoba.isIncludeWCNum());
            prstmt.setInt(4,szoba.isIncludeShoverNum());
            prstmt.setInt(5,szoba.getFreeslot());
            retnum = prstmt.executeUpdate();
            prstmt.close();
        }catch (SQLException sqle){
            System.err.println(sqle.getMessage());
        }
        return retnum;
    }

    public List<Szoba> getAllSzoba(){
        List<Szoba> szobak = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SZOBAK");
            while (rs.next()){
             Szoba szoba = new Szoba(
                     rs.getInt("id"),
                     rs.getInt("maxpeople"),
                     rs.getInt("incwc"),
                     rs.getInt("incshower"),
                     rs.getInt("freeslot")
             );
             szobak.add(szoba);
            }
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
        }
        return szobak;
    }

    public Szoba getSzobaByID(int id){
        try {
            PreparedStatement prstmt = conn.prepareStatement("SELECT * FROM SZOBAK WHERE id = ?");
            prstmt.setInt(1,id);
            ResultSet rs = prstmt.executeQuery();
            rs.next();
                Szoba szoba = new Szoba(
                        rs.getInt("id"),
                        rs.getInt("maxpeople"),
                        rs.getInt("incwc"),
                        rs.getInt("incshower"),
                        rs.getInt("freeslot")
                );
                return szoba;
        }catch (SQLException e){
            return null;
        }

    }

    public int freeSpaceByID(int roomid){
        try {
            PreparedStatement prstmt = conn.prepareStatement("SELECT freeslot FROM SZOBAK WHERE id = ?");
            prstmt.setInt(1,roomid);
            ResultSet rs = prstmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            return 0;
        }
    }

    public List<Szoba> getSzobakWhereFreeSlot(int bed){
        List<Szoba> szobak = new ArrayList<>();
        try {
            PreparedStatement prstmt = conn.prepareStatement("SELECT * FROM SZOBAK WHERE freeslot >= ?");
            prstmt.setInt(1,bed);
            ResultSet rs = prstmt.executeQuery();
            while (rs.next()){
                Szoba szoba = new Szoba(
                        rs.getInt("id"),
                        rs.getInt("maxpeople"),
                        rs.getInt("incwc"),
                        rs.getInt("incshower"),
                        rs.getInt("freeslot")
                );
                szobak.add(szoba);
            }
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
        }
        return szobak;
    }

    public int deleteSzoba(int szobaid){
        try {
            PreparedStatement prstmt = conn.prepareStatement("DELETE FROM SZOBAK WHERE id = ?");
            prstmt.setInt(1,szobaid);
            int mod = prstmt.executeUpdate();
            return mod;
        }catch (SQLException e){
            System.out.println(e);
            return 0;
        }

    }
    public int deleteSzoba(List<Integer> szobaidk){
        int mod = 0;
        try {
            PreparedStatement prstmt = conn.prepareStatement("DELETE FROM SZOBAK WHERE id = ?");
            Iterator<Integer> iter = szobaidk.iterator();
            while (iter.hasNext()){
                prstmt.setInt(1,iter.next());
                mod += prstmt.executeUpdate();
            }
            return mod;
        }catch (SQLException e){
            System.out.println(e);
            return 0;
        }

    }

    public void decraseFreeSpace(int roomid){
        try {
            PreparedStatement psUpdate = conn.prepareStatement("UPDATE SZOBAK SET freeslot = freeslot - 1 WHERE id = ?");
            psUpdate.setInt(1,roomid);
            psUpdate.executeUpdate();
        }catch (SQLException e){
            System.err.println("sikertelen");
        }
    }
    public void increaseFreeSpace(int roomid){
        try {
            PreparedStatement psUpdate = conn.prepareStatement("UPDATE SZOBAK SET freeslot = freeslot + 1 WHERE id = ?");
            psUpdate.setInt(1,roomid);
            psUpdate.executeUpdate();
        }catch (SQLException e){
            System.err.println("sikertelen");
        }
    }

    public void deleteAllSzoba() {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("TRUNCATE TABLE SZOBAK");
            statement.close();
        }catch (SQLException e){
            System.err.println(e.getLocalizedMessage());
        }
    }
    public void dropSzoba() {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DROP TABLE  SZOBAK");
            statement.close();
            System.out.println("SZOBAK tábla törölve");
        }catch (SQLException e){
            System.err.println("Törlési hiba merült fel");
        }
    }
}
