package adatb.kristaly.domain;

public class Ember {
    private int ID;
    private String firstname;
    private String lastname;
    private int rank;
    private int roomID;
    private boolean male;

    public Ember(int ID, String firstname, String lastname, int rank, int roomID, boolean male) {
        this.ID = ID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.rank = rank;
        this.roomID = roomID;
        this.male = male;
    }

    public Ember(int ID, String firstname, String lastname, int rank, int roomID, int male) {
        this.ID = ID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.rank = rank;
        this.roomID = roomID;
        this.male = (male == 1) ? true : false;
    }

    public Ember(String firstname, String lastname, int rank, int roomID, boolean male) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.rank = rank;
        this.roomID = roomID;
        this.male = male;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public boolean isMale() {
        return male;
    }

    public int isMaleNumber(){if (this.isMale()){return 1;} return 0;}

    public void setMale(boolean male) {
        this.male = male;
    }

    public String getRankWord(){
        switch (this.rank){
            case 1 : return "alap";
            case 2: return "kiemelt";
            case 3: return "prémium";
        }
        return "hiba";
    }

    public String getgender(){
        if (male){
            return "férfi";
        }
        return "nő";
    }

    @Override
    public String toString() {
        return "Vendég " +
                "ID [" + ID +
                "] vezeteknev [" + lastname +
                "] keresztnev [" + firstname +
                "] besorolas [" + getRankWord() +
                "] szobaszam [" + roomID +
                "] gender ["+ getgender() +
                ']';
    }
}
