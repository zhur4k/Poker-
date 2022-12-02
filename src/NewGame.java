import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NewGame extends JPanel implements ActionListener {
    private final int[] POSITION_X_Table = {450, 530, 610, 690, 770, 570, 650};
    private final int[] POSITION_Y_Table = {260, 260, 260, 260, 260, 50, 50};
    private final JButton buttonRaise,buttonFold,buttonCall,buttonCheck;
    private final JScrollBar raiseScrollbar;
    private final JTextField textField,textFieldSecond,textFieldBank;


    private int betOf1 = 0;
    private int betOf2 = 0;

    private int raise1 = 0;
    private int raise2 = 0;

    private final JLabel labelBank,labelSecond;
    private int stepOfGame;
    private boolean inGame;

    private boolean player1Hod;

    private int typoOfOperationPlayer;
    private int typoOfOperationBot;


    private int bank;
    private final Player player1;
    private final Player player2;
    private final Card card;
    private int second;
    Timer timer;

    public NewGame() {
        this.card = new Card();
        this.player1 = new Player(card.getRandomCardToPlayer(), card.getRandomCardToPlayer());
        this.player2 = new Player(card.getRandomCardToPlayer(), card.getRandomCardToPlayer());
        this.stepOfGame=0;
        this.bank = 0;
        this.typoOfOperationPlayer = 0;
        this.inGame = true;
        this.player1Hod = true;

        this.buttonCheck = new JButton("Check");

        this.buttonRaise = new JButton("Raise");

        this.buttonFold = new JButton("Fold");

        this.buttonCall = new JButton("Call");

        this.raiseScrollbar = new JScrollBar(JScrollBar.HORIZONTAL);

        this.textField = new JTextField();

        this.labelBank = new JLabel("Bank$:");

        this.labelSecond = new JLabel("Time(s):");

        this.textFieldSecond = new JTextField();
        textFieldSecond.setText("0");

        this.textFieldBank = new JTextField();
        textFieldBank.setText("0");
        checkButtonClick();
        initGame();
    }

    private void checkButtonClick(){
        buttonRaise.addActionListener(e -> {typoOfOperationPlayer = 1; betOf1 = raise1 = Integer.parseInt(textField.getText());});
        buttonCall.addActionListener(e -> typoOfOperationPlayer = 2);
        buttonFold.addActionListener(e -> typoOfOperationPlayer = 3);
        buttonCheck.addActionListener(e -> typoOfOperationPlayer = 4);

        raiseScrollbar.addAdjustmentListener(e -> textField.setText(Integer.toString(raiseScrollbar.getValue())));
    }



    public void initGame(){
        timer = new Timer(200, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkSecond();
            if (second == 100) {
                fold(player2);
                player1Hod = false;
            }
            if (player1Hod) {
                raiseScrollbar.setMinimum(1);
                raiseScrollbar.setMaximum(player1.getCashOfPlayer());
                visibleOfComponents(true);
                if(betOf2>0) buttonCheck.setVisible(false);
                if(betOf2>0) raiseScrollbar.setMinimum(betOf2);

                if(typoOfOperationBot==2){
                    betOf1 = betOf2;
                }
                player1Hod = !moveOfPlayer(typoOfOperationPlayer,player1,betOf2,raise1);
            } else {
                visibleOfComponents(false);
                if (second == 25) {
                    typoOfOperationBot = new Random().nextInt(1,4);
                    if(typoOfOperationBot==1){
                        raise2 = new Random().nextInt(betOf1,player2.getCashOfPlayer());
                    }
                    if(typoOfOperationBot==2){
                        betOf2 = betOf1;
                    }
                    player1Hod = moveOfPlayer(typoOfOperationBot,player2,betOf1,raise2);
                    typoOfOperationPlayer = 0;

                }
            }
            if (typoOfOperationPlayer == 4 && typoOfOperationBot == 4) {
                stepOfGame++;
                typoOfOperationPlayer = 0;
                typoOfOperationBot = 0;}
//            }else{
//                    stepOfGame++;
//                    typoOfOperationPlayer = 0;
//                    typoOfOperationBot = 0;
//            }
        }else {
            timer.stop();
        }
    }

    private boolean moveOfPlayer(int typoOfOperation,Player player,int betOtherPlayer,int raise){
        switch (typoOfOperation) {
            case 1 -> {
                raise(raise, player);
                return true;
            }
            case 2 -> {
                call(betOtherPlayer,player);
                return true;
            }
            case 3 -> {
                fold(player);
                return true;
            }
            case 4 -> {
                if(betOtherPlayer==0) {
                    check();
                    return true;
                }
            }
        }
        return false;
    }

    private void visibleOfComponents(boolean a){
        buttonCheck.setVisible(a);
        buttonRaise.setVisible(a);
        buttonFold.setVisible(a);
        buttonCall.setVisible(a);
        raiseScrollbar.setVisible(a);
        textField.setVisible(a);
    }

    private void checkSecond() {
        second++;
        String secondT = Integer.toString(second/5);
        if(!secondT.equals(textFieldSecond.getText()))
            textFieldSecond.setText(secondT);
    }

    private void check(){
        second =0;
    }
    private void fold(Player player){
        if(player.equals(player1)) {
            player2.changeCashOfPlayer(bank);
        }else  player1.changeCashOfPlayer(bank);
        bank = 0;
        checkBank();
        inGame = false;
        second =0;
    }

    private void call(int j, Player player){
        player.changeCashOfPlayer(-j);
        bank+=j;
        checkBank();
        second =0;
    }

    private void raise(int j,Player player){
        player.changeCashOfPlayer(-j);
        bank += j;
        checkBank();
        second = 0;
    }

    private void checkBank(){
        String bankT = Integer.toString(bank);
        if(!bankT.equals(textFieldBank.getText()))
            textFieldBank.setText(bankT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(loadImage("Table.png"), 0, 0, this);
        paintCards(g);
        paintComponents();
    }
    public Image loadImage(String imageName) {
        return new ImageIcon(imageName).getImage();
    }
    private void paintCards(Graphics g) {
        g.drawImage(loadImage("cards/" + player1.getCardsOfPlayer()[0] + ".jpg"), 570, 500, this);
        g.drawImage(loadImage("cards/" + player1.getCardsOfPlayer()[1] + ".jpg"), 650, 500, this);
        switch (stepOfGame) {
            case 1 -> paintOfTo(g, 3,card.getArrayOfCardsOnTable());
            case 2 -> paintOfTo(g, 4,card.getArrayOfCardsOnTable());
            case 3 -> paintOfTo(g, 5,card.getArrayOfCardsOnTable());
            case 4 -> {
                paintOfTo(g, 5,card.getArrayOfCardsOnTable());
                g.drawImage(loadImage("cards/" + player2.getCardsOfPlayer()[0] + ".jpg"),POSITION_X_Table[5], POSITION_Y_Table[5], this);
                g.drawImage(loadImage("cards/" + player2.getCardsOfPlayer()[1] + ".jpg"),POSITION_X_Table[6], POSITION_Y_Table[6], this);
            }
            default -> paintOfTo(g, 0,card.getArrayOfCardsOnTable());
        }
    }

    private void paintComponents(){
        buttonCheck.setBounds(10,650,150,70);
        add(buttonCheck);

        buttonRaise.setBounds(200,650,150,70);
        add(buttonRaise);

        buttonFold.setBounds(400,650,150,70);
        add(buttonFold);

        buttonCall.setBounds(600,650,150,70);
        add(buttonCall);

        raiseScrollbar.setBounds(900,650,350,70);
        raiseScrollbar.setMaximum(1000);
        raiseScrollbar.setMinimum(1);
        add(raiseScrollbar);

        textField.setBounds(800,650,50,50);
        add(textField);

        labelBank.setBounds(580,400,50,40);
        add(labelBank);

        labelSecond.setBounds(1080,610,50,40);
        add(labelSecond);

        textFieldSecond.setBounds(1150,615,50,30);
        add(textFieldSecond);

        textFieldBank.setBounds(620,400,50,40);
        add(textFieldBank);
    }

        private void paintOfTo(Graphics g,int j,int[] array){
            for (int i = 0; i < j; i++)
                g.drawImage(loadImage("cards/"+array[i]+".jpg"), POSITION_X_Table[i], POSITION_Y_Table[i], this);
            for (int i = j; i < POSITION_X_Table.length; i++)
                g.drawImage(loadImage("cards/0.jpg"), POSITION_X_Table[i], POSITION_Y_Table[i], this);
        }
    }