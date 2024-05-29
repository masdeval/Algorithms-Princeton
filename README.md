## This is a page dedicated to share my implementation of some algorithms 

### Coursera Algorithms I

Code I wrote to solve the quizzes when taking the course [Algorithms I at 
Coursera](https://www.google.com/url?sa=t&source=web&rct=j&opi=89978449&url=https://www.coursera.org/learn/algorithms-part1). 

Some code load input tests from text files. Please, *be advised* to change the location to resamble your configuration. 
The path in those files always point to `/home/ubuntu/coursera`

Find the code inside the **src** folder:

- [Percolation](https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php):
  - [PercolationClient](src/PercolationClient.java)
  - [PercolationStats](src/PercolationStats.java)
  - [Percolation](src/Percolation.java)
- [Collinear Points](https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php)
  - [CollinearPointsClient](src/CollinearPointsClient.java)
  - [Point](src/Point.java)
  - [FastCollinearPoints](src/FastCollinearPoints.java)
  - [LineSegment](src/LineSegment.java)
  - [BruteCollinearPoints](src/BruteCollinearPoints.java)
- [Balanced Search Trees](https://coursera.cs.princeton.edu/algs4/assignments/kdtree/specification.php)
  - [KdTree](src/KdTree.java)
  - [KdTreeVisualizer](src/KdTreeVisualizer.java)
  - [NearestNeighborVisualizer](src/NearestNeighborVisualizer.java)
  - [PointSET](src/PointSET.java)
  - [RangeSearchVisualizer](src/RangeSearchVisualizer.java)
- [Priority Queue](https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php)
  - [Solver](src/Solver.java)
  - [Board](src/Board.java)
- [Deques and Randomized Queues](https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php)
  - [Subset](src/Subset.java)
  - [RandomizedQueue](src/RandomizedQueue.java) 
  - [Deque](src/Deque.java)

### Coding Challenges

- [Stair Problem](src/Coding Challenges/Stair Problem/StairProblem.java) :
  - Definition: A child is running up a staircase with n steps and can hop either 1 step, 2
	steps, or 3 steps at a time. Implement a method to count how many possible ways the child can run up the stairs.
  - There are three commented solutions in the code:
    - Using dynamic programming
    - Counting (my implementation)
    - An attempt to use matrix power by linear algebra, which would lead to o O(1) solution. This attempt is partially wrong. I should have used the matrix of eigenvectors, but instead I factorized using SVD.     



In the folder **others** you can find other trick algorithms with discussion and explanation.
