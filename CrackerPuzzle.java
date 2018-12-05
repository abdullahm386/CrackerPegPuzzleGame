// Abdullah Mumtaz
// CSCE 4430.001
// Java implementation that uses BFS to solve cracker barrel peg puzzle


//for ArrayList, HashMap, and LinkedList
import java.util.*;

public class CrackerPuzzle {

	final public static int SIZE = 5;
	final public static boolean DEBUG = false;



	//Used to solve
	public static HashMap<Integer,Integer> distances;

    //Mark starting hole
	final public static int HOLE_R = 4;
    final public static int HOLE_C = 2;
    
	//Possible moves
	final public static int[] DX = {-1,-1,0,0,1,1};
	final public static int[] DY = {-1,0,-1,1,0,1};

    
	public static void main(String[] args) {

		
		int begin = initBoard(HOLE_R, HOLE_C);

		//Begining position
		distances = new HashMap<Integer,Integer>();
		distances.put(begin, 0);

		
		LinkedList<Integer> q = new LinkedList<Integer>();
		q.offer(begin);

		
		while (q.size() > 0) {

			// Get next move.
			int cur = q.poll();
			int current_distance = distances.get(cur);

			ArrayList<Integer> nextList = next_position(cur);
			
			for (int i=0; i<nextList.size(); i++) {
				if (!distances.containsKey(nextList.get(i))) {
					distances.put(nextList.get(i), current_distance+1);
					q.offer(nextList.get(i));
				}
			}
			
			if (nextList.size() == 0) print(cur);
		}

	}

	//Initliazes the board
	public static int initBoard(int holeR, int holeC) {

		
		int position = 0;
		for (int i=0; i<SIZE; i++)
			for (int j=0; j<=i; j++)
				position = position | (1<<(SIZE*i+j));

		
		return position - (1<<(SIZE*holeR+holeC));
	}

	//Determines possible board positions
	public static ArrayList<Integer> next_position(int position) {

		ArrayList<Integer> pos = new ArrayList<Integer>();

		
		for (int r =0; r<SIZE; r++) {
			for (int c=0; c<=SIZE; c++) {

				
				for (int dir=0; dir<DX.length; dir++) {

					
					if (!check_bound(r+2*DX[dir], c+2*DY[dir])) continue;

					
					if (on(position, SIZE*r+c) && on(position, SIZE*(r+DX[dir]) + c + DY[dir]) && !on(position, SIZE*(r+2*DX[dir]) + c + 2*DY[dir])) {
						int newpos = apply(position, dir, r, c);
						pos.add(newpos);
					}
				}
			}
		}
		return pos;
	}

	//Prints the board
	public static void print(int position) {

		for (int i=0; i<SIZE; i++) {

			for (int j=0; j<SIZE-1-i; j++) System.out.print(" ");

			for (int j=0; j<=i; j++) {
				if (on(position, SIZE*i+j)) System.out.print("X ");
				else					System.out.print("0 ");
			}
			System.out.println();
		}
		System.out.println();
	}

	
	public static int apply(int position, int dir, int r, int c) {
		
		int begin = SIZE*r + c;
		int mid = SIZE*(r+DX[dir]) + c + DY[dir];
		int end = SIZE*(r+2*DX[dir]) + c + 2*DY[dir];

		return position - (1<<begin) - (1<<mid) + (1<<end);
	}

	
	public static boolean on(int position, int bit) {
		return (position & (1<<bit)) != 0;
	}


	public static boolean check_bound(int myr, int myc) {
		return myr >= 0 && myr < SIZE && myc >= 0 && myc <= myr;
	}
}