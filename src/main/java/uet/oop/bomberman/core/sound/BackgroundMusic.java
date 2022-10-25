package uet.oop.bomberman.core.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.core.scenes.game.RightSideBar;
import uet.oop.bomberman.core.scenes.menu.Setting;

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
    private static MediaPlayer mediaPlayer;
    private static boolean initialized = false;
    private static ArrayList<File> songsCustoms;
    private static ArrayList<File> songsDefault;
    private Random random;
    private THEME theme;
    // from 0.0 - 1.1
    private double volume;
    private int indexCustoms;
    private int indexDefault;
    private static boolean mute;

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
        volume = 0.5;
        indexCustoms = random.nextInt(songsCustoms.size());
        indexDefault = random.nextInt(songsDefault.size());
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
                indexCustoms++;
                if (indexCustoms >= songsCustoms.size()) {
                    indexCustoms = 0;
                }
                media = new Media(songsCustoms.get(indexCustoms).toURI().toString());
                break;
            }
            case DEFAULT: {
                indexDefault++;
                if (indexDefault >= songsDefault.size()) {
                    indexDefault = 0;
                }
                media = new Media(songsDefault.get(indexDefault).toURI().toString());
                break;
            }
        }
        if (mediaPlayer != null) {
            play();
        }
    }

    public void before() {
        switch (theme) {
            case CUSTOMS: {
                indexCustoms--;
                if (indexCustoms < 0) {
                    indexCustoms = songsCustoms.size() - 1;
                }
                media = new Media(songsCustoms.get(indexCustoms).toURI().toString());
                break;
            }
            case DEFAULT: {
                indexDefault--;
                if (indexDefault < 0) {
                    indexDefault = songsDefault.size() - 1;
                }
                media = new Media(songsDefault.get(indexDefault).toURI().toString());
                break;
            }
        }
        if (mediaPlayer != null) {
            play();
        }
    }

    private void play() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(this::next);
        mediaPlayer.setVolume(volume);
        mediaPlayer.play();
    }

    public void pause() {
        mediaPlayer.pause();
    }
    public void unpause() {
        mediaPlayer.play();
    }

    /**
     *
     * @param volumn : 0.0 -> 1.0
     */
    public void setVolume(double volumn) {
        this.volume = volumn;
        mediaPlayer.setVolume(volumn);
    }

    public double getVolume() {
        return volume;
    }

    public static void setMute(boolean mute) {
        BackgroundMusic.mute = mute;
        Setting.getInstance().setMute(mute);
        RightSideBar.getInstance().setMute(mute);
        mediaPlayer.setMute(mute);
    }

    public static boolean isMute() {
        return mute;
    }
}
