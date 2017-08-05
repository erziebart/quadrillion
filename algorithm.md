Summary of Data Structures and Algorithm

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
- 1.) Choose a point S in B.
- 2.) Try to find a P in L which can 

Overall Algorithm Steps:
- 1.) Calculate initial B and initialize L
- 2.) Call recursive function solve(B, L).
- 3.) Capture return value of solve(), which is a list of Piece locations which solve the board, or NULL if no solution is found

Optimizations:
- 
