package sudokugame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Sudokutesting {
	private SudokuSolver puzzle;
	private SudokuSolver puzzle2;

	@BeforeEach

	void setUp() throws Exception {
		puzzle = new SudokuPuzzle();
		puzzle2 = new SudokuPuzzle();
		int[][] Board = new int[][] { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
				{ 1, 0, 2, 5, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
				{ 6, 0, 0, 0, 0, 0, 0, 2, 8 }, { 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 3, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 4, 0, 0 }, };
		puzzle2.setMatrix(Board);
	}

	@AfterEach
	void tearDown() throws Exception {
		this.puzzle = null;
	}

	// testing solve empty Sudoku
	@Test
	public void emptysolve() {
		puzzle.clear();
		assertTrue(puzzle.solve());
	}

	// testing solve with unsolvable sudokou
	@Test
	public void unsolvablesolve() {
		puzzle.add(0, 0, 5);
		puzzle.add(0, 1, 5);
		assertFalse(puzzle.solve()); // check that sudoku is unsolvable if the same digit is in the same row
		puzzle.clear();
		puzzle.add(0, 0, 5);
		puzzle.add(5, 0, 5);
		assertFalse(puzzle.solve()); // check that sudoku is unsolvable if the same digit is in the same colum
		puzzle.clear();
		puzzle.add(0, 0, 5);
		puzzle.add(2, 2, 5);
		assertFalse(puzzle.solve()); // check that sudoku is unsolvable if the same digit is in the same box
	}

	// testing solve with unsolvable sudokou and then remove so it can be solved
	@Test
	public void fixedsolve() {
		puzzle.add(0, 0, 1);
		puzzle.add(0, 1, 2);
		puzzle.add(0, 2, 3);
		puzzle.add(1, 0, 4);
		puzzle.add(1, 1, 5);
		puzzle.add(1, 2, 6);
		puzzle.add(2, 3, 7);
		assertFalse(puzzle.solve()); // check that sudoku is unsolvable
		puzzle.remove(2, 3);
		assertTrue(puzzle.solve()); // check that sudoku is now solvable
		assertTrue(puzzle.isValid()); // check that it's valid
	}

	// testing unsolvable sudoku + clear = solvable
	@Test
	public void testclear() {
		puzzle.add(0, 0, 5);
		puzzle.add(0, 8, 5);
		assertFalse(puzzle.solve()); // check that sudoku is unsolvable
		puzzle.clear();
		assertTrue(puzzle.solve()); // check that sudoku is now solvable
		assertTrue(puzzle.isValid()); // check that it's valid
	}

	// testing solvable sudoku
	public void solvablesolve() {
		assertTrue(puzzle2.solve()); // check that sudoku is now solvable
		assertTrue(puzzle2.isValid()); // check that it's valid
	}

	// testing get
	@Test
	public void testget() {
		;
		assertEquals(0, puzzle2.get(8, 8), "checking[8,8]");
		assertEquals(2, puzzle2.get(2, 2), "checking[2,2]");
		assertEquals(5, puzzle2.get(4, 1), "checking[4,1]");
	}

//testing add
	@Test
	public void testadd() {
		puzzle.add(8, 8, 7);
		assertEquals(7, puzzle.get(8, 8));
	}

//testing remove
	@Test
	public void testremove() {
		puzzle.add(3, 6, 7);
		puzzle.remove(3, 6);
		assertEquals(0, puzzle.get(3, 6), "removing 2 [3,6]");
	}

//testing isvalid
	@Test
	public void testisvalid() {
		assertTrue(puzzle2.isValid());
		puzzle2.add(0, 0, 8);
		assertFalse(puzzle2.isValid());
	}

}
