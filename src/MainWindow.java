import javax.swing.*;

public class MainWindow extends JFrame{
    public MainWindow() {
        setTitle("Poker GAME");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1310, 800);
        setLocation(20,10);
        add(new NewGame());
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new  MainWindow();
    }
}
