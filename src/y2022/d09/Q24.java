package y2022.d09;

import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import static java.lang.System.out;
import static java.nio.file.Files.readAllLines;
import static java.util.stream.IntStream.range;
import static y2022.d09.Q24.D.valueOf;
import static y2022.d09.Q24.P.p;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;

public class Q24 {
	
	enum D {U, L, D, R};
	
	static class P {
		int r; int c;
		private P(int r, int c) { this.r=r; this.c=c; } 
		@Override public String toString() { return "{" + r + " " + c + "}"; }
		@Override public boolean equals(Object obj) { return obj instanceof P p && p.c == c && p.r == r; }
		@Override public int hashCode() { return Objects.hashCode(r) * Objects.hashCode(c); }
		@Override protected P clone() throws CloneNotSupportedException { return p(r, c); }
		P move(D d) { switch (d) { case U-> r+=1; case L-> c-=1; case D-> r-=1; case R-> c+=1; }; return this; }
		P move(P h) { int dr=h.r-r, dc=h.c-c; r+=delta(dr, dc); c+=delta(dc, dr); return this; }
		private int delta(int da, int db) {
			return switch (da) { case -2-> -1; case -1-> abs(db)==2 ? -1 : 0; default-> 0; case 1-> abs(db)==2 ? 1 : 0; case +2-> +1; };
		} 
		static P p() { return p(0, 0); };
		static P p(int r, int c) { return new P(r,c); }
	};
	
	public static void main(String[] args) throws Exception {
		var tp = new HashSet<P>(); tp.add(p());
		P head = p(), tail[] = range(0, 9).mapToObj(i-> p()).toArray(P[]::new);		
		for (var move: readAllLines(new File("src/y2022/d09/q1.txt").toPath())) {
			var part = move.split(" ");
			var dir = valueOf(part[0]);
			//for (int n = parseInt(part[1]); n > 0; n-=1) {
			int n = parseInt(part[1]); while( n-- > 0) {
				P p = head.move(dir);
				for (var t: tail) p = t.move(p);
				tp.add(p.clone());
			}
		}
		out.println(tp.size());
	}
}
