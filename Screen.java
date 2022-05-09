import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.HashSet;
import java.util.Random;

public class Screen extends JPanel {
    public static Screen menuScreen = new Screen();
    public static Screen gameScreen = new Screen();
    static int lives;
    static int number;
    static int limit;
    public static JTextArea livesTextArea = new JTextArea();
    static HashSet<Integer> guessed = new HashSet<Integer>();
    static JTextArea hintTextArea = new JTextArea();
    static JTextArea rangeTextArea = new JTextArea();


    public static void addAll(){
        //Menu screen-------------------------------------------------------------------------------------------------
        menuScreen.setVisible(true);
        menuScreen.setSize(1080,720);
        menuScreen.setLayout(null);
        menuScreen.setBackground(new Color(0,153,0));

        JButton playButton = new JButton();
        playButton.setVisible(true);
        playButton.setBounds(470,300,160,60);
        playButton.setText("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] difficulties = {"Easy", "Medium", "Hard"};
                String difficulty = "";
                try {
                    difficulty = JOptionPane.showInputDialog(null, "Choose a difficulty", "Difficulty Selection", JOptionPane.QUESTION_MESSAGE, null, difficulties, difficulties[0]).toString();
                } catch (Exception exception){
                    System.out.println("Cancelled");
                    return;
                }
                menuScreen.setVisible(false);
                gameScreen.setVisible(true);

                if(difficulty.equals("Easy")){
                    lives = 9;
                    limit = 100;
                    number = (new Random().nextInt(limit) + 1);
                    livesTextArea.setText(Integer.toString(lives));
                    livesTextArea.setForeground(Color.DARK_GRAY);
                    rangeTextArea.setText("Range: 1 - " + limit);
                } else if (difficulty.equals("Medium")) {
                    lives = 8;
                    limit = 200;
                    number = (new Random().nextInt(limit) + 1);
                    livesTextArea.setText(Integer.toString(lives));
                    livesTextArea.setForeground(Color.DARK_GRAY);
                    rangeTextArea.setText("Range: 1 - " + limit);
                } else if (difficulty.equals("Hard")) {
                    lives = 7;
                    limit = 300;
                    number = (new Random().nextInt(limit) + 1);
                    livesTextArea.setText(Integer.toString(lives));
                    livesTextArea.setForeground(Color.DARK_GRAY);
                    rangeTextArea.setText("Range: 1 - " + limit);
                }
                System.out.println("Game started, Difficulty:" + difficulty + " Lives: " + lives);
            }
        });

        JTextArea title = new JTextArea();
        title.setBounds(350,30,450,130);
        title.setVisible(true);
        title.setText("Guess the Number");
        title.setBackground(new Color(0,153,0));
        title.setFont(new Font(Font.MONOSPACED, Font.BOLD, 47));
        title.setEditable(false);

        JButton rulesButton = new JButton();
        rulesButton.setVisible(true);
        rulesButton.setBounds(500,380,100,30);
        rulesButton.setText("Rules");
        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Try to guess the number by getting closer and closer using the hints before losing all your lives. Difficulties: Easy(9 lives, 1-100) Medium(8 lives, 1-200) Hard(7 lives, 1-300)");
            }
        });

        JButton exitButton = new JButton();
        exitButton.setVisible(true);
        exitButton.setBounds(500,420,100,30);
        exitButton.setText("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

        JTextArea name = new JTextArea();
        name.setBounds(820,630,300,50);
        name.setVisible(true);
        name.setText("Made by: Hasan Syed");
        name.setBackground(new Color(0,153,0));
        name.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
        name.setEditable(false);

        menuScreen.add(name);
        menuScreen.add(exitButton);
        menuScreen.add(rulesButton);
        menuScreen.add(title);
        menuScreen.add(playButton);

        //Game Screen--------------------------------------------------------------------------------------------------
        gameScreen.setVisible(false);
        gameScreen.setSize(1080,720);
        gameScreen.setLayout(null);
        gameScreen.setBackground(new Color(0,0,160));

        JTextField inputField = new JTextField();
        inputField.setVisible(true);
        inputField.setBounds(400,270,170,80);
        inputField.setFont(new Font("Braggadocio", Font.BOLD, 32));
        inputField.setToolTipText("Enter Number");

        JButton returnButton = new JButton();
        returnButton.setVisible(true);
        returnButton.setBounds(920,15,130   ,30);
        returnButton.setText("Return to menu");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you would like to exit? Your progress will not be saved", "Return to menu confirmation", JOptionPane.YES_NO_OPTION);
                if(confirmation == 0){
                    gameScreen.setVisible(false);
                    menuScreen.setVisible(true);
                    hintTextArea.setText("Guess the Number!");
                    guessed.clear();
                    inputField.setText("");
                    System.out.println("Returned to menu");
                }
            }
        });

        JButton submitButton = new JButton();
        submitButton.setVisible(true);
        submitButton.setBounds(585, 295, 75,40);
        submitButton.setText("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                int guess = 0;
                try {
                    guess = Integer.parseInt(input);
                } catch (Exception exception){
                    JOptionPane.showMessageDialog(null, "You may only submit a number within the range!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    System.out.println("NOT AN INT");
                    return;
                }
                if(guess > limit || guess < 1){
                    JOptionPane.showMessageDialog(null, "You may only submit a number within the range!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    System.out.println("OUT OF RANGE");
                    return;
                }
                if(guessed.contains(guess)){
                    JOptionPane.showMessageDialog(null, "You already guessed that number!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    System.out.println("ALREADY GUESSED");
                    return;
                }
                lives--;
                livesTextArea.setText(Integer.toString(lives));
                guessed.add(guess);

                if(guess > number){
                    hintTextArea.setText("Too high!");
                } else if(guess < number){
                    hintTextArea.setText("Too low!");
                } else if(guess == number){
                    hintTextArea.setText("You got it!!!");
                    JOptionPane.showMessageDialog(null, "You got it! The number was " + number);
                    gameScreen.setVisible(false);
                    menuScreen.setVisible(true);
                    inputField.setText("");
                    hintTextArea.setText("Guess the Number!");
                    guessed.clear();
                    return;
                }

                if(lives == 1){
                    livesTextArea.setForeground(Color.RED);
                }

                if(lives <= 0){
                    hintTextArea.setText("Game Over!");
                    JOptionPane.showMessageDialog(null, "Game Over! The number was " + number);

                    gameScreen.setVisible(false);
                    menuScreen.setVisible(true);
                    inputField.setText("");
                    hintTextArea.setText("Guess the Number!");
                    guessed.clear();
                }

                System.out.println("Submitted " + guess);
            }
        });

        hintTextArea.setVisible(true);
        hintTextArea.setEditable(false);
        hintTextArea.setBackground(new Color(0,0,160));
        hintTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
        hintTextArea.setText("Guess the Number!");
        hintTextArea.setBounds(400,210,400,60);

        JTextArea remainingLivesTextArea = new JTextArea();
        remainingLivesTextArea.setVisible(true);
        remainingLivesTextArea.setEditable(false);
        remainingLivesTextArea.setBackground(new Color(0,0,160));
        remainingLivesTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        remainingLivesTextArea.setText("Remaining lives:");
        remainingLivesTextArea.setBounds(400,350,220,30);

        livesTextArea.setVisible(true);
        livesTextArea.setEditable(false);
        livesTextArea.setBackground(new Color(0,0,160));
        livesTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        livesTextArea.setBounds(630,350,50,30);

        rangeTextArea.setVisible(true);
        rangeTextArea.setEditable(false);
        rangeTextArea.setBackground(new Color(0,0,160));
        rangeTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
        rangeTextArea.setBounds(400,385,175,30);

        gameScreen.add(rangeTextArea);
        gameScreen.add(livesTextArea);
        gameScreen.add(remainingLivesTextArea);
        gameScreen.add(hintTextArea);
        gameScreen.add(submitButton);
        gameScreen.add(inputField);
        gameScreen.add(returnButton);
    }
}
