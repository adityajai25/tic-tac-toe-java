import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGame extends JFrame {
    private JButton[][] buttons; // Grid of buttons representing the game board
    private char currentPlayer; // Current player (X or O)
    private JLabel statusLabel; // Label to display game status
    private JButton resetButton; // Button to reset the game

    public TicTacToeGame() {
        setTitle("Tic-Tac-Toe Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the game board
        buttons = new JButton[3][3];
        currentPlayer = 'X'; // Player X starts the game

        // Set layout for the game board
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
                buttons[i][j].addActionListener(new ButtonClickListener());
                boardPanel.add(buttons[i][j]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        // Status label to display game status
        statusLabel = new JLabel("Player " + currentPlayer + "'s turn");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);

        // Reset button to restart the game
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        add(resetButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ActionListener for button clicks
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton buttonClicked = (JButton) e.getSource();

            // Check if the clicked button is empty
            if (buttonClicked.getText().equals("")) {
                buttonClicked.setText(String.valueOf(currentPlayer)); // Set player's symbol on the button
                buttonClicked.setEnabled(false); // Disable the button after it's clicked

                // Check for a winner
                if (checkForWinner()) {
                    statusLabel.setText("Player " + currentPlayer + " wins!");
                    disableAllButtons();
                } else if (checkForDraw()) {
                    statusLabel.setText("It's a draw!");
                } else {
                    // Switch players
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                    statusLabel.setText("Player " + currentPlayer + "'s turn");
                }
            }
        }
    }

    // Check for a winner
    private boolean checkForWinner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText())
                    && buttons[i][0].getText().equals(buttons[i][2].getText())
                    && !buttons[i][0].getText().equals("")) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (buttons[0][j].getText().equals(buttons[1][j].getText())
                    && buttons[0][j].getText().equals(buttons[2][j].getText())
                    && !buttons[0][j].getText().equals("")) {
                return true;
            }
        }

        // Check diagonals
        if (buttons[0][0].getText().equals(buttons[1][1].getText())
                && buttons[0][0].getText().equals(buttons[2][2].getText())
                && !buttons[0][0].getText().equals("")) {
            return true;
        }

        if (buttons[0][2].getText().equals(buttons[1][1].getText())
                && buttons[0][2].getText().equals(buttons[2][0].getText())
                && !buttons[0][2].getText().equals("")) {
            return true;
        }

        return false;
    }

    // Check for a draw (all buttons filled)
    private boolean checkForDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false; // At least one button is empty, not a draw
                }
            }
        }
        return true; // All buttons are filled, it's a draw
    }

    // Disable all buttons after the game ends
    private void disableAllButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    // Reset the game to start a new round
    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        currentPlayer = 'X';
        statusLabel.setText("Player " + currentPlayer + "'s turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToeGame();
            }
        });
    }
}
