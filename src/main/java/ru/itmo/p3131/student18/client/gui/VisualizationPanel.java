package ru.itmo.p3131.student18.client.gui;

import ru.itmo.p3131.student18.client.gui.clientCollection.HumanBeingCollectionManager;
import ru.itmo.p3131.student18.client.gui.tools.ImageIconResized;
import ru.itmo.p3131.student18.interim.objectclasses.Coordinates;
import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class VisualizationPanel extends JPanel {
    private final Map<String, Color> ownersToColors = new HashMap<>();
    private final Color[] colors = new Color[]{Color.BLACK, Color.BLUE, Color.CYAN, Color.darkGray, Color.GREEN,
            Color.MAGENTA, Color.ORANGE};
    private int colorIndex;

    public VisualizationPanel() {
        super();
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
    }

    public void drawWorker(HumanBeing human, MainFrame mainFrame) {
        HumanBeingCollectionManager colManager = mainFrame.getColManager();
        Coordinates coordinates = human.getCoordinates();
        int x = Math.toIntExact(coordinates.getX());
        int y = Math.toIntExact(coordinates.getY());
        boolean isContact = false;
        for (HumanBeing w : colManager.getHumanBeings()) {
            if (human.equals(w)) continue;
            int x1 = Math.toIntExact(w.getCoordinates().getX());
            int y1 = Math.toIntExact(w.getCoordinates().getY());
            if ((2 * x - 50) <= x1 * 2 && x1 * 2 <= (2 * x + 50) && y1 * 1.2 >= (1.2 * y - 50) && y1 * 1.2 <= (1.2 * y + 50)) {
                isContact = true;
                break;
            }
        }
        ImageIconResized imageIcon;
        URL resource;
        if (isContact) resource = getClass().getClassLoader().getResource("images/thisIsForYou-PhotoRoom.png");
        else resource = getClass().getClassLoader().getResource("images/playful-PhotoRoom.png");
        imageIcon = new ImageIconResized(resource);
        imageIcon.setImageObserver(mainFrame);
        JLabel label = new JLabel(imageIcon.getScaledImage(imageIcon.getImage(), 50, 50));
        label.setBounds(340 + (x * 2), 140 + (int) (y * 1.2), 50, 50);
        if (!ownersToColors.containsKey(human.getUser())) {
            if (colorIndex == 6) colorIndex = 0;
            else colorIndex += 1;
            ownersToColors.put(human.getUser(), colors[colorIndex]);
        }
        Border border = BorderFactory.createLineBorder(ownersToColors.get(human.getUser()), 3);
        label.setBorder(border);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                mainFrame.getMessageLabel().setText(human.toString());
            }
        });
        add(label);
    }
}
