package siam;

import siam.audio.Music;
import siam.audio.SoundsLibrary;
import siam.graphics.FontTools;
import siam.graphics.Sprite;
import siam.graphics.TextureManager;
import siam.level.*;
import siam.player.Camp;
import siam.player.Computer;
import siam.player.Player;
import siam.player.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Game implements Runnable, ActionListener, Constants, Texts {

    private FontTools fontTools = new FontTools();
    private Music music;
    private SoundsLibrary soundsLibrary;

    private Theme theme = Theme.STANDARD;

    private Board board;
    private Player[] players;
    private int playerActive;
    private boolean putActive, moveActive, orientActive, bringOutActive, upActive,downActive,leftActive,rightActive;
    private boolean enableUp, enableDown, enableLeft, enableRight;
    private boolean computerPlay;

    private boolean songEnable = false;
    private boolean variantMountainOn;
    private boolean variantPieceOn;
    private boolean variantTileOn;

    private JFrame frame;
    private JLabel playerName;
    private JButton put;
    private JButton bringOut;
    private JButton move;
    private JButton orient;
    private JButton top;
    private JButton left;
    private JButton right;
    private JButton bottom;
    private JMenuItem newGame;
    private JMenuItem rules;
    private JMenuItem backToMenu;
    private JMenuItem song;

    private MouseHandler mouse;
    private Thread thread;
    private int nbTours;
    private boolean running = false;

    public Game(JFrame frame, Music music, SoundsLibrary soundsLibrary,
                boolean songEnable, boolean variantMountainOn,
                boolean variantPieceOn, boolean variantTileOn,
                Theme theme, String black, String white) {
        this.music = music;
        this.soundsLibrary = soundsLibrary;
        this.songEnable = songEnable;
        this.theme = theme;

        this.variantMountainOn = variantMountainOn;
        this.variantPieceOn = variantPieceOn;
        this.variantTileOn = variantTileOn;

        board = new Board(BOARD_SIZE, variantMountainOn, variantTileOn);
        players = new Player[2];
        playerActive = 0;
        players[0] = new Player(Camp.WHITE, white);
        players[1] = new Player(Camp.BLACK, black);

        this.frame = frame;

        Dimension dimension = new Dimension(WIN_WIDTH, WIN_HEIGTH);
        frame.setPreferredSize(dimension);

        putActive = moveActive = orientActive = bringOutActive = upActive = downActive = rightActive = leftActive =  false;
        enableUp = enableDown = enableRight = enableLeft = false;
        initFrame();
        renderFrame();

        mouse = new MouseHandler();
        setControl(this);
        nbTours = 0;

        frame.setVisible(true);

        setButtonEnabled();
        start();
    }

    public Game(JFrame frame, Music music, SoundsLibrary soundsLibrary,
                boolean songEnable, Theme theme,
                String black, boolean blackChoose, String white) {
        this.music = music;
        this.soundsLibrary = soundsLibrary;
        this.songEnable = songEnable;
        this.theme = theme;

        board = new Board(BOARD_SIZE, variantMountainOn, variantTileOn);
        players = new Player[2];
        playerActive = 0;
        if (blackChoose) {
            players[1] = new Player(Camp.BLACK, black);
            players[0] = new Computer(this, Camp.WHITE, players[1]);
        } else {
            players[0] = new Player(Camp.WHITE, white);
            players[1] = new Computer(this, Camp.BLACK, players[0]);
        }
        computerPlay = false;

        this.frame = frame;

        Dimension dimension = new Dimension(WIN_WIDTH, WIN_HEIGTH);
        frame.setPreferredSize(dimension);

        putActive = moveActive = orientActive = bringOutActive = upActive = downActive = rightActive = leftActive =  false;
        enableUp = enableDown = enableRight = enableLeft = false;
        initFrame();
        renderFrame();

        mouse = new MouseHandler();
        setControl(this);
        nbTours = 0;

        frame.setVisible(true);

        setButtonEnabled();
        start();
    }

    public Board getBoard() {
        return board;
    }

    private void initFrame() {
        playerName = new JLabel(players[playerActive].getName());
        put = new JButton();
        bringOut = new JButton();
        move = new JButton();
        orient = new JButton();
        top = new JButton();
        left = new JButton();
        right = new JButton();
        bottom = new JButton();

        newGame = new JMenuItem(NEWGAME_BAR);
        rules = new JMenuItem(RULES_BAR);
        backToMenu = new JMenuItem(BACKTOMENU_BAR);
        if (songEnable) {
            song = new JMenuItem(SONG_ENABLE_BAR);
        } else {
            song = new JMenuItem(SONG_DISABLE_BAR);
        }
    }

    private void renderFrame() {
        JPanel mainPanel = new JPanel();
        JPanel buttonPanel = new JPanel() {
            BufferedImage image = TextureManager.library.getImage(theme, "Button Background");
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                frame.repaint();
                g.drawImage(image, 0, 0, BUTTON_WIDTH, WIN_HEIGTH-TOPBAR_HEIGHT, this);
            }
        };
        Dimension dimension = new Dimension(BUTTON_WIDTH, WIN_HEIGTH);
        buttonPanel.setPreferredSize(dimension);
        JPanel playerNamePanel = new JPanel();
        JPanel putPanel = new JPanel();
        JPanel bringOutPanel = new JPanel();
        JPanel movePanel = new JPanel();
        JPanel orientPanel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel sidePanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        mainPanel.setOpaque(false);
        buttonPanel.setOpaque(false);
        playerNamePanel.setOpaque(false);
        putPanel.setOpaque(false);
        bringOutPanel.setOpaque(false);
        movePanel.setOpaque(false);
        orientPanel.setOpaque(false);
        topPanel.setOpaque(false);
        sidePanel.setOpaque(false);
        leftPanel.setOpaque(false);
        rightPanel.setOpaque(false);
        bottomPanel.setOpaque(false);

        put.setIcon(new ImageIcon(TextureManager.library.getImage(theme, "Button Put")));
        move.setIcon(new ImageIcon(TextureManager.library.getImage(theme, "Button Move")));
        orient.setIcon(new ImageIcon(TextureManager.library.getImage(theme, "Button Orient")));
        bringOut.setIcon(new ImageIcon(TextureManager.library.getImage(theme, "Button Bring Out")));
        top.setIcon(new ImageIcon(TextureManager.library.getImage(theme, "Button Top")));
        left.setIcon(new ImageIcon(TextureManager.library.getImage(theme, "Button Left")));
        right.setIcon(new ImageIcon(TextureManager.library.getImage(theme, "Button Right")));
        bottom.setIcon(new ImageIcon(TextureManager.library.getImage(theme, "Button Bottom")));

        updateFonts();

        playerNamePanel.add(playerName);
        putPanel.add(put);
        bringOutPanel.add(bringOut);
        movePanel.add(move);
        orientPanel.add(orient);
        topPanel.add(top);
        leftPanel.add(left);
        rightPanel.add(right);
        bottomPanel.add(bottom);

        sidePanel.setLayout(new GridLayout(1, 2));
        sidePanel.add(leftPanel);
        sidePanel.add(rightPanel);

        buttonPanel.setLayout(new GridLayout(8, 1));
        buttonPanel.add(playerNamePanel);
        buttonPanel.add(putPanel);
        buttonPanel.add(bringOutPanel);
        buttonPanel.add(movePanel);
        buttonPanel.add(orientPanel);
        buttonPanel.add(topPanel);
        buttonPanel.add(sidePanel);
        buttonPanel.add(bottomPanel);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.add(board);
        mainPanel.add(buttonPanel);

        frame.setContentPane(mainPanel);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(WIN_WIDTH, TOPBAR_HEIGHT));
        JMenu menu = new JMenu(MENU_BAR);
        JMenu option = new JMenu(OPTION_BAR);

        menu.add(newGame);
        menu.add(rules);
        menu.add(backToMenu);

        option.add(song);

        menuBar.add(menu);
        menuBar.add(option);

        frame.setJMenuBar(menuBar);
    }

    private void updateFonts() {
        if (theme == Theme.CHRISTMAS) {
            fontTools.updateFontJLabel(playerName, 70, Color.red, fontTools.getTextFontChristmas());
            fontTools.updateFontJButton(put, 60, Color.red, fontTools.getTextFontChristmas());
            fontTools.updateFontJButton(bringOut, 60, Color.red, fontTools.getTextFontChristmas());
            fontTools.updateFontJButton(move, 60, Color.red, fontTools.getTextFontChristmas());
            fontTools.updateFontJButton(orient, 60, Color.red, fontTools.getTextFontChristmas());
            fontTools.updateFontJButton(top, 60, Color.red, fontTools.getTextFontChristmas());
            fontTools.updateFontJButton(left, 60, Color.red, fontTools.getTextFontChristmas());
            fontTools.updateFontJButton(right, 60, Color.red, fontTools.getTextFontChristmas());
            fontTools.updateFontJButton(bottom, 60, Color.red, fontTools.getTextFontChristmas());
        } else if (theme == Theme.STARWARS) {
            fontTools.updateFontJLabel(playerName, 40, Color.black, fontTools.getTextFontStarWars());
            fontTools.updateFontJButton(put, 30, Color.yellow, fontTools.getTextFontStarWars());
            fontTools.updateFontJButton(bringOut, 30, Color.yellow, fontTools.getTextFontStarWars());
            fontTools.updateFontJButton(move, 30, Color.yellow, fontTools.getTextFontStarWars());
            fontTools.updateFontJButton(orient, 30, Color.yellow, fontTools.getTextFontStarWars());
            fontTools.updateFontJButton(top, 30, Color.yellow, fontTools.getTextFontStarWars());
            fontTools.updateFontJButton(left, 30, Color.yellow, fontTools.getTextFontStarWars());
            fontTools.updateFontJButton(right, 30, Color.yellow, fontTools.getTextFontStarWars());
            fontTools.updateFontJButton(bottom, 30, Color.yellow, fontTools.getTextFontStarWars());
        } else {
            fontTools.updateFontJLabel(playerName, 50, Color.orange, fontTools.getTextFont());
            fontTools.updateFontJButton(put, 30, Color.orange, fontTools.getTextFont());
            fontTools.updateFontJButton(bringOut, 30, Color.orange, fontTools.getTextFont());
            fontTools.updateFontJButton(move, 30, Color.orange, fontTools.getTextFont());
            fontTools.updateFontJButton(orient, 30, Color.orange, fontTools.getTextFont());
            fontTools.updateFontJButton(top, 30, Color.orange, fontTools.getTextFont());
            fontTools.updateFontJButton(left, 30, Color.orange, fontTools.getTextFont());
            fontTools.updateFontJButton(right, 30, Color.orange, fontTools.getTextFont());
            fontTools.updateFontJButton(bottom, 30, Color.orange, fontTools.getTextFont());
        }
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        frame.requestFocus();
        while(running) {
            update();
            render();
        }
        stop();
    }

    public void update() {
        setButtonEnabled();
        boolean actionPerformed = false;
        if (players[playerActive] instanceof Computer) {
            if (!computerPlay) {
                ((Computer) players[playerActive]).play(2); // 2 -> 1 coup d'avance
                computerPlay = true;
            }
        } else {
            if (!board.pieceSelected()) {
                if (mouse.getClick()[0] < 0 || mouse.getClick()[0] >= BOARD_SIZE ||
                        mouse.getClick()[1] < 0 || mouse.getClick()[1] >= BOARD_SIZE) return;
                if (putActive) actionPerformed = actionPut();
                selectPiece();
            } else {
                if (moveActive) actionPerformed = testMove();
                else if (bringOutActive) actionPerformed = actionBringOut();
                else if (orientActive) actionPerformed = actionOrient();
            }
            if (actionPerformed) nextPlayer();
            if (mouse.isRightClick() && !orientActive) {
                board.deselect();
                resetButton();
                setButtonSelected(10);
            }
        }
    }

    private void resetButton() {
        put.setIcon(new ImageIcon(TextureManager.library.getImage(theme, "Button Put")));
        move.setIcon(new ImageIcon(TextureManager.library.getImage(theme, "Button Move")));
    }

    public void render() {
        board.repaint();
    }

    private void setControl(ActionListener actionListener) {
        put.addActionListener(actionListener);
        bringOut.addActionListener(actionListener);
        move.addActionListener(actionListener);
        orient.addActionListener(actionListener);
        top.addActionListener(actionListener);
        left.addActionListener(actionListener);
        right.addActionListener(actionListener);
        bottom.addActionListener(actionListener);
        newGame.addActionListener(actionListener);
        rules.addActionListener(actionListener);
        backToMenu.addActionListener(actionListener);
        song.addActionListener(actionListener);
        board.addMouseListener(mouse);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        soundsLibrary.playButtonSound();
        mouse.openClick();
        if (source == newGame) {
            frame.setJMenuBar(null);
            new Menu(frame, false, true, music, soundsLibrary, songEnable, theme);
        } else if (source == rules) {
            try {
                Desktop.getDesktop().open(new File("rules.pdf"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (source == backToMenu) {
            frame.setJMenuBar(null);
            new Menu(frame, false, false, music, soundsLibrary, songEnable, theme);
        } else if (source == song) {
            if (songEnable) {
                songEnable = false;
                music.stopIt();
                song.setText(SONG_DISABLE_BAR);
            } else {
                song.setText(SONG_ENABLE_BAR);
                music = new Music(theme);
                music.start();
                this.songEnable = true;
            }
        }
        else if(source == put){
            put.setIcon(new ImageIcon(TextureManager.library.getImage(theme, "Button Put Selected")));
            mouse.closeClick();
            mouse.openClick();
            setButtonSelected(1);
        }
        else if(source == move){
            move.setIcon(new ImageIcon(TextureManager.library.getImage(theme, "Button Move Selected")));
            mouse.closeClick();
            mouse.openClick();
            setButtonSelected(2);
        }
        else if(source == bringOut){
            setButtonSelected(3);
        }
        else if(source == orient){
            setButtonSelected(4);
        }
        else if(source == top){
            upActive = true;
        }
        else if(source == bottom){
            downActive = true;
        }
        else if(source == right){
            rightActive = true;
        }
        else if(source == left){
            leftActive = true;
        }

    }

    public void setButtonEnabled() {
        top.setEnabled(orientActive || enableUp);
        bottom.setEnabled(orientActive || enableDown);
        right.setEnabled(orientActive || enableRight);
        left.setEnabled(orientActive || enableLeft);

        put.setEnabled(players[playerActive].canPut() && !orientActive && !board.pieceSelected());
        move.setEnabled(board.pieceSelected() && !orientActive);
        bringOut.setEnabled(testBringOut() && !orientActive);
        orient.setEnabled(board.pieceSelected() && !orientActive);

        if (players[playerActive] instanceof Computer) {
            put.setEnabled(false);
            move.setEnabled(false);
            bringOut.setEnabled(false);
            orient.setEnabled(false);
        }
    }

    public void setButtonSelected(int buttonSelected) {
        switch(buttonSelected){
            case 1:
                putActive = true;
                moveActive = false;
                bringOutActive = false;
                orientActive = false;
                break;
            case 2 :
                moveActive = true;
                putActive = false;
                bringOutActive = false;
                orientActive = false;
                break;
            case 3 :
                bringOutActive = true;
                moveActive = false;
                putActive = false;
                orientActive = false;
                break;
            case 4 :
                orientActive = true;
                moveActive = false;
                bringOutActive = false;
                putActive = false;
                break;
            default:
                putActive = false;
                moveActive = false;
                bringOutActive = false;
                orientActive = false;
        }
    }

    public boolean testBringOut() {
        if(board.getPieceSelected() != null){
            if (variantPieceOn && players[playerActive].canBringOut() || !variantPieceOn) {
                int[] coord = convertPixToCase(board.getPieceSelected().getCoord());
                return board.isOnEdge(coord[0],coord[1]);
            }
        }
        return false;
    }

    public void testVictory(int[]coord) {
        Camp winner = null;
        if(coord[0] == -1){
            int i = 0;
            while(!(board.getPiece(i,coord[1]) instanceof Animal)){
                i++;
                while(board.getPiece(i,coord[1]) instanceof Animal){
                    if(((Animal)board.getPiece(i,coord[1])).getOrientation() != Orientation.LEFT) {
                        i++;
                    }
                    else break;
                }
            }
            winner = board.getPiece(i,coord[1]).getCamp();
        }
        else if(coord[0] == 5){
            int i = 4;
            while(!(board.getPiece(i,coord[1]) instanceof Animal)){
                i--;
                while(board.getPiece(i,coord[1]) instanceof Animal){
                    if(((Animal)board.getPiece(i,coord[1])).getOrientation() != Orientation.RIGTH) {
                        i--;
                    }
                    else break;
                }
            }
            winner = board.getPiece(i,coord[1]).getCamp();
        }
        else if(coord[1] == -1){
            int i = 0;
            while(!(board.getPiece(coord[0],i) instanceof Animal)){
                i++;
                while(board.getPiece(coord[0],i) instanceof Animal){
                    if(((Animal)board.getPiece(coord[0],i)).getOrientation() != Orientation.TOP) {
                        i++;
                    }
                    else break;
                }
            }
            winner = board.getPiece(coord[0],i).getCamp();
        }
        else if(coord[1] == 5){
            int i = 4;
            while(!(board.getPiece(coord[0],i) instanceof Animal)){
                i--;
                while(board.getPiece(coord[0],i) instanceof Animal){
                    if(((Animal)board.getPiece(coord[0],i)).getOrientation() != Orientation.DOWN) {
                        i--;
                    }
                    else break;
                }
            }
            winner = board.getPiece(coord[0],i).getCamp();
        }

        frame.setJMenuBar(null);
        if(players[0].getCamp() == winner){
            new Menu(frame,players[0],players[1],music,soundsLibrary,songEnable,theme,
                    variantPieceOn,variantTileOn,variantMountainOn,
                    players[0] instanceof Computer || players[1] instanceof Computer);
        }
        else if(players[1].getCamp() == winner){
            new Menu(frame,players[1],players[0],music,soundsLibrary,songEnable,theme,
                    variantPieceOn,variantTileOn,variantMountainOn,
                    players[0] instanceof Computer || players[1] instanceof Computer);
        }
    }

    public void testVictoryMountains(int[] coord) {
        frame.setJMenuBar(null);
        if(board.getPiece(coord[0],coord[1]).getCamp() == Camp.BLACK){
            new Menu(frame,players[1],players[0],music,soundsLibrary,songEnable,theme,
                    variantPieceOn,variantTileOn,variantMountainOn, false);
        }
        else if(board.getPiece(coord[0],coord[1]).getCamp() == Camp.WHITE){
            new Menu(frame,players[0],players[1],music,soundsLibrary,songEnable,theme,
                    variantPieceOn,variantTileOn,variantMountainOn, false);
        }
    }

    public boolean actionPut() {
        if(mouse.isSelected()) {
            int[] coord = convertCaseToPix(mouse.getClick());
            if (variantTileOn && nbTours < 4 && board.asABanishedTile(mouse.getClick()[0], mouse.getClick()[1])) {
                putActive = false;
                board.deselect();
                mouse.closeClick();
                mouse.openClick();
                soundsLibrary.playErrorActionSound();
                resetButton();
                return false;
            }
            if (board.isOnEdge(mouse.getClick()[0], mouse.getClick()[1])) {
                if (board.isFree(mouse.getClick()[0], mouse.getClick()[1])) {
                    Animal animal;
                    if (playerActive == 0)
                        animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                    else
                        animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                    animal.selected();
                    board.putPiece(animal);
                    soundsLibrary.playPutSound(theme);
                    putActive = false;
                    orientActive = true;
                    mouse.closeClick();
                    players[playerActive].put();
                    return actionOrient();
                } else {
                    if (mouse.getClick()[0] == 0 && mouse.getClick()[1] == 0) {
                        Orientation orientation = whichOrient(1);
                        if (testEnterWithPush(orientation)) {
                            Animal animal;
                            if (playerActive == 0)
                                animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                            else
                                animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                            animal.selected();
                            board.putPiece(animal);
                            soundsLibrary.playPutSound(theme);
                            players[playerActive].put();
                            animal.setOrientation(orientation);
                            double angle = getAngle(Orientation.TOP,board.getPieceSelected().getOrientation());
                            if(angle != 0)
                                board.getPieceSelected().setSprite(Sprite.rotate(board.getPieceSelected().getSprite(),angle));
                            board.deselect();
                            putActive = false;
                            mouse.closeClick();
                            mouse.openClick();
                            return true;
                        }
                        if (orientation != null) {
                            putActive = false;
                            board.deselect();
                            mouse.closeClick();
                            mouse.openClick();
                            soundsLibrary.playErrorActionSound();
                            resetButton();
                            return false;
                        }
                    } else if (mouse.getClick()[0] == 0 && mouse.getClick()[1] == (BOARD_SIZE - 1)) {
                        Orientation orientation = whichOrient(4);
                        if (testEnterWithPush(orientation)) {
                            Animal animal;
                            if (playerActive == 0)
                                animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                            else
                                animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                            animal.selected();
                            board.putPiece(animal);
                            soundsLibrary.playPutSound(theme);
                            players[playerActive].put();
                            animal.setOrientation(orientation);
                            double angle = getAngle(Orientation.TOP,board.getPieceSelected().getOrientation());
                            if(angle != 0)
                                board.getPieceSelected().setSprite(Sprite.rotate(board.getPieceSelected().getSprite(),angle));
                            board.deselect();
                            putActive = false;
                            mouse.closeClick();
                            mouse.openClick();
                            return true;
                        }
                        if (orientation != null) {
                            putActive = false;
                            board.deselect();
                            mouse.closeClick();
                            mouse.openClick();
                            soundsLibrary.playErrorActionSound();
                            resetButton();
                            return false;
                        }
                    } else if (mouse.getClick()[0] == (BOARD_SIZE - 1) && mouse.getClick()[1] == 0) {
                        Orientation orientation = whichOrient(2);
                        if (testEnterWithPush(orientation)) {
                            Animal animal;
                            if (playerActive == 0)
                                animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                            else
                                animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                            animal.selected();
                            board.putPiece(animal);
                            soundsLibrary.playPutSound(theme);
                            players[playerActive].put();
                            animal.setOrientation(orientation);
                            double angle = getAngle(Orientation.TOP,board.getPieceSelected().getOrientation());
                            if(angle != 0)
                                board.getPieceSelected().setSprite(Sprite.rotate(board.getPieceSelected().getSprite(),angle));
                            board.deselect();
                            putActive = false;
                            mouse.closeClick();
                            mouse.openClick();
                            return true;
                        }
                        if (orientation != null) {
                            putActive = false;
                            board.deselect();
                            mouse.closeClick();
                            mouse.openClick();
                            soundsLibrary.playErrorActionSound();
                            resetButton();
                            return false;
                        }
                    } else if (mouse.getClick()[0] == (BOARD_SIZE - 1) && mouse.getClick()[1] == (BOARD_SIZE - 1)) {
                        Orientation orientation = whichOrient(3);
                        if (testEnterWithPush(orientation)) {
                            Animal animal;
                            if (playerActive == 0)
                                animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                            else
                                animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                            animal.selected();
                            board.putPiece(animal);
                            soundsLibrary.playPutSound(theme);
                            players[playerActive].put();
                            animal.setOrientation(orientation);
                            double angle = getAngle(Orientation.TOP,board.getPieceSelected().getOrientation());
                            if(angle != 0)
                                board.getPieceSelected().setSprite(Sprite.rotate(board.getPieceSelected().getSprite(),angle));
                            board.deselect();
                            putActive = false;
                            mouse.closeClick();
                            mouse.openClick();
                            return true;
                        }
                        if (orientation != null) {
                            putActive = false;
                            board.deselect();
                            mouse.closeClick();
                            mouse.openClick();
                            soundsLibrary.playErrorActionSound();
                            resetButton();
                            return false;
                        }
                    } else if (mouse.getClick()[0] == 0) {
                        if (testEnterWithPush(Orientation.RIGTH)) {
                            Animal animal;
                            if (playerActive == 0)
                                animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                            else
                                animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                            animal.selected();
                            board.putPiece(animal);
                            soundsLibrary.playPutSound(theme);
                            players[playerActive].put();
                            animal.setOrientation(Orientation.RIGTH);
                            double angle = getAngle(Orientation.TOP,board.getPieceSelected().getOrientation());
                            if(angle != 0)
                                board.getPieceSelected().setSprite(Sprite.rotate(board.getPieceSelected().getSprite(),angle));
                            board.deselect();
                            putActive = false;
                            mouse.closeClick();
                            mouse.openClick();
                            return true;
                        }
                        putActive = false;
                        board.deselect();
                        mouse.closeClick();
                        mouse.openClick();
                        soundsLibrary.playErrorActionSound();
                        resetButton();
                        return false;
                    } else if (mouse.getClick()[0] == (BOARD_SIZE - 1)) {
                        if (testEnterWithPush(Orientation.LEFT)) {
                            Animal animal;
                            if (playerActive == 0)
                                animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                            else
                                animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                            animal.selected();
                            board.putPiece(animal);
                            soundsLibrary.playPutSound(theme);
                            players[playerActive].put();
                            animal.setOrientation(Orientation.LEFT);
                            double angle = getAngle(Orientation.TOP,board.getPieceSelected().getOrientation());
                            if(angle != 0)
                                board.getPieceSelected().setSprite(Sprite.rotate(board.getPieceSelected().getSprite(),angle));
                            board.deselect();
                            putActive = false;
                            mouse.closeClick();
                            mouse.openClick();
                            return true;
                        }
                        putActive = false;
                        board.deselect();
                        mouse.closeClick();
                        mouse.openClick();
                        soundsLibrary.playErrorActionSound();
                        resetButton();
                        return false;
                    } else if (mouse.getClick()[1] == 0) {
                        if (testEnterWithPush(Orientation.DOWN)) {
                            Animal animal;
                            if (playerActive == 0)
                                animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                            else
                                animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                            animal.selected();
                            board.putPiece(animal);
                            soundsLibrary.playPutSound(theme);
                            players[playerActive].put();
                            animal.setOrientation(Orientation.DOWN);
                            double angle = getAngle(Orientation.TOP,board.getPieceSelected().getOrientation());
                            if(angle != 0)
                                board.getPieceSelected().setSprite(Sprite.rotate(board.getPieceSelected().getSprite(),angle));
                            board.deselect();
                            putActive = false;
                            mouse.closeClick();
                            mouse.openClick();
                            return true;
                        }
                        putActive = false;
                        board.deselect();
                        mouse.closeClick();
                        mouse.openClick();
                        soundsLibrary.playErrorActionSound();
                        resetButton();
                        return false;
                    } else if (mouse.getClick()[1] == (BOARD_SIZE - 1)) {
                        if (testEnterWithPush(Orientation.TOP)) {
                            Animal animal;
                            if (playerActive == 0)
                                animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                            else
                                animal = new Animal(coord[0], coord[1], players[playerActive].getCamp(), Orientation.TOP);
                            board.putPiece(animal);
                            soundsLibrary.playPutSound(theme);
                            players[playerActive].put();
                            putActive = false;
                            mouse.closeClick();
                            mouse.openClick();
                            return true;
                        }
                        putActive = false;
                        board.deselect();
                        mouse.closeClick();
                        mouse.openClick();
                        soundsLibrary.playErrorActionSound();
                        resetButton();
                        return false;
                    }
                }
            } else {
                putActive = false;
                board.deselect();
                mouse.closeClick();
                mouse.openClick();
                soundsLibrary.playErrorActionSound();
                resetButton();
                return false;
            }
        }
        return false;
    }

    public void actionPut(int[] coord, Orientation orientation, Player player) {
        int[] coordPix = convertCaseToPix(coord);
        Animal animal = new Animal(coordPix[0], coordPix[1], player.getCamp(), Orientation.TOP);
        animal.selected();
        board.putPiece(animal);
        soundsLibrary.playPutSound(theme);
        players[playerActive].put();
        actionOrient(coord, orientation);
    }

    public void actionPutPush(int[] coord, Orientation direction, Player player) {
        ArrayList<int[]> pile = new ArrayList<>();
        int x = coord[0];
        int y = coord[1];
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
                if(board.getPiece(pile.get(i)[0],pile.get(i)[1]) instanceof Mountain) actionMove(pile.get(i),
                        direction, Orientation.TOP);
                else actionMove(pile.get(i), direction,
                        ((Animal) board.getPiece(pile.get(i)[0],pile.get(i)[1])).getOrientation());
            } else {
                if(board.getPiece(pile.get(i)[0],pile.get(i)[1]) instanceof Mountain) testVictory(newCoord);
                else actionBringOut(pile.get(i));
            }
        }
        soundsLibrary.playPushSound(theme);
        actionPut(coord, direction, player);
    }

    public boolean testEnterWithPush(Orientation orientation) {
        if (orientation == null) {
            return false;
        }
        ArrayList<Piece> pile = new ArrayList<>();
        int[] coordPiece = {mouse.getClick()[0], mouse.getClick()[1]};
        int[] direction = null;
        switch (orientation) {
            case TOP:
                direction = new int[]{0, -1};
                break;
            case DOWN:
                direction = new int[]{0, 1};
                break;
            case LEFT:
                direction = new int[]{-1, 0};
                break;
            case RIGTH:
                direction = new int[]{1, 0};
                break;
        }
        if((direction[0] == 1 && orientation == Orientation.RIGTH) ||
                (direction[0] == -1 && orientation == Orientation.LEFT) ||
                (direction[1] == 1 && orientation == Orientation.DOWN) ||
                (direction[1] == -1 && orientation == Orientation.TOP) ) {
            int nbCase = 0;
            int mountains = 0;
            int animalOpposed = 0;
            int animalOriented = 1;
            while (board.isInBound(coordPiece[0] + nbCase * direction[0], coordPiece[1] + nbCase * direction[1])
                    && !board.isFree(coordPiece[0] + nbCase * direction[0], coordPiece[1] + nbCase * direction[1])) {
                if (board.getPiece(coordPiece[0] + nbCase * direction[0], coordPiece[1] + nbCase * direction[1]) instanceof Mountain) {
                    mountains++;
                } else if (board.getPiece(coordPiece[0] + nbCase * direction[0], coordPiece[1] + nbCase * direction[1]) instanceof Animal) {
                    Animal a = (Animal) board.getPiece(coordPiece[0] + nbCase * direction[0], coordPiece[1] + nbCase * direction[1]);
                    if (oppositeDirection(orientation, a.getOrientation())) {
                        animalOpposed++;
                    } else if (orientation == a.getOrientation()) {
                        animalOriented++;
                    }
                }
                pile.add(board.getPiece(coordPiece[0] + nbCase * direction[0], coordPiece[1] + nbCase * direction[1]));
                nbCase++;
            }
            int offset = 1;
            while (pile.get(pile.size() - offset) instanceof Animal &&
                    ((Animal) pile.get(pile.size() - offset)).getOrientation() == orientation) {
                animalOriented--;
                offset++;
                if (pile.size() - offset < 0) {
                    break;
                }
            }
            if (mountains == 0) {
                if (animalOriented > animalOpposed) {
                    return actionPush(pile, direction);
                }
            } else {
                if (animalOriented >= animalOpposed + mountains) {
                    return actionPush(pile, direction);
                }
            }
        }
        return false;
    }

    public boolean testPush() {
        ArrayList<Piece> pile = new ArrayList<>();
        int[] coordPiece = convertPixToCase(board.getPieceSelected().getCoord());
        int[] direction = {-(coordPiece[0]-mouse.getClick()[0]),-(coordPiece[1]-mouse.getClick()[1])};
        if((direction[0] == 1 && board.getPieceSelected().getOrientation() == Orientation.RIGTH) ||
                (direction[0] == -1 && board.getPieceSelected().getOrientation() == Orientation.LEFT) ||
                (direction[1] == 1 && board.getPieceSelected().getOrientation() == Orientation.DOWN) ||
                (direction[1] == -1 && board.getPieceSelected().getOrientation() == Orientation.TOP) ) {
            int nbCase = 1;
            int mountains = 0;
            int animalOpposed = 0;
            int animalOriented = 1;
            while (board.isInBound(coordPiece[0] + nbCase * direction[0], coordPiece[1] + nbCase * direction[1])
                    && !board.isFree(coordPiece[0] + nbCase * direction[0], coordPiece[1] + nbCase * direction[1])) {
                if (board.getPiece(coordPiece[0] + nbCase * direction[0], coordPiece[1] + nbCase * direction[1]) instanceof Mountain) {
                    mountains++;
                } else if (board.getPiece(coordPiece[0] + nbCase * direction[0], coordPiece[1] + nbCase * direction[1]) instanceof Animal) {
                    Animal a = (Animal) board.getPiece(coordPiece[0] + nbCase * direction[0], coordPiece[1] + nbCase * direction[1]);
                    if (oppositeDirection(board.getPieceSelected().getOrientation(), a.getOrientation())) {
                        animalOpposed++;
                    } else if (board.getPieceSelected().getOrientation() == a.getOrientation()) {
                        animalOriented++;
                    }
                }
                pile.add(board.getPiece(coordPiece[0] + nbCase * direction[0], coordPiece[1] + nbCase * direction[1]));
                nbCase++;
            }
            int offset = 1;
            while (pile.get(pile.size() - offset) instanceof Animal &&
                    ((Animal) pile.get(pile.size() - offset)).getOrientation() == board.getPieceSelected().getOrientation()) {
                animalOriented--;
                offset++;
                if (pile.size() - offset < 0) {
                    break;
                }
            }
            if (mountains == 0) {
                if (animalOriented > animalOpposed) {
                    return actionPush(pile, direction);
                }
            } else {
                if (animalOriented >= animalOpposed + mountains) {
                    return actionPush(pile, direction);
                }
            }
        }
        return false;
    }

    public boolean oppositeDirection(Orientation o1, Orientation o2) {
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

    public boolean testMove() {
        if(mouse.isSelected()) {
            if (isAdjacent()) {
                if(board.isFree(mouse.getClick()[0],mouse.getClick()[1])) {
                    actionMove();
                    moveActive = false;
                    orientActive = true;
                    mouse.closeClick();
                    return actionOrient();
                } else if (testPush()) actionMove();
                else {
                    moveActive = false;
                    board.deselect();
                    mouse.closeClick();
                    mouse.openClick();
                    soundsLibrary.playErrorActionSound();
                    resetButton();
                    return false;
                }
            } else {
                moveActive = false;
                board.deselect();
                mouse.closeClick();
                mouse.openClick();
                soundsLibrary.playErrorActionSound();
                resetButton();
                return false;
            }
            moveActive = false;
            board.deselect();
            mouse.closeClick();
            mouse.openClick();
            return true;
        }
        return false;
    }

    public boolean actionPush(ArrayList<Piece> pile, int[] direction) {
        for (int i = pile.size() - 1; i >= 0; i--) {
            int[] old = convertPixToCase(pile.get(i).getCoord());
            int[] newCoord = {old[0]+direction[0], old[1]+direction[1]};
            if (board.isInBound(newCoord[0], newCoord[1])) {
                actionMove(old, newCoord);
            }
            else {
                if(pile.get(i) instanceof Animal) {
                    actionBringOut(old);
                }
                else{
                    if(variantMountainOn) {
                        if(pile.get(i).getCamp() == Camp.NEUTRAL){
                            // Poser une pierre
                        }else{
                            testVictoryMountains(old);
                        }
                    }
                    else{
                        testVictory(newCoord);
                    }
                }
            }
        }
        soundsLibrary.playPushSound(theme);
        return true;
    }

    public void actionPush(int[] coord) {
        ArrayList<int[]> pile = new ArrayList<>();
        int x = coord[0];
        int y = coord[1];
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
                if(board.getPiece(pile.get(i)[0],pile.get(i)[1]) instanceof Mountain) actionMove(pile.get(i),
                        direction, Orientation.TOP);
                else actionMove(pile.get(i), direction,
                        ((Animal) board.getPiece(pile.get(i)[0],pile.get(i)[1])).getOrientation());
            } else {
                if(board.getPiece(pile.get(i)[0],pile.get(i)[1]) instanceof Mountain) testVictory(newCoord);
                else actionBringOut(pile.get(i));
            }
        }
    }

    public boolean actionBringOut() {
        board.removePiece(convertPixToCase(board.getPieceSelected().getCoord()));
        soundsLibrary.playOutSound(theme);
        players[playerActive].bringOut();
        bringOutActive = false;
        mouse.closeClick();
        mouse.openClick();
        return true;
    }

    public void actionBringOut(int[] coord) {
        Animal p = (Animal) board.getPiece(coord[0], coord[1]);
        if(players[0].getCamp() == p.getCamp()){
            players[0].bringOut();
        } else {
            players[1].bringOut();
        }
        board.removePiece(coord);
        soundsLibrary.playOutSound(theme);
    }

    public void actionBringOut(int[] coord, Player player) {
        player.bringOut();
        board.removePiece(coord);
        soundsLibrary.playOutSound(theme);
    }

    public void actionMove() {
        int[] newCoordPix = convertCaseToPix(mouse.getClick());
        int[] oldCoordCase = convertPixToCase(board.getPieceSelected().getCoord());
        board.movePiece(mouse.getClick()[0],mouse.getClick()[1], oldCoordCase[0], oldCoordCase[1]);
        int[] oldCoordPix = board.getPieceSelected().getCoord();
        int[] direction = new int[2];
        if (oldCoordPix[0]-newCoordPix[0] == 128) direction[0] = -1;
        else if (oldCoordPix[0]-newCoordPix[0] == -128) direction[0] = 1;
        else direction[0] = 0;
        if (oldCoordPix[1]-newCoordPix[1] == 128) direction[1] = -1;
        else if (oldCoordPix[1]-newCoordPix[1] == -128) direction[1] = 1;
        else direction[1] = 0;
        board.pieceMoving(board.getPieceSelected());
        if(board.getPieceSelected().getCamp() == Camp.WHITE) soundsLibrary.playWalkSound(theme, Camp.WHITE);
        else soundsLibrary.playWalkSound(theme, Camp.BLACK);
        for (int i = 0; i < 128; i++) {
            board.getPieceSelected().setPosition(oldCoordPix[0] + i*direction[0], oldCoordPix[1] + i*direction[1]);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        board.pieceStopMoving();
        board.getPieceSelected().setPosition(newCoordPix[0],newCoordPix[1]);
    }

    public void actionMove(int[] old, int[] newCoord) {
        int[] newCoordPix = convertCaseToPix(newCoord);
        board.movePiece(newCoord[0],newCoord[1], old[0], old[1]);
        int[] oldCoordPix = convertCaseToPix(old);
        int[] direction = new int[2];
        if (oldCoordPix[0]-newCoordPix[0] == 128) direction[0] = -1;
        else if (oldCoordPix[0]-newCoordPix[0] == -128) direction[0] = 1;
        else direction[0] = 0;
        if (oldCoordPix[1]-newCoordPix[1] == 128) direction[1] = -1;
        else if (oldCoordPix[1]-newCoordPix[1] == -128) direction[1] = 1;
        else direction[1] = 0;
        board.pieceMoving(board.getPiece(newCoord[0],newCoord[1]));
        if(board.getPiece(newCoord[0],newCoord[1]).getCamp() == Camp.WHITE) soundsLibrary.playWalkSound(theme, Camp.WHITE);
        else soundsLibrary.playWalkSound(theme, Camp.BLACK);
        for (int i = 0; i < 128; i++) {
            board.getPiece(newCoord[0],newCoord[1]).setPosition(oldCoordPix[0] + i*direction[0], oldCoordPix[1] + i*direction[1]);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        board.pieceStopMoving();
        board.getPiece(newCoord[0],newCoord[1]).setPosition(newCoordPix[0],newCoordPix[1]);
    }

    public void actionMove(int[] coord, Orientation direction, Orientation orientation) {
        board.movePiece(coord[0], coord[1], direction);
        int[] oldCoordPix = convertCaseToPix(coord);
        switch (direction) {
            case TOP:
                coord[1]--;
                break;
            case DOWN:
                coord[1]++;
                break;
            case LEFT:
                coord[0]--;
                break;
            case RIGTH:
                coord[0]++;
                break;
        }
        int[] newCoordPix = convertCaseToPix(coord);
        int[] dir = new int[2];
        if (oldCoordPix[0]-newCoordPix[0] == 128) dir[0] = -1;
        else if (oldCoordPix[0]-newCoordPix[0] == -128) dir[0] = 1;
        else dir[0] = 0;
        if (oldCoordPix[1]-newCoordPix[1] == 128) dir[1] = -1;
        else if (oldCoordPix[1]-newCoordPix[1] == -128) dir[1] = 1;
        else dir[1] = 0;
        board.pieceMoving(board.getPiece(coord[0], coord[1]));
        if(board.getPiece(coord[0],coord[1]).getCamp() == Camp.WHITE) soundsLibrary.playWalkSound(theme, Camp.WHITE);
        else soundsLibrary.playWalkSound(theme, Camp.BLACK);
        for (int i = 0; i < 128; i++) {
            board.getPiece(coord[0],coord[1]).setPosition(oldCoordPix[0] + i*dir[0], oldCoordPix[1] + i*dir[1]);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        board.pieceStopMoving();
        if (board.getPiece(coord[0],coord[1]) instanceof Animal) actionOrient(coord, orientation);
    }

    public boolean actionOrient() {
        if(board.getPieceSelected() != null){
            boolean actionPerformed = false;
            Orientation oldOrient = board.getPieceSelected().getOrientation();
            if(upActive){
                board.getPieceSelected().setOrientation(Orientation.TOP);
                actionPerformed = true;
            }
            else if(downActive){
                board.getPieceSelected().setOrientation(Orientation.DOWN);
                actionPerformed = true;
            }
            else if(rightActive){
                board.getPieceSelected().setOrientation(Orientation.RIGTH);
                actionPerformed = true;
            }
            else if(leftActive){
                board.getPieceSelected().setOrientation(Orientation.LEFT);
                actionPerformed = true;
            }
            if(actionPerformed) {
                soundsLibrary.playTurnSound(theme);
                upActive = false;
                downActive = false;
                rightActive = false;
                leftActive = false;
                double angle = getAngle(oldOrient,board.getPieceSelected().getOrientation());
                if(angle != 0)
                    board.getPieceSelected().setSprite(Sprite.rotate(board.getPieceSelected().getSprite(),angle));
                board.deselect();
                orientActive = false;
                mouse.closeClick();
                mouse.openClick();
                return true;
            }
        }
        return false;
    }

    public void actionOrient(int[] coord, Orientation orientation) {
        soundsLibrary.playTurnSound(theme);
        Orientation oldOrient = ((Animal) board.getPiece(coord[0], coord[1])).getOrientation();
        board.select(coord[0],coord[1]);
        ((Animal) board.getPiece(coord[0], coord[1])).setOrientation(orientation);
        double angle = getAngle(oldOrient, orientation);
        if(angle != 0)
            board.getPieceSelected().setSprite(Sprite.rotate(board.getPieceSelected().getSprite(),angle));
        board.deselect();
    }

    public Orientation whichOrient(int where) {
        switch (where) {
            case 1:
                enableDown = enableRight = true;
                break;
            case 2:
                enableDown = enableLeft = true;
                break;
            case 3:
                enableUp = enableLeft = true;
                break;
            case 4:
                enableUp = enableRight = true;
                break;
            default:
                break;
        }
        boolean actionPerformed = false;
        Orientation orientation = null;
        if(upActive){
            orientation = Orientation.TOP;
            actionPerformed = true;
        }
        else if(downActive){
            orientation = Orientation.DOWN;
            actionPerformed = true;
        }
        else if(rightActive){
            orientation = Orientation.RIGTH;
            actionPerformed = true;
        }
        else if(leftActive){
            orientation = Orientation.LEFT;
            actionPerformed = true;
        }
        if (actionPerformed) {
            enableUp = enableDown = enableRight = enableLeft = false;
            upActive = downActive = rightActive = leftActive = false;
        }
        return orientation;
    }

    public int[] convertCaseToPix(int[] point){
        return new int[]{point[0]*SPRITE_SIZE+BOARD_BORDER/2,point[1]*SPRITE_SIZE+BOARD_BORDER/2};
    }

    public int[] convertPixToCase(int[] point){
        return new int[]{point[0]/SPRITE_SIZE,point[1]/SPRITE_SIZE};
    }

    public void nextPlayer() {
        board.deselect();
        nbTours++;
        if (variantTileOn && nbTours == 4) {
            board.changeTile(2, (BOARD_SIZE - 1), false);
            board.changeTile(2, 0, false);
        }
        if(playerActive == 0) {
            playerActive = 1;
            computerPlay = false;
        } else if(playerActive == 1) {
            playerActive = 0;
            computerPlay = false;
        }
        setButtonSelected(10);
        enableUp = enableDown = enableRight = enableLeft = false;
        put.setIcon(new ImageIcon(TextureManager.library.getImage(theme, "Button Put")));
        move.setIcon(new ImageIcon(TextureManager.library.getImage(theme, "Button Move")));
        orient.setIcon(new ImageIcon(TextureManager.library.getImage(theme, "Button Orient")));
        playerName.setText(players[playerActive].getName());
    }

    public double getAngle(Orientation oldOrient, Orientation newOrient){
        if(oldOrient != board.getPieceSelected().getOrientation()){
            if(oldOrient == Orientation.TOP){
                if(newOrient == Orientation.RIGTH){
                    return Math.PI*2;
                }
                else if(newOrient == Orientation.DOWN){
                    return Math.PI/2;
                }
                else if(newOrient == Orientation.LEFT){
                    return Math.PI;
                }
            }
            else if(oldOrient == Orientation.RIGTH){
                if(newOrient == Orientation.DOWN){
                    return Math.PI*2;
                }
                else if(newOrient == Orientation.LEFT){
                    return Math.PI/2;
                }
                else if(newOrient == Orientation.TOP){
                    return Math.PI;
                }
            }
            else if(oldOrient == Orientation.DOWN){
                if(newOrient == Orientation.LEFT){
                    return Math.PI*2;
                }
                else if(newOrient == Orientation.TOP){
                    return Math.PI/2;
                }
                else if(newOrient == Orientation.RIGTH){
                    return Math.PI;
                }
            }
            else if(oldOrient == Orientation.LEFT){
                if(newOrient == Orientation.TOP){
                    return Math.PI*2;
                }
                else if(newOrient == Orientation.RIGTH){
                    return Math.PI/2;
                }
                else if(newOrient == Orientation.DOWN){
                    return Math.PI;
                }
            }
        }
        return 0;
    }

    public void selectPiece(){
        if(mouse.isSelected()){
            if(board.getPiece(mouse.getClick()[0],mouse.getClick()[1]) != null){
                if(board.getPiece(mouse.getClick()[0],mouse.getClick()[1]).getCamp() == players[playerActive].getCamp()){
                    board.deselect();
                    board.select(mouse.getClick()[0],mouse.getClick()[1]);
                    if (board.getPieceSelected().getCamp()== Camp.WHITE) soundsLibrary.playWhiteSound(theme);
                    else soundsLibrary.playBlackSound(theme);
                }
            }
        }
    }

    public boolean isAdjacent(){
        int[] coordPieceSelected = convertPixToCase(board.getPieceSelected().getCoord());
        if(coordPieceSelected[0] == mouse.getClick()[0]){
            if(board.isInBound(coordPieceSelected[0],coordPieceSelected[1] - 1)){
                if(coordPieceSelected[1] - 1 == mouse.getClick()[1]){
                    return true;
                }
            }
            if(board.isInBound(coordPieceSelected[0],coordPieceSelected[1] + 1)){
                if(coordPieceSelected[1] + 1 == mouse.getClick()[1]){
                    return true;
                }
            }
        }
        if(coordPieceSelected[1] == mouse.getClick()[1]){
            if(board.isInBound(coordPieceSelected[0] - 1,coordPieceSelected[1])){
                if(coordPieceSelected[0] - 1 == mouse.getClick()[0]){
                    return true;
                }
            }
            if(board.isInBound(coordPieceSelected[0] + 1,coordPieceSelected[1])){
                if(coordPieceSelected[0] + 1 == mouse.getClick()[0]){
                    return true;
                }
            }
        }
        return false;
    }

}