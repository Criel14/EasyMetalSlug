package com.g543.g543game.manager;
import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class SoundManager {
    private Map<String, Clip> soundMap = new HashMap<>();
    private Properties properties = new Properties();

    public SoundManager() {
        loadSounds();
    }

    public static SoundManager getInstance() {
        return new SoundManager();
    }

    // 加载音频方法
    private void loadSounds() {
        String audioProperties = "properties/AudioData.properties";
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream(audioProperties);
        properties.clear();
        try {
            properties.load(is);
            Set<Object> set = properties.keySet();
            for (Object o : set) {
                String url = properties.getProperty(o.toString());
                loadSound(o.toString(), url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 加载单个音频文件并转换格式
    private void loadSound(String key, String filePath) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream audioSrc = classLoader.getResourceAsStream(filePath);
            if (audioSrc == null) {
                System.err.println("Sound not found: " + filePath);
                return;
            }

            InputStream bufferedIn = new java.io.BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            // 获取音频格式并转换为常见的 PCM_SIGNED 格式
            AudioFormat baseFormat = audioStream.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );
            AudioInputStream decodedAudioStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);

            DataLine.Info info = new DataLine.Info(Clip.class, decodedFormat);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(decodedAudioStream);
            soundMap.put(key, audioClip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    // 播放音频
    public void playSound(String soundName) {
        Clip clip = soundMap.get(soundName);
        if (clip != null) {
            clip.setFramePosition(0); // 将音频播放位置重置为开始
            clip.start();
        } else {
            System.err.println("Sound not found: " + soundName);
        }
    }
}


