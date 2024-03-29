import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Random;

public class HomepageLight extends JFrame {
	public static final int GRID_SIZE = 9;
	public static final int CELL_SIZE = 60;
	public static final int CANVAS_WIDTH = CELL_SIZE*GRID_SIZE;
	public static final int CANVAS_HEIGHT = CELL_SIZE*GRID_SIZE;
	
	public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);
	public static final Font WELCOME_FONT = new Font("Lato", Font.BOLD, 90);
	public static final Font BEGIN_FONT = new Font("Lato", Font.BOLD, 50);
	public static final Font THEME_FONT = new Font("Lato", Font.BOLD, 30);
	
	public static final Color lilac = new Color(230, 230, 255);
	
	private static String filePath = "C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/themeSong.wav";
	private static String clickSound = "C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/soundClick.wav";
	
	public static String playerName;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomepageLight frame = new HomepageLight();
					frame.setVisible(true);
				}
				catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setPlayerName(String name) {
		this.playerName = name;
	}

	public HomepageLight() {
		JFrame frame = new JFrame("Sudoku");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width, screenSize.height);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setContentPane(new JLabel(new ImageIcon("C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/homepagelight.png")));
		JPanel panel = new JPanel(new BorderLayout());
		//panel.setBackground(Color.WHITE);
		
		
		
		JLabel usernameLabel = new JLabel("Welcome, " + playerName, JLabel.CENTER);
		usernameLabel.setFont(THEME_FONT);
		usernameLabel.setForeground(Color.BLACK);
		usernameLabel.setBounds(0,50,screenSize.width, 40);

		JLabel titleNameLabel = new JLabel("SUDOKU", JLabel.CENTER);
		titleNameLabel.setForeground(Color.BLACK);
		titleNameLabel.setFont(WELCOME_FONT);
		titleNameLabel.setBounds(0, 60, screenSize.width, 300);
		
		JLabel themeTitle = new JLabel("Choose Theme");
		themeTitle.setFont(THEME_FONT);
		themeTitle.setForeground(Color.BLACK);
		themeTitle.setBounds(400,300, 250, 40);

		JButton themeDarkBtn = new JButton("Dark");
		themeDarkBtn.setBounds(640,295,110,50);
		themeDarkBtn.setBackground(Color.WHITE);
		themeDarkBtn.setFont(THEME_FONT);
		themeDarkBtn.setForeground(Color.BLACK);
		themeDarkBtn.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
						HomepageDark frame = new HomepageDark();
						frame.setVisible(true);
						frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					}
				});
	
		JButton themeLightBtn = new JButton("Light");
		themeLightBtn.setBounds(750,295,110,50);
		themeLightBtn.setBackground(lilac);
		themeLightBtn.setFont(THEME_FONT);
		themeLightBtn.setForeground(Color.BLACK);
		themeLightBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {

			}
		});
		
		
		JButton beginBtn = new JButton("BEGIN");
		beginBtn.setBounds(530,400,200,70);
		beginBtn.setBackground(Color.WHITE);
		beginBtn.setFont(BEGIN_FONT);
		beginBtn.setForeground(Color.BLACK);
		beginBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				ClickSound sound = new ClickSound();
				sound.setFile(clickSound);
				sound.play();
				SudokuLevelLight level = new SudokuLevelLight();
			}
		});
		
		frame.add(usernameLabel);
		frame.add(titleNameLabel);
		frame.add(themeLightBtn);
		frame.add(themeDarkBtn);
		frame.add(themeTitle);
		frame.add(beginBtn);
		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
