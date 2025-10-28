# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Java-based game AI framework implementing Alpha-Beta pruning search for two games:
- **Mancala**: Traditional board game with marble distribution mechanics
- **Ultimate Tic-Tac-Toe**: Meta-game with 9 tic-tac-toe boards where winning a small board claims that square on the large board

The codebase includes a genetic algorithm system for evolving evaluation function weights for Ultimate Tic-Tac-Toe.

## Architecture

### Core Framework (common package)

The framework follows a generic game abstraction pattern:

- **GameState<M>**: Interface representing immutable game states. Implementations must provide:
  - `getMoves()`: Returns all legal moves
  - `makeMove(M move)`: Returns new GameState after applying move
  - `currentlyMaximizing()`: Player perspective for alpha-beta search

- **Evaluation<T>**: Interface for position evaluation functions. Returns integer scores where higher values favor the maximizing player.

- **Game<T, M>**: Adapter that wraps GameState and Evaluation to implement the `cb.alphabeta.Position` interface from the external AlphaBetaLib_v0.2.jar library.

- **MultithreadRunner**: Thread pool executor for running parallel game simulations (used in genetic algorithm tournaments).

### Alpha-Beta Search Integration

The codebase depends on `lib/AlphaBetaLib_v0.2.jar` which provides:
- `cb.alphabeta.AlphaBeta`: Alpha-beta search implementation
- `cb.alphabeta.Position`: Interface for game positions
- `cb.alphabeta.Move`: Base interface for moves

To use: Create a `Game` wrapper around your GameState and Evaluation, then call `new AlphaBeta(game).analyzeDepth(depth)` to get the best move.

### Mancala Implementation

- **MancalaGameState**: Immutable state with 14-element short array representing the board (2 stores + 12 pits)
  - Board layout: `[0, 1-6, 7, 8-13]` where 0=top store, 1-6=bottom pits, 7=bottom store, 8-13=top pits
  - Bottom player (maximizer) uses pits 1-6 and store 7
  - Top player (minimizer) uses pits 8-13 and store 0
- **MancalaMove**: Represents pit selection moves plus special SKIP_TURN_MOVE
- **MancalaMain**: Console-based game implementation
- **MancalaMainGUI**: GUI implementation using Java Swing
  - Extends MancalaMain and overrides `getMoveForBottom()` to use click events instead of console input
  - Uses a lock object for thread synchronization between GUI event thread and game logic thread
  - Displays clickable pits, stores, and current game status
  - Highlights valid moves in green
  - Shows "Play again?" dialog when game ends
- **Evaluation implementations**:
  - `SimpleDiffEvaluation`: Score difference
  - `TotalMarblesEvaluation`: Total marble count
  - `DaddysEvaluation`, `EvansEvaluation*`: More sophisticated heuristics
  - `AbstractMancalaEvaluation`: Base class for evaluations

### Ultimate Tic-Tac-Toe Implementation

- **UltimateTicTacToeGameState**: Uses BitSet arrays (length 9) to efficiently represent:
  - `circlePieces[i]`: Circle positions on board i
  - `crossPieces[i]`: Cross positions on board i
  - `boardsCapturedByCircle/Cross`: Which of the 9 boards each player has won
  - `boardIndexForCurrentMove`: Which board the current move must be played on

- **UltimateTicTacToeMove**: Stores board index and position within that board

- **Evaluation implementations**:
  - `GenericEvaluation`: Parameterized by `GenericEvaluationWeights` - used by genetic algorithm
  - `SimpleUltimateTicTacToeEvaluation`, `DaddysUltimateTicTacToeEvaluation*`, etc.
  - `AbstractUltimateTicTacToeEvaluation`: Base class that handles terminal states (returns ±10000 for wins)

- **BitSetUtils**: Utility for detecting "two in a row" patterns and potential winning moves

### Genetic Algorithm System (ultimatetictactoe.evaluation.weights)

- **GenericEvaluationWeights**: Encapsulates 7 tunable parameters:
  1. Boards captured weight
  2. Advantage board cardinality weight (two-in-a-row on meta-board)
  3. Two-in-a-row pieces weight (on individual boards)
  4. Two-in-a-row advantage weight
  5. Center board weight
  6. Offense weight
  7. Defense weight

- **WeightMutationsAndCrossovers**: Implements genetic algorithm tournament system:
  - Evolves populations of weight configurations
  - Each contestant plays against "grandmaster" baselines (DaddyEvaluationWeights, SimpleEvaluationWeights)
  - Top performers breed through mutation (±10% variation)
  - Uses MultithreadRunner for parallel game execution
  - Constants: 50 contestants, 20 threads, 30 generations, depth 7 search

- **PlayerWeightsandScores**: Pairs weight arrays with tournament scores

