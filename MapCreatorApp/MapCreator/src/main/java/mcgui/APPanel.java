/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mctemplates.MCSettings;

/**
 * APPanel is AlgorithmsParameters. Here we can change values of parameters in
 * particular algorithms
 *
 * @author swacisko
 */
public class APPanel extends JPanel {

    public APPanel() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLayout(new GridLayout(1, 1));
        addGlueingParametersSliders();

    }

    private void addGlueingParametersSliders() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.RED.darker(), 2, true), "Glueing parameters"));

        panel.add(new JLabel("First glueing distance parameter"), new GBC(0, 0, 2, 1).setAnchor(GBC.CENTER).setFill(GBC.BOTH).setWeight(100, 100));
        ChangeListener listener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                MCSettings.setFIRST_GLUEING_DISTANCE_PARAMETER((float) slider.getValue());
            }
        };
        JSlider slider = getJSlider(0, Math.round(3 * MCSettings.getFIRST_GLUEING_DISTANCE_PARAMETER()), (int) MCSettings.getFIRST_GLUEING_DISTANCE_PARAMETER(),
                4, listener);
        panel.add(slider, new GBC(2, 0, 6, 1).setAnchor(GBC.CENTER).setFill(GBC.BOTH).setWeight(100, 100));

        panel.add(new JLabel("Second glueing distance parameter"), new GBC(0, 1, 2, 1).setAnchor(GBC.CENTER).setFill(GBC.BOTH).setWeight(100, 100));
        listener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                MCSettings.setFIRST_GLUEING_DISTANCE_PARAMETER((float) slider.getValue());
            }
        };
        slider = getJSlider(0, Math.round(3 * MCSettings.getSECOND_GLUEING_DISTANCE_PARAMETER()), (int) MCSettings.getSECOND_GLUEING_DISTANCE_PARAMETER(),
                8, listener);
        panel.add(slider, new GBC(2, 1, 6, 1).setAnchor(GBC.CENTER).setFill(GBC.BOTH).setWeight(100, 100));

        add(panel);
    }

    private void addForceAlgorithmParametersSliders() {

    }

    /**
     * Creates JSlider object with specified parameters. Invokes methods:
     * setPaintTicks(), setPaintTrack(), setPaintLabels(),
     * setMajorTickSpacing(), setSnapToTicks()
     *
     * @param min minimal value of slider scope
     * @param max maximal value of slider scope
     * @param value initial value of the slider
     * @param listener change listener for the slider
     * @return returns constructed slider
     */
    private JSlider getJSlider(int min, int max, int value, int tickSpacing, ChangeListener listener) {
        JSlider slider = new JSlider(min, max, value);
        slider.setPaintTicks(true);
        slider.setPaintTrack(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(tickSpacing);
        slider.setMinorTickSpacing( 2 );
        slider.setSnapToTicks(true);
        slider.addChangeListener(listener);
        return slider;
    }

    private int DEFAULT_WIDTH = 100;
    private int DEFAULT_HEIGHT = 100;
}
