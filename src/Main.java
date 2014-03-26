import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.print.attribute.AttributeSet;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;


public class Main extends JFrame {
	
	final WordObj word = new WordObj();
	
	private JTextField textField;
	private JTextField guessField;
	private JTextField guessLeftField;
	private JTextField wrongGuessField;
	private boolean gameRunning; // a variable that changes to false once the game has been won / lost
	
	public Main() {

		makeMenu();
		makeBoxes();

	}
	
	// check if the input is suitable to use
	public boolean inputIsSuitable(String s) {
		boolean suitable = true;
		
		if(s.length() != 1) { // check if single letter
			System.out.println("NOT A SINGLE CHARACTER");
			suitable = false;
		}
		
		char c = s.charAt(0);
		
		if(!Character.isLetter(c)) { // check if input is a letter
			suitable = false;
			System.out.println("NOT LETTER");
		}
		
		return suitable;
	}
	
	public static void main(String[] args) {
		JFrame frame = new Main();
		frame.setTitle("Kaspers Badass Hangman");
		
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(480,360);
		frame.setVisible(true);

	}
	
	public void makeMenu() {

		JMenu file = new JMenu("Game");
		
		JMenuItem newGameItem = new JMenuItem("New Game");
		file.add(newGameItem);

		JMenuItem exitItem = new JMenuItem("Exit");
		file.add(exitItem);

		JMenu options = new JMenu("Options");
		final JRadioButtonMenuItem rbMenuItemEasy;
		final JRadioButtonMenuItem rbMenuItemMedium;
		JRadioButtonMenuItem rbMenuItemHard;
		ButtonGroup group = new ButtonGroup();
		rbMenuItemEasy = new JRadioButtonMenuItem("Easy");
		rbMenuItemMedium = new JRadioButtonMenuItem("Medium");
		rbMenuItemHard = new JRadioButtonMenuItem("Hard");
		rbMenuItemEasy.setSelected(true);
		group.add(rbMenuItemEasy);
		group.add(rbMenuItemMedium);
		group.add(rbMenuItemHard);
		options.add(rbMenuItemEasy);
		options.add(rbMenuItemMedium);
		options.add(rbMenuItemHard);
		
		JMenu help = new JMenu("Help");
		JMenuItem rulesButton = new JMenuItem("Game Rules");
		help.add(rulesButton);
		
		JMenuItem aboutButton = new JMenuItem("About");
		help.add(aboutButton);
		
		newGameItem.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						if(rbMenuItemEasy.isSelected()) {
							System.out.println("New Easy");
							word.resetWord(10);
						} else if(rbMenuItemMedium.isSelected()) {
							System.out.println("New Medium");
							word.resetWord(7);
						} else {
							System.out.println("New Hard");
							word.resetWord(5);
						}
						textField.setText(word.getTextString());
						guessLeftField.setText(word.getGuessLeftString());
						wrongGuessField.setText(word.getWrongTextString());
					}
				}
		);

		exitItem.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					System.exit(0);
				}
			}
		);	
		rulesButton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						JOptionPane.showMessageDialog(null, "Guess a letter to reveal the word?", "Rules", JOptionPane.INFORMATION_MESSAGE);				
					}
				}
		);		
		aboutButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					JOptionPane.showMessageDialog(null, "Hangman\nBy Kasper Wilkosz\nv1.0", "About", JOptionPane.INFORMATION_MESSAGE);				
				}
			}
		);		
		
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		bar.add(file);
		bar.add(options);
		bar.add(help);		
	}
	
	public void makeBoxes() {
		
		Color darkGreen = new Color(0, 102, 0);	
		Color lightGreen = new Color(153, 255, 51);
		Color lightPink = new Color(255, 153, 255);	
		Color lightBlue = new Color(153, 255, 204);		
		Color lightYellow = new Color(255, 255, 102);	
		Color lightGrey = new Color(255, 114, 0);	
		
		textField = new JTextField(15);
		guessField = new JTextField(1);
		guessLeftField = new JTextField(2);
		wrongGuessField = new JTextField(15);
		gameRunning = true;
		
		JButton jbtGuess = new JButton("GUESS");
		jbtGuess.setPreferredSize(new Dimension(82, 128));
		
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		JPanel topPanel = new JPanel();		
		JPanel bottomPanel = new JPanel();	
		JPanel centerPanel = new JPanel();
		
		topPanel.setPreferredSize(new Dimension(480, 80));
		bottomPanel.setPreferredSize(new Dimension(480, 80));
		leftPanel.setPreferredSize(new Dimension(98, 200));
		rightPanel.setPreferredSize(new Dimension(98, 200));
		
		Box iconPanel = new Box(BoxLayout.Y_AXIS);
		
		JLabel rightGuessLabel = new JLabel();
		rightGuessLabel.setText("Correct guesses");		
		
		JLabel guessLabel = new JLabel();
		guessLabel.setText("          Guess Letter");	

		JLabel guessLeftLabel = new JLabel();
		guessLeftLabel.setText("<html><body>Guesses<br />Left<br /></body></html>");		
		
		JLabel wrongGuessLabel = new JLabel();
		wrongGuessLabel.setText("Wrong guesses");

		topPanel.setBackground(lightGreen);		
		leftPanel.setBackground(lightBlue);
		rightPanel.setBackground(lightYellow);		
		bottomPanel.setBackground(lightPink);
		centerPanel.setBackground(lightGrey);
		
		Font font1 = new Font("SansSerif", Font.BOLD, 28);
		Font guessFont = new Font("SansSerif", Font.BOLD, 80);		
		
		textField.setFont(font1);
		textField.setForeground(darkGreen);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setEditable(false);
		
		textField.setText(word.getTextString());
		
		guessLeftField.setEditable(false);
		
		guessField.setFont(guessFont);
		guessField.setHorizontalAlignment(JTextField.CENTER);
		guessLeftField.setFont(font1);
		
		wrongGuessField.setFont(font1);
		wrongGuessField.setHorizontalAlignment(JTextField.CENTER);
		wrongGuessField.setForeground(Color.red);
		wrongGuessField.setEditable(false);
		
		guessLeftField.setText(word.getGuessLeftString());
		guessLeftField.setHorizontalAlignment(JTextField.CENTER);
		
		topPanel.add(rightGuessLabel);
		topPanel.add(textField);
		leftPanel.add(guessLeftLabel);
		leftPanel.add(guessLeftField);	
		
		iconPanel.add(guessLabel);
		iconPanel.add(guessField);
		centerPanel.add(iconPanel);
		
		rightPanel.add(jbtGuess);
		bottomPanel.add(wrongGuessLabel);
		bottomPanel.add(wrongGuessField);
	
		add(topPanel, BorderLayout.NORTH);
		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.EAST);
		add(bottomPanel, BorderLayout.SOUTH);	
		add(centerPanel, BorderLayout.CENTER);		
		
		jbtGuess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameRunning = word.checkGameState();
				if(guessField.getText().length() != 0 && inputIsSuitable(guessField.getText()) && gameRunning) { // make sure input is not empty
					word.checkGuess(guessField.getText());
					
					textField.setText(word.getTextString());
					guessLeftField.setText(word.getGuessLeftString());
					wrongGuessField.setText(word.getWrongTextString());
				}
			guessField.setText("");
			}
		});		
	}
}
