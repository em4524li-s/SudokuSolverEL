package sudokugame;

public class SudokuPuzzle implements SudokuSolver {
	protected int[][] Matrix;

	public SudokuPuzzle() {
		this.Matrix = new int[9][9];
		setMatrix(Matrix);
	}

	public SudokuPuzzle(SudokuPuzzle puzzle) {
		this.Matrix = new int[9][9];
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				Matrix[r][c] = puzzle.Matrix[r][c];
			}
		}
	}

	/**
	 * Solves the sudoku with the help of backtracking
	 * 
	 * @return true or false depending if the sudoku can be sloved or not
	 */
	public boolean solve() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (Matrix[row][col] == 0) {
					// try possible numbers
					for (int number = 1; number < 10; number++) {
						if (isValid()==true) {
							// number valid. it respects sudoku constraints
							Matrix[row][col] = number;
							if (solve()==true) { // start backtracking recursively
								return true;
							} else { // if not a solution, empty the cell and continue
								remove(row, col);
							}
						}
					}

					return false;
				}
				if (get(8, 8) == 1 && isCellValid(8, 8) == false) { // to fix that (8,8) always is 1 even if it's not
																	// valid
					for (int number = 2; number <= 9; number++) {
						Matrix[8][8] = number;
						if (isCellValid(8, 8)) {
							return true;
						}
					}
				}
			}
		}
		return true; // sudoku solved
	}

	/**
	 * Puts digit in the box row, col.
	 * 
	 * @param row   The row
	 * @param col   The column
	 * @param digit The digit to insert in box row, col
	 * @throws IllegalArgumentException if row, col or digit is outside the range
	 *                                  [0..9]
	 */

	public void add(int row, int col, int digit) {
		if (digit < 0 || digit > 9) {
			throw new IllegalArgumentException("Digit can not be outside the range [0..9]");
		}
		Matrix[row][col] = digit;
	}

	/**
	 * Removes digit in the box by replacing it with 0
	 * 
	 * @param row The row
	 * @param col The column
	 */
	public void remove(int row, int col) {
		Matrix[row][col] = 0;
	}

	/**
	 * Returns the digit in the box
	 * 
	 * @param row The row
	 * @param col The column
	 * @return digit
	 */
	public int get(int row, int col) {
		int digit = Matrix[row][col];
		return digit;
	}

	/**
	 * Checks that all filled in digits follows the the sudoku rules.
	 * 
	 * @return true or false depending on if the sudoku is valid
	 */
	public boolean isValid() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (get(row, col) != 0) { // search for filled cell
					if (isCellValid(row, col) == false) {
						return false;
					}
				}
			}

		}

		return true;
	}

	/**
	 * Checks one square follows the the sudoku rules.
	 * 
	 * @param row The row
	 * @param col The column
	 * @return true or false depending on if the cell is valid or not
	 */
	private boolean isCellValid(int row, int col) {
		int digit = get(row, col);
		for (int a = 0; a < 9; a++) {
			if (get(a, col) == digit && !(a == row)) { // same digit in a row
				return false;
			}
			if (get(row, a) == digit && !(a == col)) { // same digit in a col
				return false;
			}
		}
		int startingRow = (row / 3) * 3;
		int startingCol = (col / 3) * 3;

		for (int b = startingRow; b <= startingRow + 2; b++) {
			for (int d = startingCol; d <= startingCol + 2; d++) {
				if (digit == get(b, d) && !(d == col) && !(b == row)) { // same box
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Clears the sudoku by filling it with 0
	 */
	public void clear() {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				Matrix[r][c] = 0;
			}
		}

	}

	/**
	 * Fills the grid with the digits in m. The digit 0 represents an empty box.
	 * 
	 * @param m the matrix with the digits to insert
	 * @throws IllegalArgumentException if m has the wrong dimension or contains
	 *                                  values outside the range [0..9]
	 */
	public void setMatrix(int[][] m) {
		if(m.length != 9 || m[0].length !=9) {
			throw new IllegalArgumentException("Wrong dimension");
		}
		for(int row=0; row<9;row++) {
			for(int col=0; col<9;col++) {
				if(m[row][col]>10 || m[row][col]<0) {
					throw new IllegalArgumentException("can't contain values outside the range [0..9]");
				}
			}
		}
		
		Matrix = m;
	}

	/**
	 * Get the Matrix
	 * @return Matrix
	 */
	public int[][] getMatrix() {

		return Matrix;
	}

}
