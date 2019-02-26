package ws6;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.colorchooser.ColorSelectionModel;

public class KnightsTourUser extends JFrame {

	private Container contents;

	private JButton[][] squares = new JButton[8][8];

	private static int clickCounter = 0;// to show knight in starting square
	private static int squaresTouched = 0;

	private final static int[] horizontal = { 2, 1, -1, -2, -2, -1, 1, 2 };
	private final static int[] vertical = { -1, -2, -2, -1, 1, 2, 2, 1 };

	private int currentRow = 0;
	private int currentColumn = 0;

	private ImageIcon knight = new ImageIcon("blackKnight.png");

	public KnightsTourUser() {

		super("Knight's Tour");
		contents = getContentPane();
		contents.setLayout(new GridLayout(8, 8));

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu file = new JMenu("File");
		menuBar.add(file);

		JMenuItem restart = new JMenuItem("New User-directed Tour");
		file.add(restart);
		restart.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				dispose();

				clickCounter = 0;
				squaresTouched = 0;
				currentRow = 0;
				currentColumn = 0;

				new KnightsTourUser();
			}
		}));

		ButtonHandler handler = new ButtonHandler();

		// create the board
		// upper left corner of board is (0,0)
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares[i][j] = new JButton();
				if ((i + j) % 2 != 0)
					squares[i][j].setBackground(Color.black);
				if ((i + j) % 2 == 0)
					squares[i][j].setBackground(Color.white);
				contents.add(squares[i][j]);
				squares[i][j].addActionListener(handler);
			}
		}

		setSize(600, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void displayValidMoves(int i, int j) {

		int validMoveCount = 0;

		for (int index = 0; index < 8; index++) {
			if (currentRow + vertical[index] < 8 && currentRow + vertical[index] >= 0
					&& currentColumn + horizontal[index] < 8 && currentColumn + horizontal[index] >= 0
					&& squares[currentRow + vertical[index]][currentColumn + horizontal[index]]
							.getBackground() != Color.red) {
				validMoveCount++;
				squares[currentRow + vertical[index]][currentColumn + horizontal[index]].setBackground(Color.green);
			}
		}

		if (validMoveCount > 0)
			return;

		JOptionPane.showMessageDialog(null,
				"You've run out of moves! The knight touched " + squaresTouched + " squares.", "Tour Over",
				JOptionPane.PLAIN_MESSAGE);
	}

	private boolean isValidMove(int i, int j) {

		int rowDelta = Math.abs(i - currentRow);
		int colDelta = Math.abs(j - currentColumn);

		if (((rowDelta == 1 && colDelta == 2) || (colDelta == 1 && rowDelta == 2))
				&& squares[i][j].getBackground() != Color.red) {
			squaresTouched++;
			return true;
		}

		return false;
	}

	private void processClick(int i, int j) {
		if (isValidMove(i, j) == false)
			return;
		squares[currentRow][currentColumn].setIcon(null);
		squares[i][j].setIcon(knight);
		currentRow = i;
		currentColumn = j;
		squares[currentRow][currentColumn].setBackground(Color.red);

		for (int k = 0; k < 8; k++) {
			for (int l = 0; l < 8; l++) {
				if (squares[k][l].getBackground() == Color.green) {
					if ((k + l) % 2 != 0)
						squares[k][l].setBackground(Color.black);
					if ((k + l) % 2 == 0)
						squares[k][l].setBackground(Color.white);
				}
			}
		}

		displayValidMoves(i, j);
	}

	private class ButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (source == squares[i][j]) {
						if (clickCounter == 0) {
							currentRow = i;
							currentColumn = j;
							squares[currentRow][currentColumn].setIcon(knight);
							squares[currentRow][currentColumn].setBackground(Color.red);
							clickCounter++;
						}
						displayValidMoves(i, j);
						processClick(i, j);
						return;
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		new KnightsTourUser();
	}
}
