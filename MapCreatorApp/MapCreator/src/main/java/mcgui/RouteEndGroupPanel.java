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
import mcmapdrawing.RouteEndGroup;
import mctemplates.MCSettings;
import mctemplates.Pair;

/**
 *
 * @author swacisko
 */
public class RouteEndGroupPanel extends JPanel implements ChangeListener {

    public RouteEndGroupPanel(RouteEndGroup group, JPanel parentPanel) {
        this.group = group;
        this.parentPanel = parentPanel;

        setLayout(new GridBagLayout());
        String title = "Routes:";
        for (String s : group.getRouteIds()) {
            title += " " + s;
        }
        setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.BLUE, 3), title));
        addAllComponents();
    }
    
    private void setSliderParameters( JSlider slider ){
        slider.setPaintLabels(true);
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20); 
    }

    private void addAllComponents() {
        offsetXslider = new JSlider(-Math.abs(MCSettings.getMAX_TEXT_OFFSET().getST()), Math.abs(MCSettings.getMAX_TEXT_OFFSET().getND()),
                group.getOffset().getST());
        offsetXslider.addChangeListener(this);
        setSliderParameters(offsetXslider);

        offsetYslider = new JSlider(-Math.abs(MCSettings.getMAX_TEXT_OFFSET().getST()), Math.abs(MCSettings.getMAX_TEXT_OFFSET().getND()),
                group.getOffset().getND());
        offsetYslider.addChangeListener(this);
        setSliderParameters(offsetYslider);

        JLabel offsetXlabel = new JLabel("Offset X:");
        JLabel offsetYlabel = new JLabel("Offset Y:");

        add(offsetXlabel, new GBC(0, 0, 2, 1).setFill(GBC.BOTH).setAnchor(GBC.EAST).setWeight(100, 100));
        add(offsetXslider, new GBC(2, 0, 4, 1).setFill(GBC.BOTH).setAnchor(GBC.EAST).setWeight(100, 100));

        add(offsetYlabel, new GBC(0, 1, 2, 1).setFill(GBC.BOTH).setAnchor(GBC.EAST).setWeight(100, 100));
        add(offsetYslider, new GBC(2, 1, 4, 1).setFill(GBC.BOTH).setAnchor(GBC.EAST).setWeight(100, 100));
    }

    private JSlider offsetXslider = null;
    private JSlider offsetYslider = null;

    private RouteEndGroup group = null;

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        Pair<Integer, Integer> offset = group.getOffset();
        if (slider == offsetXslider) {
            group.setOffset(new Pair<>(slider.getValue(), offset.getND()));

        } else if (slider == offsetYslider) {
            group.setOffset(new Pair<>(offset.getST(), slider.getValue()));
        }
        parentPanel.repaint();
    }
    
    private JPanel parentPanel = null;
}
