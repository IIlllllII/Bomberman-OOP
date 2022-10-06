package uet.oop.bomberman.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sound {
    public static final String LEVEL_COMPLETE_SOUND = "src/resources/sounds/levelComplete.mp3";
    public static final String EAT_ITEM_SOUND = "src/resources/sounds/eatingItem.mp3";
    public static final String EXPLODE_SOUND = "src/resources/sounds/explosion.mp3";
    public static final String DEAD_SOUND = "src/resources/sounds/LifeLost.mp3";
    public static final String LOSE_GAME_SOUND = "src/resources/sounds/gameOver.mp3";
    public static final String WIN_GAME_SOUND = "src/resources/sounds/Victory.mp3";
    public static final String MOVING_SOUND = "src/resources/sounds/moving.mp3";
    public static final String PLACE_BOMB_SOUND = "src/resources/sounds/placeBomb.mp3";
    protected Media media;
    protected MediaPlayer mediaPlayer;

    protected static boolean muteAll = false;
    private String path;

    /**
     * Constructor for creating sound from path.
     */
    public Sound(String path) {
        this.path = path;
        try{
            media = new Media(new File(path).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
        }catch (Exception e){
            System.out.println("uet.oop.bomberman.sound : error load media");
        }
    }

    public Sound() {}

    public void setMedia(Media media) {
        this.media = media;
        mediaPlayer = new MediaPlayer(media);
    }

    public Media getMedia() {
        return media;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static boolean isMuteAll(){
        return Sound.muteAll;
    }

    public static void setMuteAll(boolean muteAll) {
        Sound.muteAll = muteAll;
    }

    public void playSound(){
        if (! Sound.muteAll) {
            mediaPlayer.play();
        }
    }

    public void mute() {
        mediaPlayer.setMute(true);
    }

    public void unmute() {
        mediaPlayer.setMute(false);
    }
}
