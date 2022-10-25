package uet.oop.bomberman.core.scenes.game;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import uet.oop.bomberman.core.sound.BackgroundMusic;

import java.net.URISyntaxException;

public class RightSideBar extends VBox {
    private static boolean initialized = false;
    private static Image upImage;
    private static Image downImage;
    private static Image playImage;
    private static Image pauseImage;
    private static Image volumeImage;
    private static Image muteImage;
    private boolean pause = false;
    private boolean mute = false;


    public static void init() {
        if (!initialized) {
            try {
                upImage = new Image(RightSideBar.class.getResource("/UI/button/up.png").toURI().toString());
                downImage = new Image(RightSideBar.class.getResource("/UI/button/down.png").toURI().toString());
                playImage = new Image(RightSideBar.class.getResource("/UI/button/pause.png").toURI().toString());
                pauseImage = new Image(RightSideBar.class.getResource("/UI/button/play.png").toURI().toString());
                volumeImage = new Image(RightSideBar.class.getResource("/UI/button/volume.png").toURI().toString());
                muteImage = new Image(RightSideBar.class.getResource("/UI/button/volume-mute.png").toURI().toString());
            } catch (URISyntaxException e) {
                System.out.println("load button sound");
            }
            initialized = true;
        }
    }

    private static class SingletonHelper {
        private static final RightSideBar INSTANCE = new RightSideBar();
    }

    public static RightSideBar getInstance() {
        return RightSideBar.SingletonHelper.INSTANCE;
    }

    private RightSideBar() {
        setAlignment(Pos.CENTER);
        setSpacing(40);
        setMaxWidth(30);

        BackgroundMusic music = BackgroundMusic.getInstance();

        ImageView buttonUp = new ImageView(upImage);
        buttonUp.setOnMouseClicked(mouseEvent -> {
            music.next();
        });

        ImageView buttonDown = new ImageView(downImage);
        buttonDown.setOnMouseClicked(mouseEvent -> {
            System.out.println("down click");
            music.next();
        });

        ImageView buttonPlay = new ImageView(playImage);
        buttonPlay.setOnMouseClicked(mouseEvent -> {
            if (!pause) {
                buttonPlay.setImage(pauseImage);
                music.pause();
                pause = true;
            } else {
                pause = false;
                buttonPlay.setImage(playImage);
                music.unpause();
            }
        });
        ImageView buttonMute = new ImageView(volumeImage);
        buttonMute.setFitWidth(30);
        buttonMute.setOnMouseClicked(mouseEvent -> {
            if (!mute) {
                mute = true;
                buttonMute.setImage(muteImage);
            } else {
                mute = false;
                buttonMute.setImage(volumeImage);
            }
            music.setMute(mute);
        });

        getChildren().addAll(buttonUp, buttonPlay, buttonDown, buttonMute);
    }
}
