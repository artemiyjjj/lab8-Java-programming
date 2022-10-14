package ru.itmo.p3131.student18.client.gui.dialogs;

import ru.itmo.p3131.student18.client.gui.MainFrame;
import ru.itmo.p3131.student18.client.gui.Translator;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class ClearDialog extends JDialog {
    private final JLabel sureLabel;
    private final JButton yes;
    private final JButton no;

    public ClearDialog(MainFrame owner) {
        super(owner, "Clear", true);
        setSize(300, 100);
        setMinimumSize(new Dimension(300, 100));
        setLayout(new FlowLayout());
        sureLabel = new JLabel();
        add(sureLabel);
        yes = new JButton();
        no = new JButton();
        add(yes);
        add(no);
        no.addActionListener(e -> setVisible(false));
        yes.addActionListener(e -> {
        owner.getMessageLabel().setText(owner.getClient().run(new String[] {"clear"}, null)[0]);
        setVisible(false);
        owner.updateTable();
        });
    }

    public void setLocaleAndUpdateLabels(String localeName) {
        ResourceBundle bundle = ResourceBundle.getBundle("MainLabels", Translator.getLocale(localeName));
        sureLabel.setText(bundle.getString("sure"));
        yes.setText(bundle.getString("yes"));
        no.setText(bundle.getString("no"));
        setTitle(bundle.getString("clear"));
    }
}
