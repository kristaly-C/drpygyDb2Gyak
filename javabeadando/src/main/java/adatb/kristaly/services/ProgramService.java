package adatb.kristaly.services;

import adatb.kristaly.domain.Ember;
import adatb.kristaly.domain.Program;
import adatb.kristaly.repository.ProgramRepo;

import java.sql.Connection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ProgramService {

    Connection connection;

    public ProgramService(Connection connection) {
        this.connection = connection;
    }

    public void addNewProgram(Program program){
        ProgramRepo programRepo = new ProgramRepo(connection);
        if(programRepo.insertData(program) > 0){
            System.out.println("Sikeres Adatfelvitel");
        }

    }

    public void deleteTables() {
        ProgramRepo programRepo = new ProgramRepo(connection);
        programRepo.deleteProgramTable();
    }

    public void deleteAll() {
        ProgramRepo programRepo = new ProgramRepo(connection);
        programRepo.clearKapcsolatok();
    }

    public int pickID() {
        ProgramRepo programRepo = new ProgramRepo(connection);
        List<Program> lista = programRepo.getAll();
        if(lista.isEmpty()){
            return 1;
        }else{
            return lista.get(0).getProgramID() + 1;
        }
    }

    public List<Program> getprograms(){
        ProgramRepo programRepo = new ProgramRepo(connection);
        return programRepo.getAll();
    }

    public int howManyProgram() {
        ProgramRepo programRepo = new ProgramRepo(connection);
        List<Program> lista = programRepo.getAll();
        if (lista.isEmpty()){
            return 0;
        }else {
         return lista.size();
        }
    }

    public boolean eventExists(int programID) {
        ProgramRepo programRepo = new ProgramRepo(connection);
        Program program = programRepo.getProgramByID(programID);
        if (program != null){
            return true;
        }
        return false;
    }

    public void addPersonToProgram(int programid, int emberid) {
        ProgramRepo programRepo = new ProgramRepo(connection);
        int db = programRepo.insertKapcsolat(programid, emberid);
        if (db > 0){
            System.out.println("Sikeres adatfelvétel");
        }else {
            System.out.println("Sikertelen adatfelvétel");
        }
    }

    public void removeUserFromProgram(int programID, int guest) {
        ProgramRepo programRepo = new ProgramRepo(connection);
        int db = programRepo.deleteKapcsolat(programID,guest);
        if (db > 0){
            System.out.println("Sikeres törlés");
        }else {
            System.out.println("Sikertelen törlés");
        }
    }

    public void getNextPrograms() {
        ProgramRepo programRepo = new ProgramRepo(connection);
        Date now = new Date();
        String nowString = now.getYear() +"-"+ now.getMonth() +"-"+ now.getDay() +" "+ now.getHours() +":"+ now.getMinutes() +":"+ now.getSeconds();
        List<Program> lista = programRepo.getAllNext(nowString);
        if (!lista.isEmpty()){
            Iterator<Program> iter = lista.iterator();
            while (iter.hasNext()){
               System.out.println(iter.next().toString());
            }
        }else {
            System.out.println("Nincs tervezett rendezveny.");
        }
    }

    public void ShowProgramGuestList(int programID) {
        ProgramRepo programRepo = new ProgramRepo(connection);
        List<Ember> list = programRepo.getGuestsWhoInProgram(programID);
        if (list.isEmpty()){
            System.out.println("Ezen a programra nincs regisztrált vendég");
        }else{
            Iterator<Ember> iter = list.iterator();
            while (iter.hasNext()){
               System.out.println(iter.next().toString());
            }
        }
    }

    public List<Ember> getProgramGuestList(int programID) {
        ProgramRepo programRepo = new ProgramRepo(connection);
        return programRepo.getGuestsWhoInProgram(programID);
    }

    public Program getEventInfos(int programID) {
        ProgramRepo programRepo = new ProgramRepo(connection);
        return programRepo.getProgramByID(programID);
    }
}
