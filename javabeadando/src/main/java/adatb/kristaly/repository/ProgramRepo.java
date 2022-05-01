package adatb.kristaly.repository;

import adatb.kristaly.domain.Ember;
import adatb.kristaly.domain.Program;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                    "startDate TIMESTAMP not null ," +
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

    public int insertData(Program program){
        try {
            PreparedStatement prstmt = conn.prepareStatement("INSERT INTO PROGRAMOK VALUES (?,?,?,?,?)");
            prstmt.setInt(1,program.getProgramID());
            prstmt.setString(2,program.getProgramName());
            prstmt.setTimestamp(3,Timestamp.valueOf(program.getProgramStartString()));
            prstmt.setInt(4,program.getDuration());
            prstmt.setInt(5,program.getMaxPerson());
            return prstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("Sikertelen adatfelvitel" + e.getLocalizedMessage());
            return 0;
        }/*catch (Exception e){
            System.out.println("Hiba");
            return 0;
        }*/
    }

    public List<Program> getAll(){
        List<Program> programok = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PROGRAMOK ORDER BY id DESC");
            while (rs.next()){
                Program prgram = new Program(
                rs.getInt(1),
                rs.getString(2),
                new Date(rs.getTimestamp(3).getTime()),
                rs.getInt(4),
                rs.getInt(5));
                programok.add(prgram);
            }
            return programok;
        }catch (SQLException e){
            System.out.println("Hibas adatolvasas a szerverről");
            System.out.println(e.getLocalizedMessage());
            return programok;
        }
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


    public void clearKapcsolatok() {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("TRUNCATE TABLE PROGRAMEMBER");
            statement.executeUpdate("TRUNCATE TABLE PROGRAMOK");
            statement.close();
        }catch (SQLException e){
            System.err.println(e.getLocalizedMessage());
        }
    }

    public Program getProgramByID(int programID) {
        try {
            PreparedStatement prstmt = conn.prepareStatement("SELECT * FROM PROGRAMOK WHERE id = ?");
            prstmt.setInt(1, programID);
            ResultSet rs = prstmt.executeQuery();
            rs.next();
            Program prgram = new Program(
                    rs.getInt(1),
                    rs.getString(2),
                    new Date(rs.getTimestamp(3).getTime()),
                    rs.getInt(4),
                    rs.getInt(5));
            rs.close();
            prstmt.close();
            return prgram;
        }catch (SQLException e){
            return null;
        }
    }

    public int insertKapcsolat(int programid, int emberid) {
        try {
            PreparedStatement prstmt = conn.prepareStatement("INSERT INTO PROGRAMEMBER VALUES (?,?)");
            prstmt.setInt(1,programid);
            prstmt.setInt(2,emberid);
            return prstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("Adatbázis hiba");
            return 0;
        }
    }

    public int deleteKapcsolat(int programID, int guest) {
        try {
            PreparedStatement prstmt = conn.prepareStatement("DELETE FROM PROGRAMEMBER WHERE programID = ? AND emberID = ?");
            prstmt.setInt(1,programID);
            prstmt.setInt(2,guest);
            return prstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("Adatbázis hiba");
            return 0;
        }
    }

    public List<Program> getAllNext(String datum) {
        List<Program> list = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PROGRAMOK WHERE CURRENT_TIMESTAMP < startDate");
            while(rs.next()){
                Program prgram = new Program(
                        rs.getInt(1),
                        rs.getString(2),
                        new Date(rs.getTimestamp(3).getTime()),
                        rs.getInt(4),
                        rs.getInt(5));
                list.add(prgram);
            }
            rs.close();
            stmt.close();
            return list;
        }catch (SQLException e){
            return list;
        }

    }

    public List<Ember> getGuestsWhoInProgram(int programID){
       List<Ember> emberek = new ArrayList<>();
        try {
            PreparedStatement prstmt = conn.prepareStatement("SELECT EMBEREK.* FROM EMBEREK INNER JOIN PROGRAMEMBER ON EMBEREK.id = PROGRAMEMBER.emberID WHERE PROGRAMEMBER.programID = ? ");
            prstmt.setInt(1,programID);
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
                emberek.add(ember);
            }
            rs.close();
            prstmt.close();
            return emberek;
        }catch (SQLException e){
            return emberek;
        }
    }
}
