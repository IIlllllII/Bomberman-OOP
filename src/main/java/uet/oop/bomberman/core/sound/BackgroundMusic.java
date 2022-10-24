package uet.oop.bomberman.core.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class BackgroundMusic {
    public enum THEME {
        CUSTOMS, DEFAULT
    }
    private Media media;
    private MediaPlayer mediaPlayer;
    private static boolean initialized = false;
    private static ArrayList<File> songsCustoms;
    private static ArrayList<File> songsDefault;
    private Random random;
    private THEME theme;
    // from 0.0 - 1.1
    private double volumn;

    private static class SingletonHelper {
        private static final BackgroundMusic INSTANCE = new BackgroundMusic();
    }
    public static BackgroundMusic getInstance() {
        return BackgroundMusic.SingletonHelper.INSTANCE;
    }

    public static void init() {
        if (!initialized) {
            songsCustoms = new ArrayList<>();
            songsDefault = new ArrayList<>();

            File directionCustoms = new File("src/main/resources/sound/Customs");
            songsCustoms.addAll(List.of(Objects.requireNonNull(directionCustoms.listFiles())));

            File directionDefault = new File("src/main/resources/sound/Default");
            songsDefault.addAll(List.of(Objects.requireNonNull(directionDefault.listFiles())));

            initialized = true;
        }
    }

    private BackgroundMusic() {
        random = new Random();
        theme = THEME.CUSTOMS;
        volumn = 0.5;
        setTheme(theme);
        play();
    }

    public void setTheme(THEME theme) {
        this.theme = theme;
        next();
    }

    public void next() {
        switch (theme) {
            case CUSTOMS: {
                media = new Media(songsCustoms.get(random.nextInt(songsCustoms.size())).toURI().toString());
                break;
            }
            case DEFAULT: {
                media = new Media(songsDefault.get(random.nextInt(songsDefault.size())).toURI().toString());
                break;
            }
        }
        if (mediaPlayer != null) {
            play();
        }
    }

    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(this::next);
        mediaPlayer.setVolume(volumn);
        mediaPlayer.play();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    /**
     *
     * @param volumn : 0.0 -> 1.0
     */
    public void setVolume(double volumn) {
        this.volumn = volumn;
        mediaPlayer.setVolume(volumn);
    }

    public double getVolumn() {
        return volumn;
    }

    public void setMute(boolean mute) {
        mediaPlayer.setMute(mute);
    }
}
