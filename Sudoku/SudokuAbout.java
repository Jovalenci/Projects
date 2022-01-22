import java.awt.*;        // Uses AWT's Layout Managers
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.ImageIcon;

public class SudokuAbout extends JFrame {
	JFrame window; 
	Container cp;
	JPanel titleNamePanel, imagePanel, writtingPanel; 
	JLabel titleNameLabel,imageLabel;
	Font titleFont = new Font("Lato",Font.BOLD,30);
	Font normalFont = new Font("Serif",Font.BOLD,30);
	public static final Color PALE_TURQUOISE = new Color(175,238,238);
	public static final Color CORN_FLOWER_BLUE = new Color(100,149,237);
	public static Color background=new Color(247, 235, 254);
	public static Color fontColor=new Color(46,1,70);
	
	public static void main(String[]args) {
		new SudokuAbout();
	}
	
	public SudokuAbout() {
		window = new JFrame("About");
		//window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setSize(800,400);
		window.getContentPane().setBackground(background);;
		window.setLayout(new GridLayout(1,2));
		cp = window.getContentPane();
		imagePanel = new JPanel();
		imagePanel.setBackground(background);
		writtingPanel = new JPanel(new BorderLayout());
		writtingPanel.setBackground(background);
		titleNamePanel = new JPanel(new GridLayout(1,1));
		titleNamePanel.setBackground(background);
		imageLabel = new JLabel("");
		imageLabel.setIcon(new ImageIcon("C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/icon.png"));
		imagePanel.setSize(500, 500);
		titleNameLabel = new JLabel("<html><b><h1><font size=25>SUDOKU</font><br>PUZZLE GAME</font></h1></b><h2>Created by:<br>-Jocelyn Valencia Yoswara (JOCE0019@e.ntu.edu.sg)<br><br>-Vanessa Christy Chandra (VANESSAC001@e.ntu.edu.sg)</h2></br></html></br></html>");
		titleNameLabel.setFont(titleFont);
		titleNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		titleNameLabel.setVerticalAlignment(SwingConstants.CENTER);
		titleNameLabel.setForeground(fontColor);
		
		imagePanel.add(imageLabel);
		titleNamePanel.add(titleNameLabel);
		
		writtingPanel.add(titleNamePanel,BorderLayout.NORTH);
		
		cp.add(imagePanel);
		cp.add(writtingPanel);
		window.setVisible(true);
		titleNameLabel.setVisible(true);
	}

}