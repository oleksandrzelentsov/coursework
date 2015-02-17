package com.visualisation;

import java.util.LinkedHashMap;
import java.util.Map;

public class Menu {
    Map<String, Action> menu;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    String menuName;

    static final String DEFAULT_MENU_NAME = "Menu";

    public Menu() {
        menu = new LinkedHashMap<String, Action>();
        setMenuName(DEFAULT_MENU_NAME);
    }

    public Menu(String name) {
        this();
        menuName = name;
    }

    public void addAction(Action a) {
        menu.put(a.getName(), a);
    }

    public void addActions(Action[] actions) {
        for(Action a : actions) {
            addAction(a);
        }
    }


    public void mainLoop() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        while(true) {
            System.out.println("------------------------------------------------");
            System.out.println(menuName+":");
            for(int i = 0; i < menu.keySet().toArray().length; ++i) {
                System.out.println(i+1 + ". " + menu.keySet().toArray()[i]);
            }
            System.out.println("------------------------------------------------");
            System.out.print("Enter either the name or the number of action: ");
            try {
                if(!sc.hasNextInt())
                    performAction(sc.next());
                else
                    performAction((String)menu.keySet().toArray()[sc.nextInt()-1]);
            }
            catch (RuntimeException re) {
                System.out.println(re.getMessage());
            }
        }
    }

    void performAction(String s) {
        Action act = menu.get(s);
        if(act != null) act.perform();
        else throw new RuntimeException("Error: no action with label " + s);
    }
}
