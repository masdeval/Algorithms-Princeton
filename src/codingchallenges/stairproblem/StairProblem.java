package codingchallenges.stairproblem;



public class StairProblem {

	/**
	 * 
	 * A child is running up a staircase with n steps and can hop either 1 step, 2
	 * steps, or 3 steps at a time. Implement a method to count how many possible
	 * ways the child can run up the stairs.
	 * 
	 * Discussion: https://stackoverflow.com/questions/66790486/using-only-1-2-3-steps-reach-to-nth-step
	 *						 
	 * 
	 */

	
	
	
	//  *** Dynamic Programming solution ***
	
	
	/**
	 * https://www.youtube.com/watch?v=5o-kdjv7FD0
	 * 
	 * The idea is, to compute a stair with 5 steps, we sum number of steps for 4,3 and 2.
	 * 
	 * This holds because we can do steps of size 1, 2 and 3. 
	 * 
	 * Thus, in step 0 we can:
	 * 
	 *  1 - do one step -> the ways to get to the top is steps(4)
	 *  2 - do two steps -> the way to get to the top is steps(3)
	 *  3 - do three steps -> the way to get to the top is steps(2)
	 * 
	 *  steps(0) = 1
	 *  steps(1) = 1
	 *  steps(2) = 2
	 *  steps(3) = steps(2) + steps(1) + steps(0) = 4
	 *  steps(4) = steps(3) + steps(2) + steps(1) = 7
	 * 
	 * @param n
	 * @return
	 */
	public static long dynamicProgramming(int n) {
		if (n == 0 || n == 1)
			return 1;

		long[] res = new long[n + 1];
		res[0] = 1;
		res[1] = 1;
		res[2] = 2;

		for (int i = 3; i <= n; i++)
			res[i] = res[i - 1] + res[i - 2] + res[i - 3];

		return res[n];
	}

	
	
	
	// *** Counting solution ***
	
	public static long factorial(Integer number) {
		long result = Integer.valueOf(1);

		for (long factor = 2; factor <= number.longValue(); factor++) {
			result = result * ((factor));
		}

		return result;
	}

	private static long combination(int n, int k) {

		return factorial(n) / (factorial(k) * factorial(n - k));

	}

	/**
	 * This function computes the number of ways to climb the stair in the case there is a three. 
	 * 
	 * // it is necessary to count all ways we can combine ones with twos having a three
	 *  n = 6
	 *  
	 *  1 + 1 + 1 + 1 + 1 + 1
	 *  
	 *  1 + 1 + 1 + 1 + 2
	 *  1 + 1 + 1 + 2 + 1
	 *  1 + 1 + 2 + 1 + 1
	 *  1 + 2 + 1 + 1 + 1
	 *  2 + 1 + 1 + 1 + 1
	 *  1 + 1 + 2 + 2
	 *  2 + 2 + 1 + 1
	 *  1 + 2 + 2 + 1
	 *  1 + 2 + 1 + 2
	 *  2 + 1 + 2 + 1
	 *  2 + 1 + 1 + 2
	 *  2 + 2 + 2
	 *  
	 *  3 + 1 + 1 + 1
	 *  1 + 3 + 1 + 1
	 *  1 + 1 + 3 + 1
	 *  1 + 1 + 1 + 3
	 *  
	 *  3 + 2 + 1
	 *  3 + 1 + 2
	 *  1 + 3 + 2
	 *  2 + 3 + 1
	 *  1 + 2 + 3
	 *  2 + 1 + 3
	 *  
	 *  3 + 3
	 * 
	 * Total = 24
	 * 
	 * @param lenghtOfOnes
	 * @return
	 */
	private static long computeVariationInBlckOfOnes(int lengthOfOnes, int lengthOfThrees) {

		long counter = 0;

		// block with two's
		int i = 1; // number of twos's
		while (lengthOfOnes - 2 * i >= 0) {

			int numberOfOnes = (lengthOfOnes - 2 * i);
			int aux = numberOfOnes + i; // size of sub_seq


			if (numberOfOnes == 0) {

				counter = counter + combination(i+lengthOfThrees, lengthOfThrees);  
				/**
				 * Suppose n = 10, and 3 + 3 + 1 + 1 + 1 + 1, this function will compute how 1's can vary 				 * 
				 * At some point, the 1's block will be processed here
				 * 
				 * when i = 2 =>  3 + 3 + 2 + 2, which lead to
				 * 
				 *  3 + 3 + 2 + 2
				 *  3 + 2 + 3 + 2
				 *  3 + 2 + 2 + 3 
				 *  2 + 3 + 2 + 3
				 *  2 + 2 + 3 + 3
				 *  2 + 3 + 3 + 2
				 *  				  
				 */
				 
				break;
			}

	
			int lengthOfSequence = aux + lengthOfThrees;

			// Ex: 3 + 1 + 1 + 1 + 1 + 1
			// The case with 5 ones, first iteration we have one 2 
			// 1 + 1 + 1 + 2 => 4 different combinations => combination( #size_of_sub_seq , #of_threes ) 
			// but, because there is a hidden 3, 3 + 1 + 1 + 1 + 2, we have to multiply combination( #length_of_seq, #of_thress )
			// to take into account that this 3 can be in any of the 5 positions in any of the 4 different combinations	
			
			counter = counter + (combination(aux, i) * combination(lengthOfSequence, lengthOfThrees)); 

			i++;

		}

		return counter;

	}

