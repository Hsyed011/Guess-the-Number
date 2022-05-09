import javax.swing.*;

public class Main {

    public static void main(String[] args){
        Screen.addAll();

        JFrame mainFrame = new JFrame();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1080, 720);
        mainFrame.setTitle("Guess the Number");
        mainFrame.setLayout(null);

        mainFrame.add(Screen.menuScreen);
        mainFrame.add(Screen.gameScreen);
    }
}
