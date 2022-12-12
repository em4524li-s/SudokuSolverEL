package sudokugame;

public interface SudokuSolver {
	/**
	 * Solves the sudoku with the help of backtracking
	 * 
	 * @return true or false depending if the sudoku can be sloved or not
	 */
	boolean solve();

	/**
	 * Puts digit in the box row, col.
	 * 
	 * @param row   The row
	 * @param col   The column
	 * @param digit The digit to insert in box row, col
	 * @throws IllegalArgumentException if row, col or digit is outside the range
	 *                                  [0..9]
	 */;
	
	void add(int row, int col, int digit);

	/**
	 * Removes digit in the box by replacing it with 0
	 * 
	 * @param row The row
	 * @param col The column
	 */
	void remove(int row, int col);

	/**
	 * Returns the digit in the box
	 * 
	 * @param row The row
	 * @param col The column
	 * @return digit
	 */
	int get(int row, int col);

	/**
	 * Checks that all filled in digits follows the the sudoku rules.
	 * 
	 * @return true or false depending on if the sudoku is valid
	 */
	boolean isValid();

	/**
	 * Clears the sudoku by filling it with 0
	 */
	void clear();

	/**
	 * Fills the grid with the digits in m. The digit 0 represents an empty box.
	 * 
	 * @param m the matrix with the digits to insert
	 * @throws IllegalArgumentException if m has the wrong dimension or contains
	 *                                  values outside the range [0..9]
	 */
	void setMatrix(int[][] m);

	/**
	 * Get the Matrix
	 * @return Matrix
	 */
	int[][] getMatrix();
}