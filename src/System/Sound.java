package System;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.IOException;

public class Sound {
    private Clip clip;

    public Sound(String path) {
        try {
            // 1. Get the raw byte stream from the JAR (just like your image code)
            InputStream rawStream = getClass().getResourceAsStream(path);
            
            if (rawStream == null) {
                System.err.println("ERROR: Could not find sound at: " + path);
                return; // Stop here so we don't crash below
            }

            // 2. Wrap it in a buffer (CRITICAL for Audio in JARs)
            // AudioSystem needs to "mark" and "reset" the stream, which raw JAR streams can't do.
            InputStream bufferedIn = new BufferedInputStream(rawStream);

            // 3. Get the AudioInputStream
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);

            // 4. Load the clip
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip == null) return;
        // Your thread logic here...
        new Thread(() -> {
            if (clip.isRunning()) clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }).start();
    }
}