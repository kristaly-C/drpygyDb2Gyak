package adatb.kristaly.services;

import adatb.kristaly.domain.Szoba;
import adatb.kristaly.repository.EmberRepo;
import adatb.kristaly.repository.SzobaRepo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SzobaService {

    Connection connection;

    public SzobaService(Connection connection) {
        this.connection = connection;
    }

    public void showNotFullSzoba(){
        SzobaRepo szobarepo = new SzobaRepo(connection);
        List<Szoba> szobak = szobarepo.getSzobakWhereFreeSlot(1);
        if (szobak.isEmpty()){
            System.out.println("Nincs szabad szoba");
        }else{
            Iterator<Szoba> iter = szobak.iterator();
            while (iter.hasNext()){
                System.out.print(iter.next().toString() + " ");
            }
            System.out.print("\n");
        }
    }

    public boolean isThereNotFullSzoba(){
        SzobaRepo szobarepo = new SzobaRepo(connection);
        List<Szoba> szobak = szobarepo.getSzobakWhereFreeSlot(1);
        if (szobak.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public int howMuchSpaceIsThere(int roomID){
        SzobaRepo szobarepo = new SzobaRepo(connection);
        int szabadhely = szobarepo.freeSpaceByID(roomID);
        return szabadhely;
    }

    public boolean szobaRegistrated(int id){
        SzobaRepo szrep = new SzobaRepo(connection);
        Szoba szoba = szrep.getSzobaByID(id);
        if (szoba == null){
            return false;
        }
        //System.out.println("\n" + szoba.toString());
        return true;
    }

    public void registerNewSzoba(Szoba newszoba) {
        SzobaRepo szrep = new SzobaRepo(connection);
        if(szrep.insertRoom(newszoba) > 0){
            System.out.println("Sikeres");
        }else {
            System.out.println("Sikertelen");
        }
    }

    public void allSzoba(){
        SzobaRepo szobaRepo = new SzobaRepo(connection);
        List<Szoba> szobak = szobaRepo.getAllSzoba();
        if (szobak.isEmpty()){
            System.out.println("\nNincsenek regisztralt szobak\n");
        }else {
            Iterator<Szoba> iter = szobak.iterator();
            System.out.print("\n");
            while (iter.hasNext()){
                System.out.println(iter.next().toString());
            }
            System.out.print("\n");
        }

    }
    public List<Integer> getAllSzoba(){
        SzobaRepo szobaRepo = new SzobaRepo(connection);
        List<Integer> szobakid = new ArrayList<>();

        List<Szoba> szobak = szobaRepo.getAllSzoba();
        if(!szobak.isEmpty()){
            Iterator<Szoba> iter = szobak.iterator();
            while (iter.hasNext()){
                szobakid.add(iter.next().getId());
            }
        }
        return szobakid;
    }

    public boolean isSzobaEmpty(int id){
        SzobaRepo szobaRepo = new SzobaRepo(connection);
        Szoba szoba = szobaRepo.getSzobaByID(id);
        if (szoba == null){
            return false;
        }
        System.out.println("\n" + szoba.toString());
        int maxperson = szoba.getMaxperson();
        if (szoba.getFreeslot() == maxperson){
            return true;
        }else {
            return false;
        }
    }

    public void deleteSzoba(int roomID) {
        SzobaRepo szobaRepo = new SzobaRepo(connection);
        if(szobaRepo.deleteSzoba(roomID) != 0){
            System.out.println("\nSzoba törölve\n");
        }
    }
    public void deleteSzoba(List<Integer> szobak) {
        SzobaRepo szobaRepo = new SzobaRepo(connection);
        if(szobaRepo.deleteSzoba(szobak) != 0){
            System.out.println("\nSzobak törölve\n");
        }
    }

    public void getSzoba(int szobaID) {
        SzobaRepo szobaRepo = new SzobaRepo(connection);
        Szoba szoba = szobaRepo.getSzobaByID(szobaID);
        if (szoba != null){
            System.out.println(szoba.toString());
        }else {
            System.out.println("\nNincs " + szobaID + "-es szoba a lisstában\n");
        }
    }

    public void decreaseFreeSlot(int roomid){
        SzobaRepo szobaRepo = new SzobaRepo(connection);
        szobaRepo.decraseFreeSpace(roomid);
    }

    public void getStat() {
        SzobaRepo szobaRepo = new SzobaRepo(connection);
        List<Szoba> szobak = szobaRepo.getAllSzoba();
        int capacity = 0;
        int reserved = 0;
        int numberofrooms = 0;
        if (szobak.isEmpty()){
            System.out.println("-----Nincsenek adatok-----");
        }else {
            Iterator<Szoba> iter = szobak.iterator();
            while (iter.hasNext()){
                Szoba szoba = iter.next();
                capacity += szoba.getMaxperson();
                reserved += szoba.getFreeslot();
                numberofrooms++;
            }
            System.out.println("Férőhely [" + capacity +"] ebből szabad ["+ reserved +"] telítettség [" + (int)(((float)1-((float)reserved/(float)capacity))*100) + "%] ez [" + numberofrooms +"] szoba");
        }

    }

    public void increaseFreeSlot(int roomid) {
        SzobaRepo szobaRepo = new SzobaRepo(connection);
        szobaRepo.increaseFreeSpace(roomid);
    }

    public void deleteAll() {
        SzobaRepo szobaRepo = new SzobaRepo(connection);
        szobaRepo.deleteAllSzoba();
    }

    public void deleteTable() {
        SzobaRepo szobaRepo = new SzobaRepo(connection);
        szobaRepo.dropSzoba();
    }

    public int howManySzoba() {
        SzobaRepo szobaRepo = new SzobaRepo(connection);
        int rooms = 0;
        List<Szoba> szobak = szobaRepo.getAllSzoba();
        if(!szobak.isEmpty()){
          rooms = szobak.size();
        }
        return rooms;
    }
}
