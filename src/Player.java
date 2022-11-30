public class Player {
    private final int [] cardsOfPlayer;
    private int cashOfPlayer;
    public Player(int i, int j) {
        this.cashOfPlayer = 1000;
        this.cardsOfPlayer = new int[]{i,j};
    }

    public int getCashOfPlayer() {
        return cashOfPlayer;
    }

    public int[] getCardsOfPlayer() {
        return cardsOfPlayer;
    }

    public void changeCashOfPlayer(int change) {
        cashOfPlayer+=change;
    }
}
