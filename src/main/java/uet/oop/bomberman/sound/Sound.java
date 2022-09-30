package uet.oop.bomberman.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sound {
    protected Media media;
    protected MediaPlayer mediaPlayer;
    private String path;

    /**
     * Constructor for creating sound from path.
     */
    public Sound(String path){
        this.path = path;
        try{
            media = new Media(new File(path).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
        }catch (Exception e){
            System.out.println("uet.oop.bomberman.sound : error load media");
        }
    }

    public Sound(){}

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

    public void playSound(){
        mediaPlayer.play();
    }

    public void mute() {
        mediaPlayer.setMute(true);
    }

    public void unmute() {
        mediaPlayer.setMute(false);
    }

}
