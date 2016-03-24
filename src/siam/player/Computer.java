package siam.player;

import siam.Constants;
import siam.Game;
import siam.level.*;

import java.util.ArrayList;
import java.util.Random;

public class Computer extends Player implements Constants {

    private Game game;
    private Camp winner;
    private Player human;

    private static Random random = new Random();

    public Computer(Game game, Camp camp, Player human) {
        super(camp, "Computer");
        this.game = game;
        this.human = human;
    }

    public void play(int depth) {
        if (depth <= 0) depth = 1;
        Board board = new Board(game.getBoard());
        winner = null;
        int max = -100000;
        int val;
        Move best_move = null;
        int[] best_coord = new int[2];
        Orientation best_orient = null;
        Orientation best_dir = null;
        int savePieceOnBoard = pieceOnBoard;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board.isFree(i,j)) {
                    if (board.isOnEdge(i,j)) {
                        if (canPut()) {
                            for (Orientation orientation : Orientation.values()) {
                                actionPut(board, i, j, orientation, this);
                                val = min(board, depth - 1);
                                if (val > max) {
                                    max = val;
                                    best_move = Move.PUT;
                                    best_coord = new int[]{i, j};
                                    best_orient = orientation;
                                } else if (val == max && random.nextInt(10) == 0) {
                                    best_move = Move.PUT;
                                    best_coord = new int[]{i, j};
                                    best_orient = orientation;
                                }
                                board = new Board(game.getBoard());
                                pieceOnBoard = savePieceOnBoard;
                                winner = null;
                            }
                        }
                    }
                } else {
                    if (board.isOnEdge(i,j)) {
                        if (canPut()) {
                            if (i == 0 && j == 0) {
                                if (testPutPush(board, i, j, Orientation.DOWN)) {
                                    actionPutPush(board, i, j, Orientation.DOWN, this);
                                    val = min(board, depth - 1);
                                    if (val > max) {
                                        max = val;
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.DOWN;
                                    } else if (val == max && random.nextInt(10) == 0) {
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.DOWN;
                                    }
                                    board = new Board(game.getBoard());
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                                if (testPutPush(board, i, j, Orientation.RIGTH)) {
                                    actionPutPush(board, i, j, Orientation.RIGTH, this);
                                    val = min(board, depth - 1);
                                    if (val > max) {
                                        max = val;
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.RIGTH;
                                    } else if (val == max && random.nextInt(10) == 0) {
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.RIGTH;
                                    }
                                    board = new Board(game.getBoard());
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (i == 0 && j == (BOARD_SIZE - 1)) {
                                if (testPutPush(board, i, j, Orientation.TOP)) {
                                    actionPutPush(board, i, j, Orientation.TOP, this);
                                    val = min(board, depth - 1);
                                    if (val > max) {
                                        max = val;
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.TOP;
                                    } else if (val == max && random.nextInt(10) == 0) {
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.TOP;
                                    }
                                    board = new Board(game.getBoard());
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                                if (testPutPush(board, i, j, Orientation.RIGTH)) {
                                    actionPutPush(board, i, j, Orientation.RIGTH, this);
                                    val = min(board, depth - 1);
                                    if (val > max) {
                                        max = val;
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.RIGTH;
                                    } else if (val == max && random.nextInt(10) == 0) {
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.RIGTH;
                                    }
                                    board = new Board(game.getBoard());
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (i == (BOARD_SIZE - 1) && j == 0) {
                                if (testPutPush(board, i, j, Orientation.DOWN)) {
                                    actionPutPush(board, i, j, Orientation.DOWN, this);
                                    val = min(board, depth - 1);
                                    if (val > max) {
                                        max = val;
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.DOWN;
                                    } else if (val == max && random.nextInt(10) == 0) {
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.DOWN;
                                    }
                                    board = new Board(game.getBoard());
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                                if (testPutPush(board, i, j, Orientation.LEFT)) {
                                    actionPutPush(board, i, j, Orientation.LEFT, this);
                                    val = min(board, depth - 1);
                                    if (val > max) {
                                        max = val;
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.LEFT;
                                    } else if (val == max && random.nextInt(10) == 0) {
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.LEFT;
                                    }
                                    board = new Board(game.getBoard());
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (i == (BOARD_SIZE - 1) && j == (BOARD_SIZE - 1)) {
                                if (testPutPush(board, i, j, Orientation.TOP)) {
                                    actionPutPush(board, i, j, Orientation.TOP, this);
                                    val = min(board, depth - 1);
                                    if (val > max) {
                                        max = val;
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.TOP;
                                    } else if (val == max && random.nextInt(10) == 0) {
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.TOP;
                                    }
                                    board = new Board(game.getBoard());
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                                if (testPutPush(board, i, j, Orientation.LEFT)) {
                                    actionPutPush(board, i, j, Orientation.LEFT, this);
                                    val = min(board, depth - 1);
                                    if (val > max) {
                                        max = val;
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.LEFT;
                                    } else if (val == max && random.nextInt(10) == 0) {
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.LEFT;
                                    }
                                    board = new Board(game.getBoard());
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (i == 0) {
                                if (testPutPush(board, i, j, Orientation.RIGTH)) {
                                    actionPutPush(board, i, j, Orientation.RIGTH, this);
                                    val = min(board, depth - 1);
                                    if (val > max) {
                                        max = val;
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.RIGTH;
                                    } else if (val == max && random.nextInt(10) == 0) {
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.RIGTH;
                                    }
                                    board = new Board(game.getBoard());
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (i == (BOARD_SIZE - 1)) {
                                if (testPutPush(board, i, j, Orientation.LEFT)) {
                                    actionPutPush(board, i, j, Orientation.LEFT, this);
                                    val = min(board, depth - 1);
                                    if (val > max) {
                                        max = val;
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.LEFT;
                                    } else if (val == max && random.nextInt(10) == 0) {
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.LEFT;
                                    }
                                    board = new Board(game.getBoard());
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (j == 0) {
                                if (testPutPush(board, i, j, Orientation.DOWN)) {
                                    actionPutPush(board, i, j, Orientation.DOWN, this);
                                    val = min(board, depth - 1);
                                    if (val > max) {
                                        max = val;
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.DOWN;
                                    } else if (val == max && random.nextInt(10) == 0) {
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.DOWN;
                                    }
                                    board = new Board(game.getBoard());
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (j == (BOARD_SIZE - 1)) {
                                if (testPutPush(board, i, j, Orientation.TOP)) {
                                    actionPutPush(board, i, j, Orientation.TOP, this);
                                    val = min(board, depth - 1);
                                    if (val > max) {
                                        max = val;
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.TOP;
                                    } else if (val == max && random.nextInt(10) == 0) {
                                        best_move = Move.PUT_PUSH;
                                        best_coord = new int[]{i, j};
                                        best_dir = Orientation.TOP;
                                    }
                                    board = new Board(game.getBoard());
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            }
                        }
                        if (board.getPiece(i,j) instanceof Animal) {
                            if (board.getPiece(i,j).getCamp() == camp) {
                                actionBringOut(board, i, j, this);
                                val = min(board, depth - 1);
                                if (val > max) {
                                    max = val;
                                    best_move = Move.BRINGOUT;
                                    best_coord = new int[]{i, j};
                                } else if (val == max && random.nextInt(10) == 0) {
                                    best_move = Move.BRINGOUT;
                                    best_coord = new int[]{i, j};
                                }
                                board = new Board(game.getBoard());
                                pieceOnBoard = savePieceOnBoard;
                                winner = null;
                            }
                        }
                    }
                    if (board.getPiece(i,j) instanceof Animal) {
                        if (board.getPiece(i,j).getCamp() == camp) {
                            int[] direction = new int[2];
                            switch (((Animal) board.getPiece(i,j)).getOrientation()) {
                                case TOP:
                                    direction[1] = -1;
                                    break;
                                case DOWN:
                                    direction[1] = 1;
                                    break;
                                case RIGTH:
                                    direction[0] = 1;
                                    break;
                                case LEFT:
                                    direction[0] = -1;
                                    break;
                            }
                            if (board.isInBound(i + direction[0], j + direction[1]) && !board.isFree(i + direction[0], j + direction[1])) {
                                if (testPush(board, i, j)) {
                                    actionPush(board, i, j, this);
                                    val = min(board, depth - 1);
                                    if (val > max) {
                                        max = val;
                                        best_move = Move.PUSH;
                                        best_coord = new int[]{i, j};
                                    } else if (val == max && random.nextInt(10) == 0) {
                                        best_move = Move.PUSH;
                                        best_coord = new int[]{i, j};
                                    }
                                    board = new Board(game.getBoard());
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            }
                            for(Orientation orientation: Orientation.values()) {
                                int[] coord = new int[2];
                                switch (orientation) {
                                    case TOP:
                                        coord = new int[]{i, j - 1};
                                        break;
                                    case DOWN:
                                        coord = new int[]{i, j + 1};
                                        break;
                                    case LEFT:
                                        coord = new int[]{i - 1, j};
                                        break;
                                    case RIGTH:
                                        coord = new int[]{i + 1, j};
                                        break;
                                }
                                if (board.isInBound(coord[0], coord[1])) {
                                    if (board.isFree(coord[0], coord[1])) {
                                        for (Orientation orient : Orientation.values()) {
                                            actionMove(board, i, j, orientation, orient);
                                            val = min(board, depth - 1);
                                            if (val > max) {
                                                max = val;
                                                best_move = Move.MOVE;
                                                best_coord = new int[]{i, j};
                                                best_dir = orientation;
                                                best_orient = orient;
                                            } else if (val == max && random.nextInt(10) == 0) {
                                                best_move = Move.MOVE;
                                                best_coord = new int[]{i, j};
                                                best_dir = orientation;
                                                best_orient = orient;
                                            }
                                            board = new Board(game.getBoard());
                                            pieceOnBoard = savePieceOnBoard;
                                            winner = null;
                                        }
                                    }
                                }
                                actionOrient(board, i, j, orientation);
                                val = min(board, depth - 1);
                                if (val > max) {
                                    max = val;
                                    best_move = Move.ORIENT;
                                    best_coord = new int[]{i, j};
                                    best_orient = orientation;
                                } else if (val == max && random.nextInt(10) == 0) {
                                    best_move = Move.ORIENT;
                                    best_coord = new int[]{i, j};
                                    best_orient = orientation;
                                }
                                board = new Board(game.getBoard());
                                pieceOnBoard = savePieceOnBoard;
                                winner = null;
                            }
                        }
                    }
                }
            }
        }

        switch (best_move) {
            case PUT:
                game.actionPut(best_coord, best_orient, this);
                break;
            case PUT_PUSH:
                game.actionPutPush(best_coord, best_dir, this);
                break;
            case BRINGOUT:
                game.actionBringOut(best_coord, this);
                break;
            case MOVE:
                game.actionMove(best_coord, best_dir, best_orient);
                break;
            case PUSH:
                game.actionPush(best_coord);
                break;
            case ORIENT:
                game.actionOrient(best_coord, best_orient);
                break;
        }
        game.nextPlayer();
    }

    private int max(Board gameBoard, int depth) {
        if (depth == 0 || winner(human) != 0) return eval(gameBoard, human);

        Board board = new Board(gameBoard);
        winner = null;
        int max = -100000;
        int val;
        int savePieceOnBoard = pieceOnBoard;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board.isFree(i,j)) {
                    if (board.isOnEdge(i,j)) {
                        if (canPut()) {
                            for (Orientation orientation : Orientation.values()) {
                                actionPut(board, i, j, orientation, this);
                                val = min(board, depth - 1);
                                if (val > max) max = val;
                                board = new Board(gameBoard);
                                pieceOnBoard = savePieceOnBoard;
                                winner = null;
                            }
                        }
                    }
                } else {
                    if (board.isOnEdge(i,j)) {
                        if (canPut()) {
                            if (i == 0 && j == 0) {
                                if (testPutPush(board, i, j, Orientation.DOWN)) {
                                    actionPutPush(board, i, j, Orientation.DOWN, this);
                                    val = min(board, depth - 1);
                                    if (val > max) max = val;
                                    board = new Board(gameBoard);
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                                if (testPutPush(board, i, j, Orientation.RIGTH)) {
                                    actionPutPush(board, i, j, Orientation.RIGTH, this);
                                    val = min(board, depth - 1);
                                    if (val > max) max = val;
                                    board = new Board(gameBoard);
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (i == 0 && j == (BOARD_SIZE - 1)) {
                                if (testPutPush(board, i, j, Orientation.TOP)) {
                                    actionPutPush(board, i, j, Orientation.TOP, this);
                                    val = min(board, depth - 1);
                                    if (val > max) max = val;
                                    board = new Board(gameBoard);
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                                if (testPutPush(board, i, j, Orientation.RIGTH)) {
                                    actionPutPush(board, i, j, Orientation.RIGTH, this);
                                    val = min(board, depth - 1);
                                    if (val > max) max = val;
                                    board = new Board(gameBoard);
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (i == (BOARD_SIZE - 1) && j == 0) {
                                if (testPutPush(board, i, j, Orientation.DOWN)) {
                                    actionPutPush(board, i, j, Orientation.DOWN, this);
                                    val = min(board, depth - 1);
                                    if (val > max) max = val;
                                    board = new Board(gameBoard);
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                                if (testPutPush(board, i, j, Orientation.LEFT)) {
                                    actionPutPush(board, i, j, Orientation.LEFT, this);
                                    val = min(board, depth - 1);
                                    if (val > max) max = val;
                                    board = new Board(gameBoard);
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (i == (BOARD_SIZE - 1) && j == (BOARD_SIZE - 1)) {
                                if (testPutPush(board, i, j, Orientation.TOP)) {
                                    actionPutPush(board, i, j, Orientation.TOP, this);
                                    val = min(board, depth - 1);
                                    if (val > max) max = val;
                                    board = new Board(gameBoard);
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                                if (testPutPush(board, i, j, Orientation.LEFT)) {
                                    actionPutPush(board, i, j, Orientation.LEFT, this);
                                    val = min(board, depth - 1);
                                    if (val > max) max = val;
                                    board = new Board(gameBoard);
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (i == 0) {
                                if (testPutPush(board, i, j, Orientation.RIGTH)) {
                                    actionPutPush(board, i, j, Orientation.RIGTH, this);
                                    val = min(board, depth - 1);
                                    if (val > max) max = val;
                                    board = new Board(gameBoard);
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (i == (BOARD_SIZE - 1)) {
                                if (testPutPush(board, i, j, Orientation.LEFT)) {
                                    actionPutPush(board, i, j, Orientation.LEFT, this);
                                    val = min(board, depth - 1);
                                    if (val > max) max = val;
                                    board = new Board(gameBoard);
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (j == 0) {
                                if (testPutPush(board, i, j, Orientation.DOWN)) {
                                    actionPutPush(board, i, j, Orientation.DOWN, this);
                                    val = min(board, depth - 1);
                                    if (val > max) max = val;
                                    board = new Board(gameBoard);
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (j == (BOARD_SIZE - 1)) {
                                if (testPutPush(board, i, j, Orientation.TOP)) {
                                    actionPutPush(board, i, j, Orientation.TOP, this);
                                    val = min(board, depth - 1);
                                    if (val > max) max = val;
                                    board = new Board(gameBoard);
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            }
                        }
                        if (board.getPiece(i, j) instanceof Animal) {
                            if (board.getPiece(i, j).getCamp() == camp) {
                                actionBringOut(board, i, j, this);
                                val = min(board, depth - 1);
                                if (val > max) max = val;
                                board = new Board(gameBoard);
                                pieceOnBoard = savePieceOnBoard;
                                winner = null;
                            }
                        }
                    }
                    if (board.getPiece(i,j) instanceof Animal) {
                        if (board.getPiece(i,j).getCamp() == camp) {
                            int[] direction = new int[2];
                            switch (((Animal) board.getPiece(i,j)).getOrientation()) {
                                case TOP:
                                    direction[1] = -1;
                                    break;
                                case DOWN:
                                    direction[1] = 1;
                                    break;
                                case RIGTH:
                                    direction[0] = 1;
                                    break;
                                case LEFT:
                                    direction[0] = -1;
                                    break;
                            }
                            if (board.isInBound(i + direction[0], j + direction[1]) && !board.isFree(i + direction[0], j + direction[1])) {
                                if (testPush(board, i, j)) {
                                    actionPush(board, i, j, this);
                                    val = min(board, depth - 1);
                                    if (val > max) max = val;
                                    board = new Board(gameBoard);
                                    pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            }
                            for(Orientation orientation: Orientation.values()) {
                                int[] coord = new int[2];
                                switch (orientation) {
                                    case TOP:
                                        coord = new int[]{i, j - 1};
                                        break;
                                    case DOWN:
                                        coord = new int[]{i, j + 1};
                                        break;
                                    case LEFT:
                                        coord = new int[]{i - 1, j};
                                        break;
                                    case RIGTH:
                                        coord = new int[]{i + 1, j};
                                        break;
                                }
                                if (board.isInBound(coord[0], coord[1]) && board.isFree(coord[0], coord[1])) {
                                    if (board.isFree(coord[0], coord[1])) {
                                        for (Orientation orient : Orientation.values()) {
                                            actionMove(board, i, j, orientation, orient);
                                            val = min(board, depth - 1);
                                            if (val > max) max = val;
                                            board = new Board(gameBoard);
                                            pieceOnBoard = savePieceOnBoard;
                                            winner = null;
                                        }
                                    }
                                }
                                actionOrient(board, i, j, orientation);
                                val = min(board, depth - 1);
                                if (val > max) max = val;
                                board = new Board(gameBoard);
                                pieceOnBoard = savePieceOnBoard;
                                winner = null;
                            }
                        }
                    }
                }
            }
        }

        return max;
    }

    private int min(Board gameBoard, int depth) {
        if (depth == 0 || winner(this) != 0) return eval(gameBoard, this);

        Board board = new Board(gameBoard);
        winner = null;
        int min = 100000;
        int val;
        int savePieceOnBoard = human.pieceOnBoard;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board.isFree(i,j)) {
                    if (board.isOnEdge(i,j)) {
                        if (canPut()) {
                            for (Orientation orientation : Orientation.values()) {
                                actionPut(board, i, j, orientation, human);
                                val = max(board, depth - 1);
                                if (val < min) min = val;
                                board = new Board(gameBoard);
                                human.pieceOnBoard = savePieceOnBoard;
                                winner = null;
                            }
                        }
                    }
                } else {
                    if (board.isOnEdge(i,j)) {
                        if (canPut()) {
                            if (i == 0 && j == 0) {
                                if (testPutPush(board, i, j, Orientation.DOWN)) {
                                    actionPutPush(board, i, j, Orientation.DOWN, human);
                                    val = max(board, depth - 1);
                                    if (val < min) min = val;
                                    board = new Board(gameBoard);
                                    human.pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                                if (testPutPush(board, i, j, Orientation.RIGTH)) {
                                    actionPutPush(board, i, j, Orientation.RIGTH, human);
                                    val = max(board, depth - 1);
                                    if (val < min) min = val;
                                    board = new Board(gameBoard);
                                    human.pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (i == 0 && j == (BOARD_SIZE - 1)) {
                                if (testPutPush(board, i, j, Orientation.TOP)) {
                                    actionPutPush(board, i, j, Orientation.TOP, human);
                                    val = max(board, depth - 1);
                                    if (val < min) min = val;
                                    board = new Board(gameBoard);
                                    human.pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                                if (testPutPush(board, i, j, Orientation.RIGTH)) {
                                    actionPutPush(board, i, j, Orientation.RIGTH, human);
                                    val = max(board, depth - 1);
                                    if (val < min) min = val;
                                    board = new Board(gameBoard);
                                    human.pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (i == (BOARD_SIZE - 1) && j == 0) {
                                if (testPutPush(board, i, j, Orientation.DOWN)) {
                                    actionPutPush(board, i, j, Orientation.DOWN, human);
                                    val = max(board, depth - 1);
                                    if (val < min) min = val;
                                    board = new Board(gameBoard);
                                    human.pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                                if (testPutPush(board, i, j, Orientation.LEFT)) {
                                    actionPutPush(board, i, j, Orientation.LEFT, human);
                                    val = max(board, depth - 1);
                                    if (val < min) min = val;
                                    board = new Board(gameBoard);
                                    human.pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (i == (BOARD_SIZE - 1) && j == (BOARD_SIZE - 1)) {
                                if (testPutPush(board, i, j, Orientation.TOP)) {
                                    actionPutPush(board, i, j, Orientation.TOP, human);
                                    val = max(board, depth - 1);
                                    if (val < min) min = val;
                                    board = new Board(gameBoard);
                                    human.pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                                if (testPutPush(board, i, j, Orientation.LEFT)) {
                                    actionPutPush(board, i, j, Orientation.LEFT, human);
                                    val = max(board, depth - 1);
                                    if (val < min) min = val;
                                    board = new Board(gameBoard);
                                    human.pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (i == 0) {
                                if (testPutPush(board, i, j, Orientation.RIGTH)) {
                                    actionPutPush(board, i, j, Orientation.RIGTH, human);
                                    val = max(board, depth - 1);
                                    if (val < min) min = val;
                                    board = new Board(gameBoard);
                                    human.pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (i == (BOARD_SIZE - 1)) {
                                if (testPutPush(board, i, j, Orientation.LEFT)) {
                                    actionPutPush(board, i, j, Orientation.LEFT, human);
                                    val = max(board, depth - 1);
                                    if (val < min) min = val;
                                    board = new Board(gameBoard);
                                    human.pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (j == 0) {
                                if (testPutPush(board, i, j, Orientation.DOWN)) {
                                    actionPutPush(board, i, j, Orientation.DOWN, human);
                                    val = max(board, depth - 1);
                                    if (val < min) min = val;
                                    board = new Board(gameBoard);
                                    human.pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            } else if (j == (BOARD_SIZE - 1)) {
                                if (testPutPush(board, i, j, Orientation.TOP)) {
                                    actionPutPush(board, i, j, Orientation.TOP, human);
                                    val = max(board, depth - 1);
                                    if (val < min) min = val;
                                    board = new Board(gameBoard);
                                    human.pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            }
                        }
                        if (board.getPiece(i,j) instanceof Animal) {
                            if (board.getPiece(i,j).getCamp() == human.camp) {
                                actionBringOut(board, i, j, human);
                                val = max(board, depth - 1);
                                if (val < min) min = val;
                                board = new Board(gameBoard);
                                human.pieceOnBoard = savePieceOnBoard;
                                winner = null;
                            }
                        }
                    }
                    if (board.getPiece(i,j) instanceof Animal) {
                        if (board.getPiece(i,j).getCamp() == human.camp) {
                            int[] direction = new int[2];
                            switch (((Animal) board.getPiece(i,j)).getOrientation()) {
                                case TOP:
                                    direction[1] = -1;
                                    break;
                                case DOWN:
                                    direction[1] = 1;
                                    break;
                                case RIGTH:
                                    direction[0] = 1;
                                    break;
                                case LEFT:
                                    direction[0] = -1;
                                    break;
                            }
                            if (board.isInBound(i + direction[0], j + direction[1]) && !board.isFree(i + direction[0], j + direction[1])) {
                                if (testPush(board, i, j)) {
                                    actionPush(board, i, j, human);
                                    val = max(board, depth - 1);
                                    if (val < min) min = val;
                                    board = new Board(gameBoard);
                                    human.pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            }
                            for(Orientation orientation: Orientation.values()) {
                                int[] coord = new int[2];
                                switch (orientation) {
                                    case TOP:
                                        coord = new int[]{i, j - 1};
                                        break;
                                    case DOWN:
                                        coord = new int[]{i, j + 1};
                                        break;
                                    case LEFT:
                                        coord = new int[]{i - 1, j};
                                        break;
                                    case RIGTH:
                                        coord = new int[]{i + 1, j};
                                        break;
                                }
                                if (board.isInBound(coord[0], coord[1]) && board.isFree(coord[0], coord[1])) {
                                    for(Orientation orient: Orientation.values()) {
                                        actionMove(board, i, j, orientation, orient);
                                        val = max(board, depth - 1);
                                        if (val < min) min = val;
                                        board = new Board(gameBoard);
                                        human.pieceOnBoard = savePieceOnBoard;
                                        winner = null;
                                    }
                                    actionOrient(board, i, j, orientation);
                                    val = max(board, depth - 1);
                                    if (val < min) min = val;
                                    board = new Board(gameBoard);
                                    human.pieceOnBoard = savePieceOnBoard;
                                    winner = null;
                                }
                            }
                        }
                    }
                }
            }
        }

        return min;
    }

    private void actionPut(Board board, int x, int y, Orientation orientation, Player player) {
        Animal animal = new Animal(x, y, player.getCamp(), orientation);
        board.putPiece(x, y, animal);
        player.put();
    }

    private void actionPutPush(Board board, int x, int y, Orientation direction, Player player) {
        ArrayList<int[]> pile = new ArrayList<>();
        int[] modDir = new int[2];
        switch (direction) {
            case TOP:
                modDir = new int[]{0, -1};
                break;
            case DOWN:
                modDir = new int[]{0, 1};
                break;
            case LEFT:
                modDir = new int[]{-1, 0};
                break;
            case RIGTH:
                modDir = new int[]{1, 0};
                break;
        }
        int nbCase = 0;
        while (board.isInBound(x + nbCase * modDir[0], y + nbCase * modDir[1])
                && !board.isFree(x + nbCase * modDir[0], y + nbCase * modDir[1])) {
            pile.add(new int[]{x + nbCase * modDir[0], y + nbCase * modDir[1]});
            nbCase++;
        }
        for (int i = pile.size() - 1; i >= 0; i--) {
            int[] newCoord = {pile.get(i)[0]+modDir[0], pile.get(i)[1]+modDir[1]};
            if (board.isInBound(newCoord[0], newCoord[1])) {
                if(board.getPiece(pile.get(i)[0],pile.get(i)[1]) instanceof Mountain) actionMove(board,
                        pile.get(i)[0], pile.get(i)[1], direction, Orientation.TOP);
                else actionMove(board, pile.get(i)[0], pile.get(i)[1], direction,
                        ((Animal) board.getPiece(pile.get(i)[0],pile.get(i)[1])).getOrientation());
            } else {
                if(board.getPiece(pile.get(i)[0],pile.get(i)[1]) instanceof Mountain) winner = player.getCamp();
                actionBringOut(board, pile.get(i)[0], pile.get(i)[1], player);
            }
        }
        actionPut(board, x, y, direction, player);
    }

    private void actionBringOut(Board board, int x, int y, Player player) {
        board.removePiece(new int[]{x, y});
        player.bringOut();
    }

    private void actionMove(Board board, int x, int y, Orientation direction, Orientation orientation) {
        int new_x = x;
        int new_y = y;
        switch (direction) {
            case TOP:
                new_y--;
                break;
            case DOWN:
                new_y++;
                break;
            case LEFT:
                new_x--;
                break;
            case RIGTH:
                new_x++;
                break;
        }
        board.movePiece(x, y, direction);
        if (board.getPiece(new_x,new_y) instanceof Animal) actionOrient(board, new_x, new_y, orientation);
    }

    private void actionPush(Board board, int x, int y, Player player) {
        ArrayList<int[]> pile = new ArrayList<>();
        Orientation direction = ((Animal) board.getPiece(x, y)).getOrientation();
        int[] modDir = new int[2];
        switch (direction) {
            case TOP:
                modDir = new int[]{0, -1};
                break;
            case DOWN:
                modDir = new int[]{0, 1};
                break;
            case LEFT:
                modDir = new int[]{-1, 0};
                break;
            case RIGTH:
                modDir = new int[]{1, 0};
                break;
        }
        int nbCase = 0;
        while (board.isInBound(x + nbCase * modDir[0], y + nbCase * modDir[1])
                && !board.isFree(x + nbCase * modDir[0], y + nbCase * modDir[1])) {
            pile.add(new int[]{x + nbCase * modDir[0], y + nbCase * modDir[1]});
            nbCase++;
        }
        for (int i = pile.size() - 1; i >= 0; i--) {
            int[] newCoord = {pile.get(i)[0]+modDir[0], pile.get(i)[1]+modDir[1]};
            if (board.isInBound(newCoord[0], newCoord[1])) {
                if(board.getPiece(pile.get(i)[0],pile.get(i)[1]) instanceof Mountain) actionMove(board,
                        pile.get(i)[0], pile.get(i)[1], direction, Orientation.TOP);
                else actionMove(board, pile.get(i)[0], pile.get(i)[1], direction,
                            ((Animal) board.getPiece(pile.get(i)[0],pile.get(i)[1])).getOrientation());
            } else {
                if(board.getPiece(pile.get(i)[0],pile.get(i)[1]) instanceof Mountain) winner = player.getCamp();
                actionBringOut(board, pile.get(i)[0], pile.get(i)[1], player);
            }
        }
    }

    private void actionOrient(Board board, int x, int y, Orientation orientation) {
        ((Animal) board.getPiece(x, y)).setOrientation(orientation);
    }

    private boolean testPutPush(Board board, int x, int y, Orientation direction) {
        ArrayList<Piece> pile = new ArrayList<>();
        int[] modDir = new int[2];
        switch (direction) {
            case TOP:
                modDir = new int[]{0, -1};
                break;
            case DOWN:
                modDir = new int[]{0, 1};
                break;
            case LEFT:
                modDir = new int[]{-1, 0};
                break;
            case RIGTH:
                modDir = new int[]{1, 0};
                break;
        }
        int nbCase = 0;
        int mountains = 0;
        int animalOpposed = 0;
        int animalOriented = 1;
        while (board.isInBound(x + nbCase * modDir[0], y + nbCase * modDir[1])
                && !board.isFree(x + nbCase * modDir[0], y + nbCase * modDir[1])) {
            if (board.getPiece(x + nbCase * modDir[0], y + nbCase * modDir[1]) instanceof Mountain) {
                mountains++;
            } else if (board.getPiece(x + nbCase * modDir[0], y + nbCase * modDir[1]) instanceof Animal) {
                Animal a = (Animal) board.getPiece(x + nbCase * modDir[0], y + nbCase * modDir[1]);
                if (oppositeDirection(direction, a.getOrientation())) {
                    animalOpposed++;
                } else if (direction == a.getOrientation()) {
                    animalOriented++;
                }
            }
            pile.add(board.getPiece(x + nbCase * modDir[0], y + nbCase * modDir[1]));
            nbCase++;
        }
        if (pile.size() == 0) return false;
        int offset = 1;
        while (pile.get(pile.size() - offset) instanceof Animal &&
                ((Animal) pile.get(pile.size() - offset)).getOrientation() == direction) {
            animalOriented--;
            offset++;
            if (pile.size() - offset < 0) {
                break;
            }
        }
        if (mountains == 0) {
            if (animalOriented > animalOpposed) {
                return true;
            }
        } else {
            if (animalOriented >= animalOpposed + mountains) {
                return true;
            }
        }
        return false;
    }

    private boolean testPush(Board board, int x, int y) {
        ArrayList<Piece> pile = new ArrayList<>();
        Orientation direction = ((Animal) board.getPiece(x, y)).getOrientation();
        int[] modDir = new int[2];
        switch (direction) {
            case TOP:
                modDir = new int[]{0, -1};
                break;
            case DOWN:
                modDir = new int[]{0, 1};
                break;
            case LEFT:
                modDir = new int[]{-1, 0};
                break;
            case RIGTH:
                modDir = new int[]{1, 0};
                break;
        }
        int nbCase = 1;
        int mountains = 0;
        int animalOpposed = 0;
        int animalOriented = 1;
        while (board.isInBound(x + nbCase * modDir[0], y + nbCase * modDir[1])
                && !board.isFree(x + nbCase * modDir[0], y + nbCase * modDir[1])) {
            if (board.getPiece(x + nbCase * modDir[0], y + nbCase * modDir[1]) instanceof Mountain) {
                mountains++;
            } else if (board.getPiece(x + nbCase * modDir[0], y + nbCase * modDir[1]) instanceof Animal) {
                Animal a = (Animal) board.getPiece(x + nbCase * modDir[0], y + nbCase * modDir[1]);
                if (oppositeDirection(direction, a.getOrientation())) {
                    animalOpposed++;
                } else if (direction == a.getOrientation()) {
                    animalOriented++;
                }
            }
            pile.add(board.getPiece(x + nbCase * modDir[0], y + nbCase * modDir[1]));
            nbCase++;
        }
        if (pile.size() == 0) return false;
        int offset = 1;
        while (pile.get(pile.size() - offset) instanceof Animal &&
                ((Animal) pile.get(pile.size() - offset)).getOrientation() == ((Animal) board.getPiece(x, y)).getOrientation()) {
            animalOriented--;
            offset++;
            if (pile.size() - offset < 0) {
                break;
            }
        }
        if (mountains == 0) {
            if (animalOriented > animalOpposed) {
                return true;
            }
        } else {
            if (animalOriented >= animalOpposed + mountains) {
                return true;
            }
        }
        return false;
    }

    private boolean oppositeDirection(Orientation o1, Orientation o2) {
        if(o1 == Orientation.DOWN && o2 == Orientation.TOP){
            return true;
        }
        else if(o1 == Orientation.LEFT && o2 == Orientation.RIGTH){
            return true;
        }
        else if(o1 == Orientation.RIGTH && o2 == Orientation.LEFT){
            return true;
        }
        else if(o1 == Orientation.TOP && o2 == Orientation.DOWN){
            return true;
        }
        return false;
    }

    private int eval(Board board, Player player) {
        if (winner(player) == 0) {
            int result = 0;
            for (int x = 0; x < (BOARD_SIZE - 1); x++) {
                for (int y = 0; y < (BOARD_SIZE - 1); y++) {
                    if (!board.isFree(x,y) && board.getPiece(x,y) instanceof Mountain) {
                        if (x != 0 && board.getPiece(x - 1,y) instanceof Animal) {
                            for (int i = x - 1; i >= 0 && !board.isFree(i,y); i--) {
                                if (board.getPiece(i,y) instanceof Animal &&
                                        ((Animal) board.getPiece(i,y)).getOrientation() == Orientation.RIGTH &&
                                        testPush(board, i, y)) {
                                    int u = x - 1;
                                    while (u >= i) {
                                        if (board.getPiece(u,y) instanceof Animal &&
                                                ((Animal) board.getPiece(u,y)).getOrientation() == Orientation.RIGTH) break;
                                        u--;
                                    }
                                    int modif;
                                    if (!board.isInBound(x + 1, y)) modif = 50000;
                                    else if (board.isOnEdge(x + 1, y)) modif = 500;
                                    else if (x + 1 == 2 && y == 2) modif = 5;
                                    else modif = 50;
                                    if (board.getPiece(u,y).getCamp() == camp) result += modif;
                                    else result -= modif;
                                }
                            }
                        }
                        if (x != (BOARD_SIZE - 1) && board.getPiece(x + 1,y) instanceof Animal) {
                            for (int i = x + 1; i < BOARD_SIZE && !board.isFree(i,y); i++) {
                                if (board.getPiece(i,y) instanceof Animal &&
                                        ((Animal) board.getPiece(i,y)).getOrientation() == Orientation.LEFT &&
                                        testPush(board, i, y)) {
                                    int u = x + 1;
                                    while (u <= i) {
                                        if (board.getPiece(u,y) instanceof Animal &&
                                                ((Animal) board.getPiece(u,y)).getOrientation() == Orientation.LEFT) break;
                                        u++;
                                    }
                                    int modif;
                                    if (!board.isInBound(x - 1, y)) modif = 50000;
                                    else if (board.isOnEdge(x - 1, y)) modif = 500;
                                    else if (x - 1 == 2 && y == 2) modif = 5;
                                    else modif = 50;
                                    if (board.getPiece(u,y).getCamp() == camp) result += modif;
                                    else result -= modif;
                                }
                            }
                        }
                        if (y != 0 && board.getPiece(x,y - 1) instanceof Animal) {
                            for (int j = y - 1; j >= 0 && !board.isFree(x,j); j--) {
                                if (board.getPiece(x,j) instanceof Animal &&
                                        ((Animal) board.getPiece(x,j)).getOrientation() == Orientation.DOWN &&
                                        testPush(board, x, j)) {
                                    int v = y - 1;
                                    while (v >= j) {
                                        if (board.getPiece(x,v) instanceof Animal &&
                                                ((Animal) board.getPiece(x,v)).getOrientation() == Orientation.DOWN) break;
                                        v--;
                                    }
                                    int modif;
                                    if (!board.isInBound(x, y - 1)) modif = 50000;
                                    else if (board.isOnEdge(x, y - 1)) modif = 500;
                                    else if (x == 2 && y - 1 == 2) modif = 5;
                                    else modif = 50;
                                    if (board.getPiece(x,v).getCamp() == camp) result += modif;
                                    else result -= modif;
                                }
                            }
                        }
                        if (y != (BOARD_SIZE - 1) && board.getPiece(x,y + 1) instanceof Animal) {
                            for (int j = y + 1; j < BOARD_SIZE && !board.isFree(x,j); j++) {
                                if (board.getPiece(x,j) instanceof Animal &&
                                        ((Animal) board.getPiece(x,j)).getOrientation() == Orientation.TOP &&
                                        testPush(board, x, j)) {
                                    int v = y + 1;
                                    while (v <= j) {
                                        if (board.getPiece(x,v) instanceof Animal &&
                                                ((Animal) board.getPiece(x,v)).getOrientation() == Orientation.TOP) break;
                                        v++;
                                    }
                                    int modif;
                                    if (!board.isInBound(x, y + 1)) modif = 50000;
                                    else if (board.isOnEdge(x, y + 1)) modif = 500;
                                    else if (x == 2 && y + 1 == 2) modif = 5;
                                    else modif = 50;
                                    if (board.getPiece(x,v).getCamp() == camp) result += modif;
                                    else result -= modif;
                                }
                            }
                        }
                    }
                }
            }
            return result;
        } else return winner(player);
    }

    private int winner(Player player) {
        if (winner == player.camp) return 100000;
        else if (winner != null) return -100000;
        return 0;
    }

}