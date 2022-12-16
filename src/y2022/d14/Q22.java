package y2022.d14;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.signum;
import static java.lang.System.out;
import static java.nio.file.Files.readAllLines;
import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;
import static y2022.d14.Q22.P.p;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

public class Q22 {

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
		public P moveTo(P p) {
			return p(r + (int) signum(p.r - r), c + (int) signum(p.c - c));
		}
		public P moveDn() {
			var p = p(r+1, c);
			if (!cave.containsKey(p)) return p;
			p.c -= 1; if (!cave.containsKey(p)) return p;
			p.c += 2; if (!cave.containsKey(p)) return p;	
			return this;
		}
	};
	
	static class Cave extends HashMap<P,Character> {
		private static final long serialVersionUID = 1L;
		int maxR=MIN_VALUE, minC=MAX_VALUE, maxC=MIN_VALUE, sand=0;
		@Override public Character put(P p, Character c) {
			if (p.r > maxR) maxR = p.r;
			if (p.c < minC) minC = p.c;
			if (p.c > maxC) maxC = p.c;
			if (c == 'o') sand += 1;
			return super.put(p, c);
		}
	}; 
	
	static Cave cave = new Cave();
	
	public static void main(String[] args) throws Exception {
		for (var line: readAllLines(new File("src/y2022/d14/q1.txt").toPath())) {
			var ps = stream(line.split(" -> ")).map(p-> stream(p.split(",")).mapToInt(Integer::parseInt).toArray()).map(P::p).toArray(P[]::new);
    		range(0, ps.length-1).mapToObj(i-> fromTo(ps[i], ps[i+1])).flatMap(s-> s).forEach(p-> cave.put(p, '#'));
		}
		for (int maxR=cave.maxR+2, c=cave.minC-1, maxC=cave.maxC+1; c<=maxC; c+=1) cave.put(p(maxR, c), '#');
		P p; do {
			p = p(0, 500); for (P n; p != (n = p.moveDn()) && p.r < cave.maxR; p = n);
			cave.put(p, p.r == cave.maxR ? '#' : 'o');
		} while (p.r != 0 || p.c != 500);
		out.println(cave.sand);
	}
	
	static Stream<P> fromTo(P p1, P p2) {
		var stream = of(p1); return p1.equals(p2) ? stream : concat(stream, fromTo(p1.moveTo(p2), p2));
    }
}
