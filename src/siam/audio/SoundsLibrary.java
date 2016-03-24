package siam.audio;

import siam.player.Camp;
import siam.player.Theme;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Random;

public class SoundsLibrary {

    private HashMap<String,AudioClip> soundLibraryStandard;
    private HashMap<String,AudioClip> soundLibraryChristmas;
    private HashMap<String,AudioClip> soundLibraryStarWars;

    private static final Random random = new Random();

    public SoundsLibrary () {
        try {
            loadStandardSounds();
            loadChristmasSounds();
            loadStarWarsSounds();
        } catch (Exception e) {
            System.out.println("Error - Could'nt load sounds ! " + e.getMessage());
        }
    }

    private void loadStandardSounds() throws MalformedURLException {
        String standardPath = "/audio/sounds/standard/";

        AudioClip button = Applet.newAudioClip(getClass().getResource(standardPath + "button.wav"));
        AudioClip error = Applet.newAudioClip(getClass().getResource(standardPath + "error.wav"));

        AudioClip white1 = Applet.newAudioClip(getClass().getResource(standardPath + "white1.wav"));
        AudioClip white2 = Applet.newAudioClip(getClass().getResource(standardPath + "white2.wav"));
        AudioClip white3 = Applet.newAudioClip(getClass().getResource(standardPath + "white3.wav"));

        AudioClip black1 = Applet.newAudioClip(getClass().getResource(standardPath + "black1.wav"));
        AudioClip black2 = Applet.newAudioClip(getClass().getResource(standardPath + "black2.wav"));
        AudioClip black3 = Applet.newAudioClip(getClass().getResource(standardPath + "black3.wav"));

        AudioClip put = Applet.newAudioClip(getClass().getResource(standardPath + "put.wav"));
        AudioClip bringout = Applet.newAudioClip(getClass().getResource(standardPath + "bringout.wav"));

        AudioClip move = Applet.newAudioClip(getClass().getResource(standardPath + "move.wav"));
        AudioClip push = Applet.newAudioClip(getClass().getResource(standardPath + "push.wav"));
        AudioClip orient = Applet.newAudioClip(getClass().getResource(standardPath + "orient.wav"));

        soundLibraryStandard = new HashMap<>();
        soundLibraryStandard.put("button", button);
        soundLibraryStandard.put("error", error);

        soundLibraryStandard.put("white1", white1);
        soundLibraryStandard.put("white2", white2);
        soundLibraryStandard.put("white3", white3);

        soundLibraryStandard.put("black1", black1);
        soundLibraryStandard.put("black2", black2);
        soundLibraryStandard.put("black3", black3);

        soundLibraryStandard.put("put", put);
        soundLibraryStandard.put("bringout", bringout);

        soundLibraryStandard.put("move_white", move);
        soundLibraryStandard.put("move_black", move);
        soundLibraryStandard.put("push", push);
        soundLibraryStandard.put("orient", orient);
    }

    private void loadChristmasSounds() throws MalformedURLException {
        String christmasPath = "/audio/sounds/christmas/";

        AudioClip white1 = Applet.newAudioClip(getClass().getResource(christmasPath + "white1.wav"));
        AudioClip white2 = Applet.newAudioClip(getClass().getResource(christmasPath + "white2.wav"));
        AudioClip white3 = Applet.newAudioClip(getClass().getResource(christmasPath + "white3.wav"));

        AudioClip black1 = Applet.newAudioClip(getClass().getResource(christmasPath + "black1.wav"));
        AudioClip black2 = Applet.newAudioClip(getClass().getResource(christmasPath + "black2.wav"));
        AudioClip black3 = Applet.newAudioClip(getClass().getResource(christmasPath + "black3.wav"));

        AudioClip put = Applet.newAudioClip(getClass().getResource(christmasPath + "put.wav"));
        AudioClip bringout = Applet.newAudioClip(getClass().getResource(christmasPath + "bringout.wav"));

        AudioClip move_white = Applet.newAudioClip(getClass().getResource(christmasPath + "move_white.wav"));
        AudioClip move_black = Applet.newAudioClip(getClass().getResource(christmasPath + "move_black.wav"));
        AudioClip push = Applet.newAudioClip(getClass().getResource(christmasPath + "push.wav"));
        AudioClip orient = Applet.newAudioClip(getClass().getResource(christmasPath + "orient.wav"));

        soundLibraryChristmas = new HashMap<>();

        soundLibraryChristmas.put("white1", white1);
        soundLibraryChristmas.put("white2", white2);
        soundLibraryChristmas.put("white3", white3);

        soundLibraryChristmas.put("black1", black1);
        soundLibraryChristmas.put("black2", black2);
        soundLibraryChristmas.put("black3", black3);

        soundLibraryChristmas.put("put", put);
        soundLibraryChristmas.put("bringout", bringout);

        soundLibraryChristmas.put("move_white", move_white);
        soundLibraryChristmas.put("move_black", move_black);
        soundLibraryChristmas.put("push", push);
        soundLibraryChristmas.put("orient", orient);
    }

