package siam.audio;

import siam.player.Theme;

import java.util.Random;

public class Music extends Thread {

    private static Random random = new Random();

    private MP3 dj;
    private String[] standardSounds;
    private String[] christmasSounds;
    private String[] starWarsSounds;

    private Theme theme;

    public Music(Theme theme){
        this.theme = theme;
        standardSounds = new String[]{"/audio/songs/Standard1.mp3",
                                        "/audio/songs/Standard2.mp3",
                                        "/audio/songs/Standard3.mp3"};
        christmasSounds = new String[]{"/audio/songs/Noel1.mp3",
                                    "/audio/songs/Noel2.mp3",
                                    "/audio/songs/Noel3.mp3"};
        starWarsSounds = new String[]{"/audio/songs/StarWars1.mp3",
                                        "/audio/songs/StarWars2.mp3",
                                        "/audio/songs/StarWars3.mp3",
                                        "/audio/songs/StarWars4.mp3",
                                        "/audio/songs/StarWars5.mp3"};

    }

    public synchronized void start() {
        Thread thread = new Thread(this, "Music");
        thread.start();
    }

    public void run(){
        String[] sounds = null;
        switch (theme) {
            case STANDARD:
                sounds = standardSounds;
                break;
            case CHRISTMAS:
                sounds = christmasSounds;
                break;
            case STARWARS:
                sounds = starWarsSounds;
                break;
        }
        int newCount;
        int count = random.nextInt(sounds.length);
        dj = new MP3(sounds[count]);
        dj.play();

        while (true){
            if (dj == null) return;
            if (dj.getPlayer().isComplete()) {
                newCount = random.nextInt(sounds.length);
                while (count == newCount) newCount = random.nextInt(sounds.length);
                count = newCount;
                dj = new MP3(sounds[count]);
            }
            dj.play();
        }
    }

    public void stopIt() {
        dj.stop();
        dj = null;
    }
}