	public static long countingMethod(int n) {

		if (n == 0 || n == 1)
			return 1;

		int counter = 0;

		// block with two's
		int i = 1;
		while (n - 2 * i >= 0) {

			int a = (n - 2 * i);

			if (a == 0) {

				counter += 1;
				break;
			}

			
			int aux = a + i;

			counter += combination(aux, i);

			i++;

		}

		//System.out.println(counter);
		
		// block with three's
		i = 1;
		while (n - 3 * i >= 0) {

			int a = (n - 3 * i); // this is the number of ones if we subtract 3*i from n

			if (a == 0) {

				counter += 1;
				break;
			}

			counter += computeVariationInBlckOfOnes(a, i);

			int aux = a + i;

			counter += combination(aux, i);

			i++;

		}
		
		return counter + 1;

	}

	
	// *** Matrix power solution *** 
	
	/**
	 * 
	 * 
	 * S0 = 1
	 * S1 = 1
	 * S2 = 2
	 * S3 = S2 + S1 + S0
	 *  
	 * 							
	 *  | 1  1  1 |     | SK+2 |   | S[3] | 
			| 1  0  0 |   * | SK+1 | = | S[2] |
			| 0  1  0 |     | SK   |   | S[1] |

					A              S2         S3
				
	 * 
	 *  Sk+1 = A * SK  
	 *  
	 *  ->  k=2   S3 = A * S2
	 *  
	 *  S[3] = S[2] + S[1] + S[0] = 4 
	 *  S[2] = S[2] 
	 *  S[1] = S[1]  
	 *   
	 *  S3 = | 4 2 1 |
	 *  
	 *  -> K=3  S4 = A * S3   // Compute Sk+1 from Sk one step at time
	 * 
	 *  | 1  1  1 |     | 4 |   | S[4] | 
			| 1  0  0 |   * | 2 | = | S[3] |
			| 0  1  0 |     | 1 |   | S[2] |

					A            S3         S4
					
	 *  S[4] = S[3] + S[2] + S[1] = 7
	 *  S[3] = S[3] = 4
	 *  S[2] = S[2] = 2 
	 *   
	 *  
	 *  If k = 100 : 
	 *  
	 *   		Slow way, multiply each step at a time 
	 *  
	 *  		Fast way, diagonalization 
	 *  
	 *  		The diagonalization process can be expressed with the following formula : A = S V S^−1
	 *  
	 *  
	 *  The eigenvector matrix S produces A = S V S^-1. 
	 *  
	 *      A^k * S0 = (S V S^-1)^k * S0 = (S V S^-1) * (S V S^-1) * ... (S V S^-1) * S0 = S V^k S^-1 * S0    
	 *    
	 *  This holds because S * S^-1 = I (identity matrix) 
	 * 
	 *  		Sk+1 = A * SK  -> S100 = A * S99  (100 steps)
	 *  
	 *  But, with matrix factorization we can solve in one step
	 *  
	 *   		Sk = A^k * S0  
	 * 
	 * 
	 *  The matrix factorization is:
	 *  
	 *  | 0.89  0      0.46  |  | 1.93  0  0    |  | 0.63  -0.71  -0.33 |
	 *  | 0.33  -0.71  -0.63 |  |  0    1  0    |  | 0.63   0.71  -0.33 | 
	 *  | 0.33  0.71   -0.63 |	|  0    0  0.52 |  | 0.46     0    0.89 | 
	 * 
	 *             S                    V                     S^-1    
	 *             
	 *             
	 *  Solving S V^k S^-1 * S0           
	 *             
	 *  First step:  S^-1 * S0          
	 *             
	 *  | 0.63  -0.71  -0.33 |  	|1|    |-0.74 |
	 *  | 0.63   0.71  -0.33 |  * |1|  = | 0.68 |
	 *  | 0.46     0    0.89 | 		|2|    | 2.24 |
	 *  
	 *  																		c
	 *  
	 *  Second step: c * V^k =  (1.93)^k * -.74 , (1) * 0.68 , 2^k * 2.24
	 *  
	 *  Third step:  S * c * V^k  =  Sk
	 *  
	 *  | 0.89     0   0.46  |  | (1.93)^k * -.74 |			| (1.93)^k * -.74 * 0.89 + 2^k * 2.24 * 0.46                    |   | (1.93)^k * -0.66 + 2^k * 1.03         |
	 *  | 0.33  -0.71  -0.63 |  |      0.68       |  =  | (1.93)^k * -.74 * 0.33 + 0.68 * -0.71 +  2^k * 2.24 * -0.63   | = | (1.93)^k * -0.24 - 0.48 + 2^k * -1.41 |
 	 *  | 0.33  0.71   -0.63 |  |  2^k * 2.24     | 		| (1.93)^k * -.74 * 0.33 + 0.68 * 0.71  +  2^k * 2.24 * -0.63   |   | (1.93)^k * -0.24 + 0.48 + 2^k * -1.41 |
	 *  
	 *  
	 *  
	 * 
	 * @param args
	 */
	
