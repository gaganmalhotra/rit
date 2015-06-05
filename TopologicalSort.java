import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * This class sorts the given elements of a tree in topological order using stacks
 * @author Gagandeep Malhotra
 *
 */
public class TopologicalSort {
	
	static int numberOfVertices;
	static List<List<Integer>> adjacencyList;
	static Stack<Integer> stack = new Stack<Integer>();

	
	public static void topologicalsortUntil(int v, boolean[] currentState){
		currentState[v]=true;
		
		for (int i = 0; i < adjacencyList.get(v).size(); i++) {
			if(currentState[adjacencyList.get(v).get(i)]==false){
				topologicalsortUntil(adjacencyList.get(v).get(i), currentState);
			}
		}
		
	stack.push(v);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int v= 6;
		
		//setting all the list to zero
		numberOfVertices = v;
		adjacencyList = new ArrayList<List<Integer>>();
		boolean currentState[] = new boolean[v];
		
		
		//initializing the array list
		for (int i = 0; i <v ; i++) {
			adjacencyList.add(i, Arrays.asList(0));
		}
		
		//adding the edges
		adjacencyList.remove(2);
		adjacencyList.add(2, Arrays.asList(3));
		adjacencyList.remove(3);
		adjacencyList.add(3, Arrays.asList(1));
		adjacencyList.remove(4);
		adjacencyList.add(4, Arrays.asList(0,1));
		adjacencyList.remove(5);
		adjacencyList.add(5, Arrays.asList(0,2));
		
		//marking all the vertex unvisited
		for (int i = 0; i < v; i++) {
			currentState[i]= false;
		}	
		
		for (int i = 0; i < v; i++) {
			if(currentState[i]==false){
				topologicalsortUntil(i, currentState);
			}
		}
		
		//printing the stack here
		while(!stack.empty()){
			System.out.print(stack.pop()+" ");
		}
		
		
	}

	
}


