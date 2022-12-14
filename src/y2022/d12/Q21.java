package y2022.d12;

import static java.lang.System.out;
import static java.nio.file.Files.readAllLines;
import static java.util.Arrays.stream;
import static y2022.d12.Q21.P.p;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Q21 { 
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
		List<P> neighbours() {return stream(D.values()).map(d-> move(d)).filter(p-> p!=null).toList(); }
		int high() { var v=high[r][c]; return switch (v) { case 'S'->'a'; case 'E'->'z'; default->v; }; }
		boolean isVisited() { return visited[r][c]; }
		void setVisited() { visited[r][c] = true; }
		static P p(int r, int c) { return new P(r,c); }
	};
	
	static int nr, nc, high[][];
	static boolean visited[][];
	static P E;
	
	public static void main(String[] args) throws Exception {
		high = readAllLines(new File("src/y2022/d12/q1.txt").toPath()).stream().map(s-> s.chars().toArray()).toArray(int[][]::new);
		nr = high.length;
		nc = high[0].length;
		E = find('E').findFirst().get(); 
		//out.println(path(find('S').findFirst().get()));
		out.println(find('a').mapToInt(Q21::path).filter(r-> r != -1).min().getAsInt());
	}

	private static int path(P s) {
		visited = new boolean[nr][nc];
		record Path (int size, P p){}; 
		var todo = new LinkedList<Path>();
		todo.add(new Path(0, s)); s.setVisited();
		do if (todo.removeFirst() instanceof Path(int size, P c) path) {
			//show(c);
			if (c.equals(E)) return size;
			for (var n: c.neighbours()) {
				if (n.isVisited() || n.high() - c.high() > 1) continue;
				todo.add(new Path(size+1, n)); n.setVisited();
			}
			//show(c);
		} while (!todo.isEmpty());
		return -1;
	}

	private static Stream<P> find(int c) {
		var l = new ArrayList<P>();
		for (int i=0; i<nr; i+=1) for(int j=0; j<nc; j+=1) if (high[i][j] == c) l.add(p(i,j));
		return l.stream();
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
