package ru.itmo.p3131.student18.client.gui.dialogs;

import ru.itmo.p3131.student18.client.Client;
import ru.itmo.p3131.student18.client.gui.LoginFrame;
import ru.itmo.p3131.student18.client.gui.Translator;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegDialog extends JDialog {
    private final JLabel enterLoginLabel;
    private final JLabel enterPasswordLabel;
    private final JLabel enterPasswordAgain;
    private final JButton register;
    private final JButton cancel;

    public RegDialog(LoginFrame owner) {
        super(owner, "Registration", true);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        setSize(300,200);
        setMinimumSize(new Dimension(300,220));

        enterLoginLabel = new JLabel();
        enterLoginLabel.setAlignmentX(Component.BOTTOM_ALIGNMENT);
        panel.add(enterLoginLabel);
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        JTextField login = new JTextField(14);
        panel.add(login);
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        enterPasswordLabel = new JLabel();
        enterPasswordLabel.setAlignmentX(Component.BOTTOM_ALIGNMENT);
        panel.add(enterPasswordLabel);
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        JPasswordField password = new JPasswordField(14);
        panel.add(password);
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        enterPasswordAgain = new JLabel();
        //enterPasswordAgain.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(enterPasswordAgain);
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        JPasswordField passwordAgain = new JPasswordField(14);
        panel.add(passwordAgain);
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        register = new JButton();
        buttonPane.add(register);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        cancel = new JButton();
        buttonPane.add(cancel);
        panel.add(buttonPane);
        getContentPane().add(panel);

        cancel.addActionListener(e -> setVisible(false));

        register.addActionListener(e -> {
            String newLogin = login.getText();
            String newPassword = password.getText();
            String newPasswordAgain = passwordAgain.getText();
            String[] command = {"register", newLogin, newPassword, newPasswordAgain };
            Client client = owner.getClient();

            String[] result = client.run(command, null);
            if (!Objects.equals(result[0], null)) {
                if (result[0].equals("\nUser has successfully registered.")) {
                    setVisible(false);
                }
            }
            else if (!Objects.equals(result[1], null)) {
                panel.setBackground(Color.RED);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {}
                panel.setBackground(Color.white);
            }
        });
    }

    public void setLocaleAndUpdateLabels(String localeName) {
        ResourceBundle bundle = ResourceBundle.getBundle("RegDialogLabels", Translator.getLocale(localeName));
        enterLoginLabel.setText(bundle.getString("enterLogin"));
        enterPasswordLabel.setText(bundle.getString("enterPassword"));
        enterPasswordAgain.setText(bundle.getString("enterPasswordAgain"));
        register.setText(bundle.getString("register"));
        cancel.setText(bundle.getString("cancel"));
    }
}
