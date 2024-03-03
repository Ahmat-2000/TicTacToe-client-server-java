import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class CustomJButton extends JButton {
    private int position ;

    public CustomJButton(int x) {
        this.setBackground(Color.LIGHT_GRAY);
        this.setForeground(Color.WHITE);
        this.setFocusPainted(false);
        this.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 34));
        this.position = x;
    }
    public int getPosition() {
        return position;
    }
}
