package System;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.IOException;

/**
 * Wrapper class for loading and playing audio files in Java.
 */
public class Sound {
    private Clip clip;

    /**
     * Loads an audio file from the resources directory into memory.
     * * @param path The classpath-relative path to the .wav file.
     */
    public Sound(String path) {
        try {
            // 1. Get the raw byte stream from the JAR (just like image loading code)
            InputStream rawStream = getClass().getResourceAsStream(path);
            
            if (rawStream == null) {
                System.err.println("ERROR: Could not find sound at: " + path);
                return; // Stop here so we don't crash below
            }

            // 2. Wrap it in a buffer (CRITICAL for Audio in JARs)
            // AudioSystem needs to "mark" and "reset" the stream, which raw JAR streams can't do natively.
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

    /**
     * Plays the sound effect. If the sound is already playing, it will be 
     * stopped, rewound to the beginning, and restarted. 
     * Runs on a separate thread to prevent stalling the game loop.
     */
    public void play() {
        if (clip == null) return;
        
        new Thread(() -> {
            if (clip.isRunning()) clip.stop();
            clip.setFramePosition(0); // Rewind to start
            clip.start();
        }).start();
    }
}