package y2022.d09;

import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.nio.file.Files.readAllLines;
import static java.util.stream.IntStream.range;
import static y2022.d09.Q23.D.valueOf;
import static y2022.d09.Q23.P.p;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Q23 {
	
	enum D {U, L, D, R};
	
	static class P {
		int r; int c;
		private P(int r, int c) { this.r=r; this.c=c; } 
		@Override public String toString() { return "{" + r + " " + c + "}"; }
		@Override public boolean equals(Object obj) { return obj instanceof P p && p.c == c && p.r == r; }
		@Override public int hashCode() { return Objects.hashCode(r) * Objects.hashCode(c); }
		@Override protected P clone() throws CloneNotSupportedException { return p(r,c); }
		P move(D d) { switch (d) { case U-> r+=1; case L-> c-=1; case D-> r-=1; case R-> c+=1; }; return this; }
		
		/*
		   h h     h     h h
		   h +1-1 +10 +1+1 h
		   h  0-1  t   0+1 h
		   h -1-1 -10 -1+1 h
		   h h     h     h h
		*/
		
		private static P[][] move = new P[][] {
			/* R      C     0:-2     1:-1     2:0     3:+1     4:+2 	
			/*0:-2*/	{p(+1,-1),p(+1,-1),p(+1,0),p(+1,+1),p(+1,+1)},
			/*1:-1*/	{p(+1,-1),p( 0, 0),p( 0,0),p( 0, 0),p(+1,+1)},
			/*2: 0*/	{p( 0,-1),p( 0, 0),p( 0,0),p( 0, 0),p( 0,+1)},
			/*3:+1*/	{p(-1,-1),p( 0, 0),p( 0,0),p( 0, 0),p(-1,+1)},
			/*4:+2*/	{p(-1,-1),p(-1,-1),p(-1,0),p(-1,+1),p(-1,+1)}
		};	
		P move(P h) { return add(move[2+r-h.r][2+h.c-c]); }
		P add(P p) { r+=p.r; c+=p.c; return this; } 
		static P p() { return p(0, 0); };
		static P p(int r, int c) { return new P(r,c); }
	};
	
	public static void main(String[] args) throws Exception {
		int len=9;
		Set<P> tp = new HashSet(); tp.add(p());
		P head=p(), tail[]=range(0, len).mapToObj(i-> p()).toArray(P[]::new);
		for (var line: readAllLines(new File("src/y2022/d09/q1.txt").toPath())) {
			var part = line.split(" ");
			var d = valueOf(part[0]);
			int n = parseInt(part[1]);
			while (n-- > 0) {
				var p = head.move(d);
				for (int i=0; i<len; i+=1) p = tail[i].move(p);
				tp.add(p.clone());
			}
		}
		out.println(tp.size());
	}	
}
