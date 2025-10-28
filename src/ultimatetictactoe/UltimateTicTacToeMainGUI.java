package ultimatetictactoe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.BitSet;

import common.Evaluation;
import ultimatetictactoe.evaluation.DaddysUltimateTicTacToeEvaluation;

public class UltimateTicTacToeMainGUI extends UltimateTicTacToeMain {

	private JFrame frame;
	private JButton[][] cellButtons; // [board][position]
	private JPanel[] boardPanels;
	private JLabel statusLabel;
	private JLabel boardStatusLabel;
	private UltimateTicTacToeGameState currentState;
	private volatile UltimateTicTacToeMove selectedMove = null;
	private final Object moveLock = new Object();

	public UltimateTicTacToeMainGUI() {
		super();
		initializeGUI();
	}

	private void initializeGUI() {
		frame = new JFrame("Ultimate Tic-Tac-Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 850);
		frame.setLayout(new BorderLayout(10, 10));

		// Status panel at top
		JPanel statusPanel = new JPanel(new GridLayout(2, 1));
		statusLabel = new JLabel("Ultimate Tic-Tac-Toe - Circle (O) starts!", SwingConstants.CENTER);
		statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
		boardStatusLabel = new JLabel("Play on board: 4 (center)", SwingConstants.CENTER);
		boardStatusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		statusPanel.add(statusLabel);
		statusPanel.add(boardStatusLabel);
		statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		frame.add(statusPanel, BorderLayout.NORTH);

		// Main game board - 3x3 grid of boards
		JPanel mainPanel = new JPanel(new GridLayout(3, 3, 10, 10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.setBackground(Color.BLACK);

		cellButtons = new JButton[9][9];
		boardPanels = new JPanel[9];

		// Create 9 boards
		for (int boardIdx = 0; boardIdx < 9; boardIdx++) {
			JPanel boardPanel = createBoardPanel(boardIdx);
			boardPanels[boardIdx] = boardPanel;
			mainPanel.add(boardPanel);
		}

		frame.add(mainPanel, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private JPanel createBoardPanel(int boardIdx) {
		JPanel boardPanel = new JPanel(new GridLayout(3, 3, 2, 2));
		boardPanel.setBackground(Color.GRAY);
		boardPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(Color.BLACK, 3),
			BorderFactory.createEmptyBorder(5, 5, 5, 5)
		));

		// Add board index label
		boardPanel.setLayout(new BorderLayout());
		JLabel boardLabel = new JLabel("Board " + boardIdx, SwingConstants.CENTER);
		boardLabel.setFont(new Font("Arial", Font.BOLD, 10));
		boardPanel.add(boardLabel, BorderLayout.NORTH);

		JPanel cellsPanel = new JPanel(new GridLayout(3, 3, 2, 2));
		cellsPanel.setBackground(Color.GRAY);

		// Create 9 cells for this board
		for (int pos = 0; pos < 9; pos++) {
			JButton cellButton = new JButton();
			cellButton.setPreferredSize(new Dimension(60, 60));
			cellButton.setFont(new Font("Arial", Font.BOLD, 36));
			cellButton.setBackground(Color.WHITE);
			cellButton.setFocusPainted(false);

			final int finalBoardIdx = boardIdx;
			final int finalPos = pos;
			cellButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					handleCellClick(finalBoardIdx, finalPos);
				}
			});

			cellButtons[boardIdx][pos] = cellButton;
			cellsPanel.add(cellButton);
		}

		boardPanel.add(cellsPanel, BorderLayout.CENTER);
		return boardPanel;
	}

	private void handleCellClick(int boardIdx, int position) {
		synchronized (moveLock) {
			if (selectedMove == null && currentState != null) {
				UltimateTicTacToeMove move = new UltimateTicTacToeMove(boardIdx, position);
				if (currentState.isValidMove(move)) {
					selectedMove = move;
					moveLock.notifyAll();
				}
			}
		}
	}

