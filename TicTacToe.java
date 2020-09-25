import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class TicTacToe extends JFrame implements ActionListener {

    private JButton [][]buttons = new JButton[3][3];
    private JButton SpeelButton = new JButton("Speel");
    private JLabel statusLabel = new JLabel("");
    private TicTacToeComputer game = null;
    private int speler1 = 0;
    private int computer = 0;
    private boolean isSpeel = false;
    private String []chars=new String[]{"","X","O"};

    private void setStatus(String s) {
        statusLabel.setText(s);
    }

    private void setButtonsEnabled(boolean enabled) {
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++) {
                buttons[i][j].setEnabled(enabled);
                if(enabled) buttons[i][j].setText(" ");
            }
    }

    public TicTacToe() {

        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel centerPanel = new JPanel(new GridLayout(3,3));
        Font font = new Font("Arial",Font.BOLD, 32);
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++) {
                buttons[i][j] = new JButton(" ");
                buttons[i][j].setFont(font);
                buttons[i][j].addActionListener(this);
                buttons[i][j].setFocusable(false);
                centerPanel.add(buttons[i][j]);
            }

        SpeelButton.addActionListener(this);

        JPanel northPanel = new JPanel();
        northPanel.add(statusLabel);

        JPanel southPanel = new JPanel();
        southPanel.add(SpeelButton);

        setStatus("Klik op 'Start' om te beginnen");
        setButtonsEnabled(false);

        add(northPanel,"North");
        add(centerPanel,"Center");
        add(southPanel,"South");

        setSize(300,300);

        setLocationRelativeTo(null);
    }

    public static void main(String []args) {
        new TicTacToe().setVisible(true);
    }

    private void computerBeurt() {
        if(!isSpeel) return;

        int []pos = game.nextMove(computer);
        if(pos!=null) {
            int i = pos[0];
            int j = pos[1];
            buttons[i][j].setText(chars[computer]);
            game.setBordValue(i,j,computer);
        }

        checkState();
    }

    private void gameOver(String s) {
        setStatus(s);
        setButtonsEnabled(false);
        isSpeel = false;
    }

    private void checkState() {
        if(game.isWin(speler1)) {
            gameOver("Speler 1 heeft gewonnen");
        }
        if(game.isWin(computer)) {
            gameOver("Computer heeft gewonnen");
        }
        if(game.nextMove(speler1)==null && game.nextMove(computer)==null) {
            gameOver("Gelijkspel");
        }
    }

    private void click(int i,int j) {
        if(game.getBordValue(i,j)==TicTacToeComputer.EMPTY) {
            buttons[i][j].setText(chars[speler1]);
            game.setBordValue(i,j,speler1);
            checkState();
            computerBeurt();
        }
    }

    public void actionPerformed(ActionEvent event) {
        if(event.getSource()==SpeelButton) {
            Speel();
        }else {
            for(int i=0;i<3;i++)
                for(int j=0;j<3;j++)
                    if(event.getSource()==buttons[i][j])
                        click(i,j);
        }
    }

    private void Speel() {
        game = new TicTacToeComputer();
        speler1 = TicTacToeComputer.ONE;
        computer = TicTacToeComputer.TWO;
        setStatus("Speler 1 is aan de beurt");
        setButtonsEnabled(true);
        isSpeel = true;
    }
}