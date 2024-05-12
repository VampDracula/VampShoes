package fr.vamp.shoes;


import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Shoes extends JFrame {

    private static Shoes instance;

    HashMap<Panel, JPanel> panels = new HashMap<Panel, JPanel>();

//    private ConnectDBPanel connectDBPanel;
//    private MainPanel mainPanel;

    private DatabaseHandler databaseHandler;
    private ConfigHandler configHandler;



    public Shoes() {
        configHandler = new ConfigHandler("config.xml");
        databaseHandler = new DatabaseHandler();
    }
    public void startWindow() {
        this.setTitle("Shoes");
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setBackground(new Color(255,255,255,255));
        this.setVisible(true);
        this.setResizable(false);

        panels.put(Panel.ConnectDB, new ConnectDBPanel());
        panels.put(Panel.MainPanel, new MainPanel());

        changePanel(Panel.ConnectDB);
    }

    public static void main(String[] args) {
        instance = new Shoes();
        instance.startWindow();
    }

    public void changePanel(Panel panel) {
        this.setContentPane(panels.get(panel));
        this.setVisible(true);
        this.repaint();
        this.revalidate();
    }

    public static Shoes getInstance() {
        return instance;
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }
}