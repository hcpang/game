package mancala.evaluation;

import mancala.MancalaGameState;
import mancala.MancalaMove;

public class EvansEvaluation extends AbstractMancalaEvaluation {

	private int weight;



	public EvansEvaluation(int weight) {
		super();
		this.weight = weight;
	}


	public int calculateNumCapturableByTop(short[] board) {
		int maxnumofTakableMarbles = 0;
		int finalIndex;
		for (int i = 13; i >= 8; i--) {
			finalIndex = i + board[i];
			if (finalIndex<=13 && board[i]>0) {
				if (board[finalIndex] == 0) {
					if (board[MancalaMove.OPPOSITE_LOOKUP[finalIndex]] + 1 > maxnumofTakableMarbles) {
						maxnumofTakableMarbles = board[MancalaMove.OPPOSITE_LOOKUP[finalIndex]] + 1;
					}
				}
			} else if (finalIndex%13 >= 8 && board[finalIndex%13] == 0  && finalIndex%13 <=i|| finalIndex%13 == i || i == 13 && board[i] == 13) {
				if (finalIndex/13 ==1) {
					if (board[MancalaMove.OPPOSITE_LOOKUP[finalIndex%13]] + 2 > maxnumofTakableMarbles) {
						maxnumofTakableMarbles = board[MancalaMove.OPPOSITE_LOOKUP[finalIndex%13]] + 2;
					}
				} else if (i == 13 && board[i] == 13) {
					if (board[MancalaMove.OPPOSITE_LOOKUP[13]] + 2 > maxnumofTakableMarbles) {
						maxnumofTakableMarbles = board[MancalaMove.OPPOSITE_LOOKUP[13]] + 2;
					}
				}
			}
		}
		return maxnumofTakableMarbles;
	}


	public int calculateNumBottomMarbles(short[] board) {
		int sum = 0;
		for (int i = 1; i <= 6; i++) {
			sum += board[i];
		}
		return sum;
	}

	public int calculateNumTopMarbles(short[] board) {
		int sum = 0;
		for (int i = 8; i <= 13; i++) {
			sum += board[i];
		}
		return sum;
	}


	public int calculateNumCapturableByBottom(short[] board) {
		int maxnumofVulnurableMarbles = 0; 
		int finalIndex;
		for (int i = 6; i >= 1; i--) {
			finalIndex = i + board[i];
			if (finalIndex<=6 && board[i]>0) {
				if (board[finalIndex] == 0) {
					if (board[MancalaMove.OPPOSITE_LOOKUP[finalIndex]] + 1 > maxnumofVulnurableMarbles) {
						maxnumofVulnurableMarbles = board[MancalaMove.OPPOSITE_LOOKUP[finalIndex]] + 1;
					}
				}
			} else if (finalIndex%13 <= 6 && board[finalIndex%13] == 0 && finalIndex%13 <= i|| finalIndex%13 == i) {
				if (finalIndex/13 ==1) {
					if (board[MancalaMove.OPPOSITE_LOOKUP[finalIndex%13]] + 2 > maxnumofVulnurableMarbles) {
						maxnumofVulnurableMarbles = board[MancalaMove.OPPOSITE_LOOKUP[finalIndex%13]] + 2;
					}
				}
			}
		}
		return maxnumofVulnurableMarbles; 
	}

	public int calculateEmptyPocketsTop(short[] board) {
		int numofEmptyPockets = 0;
		for (int i = 13; i>=8; i--) {
			if (board[i] == 0) {
				numofEmptyPockets +=1;
			}
		}
		return numofEmptyPockets;
	}
	
	public int calculateEmptyPocketsBottom(short[] board) {
		int numofEmptyPockets = 0;
		for (int i = 1; i<=6; i++) {
			if (board[i] == 0) {
				numofEmptyPockets +=1;
			}
		}
		return numofEmptyPockets;
	}

	public int calculateH4(boolean isBottomTurn, short[] board) {
		if (isBottomTurn) {
			return calculateNumCapturableByBottom(board);
		} else {
			return -calculateNumCapturableByTop(board);
		}

	}

	public int calculateMarbleDiff(short[] board) {
		return calculateNumBottomMarbles(board) - calculateNumTopMarbles(board);
	}
	
	@Override
	protected int evaluateBoardNonTerminalState(MancalaGameState state) {
		short[] board = state.getBoard();
		boolean isBottomTurn = state.currentlyMaximizing();

		int numBottomMarbles = calculateNumBottomMarbles(board);
		int numTopMarbles = calculateNumTopMarbles(board);
		
		int marbleDiff = numBottomMarbles - numTopMarbles;
		int h4 = calculateH4(isBottomTurn, board);
		int emptyPocketsDiff = calculateEmptyPocketsTop(board) - calculateEmptyPocketsBottom(board);

		//		return weight * sum + board[7]  - 2 * numofEmptyPockets;
		if (numBottomMarbles + numTopMarbles > 24) {
			return 100 * (board[7] - board[0]) + 56 * h4 + 37 * emptyPocketsDiff; 
		} else {
			return 100 * (board[7] - board[0]);
		}
	}


}
