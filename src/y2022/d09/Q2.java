package y2022.d09;

import static java.lang.System.out;
import static java.nio.file.Files.readAllLines;

import java.io.File;
import java.util.LinkedList;
import java.util.stream.IntStream;

public class Q2 {
	
	enum D {U, L, D, R};
	
	record P(int r, int c) {
		@Override public String toString() { return "{" + r + " " + c + "}"; }
	};
	
	static int len=9, tail=len-1;
	static P head=new P(0,0), rope[] = IntStream.range(0, len).mapToObj(i-> new P(0,0)).toArray(P[]::new);

	public static void main(String[] args) throws Exception {
		LinkedList<P> tp = new LinkedList(); tp.add(rope[tail]);
		//out.printf("h%s t%s\n", h, t);
		for (var line: readAllLines(new File("src/y2022/d09/q1.txt").toPath())) {
			var part = line.split(" ");
			D d = D.valueOf(part[0]);
			int n = Integer.parseInt(part[1]);
			//out.printf("%s %d\n", d, n);
			while (n-- > 0) {
				var p = head = move(head, d);
				//out.printf("h%s t%s\n", h, t);
				for (int i=0; i<len; i+=1) p = rope[i] = move(p, rope[i]);
				if (!tp.getLast().equals(p)) tp.add(p);
			}
		}
		out.println(tp.stream().distinct().count());
	}
	
	/*
	   h h     h     h h
	   h +1-1 +10 +1+1 h
	   h  0-1  t   0+1 h
	   h -1-1 -10 -1+1 h
	   h h     h     h h
	*/
	
	static int[][][] move = new int [][][] {
		/* R      C    0 -2    1 -1    2 0    3 +1    4 +2 	
		/*0 -2*/	{{+1,-1},{+1,-1},{+1,0},{+1,+1},{+1,+1}},
		/*1 -1*/	{{+1,-1},{ 0, 0},{ 0,0},{ 0, 0},{+1,+1}},
		/*2  0*/	{{ 0,-1},{ 0, 0},{ 0,0},{ 0, 0},{ 0,+1}},
		/*3 +1*/	{{-1,-1},{ 0, 0},{ 0,0},{ 0, 0},{-1,+1}},
		/*4 +2*/	{{-1,-1},{-1,-1},{-1,0},{-1,+1},{-1,+1}}
	};
	
	static P move(P h, P t) {
		var ir = t.r - h.r;
		var ic = h.c - t.c;
		var mt = move[2+ir][2+ic];
		var p = new P(t.r + mt[0], t.c + mt[1]);
		//out.printf("%d %d {%+d,%+d} t%s\n",ir,ic,mt[0],mt[1],p);
		return p;
	}

	static P move(P h, D d) {
		return switch (d) {
			case U -> new P(h.r+1, h.c);
			case L -> new P(h.r,   h.c-1);
			case D -> new P(h.r-1, h.c);
			case R -> new P(h.r,   h.c+1);
		};
	}
}
