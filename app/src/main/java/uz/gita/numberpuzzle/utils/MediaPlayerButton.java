package uz.gita.numberpuzzle.utils;
// Created by Jamshid Isoqov an 7/1/2022

import android.content.Context;
import android.media.MediaPlayer;

import uz.gita.numberpuzzle.R;

public class MediaPlayerButton {

    MediaPlayer player;
    private static MediaPlayerButton mediaPlayerButton;

    private MediaPlayerButton() {

    }

    public void soundButtonClick(Context context) {
        player = MediaPlayer.create(context, R.raw.click);
        player.start();
    }

    public void soundGame(Context context) {
        player = MediaPlayer.create(context, R.raw.sound);
        player.start();
        player.setLooping(true);
    }

    public void stopPlayer() {
        if (player != null) {
            player.stop();
        }
    }

    public static MediaPlayerButton getInstance() {
        if (mediaPlayerButton == null) mediaPlayerButton = new MediaPlayerButton();
        return mediaPlayerButton;
    }
}
