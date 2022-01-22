import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

public class SudokuSolver {

    private final JFrame frame = new JFrame("Sudoku solver");
    Sudoku sudoku;

    public SudokuSolver() {
        frame.getContentPane().add(sudoku);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildMenu();
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public JMenuBar buildMenu() {
 	   JMenuBar bar;
        bar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        bar.add(fileMenu);
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
        return bar;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
      	  @Override
      	  public void run() {
      		  new Sudoku();
      	  }
        });
     }
}
