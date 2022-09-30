package uet.oop.bomberman.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class AudioManager {
    private Media media;
    private MediaPlayer mediaPlayer;
    private String path;
    private boolean loops;

    public AudioManager() {
    }

    public AudioManager(String path, boolean loops) {
        this.path = path;
        this.loops = loops;
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }
    public void playMusic() {
        mediaPlayer.play();

    }


    public void pauseMusic() {
        mediaPlayer.pause();
    }

    public void muteMusic() {
        mediaPlayer.setMute(true);
    }

    public void unmuteMusic() {
        mediaPlayer.setMute(false);
    }
}
