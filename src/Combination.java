import java.util.Arrays;

public class Combination {
    private static int [] cardsOfCombination = new int[7];
        private static final String [] cards= {"2 Clubs","3 Clubs","4 Clubs","5 Clubs","6 Clubs","7 Clubs","8 Clubs","9 Clubs","10 Clubs","Jack Clubs","Queen Clubs","King Clubs","Ace Clubs",
                "2 Hearts","3 Hearts","4 Hearts","5 Hearts","6 Hearts","7 Hearts","8 Hearts","9 Hearts","10 Hearts","Jack Hearts","Queen Hearts","King Hearts","Ace Hearts",
                "2 Spades","3 Spades","4 Spades","5 Spades","6 Spades","7 Spades","8 Spades","9 Spades","10 Spades","Jack Spades","Queen Spades","King Spades","Ace Spades",
                "2 Diamonds","3 Diamonds","4 Diamonds","5 Diamonds","6 Diamonds","7 Diamonds","8 Diamonds","9 Diamonds","10 Diamonds","Jack Diamonds","Queen Diamonds","King Diamonds","Ace Diamonds"};

    static  public String getWinner(Player player1, Player player2, int[] CardsOnTable,int bank) {
            StringBuilder result;
            int [] combination1 = new int[]{player1.getCardsOfPlayer()[0],player1.getCardsOfPlayer()[1],CardsOnTable[0],CardsOnTable[1],CardsOnTable[2],CardsOnTable[3],CardsOnTable[4]};
            int [] combination2 = new int[]{player2.getCardsOfPlayer()[0],player2.getCardsOfPlayer()[1],CardsOnTable[0],CardsOnTable[1],CardsOnTable[2],CardsOnTable[3],CardsOnTable[4]};
            checkCombination(combination1);
            combination1 = Arrays.copyOf(cardsOfCombination,cardsOfCombination.length);
            checkCombination(combination2);
            combination2 = Arrays.copyOf(cardsOfCombination,cardsOfCombination.length);
            if(combination1[0]>combination2[0]){
                player1.changeCashOfPlayer(bank);
                result = new StringBuilder(player1.getName() +" "+ combinationToString(combination1[0])+"\n");
            }else if(combination1[0]<combination2[0]){
            player2.changeCashOfPlayer(bank);
            result = new StringBuilder(player2.getName() +" "+ combinationToString(combination1[0])+"\n");
            }else {
                if(combination1[1]>combination2[1]){
                    player1.changeCashOfPlayer(bank);
                    result = new StringBuilder(player1.getName() +" "+ combinationToString(combination1[0])+"\n");
                } else if(combination1[1]<combination2[1]){
                    player2.changeCashOfPlayer(bank);
                    result = new StringBuilder(player2.getName() +" "+ combinationToString(combination1[0])+"\n");
                }else {
                    player1.changeCashOfPlayer(bank/2);
                    player2.changeCashOfPlayer(bank/2);
                    result = new StringBuilder("Dead heat" + combinationToString(combination1[0]));
                }
            }
            return result.toString();
        }

        private static void sortRevers(int []array){
            Arrays.sort(array);
            for (int i = 0; i < array.length / 2; i++) {
                int tmp = array[i];
                array[i] = array[array.length - i - 1];
                array[array.length - i - 1] = tmp;
            }
        }

        static private String combinationToString(int combination){
            return switch (combination) {
                case 0 -> "High Card";
                case 1 -> "Pair";
                case 2 -> "Two Pairs";
                case 3 -> "Three of a Kind";
                case 4 -> "Straight";
                case 5 -> "Flush";
                case 6 -> "Full House";
                case 7 -> "Four of a Kind";
                case 8 -> "Straight Flush";
                case 9 -> "Royal Flush";
                default -> "0";
            };
        }
        static private void checkCombination(int [] array){
            sortRevers(array);
            int []arraySameSuits = checkSameSuit(array);
            if(arraySameSuits.length>4&&royalFlush(arraySameSuits)) return;
            if(arraySameSuits.length>4&&straightFlush(arraySameSuits)) return;
            if(careCheck(array)) return;
            if(fullHouse(array)) return;
            if(flush(arraySameSuits)) return;
            if(straight(array)) return;
            if(set(array)) return;
            if(twoPair(array)) return;
            if(pair(array)) return;
            highCard(array);
        }

