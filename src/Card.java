import java.util.Arrays;
import java.util.Random;

public class Card {
    private final int [] arrayOfCardsOnTable;
    private final boolean [] arrayOfGetCards;

    public Card() {
        this.arrayOfGetCards = new boolean[53];
        this.arrayOfCardsOnTable=new int[5];
        getRandomCardToTable();
    }
    public void getRandomCardToTable(){
        for (int j,i = 0; i <5; i++) {
            do{
                j = new Random().nextInt(1,52);
            }while(arrayOfGetCards[j]);
            arrayOfGetCards[j]=true;
            arrayOfCardsOnTable[i]=j;
        }
    }

    public int getRandomCardToPlayer(){
        int i;
        do{
            i = new Random().nextInt(1,52);
        }while(arrayOfGetCards[i]);
        arrayOfGetCards[i]=true;
        return i;
    }

    public int[] getArrayOfCardsOnTable(){
        return  arrayOfCardsOnTable;
    }
}
