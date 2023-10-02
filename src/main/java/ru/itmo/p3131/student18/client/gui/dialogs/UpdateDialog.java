package ru.itmo.p3131.student18.client.gui.dialogs;

import ru.itmo.p3131.student18.client.gui.MainFrame;
import ru.itmo.p3131.student18.client.gui.Translator;
import ru.itmo.p3131.student18.client.gui.clientCollection.HumanBeingCollectionManager;
import ru.itmo.p3131.student18.interim.commands.tools.parsers.HumanBeingBuilder;
import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;
import ru.itmo.p3131.student18.interim.objectclasses.Mood;
import ru.itmo.p3131.student18.interim.objectclasses.WeaponType;

import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateDialog extends AddDialog {
    private final MainFrame mainFrame;
    private int humanId;

    public UpdateDialog(MainFrame owner, String title, boolean modal) {
        super(owner, title, modal);
        mainFrame = owner;
        setAddButtonListener(e -> {
            try {
                HumanBeing human = new HumanBeingBuilder().create(getHumanNameField().getText(), (long) getCoordXSlider().getValue(), (long) getCoordYSlider().getValue(), getRealHero().isSelected(), getHasToothPick().isSelected(), Float.parseFloat(getImpactSpeed().getText()), WeaponType.valueOf(getWeaponTypeComboBox().getSelectedItem().toString()), Mood.valueOf(getMoodComboBox().getSelectedItem().toString()), getCar().isSelected(), owner.getClient().getUserName());
                String[] result = owner.getClient().run(new String[] {"update", String.valueOf(humanId)}, human);
                owner.getMessageLabel().setText(Objects.equals(result[0], "") ? (Objects.equals(result[1], "") ? "" : result[1]) : result[0]);
                setVisible(false);
                owner.updateTable();
            } catch (NumberFormatException ex) {
                getMessageLabel().setText(ex.getMessage());
                owner.getMessageLabel().setText(ex.getMessage());
            }
        });
    }

    public void fillFields() {
        HumanBeing human = mainFrame.getColManager().getHumanBeingByID(humanId);
        setNameText(human.getName());
        setCoordinateX(Math.toIntExact(human.getCoordinates().getX()));
        setCoordinateY((int) human.getCoordinates().getY());
        setRealHeroText(String.valueOf(human.isRealHero()));
        setHasToothPickText(String.valueOf(human.isHasToothpick()));
        setImpactSpeedText(String.valueOf(human.getImpactSpeed()));
        setWeaponTypeComboBox(human.getWeaponType());
        setMoodComboBox(human.getMood());
        setCarText(human.getCar().toString());
    }

    public void setLocaleAndUpdateLabels(String localeName) {
        ResourceBundle bundle = ResourceBundle.getBundle("MainLabels", Translator.getLocale(localeName));
        setAddButtonName(bundle.getString("update"));
        setCancelButtonName(bundle.getString("cancel"));
        setTitle(bundle.getString("update"));
    }

    public HumanBeingCollectionManager getColManager() {
        return mainFrame.getColManager();
    }

    public void setHumanId(int humanId) {
        this.humanId = humanId;
    }

    public int getHumanId() {
        return humanId;
    }
}
