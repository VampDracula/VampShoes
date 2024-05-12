package fr.vamp.shoes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.awt.*;
import java.sql.SQLException;

public class ConnectDBPanel extends JPanel {

    ConfigHandler configHandler = null;
    DatabaseHandler databaseHandler = null;

    JPanel inputsPanel = new JPanel();

    JLabel errorLabel = new JLabel("");
    JTextField hostnameTextField = new JTextField();
    JTextField dbnameTextField = new JTextField();
    JTextField usernameTextField = new JTextField();
    JPasswordField passwordTextField = new JPasswordField();

    JButton connectButton = new JButton("Se connecter");

    final String html = "<html><body style='width: %1spx'>%1s";

    public ConnectDBPanel() {

        configHandler = Shoes.getInstance().getConfigHandler();
        databaseHandler = Shoes.getInstance().getDatabaseHandler();

        hostnameTextField.setText(configHandler.getHostname());
        dbnameTextField.setText(configHandler.getDbname());
        usernameTextField.setText(configHandler.getUsername());

        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == connectButton) {
                    errorLabel.setVisible(false);
                    inputsPanel.setEnabled(false);
                    String hostname = hostnameTextField.getText();
                    String dbname = dbnameTextField.getText();
                    String username = usernameTextField.getText();
                    String password = passwordTextField.getText();
                    try {
                        configHandler.setHostname(hostname);
                        configHandler.setDbname(dbname);
                        configHandler.setUsername(username);
                        databaseHandler.connectToDb(
                                hostname,
                                dbname,
                                username,
                                password
                        );
                        if(databaseHandler.isConnected()) {
                            Shoes.getInstance().changePanel(Panel.MainPanel);
                        }
                    } catch( SQLException e) {
                        errorLabel.setText(String.format(html, 250, e.getMessage()));
                        errorLabel.setVisible(true);
                        inputsPanel.setEnabled(true);
                    }
                }
            }
        };

        this.setBackground(new Color(220, 221, 225,255));
        this.setLayout(new FlowLayout());

        JPanel connectPanel = new JPanel();
        connectPanel.setLayout(new BorderLayout());
        connectPanel.setBackground(new Color(245, 246, 250,255));
        connectPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        connectPanel.setPreferredSize(new Dimension(400, 400));

        inputsPanel.setLayout(new GridLayout(9, 1));
        inputsPanel.setBackground(new Color(245, 246, 250,255));

        inputsPanel.add(new JLabel("Hôte"));
        inputsPanel.add(hostnameTextField);

        inputsPanel.add(new JLabel("Base de donnée"));
        inputsPanel.add(dbnameTextField);

        inputsPanel.add(new JLabel("Nom d'utilisateur"));
        inputsPanel.add(usernameTextField);

        inputsPanel.add(new JLabel("Mot de passe"));
        inputsPanel.add(passwordTextField);

        connectButton.addActionListener(al);
        inputsPanel.add(connectButton);

        connectPanel.add(inputsPanel, BorderLayout.NORTH);



        errorLabel.setForeground(Color.BLACK);
        errorLabel.setVisible(false);
        connectPanel.add(errorLabel, BorderLayout.SOUTH);

        this.add(connectPanel);
    }

}
