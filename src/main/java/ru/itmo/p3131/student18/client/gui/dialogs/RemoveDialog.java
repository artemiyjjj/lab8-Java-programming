package ru.itmo.p3131.student18.client.gui.dialogs;

import ru.itmo.p3131.student18.client.gui.GBC;
import ru.itmo.p3131.student18.client.gui.MainFrame;
import ru.itmo.p3131.student18.client.gui.Translator;
import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class RemoveDialog extends JDialog {
    private ResourceBundle bundle;
    private final JLabel enterIdLabel;
    private final JButton ok;
    private final JTextField id;
    private final JButton cancel;
    private final JLabel messageLabel;

    public RemoveDialog(MainFrame owner) {
        super(owner, "Remove", true);
        setSize(600, 120);
        setMinimumSize(new Dimension(600, 120));
        setLayout(new GridBagLayout());
        enterIdLabel = new JLabel();
        add(enterIdLabel, new GBC(0,0,1,1));
        id = new JTextField(15);
        ok = new JButton("Ok");
        cancel = new JButton("Cancel");
        messageLabel = new JLabel();
        add(id, new GBC(1,0,1,1));
        add(messageLabel, new GBC(0,1,2,1));
        add(ok, new GBC(0,2,1,1));
        add(cancel, new GBC(1,2,1,1));

        cancel.addActionListener(e -> setVisible(false));

        ok.addActionListener(e -> {
            try {
                if (owner.getColManager().checkId(Integer.parseInt(id.getText()))) {
                    owner.getMessageLabel().setText(owner.getClient().run(new String[]{"remove",  id.getText()},  null)[0]);
                    setVisible(false);
                    owner.updateTable();
                } else messageLabel.setText(bundle.getString("incorrectId"));
            } catch (NumberFormatException ex) {
                messageLabel.setText((bundle.getString("incorrectId")));
            }
        });
    }
    public JButton getOk() {
        return ok;
    }
    public JButton getCancel() {
        return cancel;
    }
    public JLabel getEnterIdLabel() {
        return enterIdLabel;
    }
    public JTextField getId() {
        return id;
    }
    public JLabel getMessageLabel() {
        return messageLabel;
    }
    public ResourceBundle getBundle() {
        return bundle;
    }
    public void setLocaleAndUpdateLabels(String localeName) {
        bundle = ResourceBundle.getBundle("MainLabels", Translator.getLocale(localeName));
        enterIdLabel.setText(bundle.getString("enterIdRemove"));
        ok.setText(bundle.getString("ok"));
        cancel.setText(bundle.getString("cancel"));
        setTitle(bundle.getString("remove"));
    }
}
