package y2022.d08;

import static java.lang.Math.max;
import static java.lang.System.out;
import static java.nio.file.Files.readAllLines;
import static java.util.Arrays.stream;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Q2 {
	
	public static void main(String[] args) throws Exception {
		List<int[]> list = new ArrayList();
		for (var line: readAllLines(new File("src/y2022/d08/q1.txt").toPath())) {
			list.add(stream(line.split("")).mapToInt(Integer::parseInt).toArray());
		}
		map = list.toArray(int[][]::new);
		len = map.length;
		//show(map);
		int max = 0;
		//max = point(len, 3, 2);
		for (int i=0; i<len; i+=1) 
			for (int j=0; j<len; j+=1)
				max = max(max, scenic(i,j));
		out.println(max);
	}
	
	static int len, map[][];

	private static int scenic(int ti, int tj) {
		int h = map[ti][tj];
		int u=0; for (int i=ti-1; i>=0;  i-=1) {u+=1; if (map[i][tj] >= h) break; }
		int l=0; for (int j=tj-1; j>=0;  j-=1) {l+=1; if (map[ti][j] >= h) break; }
		int d=0; for (int i=ti+1; i<len; i+=1) {d+=1; if (map[i][tj] >= h) break; }
		int r=0; for (int j=tj+1; j<len; j+=1) {r+=1; if (map[ti][j] >= h) break; }
		//out.printf("%d %d %d %d\n",u,l,d,r);  
		return u*l*d*r;
	}
	
	@SuppressWarnings("unused")
	private static void show(int[][] map) {
		for (int i=0; i<len; i+=1) {
			for (int j=0; j<len; j+=1)
				out.print(map[i][j]);
			out.println();
		}
		out.println();
	}
}
