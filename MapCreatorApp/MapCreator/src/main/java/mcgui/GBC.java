/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * This class represents the GridBagContraints - it is faster and easier to use
 * it than GridBagContraints
 *
 * @author swacisko
 */
public class GBC extends GridBagConstraints {

    public GBC(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
    }

    /**
     * Creates GridBagContrants object with given parameters and other set to their default values
     * @param gridx gridx coordinate - of the left upper corner
     * @param gridy gridy coordinate - of the left upper corner
     * @param gridwidth number of cells in width
     * @param gridheight number of cells in height
     * @return returns this GBC to enable chain function calls ( new GBC(...).setAnchor(...).setFill(...).somethingElse)
     */
    public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridheight = gridheight;
        this.gridwidth = gridwidth;
    }

    public GBC setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    public GBC setFill(int fill) {
        this.fill = fill;
        return this;
    }

    public GBC setWeight(double weightx, double weighty) {
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    public GBC setInsets(int distance) {
        this.insets = new Insets(distance, distance, distance, distance);
        return this;
    }

    public GBC setInsets(int top, int left, int bottom, int right) {
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

    public GBC setIpad(int ipadx, int ipady) {
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }

}