        static private int [] checkSameSuit(int[] array1){
        int []array2 = new int[array1.length];
        int j= 0;
        int suit1 = 0;
        int suit2 = 13;
        while(suit2<53) {
            j = 0;
            for (int k : array1) {
                if (k < suit2&&k>suit1) {
                    array2[j] = k;
                    j++;
                }
            }
            suit1 = suit2;
            suit2+=13;
        }
        return Arrays.copyOf(array2,j);
        }
        static private boolean royalFlush(int[]cards){
        if(straightFlush(cards)&&(cardsOfCombination[1] == 13||cardsOfCombination[1] == 26||cardsOfCombination[1] == 39||cardsOfCombination[1] == 52)){
            cardsOfCombination[0]=9;
            return true;
        }
            return false;
        }
        static private boolean straightFlush(int[]cards){
            cardsOfCombination[0] = 8;
            return straight(cards);
        }
        static private boolean careCheck(int[]cards){
            cardsOfCombination = new int[5];
            int kolvo=0;
            for (int card:cards) {
                for (int card2:cards) {
                    if(card%13==card2%13) {
                        cardsOfCombination[kolvo+1] = card2;
                        kolvo++;
                    }
                }
                if(kolvo ==4){
                    cardsOfCombination[0] = 7;
                    return true;
                }else kolvo=0;

            }
            return false;
        }

        static private boolean fullHouse(int[]cards){
            if(set(cards)){
                int [] array =cardsOfCombination;
                cardsOfCombination = new int[6];
                System.arraycopy(array, 1, cardsOfCombination, 1, 3);
                int kolvo=0;
                for (int card:cards) {
                    for (int card2:cards) {
                        if(card%13==card2%13&&card%13!=cardsOfCombination[1]%13) {
                            cardsOfCombination[kolvo+4] = card2;
                            kolvo++;
                        }
                    }
                    if(kolvo ==2){
                        cardsOfCombination[0] = 6;
                        return true;
                    }else kolvo =0;
                }
                return false;
            }
            return false;
        }
        static private boolean flush(int[]cards){
        if(cards.length>4){
            cardsOfCombination = new int[6];
            cardsOfCombination[0]= 5;
            System.arraycopy(cards, 0, cardsOfCombination, 1, 5);
            return true;
        }
        return false;
        }

        static private boolean straight(int[]cards){
            cardsOfCombination = new int[6];
            int j = cards[0],kolvo = 0,index =0;
            for (int i = 0; i < cards.length; i++) {
                if(kolvo == 5) {
                    cardsOfCombination[0] = 4;
                    System.arraycopy(cards, index, cardsOfCombination, 1, 5);
                    return true;
                }
                if(j==cards[i]) {
                    kolvo++;
                    j--;
                }
                else {
                    j=cards[i];
                    index=i;
                    kolvo=0;
                }
            }
            return false;
        }

        static private boolean set(int[]cards){
            cardsOfCombination = new int[4];
            int kolvo=0;
            for (int card:cards) {
                for (int card2:cards) {
                    if(card%13==card2%13) {
                        cardsOfCombination[kolvo+1] = card2;
                        kolvo++;
                    }
                }
                if(kolvo ==3){
                    cardsOfCombination[0] = 3;
                    return true;
                }else kolvo =0;
            }
            return false;
        }

        static private boolean twoPair(int[]cards){
            cardsOfCombination = new int[5];
            int kolvo=0;
            for (int card:cards) {
                for (int card2:cards) {
                    if(card%13==card2%13) {
                        cardsOfCombination[kolvo+1] = card2;
                        kolvo++;
                    }
                }
                if(kolvo ==2){
                    for (int card3:cards) {
                        for (int card4:cards) {
                            if(card3%13==card4%13&&card3%13!=cardsOfCombination[1]%13) {
                                cardsOfCombination[kolvo+1] = card4;
                                kolvo++;
                            }
                        }
                        if(kolvo ==4) {
                            cardsOfCombination[0] = 2;
                            return true;
                        }else kolvo =0;
                    }
                }else kolvo =0;
            }
            return false;
        }

        static private boolean pair(int[]cards){
            cardsOfCombination = new int[3];
            int kolvo=0;
            for (int card:cards) {
                for (int card2:cards) {
                    if(card%13==card2%13) {
                        cardsOfCombination[kolvo+1] = card2;
                        kolvo++;
                    }
                }
                if(kolvo >1){
                    cardsOfCombination[0] = 1;
                    return true;
                }else kolvo =0;
            }
            return false;
        }

        static private void highCard(int[]cards){
            cardsOfCombination = new int[]{0,cards[1]};
        }
}
