package ws6;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.colorchooser.ColorSelectionModel;

public class KnightsTourHeuristic extends JFrame {

	private Container contents;

	private static JButton[][] squares = new JButton[8][8];

	private static int clickCounter = 0;// to show knight in starting square
	private static int squaresTouched = 0;
	private static int min = 0;
	private static int position = 0;
	private static boolean hasMoves = true;

	private final static int[] horizontal = { 2, 1, -1, -2, -2, -1, 1, 2 };
	private final static int[] vertical = { -1, -2, -2, -1, 1, 2, 2, 1 };

	private static int currentRow = 0;
	private static int currentColumn = 0;

	private static ImageIcon knight = new ImageIcon("blackKnight.png");

	private static int[][] accessibility = { { 2, 3, 4, 4, 4, 4, 3, 2 }, { 3, 4, 6, 6, 6, 6, 4, 3 },
			{ 4, 6, 8, 8, 8, 8, 6, 4 }, { 4, 6, 8, 8, 8, 8, 6, 4 }, { 4, 6, 8, 8, 8, 8, 6, 4 },
			{ 4, 6, 8, 8, 8, 8, 6, 4 }, { 3, 4, 6, 6, 6, 6, 4, 3 }, { 2, 3, 4, 4, 4, 4, 3, 2 } };

	public KnightsTourHeuristic() {

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

				new KnightsTourHeuristic();
			}
		}));

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
			}
		}

		squares[currentRow][currentColumn].setIcon(knight);
		squares[currentRow][currentColumn].setBackground(Color.red);

		setSize(600, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		while (hasMoves) {
			moveKnight(currentRow, currentColumn);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean processValidMoves(int i, int j) {

		min = 8;
		int validMoveCount = 0;

		for (int index = 0; index < 8; index++) {
			if (i + vertical[index] < 8 && i + vertical[index] >= 0 && j + horizontal[index] < 8
					&& j + horizontal[index] >= 0
					&& squares[i + vertical[index]][j + horizontal[index]].getBackground() != Color.red) {
				validMoveCount++;

				if (accessibility[i + vertical[index]][j + horizontal[index]] <= min) {
					min = accessibility[i + vertical[index]][j + horizontal[index]];
					position = index;
					System.out.println("Min: " + min + " position: " + position);
				}
			}
		}

		if (validMoveCount > 0)
			return true;

		JOptionPane.showMessageDialog(null, "The knight touched " + squaresTouched + " squares.", "Game Over",
				JOptionPane.PLAIN_MESSAGE);

		return false;

	}

	private static void moveKnight(int i, int j) {
		squares[i][j].setIcon(null);
		processValidMoves(i, j);
		currentRow += vertical[position];
		currentColumn += horizontal[position];
		System.out.println("Current Row: " + currentRow + " Current Column: " + currentColumn);
		squares[currentRow][currentColumn].setIcon(knight);
		squares[currentRow][currentColumn].setBackground(Color.red);
	}

	public static void main(String[] args) {
		new KnightsTourHeuristic();
	}
}
