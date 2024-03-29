import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.AbstractBorder;

import java.io.*;
import java.util.Random;


public class HomepageDark extends JFrame {
	public static final int GRID_SIZE = 9;
	public static final int CELL_SIZE = 60;
	public static final int CANVAS_WIDTH = CELL_SIZE*GRID_SIZE;
	public static final int CANVAS_HEIGHT = CELL_SIZE*GRID_SIZE;
	
	public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);
	public static final Font WELCOME_FONT = new Font("Lato", Font.BOLD, 90);
	public static final Font BEGIN_FONT = new Font("Lato", Font.BOLD, 50);
	public static final Font THEME_FONT = new Font("Lato", Font.BOLD, 30);
	public static final Color DARK_PURPLE = new Color(46,1,70);
	public static final Color lilac = new Color(230, 230, 255);
	
	private static String filePath = "C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/themeSong.wav";
	private static String clickSound = "C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/soundClick.wav";
	
	public static String playerName;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new HomepageDark();

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

	public HomepageDark() {
		
		
		JFrame frame = new JFrame("Sudoku");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width, screenSize.height);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setContentPane(new JLabel(new ImageIcon("C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/homepagedark.png")));
		JPanel panel = new JPanel(new BorderLayout());
		//panel.setBackground(Color.BLACK);
		

		JLabel usernameLabel = new JLabel("Welcome, " + playerName, JLabel.CENTER);
		usernameLabel.setFont(THEME_FONT);
		usernameLabel.setForeground(Color.WHITE);
		usernameLabel.setBounds(0,50,screenSize.width, 40);

		JLabel titleNameLabel = new JLabel("SUDOKU", JLabel.CENTER);
		titleNameLabel.setForeground(Color.WHITE);
		titleNameLabel.setFont(WELCOME_FONT);
		titleNameLabel.setBounds(0, 60, screenSize.width, 300);
		
		JLabel themeTitle = new JLabel("Choose Theme");
		themeTitle.setFont(THEME_FONT);
		themeTitle.setForeground(Color.WHITE);
		themeTitle.setBounds(410,300, 250, 40);
		
		JButton themeDarkBtn = new JButton("Dark");
		themeDarkBtn.setBounds(640,295,110,50);
		themeDarkBtn.setBackground(lilac);
		themeDarkBtn.setFont(THEME_FONT);
		themeDarkBtn.setForeground(Color.BLACK);
		themeDarkBtn.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e) {
						
					}
				});
		

		JButton themeLightBtn = new JButton("Light");
		themeLightBtn.setBounds(750,295,110,50);
		themeLightBtn.setBackground(DARK_PURPLE);
		themeLightBtn.setFont(THEME_FONT);
		themeLightBtn.setForeground(Color.WHITE);
		themeLightBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				HomepageLight frame = new HomepageLight();
				frame.setPlayerName(playerName);
				frame.setVisible(true);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});

		JButton beginBtn = new JButton("BEGIN");
		beginBtn.setBounds(530,400,200,70);
		beginBtn.setBackground(DARK_PURPLE);
		beginBtn.setFont(BEGIN_FONT);
		beginBtn.setForeground(Color.WHITE);
		beginBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				ClickSound sound = new ClickSound();
				sound.setFile(clickSound);
				sound.play();
				SudokuLevelDark level = new SudokuLevelDark();
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
