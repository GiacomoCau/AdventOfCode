package y2022.d14;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.System.out;
import static java.nio.file.Files.readAllLines;
import static java.util.Arrays.stream;
import static y2022.d14.Q2.P.p;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class Q2 {

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
			if (c != p.c) c += c < p.c ? +1 : -1;
			if (r != p.r) r += r < p.r ? +1 : -1;
			return this;
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
		int minR=MAX_VALUE, maxR=MIN_VALUE, minC=MAX_VALUE, maxC=MIN_VALUE;
		@Override public Character put(P p, Character c) {
			if (p.r < minR) minR = p.r;
			if (p.r > maxR) maxR = p.r;
			if (p.c < minC) minC = p.c;
			if (p.c > maxC) maxC = p.c;
			return super.put(p, c);
		}
		void println() {
			for (int c=minC-1; c<=maxC+1; c+=1) out.print(c==500 ? '+' : '.'); 
			out.println();
			for (int r=1; r<=maxR+1; r+=1) {
				for (int c=minC-1; c<=maxC+1; c+=1)
					out.print(getOrDefault(p(r,c), '.'));
				out.println();
			}
			out.println();
		}
	}; 
	
	static Cave cave = new Cave();
	
	public static void main(String[] args) throws Exception {
		for (var line: readAllLines(new File("src/y2022/d14/q1.txt").toPath())) {
			//out.println(line);
			var ps = stream(line.split(" -> ")).map(p-> stream(p.split(",")).mapToInt(Integer::parseInt).toArray()).map(P::p).toArray(P[]::new);
			//for (var p: ps) out.print(p); out.println();
			var p = ps[0]; cave.put(p.clone(), '#'); 
			for (int i=1; i<ps.length; i+=1) {
				var psi = ps[i]; do {				
					p = p.moveTo(psi);
					cave.put(p.clone(), '#');
				} while (!p.equals(psi));
			}
		}
		for (int maxR=cave.maxR+2, c=cave.minC-1, maxC=cave.maxC+1; c<=maxC; c+=1) cave.put(p(maxR, c), '#');
		//cave.println();
		int sand = 0;
		loop: for(;;) {
			P p = p(0,500);
			for (P n=null; p.r < cave.maxR; p = n) {
				n = p.moveDn();
				if (n != p) continue;
				cave.put(p.clone(), 'o');
				//cave.println();
				sand += 1;
				if (p.r==0 && p.c==500) break loop;
				continue loop;
			}
			cave.put(p.clone(), '#');
		}
		//cave.println();
		out.println(sand);
	}
}
