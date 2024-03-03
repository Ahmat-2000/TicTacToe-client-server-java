package server;

/**
 * La classe Plateau représente le plateau de jeu pour Tic Tac Toe.
 */
public class Plateau {
    // Grille représentant l'état du plateau de jeu
    private int[] grille;
    // Identifiants des joueurs et du joueur courant
    private int player1ID, player2ID, currentPlayerID;
    // Nombre de coups joués
    private int countMove;

    /**
     * Constructeur de la classe Plateau.
     * @param player1ID L'identifiant du premier joueur.
     * @param player2ID L'identifiant du deuxième joueur.
     * @param currentPlayerID L'identifiant du joueur courant.
     */
    public Plateau(int player1ID, int player2ID, int currentPlayerID) {
        this.grille = new int[9];
        this.initiateGrid();
        this.player1ID = player1ID;
        this.currentPlayerID = currentPlayerID;
        this.player2ID = player2ID;
        countMove = 0;
    }

    /**
     * Méthode pour obtenir l'élément à la position donnée dans la grille.
     * @param x La position dans la grille.
     * @return La valeur de la case.
     */
    public int getElement(int x) {
        return grille[x];
    }

    /**
     * Méthode pour vérifier si un coup est valide à la position donnée.
     * @param x La position du coup.
     * @return true si le coup est valide, sinon false.
     */
    public boolean validMove(int x) {
        return grille[x] == 0;
    }

    /**
     * Méthode pour initialiser la grille de jeu.
     */
    public void initiateGrid() {
        for (int i = 0; i < grille.length; i++) {
            grille[i] = 0;
        }
    }

    /**
     * Méthode pour obtenir l'identifiant du joueur courant.
     * @return L'identifiant du joueur courant.
     */
    public int getCurrentPlayer() {
        return this.currentPlayerID;
    }

    /**
     * Méthode pour jouer un coup sur la grille.
     * @param x La position où jouer le coup.
     */
    public void play(int x) {
        this.grille[x] = this.currentPlayerID;
        countMove++;
    }

    /**
     * Méthode pour changer de joueur après un coup.
     */
    public void changePlayer() {
        if (this.currentPlayerID == this.player1ID) {
            this.currentPlayerID = this.player2ID;
        } else {
            this.currentPlayerID = this.player1ID;
        }
    }

    /**
     * Méthode pour vérifier si le jeu est terminé.
     * @return true si le jeu est terminé, sinon false.
     */
    public boolean isOver() {
        return countMove == 9;
    }

    /**
     * Méthode pour déterminer le gagnant du jeu.
     * @return L'identifiant du joueur gagnant, 0 pour match nul, -1 si aucun gagnant.
     */
    public int getWinner() {
        // Vérification des lignes, colonnes et diagonales
        if (countMove >= 5) {
            for (int i = 0; i <= 6; i += 3) {
                if (this.grille[i] == this.currentPlayerID &&
                    this.grille[i] == this.grille[i + 1] &&
                    this.grille[i] == this.grille[i + 2]) {
                    return this.currentPlayerID;
                }
            }
            for (int i = 0; i <= 2; i++) {
                if (this.grille[i] == this.currentPlayerID &&
                    this.grille[i] == this.grille[i + 3] &&
                    this.grille[i] == this.grille[i + 6]) {
                    return this.currentPlayerID;
                }
            }
            if (this.grille[0] == this.currentPlayerID &&
                this.grille[0] == this.grille[4] &&
                this.grille[0] == this.grille[8]) {
                return this.currentPlayerID;
            }
            if (this.grille[2] == this.currentPlayerID &&
                this.grille[2] == this.grille[4] &&
                this.grille[2] == this.grille[6]) {
                return this.currentPlayerID;
            }
            // Si toutes les cases sont remplies et aucun gagnant
            if (this.isOver()) {
                return 0;
            }
        }
        return -1;
    }

    /**
     * Méthode pour obtenir la grille de jeu.
     * @return La grille de jeu.
     */
    public int[] getGrille() {
        return grille;
    }

    /**
     * Méthode pour définir la grille de jeu.
     * @param grille La nouvelle grille de jeu.
     */
    public void setGrille(int[] grille) {
        this.grille = grille;
    }
}
