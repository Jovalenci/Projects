import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Random;

public class LoginPage extends JFrame {
	public static final int GRID_SIZE = 9;
	public static final int CELL_SIZE = 60;
	public static final int CANVAS_WIDTH = CELL_SIZE*GRID_SIZE;
	public static final int CANVAS_HEIGHT = CELL_SIZE*GRID_SIZE;
	
	public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);
	public static final Font WELCOME_FONT = new Font("Lato", Font.BOLD, 90);
	public static final Font BEGIN_FONT = new Font("Lato", Font.BOLD, 50);
	public static final Font THEME_FONT = new Font("Lato", Font.BOLD, 20);
	
	public static final Color lilac = new Color(230, 230, 255);
	
	private static String filePath = "C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/themeSong.wav";
	private static String clickSound = "C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/soundClick.wav";
	
	public static String playerName="PlayerName";
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
					frame.setVisible(true);
				}
				catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}
	
	public LoginPage() {
		JFrame frame = new JFrame("Sudoku");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width, screenSize.height);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setContentPane(new JLabel(new ImageIcon("C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/homepagelight.png")));
		JPanel panel = new JPanel(new BorderLayout());
		//panel.setBackground(Color.WHITE);
		
		
		
		JLabel welcomeLabel = new JLabel("Welcome!", JLabel.CENTER);
		welcomeLabel.setFont(new Font("Lato", Font.BOLD, 30));
		welcomeLabel.setForeground(Color.BLACK);
		welcomeLabel.setBounds(0,50,screenSize.width, 40);

		JLabel titleNameLabel = new JLabel("SUDOKU", JLabel.CENTER);
		titleNameLabel.setForeground(Color.BLACK);
		titleNameLabel.setFont(WELCOME_FONT);
		titleNameLabel.setBounds(0, 60, screenSize.width, 300);
		
		JLabel userLabel = new JLabel("PLAYER NAME");
		userLabel.setFont(new Font("Lato", Font.BOLD, 25));
		userLabel.setForeground(Color.BLACK);
		userLabel.setBounds(400,300, 220, 40);
		JTextField userTextField = new JTextField();
		userTextField.setBounds(630, 300, 250, 40);
		userTextField.setFont(new Font("Lato", Font.PLAIN, 20));
		
		JButton loginBtn = new JButton("LOGIN");
		loginBtn.setBounds(400,450,110,50);
		loginBtn.setBackground(Color.WHITE);
		loginBtn.setFont(THEME_FONT);
		loginBtn.setForeground(Color.BLACK);
		loginBtn.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e) {
						if(userTextField.getText().isEmpty()==false) {
							JOptionPane.showMessageDialog(null, "Login successful!");
							String name = userTextField.getText();
							frame.dispose();
							HomepageDark frame = new HomepageDark();
							frame.setPlayerName(name);
							frame.setVisible(false);
							new HomepageDark();
							frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						}
						else {
							JOptionPane.showMessageDialog(null, "Please enter your name!");
						}
						
					}
				});
	
		JButton resetBtn = new JButton("Reset");
		resetBtn.setBounds(700,450,110,50);
		resetBtn.setBackground(Color.WHITE);
		resetBtn.setFont(THEME_FONT);
		resetBtn.setForeground(Color.BLACK);
		resetBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				userTextField.setText("");
			}
		});

		frame.add(userTextField);
		frame.add(welcomeLabel);
		frame.add(titleNameLabel);
		frame.add(resetBtn);
		frame.add(loginBtn);
		frame.add(userLabel);
		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
