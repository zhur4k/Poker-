import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Random;

public class NewGame extends JPanel implements ActionListener {
    private final int[] POSITION_X_Table = {450, 530, 610, 690, 770, 570, 650};
    private final int[] POSITION_Y_Table = {260, 260, 260, 260, 260, 50, 50};
    private final JButton raiseB,foldB,callB;
    private final JScrollBar raiseS;

    private final JTextField textField,textFieldBank,textFieldSecond;
    private final JLabel labelBank,labelSecond;
    private int stepOfGame;
    private boolean inGame;

    private boolean player1Hod;

    private int typoOfOperation;

    private int bank;
    private final Player player1;
    private final Player player2;
    private final Card card;
    private int second;
    Timer timer;

    public NewGame(JButton raiseB,JButton foldB,JButton callB,JScrollBar raiseS,JTextField textField,JTextField textFieldBank,JLabel labelBank,JLabel labelSecond,JTextField textFieldSecond) {
        this.textFieldSecond = textFieldSecond;
        this.labelSecond = labelSecond;
        this.labelBank = labelBank;
        this.textField = textField;
        this.textFieldBank = textFieldBank;
        this.raiseB = raiseB;
        this.foldB = foldB;
        this.callB = callB;
        this.raiseS = raiseS;

        this.card = new Card();

        this.player1 = new Player(card.getRandomCardToPlayer(), card.getRandomCardToPlayer());
        this.player2 = new Player(card.getRandomCardToPlayer(), card.getRandomCardToPlayer());
        this.stepOfGame=0;
        this.bank = 0;
        this.typoOfOperation = 0;
        this.inGame = true;
        this.player1Hod = true;

        raiseB.addActionListener(e -> typoOfOperation = 1);
        callB.addActionListener(e -> typoOfOperation = 2);
        foldB.addActionListener(e -> typoOfOperation = 3);

        raiseS.addAdjustmentListener(e -> textField.setText(Integer.toString(raiseS.getValue())));

        initGame();
    }


    public void initGame(){
        timer = new Timer(200, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame) {
            checkSecond();
            second++;
            if (player1Hod) {
                setVisibleComponents(true);
                if (second == 100) {
                    fold(player2);
                    player1Hod = false;
                }
                switch (typoOfOperation) {
                    case 1 -> {
                        if(!Objects.equals(textField.getText(), "")) {
                            raise(Integer.parseInt(textField.getText()));
                            player1Hod = false;
                        }
                    }
                    case 2 -> {
                        call(player1);
                        player1Hod = false;
                    }
                    case 3 -> {
                        fold(player1);
                        player1Hod = false;
                    }
                }
                addComponents();
                repaint();

            }else{
                setVisibleComponents(false);
                addComponents();
                repaint();
                if (second == 25) {
                    player1Hod = true;
                    switch (new Random().nextInt(1, 3)) {
                        case 1 -> raise(raiseS.getValue());
                        case 2 -> call(player1);
                        case 3 -> fold(player1);
                    }
                    stepOfGame++;
                }
            }
        }else{
            timer.stop();
        }
    }

    private void checkSecond() {
        String secondT = Integer.toString(second/5);
        if(!secondT.equals(textFieldSecond.getText()))
            textFieldSecond.setText(secondT);
    }

    private void fold(Player player){
        player.changeCashOfPlayer(bank);
        bank = 0;
        checkBank();
        inGame = false;
        second =0;
        typoOfOperation = 0;
    }

    private void call(Player player){
        player.changeCashOfPlayer(-second);
        bank+=second;
        checkBank();
        second =0;
        typoOfOperation = 0;
    }

    private void raise(int j){
            player1Hod = false;
            bank += j;
            checkBank();
            second = 0;
            typoOfOperation = 0;
    }

    private void checkBank(){
        String bankT = Integer.toString(bank);
        if(!bankT.equals(textFieldBank.getText()))
            textFieldBank.setText(bankT);
    }

    private void addComponents(){
        add(textFieldSecond);
        add(labelSecond);
        add(labelBank);
        add(textField);
        add(textFieldBank);
        add(raiseB);
        add(foldB);
        add(callB);
        add(raiseS);
    }
    private void setVisibleComponents(boolean a){
        textField.setVisible(a);
        raiseB.setVisible(a);        foldB.setVisible(a);
        callB.setVisible(a);
        raiseS.setVisible(a);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(loadImage("Table.png"), 0, 0, this);
        paintCards(g);

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
        private void paintOfTo(Graphics g,int j,int[] array){
            for (int i = 0; i < j; i++)
                g.drawImage(loadImage("cards/"+array[i]+".jpg"), POSITION_X_Table[i], POSITION_Y_Table[i], this);
            for (int i = j; i < POSITION_X_Table.length; i++)
                g.drawImage(loadImage("cards/0.jpg"), POSITION_X_Table[i], POSITION_Y_Table[i], this);
        }
    }