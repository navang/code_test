package f_recursive;

public class Fibonacci {

	public static void main(String[] args) {
		int n =5;
		int result = fib(n);
		System.out.println( "결과" + result);

	}
	static int fib(int n) {
		if(n==1) return 1;			
		if(n==2) return 2;				// 
		return fib(n-1) + fib(n-2);
 	}
	

}
