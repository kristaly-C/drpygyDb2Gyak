package adatb.kristaly.domain;

import adatb.kristaly.interfaces.MenuItemInterface;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Menu {
    List<MenuItemInterface> menu = new ArrayList<>();

    public Menu(){}

    public void addMenuItem(MenuItemInterface item){

        item.setID(findLastID()+1);
        menu.add(item);
    }
    public void addMenuItem(String name){
        MenuLine newline = new MenuLine(name);
        newline.setID(findLastID());
        menu.add(newline);
    }

    private int findLastID(){
        int lastID = 1;
        if(menu.isEmpty()){
            return lastID;
        }else{
            Iterator<MenuItemInterface> iter = menu.iterator();
            while (iter.hasNext()){
                lastID = iter.next().getLineID()+1;
            }
            return lastID;
        }
    }

    public void drawVisibleMenu(){
        System.out.println("---------------------------------");
        Iterator<MenuItemInterface> iter = menu.iterator();
        while (iter.hasNext()){
            MenuItemInterface item = iter.next();
            if(item.getVisibilityStatus()){
                showItem(item);
            }
        }
        System.out.println("---------------------------------");
    }
    public void addBackButton(String subtitle){
        MenuLine exitLine = new MenuLine(subtitle);
        exitLine.setID(findLastID());
        menu.add(exitLine);
    }

    private void showItem(MenuItemInterface item){
        System.out.println(item.getLineID()+" - "+item.getLineName());
    }


    public int pickItem(){
        boolean pickOK = false;
        int retnumber = findLastID()-1;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.print("Kérlek írd be a választani kívánt menüpont számát: ");
            try {
                int picked = sc.nextInt();
                if (picked <= findLastID()-1 && picked > 0){
                    retnumber = picked;
                    pickOK = true;
                }else {
                    System.out.println("Nincs ilyen menüpont.");
                }
            }catch (Exception e){
                System.out.println("Gratulálok");
                sc.next();
            }
        }while (!pickOK);

       return retnumber;
    }
}
