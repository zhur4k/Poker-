public class Player {
    private final String name;
    private int [] cardsOfPlayer;
    private int cashOfPlayer;
    public Player(String name) {
        this.name = name;
        this.cashOfPlayer = 1000;
    }
    public String getName() {
        return name;
    }

    public void setCards(int i,int j){
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
