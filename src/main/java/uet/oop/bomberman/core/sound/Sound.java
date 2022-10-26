package uet.oop.bomberman.core.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sound {
    public static final String LEVEL_COMPLETE = "src/main/resources/sound/Effect/levelComplete.mp3";
    public static final String EAT_ITEM = "src/main/resources/sound/Effect/eatingItem.mp3";
    public static final String EXPLODE = "src/main/resources/sound/Effect/explosion.mp3";
    public static final String DEAD_SOUND = "src/main/resources/sound/Effect/LifeLost.mp3";
    public static final String LOSE_GAME = "src/main/resources/sound/Effect/gameOver.mp3";
    public static final String WIN_GAME = "src/main/resources/sound/Effect/Victory.mp3";
    public static final String PLACE_BOMB = "src/main/resources/sound/Effect/placeBomb.mp3";
    public static final String MOVING = "src/main/resources/sound/Effect/moving.mp3";
    public static final String HOVER_BUTTON = "src/main/resources/sound/Effect/hoverButton.mp3";
    public static final String CLICK = "src/main/resources/sound/Effect/click.mp3";

    private Media media;
    private MediaPlayer mediaPlayer;
    private static boolean mute = true;

    /**
     * Constructor for creating sound from path.
     */
    public Sound(String path) {
        try{
            media = new Media(new File(path).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
        }catch (Exception e){
            System.out.println("uet.oop.bomberman.core.sound : error load media");
        }
    }

    /**
     *
     * @param path String path
     * @param volume 0.0 - 1.0
     */
    public Sound(String path, double volume) {
        try{
            media = new Media(new File(path).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            if (volume < 0.3) {
                volume = 0.4;
            }
            mediaPlayer.setVolume(volume);
        }catch (Exception e){
            System.out.println("uet.oop.bomberman.core.sound : error load media");
        }
    }

    public void play(){
        if (mediaPlayer != null) {
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
            });
            mediaPlayer.play();
        }
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        mediaPlayer.setMute(mute);
    }

    public void setVolume(double volume){
        mediaPlayer.setVolume(volume);
    }
}
