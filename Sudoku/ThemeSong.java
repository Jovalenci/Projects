import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

public class ThemeSong extends JFrame{
	
	/*public static void main(String[] args) {
		ThemeSong themeSong = new ThemeSong();
		themeSong.play(filePath);
		
		
	}*/
	public Clip clip;
	public boolean isRunning;
	public void play() {
		try {
			String filePath = "C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/themeSong.wav";
			File file = new File(filePath);
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			if(clip.isRunning()) {
				clip.stop();
				clip.close();
			}
			clip.open(audioInput);
			clip.start();
			clip.loop(0);
			isRunning=true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void stop() {
		if(clip.isRunning()) {
			clip.stop();
			clip.close();
			clip = null;
		}
	}
	
	

}
