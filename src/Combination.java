public class Combination {
    private static int rangOfCombination;
        static  int checkCombination(Player player1,int[] CardsOnTable) {
            int []cards = new int[]{player1.getCardsOfPlayer()[0],player1.getCardsOfPlayer()[1],CardsOnTable[0],CardsOnTable[1],CardsOnTable[2],CardsOnTable[3],CardsOnTable[4]} ;

            if (royalFlush(cards)) return rangOfCombination;
            else if (straightFlush(cards)) return rangOfCombination;
            else if (careCheck(cards)) return rangOfCombination;
            else if (fullHouse(cards)) return rangOfCombination;
            else if (flush(cards)) return rangOfCombination;
            else if (straight(cards)) return rangOfCombination;
            else if (set(cards)) return rangOfCombination;
            else if (twoPair(cards)) return rangOfCombination;
            else if (pair(cards)) return rangOfCombination;
            else highCard(cards); return rangOfCombination;
        }
        static boolean royalFlush(int[]cards){
            return true;
        }
        static boolean straightFlush(int[]cards){
            return true;
        }
        static boolean careCheck(int[]cards){
            return true;
        }

        static boolean fullHouse(int[]cards){
            return true;
        }
        static boolean flush(int[]cards){
            return true;
        }

        static boolean straight(int[]cards){
            return true;
        }

        static boolean set(int[]cards){
            return true;
        }

        static boolean twoPair(int[]cards){
            return true;
        }

        static boolean pair(int[]cards){
            return true;
        }

        static void highCard(int[]cards){
        }
}
