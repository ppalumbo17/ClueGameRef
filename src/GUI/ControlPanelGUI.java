package GUI;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import clueGame.ClueGame;

public class ControlPanelGUI extends JFrame{

	JTextField whoseTurn;
	JButton nextPlayer, accusation;
	public ControlPanelGUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Control Panel");
		setPreferredSize(new Dimension(100, 300));
		setResizable(false);
		setBackground(Color.BLACK);
		createLayout();
	}
	public JPanel createLayout(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 3));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Control Panel"));
		panel.setSize(new Dimension(220, 200));
		panel.add(createNamesPanel());
		panel.add(createButtonsPanel());
		panel.add(createOthersPanel());
		return panel;
	}
	/*public JPanel createLayout(){
		JPanel final = new JPanel();
		JPanel names = createNamesPanel();
		final.add(names, BorderLayout.WEST);
		JPanel buttons = createButtonsPanel();
		final.add(buttons, BorderLayout.EAST);
		JPanel die = createOthersPanel();
		final.add(die, BorderLayout.SOUTH);
		
		return final;
		return null;
		
	}*/
	//Ask's Whose turn
	private JPanel createNamesPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		JLabel turn = new JLabel("Whose turn?");
		whoseTurn = new JTextField(8);
		panel.add(turn);
		panel.add(whoseTurn, BorderLayout.WEST);
		//panel.setSize(new Dimension(200, 100));
		return panel;
	}
	//Buttons Next Player and make an Accusation
	private JPanel createButtonsPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		nextPlayer = new JButton("Next Player");
		panel.add(nextPlayer);
		accusation = new JButton("Make an accusation");
		//Button to Color Black and Text to White
		nextPlayer.setBackground(Color.GRAY);
		nextPlayer.setForeground(Color.WHITE);
		//This Gets rid of that on hover border
		nextPlayer.setBorderPainted(false);
		// Tried adjusting size seems like it likes the preferred size
		//nextPlayer.setSize(new Dimension(200,50));
		accusation.setBackground(Color.GRAY);
		accusation.setForeground(Color.WHITE);
		//accusation.setSize(new Dimension(200,50));
		panel.add(accusation);
		return panel;
	}
	
	private JPanel createOthersPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		JLabel die = new JLabel("Die Roll");
		JTextField dieRoll = new JTextField(5);
		dieRoll.setEditable(false);
		panel.add(die);
		panel.add(dieRoll);
		JLabel guess = new JLabel("Guess");
		JTextField guessIn = new JTextField(5);
		guessIn.setEditable(false);
		panel.add(guess);
		panel.add(guessIn);
		JLabel guessResult = new JLabel("Guess Result");
		JTextField guessOut = new JTextField(5);
		guessOut.setEditable(false);
		panel.add(guessResult);
		panel.add(guessOut);
		return panel;
		
	}
	public static void main(String[] args) {
		ControlPanelGUI gui = new ControlPanelGUI();
		gui.setVisible(true);
		

	}
}
