package crackingTheCoding;

public class FibonacciSeries {

	// Time complexity: T(n) = Ω(2^(n/2)) (lower bound)
	// T(n) = O(2^n) (upper bound)
	// T(n) = Θ(Fib(n)) (tight bound)
	// Space Complexity: O(n) if we consider function stack size
	static int recursive_fibo(int n) {
		if (n <= 1) { // this case returns 0 in case of 0 and 1 in case of 1
			return n;
		}
		return recursive_fibo(n - 1) + recursive_fibo(n - 2); // function of the
																// form: Fn =
																// Fn-1 + Fn-2
	}

	// time complexity: O(N)
	// Space complexity: O(N)
	static int fibo_dynamic(int n) {
		// declare array for storing fibonacci number for each n
		int[] f = new int[n + 1];

		// seed values
		f[0] = 0;
		f[1] = 1;

		// noew loop through the rest of the values
		for (int i = 2; i <= n; i++) {
			f[i] = f[i - 1] + f[i - 2];
		}

		return f[n];
	}

	// time complexity: O(N)
	// Space complexity: O(1)
	static int fibo_dynamic_space_efficient(int n) {
		// LOGIC: as we need only three values at time for calculating the next
		// fibonacci number we can have 3 variables that store the last 3 values
		
		int a=0, b=1, c=0;
		int result=0;
		
		for (int j = 1; j < n; j++) {
			c=a+b;
			a=b;
			b=c;
		}
		
		return c;
	}

	public static void main(String[] args) {
		System.out.println(recursive_fibo(10));
		System.out.println(fibo_dynamic(10));
		System.out.println(fibo_dynamic_space_efficient(10));
	}
}
