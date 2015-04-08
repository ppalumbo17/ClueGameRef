package GUI;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import clueGame.Board;
import clueGame.ClueGame;
import clueGame.HumanPlayer;
import clueGame.Player;

public class ControlPanelGUI extends JPanel implements ActionListener{

	JTextField whoseTurn,dieRoll,guessIn,guessOut;
	Board board;
	JButton nextPlayer, accusation;
	ArrayList<Player> players;
	protected Player thisPlayer;
	private int playerTurn;
	public ControlPanelGUI(Board b){
		board=b;
		players=ClueGame.getGamePlayers();
		thisPlayer=players.get(0);
		playerTurn=-1;
		setPreferredSize(new Dimension(50, 150));
		createLayout();
		setVisible(true);
	}
	public void createLayout(){
		//JPanel panel = new JPanel();
		setLayout(new GridLayout(2,3));
		setBorder(new TitledBorder(new EtchedBorder(), "Control Panel"));
		setSize(new Dimension(220, 200));
		add(createNamesPanel());
		add(createButtonsPanel());
		add(createOthersPanel());
		//return panel;
	}
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
		nextPlayer.addActionListener(this);
		panel.add(nextPlayer);
		accusation = new JButton("Make an accusation");
		accusation.addActionListener(this);
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
		dieRoll = new JTextField(5);
		dieRoll.setEditable(false);
		panel.add(die);
		panel.add(dieRoll);
		JLabel guess = new JLabel("Guess");
		guessIn = new JTextField(5);
		guessIn.setEditable(false);
		panel.add(guess);
		panel.add(guessIn);
		JLabel guessResult = new JLabel("Guess Result");
		guessOut = new JTextField(5);
		guessOut.setEditable(false);
		panel.add(guessResult);
		panel.add(guessOut);
		return panel;
		
	}
	/*public static void main(String[] args) {
		ControlPanelGUI gui = new ControlPanelGUI();
		gui.setVisible(true);
		

	}*/
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(nextPlayer)){
			if(!board.checkTurn()){
				JOptionPane.showMessageDialog(null, "Please complete your turn.","Turn not over",JOptionPane.ERROR_MESSAGE);
			}
			else{
				playerTurn++;
				if(playerTurn>=players.size()){
					playerTurn=0;
				}
				thisPlayer=players.get(playerTurn);
				whoseTurn.setText(thisPlayer.getName());
				int diceRoll=(int)(Math.random()*6+1);
				dieRoll.setText(""+diceRoll);
				if(thisPlayer instanceof HumanPlayer){
					board.highlightTargets(thisPlayer.getLocation(),diceRoll);
				}
				else{
					board.takeTurn(thisPlayer,diceRoll);
					guessIn.setText(board.getCurrentSuggestion().toString());
					guessOut.setText(board.getSuggestionAnswer());
				}
			}
		}
		if(e.getSource().equals(accusation)){
			if(!board.checkTurn()){
				JOptionPane.showMessageDialog(null, "Please complete your turn.","Turn not over",JOptionPane.ERROR_MESSAGE);
				playerTurn++;
				if(playerTurn>=players.size()){
					playerTurn=0;
				}
				thisPlayer=players.get(playerTurn);
				whoseTurn.setText(thisPlayer.getName());
			}
			else{
				JOptionPane.showMessageDialog(null, "IT IS NOT YOUR TURN","Not your turn",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
