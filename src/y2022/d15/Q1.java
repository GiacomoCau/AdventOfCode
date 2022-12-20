package y2022.d15;

import static java.lang.Character.valueOf;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.abs;
import static java.lang.System.out;
import static java.nio.file.Files.readAllLines;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;
import static y2022.d15.Q1.P.p;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class Q1 {

	static class P {
		int r; int c;
		private P(int r, int c) { this.r=r; this.c=c; } 
		@Override public String toString() { return "{" + r + " " + c + "}"; }
		@Override public boolean equals(Object obj) { return obj instanceof P p && p.c == c && p.r == r; }
		@Override public int hashCode() { return Objects.hashCode(r) * Objects.hashCode(c); }
		@Override protected P clone() throws CloneNotSupportedException { return p(r, c); }
		static P p() { return p(0, 0); };
		static P p(int[] a) { return p(a[1],a[0]); }
		static P p(int r, int c) { return new P(r,c); }
	};
	
	static class Grid extends HashMap<P,Character> {
		private static final long serialVersionUID = 1L;
		int minR=MAX_VALUE, maxR=MIN_VALUE, minC=MAX_VALUE, maxC=MIN_VALUE;
		@Override public Character put(P p, Character ch) {
			if (p.r < minR) minR = p.r;
			if (p.r > maxR) maxR = p.r;
			if (p.c < minC) minC = p.c;
			if (p.c > maxC) maxC = p.c;
			return super.put(p, ch);
		}
		void area(P p, int md) {
			for (int r=p.r-md; r<=p.r+md; r+=1) {
				areaR(p, md, r);
			}
		}
		private void areaR(P p, int md, int r) {
			var d = abs(abs(p.r-r)-md);
			for (int c=p.c-d; c<=p.c+d; c+=1) {
				var n = p(r,c);
				//putIfAbsent(p, '#');
				if (!containsKey(n)) put(n, '#');
			}
		}
		public String toString() {
			return range(minR, maxR+1)
			.mapToObj(r-> range(minC, maxC+1).mapToObj(c-> getOrDefault(p(r,c), '.').toString()).collect(joining()))
			.collect(joining("\n"));
		}
	}; 

	static boolean map = true;
	
	public static void main(String[] args) throws Exception {
		var r = 10; //2_000_000;
		var grid = new Grid();
		for (var line: readAllLines(new File("src/y2022/d15/p1.txt").toPath())) {
			var SB = stream(line.substring(1+line.indexOf("=")).split("\\D+=")).mapToInt(Integer::parseInt).toArray();
			P S = p(SB[1], SB[0]), B = p(SB[3], SB[2]);
			var md = abs(S.r-B.r)+abs(S.c-B.c);
			if (map) {
				/*if (abs(S.r-r) <= md)*/ grid.area(S, md);
				grid.put(S, 'S');
				grid.put(B, 'B');
			}
			else {
			//if (S.r-md <= r && S.r+md >= r)	grid.areaR(S, md, r);
				if (abs(S.r-r) <= md) grid.areaR(S, md, r);
				if (S.r==r)	grid.put(S, 'S');
				if (B.r==r) grid.put(B, 'B');
			}
		}
		out.println(grid);
		out.println(grid.minR + " " + grid.minC);
		//out.println(range(grid.minC, grid.maxC+1).filter(c-> grid.getOrDefault(p(r,c), '.') == '#').count());
		out.println(range(grid.minC, grid.maxC+1).filter(c-> valueOf('#').equals(grid.get(p(r,c)))).count());
	}
}
