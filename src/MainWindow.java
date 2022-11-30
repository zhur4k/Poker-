import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame{
    public MainWindow() {
        setTitle("Poker GAME");

        JButton buttonRaise = new JButton("Raise");
        buttonRaise.setBounds(200,650,150,70);

        JButton buttonFold = new JButton("Fold");
        buttonFold.setBounds(400,650,150,70);

        JButton buttonCall = new JButton("Call");
        buttonCall.setBounds(600,650,150,70);

        JScrollBar raiseScrollbar = new JScrollBar(JScrollBar.HORIZONTAL);
        raiseScrollbar.setBounds(900,650,350,70);
        raiseScrollbar.setMaximum(1000);
        raiseScrollbar.setMinimum(1);

        JTextField textField = new JTextField();
        textField.setBounds(800,650,50,50);

        JLabel labelBank = new JLabel("Bank$:");
        labelBank.setBounds(580,400,50,40);

        JLabel labelSecond = new JLabel("Time(s):");
        labelSecond.setBounds(1080,610,50,40);

        JTextField textFieldSecond = new JTextField();
        textFieldSecond.setText("0");
        textFieldSecond.setBounds(1150,615,50,30);

        JTextField textFieldBank = new JTextField();
        textFieldBank.setText("0");
        textFieldBank.setBounds(620,400,50,40);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1310, 800);
        setLocation(20,10);
        setResizable(false);
        add(new NewGame(buttonRaise,buttonFold,buttonCall,raiseScrollbar,textField,textFieldBank,labelBank,labelSecond,textFieldSecond));
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow m = new MainWindow();
    }
}
