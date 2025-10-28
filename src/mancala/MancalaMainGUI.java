package mancala;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

import common.Evaluation;
import mancala.evaluation.DaddysEvaluation;

public class MancalaMainGUI extends MancalaMain {

	private JFrame frame;
	private JButton[] pitButtons;
	private JLabel[] pitLabels;
	private JLabel topStoreLabel;
	private JLabel bottomStoreLabel;
	private JLabel statusLabel;
	private MancalaGameState currentState;
	private volatile int selectedMove = -1;
	private final Object moveLock = new Object();

	public MancalaMainGUI() {
		super();
		initializeGUI();
	}

	private void initializeGUI() {
		frame = new JFrame("Mancala Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 400);
		frame.setLayout(new BorderLayout(10, 10));

		// Status label at top
		statusLabel = new JLabel("Mancala Game - Your turn!", SwingConstants.CENTER);
		statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
		statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		frame.add(statusLabel, BorderLayout.NORTH);

		// Main game board panel
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new BorderLayout(10, 10));
		boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Create stores
		topStoreLabel = createStoreLabel("0");
		bottomStoreLabel = createStoreLabel("0");

		// Create the pits grid
		JPanel pitsPanel = new JPanel();
		pitsPanel.setLayout(new GridLayout(2, 6, 10, 10));

		pitButtons = new JButton[14];
		pitLabels = new JLabel[14];

		// Top row (opponent's pits: 13 to 8, displayed left to right)
		for (int i = 13; i >= 8; i--) {
			JPanel pitPanel = createPitPanel(i, false);
			pitsPanel.add(pitPanel);
		}

		// Bottom row (player's pits: 1 to 6, displayed left to right)
		for (int i = 1; i <= 6; i++) {
			JPanel pitPanel = createPitPanel(i, true);
			pitsPanel.add(pitPanel);
		}

		// Layout stores and pits
		JPanel mainBoard = new JPanel(new BorderLayout(10, 10));
		mainBoard.add(topStoreLabel, BorderLayout.WEST);
		mainBoard.add(pitsPanel, BorderLayout.CENTER);
		mainBoard.add(bottomStoreLabel, BorderLayout.EAST);

		boardPanel.add(mainBoard, BorderLayout.CENTER);
		frame.add(boardPanel, BorderLayout.CENTER);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private JLabel createStoreLabel(String count) {
		JLabel label = new JLabel("<html><center>Store<br>" + count + "</center></html>", SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 24));
		label.setOpaque(true);
		label.setBackground(new Color(139, 69, 19));
		label.setForeground(Color.WHITE);
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		label.setPreferredSize(new Dimension(100, 150));
		return label;
	}

	private JPanel createPitPanel(int position, boolean isClickable) {
		JPanel panel = new JPanel(new BorderLayout());

		JLabel label = new JLabel("0", SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 32));
		pitLabels[position] = label;

		JButton button = new JButton();
		button.setPreferredSize(new Dimension(80, 80));
		button.setBackground(new Color(210, 180, 140));
		button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		button.setLayout(new BorderLayout());
		button.add(label, BorderLayout.CENTER);

		if (isClickable) {
			final int pos = position;
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					handlePitClick(pos);
				}
			});
		} else {
			button.setEnabled(false);
		}

		pitButtons[position] = button;

		// Add position number label
		JLabel posLabel = new JLabel(String.valueOf(position), SwingConstants.CENTER);
		posLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		panel.add(posLabel, BorderLayout.NORTH);
		panel.add(button, BorderLayout.CENTER);

		return panel;
	}

	private void handlePitClick(int position) {
		synchronized (moveLock) {
			if (selectedMove == -1 && currentState != null && currentState.isValidMove(position)) {
				selectedMove = position;
				moveLock.notifyAll();
			}
		}
	}

	private void updateBoard(MancalaGameState state) {
		currentState = state;
		short[] board = state.getBoard();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Update pit counts
				for (int i = 1; i <= 13; i++) {
					if (pitLabels[i] != null) {
						pitLabels[i].setText(String.valueOf(board[i]));
					}
				}

				// Update stores
				topStoreLabel.setText("<html><center>Store<br>" + board[0] + "</center></html>");
				bottomStoreLabel.setText("<html><center>Store<br>" + board[7] + "</center></html>");

				// Update button states based on valid moves
				List<MancalaMove> moves = state.getMoves();
				boolean isBottomTurn = state.currentlyMaximizing();

				for (int i = 1; i <= 6; i++) {
					pitButtons[i].setEnabled(false);
					pitButtons[i].setBackground(new Color(210, 180, 140));
				}

				if (isBottomTurn && !state.isSkipTurn()) {
					for (MancalaMove move : moves) {
						int pos = move.getPosition();
						if (pos >= 1 && pos <= 6) {
							pitButtons[pos].setEnabled(true);
							pitButtons[pos].setBackground(new Color(144, 238, 144));
						}
					}
				}

				// Update status
				if (state.getMoves().isEmpty()) {
					statusLabel.setText("Game Over! Top: " + state.getTopScore() + " Bottom: " + state.getBottomScore());
				} else if (state.isSkipTurn()) {
					statusLabel.setText(state.getCurrentPlayer() + " gets another turn!");
				} else {
					statusLabel.setText(state.getCurrentPlayer() + "'s turn");
				}
			}
		});

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected MancalaMove getMoveForBottom(MancalaGameState state) {
		updateBoard(state);

		synchronized (moveLock) {
			selectedMove = -1;
			while (selectedMove == -1) {
				try {
					moveLock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return MancalaMove.getMove(selectedMove);
		}
	}

	@Override
	protected MancalaMove getMoveForTop(MancalaGameState state) {
		updateBoard(state);
		statusLabel.setText("Computer is thinking...");
		MancalaMove move = getMachineMove(state, new DaddysEvaluation(), 15);
		return move;
	}

	@Override
	public int playGame(MancalaGameState state) {
		updateBoard(state);
		int result = super.playGame(state);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				int topScore = currentState.getTopScore();
				int bottomScore = currentState.getBottomScore();
				String winner;
				if (topScore > bottomScore) {
					winner = "Computer (Top) wins!";
				} else if (bottomScore > topScore) {
					winner = "You (Bottom) win!";
				} else {
					winner = "It's a tie!";
				}
				statusLabel.setText("Game Over! " + winner + " Top: " + topScore + " Bottom: " + bottomScore);

				int choice = JOptionPane.showConfirmDialog(frame,
					winner + "\nTop: " + topScore + " Bottom: " + bottomScore + "\n\nPlay again?",
					"Game Over",
					JOptionPane.YES_NO_OPTION);

				if (choice == JOptionPane.YES_OPTION) {
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
	public void playGame() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				playGame(new MancalaGameState(true, false, new short[] {0, 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4}));
			}
		}).start();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MancalaMainGUI().playGame();
			}
		});
	}
}
