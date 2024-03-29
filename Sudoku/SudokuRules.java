import java.awt.*;        // Uses AWT's Layout Managers
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SudokuRules extends JFrame {
	JFrame window; 
	Container cp;
	JPanel textPanel,backPanel,imagePanel;
	JLabel text,imageLabel;
	JButton backButton;
	Font titleFont = new Font("Lato",Font.BOLD,20);
	Font normalFont = new Font("Lato",Font.BOLD,30);
	public static Color background = new Color(247, 235, 254);
	public static final Color POWDER_BLUE = new Color(176,224,230);
	public static final Color CORN_FLOWER_BLUE = new Color(100,149,237);
	public static Color fontColor;
	
	public static void main(String[]args) {
		new SudokuRules();
	}

	
	public SudokuRules() {
		window = new JFrame("Sudoku Rules");
		window.setSize(800,650);
		window.getContentPane().setBackground(background);
		window.setLayout(new BorderLayout());
		cp = window.getContentPane();
		
		imagePanel = new JPanel();
		imagePanel.setBackground(background);
		imageLabel = new JLabel("");
		imageLabel.setIcon(new ImageIcon("C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/sudokurainbow.jpg"));
		imagePanel.add(imageLabel);
		
		textPanel = new JPanel();
		textPanel.setBackground(background);
		text = new JLabel("<html><br>A Sudoku Puzzle begins with a grid in which some of the numbers <br>are already in place, depending on the game difficulty.<br> A completed puzzle is one where each number from 1 to 9 appears <br>only once in each of the 9 rows, columns, and blocks. <br>Study the grid to find the numbers that might fit into each cell.<br>Select a cell, then tap a number to fill in the cell.<br><br><div align = center><h1>HAPPY PLAYING !</h1></htmml>");
		text.setFont(titleFont);
		text.setForeground(new Color(46,1,70));
		textPanel.add(text);
		
		cp.add(imagePanel,BorderLayout.NORTH);
		cp.add(textPanel,BorderLayout.CENTER);
	
		window.setVisible(true);
		text.setVisible(true);
		
	}

}
