import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class SafeBox extends JFrame{
	private JPanel pnlButtons, pnlDisplay;
	private JButton[] btnNumber = new JButton[10];
	private JTextField Text; 
	private JButton buttonC, buttonE;
	private String inStr = "";
	private String inStrCode = "";
	boolean status = true ; //initially open
	private String pin;
	private String input;
	private String inStrInput = "";
	
	public SafeBox() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		pnlDisplay = new JPanel(new FlowLayout());
		pnlButtons = new JPanel(new GridLayout(4,3));
		
		Text = new JTextField(10);
		Text.setText("OPEN");
		Text.setHorizontalAlignment(SwingConstants.RIGHT);
		Text.setMaximumSize(Text.getPreferredSize());
		Text.setEditable(false);
		pnlDisplay.add(Text);
		Text.setBorder(null);
		
		for(int i=0;i<btnNumber.length;++i) {
			btnNumber[i] = new JButton(i+"");
			btnNumber[i].addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
						if(status==true) {
							inStr = inStr + e.getActionCommand();
							Text.setText(inStr);
							pin = inStr;
						}else if(status==false){
							inStrInput = inStrInput + e.getActionCommand();
							Text.setText(inStrInput);
							input = inStrInput;
						}
					// TODO Auto-generated method stub
				}
			});
		}
		for(int i=1;i<btnNumber.length;++i) {
			pnlButtons.add(btnNumber[i]);
		}
		
		buttonC = new JButton("C");
		buttonC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inStr = "";
				inStrInput = "";
				Text.setText(inStr);
			}
		});
		buttonE = new JButton("E");
		buttonE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					inStrCode = "CODE SET";
					Text.setText(inStrCode);
					status = false;
					if(input.equals(pin)) {
						open();
						pin = "";
					}else {
						lock();
					}
			}
			public void open() {
				status = true;
				Text.setText("OPEN");
				inStr="";
			}
			public void lock() {
				status = false;
				Text.setText("WRONG CODE");
				inStrInput = "";
			}
		});
		pnlButtons.add(buttonC);
		pnlButtons.add(btnNumber[0]);
		pnlButtons.add(buttonE);
		
		cp.add(pnlDisplay, BorderLayout.NORTH);
		cp.add(pnlButtons, BorderLayout.CENTER);
		
		
		setTitle("Safe Box");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,400);
		setVisible(true);
		
		
	}
	public static void main(String[]args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				new SafeBox();
			}
		});
	}

}

