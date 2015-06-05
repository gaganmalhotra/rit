import java.util.Scanner;

/**
 * 
 * @author Gagandeep Malhotra
 *
 */
public class Kruskals {
	
	//Defining the static variables 
	static int[][] adjacencyMatrix;
	static int vertices;
	static int noOfEdges;
	static int boss[];
	static int size[];
	static int[][] set;

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		vertices = sc.nextInt();
		noOfEdges = sc.nextInt();

		// initializing adjacency list
		adjacencyMatrix = new int[vertices][vertices];
		Edge[] edge = new Edge[noOfEdges];

		// setting all the edges to -1 weight
		for (int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				adjacencyMatrix[i][j] = -1;
			}
		}

		int counter = noOfEdges;
		int ct = 0;
		while (counter > 0) {
			int u, v, w;
			u = sc.nextInt();
			v = sc.nextInt();
			w = sc.nextInt();
			adjacencyMatrix[u][v] = w;
			edge[ct] = new Edge(u, v, w);
			ct++;
			counter--;
		}
		sc.close();

		// sorting of edges
		Merge merge = new Merge();
		merge.doMergeSort(edge, 0, edge.length - 1);

		// init boss, size and set

		boss = new int[vertices];
		size = new int[vertices];
		set = new int[vertices][vertices];

		for (int i = 0; i < vertices; i++) {
			boss[i] = i;
			size[i] = 1;
			set[i] = new int[] { i };
		}
		Edge[] t = new Edge[noOfEdges];

		int numberOfMST=1;
		int ctr = 0;
		for (int i = 0; i < edge.length; i++) {
			if(i>0)
			{
				if(edge[i-1].w==edge[i].w)
				{
					numberOfMST++;
				}
			}
			if (find(edge[i].u) != find(edge[i].v)) {
				union(edge[i].u, edge[i].v);
				size[boss[edge[i].u]] += size[boss[edge[i].v]];
				t[ctr] = edge[i];
				ctr++;
			}
			
		}

		
		for (int i = 0; i < t.length; i++) {
			if(i>0 && t[i]!= null)
			{
				if(t[i-1].w==t[i].w)
				{
					numberOfMST--;
				}
			}
		}
		
		System.out.println(numberOfMST);
	}

	public static int find(int u) {
		return boss[u];
	}

	public static void union(int u, int v) {
		if (size[boss[u]] > size[boss[v]]) {
			set[boss[u]] = findUnion(set[boss[u]], set[boss[v]]);
			size[boss[u]] += size[boss[v]];
			int[] arr = set[boss[v]];
			for (int i = 0; i < arr.length; i++) {
				boss[arr[i]] = boss[u];
			}
		} else {
			set[boss[v]] = findUnion(set[boss[v]], set[boss[u]]);
			size[boss[v]] += size[boss[u]];
			int[] arr = set[boss[u]];
			for (int i = 0; i < arr.length; i++) {
				boss[arr[i]] = boss[v];
			}
		}

	}

	public static int[] findUnion(int[] a, int[] b) {
		int[] ret = new int[a.length + b.length];
		for (int i = 0; i < a.length; i++) {
			ret[i] = a[i];
		}
		int counter = 0;
		for (int i = a.length; i < ret.length; i++) {
			ret[i] = b[counter++];
		}
		return ret;
	}

}

/**
 * 
 * This class defines the Edge attributes together
 *
 */
class Edge implements Comparable<Edge> {
	int u, v, w;

	// constructor
	public Edge(int u, int v, int w) {
		this.u = u;
		this.v = v;
		this.w = w;
	}

	@Override
	public int compareTo(Edge o) {
		return this.w > o.w ? 1 : (this.w < o.w ? -1 : 0);
	}

}


class Merge {

	/**
	 * Recursive function to divide the array into half
	 * 
	 * @param a
	 *            array to be sorted
	 * @param low
	 *            Starting index
	 * @param high
	 *            Last Index
	 * 
	 * 
	 * 
	 */
	public void doMergeSort(Object a[], int low, int high) {
		if (low < high) {
			int mid = low + ((high - low) / 2);
			// Recursively divide the array into half
			doMergeSort(a, low, mid);
			doMergeSort(a, mid + 1, high);
			merge(a, low, mid, high);
		}
	}

	/**
	 * Merge the left half and right half in sorted order
	 * 
	 * @param a
	 *            array to be sorted
	 * @param low
	 *            Starting index
	 * @param mid
	 *            Middle index
	 * @param high
	 *            Last Index
	 * 
	 * 
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public static void merge(Object a[], int low, int mid, int high) {
		Object temp[] = new Object[a.length];
		for (int i = low; i <= high; i++) {
			temp[i] = a[i];
		}

		int i = low;
		int j = mid + 1;
		int k = low;

		// Merge the divided array
		while (i <= mid && j <= high) {
			if ((((Comparable) temp[i]).compareTo(temp[j])) < 0) {
				a[k] = temp[i++];
			} else {
				a[k] = temp[j++];
			}
			k++;
		}
		while (i <= mid) {
			a[k++] = temp[i++];

		}
		while (j <= high) {
			a[k++] = temp[j++];
		}

	}
}