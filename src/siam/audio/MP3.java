package siam.audio;

import javazoom.jl.player.Player;

import java.io.InputStream;

public class MP3 {

    private Player player;
    private String nom;

    public MP3(String nom) {
        this.nom = nom;
    }

    public void play() {
        try {
            player = new Player(InputStream.class.getResourceAsStream(nom));
            PlayerThread pt = new PlayerThread();
            pt.start();
            while (!player.isComplete()) {
                if (player == null) return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        player.close();
        player = null;
    }

    class PlayerThread extends Thread {
        public void run() {
            try {
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

}