	private void updateBoard(UltimateTicTacToeGameState state) {
		currentState = state;

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BitSet[] circlePieces = state.getCirclePieces();
				BitSet[] crossPieces = state.getCrossPieces();
				BitSet boardsCapturedByCircle = state.getBoardsCapturedByCircle();
				BitSet boardsCapturedByCross = state.getBoardsCapturedByCross();
				int activeBoard = state.getBoardIndexForCurrentMove();

				// Update all cells
				for (int boardIdx = 0; boardIdx < 9; boardIdx++) {
					boolean isBoardCaptured = boardsCapturedByCircle.get(boardIdx) ||
											  boardsCapturedByCross.get(boardIdx);

					// Update board panel appearance
					if (boardIdx == activeBoard && !isBoardCaptured) {
						boardPanels[boardIdx].setBackground(new Color(144, 238, 144)); // Light green
						boardPanels[boardIdx].setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createLineBorder(Color.GREEN, 5),
							BorderFactory.createEmptyBorder(5, 5, 5, 5)
						));
					} else {
						boardPanels[boardIdx].setBackground(Color.LIGHT_GRAY);
						boardPanels[boardIdx].setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createLineBorder(Color.BLACK, 3),
							BorderFactory.createEmptyBorder(5, 5, 5, 5)
						));
					}

					// Show if board is captured
					if (boardsCapturedByCircle.get(boardIdx)) {
						boardPanels[boardIdx].setBackground(new Color(173, 216, 230)); // Light blue
						boardPanels[boardIdx].setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createLineBorder(Color.BLUE, 5),
							BorderFactory.createEmptyBorder(5, 5, 5, 5)
						));
					} else if (boardsCapturedByCross.get(boardIdx)) {
						boardPanels[boardIdx].setBackground(new Color(255, 182, 193)); // Light pink
						boardPanels[boardIdx].setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createLineBorder(Color.RED, 5),
							BorderFactory.createEmptyBorder(5, 5, 5, 5)
						));
					}

					for (int pos = 0; pos < 9; pos++) {
						JButton button = cellButtons[boardIdx][pos];

						if (circlePieces[boardIdx].get(pos)) {
							button.setText("O");
							button.setForeground(Color.BLUE);
							button.setEnabled(false);
						} else if (crossPieces[boardIdx].get(pos)) {
							button.setText("X");
							button.setForeground(Color.RED);
							button.setEnabled(false);
						} else {
							button.setText("");
							// Enable only if it's on the active board (can still play on captured boards)
							button.setEnabled(boardIdx == activeBoard && state.isCirclesTurn());

							if (button.isEnabled()) {
								button.setBackground(new Color(255, 255, 200)); // Light yellow
							} else {
								button.setBackground(Color.WHITE);
							}
						}
					}
				}

				// Update status labels
				String player = state.isCirclesTurn() ? "Circle (O)" : "Cross (X)";
				if (state.getMoves().isEmpty()) {
					if (state.hasCircleWon()) {
						statusLabel.setText("Game Over! Circle (O) wins!");
					} else if (state.hasCrossWon()) {
						statusLabel.setText("Game Over! Cross (X) wins!");
					} else {
						statusLabel.setText("Game Over! It's a draw!");
					}
					boardStatusLabel.setText("");
				} else {
					statusLabel.setText(player + "'s turn");
					boardStatusLabel.setText("Play on board: " + activeBoard);
				}
			}
		});

		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected UltimateTicTacToeMove getMoveForCircle(UltimateTicTacToeGameState state) {
		updateBoard(state);

		synchronized (moveLock) {
			selectedMove = null;
			while (selectedMove == null) {
				try {
					moveLock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return selectedMove;
		}
	}

	@Override
	protected UltimateTicTacToeMove getMoveForCross(UltimateTicTacToeGameState state) {
		updateBoard(state);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				statusLabel.setText("Computer (X) is thinking...");
			}
		});

		UltimateTicTacToeMove move = getMachineMove(state,
			new DaddysUltimateTicTacToeEvaluation(), 7);

		// Apply the move and display the result before returning
		UltimateTicTacToeGameState newState = (UltimateTicTacToeGameState) state.makeMove(move);
		updateBoard(newState);

		return move;
	}

	@Override
	public MatchResult playGame(UltimateTicTacToeGameState state, boolean printBoards) {
		updateBoard(state);
		MatchResult result = super.playGame(state, false);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String message;
				if (result == MatchResult.CIRCLE_WIN) {
					message = "Circle (O) wins!";
				} else if (result == MatchResult.CROSS_WIN) {
					message = "Computer (X) wins!";
				} else {
					message = "It's a draw!";
				}

				int choice = JOptionPane.showConfirmDialog(frame,
					message + "\n\nPlay again?",
					"Game Over",
					JOptionPane.YES_NO_OPTION);

				if (choice == JOptionPane.YES_OPTION) {
					// Reset all buttons
					for (int i = 0; i < 9; i++) {
						for (int j = 0; j < 9; j++) {
							cellButtons[i][j].setText("");
							cellButtons[i][j].setEnabled(false);
							cellButtons[i][j].setBackground(Color.WHITE);
						}
					}
					new Thread(new Runnable() {
						@Override
						public void run() {
							playGame();
						}
					}).start();
				} else {
					System.exit(0);
				}
			}
		});

		return result;
	}

	@Override
	public MatchResult playGame() {
		return playGame(new UltimateTicTacToeGameState(), true);
	}

	public void startGame() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				playGame();
			}
		}).start();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new UltimateTicTacToeMainGUI().startGame();
			}
		});
	}
}
