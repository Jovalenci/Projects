import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.Timer;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.applet.*;

public class SudokuLayoutLight extends JFrame{
	public static final int GRID_SIZE = 9;
	public static final int SUBGRID_SIZE = 3;
	public static final int CELL_SIZE = 60;
	public static final int CANVAS_WIDTH = CELL_SIZE*GRID_SIZE;
	public static final int CANVAS_HEIGHT = CELL_SIZE*GRID_SIZE;
	private JTextField[][] tfCells = new JTextField[GRID_SIZE][GRID_SIZE];
	public static int[][] restore = new int[9][9];
	
	String[] levelChoices = {"Easy", "Medium", "Hard"};
	JComboBox<String> cb = new JComboBox<String>(levelChoices);
	
	public static final Color OPEN_CELL_BGCOLOR = new Color(229, 204, 255);
	public static final Color OPEN_CELL_TEXT_YES = new Color(0, 255, 0);
	public static final Color OPEN_CELL_TEXT_NO = new Color(255, 102, 102);
	public static final Color CLOSED_CELL_BGCOLOR = Color.white;
	public static final Color CLOSED_CELL_TEXT =  Color.BLACK;
	public static final Color lilac = new Color(230, 230, 255);
	public static final Color DARK_PURPLE = new Color(46,1,70);
	public static final Color SKY_BLUE = new Color(135,206,250);
	public static final Color HOT_PINK = new Color(255,182,193);
	public static final Color MOCASIN = new Color(255,228,181);
	public static final Color conflict = new Color(255,153,153);
	public static final Font FONT_NUMBERS = new Font("Lato", Font.BOLD, 20);

	private final Border b0 = BorderFactory.createMatteBorder(0,0,1,1,Color.GRAY);
	private final Border b1 = BorderFactory.createMatteBorder(0, 0, 2, 2, Color.BLACK);
	private final Border b2 = BorderFactory.createCompoundBorder(
			      BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK),
			      BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
	private final Border b3 = BorderFactory.createCompoundBorder(
			      BorderFactory.createMatteBorder(0, 0, 0,2, Color.BLACK),
			      BorderFactory.createMatteBorder(0, 0,1, 0, Color.GRAY));
	private final Border b4 = BorderFactory.createCompoundBorder(
			      BorderFactory.createMatteBorder(2, 2, 0,0, Color.BLACK),
			      BorderFactory.createMatteBorder(0, 0,1, 0, Color.GRAY));
	private final Border b5 = BorderFactory.createCompoundBorder(
			      BorderFactory.createMatteBorder(2, 0, 0,0, Color.BLACK),
			      BorderFactory.createMatteBorder(0, 1,1, 0, Color.GRAY));
	private final Border b6 = BorderFactory.createCompoundBorder(
			      BorderFactory.createMatteBorder(0, 2, 0,0, Color.BLACK),
			      BorderFactory.createMatteBorder(0, 1,1, 1, Color.GRAY));
	private final Border b7 = BorderFactory.createCompoundBorder(
			      BorderFactory.createMatteBorder(2, 0, 0,2, Color.BLACK),
			      BorderFactory.createMatteBorder(0, 1,1, 0, Color.GRAY));
	private final Border b8 = BorderFactory.createCompoundBorder(
			      BorderFactory.createMatteBorder(0, 2, 2,0, Color.BLACK),
			      BorderFactory.createMatteBorder(0, 0,0, 1, Color.GRAY));
	SudokuLevelLight sudokulight = new SudokuLevelLight();
	

