package ws6;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class KnightsTourHeuristic extends JFrame {

	private static final long serialVersionUID = 1L;

	private Container contents;

	private static JButton[][] squares = new JButton[8][8];
	
	private static int squaresTouched = 0;
	private static int min = 0;
	private static int position = 0;
	private static boolean hasMoves = true;

	private final static int[] horizontal = { 2, 1, -1, -2, -2, -1, 1, 2 };
	private final static int[] vertical = { -1, -2, -2, -1, 1, 2, 2, 1 };

	private static int startingRow;
	private static int startingColumn;

	private static int currentRow = new Random().nextInt(8);
	private static int currentColumn = new Random().nextInt(8);

	private static ImageIcon knight = new ImageIcon("blackKnight.png");

	private static int[][] accessibility = { { 2, 3, 4, 4, 4, 4, 3, 2 }, { 3, 4, 6, 6, 6, 6, 4, 3 },
			{ 4, 6, 8, 8, 8, 8, 6, 4 }, { 4, 6, 8, 8, 8, 8, 6, 4 }, { 4, 6, 8, 8, 8, 8, 6, 4 },
			{ 4, 6, 8, 8, 8, 8, 6, 4 }, { 3, 4, 6, 6, 6, 6, 4, 3 }, { 2, 3, 4, 4, 4, 4, 3, 2 } };

	public KnightsTourHeuristic() {

		super("Knight's Tour");
		contents = getContentPane();
		contents.setLayout(new GridLayout(8, 8));

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

		startingRow = currentRow;
		startingColumn = currentColumn;

		squares[currentRow][currentColumn].setIcon(knight);
		squares[currentRow][currentColumn].setBackground(Color.red);

		setSize(600, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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
				}
			}
		}

		if (validMoveCount <= 0) {
			hasMoves = false;
			squares[startingRow][startingColumn].setBackground(Color.blue);
			squaresTouched++;
			JOptionPane.showMessageDialog(null, "The knight touched " + squaresTouched + " squares. Starting position: "
					+ startingRow + "," + startingColumn, "Tour Over", JOptionPane.PLAIN_MESSAGE);

		}
		return hasMoves;
	}

	private static void moveKnight(int i, int j) {
		if (!processValidMoves(i, j))
			return;
		squares[i][j].setIcon(null);
		currentRow += vertical[position];
		currentColumn += horizontal[position];
		squares[currentRow][currentColumn].setIcon(knight);
		squares[currentRow][currentColumn].setBackground(Color.red);
		squaresTouched++;
	}

	public static void main(String[] args) {
		new KnightsTourHeuristic();
	}
}
