package server;


public class Plateau  {
    private int[] grille;
   
    private int player1ID, player2ID, currentPlayerID, countMove ;
    public Plateau(int player1ID,int player2ID,int currentPlayerID) {
        this.grille = new int[9];
        this.initiateGrid();
        this.player1ID = player1ID;
        this.currentPlayerID = currentPlayerID;
        this.player2ID = player2ID;
        countMove = 0;
    }
    public int getElement(int x){
        return grille[x];
    }
    public boolean validMove(int x){
        return grille[x] == 0;
    }
    public void initiateGrid(){
        for (int i = 0; i < grille.length; i++) {
            grille[i] = 0;
        }
    }

    public int getCurrentPlayer(){
        return this.currentPlayerID;
    }
    public void play(int x){
        this.grille[x] = this.currentPlayerID;
        countMove ++;
    }
    public void changePlayer(){
        if(this.currentPlayerID == this.player1ID){
            this.currentPlayerID = this.player2ID;
        }else{
            this.currentPlayerID = this.player1ID;
        }
    }
    public boolean isOver(){
        return countMove == 9;
    }
    public int getWinner(){
        if(countMove >= 5){
            for (int i = 0; i <= 6; i += 3) {
                if (this.grille[i] == this.currentPlayerID &&
                    this.grille[i] == this.grille[i+1] && 
                    this.grille[i] == this.grille[i+2]) 
                {
                    System.out.println("Ici : linge "+i);
                    return this.currentPlayerID;
                }
            }
            for (int i = 0; i <= 2; i++) {
                if (this.grille[i] == this.currentPlayerID &&
                    this.grille[i] == this.grille[i+3] && 
                    this.grille[i] == this.grille[i+6] ) 
                {
                    System.out.println("Ici : colonne "+i);
                    return this.currentPlayerID;
                }
            }
            if (this.grille[0] == this.currentPlayerID && 
                this.grille[0] == this.grille[4] && 
                this.grille[0] == this.grille[8]) 
            {
                System.out.println("Ici : diagonal 048 ");
                return this.currentPlayerID;
            }
            if (this.grille[2] == this.currentPlayerID &&
                this.grille[2] == this.grille[4] && 
                this.grille[2]== this.grille[6] ) 
            {
                System.out.println("Ici : diagonal 246 ");
                return this.currentPlayerID;
            }
            if (this.isOver()) {
                return 0;
           }
        }
        return -1;
    }

    public int[] getGrille() {
        return grille;
    }

    public void setGrille(int[] grille) {
        this.grille = grille;
    }
}
