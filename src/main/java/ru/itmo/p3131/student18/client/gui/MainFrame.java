package ru.itmo.p3131.student18.client.gui;

import ru.itmo.p3131.student18.client.Client;
import ru.itmo.p3131.student18.client.gui.clientCollection.HumanBeingCollection;
import ru.itmo.p3131.student18.client.gui.clientCollection.HumanBeingCollectionManager;
import ru.itmo.p3131.student18.client.gui.dialogs.*;
import ru.itmo.p3131.student18.client.gui.tools.ImageIconResized;
import ru.itmo.p3131.student18.interim.commands.tools.parsers.HumanBeingBuilder;
import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class MainFrame extends JFrame {
    private final int USER_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int USER_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final Client client;
    private HumanBeingCollectionManager colManager;
    private RemoveDialog removeDialog;
    private AddDialog addDialog;
    private UpdateDialog updateDialog;
    private ClearDialog clearDialog;
    private UpdateMiniDialog updateMiniDialog;
    private RemoveGreaterDialog removeGreaterDialog;
    private final JTabbedPane tabbedPane;
    private JScrollPane scrollPane;
    private JTable table;
    private final JLabel userLabel;
    private final JLabel messageLabel;
    private final JLabel infoLabel;
    private final List<String> infoList;
    private final JButton logOutButton;
    private final JButton addButton;
    private final JButton updateButton;
    private final JButton removeButton;
    private final JButton clearButton;
    private final JButton removeFirstButton;
    private final JButton removeLastButton;
    private final JButton removeGreaterButton;
    private final JComboBox<String> langBox;
    //private final JFileChooser fileChooser;
    private VisualizationPanel visPanel;

    public MainFrame(LoginFrame parent) {
        this.client = parent.getClient();
        setTitle("App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(USER_WIDTH / 4 * 3, USER_HEIGHT / 4 * 3);
        setLocation(new Point(USER_WIDTH / 8, USER_HEIGHT / 8));
        setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(new Color(78,107,114));
        //init

        HumanBeingCollection collection = new HumanBeingCollection();
        String show = client.run(new String[]{"show"}, null)[0];
        Scanner scanner = new Scanner(show);
        if (scanner.hasNextLine()) {
            scanner.nextLine();
            String newLine;
            while (scanner.hasNextLine()) {
                newLine = scanner.nextLine();
                if (!newLine.equals("")) {
                    collection.add(new HumanBeingBuilder().create(newLine.split(",")));
                }
            }
        }


        //table

        colManager = new HumanBeingCollectionManager(collection.sortedById());
        HumanBeingTableModel tableModel = new HumanBeingTableModel(colManager);
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        scrollPane = new JScrollPane(table);


        //visualization field

        visPanel = new VisualizationPanel();
        for (HumanBeing human: colManager.getHumanBeings()) {
            visPanel.drawWorker(human, this);
        }

        //

        tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        tabbedPane.addTab("Table", scrollPane);
        tabbedPane.addTab("Visualization", visPanel);
        tabbedPane.setBackground(new Color(104,151,162));
        add(tabbedPane, BorderLayout.CENTER);

        //--------------------

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridBagLayout());
        southPanel.setBackground(new Color(21,65,75));
        logOutButton = new JButton("Log out");
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        removeButton = new JButton("Remove");
        clearButton = new JButton("Clear");
        removeFirstButton = new JButton("Remove First");
        removeLastButton = new JButton("Remove Last");
        removeGreaterButton = new JButton("Remove Greater");
        messageLabel = new JLabel("Last command info");
        messageLabel.setForeground(Color.white);

        southPanel.add(messageLabel, new GBC(1, 0, 6, 1).setInsets(5));
        southPanel.add(addButton, new GBC(0, 1));
        southPanel.add(updateButton, new GBC(1, 1));
        southPanel.add(removeButton, new GBC(2, 1));
        southPanel.add(clearButton, new GBC(3, 1));
        southPanel.add(removeFirstButton, new GBC(4, 1));
        southPanel.add(removeGreaterButton, new GBC(5, 1));
        southPanel.add(removeLastButton, new GBC(6, 1));

        //-----------------------

        JPanel northPanel = new JPanel();
        northPanel.setPreferredSize(new Dimension(getWidth(),80));
        northPanel.setLayout(new BorderLayout());
        northPanel.setBackground(new Color(21,65,75));
        langBox = new JComboBox<>();
        langBox.addItem("English");
        langBox.addItem("Русский");
        langBox.addItem("Polski");
        langBox.addItem("Eesti");
        langBox.setPreferredSize(new Dimension(100,20));

        infoLabel = new JLabel();
        infoLabel.setForeground(Color.white);

        infoList = new ArrayList<>();
        Scanner sc = new Scanner(client.run(new String[]{"info"}, null)[0]);
        String next;
        while (sc.hasNextLine()) {

            next = sc.nextLine();
            System.out.println(next);
            if (!next.equals("")) {
                infoList.add(next);
            }
        }

        northPanel.add(infoLabel, BorderLayout.CENTER);
        JPanel tmpPanelWest = new JPanel();
        tmpPanelWest.setOpaque(false);
        tmpPanelWest.setLayout(new BorderLayout());
        tmpPanelWest.setBorder(BorderFactory.createEmptyBorder(20,10,10,20));
        tmpPanelWest.add(langBox, BorderLayout.NORTH);

        JPanel tmpPanelEast = new JPanel();
        tmpPanelEast.setLayout(new BoxLayout(tmpPanelEast, BoxLayout.Y_AXIS));
        tmpPanelEast.setOpaque(false);
        tmpPanelEast.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        tmpPanelEast.setPreferredSize(new Dimension(150, 80));

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
        userPanel.setPreferredSize(new Dimension(90, 35));
        userPanel.setBackground(new Color(104,151,162));

        ImageIconResized icon = new ImageIconResized(getClass().getClassLoader().getResource("images/smiling-PhotoRoom.png")); // load the image to a imageIcon

        JLabel userIcon = new JLabel();
        userIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        userIcon.setIcon(icon.getScaledImage(icon.getImage(), 35, 35));
        userLabel = new JLabel();
        userLabel.setForeground(Color.white);
        logOutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        userPanel.add(userIcon);
        userPanel.add(Box.createRigidArea(new Dimension(30,5)));
        userPanel.add(userLabel);
        tmpPanelEast.add(userPanel);
        tmpPanelEast.add(Box.createRigidArea(new Dimension(0,5)));
        tmpPanelEast.add(logOutButton);

        northPanel.add(tmpPanelWest, BorderLayout.WEST);
        northPanel.add(tmpPanelEast, BorderLayout.EAST);

        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        //------------------

        ActionListener logOut = e -> {
            setVisible(false);
            parent.setLang(langBox.getItemAt(langBox.getSelectedIndex()));
            parent.setLocaleAndUpdateLabels(langBox.getItemAt(langBox.getSelectedIndex()));
            parent.setVisible(true);
        };
        logOutButton.addActionListener(logOut);

        ActionListener add = e -> {
            if (addDialog == null) addDialog = new AddDialog(this, "Add", true);
            addDialog.setLocation(USER_WIDTH / 2 - addDialog.getWidth() / 2,
                    USER_HEIGHT / 2 - addDialog.getHeight() / 2);
            addDialog.setLocaleAndUpdateLabels(langBox.getItemAt(langBox.getSelectedIndex()));
            addDialog.setVisible(true);
        };
        addButton.addActionListener(add);

        ActionListener update = e -> {
            if (updateDialog == null) {

                updateDialog = new UpdateDialog(this, "Update", true);
                updateDialog.setLocation(USER_WIDTH / 2 - updateDialog.getWidth() / 2,
                        USER_HEIGHT / 2 - updateDialog.getHeight() / 2);
            }
            if (updateMiniDialog == null) updateMiniDialog = new UpdateMiniDialog(this, updateDialog);
            updateMiniDialog.setLocation(USER_WIDTH / 2 - updateMiniDialog.getWidth() / 2,
                    USER_HEIGHT / 2 - updateMiniDialog.getHeight() / 2);
            updateDialog.setLocaleAndUpdateLabels(langBox.getItemAt(langBox.getSelectedIndex()));
            updateMiniDialog.setLocaleAndUpdateLabels(langBox.getItemAt(langBox.getSelectedIndex()));
            updateMiniDialog.setVisible(true);
            if (updateDialog.getHumanId() != 0) updateDialog.fillFields();
        };
        updateButton.addActionListener(update);

        ActionListener remove = e -> {
            if (removeDialog == null) {
                removeDialog = new RemoveDialog(this);
                removeDialog.setLocation(USER_WIDTH / 2 - removeDialog.getWidth() / 2,
                        USER_HEIGHT / 2 - removeDialog.getHeight() / 2);
            }
            removeDialog.setLocaleAndUpdateLabels(langBox.getItemAt(langBox.getSelectedIndex()));
            removeDialog.setVisible(true);
        };
        removeButton.addActionListener(remove);

        ActionListener removeLast = e -> {
            getClient().run(new String[] {"remove_last"}, null);
        };
        removeLastButton.addActionListener(removeLast);

        ActionListener removeFirst = e -> {
            getClient().run(new String[]{"remove_first"}, null);
        };
        removeFirstButton.addActionListener(removeFirst);
        
        ActionListener clear = e -> {
            if (clearDialog == null) {
                clearDialog = new ClearDialog(this);
                clearDialog.setLocation(USER_WIDTH / 2 - clearDialog.getWidth() / 2,
                        USER_HEIGHT / 2 - clearDialog.getHeight() / 2);
            }
            clearDialog.setLocaleAndUpdateLabels(langBox.getItemAt(langBox.getSelectedIndex()));
            clearDialog.setVisible(true);
        };
        clearButton.addActionListener(clear);

        ActionListener removeGreater = e -> {
            if (removeGreaterDialog == null) {
                removeGreaterDialog = new RemoveGreaterDialog(this, "Remove greater", true);
                removeGreaterDialog.setLocation(USER_WIDTH / 2 - removeGreaterDialog.getWidth() / 2,
                        USER_HEIGHT / 2 - removeGreaterDialog.getHeight() / 2);
            }
            removeGreaterDialog.setLocaleAndUpdateLabels(langBox.getItemAt(langBox.getSelectedIndex()));
            removeGreaterDialog.setVisible(true);
        };
        removeGreaterButton.addActionListener(removeGreater);


        ActionListener langListener = e -> setLocaleAndUpdateLabels(langBox.getItemAt(langBox.getSelectedIndex()));
        langBox.addActionListener(langListener);

        setLocaleAndUpdateLabels(langBox.getItemAt(langBox.getSelectedIndex()));

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    updateTable();
                } catch (Exception ex) {
                    messageLabel.setText("Something is wrong with server");
                }
            }
        }, 1000, 4000);
    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }

    public Client getClient() {
        return client;
    }

    public HumanBeingCollectionManager getColManager() {
        return colManager;
    }

    public synchronized void updateTable() {
        int selectedTab = tabbedPane.getSelectedIndex();
        HumanBeingCollection collection = new HumanBeingCollection();
        String show = client.run(new String[]{"show"}, null)[0];
        Scanner scanner = new Scanner(show);
        if (scanner.hasNextLine()) {
            scanner.nextLine();
            String newLine;
            while (scanner.hasNextLine()) {
                newLine = scanner.nextLine();
                if (!newLine.equals("")) {
                    collection.add(new HumanBeingBuilder().create(newLine.split(",")));
                }
            }
        }
        colManager = new HumanBeingCollectionManager(collection.sortedById());
        HumanBeingTableModel tableModel = new HumanBeingTableModel(colManager);
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);

        scrollPane = new JScrollPane(table);

        visPanel = new VisualizationPanel();
        for (HumanBeing human: colManager.getHumanBeings()) {
            visPanel.drawWorker(human, this);
        }

        tabbedPane.removeAll();
        tabbedPane.addTab("Table", scrollPane);
        tabbedPane.addTab("Visualization", visPanel);
        tabbedPane.setSelectedIndex(selectedTab);
        setLocaleAndUpdateLabels(langBox.getItemAt(langBox.getSelectedIndex()));
        revalidate();
    }

    public void setLang(String lang) {
        langBox.setSelectedItem(lang);
    }

    public void setLocaleAndUpdateLabels(String localeName) {
        Translator.setLocale(localeName);
        ResourceBundle bundle = ResourceBundle.getBundle("MainLabels", Translator.currentLocale());
        tabbedPane.setTitleAt(0, bundle.getString("table"));
        tabbedPane.setTitleAt(1, bundle.getString("visual"));
        infoLabel.setText("<html><b>" + bundle.getString("colType") + "</b> " + infoList.get(0) + "<br><b>" +
                bundle.getString("initDate") + "</b> " + infoList.get(1));
        userLabel.setText("<html><b>" + bundle.getString("user") + "</b> " + client.getUserName() + "</html>");
        logOutButton.setText(bundle.getString("logOut"));
        addButton.setText(bundle.getString("add"));
        updateButton.setText(bundle.getString("update"));
        removeButton.setText(bundle.getString("remove"));
        clearButton.setText(bundle.getString("clear"));
        removeGreaterButton.setText(bundle.getString("removeGreater"));
    }

}
