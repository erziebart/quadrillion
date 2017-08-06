# Summary of Data Structures and Algorithm

Board Slots:
- Symbol: B
- Stored as a series of pairs of integers, representing coordinates of the open slots relative to some origin point. The origin is set to be something reasonable such as the lower left corner of the arrangement

Piece:
- Symbol: P
- Like the board slots, is stored as a series of pairs of integers to represent coordinates. Since the maximum size of these pieces is five slots, the origin can be recalculated in a reasonable amount of time to be any of these points

PieceList:
- Symbol: L
- Holds a list of P. Can dynamically add and remove these elements

Solve Function:
- is recursive
- takes as arguments B and L
- Base Case: B is empty
- calls itself with elements removed from B and L

Solve Function Algorithm:
- 1.) If B is empty, return an empty array.
- 2.) Choose a point S in B.
- 3.) Loop through the P in L. For each P, rotate to all 4 directions, mirror each, and cycle through possible origin points (for a total of 8n configurations where n is the number of points in P). For each of these, test whether that particular configuration will fit in S, meaning that there is a point in B for each point in P given that rotation and placing the origin at S.
- 4.) Once a P is found which fits, remove the corresponding P from L, and remove the filled points from B. Pass the new L and B to the same solve function once again. 
- 5.) If the captured return value from step 4.) is not NULL, return the returned array with P appended on the end. Otherwise, go to step 3.)
- 6.) If no piece fits in step 3.), return NULL.

Overall Algorithm Steps:
- 1.) Calculate initial B and initialize L
- 2.) Call recursive function solve(B, L).
- 3.) Capture return value of solve(), which is a list of Piece locations which solve the board, or NULL if no solution is found

Optimizations:
- The algorithm will run faster if the point S chosen in step 2.) of the solve() function algorithm has relatively few piece configurations which will fit there, since that means fewer recursive solve() function calls. Therefore, the solve() function uses a simple metric to decide how cornered a given S is. It then chooses the S among all the points in B which has the best score. The particular metric used here is to count how many board slots neighbor the current (above, below, left, right). The lowest score is chosen to be S.
- For some pieces P in L, it is not necessary to check all 8n orientations. In some cases, the piece has 90 degree rotational, 180 degree rotational, or mirror symmetry, which means these orientations do not need to be checked during the solve() function algorithm execution. Therefore, at the beginning of program execution, all the pieces are instantiated and a check is done for these symmetries. That info is then stored in boolean flags within the piece objects and used by the solve() function to optimize performance. Once again,  this creates fewer recursive calls to the solve() functions which speeds up program execution.
