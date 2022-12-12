package sudokugame;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class SudokuFrame extends JFrame {
	private JPanel buttonPanel;
	private SudokuPanel Sudokupanel;

	public SudokuFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku");
		this.setMinimumSize(new Dimension(800, 600));

		JPanel windowPanel = new JPanel();
		windowPanel.setLayout(new FlowLayout());
		windowPanel.setPreferredSize(new Dimension(800, 600));

		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(150, 150));

		Sudokupanel = new SudokuPanel();

		windowPanel.add(Sudokupanel);
		windowPanel.add(buttonPanel);
		this.add(windowPanel);

		rebuildInterface(26);

	}

	/**
	 * Rebuilds the Interface
	 * 
	 * @param fontSize
	 */
	public void rebuildInterface(int fontSize) {
		SudokuSolver generatedPuzzle = new SudokuPuzzle();
		Sudokupanel.newSudokuPuzzle(generatedPuzzle);
		Sudokupanel.setFontSize(fontSize);
		buttonPanel.removeAll();

		JButton num = new JButton("add number");
		num.addKeyListener(Sudokupanel.new NumKeyListener());
		buttonPanel.add(num);// måste fixas så man ej behöver ha add number intryck

		JButton solve = new JButton("solve");
		solve.addActionListener(Sudokupanel.new SolveActionListener());
		buttonPanel.add(solve);

		JButton clear = new JButton("clear");
		clear.addActionListener(Sudokupanel.new ClearActionListener());
		buttonPanel.add(clear);

		Sudokupanel.repaint();
		buttonPanel.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SudokuFrame frame = new SudokuFrame();
				frame.setVisible(true);
			}
		});
	}
}