## Build and Run

### Building the Project

**Quick Build (Windows)**:

**From Git Bash (Claude Code)**:
Use the full path with quotes:
```bash
"/c/Users/yharm/git/game/build.bat"
```

**From Windows Command Prompt**:
```cmd
build.bat
```

**Important**: The working directory must be the project root (`C:\Users\yharm\git\game`), or you must use the full path as shown above. Commands like `cmd.exe //c build.bat` or `cd ... && build.bat` do NOT work reliably in Git Bash.

This script uses the Eclipse-bundled JDK 22 located at:
`C:\Users\yharm\.p2\pool\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_22.0.2.v20240802-1626\jre\bin\javac.exe`

The script compiles all main source files (excluding tests, which require JUnit libraries) into the `bin/` directory.

**Manual Compilation**:
If the build script fails or you need to use a different Java installation:
```bash
javac -cp "lib/AlphaBetaLib_v0.2.jar" -d bin src/common/*.java src/mancala/*.java src/mancala/evaluation/*.java src/ultimatetictactoe/*.java src/ultimatetictactoe/evaluation/*.java src/ultimatetictactoe/evaluation/weights/*.java
```

**Important Notes**:
- Test files in `src/*/test/` are NOT compiled by the build script because they require JUnit 5 libraries not included in the classpath
- Eclipse handles building automatically (Project → Build Automatically)
- After making code changes, run `build.bat` before running the games from command line
- The `bin/` directory contains all compiled `.class` files and is the output directory

### Running Games

**Mancala (Human vs Computer - GUI)**:
```bash
java -cp "bin;lib/AlphaBetaLib_v0.2.jar" mancala.MancalaMainGUI
```
This launches a graphical interface where you can click on pits (1-6) to make moves. The computer plays as the top player.

**Mancala (Human vs Computer - Console)**:
```bash
java -cp "bin;lib/AlphaBetaLib_v0.2.jar" mancala.MancalaMain
```

**Mancala (Computer vs Computer)**:
```bash
java -cp "bin;lib/AlphaBetaLib_v0.2.jar" mancala.MancalaMainComputerVsComputer
```

**Ultimate Tic-Tac-Toe (Human vs Human)**:
```bash
java -cp "bin;lib/AlphaBetaLib_v0.2.jar" ultimatetictactoe.UltimateTicTacToeMain
```

**Ultimate Tic-Tac-Toe (Human vs Computer)**:
```bash
java -cp "bin;lib/AlphaBetaLib_v0.2.jar" ultimatetictactoe.UltimateTicTacToeMainHumanVsComputer
```

**Ultimate Tic-Tac-Toe (Computer vs Computer)**:
```bash
java -cp "bin;lib/AlphaBetaLib_v0.2.jar" ultimatetictactoe.UltimateTicTacToeMainComputerVsComputer
```

**Genetic Algorithm Training**:
```bash
java -cp "bin;lib/AlphaBetaLib_v0.2.jar" ultimatetictactoe.evaluation.weights.WeightMutationsAndCrossovers
```

Note: On Windows, use semicolon `;` as classpath separator. On Linux/Mac, use colon `:`.

### Running Tests

Tests use JUnit 5 (Jupiter). Example single test:
```bash
java -cp "bin;lib/AlphaBetaLib_v0.2.jar;path/to/junit-platform-console-standalone.jar" org.junit.platform.console.ConsoleLauncher --select-class mancala.test.TestMakeMove
```

Key test classes:
- `mancala.test.TestMakeMove`, `TestSkipTurn`, `TestEatOpposite`, `TestEvansEvaluation`, `TestGenerateRandomBoard`
- `ultimatetictactoe.test.TestUltimateTicTacToeGameState`, `TestBitSetUtils`, `TestEvansUltimateTicTacToeEvaluationV3`, `TestNumPiecesinLines`

## Code Patterns

### Creating New Evaluations

1. Extend appropriate abstract class (`AbstractMancalaEvaluation` or `AbstractUltimateTicTacToeEvaluation`)
2. Implement `evaluateNonTerminalState(T state)` - base class handles terminal states
3. Return higher scores for positions favoring current player
4. For Ultimate Tic-Tac-Toe, consider using `GenericEvaluation` with custom weights instead

### Adding New Games

1. Create GameState implementation with immutable state transitions
2. Create Move type extending `cb.alphabeta.Move`
3. Create Evaluation implementations
4. Create Main class that wraps state/evaluation in `Game<T, M>` and calls `AlphaBeta.analyzeDepth()`

### Modifying Game Rules

GameState implementations are immutable - `makeMove()` returns new instances. Do not modify fields of existing states. The `Game` class maintains a move history stack for alpha-beta search undo operations.
