package com.tlg.view;
import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MusicPlayer {
    private Clip clip;
    private FloatControl volumeControl;

    public MusicPlayer(String filePath) {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream(filePath);
            InputStream buffer = new BufferedInputStream(input);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(buffer);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(0.75f);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null && !clip.isRunning()) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {
            float gain = volumeControl.getMinimum() + (volumeControl.getMaximum() - volumeControl.getMinimum()) * volume;
            volumeControl.setValue(gain);
        }
    }
}