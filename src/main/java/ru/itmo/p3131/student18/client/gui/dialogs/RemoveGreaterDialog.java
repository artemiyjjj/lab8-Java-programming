package ru.itmo.p3131.student18.client.gui.dialogs;

import ru.itmo.p3131.student18.client.gui.MainFrame;
import ru.itmo.p3131.student18.client.gui.Translator;

import java.util.ResourceBundle;

public class RemoveGreaterDialog extends RemoveDialog {
    private int humanId;

    public RemoveGreaterDialog(MainFrame owner, String title, boolean modal) {
        super(owner);
        setTitle("Remove greater");
        getCancel().addActionListener(e -> setVisible(false));

        getOk().addActionListener(e -> {
            try {
                if (owner.getColManager().checkId(Integer.parseInt(getId().getText()))) {
                    owner.getMessageLabel().setText(owner.getClient().run(new String[]{"remove_greater",  getId().getText()},null)[0]);
                    setVisible(false);
                    owner.updateTable();
                } else getMessageLabel().setText(getBundle().getString("incorrectId"));
            } catch (NumberFormatException ex) {
                getMessageLabel().setText((getBundle().getString("incorrectId")));
            }
        });
    }

    public void setHumanId(int id) {
        this.humanId = id;
    }
    public void setLocaleAndUpdateLabels(String localeName) {
        ResourceBundle bundle = ResourceBundle.getBundle("MainLabels", Translator.getLocale(localeName));
        getOk().setText(bundle.getString("removeGreater"));
        getEnterIdLabel().setText(bundle.getString("enterIdRemove"));
        getCancel().setText(bundle.getString("cancel"));
        setTitle(bundle.getString("removeLower"));
    }
}
