import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class PostGame {
	public static final int GRID_SIZE = 9;
	public static final int CELL_SIZE = 60;
	public static final int CANVAS_WIDTH = CELL_SIZE*GRID_SIZE;
	public static final int CANVAS_HEIGHT = CELL_SIZE*GRID_SIZE;
	
	public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);
	public static final Font NEWGAME_FONT = new Font("Lato", Font.BOLD, 30);
	
	public static void main(String[] args) {
		new PostGame();
	}
	public PostGame() {
		JFrame frame = new JFrame("Sudoku");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width, screenSize.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setContentPane(new JLabel(new ImageIcon("C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/homepagedark.png")));
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.BLACK);
		

		JButton newGameBtn = new JButton("PLAY AGAIN");
		newGameBtn.setBounds(480,300,300,70);
		newGameBtn.setBackground(Color.WHITE);
		newGameBtn.setFont(NEWGAME_FONT);
		newGameBtn.setForeground(Color.BLACK);
		newGameBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new HomepageDark();
			}
		});
		
		frame.add(newGameBtn);
		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	
}
