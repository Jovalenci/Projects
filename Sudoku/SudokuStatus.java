import java.awt.*;        // Uses AWT's Layout Managers
import java.awt.event.*;  // Uses AWT's Event Handlers
import javax.swing.*;     // Uses Swing's Container/Components
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.*;


public class SudokuStatus extends JFrame {
   // Name-constants for the game properties
   public static final int GRID_SIZE = 9;    // Size of the board
   public static final int SUBGRID_SIZE = 3; // Size of the sub-grid
 
   // Name-constants for UI control (sizes, colors and fonts)
   public static final int CELL_SIZE = 60;   // Cell width/height in pixels
   public static final int CANVAS_WIDTH  = CELL_SIZE * GRID_SIZE;
   public static final int CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE; // Board width/height in pixels
   public static final Color OPEN_CELL_BGCOLOR = new Color(175,238,238);
   public static final Color OPEN_CELL_TEXT_YES = new Color(0, 255, 0);  // RGB
   public static final Color OPEN_CELL_TEXT_NO = Color.RED;
   public static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240); // RGB
   public static final Color CLOSED_CELL_TEXT = Color.BLACK;
   public static final Color SKY_BLUE = new Color(135,206,250);
   public static final Color HOT_PINK = new Color(255,182,193);
   public static final Color MOCASIN = new Color(255,228,181);
   public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);
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
   public static final Font FONT_STATUS = new Font("Serif",Font.BOLD,15);

   private JTextField[][] tfCells = new JTextField[GRID_SIZE][GRID_SIZE];
   JPanel gridPanel = new JPanel();
   JPanel statusBar = new JPanel();
   JPanel stopwatchPanel = new JPanel();
   JPanel startButtonPanel = new JPanel();
   JPanel resetButtonPanel = new JPanel();
   JPanel timeLabelPanel = new JPanel();
   JPanel timeButton = new JPanel();
   JLabel squareStatus = new JLabel();
   JPanel hintPanel = new JPanel();
   JButton hintButton = new JButton();
   int squareNumber;
   
   private int[][] puzzle =
      {{5, 3, 4, 6, 7, 8, 9, 1, 2},
       {6, 7, 2, 1, 9, 5, 3, 4, 8},
       {1, 9, 8, 3, 4, 2, 5, 6, 7},
       {8, 5, 9, 7, 6, 1, 4, 2, 3},
       {4, 2, 6, 8, 5, 3, 7, 9, 1},
       {7, 1, 3, 9, 2, 4, 8, 5, 6},
       {9, 6, 1, 5, 3, 7, 2, 8, 4},
       {2, 8, 7, 4, 1, 9, 6, 3, 5},
       {3, 4, 5, 2, 8, 6, 1, 7, 9}};
   // For testing, open only 2 cells.
   private boolean[][] masks =
      {{true, false, false, false, false, true, false, false, false},
       {false, false, false, false, true, false, false, false, true},
       {false, false, true, false, false, false, true, false, false},
       {false, false, false, true, true, false, false, false, false},
       {false, true, false, false, false, true, false, false, false},
       {false, false, true, false, false, true, false, false, false},
       {false, true, false, false, true, false, false, false, false},
       {false, false, false, false, true, false, false, false, false},
       {false, false, true, false, false, false, true, false, false}};
 
   public static void main(String[] args) {
	      SwingUtilities.invokeLater(new Runnable() {
	    	  @Override
	    	  public void run() {
	    		  new SudokuStatus();
	    	  }
	      });
	   }
   public SudokuStatus() {
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());
      gridPanel = new JPanel(new GridLayout(GRID_SIZE,GRID_SIZE));
      InputListener listener = new InputListener();
      // Construct 9x9 JTextFields and add to the content-pane
      for(int row=0;row<GRID_SIZE;++row) {
    	  for(int col=0;col<GRID_SIZE;++col) {
    		  if(masks[row][col]==true) {
    			  ++squareNumber;
    		  }
    	  }
      }
      for (int row = 0; row < GRID_SIZE; ++row) {
         for (int col = 0; col < GRID_SIZE; ++col) {
            tfCells[row][col] = new JTextField(); // Allocate element of array
            cp.add(tfCells[row][col]);            // ContentPane adds JTextField    
            if (masks[row][col]) {
               tfCells[row][col].setText("");     // set to empty string
               tfCells[row][col].setEditable(true);
               tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
               tfCells[row][col].addActionListener(listener); 

            } else {
               tfCells[row][col].setText(puzzle[row][col] + "");
               tfCells[row][col].setEditable(false);
               tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
               tfCells[row][col].setForeground(CLOSED_CELL_TEXT);
            }
            // Beautify all the cells
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
            if((row==3||row==4||row==5||col==3||col==4||col==5)&&masks[row][col]==false) {
            	if((row==col)||(row==3&&col==4)||(row==3&&col==5)||(row==4&&col==3)||(row==4&&col==5)||(row==5&&col==3)||(row==5&&col==4)) {
            		tfCells[row][col].setBackground(SKY_BLUE);
            	}else {
            		tfCells[row][col].setBackground(HOT_PINK);
            	}
              }else if(masks[row][col]==false){
              	tfCells[row][col].setBackground(SKY_BLUE);
              }
            gridPanel.add(tfCells[row][col]);
         }
      }
      //status bar for displaying how many square still left ( belum update kalau udah ke solve si squareNumber nya bakal berkurang 
      statusBar = new JPanel();
      statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
      statusBar.setPreferredSize(new Dimension(getWidth(), 16));
      statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
      JLabel statusLabel = new JLabel("Square Left : ");
      statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
      statusLabel.setFont(FONT_STATUS);
      JLabel squareStatus = new JLabel();
      squareStatus.setHorizontalAlignment(SwingConstants.LEFT);
      squareStatus.setFont(FONT_STATUS);
      squareStatus.setText(""+squareNumber);
      statusBar.add(statusLabel);
      statusBar.add(squareStatus);
      hintPanel = new JPanel();
      hintButton = new JButton();
      hintButton.setText("Hint");
      hintButton.setFont(new Font("Times New Roman",Font.BOLD,14));
      hintButton.setFocusable(false);
      hintPanel.add(hintButton);
      hintButton.setVisible(true);
      cp.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
      pack();
      buildMenu();
      StopWatch stopwatch = new StopWatch();
      stopwatch.start();
      cp.add(stopwatchPanel,BorderLayout.NORTH);
      cp.add(gridPanel,BorderLayout.CENTER);
      cp.add(statusBar,BorderLayout.SOUTH);
      cp.add(hintPanel,BorderLayout.WEST);
 
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Handle window closing
      setTitle("Sudoku Status");
      setVisible(true);
   }  
 
   private class InputListener implements ActionListener {
	   
	      @Override
	      public void actionPerformed(ActionEvent e) {
	         int rowSelected = -1;
	         int colSelected = -1;
	         JTextField source = (JTextField)e.getSource();
	         boolean found = false;
	         
	         for (int row = 0; row < GRID_SIZE && !found; ++row) {
	            for (int col = 0; col < GRID_SIZE && !found; ++col) {
	               if (tfCells[row][col] == source) {
	                  rowSelected = row;
	                  colSelected = col;
	                  found = true;  // break the inner/outer loops
	               }
	            }
	         }
	 
	        String txt = tfCells[rowSelected][colSelected].getText();
	        int textint = Integer.parseInt(txt);
	        for(int row=0;row<GRID_SIZE;++row) {
	        for(int col=0;col<GRID_SIZE;++col) {
	        if(textint==puzzle[rowSelected][colSelected]) {
	    	  tfCells[rowSelected][colSelected].setBackground(Color.green); 
	    	}else {
	    	  tfCells[rowSelected][colSelected].setBackground(Color.red);
	    	  }
	      }
	    }   
	  }
 }

   private void buildMenu() {
 	   JMenuBar bar;
        bar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGame  = new JMenuItem("New Game");
        JMenuItem reset  = new JMenuItem("Reset Game");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem rules   = new JMenuItem("Sudoku Rules");
        JMenuItem options = new JMenuItem("Options");

        JMenuItem about = new JMenuItem("About");

        fileMenu.add(rules);
        fileMenu.addSeparator();
        fileMenu.add(newGame);
        fileMenu.add(reset);
        fileMenu.add(exit);
        fileMenu.addSeparator();
        fileMenu.add(options);
        fileMenu.addSeparator();
        fileMenu.add(about);

        bar.add(fileMenu);
        setJMenuBar(bar);
        
        newGame.addActionListener((ActionEvent e)->{ //new puzzle
        	
        });
        reset.addActionListener((ActionEvent e)->{ //delete the answer
        	
        });
        exit.addActionListener((ActionEvent e)->{ //go to home page
        	
        });
        rules.addActionListener((ActionEvent e)->{ //go to SudokuRules homepage
        	new SudokuRules();
        });
        options.addActionListener((ActionEvent e)->{ //go to SudokuLevel homepage
        	new SudokuLevel();
        });
        about.addActionListener((ActionEvent e)->{ //go to SudokuAbout homepage
        	new SudokuAbout();
        });
        
    }
   public class StopWatch implements ActionListener{
		JButton startButton = new JButton("PAUSE");
		JButton resetButton = new JButton("RESET");
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
		
		StopWatch(){
			stopwatchPanel = new JPanel(new BorderLayout());
			timeLabelPanel = new JPanel();
			timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
			timeLabel.setFont(new Font("Verdana",Font.PLAIN,20));
			timeLabel.setBorder(BorderFactory.createBevelBorder(1));
			timeLabel.setOpaque(true);
			timeLabel.setHorizontalAlignment(JTextField.CENTER);
			timeButton = new JPanel(new GridLayout(1,2));
			
			startButtonPanel = new JPanel();
			startButton.setFont(new Font("Times New Roman",Font.BOLD,14));
			startButton.setFocusable(false);
			startButton.setBackground(MOCASIN);
			startButton.addActionListener(this);
			
			resetButtonPanel = new JPanel();
			resetButton.setFont(new Font("Times New Roman",Font.BOLD,14));
			resetButton.setFocusable(false);
			resetButton.setBackground(MOCASIN);
			resetButton.addActionListener(this);
			
			timeLabelPanel.add(timeLabel);
			startButtonPanel.add(startButton);
			resetButtonPanel.add(resetButton);
			
			timeButton.add(startButtonPanel);
			timeButton.add(resetButtonPanel);
			
			stopwatchPanel.add(timeLabelPanel,BorderLayout.NORTH);
			stopwatchPanel.add(timeButton,BorderLayout.CENTER);
			
			setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==startButton) {
				if(started==false) {
					started = true;
					startButton.setText("PAUSE");
					start();
				}else {
					started = false;
					startButton.setText("PLAY");
					stop();
				}
			}
			if(e.getSource()==resetButton) {
				started = false;
				startButton.setText("PLAY");
				reset();
			}
			
		}
		void start() {
			timer.start();
		}
		void stop() {
			timer.stop();
		}
		void reset() {
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
	}

}