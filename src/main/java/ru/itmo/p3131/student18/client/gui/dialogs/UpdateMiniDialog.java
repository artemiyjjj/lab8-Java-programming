package ru.itmo.p3131.student18.client.gui.dialogs;

import ru.itmo.p3131.student18.client.gui.GBC;
import ru.itmo.p3131.student18.client.gui.MainFrame;
import ru.itmo.p3131.student18.client.gui.Translator;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class UpdateMiniDialog extends JDialog {
    private final JTextField id;
    private final JButton ok;
    private final JButton cancel;
    private final JLabel messageLabel;
    private final JLabel enterLabel;
    private ResourceBundle bundle;

    public UpdateMiniDialog(MainFrame owner, UpdateDialog updateDialog) {
        super(owner, "ID", true);
        setSize(600, 120);
        setMinimumSize(new Dimension(600,120));
        setLayout(new GridBagLayout());
        enterLabel = new JLabel();
        add(enterLabel, new GBC(0,0,1,1));
        id = new JTextField(15);
        ok = new JButton();
        cancel = new JButton();
        messageLabel = new JLabel();
        add(id, new GBC(1,0,1,1));
        add(messageLabel, new GBC(0,1,2,1));
        add(ok, new GBC(0,2,1,1));
        add(cancel, new GBC(1,2,1,1));


        cancel.addActionListener(e -> setVisible(false));

        ok.addActionListener(e -> {
            try {
                if (updateDialog.getColManager().checkId(Integer.parseInt(id.getText()))) {
                    updateDialog.setHumanId(Integer.parseInt(id.getText()));
                    setVisible(false);
                    updateDialog.fillFields();
                    updateDialog.setVisible(true);
                } else messageLabel.setText(bundle.getString("incorrectId"));
            } catch (NumberFormatException ex) {
                messageLabel.setText(bundle.getString("incorrectId"));
            }

        });
    }

    public void setLocaleAndUpdateLabels(String localeName) {
        bundle = ResourceBundle.getBundle("MainLabels", Translator.getLocale(localeName));
        ok.setText(bundle.getString("ok"));
        cancel.setText(bundle.getString("cancel"));
        enterLabel.setText(bundle.getString("enterIdUpdate"));
    }
}