	public static double matrixPower(int n) {
		
		//(1.93)^k * -0.66 + 2^k * 1.03
		return  Math.floor( Math.pow(1.93, n) * -0.66 + Math.pow(2, n) * 1.03);		
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Dynamic Programming
		System.out.println("Dynamic Programming");
		System.out.println("4 stairs " + dynamicProgramming(4));
		System.out.println("5 stairs " + dynamicProgramming(5));
		System.out.println("6 stairs " + dynamicProgramming(6));
		System.out.println("7 stairs " + dynamicProgramming(7));
		System.out.println("8 stairs " + dynamicProgramming(8));
		System.out.println("9 stairs " + dynamicProgramming(9));
		System.out.println("10 stairs " + dynamicProgramming(10));
		System.out.println("11 stairs " + dynamicProgramming(11));
		System.out.println("12 stairs " + dynamicProgramming(12));
		System.out.println("13 stairs " + dynamicProgramming(13));

		
		System.out.println("");
		System.out.println("Counting");
		System.out.println("4 stairs " + countingMethod(4));
		System.out.println("5 stairs " + countingMethod(5));
		System.out.println("6 stairs " + countingMethod(6));
		System.out.println("7 stairs " + countingMethod(7));
		System.out.println("8 stairs " + countingMethod(8));
		System.out.println("9 stairs " + countingMethod(9));
		System.out.println("10 stairs " + countingMethod(10));
		System.out.println("11 stairs " + countingMethod(11));
		System.out.println("12 stairs " + countingMethod(12));
		System.out.println("13 stairs " + countingMethod(13));

		System.out.println("");
		System.out.println("Matrix power");
		System.out.println("4 stairs " + matrixPower(4));
		System.out.println("5 stairs " + matrixPower(5));
		System.out.println("6 stairs " + matrixPower(6));
		System.out.println("7 stairs " + matrixPower(7));
		System.out.println("8 stairs " + matrixPower(8));
		System.out.println("9 stairs " + matrixPower(9));
		System.out.println("10 stairs " + matrixPower(10));
		System.out.println("11 stairs " + matrixPower(11));
		System.out.println("12 stairs " + matrixPower(12));
		System.out.println("13 stairs " + matrixPower(13));
	
		
	}

}
