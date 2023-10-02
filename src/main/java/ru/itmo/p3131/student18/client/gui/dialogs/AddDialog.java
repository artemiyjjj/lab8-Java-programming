package ru.itmo.p3131.student18.client.gui.dialogs;

import ru.itmo.p3131.student18.client.gui.GBC;
import ru.itmo.p3131.student18.client.gui.MainFrame;
import ru.itmo.p3131.student18.client.gui.Translator;
import ru.itmo.p3131.student18.interim.commands.tools.parsers.HumanBeingBuilder;
import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;
import ru.itmo.p3131.student18.interim.objectclasses.Mood;
import ru.itmo.p3131.student18.interim.objectclasses.WeaponType;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddDialog extends JDialog {
    private final JLabel messageLabel;
    private final JTextField name;
    private final JSlider coordXSlider;
    private final JSlider coordYSlider;
    private final JCheckBox realHero;
    private final JCheckBox hasToothPick;
    private final JTextField impactSpeed;
    private final JComboBox<WeaponType> weaponTypeComboBox;
    private final JComboBox<Mood> moodComboBox;
    private final JCheckBox car;
    private final JButton addButton;
    private final JButton cancelButton;
    private ActionListener addButtonListener;

    public AddDialog(MainFrame owner, String title, boolean modal) {
        super(owner, title, modal);
        setSize(new Dimension(300, 700));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);
        mainPanel.setBackground(new Color(184, 154, 124));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        name = new JTextField(15);
        coordXSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        coordXSlider.setPaintTicks(true);
        coordXSlider.setMajorTickSpacing(20);
        coordXSlider.setMinorTickSpacing(10);
        coordYSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        coordYSlider.setPaintTicks(true);
        coordYSlider.setMajorTickSpacing(20);
        coordYSlider.setMinorTickSpacing(10);
        realHero = new JCheckBox();
        hasToothPick = new JCheckBox();
        impactSpeed = new JTextField(15);
        car = new JCheckBox();
        weaponTypeComboBox = new JComboBox<>(WeaponType.values());
        moodComboBox = new JComboBox<>(Mood.values());
        messageLabel = new JLabel();
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");

        addPanelWith2(panel, new JLabel("Name"), name);
        addPanelWith2(panel, new JLabel("coordinate X"), coordXSlider);
        addPanelWith2(panel, new JLabel("coordinate Y"), coordYSlider);
        addPanelWith2(panel, new JLabel("real hero"), realHero);
        addPanelWith2(panel, new JLabel("Has tooth pick"), hasToothPick);
        addPanelWith2(panel, new JLabel("Impact speed"), impactSpeed);
        addPanelWith2(panel, new JLabel("Weapon type"), weaponTypeComboBox);
        addPanelWith2(panel, new JLabel("Mood"), moodComboBox);
        addPanelWith2(panel, new JLabel("Has cool car?"), car);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(cancelButton);

        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        //--------------

        ChangeListener listener = event -> {
            JSlider slider = (JSlider) event.getSource();
            messageLabel.setText(Integer.toString(slider.getValue()));
        };
        coordXSlider.addChangeListener(listener);
        coordYSlider.addChangeListener(listener);
        cancelButton.addActionListener(e -> setVisible(false));
        addButtonListener = e -> {
            try {
                HumanBeing human = new HumanBeingBuilder().create(name.getText(), (long) coordXSlider.getValue(), (long) coordYSlider.getValue(), realHero.isSelected(), hasToothPick.isSelected(), Float.parseFloat(impactSpeed.getText()), WeaponType.valueOf(weaponTypeComboBox.getSelectedItem().toString()), Mood.valueOf(moodComboBox.getSelectedItem().toString()), car.isSelected(), owner.getClient().getUserName());
                owner.getMessageLabel().setText(owner.getClient().run(new String[]{"add"}, human)[0]);
                setVisible(false);
                owner.updateTable();
            } catch (NumberFormatException ex) {
                messageLabel.setText(ex.getMessage());
                owner.getMessageLabel().setText(ex.getMessage());
            }
        };
        addButton.addActionListener(addButtonListener);
    }

    public void setLocaleAndUpdateLabels(String localeName) {
        ResourceBundle bundle = ResourceBundle.getBundle("MainLabels", Translator.getLocale(localeName));
        addButton.setText(bundle.getString("add"));
        cancelButton.setText(bundle.getString("cancel"));
        setTitle(bundle.getString("add"));
    }

    private void addPanelWith2(JPanel owner, JComponent component1, JComponent component2) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        panel.add(component1);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(component2);
        owner.add(panel);
    }
    public void setAddButtonName(String name) {
        addButton.setText(name);
    }

    public void setCancelButtonName(String name) {
        cancelButton.setText(name);
    }

    public void setAddButtonListener(ActionListener listener) {
        addButton.removeActionListener(addButtonListener);
        addButton.addActionListener(listener);
        addButtonListener = listener;
    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }

    public JTextField getHumanNameField() {
        return name;
    }

    public JSlider getCoordXSlider() {
        return coordXSlider;
    }

    public JSlider getCoordYSlider() {
        return coordYSlider;
    }

    public JCheckBox getRealHero() {
        return realHero;
    }

    public JCheckBox getHasToothPick() {
        return hasToothPick;
    }

    public JTextField getImpactSpeed() {
        return impactSpeed;
    }

    public JComboBox<WeaponType> getWeaponTypeComboBox() {
        return weaponTypeComboBox;
    }

    public JComboBox<Mood> getMoodComboBox() {
        return moodComboBox;
    }

    public JCheckBox getCar() {
        return car;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }


    public void setNameText(String text) {
        name.setText(text);
    }

    public void setRealHeroText(String text) {
        realHero.setText(text);
    }

    public void setHasToothPickText(String text) { hasToothPick.setText(text);}

    public void setImpactSpeedText(String text) { impactSpeed.setText(text);}

    public void setCarText(String text) { car.setText(text);}

    public void setCoordinateX(int value) {
        coordXSlider.setValue(value);
    }

    public void setCoordinateY(int value) {
        coordYSlider.setValue(value);
    }

    public void setWeaponTypeComboBox(WeaponType weaponType) {
        weaponTypeComboBox.setSelectedItem(weaponType);
    }

    public void setMoodComboBox(Mood mood) {
        moodComboBox.setSelectedItem(mood);
    }


}