	private static int[][] puzzle = SudokuPuzzle.getPuzzle(); //generate the random puzzle
	private static boolean[][] masks = SudokuPuzzle.getMasks(); //generate the random position of open cells
	public static int idxLevel;
	//for the stopwatch timer
	private JPanel stopWatchPanel = new JPanel();
	JLabel timeLabel = new JLabel();
	int elapsedTime = 0; 
	int seconds = 0;
	int minutes = 0;
	int hours = 0;
	boolean started = true;
	String seconds_string = String.format("%02d", seconds);
	String minutes_string = String.format("%02d", minutes);
	String hours_string = String.format("%02d", hours);
	Timer timer = new Timer(1000, new ActionListener( ) {
		public void actionPerformed(ActionEvent e) {
			elapsedTime=elapsedTime+1000;
			hours = (elapsedTime/3600000);
			minutes = (elapsedTime/60000) % 60;
			seconds = (elapsedTime/1000) % 60;
			seconds_string = String.format("%02d", seconds);
			minutes_string = String.format("%02d", minutes);
			hours_string = String.format("%02d", hours);
			timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
		}
	});
	JLabel statusLabel;
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SudokuLayoutLight frame = new SudokuLayoutLight();
					frame.setVisible(true);
				}
				catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	//generate the puzzle layout
	public SudokuLayoutLight() {
		JFrame frame = new JFrame("Sudoku");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
		frame.setResizable(false);
		frame.setSize(screenSize.width, screenSize.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = getContentPane();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setContentPane(new JLabel(new ImageIcon("C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/homepagelight.png")));
		
		JPanel bgPanel = new JPanel(new BorderLayout());
		bgPanel.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
		bgPanel.setLocation(120,80);
		bgPanel.setOpaque(false);
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.BLACK);
		panel.setOpaque(false);
		panel.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);

		JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
		
		CellKeyListener keyListener = new CellKeyListener();
		CellFocusListener focusListener = new CellFocusListener();
		
		//generate open cells
		for (int row=0; row<GRID_SIZE; row++) {
			for (int col=0; col<GRID_SIZE; col++) {
				tfCells[row][col] = new JTextField();
				if (masks[row][col]) {
					tfCells[row][col].setText("");
					tfCells[row][col].setEditable(true);
					tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);

					tfCells[row][col].addFocusListener(focusListener);
					tfCells[row][col].addKeyListener(keyListener);
				} else {
					tfCells[row][col].setText(puzzle[row][col] + "");
					tfCells[row][col].setEditable(false);
					tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
					tfCells[row][col].setForeground(CLOSED_CELL_TEXT);
				}
				
				// Beautify all the cells with borders
	            tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
	            tfCells[row][col].setFont(FONT_NUMBERS);
	            if((row + 1) % 3 == 0&&(col + 1) % 3 == 0) {
	            	  tfCells[row][col].setBorder(b1);
	            }
	            else if((row+1)%3==0) {
	            	tfCells[row][col].setBorder(b2);
	            }else if((col+1)%3==0) {
	            	tfCells[row][col].setBorder(b3);
	            }
	            else {
	            	 tfCells[row][col].setBorder(b0);
	            }
	            if(row==0&&col==0) {
	        	  tfCells[row][col].setBorder(b4);
	            }else if(row==0&&(col+1)%3==0) {
	        	  tfCells[row][col].setBorder(b7);
	            }else if((row+1)%3==0&&col==0) {
	        	  tfCells[row][col].setBorder(b8);
	            }else if(row==0) {
	            	tfCells[row][col].setBorder(b5);
	            }else if(col==0) {
	            	tfCells[row][col].setBorder(b6);
	            }
	            gridPanel.add(tfCells[row][col]);
	         }
		}
	
		//start the stopwatch timer everytime begin the puzzle
		start();
		
		JLabel difficultyLabel = new JLabel("Difficulty:");
		difficultyLabel.setFont(new Font("Lato",Font.BOLD,18));
		difficultyLabel.setForeground(Color.BLACK);
		difficultyLabel.setBounds(120,30,100,40);
		
		int blankCells = getNumOfBlankCells();
		
		if(blankCells<=20) {
			idxLevel=0;
		}
		else if(blankCells>20&&blankCells<=40) {
			idxLevel=1;
		}
		else if (blankCells>40){
			idxLevel=2;
		}
		
		
		switch(idxLevel) {
		case 0:
			cb.setSelectedIndex(0);
			break;
		case 1:
			cb.setSelectedIndex(1);
			break;
		case 2:
			cb.setSelectedIndex(2);
			break;
		}
		
		cb.setBounds(220, 30, 100, 40);
		cb.setFont(new Font("Lato", Font.BOLD, 15));
		cb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			JComboBox<String> combo = (JComboBox<String>) e.getSource();
			String selectedLevel = (String)combo.getSelectedItem();
			SudokuPuzzle selectPuzzle = new SudokuPuzzle();
			if(selectedLevel.equals("Easy")) {
				frame.dispose();
				selectPuzzle.setNum(20);
				masks = SudokuPuzzle.getMasks();
				idxLevel=0;
				cb.setSelectedIndex(idxLevel);
				new SudokuLayoutLight();
				
			}else if (selectedLevel.equals("Medium")) {
				frame.dispose();
				selectPuzzle.setNum(40);
				masks = SudokuPuzzle.getMasks();
				idxLevel=1;
				cb.setSelectedIndex(idxLevel);
				new SudokuLayoutLight();
				
			}else if(selectedLevel.equals("Hard")) {
				frame.dispose();
				selectPuzzle.setNum(60);
				masks = SudokuPuzzle.getMasks();
				idxLevel=2;
				cb.setSelectedIndex(idxLevel);
				new SudokuLayoutLight();
				
				
			}
			}
		});
		
		//add new game button to generate new puzzle 
		JButton newGameBtn = new JButton("New Game");
		newGameBtn.setFont(new Font("Lato", Font.BOLD, 18));
		newGameBtn.setToolTipText("Generate new puzzle");
		newGameBtn.setBounds(700,80,150,50);
		newGameBtn.setBackground(lilac);
		newGameBtn.setForeground(DARK_PURPLE);
		newGameBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				puzzle = SudokuPuzzle.getPuzzle();
				masks = SudokuPuzzle.getMasks();
				frame.dispose();
				SudokuLayoutLight frame = new SudokuLayoutLight();
				frame.setVisible(true);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		
		//add reset button to reset the current puzzle
		JButton resetBtn = new JButton("Reset");
		resetBtn.setFont(new Font("Lato", Font.BOLD, 18));
		resetBtn.setBounds(700,140,150,50);
		resetBtn.setToolTipText("Reset all your current progress");
		resetBtn.setBackground(DARK_PURPLE);
		resetBtn.setForeground(lilac);
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				SudokuLayoutLight frame = new SudokuLayoutLight();
				frame.setVisible(true);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		
		
		JButton muteBtn = new JButton("Mute");
		muteBtn.setFont(new Font("Lato", Font.BOLD, 18));
		muteBtn.setBounds(700,200,150,50);
		muteBtn.setToolTipText("Mute the music");
		muteBtn.setBackground(DARK_PURPLE);
		muteBtn.setForeground(lilac);
		//add the time text display for the stopwatch timer
		timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
		timeLabel.setFont(new Font("Lato",Font.PLAIN,20));
		timeLabel.setForeground(Color.BLACK);
		timeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//timeLabel.setOpaque(true);
		timeLabel.setBounds(510,30,100,40);
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
	
		//add the time button for the stopwatch timer
		JButton timeBtn = new JButton();
		timeBtn.setIcon(new ImageIcon("C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/playIcon.png"));
		timeBtn.setBounds(620,30,40,40);
		timeBtn.setBackground(Color.BLACK);
		timeBtn.setForeground(Color.WHITE);
		timeBtn.addActionListener(new ActionListener() {
			boolean started=true;
			public void actionPerformed(ActionEvent e) {
				if(started==false) {
					started=true;
					timeBtn.setIcon(new ImageIcon("C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/playIcon.png"));
					start();
				}else {
					started=false;
					timeBtn.setIcon(new ImageIcon("C:/Users/ASUS/eclipse-workspace/Java/bin/Sudoku/src/pauseIcon.png"));
					stop();
				}
			}
		});
		
		
		/*JButton hintBtn = new JButton("Hint");
		hintBtn.setLocation(300, 100);
		hintBtn.setBounds(700,150,100,50);
		hintBtn.setBackground(Color.WHITE);
		hintBtn.setForeground(Color.BLACK);
		hintBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					for (int i=0; i<GRID_SIZE; i++) {
						for (int j=0; j<GRID_SIZE; j++) {
							if(masks[i][j]==true) {
								masks[i][j]=false;
							
							}
					
						}
					}

				
				SudokuLayoutLight frame = new SudokuLayoutLight();
				frame.setVisible(true);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		*/

		//add menu bar that consists Sudoku Rules, Options, About, Exit at the PAGE_START position
		JMenuBar bar;
        bar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem rules   = new JMenuItem("Sudoku Rules");
        JMenuItem tips = new JMenuItem("Tips");
        JMenuItem about = new JMenuItem("About");
        JMenuItem exit = new JMenuItem("Exit");
        fileMenu.add(rules);
        fileMenu.addSeparator();
        fileMenu.add(tips);
        fileMenu.addSeparator();
        fileMenu.add(about);
        fileMenu.addSeparator();
        fileMenu.add(exit);
        bar.add(fileMenu);
        setJMenuBar(bar);
        
        exit.addActionListener((ActionEvent e)->{ //go to home page
        	frame.dispose();
        	new HomepageLight();
        });
        rules.addActionListener((ActionEvent e)->{ //go to SudokuRules homepage
        	new SudokuRules();
        	
        });
        tips.addActionListener((ActionEvent e)->{ //go to SudokuLevel homepage
        	new SudokuTips();
        });
        about.addActionListener((ActionEvent e)->{ //go to SudokuAbout homepage
        	new SudokuAbout();
        });
        
        //status bar for displaying how many square still left 
        statusLabel = new JLabel(String.valueOf("Cells left: "+getNumOfBlankCells()));
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusLabel.setFont(FONT_NUMBERS);
        statusLabel.setBounds(120, 630,120, 30);
        
		//combine all the components
		bgPanel.add(gridPanel, BorderLayout.CENTER);
		frame.add(newGameBtn);
		frame.add(resetBtn);
		frame.add(timeBtn);
		frame.add(muteBtn);
		frame.add(timeLabel);
		frame.add(cb);
		//frame.add(hintBtn);
		frame.add(difficultyLabel);
		frame.add(bgPanel, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.CENTER);
		frame.add(bar, BorderLayout.PAGE_START);
		frame.add(statusLabel);
		frame.setVisible(true);
	}
	
	public void updateStatus() {
		statusLabel.setText(String.valueOf("Cells left: " + getNumOfBlankCells()));
	}
	public int getNumOfBlankCells() {
		int numOfBlankCells=0;
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(masks[i][j]==true) {
					++numOfBlankCells;
				}
			}
		}
		return numOfBlankCells;
	}
	public void start() {
		timer.start();
	}
	public void stop() {
		timer.stop();
	}
	public void reset() {
		timer.stop();
		elapsedTime = 0;
		seconds = 0;
		minutes = 0;
		hours = 0;
		seconds_string = String.format("%02d", seconds);
		minutes_string = String.format("%02d", minutes);
		hours_string = String.format("%02d", hours);
		timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
	}
	
	//check for conflicting cells & highlight them
	public void check2(int rowSelected, int colSelected, int inp, Color background) {
		for(int row=0; row<GRID_SIZE; row++) {
			if(tfCells[row][colSelected].getText().isEmpty()) {
				continue;
			}
			else if (tfCells[row][colSelected].isEditable()) {
				continue;
			}
			else if (inp==Integer.parseInt(tfCells[row][colSelected].getText())) {
				tfCells[row][colSelected].setBackground(background);
			}
		}
		
		for (int col=0; col<GRID_SIZE; col++) {
			if(tfCells[rowSelected][col].getText().isEmpty()) {
				continue;
			}
			else if (tfCells[rowSelected][col].isEditable()) {
				continue;
			}
			else if (inp == Integer.parseInt(tfCells[rowSelected][col].getText())) {
				tfCells[rowSelected][col].setBackground(background);
			}
		}
		
		int newRow = rowSelected-rowSelected%3;
		int newCol = colSelected - colSelected%3;
		
		for (int row=newRow; row<newRow+3; row++) {
			for(int col=newCol; col<newCol+3; col++) {
				if (tfCells[row][col].getText().isEmpty()) {
					continue;
				}
				else if (tfCells[row][col].isEditable()) {
					continue;
				}
				else if (inp==Integer.parseInt(tfCells[row][col].getText())) {
					tfCells[row][col].setBackground(background);
				}
			}
		}	
		
	}
	
	public void check(int rowSelected, int colSelected, int inp, Color background) {
		int forCheck2;
		
		for (int row=0; row<GRID_SIZE; row++) {
			if(tfCells[row][colSelected].getText().isEmpty()) {
				continue;
			}
			else {
				if(tfCells[row][colSelected].isEditable()) {
					forCheck2=Integer.parseInt(tfCells[row][colSelected].getText());
					check2(row, colSelected, forCheck2, background);
				}
				else if (inp==Integer.parseInt(tfCells[row][colSelected].getText())) {
					tfCells[row][colSelected].setBackground(background);
				}
			}
		}
		
		for (int col=0; col<GRID_SIZE; ++col) {
			if(tfCells[rowSelected][col].getText().isEmpty()) {
				continue;
			}
			else if (tfCells[rowSelected][col].isEditable()) {
				forCheck2 = Integer.parseInt(tfCells[rowSelected][col].getText());
				check2(rowSelected, col, forCheck2, background);
			}
			else if (inp == Integer.parseInt(tfCells[rowSelected][col].getText())) {
				tfCells[rowSelected][col].setBackground(background);
			}
		}
		
		int newRow = rowSelected-rowSelected%3;
		int newCol = colSelected - colSelected%3;
		for (int row = newRow; row<newRow+3; row++) {
			for(int col=newCol; col<newCol+3; col++) {
				if(tfCells[row][col].getText().isEmpty()) {
					continue;
				}
				else if (tfCells[row][col].isEditable()) {
					forCheck2 = Integer.parseInt(tfCells[row][col].getText());
					check2(row, col, forCheck2, background);
				}
				else if (inp==Integer.parseInt(tfCells[row][col].getText())) {
					tfCells[row][col].setBackground(background);
				}
			}
		}
	}
	
	//takes input without requiring user to press enter
	public class CellKeyListener implements KeyListener{
		
		int rowSelected=-1, colSelected=-1;
		int tempRow, tempCol;
	
		@Override
		public void keyTyped(KeyEvent e) {
			//generate number of open cells for status bar
	
			int keyCode = Integer.parseInt(Character.toString(e.getKeyChar()));
			boolean found = false;
			JTextField source = (JTextField)e.getSource();
			for (int row = 0; row<GRID_SIZE && !found; ++row) {
				for (int col=0; col<GRID_SIZE && !found; ++col) {
					if (tfCells[row][col] == source) {
						rowSelected = row;
						tempRow = rowSelected;
						colSelected = col;
						tempCol = colSelected;
						found = true;
					}
				}
			}
			
			int input = keyCode;
			
			if (input>9 || input<0) {
				tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_NO);
				JOptionPane.showMessageDialog(null, "Please input an integer 1-9");
			}
			else {
				if (input==puzzle[rowSelected][colSelected]) {
					tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_YES);
					masks[rowSelected][colSelected]=false;
					updateStatus();
				}
				else {
					tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_NO);
					masks[rowSelected][colSelected]=true;
					updateStatus();
				}
				//conflicting cells
				check(rowSelected, colSelected, restore[rowSelected][colSelected], CLOSED_CELL_BGCOLOR);
				restore[rowSelected][colSelected] = input;
				check(rowSelected, colSelected, input, conflict);	
			}	
			
			
			
			int correct=0;
			
			for(int row=0; row<GRID_SIZE; row++) {
				for(int col=0; col<GRID_SIZE; col++) {
					if (masks[row][col]==true) {
						break;
					}
					else {
						correct++;
					}
				}
			}
			if(correct==81) {
				JOptionPane.showMessageDialog(null, "Congratulation");
				PostGame frame = new PostGame();
			}
		}
		
		public int[] getSelectedCells(){
			int[] tempCell = new int[2];
			tempCell[0] = tempRow;
			tempCell[1] = tempCol;
			return tempCell;
			
		}
		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}
	
	
	
	
	//focusing color on selected cell
	public class CellFocusListener implements FocusListener {
		int rowSelected, colSelected;
		int tempRow, tempCol;

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			boolean found = false;
			JTextField source = (JTextField)e.getSource();
			for (int row = 0; row<GRID_SIZE && !found; ++row) {
				for (int col=0; col<GRID_SIZE && !found; ++col) {
					if (tfCells[row][col] == source) {
						rowSelected = row;
						tempRow = rowSelected;
						colSelected = col;
						tempCol = colSelected;
						found = true;
					}
				}
			}
			tfCells[rowSelected][colSelected].setBackground(Color.LIGHT_GRAY);
		}
	
		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			CellKeyListener kl = new CellKeyListener();
			tfCells[tempRow][tempCol].setBackground(OPEN_CELL_BGCOLOR);
			if(Integer.parseInt(tfCells[tempRow][tempCol].getText())==puzzle[tempRow][tempCol]) {
				tfCells[tempRow][tempCol].setBackground(OPEN_CELL_TEXT_YES);
			}
			else {
				tfCells[tempRow][tempCol].setBackground(OPEN_CELL_TEXT_NO);
			}
		}
		
	}
	
}
