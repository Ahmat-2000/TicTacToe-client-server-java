package client;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

/**
 * La classe CustomJButton représente un bouton personnalisé pour le jeu Tic Tac Toe.
 * Elle hérite de JButton.
 */
public class CustomJButton extends JButton {
    /**
     * Position du bouton sur le plateau de jeu
     */
    private int position;

    /**
     * Constructeur de la classe CustomJButton.
     * @param x La position du bouton sur le plateau de jeu.
     */
    public CustomJButton(int x) {
        // Définition des propriétés visuelles du bouton
        this.setBackground(Color.LIGHT_GRAY);
        this.setForeground(Color.WHITE);
        this.setFocusPainted(false);
        this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 34));
        // Initialisation de la position du bouton
        this.position = x;
    }

    /**
     * Méthode pour obtenir la position du bouton sur le plateau de jeu.
     * @return La position du bouton.
     */
    public int getPosition() {
        return position;
    }
}
