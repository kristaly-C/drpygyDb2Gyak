package adatb.kristaly.domain;

import adatb.kristaly.interfaces.MenuItemInterface;

public class MenuLine implements MenuItemInterface {
    private int ID = 0;
    private String Name;
    private boolean visible;

    public MenuLine(String name){
        this.Name = name;
        this.visible = true;
    }


    @Override
    public int getLineID() {
        return this.ID;
    }

    @Override
    public String getLineName() {
        return this.Name;
    }

    @Override
    public void setVisibility(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean getVisibilityStatus() {
        return this.visible;
    }

    @Override
    public void setID(int id) {
        this.ID = id;
    }

}
