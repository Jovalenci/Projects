import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;


public class ClickSound extends JFrame {
	
	Clip clip;
	
	public void setFile(String soundFileName) {
		try {
			File file = new File(soundFileName);
			AudioInputStream sound = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(sound);
		
		}
		catch(Exception e){
			
		}
	}
	
	public void play() {
		clip.setFramePosition(1);
		clip.start();
	}
}

