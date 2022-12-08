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
    private final JTextField textField,textFieldSecond,textFieldBank,textFieldOfMove,textFieldBank1Player,textFieldBank2Player;


    private int betOf1 = 0;
    private int betOf2 = 0;


    private int stepOfGame;
    private boolean inGame;


    private boolean playerHod;

    private int typoOfOperationPlayer;
    private int typoOfOperationBot;


    private int bank;
    private final Player player1;
    private final Player player2;
    private Card card;
    private int second;
    Timer timer;

    public NewGame() {
        this.player1 = new Player("Kostya ");
        this.player2 = new Player("Bot Bob ");

        this.buttonCheck = new JButton("Check");
        buttonCheck.setFont(new Font("TimesRoman", Font.PLAIN, 25));


        this.buttonRaise = new JButton("Raise");
        buttonRaise.setFont(new Font("TimesRoman", Font.PLAIN, 25));


        this.buttonFold = new JButton("Fold");
        buttonFold.setFont(new Font("TimesRoman", Font.PLAIN, 25));


        this.buttonCall = new JButton("Call");
        buttonCall.setFont(new Font("TimesRoman", Font.PLAIN, 25));

        this.raiseScrollbar = new JScrollBar(JScrollBar.HORIZONTAL);

        this.textField = new JTextField();

        this.textFieldBank1Player = new JTextField();
        textFieldBank1Player.setBackground(new Color(47,47,47));
        textFieldBank1Player.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        textFieldBank1Player.setForeground(Color.WHITE);

        this.textFieldBank2Player = new JTextField();
        textFieldBank2Player.setBackground(new Color(13,88,42));
        textFieldBank2Player.setFont(new Font("TimesRoman", Font.PLAIN, 25));

        this.textFieldSecond = new JTextField();

        this.textFieldOfMove = new JTextField();
        textFieldOfMove.setBackground(new Color(13,88,42));
        textFieldOfMove.setFont(new Font("TimesRoman", Font.PLAIN, 25));


        this.textFieldBank = new JTextField();
        textFieldBank.setText("Bank$:0");
        textFieldBank.setBackground(new Color(13,88,42));
        textFieldBank.setFont(new Font("TimesRoman", Font.PLAIN, 25));

        newGameSettings();
        checkButtonClick();
        initGame();
    }

    private void newGameSettings(){
        card = new Card();
        player1.setCards(card.getCardToPlayer(), card.getCardToPlayer());
        player2.setCards(card.getCardToPlayer(), card.getCardToPlayer());
        second = 0;
        stepOfGame=0;
        bank = 0;
        typoOfOperationPlayer = 0;
        typoOfOperationBot = 0;
        inGame = true;
        playerHod = true;
        betOf1 = 0;
        betOf2 = 0;
    }
    private void checkButtonClick(){
        buttonRaise.addActionListener(e -> {typoOfOperationPlayer = 1;betOf1 = Integer.parseInt(textField.getText());});
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
        checkBank();
        if (inGame) {
            checkSecond();
            checkOperations();
            playerMove();
        }else {
            int i = Combination.checkCombination(player1,card.getCardsOnTable());
            newGameSettings();
            textFieldOfMove.setText("New Game Start!");
            repaint();
        }
    }

    private void playerMove(){
        if (playerHod) {
            if(typoOfOperationBot==1){
                raiseScrollbar.setMinimum(betOf2);
            }else{
                raiseScrollbar.setMinimum(1);
            }
            raiseScrollbar.setMaximum(player1.getCashOfPlayer());
            visibleOfComponents(true);
            if(betOf2>0) {
                buttonCheck.setVisible(false);
                raiseScrollbar.setMinimum(betOf2);
            }
            if(typoOfOperationPlayer==2){
                betOf1 = betOf2;
                if(betOf1==0) typoOfOperationPlayer = 4;
            }
            playerHod = moveOfPlayer(typoOfOperationPlayer,player1,betOf1);
        }else {
            visibleOfComponents(false);
            if (second == 25) {
                do{ typoOfOperationBot = new Random().nextInt(1,4);
                }while(typoOfOperationBot == 3);
                if(typoOfOperationPlayer==1&&typoOfOperationBot==4) typoOfOperationBot =2;
                if(typoOfOperationBot==1){
                    betOf2 = new Random().nextInt(betOf1,player2.getCashOfPlayer());
                }
                if(typoOfOperationBot==2){
                    betOf2 = betOf1;
                    if(betOf2==0) typoOfOperationBot = 4;
                }
                playerHod = !moveOfPlayer(typoOfOperationBot,player2,betOf2);
            }
        }
    }
    private void checkOperations() {
        if (typoOfOperationPlayer == 1 && typoOfOperationBot == 1) {
            if (betOf2 == betOf1){
                stepOfGame++;
                typoOfOperationPlayer = 0;
                typoOfOperationBot = 0;
                betOf1 = 0;
                betOf2 = 0;
            }else if (betOf2 > betOf1) {
                betOf2 = betOf2 - betOf1;
                betOf1 = 0;
                typoOfOperationPlayer = 0;
                playerHod = true;
            } else {
                betOf1 = betOf1 - betOf2;
                betOf2 = 0;
                typoOfOperationBot = 0;
                playerHod = false;
            }
        }else if((typoOfOperationPlayer == 1 && typoOfOperationBot == 2&&playerHod)||(typoOfOperationPlayer == 2 && typoOfOperationBot == 1&&!playerHod)) {
            stepOfGame++;
            typoOfOperationPlayer = 0;
            typoOfOperationBot = 0;
            betOf1 = 0;
            betOf2 = 0;
        }else if ((typoOfOperationPlayer == 4 && typoOfOperationBot == 4)) {
            stepOfGame++;
            typoOfOperationPlayer = 0;
            typoOfOperationBot = 0;
            betOf1 = 0;
            betOf2 = 0;
        }else if (typoOfOperationPlayer == 4 && typoOfOperationBot == 1&&betOf1!=betOf2) {
            typoOfOperationPlayer = 0;
            betOf1 = 0;
        }else if (typoOfOperationPlayer == 1 && typoOfOperationBot == 4&&betOf1!=betOf2) {
            typoOfOperationBot = 0;
            betOf2 = 0;
        }

    }
    private void checkSecond() {
        second++;
        if (second == 100) {
            fold(player2);
        }
        if(!("Time(s):"+second/5).equals(textFieldSecond.getText()))
            textFieldSecond.setText("Time(s):"+second/5);
    }
    private void checkBank(){
        String bankT = Integer.toString(bank);
        if(!bankT.equals(textFieldBank.getText()))
            textFieldBank.setText("Bank$:"+bankT);
        textFieldBank1Player.setText("Bank of "+player1.getName()+":"+player1.getCashOfPlayer());
        textFieldBank2Player.setText("Bank of "+player2.getName()+":"+player2.getCashOfPlayer());
    }


    private boolean moveOfPlayer(int typoOfOperation,Player player,int betOtherPlayer){
        switch (typoOfOperation) {
            case 1 -> {
                raise(betOtherPlayer, player);
                return false;
            }
            case 2 -> {
                if(betOtherPlayer==0){
                    check(player);
                }else {
                    call(betOtherPlayer, player);
                }
                return false;
            }
            case 3 -> {
                fold(player);
                return false;
            }
            case 4 -> {
                if(betOtherPlayer==0) {
                    check(player);
                    return false;
                }
            }
        }
        return true;
    }


    private void check(Player player){
        textFieldOfMove.setText(player.getName()+" Check");
        second =0;
    }
    private void fold(Player player){
        textFieldOfMove.setText(player.getName()+" fold:");
        if(player.equals(player1)) {
            player2.changeCashOfPlayer(bank);
        }else  player1.changeCashOfPlayer(bank);
        bank = 0;
        inGame = false;
        second =0;
    }

    private void call(int betOtherPlayer, Player player){
        textFieldOfMove.setText(player.getName() + " Call:" + betOtherPlayer);
        player.changeCashOfPlayer(-betOtherPlayer);
        bank+=betOtherPlayer;
        second =0;
    }

    private void raise(int betOtherPlayer,Player player){
        textFieldOfMove.setText(player.getName()+" Raise:"+betOtherPlayer);
        player.changeCashOfPlayer(-betOtherPlayer);
        bank += betOtherPlayer;
        second = 0;
    }

    private void visibleOfComponents(boolean a){
        buttonCheck.setVisible(a);
        buttonRaise.setVisible(a);
        buttonFold.setVisible(a);
        buttonCall.setVisible(a);
        raiseScrollbar.setVisible(a);
        textField.setVisible(a);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(loadImage("Table.png"), 0, 0, this);
        paintCards(g);
        paintComponents();
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
        add(raiseScrollbar);

        textField.setBounds(800,650,50,30);
        add(textField);

        textFieldBank1Player.setBounds(750,600,250,30);
        add(textFieldBank1Player);

        textFieldBank2Player.setBounds(750,90,260,30);
        add(textFieldBank2Player);

        textFieldOfMove.setBounds(850,280,250,50);
        add(textFieldOfMove);

        textFieldSecond.setBounds(1150,615,70,30);
        add(textFieldSecond);

        textFieldBank.setBounds(620,400,150,30);
        add(textFieldBank);

    }

    public Image loadImage(String imageName) {
        return new ImageIcon(imageName).getImage();
    }
    private void paintCards(Graphics g) {
        g.drawImage(loadImage("cards/" + player1.getCardsOfPlayer()[0] + ".jpg"), 570, 500, this);
        g.drawImage(loadImage("cards/" + player1.getCardsOfPlayer()[1] + ".jpg"), 650, 500, this);
        switch (stepOfGame) {
            case 1 -> paintOfTo(g, 3,card.getCardsOnTable());
            case 2 -> paintOfTo(g, 4,card.getCardsOnTable());
            case 3 -> paintOfTo(g, 5,card.getCardsOnTable());
            case 4 -> {
                paintOfTo(g, 5,card.getCardsOnTable());
                g.drawImage(loadImage("cards/" + player2.getCardsOfPlayer()[0] + ".jpg"),POSITION_X_Table[5], POSITION_Y_Table[5], this);
                g.drawImage(loadImage("cards/" + player2.getCardsOfPlayer()[1] + ".jpg"),POSITION_X_Table[6], POSITION_Y_Table[6], this);
            }
            default -> paintOfTo(g, 0,card.getCardsOnTable());
        }
    }

    private void paintOfTo(Graphics g,int j,int[] array){
            for (int i = 0; i < j; i++)
                g.drawImage(loadImage("cards/"+array[i]+".jpg"), POSITION_X_Table[i], POSITION_Y_Table[i], this);
            for (int i = j; i < POSITION_X_Table.length; i++)
                g.drawImage(loadImage("cards/0.jpg"), POSITION_X_Table[i], POSITION_Y_Table[i], this);
        }
    }