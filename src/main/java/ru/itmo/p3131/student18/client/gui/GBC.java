package ru.itmo.p3131.student18.client.gui;

import java.awt.*;

public class GBC extends GridBagConstraints {

    public GBC(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
    }

    public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }

    public GBC setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    public GBC setInsets(int dist) {
        this.insets = new Insets(dist,dist,dist,dist);
        return this;
    }
}
