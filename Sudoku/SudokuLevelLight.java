import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class SudokuLevelLight extends JFrame implements ActionListener {

	public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);
	public static final Font TITLE_FONT = new Font("Lato", Font.BOLD, 80);
	public static final Font NORMAL_FONT = new Font("Lato", Font.BOLD, 50);
	public static final Color DARK_PURPLE = new Color(46,1,70);
	public static final Color lilac = new Color(230, 230, 255);
	boolean isRunning=false;
	
	private static String filePath = "C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/themeSong.wav";
	private static String clickSound = "C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/soundClick.wav";
	ThemeSong song = new ThemeSong();
	
	JFrame frame;
	JRadioButton easyRadio, mediumRadio, hardRadio;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new SudokuLevelLight();
				}
				catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	public SudokuLevelLight() {
		frame = new JFrame("Sudoku");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width, screenSize.height);
		frame.setResizable(false);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setContentPane(new JLabel(new ImageIcon("C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/img/homepagelight.png")));
		JPanel panel = new JPanel(new BorderLayout());
		//panel.setBackground(Color.WHITE);
		
		JLabel titleNameLabel = new JLabel("PICK A LEVEL");
		titleNameLabel.setForeground(DARK_PURPLE);
		titleNameLabel.setFont(TITLE_FONT);
		titleNameLabel.setBounds(360, 20, 700, 300);
		
		easyRadio = new JRadioButton("EASY", true);
		easyRadio.setBounds(190,300,180,70);
		easyRadio.setBackground(Color.WHITE);
		easyRadio.setForeground(DARK_PURPLE);
		easyRadio.setFont(NORMAL_FONT);
		easyRadio.setHorizontalAlignment(SwingConstants.CENTER);
		
		mediumRadio = new JRadioButton("MEDIUM", true);
		mediumRadio.setBounds(460,300,300,70);
		mediumRadio.setBackground(Color.WHITE);
		mediumRadio.setForeground(DARK_PURPLE);
		mediumRadio.setFont(NORMAL_FONT);
		mediumRadio.setHorizontalAlignment(SwingConstants.CENTER);
		
		hardRadio = new JRadioButton("HARD", true);
		hardRadio.setBounds(850,300,200,70);
		hardRadio.setBackground(Color.WHITE);
		hardRadio.setForeground(DARK_PURPLE);
		hardRadio.setFont(NORMAL_FONT);
		hardRadio.setHorizontalAlignment(SwingConstants.CENTER);
		
		ButtonGroup group = new ButtonGroup();
		group.add(easyRadio);
		group.add(mediumRadio);
		group.add(hardRadio);
		
		JButton beginBtn = new JButton("START");
		beginBtn.setBounds(510,450,200,70);
		beginBtn.setBackground(lilac);
		beginBtn.setFont(NORMAL_FONT);
		beginBtn.setForeground(DARK_PURPLE);
		beginBtn.addActionListener(this);
		ThemeSong song = new ThemeSong();
		if(isRunning=false) {
			song.play();
			isRunning = true;
		}
		
		
		frame.add(titleNameLabel);
		frame.add(easyRadio);
		frame.add(mediumRadio);
		frame.add(hardRadio);
		frame.add(beginBtn);
		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (easyRadio.isSelected()) {
			frame.dispose();
			ClickSound sound = new ClickSound();
			sound.setFile(clickSound);
			sound.play();
			SudokuPuzzle setMasks = new SudokuPuzzle();
			setMasks.setNum(20);
			SudokuLayoutLight frame = new SudokuLayoutLight();
			frame.setVisible(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
		}
		
		if (mediumRadio.isSelected()) {
			frame.dispose();
			ClickSound sound = new ClickSound();
			sound.setFile(clickSound);
			sound.play();
			SudokuPuzzle setMasks = new SudokuPuzzle();
			setMasks.setNum(40);
			SudokuLayoutLight frame = new SudokuLayoutLight();
			frame.setVisible(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			ThemeSong song = new ThemeSong();
				song.play();
			
		}
		
		if (hardRadio.isSelected()){
			frame.dispose();
			ClickSound sound = new ClickSound();
			sound.setFile(clickSound);
			sound.play();
			SudokuPuzzle setMasks = new SudokuPuzzle();
			setMasks.setNum(60);
			SudokuLayoutLight frame = new SudokuLayoutLight();
			frame.setVisible(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			ThemeSong song = new ThemeSong();
			song.play();
		}
	}
}
