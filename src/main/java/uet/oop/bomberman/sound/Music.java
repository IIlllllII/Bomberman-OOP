package uet.oop.bomberman.sound;

public class Music extends Sound {
    public static final String MENU_MUSIC = "src/main/resources/sound/Title.mp3";
    public static final String GAME_MUSIC = "src/main/resources/sound/inGame.mp3";

    public static final String MOVING_SOUND = "src/main/resources/sound/moving.mp3";
    private boolean loops;

    public enum MusicStatus {
        PLAY, STOP, PAUSE;
    }

    private MusicStatus status = MusicStatus.STOP;

    public Music() {
    }

    public Music(String path, boolean loops) {
        super(path);
        this.loops = loops;
    }

    public void setLoops(boolean loops) {
        this.loops = loops;
    }

    public boolean isLoops() {
        return loops;
    }

    public void playMusic() {
        if (status == MusicStatus.STOP && !muteAll) {
            if(loops){
                mediaPlayer.setCycleCount(Integer.MAX_VALUE);
            }
            status = MusicStatus.PLAY;
            mediaPlayer.play();
        }
    }


    public void pauseMusic() {
        if (status == MusicStatus.PLAY) {
            status = MusicStatus.PAUSE;
            mediaPlayer.pause();
        }
    }

    public void resumeMusic() {
        if (status == MusicStatus.PAUSE) {
            status = MusicStatus.PLAY;
            mediaPlayer.play();
        }
    }

    public void stopMusic() {
        status = MusicStatus.STOP;
        mediaPlayer.stop();
    }
}
