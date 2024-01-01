package conwaysGameOfLife;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;

public enum Tile {
    ALIVE, DEAD;

    /** Returns a displayable representation of this enum
     *
     * @return a JComponent
     */
    public JComponent getIcon() {
        JPanel p = new JPanel();
        int size = 75;
        p.setPreferredSize(new Dimension(size, size));

        switch (this) {
            case ALIVE -> p.setBackground(Color.WHITE);
            case DEAD -> p.setBackground(Color.BLACK);
        }

        return p;
    }

    @Override
    public String toString() {
        return switch (this) {
            case DEAD -> "dead";
            case ALIVE -> "alive";
        };
    }
}
