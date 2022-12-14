package y2022.d12;

import static java.lang.System.out;
import static java.nio.file.Files.readAllLines;
import static java.util.Arrays.stream;
import static y2022.d12.Q1.P.p;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Q1 { 
	enum D { U, L, D, R };
	record P(int r, int c) {
		P move(D d) {
			return switch (d) {
				case U-> r==0 ? null : p(r-1, c);
				case L-> c==0 ? null : p(r, c-1);
				case D-> r==nr-1 ? null: p(r+1, c);
				case R-> c==nc-1 ? null: p(r, c+1);
			};
		}
		List<P> neighbours() {return stream(D.values()).map(d-> move(d)).filter(p-> p != null).toList(); }
		int high() { return high[r][c]; }
		boolean isVisited() { return visited[r][c]; }
		void setVisited() { visited[r][c] = true; }
		void setHigh(int n) { high[r][c] = n; }
		static P p(int r, int c) { return new P(r,c); }
	};
	
	static int nr, nc, high[][];
	static int size = Integer.MAX_VALUE;
	static boolean visited[][];
	static P S, E;
	
	public static void main(String[] args) throws Exception {
		high = readAllLines(new File("src/y2022/d12/q1.txt").toPath()).stream().map(s-> s.chars().toArray()).toArray(int[][]::new);
		nr = high.length;
		nc = high[0].length;
		visited = new boolean[nr][nc];
		
		S = find('S'); S.setHigh('a');
		E = find('E'); E.setHigh('z');
		
		record Path (int size, P p){}; 
		var todo = new LinkedList<Path>(); // { public boolean add(Path path) { path.p.setv(); return super.add(path);}};
		todo.add(new Path(0, S));
		S.setVisited();
		do {
			Path path = todo.removeFirst();
			P c = path.p;
			//show(c);
			if (c.equals(E)) {
				if (path.size < size) size = path.size;
			}	
			else for (var n: c.neighbours()) {
				if (n.isVisited() || n.high() - c.high() > 1) continue;
				todo.add(new Path(path.size+1, n));
				n.setVisited();
			}
			//show(c);
		} while (!todo.isEmpty());
		out.println(size == Integer.MAX_VALUE ? "none" : size);
	}

	private static P find(int c) {
		for (int i=0; i<nr; i+=1) for(int j=0; j<nc; j+=1) if (high[i][j] == c) return p(i,j);
		return null;
	}

	public static void show(P p) {
		for (int i=0; i<nr; i+=1) {
			var highi = high[i];
			for(int j=0; j<nc; j+=1)
				out.print(i==p.r && j==p.c ? '@' : visited[i][j] ? '.' : (char) highi[j]);
			out.println();
		}
		out.println();
	}
}
