package adatb.kristaly.services;

import adatb.kristaly.repository.DbMetaRepo;
import adatb.kristaly.repository.EmberRepo;
import adatb.kristaly.repository.ProgramRepo;
import adatb.kristaly.repository.SzobaRepo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DatabaseService {
    private List<String> tablak = new ArrayList<>();

    public DatabaseService(){
        tablak.add("szobak");
        tablak.add("emberek");
        tablak.add("programok");
        tablak.add("programember");
    }

    public void checkTables(Connection conn){
        List<String> hianyzik = new ArrayList<>();
        Iterator<String> iter = tablak.iterator();
        DbMetaRepo db = new DbMetaRepo();

        while (iter.hasNext()){
            String name = iter.next();
            if(!db.checkTableStatus(conn, name)){
                hianyzik.add(name);
            }
        }
        if (hianyzik.isEmpty()){
            System.out.println("Az adatbázis készen áll!");
        }else if (hianyzik.size() == tablak.size()){
            System.out.println("Adatbázistáblák létrehozása....");
            createAllTable(conn);

        }else{
            System.out.println("Pár szükséges táblák nem léteznek az adatbázisában! "+ hianyzik.toString());
        }



    }

    private void createAllTable(Connection conn){
        SzobaRepo szobak = new SzobaRepo(conn);
        EmberRepo emberek = new EmberRepo(conn);
        ProgramRepo programok = new ProgramRepo(conn);
        szobak.createTable();
        emberek.createTable();
        programok.createTable();
        programok.createConnectionTable();
    }
}