    private void loadStarWarsSounds() throws MalformedURLException {
        String starwarsPath = "/audio/sounds/starwars/";

        AudioClip white = Applet.newAudioClip(getClass().getResource(starwarsPath + "white.wav"));

        AudioClip black1 = Applet.newAudioClip(getClass().getResource(starwarsPath + "black1.wav"));
        AudioClip black2 = Applet.newAudioClip(getClass().getResource(starwarsPath + "black2.wav"));

        AudioClip put = Applet.newAudioClip(getClass().getResource(starwarsPath + "put.wav"));
        AudioClip bringout = Applet.newAudioClip(getClass().getResource(starwarsPath + "bringout.wav"));

        AudioClip move = Applet.newAudioClip(getClass().getResource(starwarsPath + "move.wav"));
        AudioClip push = Applet.newAudioClip(getClass().getResource(starwarsPath + "push.wav"));
        AudioClip orient = Applet.newAudioClip(getClass().getResource(starwarsPath + "orient.wav"));

        soundLibraryStarWars = new HashMap<>();

        soundLibraryStarWars.put("white1", white);
        soundLibraryStarWars.put("white2", white);
        soundLibraryStarWars.put("white3", white);

        soundLibraryStarWars.put("black1", black1);
        soundLibraryStarWars.put("black2", black2);
        soundLibraryStarWars.put("black3", black1);

        soundLibraryStarWars.put("put", put);
        soundLibraryStarWars.put("bringout", bringout);

        soundLibraryStarWars.put("move_white", move);
        soundLibraryStarWars.put("move_black", move);
        soundLibraryStarWars.put("push", push);
        soundLibraryStarWars.put("orient", orient);
    }

    private void playAudio(String name, Theme theme){
        AudioClip sample = soundLibraryStandard.get(name);
        switch (theme) {
            case STANDARD:
                sample = soundLibraryStandard.get(name);
                break;
            case CHRISTMAS:
                sample = soundLibraryChristmas.get(name);
                break;
            case STARWARS:
                sample = soundLibraryStarWars.get(name);
                break;
        }
        if(sample != null) sample.play();
    }

    public void playWhiteSound(Theme theme){
        String name = "";
        switch (random.nextInt(3)) {
            case 0 :
                name = "white1";
                break;
            case 1 :
                name = "white2";
                break;
            case 2 :
                name = "white3";
                break;
        }
        playAudio(name, theme);
    }

    public void playBlackSound(Theme theme){
        String name = "";
        switch (random.nextInt(3)) {
            case 0 :
                name = "black1";
                break;
            case 1 :
                name = "black2";
                break;
            case 2 :
                name = "black3";
                break;
        }
        playAudio(name, theme);
    }

    public void playButtonSound(){
        playAudio("button", Theme.STANDARD);
    }

    public void playPushSound(Theme theme) {
        playAudio("push", theme);
    }

    public void playWalkSound(Theme theme,  Camp camp){
        switch (camp){
            case WHITE:
                playAudio("move_white", theme);
                break;
            case BLACK:
                playAudio("move_black", theme);
                break;
        }
    }

    public void playTurnSound(Theme theme){
        playAudio("orient", theme);
    }

    public void playPutSound(Theme theme) {
        playAudio("put", theme);
    }

    public void playErrorActionSound(){
        playAudio("error", Theme.STANDARD);
    }

    public void playOutSound(Theme theme){
        playAudio("bringout", theme);
    }
}
