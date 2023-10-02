package ru.itmo.p3131.student18.client.gui;

import ru.itmo.p3131.student18.client.Client;
import ru.itmo.p3131.student18.client.gui.dialogs.RegDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.ResourceBundle;


public class LoginFrame extends JFrame {
    private final int USER_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int USER_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final Client client;
    private final JPanel mainPanel;
    private final JPanel panel;
    private final JLabel messageLabel;
    private final JComboBox<String> langBox;
    private final JLabel loginLabel;
    private final JLabel wrongLoginLabel;
    private final JLabel passwordLabel;
    private final JLabel wrongPasswordLabel;
    private final JButton loginButton;
    private final JButton registerButton;
    private RegDialog regDialog;
    private MainFrame mainFrame;

    public LoginFrame(Client client) {
        setTitle("Welcome!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        this.client = client;
        //setPreferredSize(new Dimension(USER_WIDTH / 2 - 50, USER_HEIGHT / 2 - 50));
        setMinimumSize(new Dimension(USER_WIDTH / 2 - 50, USER_HEIGHT / 2 - 50));
        setLocation(new Point(USER_WIDTH / 4 + 25, USER_HEIGHT / 4 + 25));
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        setContentPane(mainPanel);
        getContentPane().setBackground(new Color(48, 93, 104));

        Color exeptColor = new Color(78, 107 ,114);
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(exeptColor);
        //panel.setPreferredSize(new Dimension(250, 250));
        panel.setBounds(getWidth()/2 - 125, getHeight()/2 - 125, 250, 250);

        Font font = new Font("Inter", Font.PLAIN, 12);
        messageLabel = new JLabel();
        messageLabel.setFont(font);
        messageLabel.setText("abob");
        messageLabel.setForeground(exeptColor);
        loginLabel = new JLabel();
        loginLabel.setFont(font);
        loginLabel.setForeground(new Color(206, 181, 156));
        wrongLoginLabel = new JLabel();
        wrongLoginLabel.setForeground(exeptColor);
        wrongLoginLabel.setFont(font);
        passwordLabel = new JLabel();
        passwordLabel.setForeground(new Color(206, 181, 156));
        passwordLabel.setFont(font);
        wrongPasswordLabel = new JLabel();
        wrongPasswordLabel.setForeground(exeptColor);
        wrongPasswordLabel.setFont(font);
        JTextField loginField = new JTextField(14);
        loginField.setBackground(new Color(206, 177, 148));
        JPasswordField pwdField = new JPasswordField(14);
        pwdField.setBackground(new Color(206, 177, 148));
        loginButton = new JButton();
        loginButton.setBackground(new Color(122,77,33));
        loginButton.setForeground(Color.WHITE);
        registerButton = new JButton();
        registerButton.setBackground(new Color(184, 154,124));
        registerButton.setForeground(Color.WHITE);

        langBox = new JComboBox<>();
        langBox.setLocation(0,0);
        langBox.setBounds(20, 20, 100,20);
        langBox.setBackground(new Color(217,217,217));
        langBox.addItem("English");
        langBox.addItem("Русский");
        langBox.addItem("Polski");
        langBox.addItem("Eesti");

        setLocaleAndUpdateLabels("English");

        ActionListener listenerLogIn = e -> {
            messageLabel.setForeground(exeptColor);
            wrongLoginLabel.setForeground(exeptColor);
            wrongPasswordLabel.setForeground(exeptColor);
            String login = loginField.getText();
            String password = pwdField.getText();
            //if fields are empty
            String[] command = {"login", login, password};
            String[] result = client.run(command, null);
            if (!Objects.equals(result[0], "")) {
                messageLabel.setText(result[0]);
                if (result[0].equals(("\nUser has successfully logged in."))) {
                    System.out.println("success");
                    client.authorizationComplete();
                    if (mainFrame == null) mainFrame = new MainFrame(this);
                    mainFrame.setLang(langBox.getItemAt(langBox.getSelectedIndex()));
                    mainFrame.setLocaleAndUpdateLabels(langBox.getItemAt(langBox.getSelectedIndex()));
                    setVisible(false);
                    mainFrame.setVisible(true);
                }
            } else if (!Objects.equals(result[1], "")) {
                switch (result[1]) {
                    case "\nThere is no user with what login." -> wrongLoginLabel.setForeground(Color.WHITE);
                    case "\nPassword is not correct." -> wrongPasswordLabel.setForeground(Color.WHITE);
                    default -> {
                        messageLabel.setText(result[1]);
                        messageLabel.setForeground(Color.WHITE);
                    }
                }
            }
        };

        MouseAdapter adapterReg = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (regDialog == null) {
                    regDialog = new RegDialog(LoginFrame.this);
                    regDialog.setLocation(USER_WIDTH / 2 - 150, USER_HEIGHT / 2 - 100);
                }
                regDialog.setLocaleAndUpdateLabels(langBox.getItemAt(langBox.getSelectedIndex()));
                regDialog.setVisible(true);
            }
        };

        ActionListener langListener = e -> {
            setLocaleAndUpdateLabels(langBox.getItemAt(langBox.getSelectedIndex()));
            //mainFrame.setLang(langBox.getItemAt(langBox.getSelectedIndex()));
        };

        loginButton.addActionListener(listenerLogIn);
        registerButton.addMouseListener(adapterReg);
        langBox.addActionListener(langListener);

        add(langBox);
        add(panel);
        panel.add(messageLabel, new GBC(0,0,4,1));
        panel.add(loginLabel, new GBC(0, 1, 2,1).setInsets(15));
        panel.add(loginField, new GBC(3, 1, 4, 1));
        panel.add(wrongLoginLabel, new GBC(2,2, 2,1));
        panel.add(passwordLabel, new GBC(0, 3, 2,1).setInsets(15));
        panel.add(pwdField, new GBC(2, 3, 4, 1));
        panel.add(wrongPasswordLabel, new GBC(2,4, 2,1));
        panel.add(loginButton, new GBC(2, 5, 3, 1).setInsets(15));
        panel.add(registerButton, new GBC(1, 6, 4,1));
    }

    public Client getClient() {
        return client;
    }

    public void setLang(String lang) {
        langBox.setSelectedItem(lang);
    }

    public void setLocaleAndUpdateLabels(String localeName) {
        Translator.setLocale(localeName);
        ResourceBundle bundle = ResourceBundle.getBundle("LoginLabels", Translator.currentLocale());
        loginLabel.setText(bundle.getString("login"));
        wrongLoginLabel.setText(bundle.getString("wrongLogin"));
        passwordLabel.setText(bundle.getString("password"));
        wrongPasswordLabel.setText(bundle.getString("wrongPassword"));
        loginButton.setText(bundle.getString("logIn"));
        registerButton.setText(bundle.getString("register"));
    }

    public static void main(String[] args) {
        LoginFrame frame = new LoginFrame(new Client());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Welcome!");
        frame.setVisible(true);
    }
}
