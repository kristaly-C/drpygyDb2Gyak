package adatb.kristaly.services;

import adatb.kristaly.domain.Ember;
import adatb.kristaly.repository.EmberRepo;
import adatb.kristaly.repository.SzobaRepo;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

public class EmberService {

    Connection conn;

    public EmberService(Connection conn){
        this.conn = conn;
    }

    public void addNewPerson(int id,String firstname,String lastname,int rank,int roomID,boolean male){
        Ember person = new Ember(id,firstname,lastname,rank,roomID,male);
        EmberRepo emberRepo = new EmberRepo(conn);
        emberRepo.insertPerson(person);

    }
    public void addNewPerson(Ember person){
        SzobaRepo szobrep = new SzobaRepo(conn);

    }

    public boolean userInTable(int id){
        EmberRepo emberRepo = new EmberRepo(conn);
        if (emberRepo.getPersonbyID(id) == null){
            return false;
        }
        return true;
    }

    public void showAllPerson(){
        EmberRepo emberRepo = new EmberRepo(conn);
        List<Ember> emberek = emberRepo.getAllActivePerson();
        if (emberek.isEmpty()){
            System.out.println("Üres");
        }else{
            Iterator<Ember> iter = emberek.iterator();
            while (iter.hasNext()){
                Ember ember = iter.next();
               System.out.println(ember.toString());
            }
        }
    }
    public int howManyPeople(){
        EmberRepo emberRepo = new EmberRepo(conn);
        int ember = emberRepo.numberOfRow();
        return ember;
    }

    public void getEmberByRoomID(int roomID) {
        EmberRepo emberRepo = new EmberRepo(conn);
        List<Ember> emberek = emberRepo.getPeopleWhereRoomID(roomID);
        if (emberek != null){
            Iterator<Ember> iter = emberek.iterator();
            while (iter.hasNext()){
                System.out.println(iter.next().toString());
            }
        }else {
            System.out.println("Üres szoba\n");
        }
    }

    public void switchRoom(int userID,int targetRoom) {
        EmberRepo emberRepo = new EmberRepo(conn);
        SzobaService szS = new SzobaService(conn);
        Ember ember  = emberRepo.getPersonbyID(userID);
        if(emberRepo.switchUserRoom(userID,targetRoom) > 0){
            szS.increaseFreeSlot(ember.getRoomID());
            szS.decreaseFreeSlot(targetRoom);
            System.out.println("Sikeres áthelyezés");
        }else {
            System.out.println("Sikertelen áthelyezés");
        }
    }

    public Ember getEmberByID(int id) {
        EmberRepo emberRepo = new EmberRepo(conn);
        Ember vendeg = emberRepo.getPersonbyID(id);
        return vendeg;
    }

    public void deleteAll() {
        EmberRepo emberRepo = new EmberRepo(conn);
        emberRepo.deleteAllEmber();
    }
    public void deleteTable() {
        EmberRepo emberRepo = new EmberRepo(conn);
        emberRepo.dropEmber();
    }

    public List<Ember> getAllEmber() {
        EmberRepo emberRepo = new EmberRepo(conn);
        return emberRepo.getAllActivePerson();
    }
}
