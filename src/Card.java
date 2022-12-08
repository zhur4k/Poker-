import java.util.Random;

public class Card {
    private final int [] CardsOnTable;
    private final boolean [] arrayOfGetCards;

    public Card() {
        this.arrayOfGetCards = new boolean[53];
        this.CardsOnTable =new int[5];
        getCardToTable();
    }
    public void getCardToTable(){
        for (int j,i = 0; i <5; i++) {
            do{
                j = new Random().nextInt(1,52);
            }while(arrayOfGetCards[j]);
            arrayOfGetCards[j]=true;
            CardsOnTable[i]=j;
        }
    }

    public int getCardToPlayer(){
        int i;
        do{
            i = new Random().nextInt(1,52);
        }while(arrayOfGetCards[i]);
        arrayOfGetCards[i]=true;
        return i;
    }

    public int[] getCardsOnTable(){
        return CardsOnTable;
    }


}
