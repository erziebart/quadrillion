# quadrillion-solver

Summary:
Quadrillion is a logic puzzle invented by Smart Games. The objective is to fit 12 colored pieces of varying sizes and shapes onto a board. The four black and white board pieces can be flipped, rotated, and arranged into different shapes, which varies the difficulty of the puzzle. This program allows a user to choose a board arrangement and uses a recursive algorithm to find solutions for that particular board.


Code Files:
- QuadrillionSolverApp.java
- Solver.java
- Board.java
- BoardSetUp.java
- Piece.java
- PieceList.java
- PointCloud2D.java


Required Class Files:
- QuadrillionSolverApp.class
- Solver.class
- Board.class
- BoardSetUp.class
- Piece.class
- PieceList.class
- PointCloud2D.class


Executable:
- QuadrillionSolverApp


Steps to Run:

1.) compile all code files into class files
  --- command: "javac [filename].java"
  
2.) execute QuadrillionSolverApp
  --- command: "java QuadrillionSolverApp"
  


App User Instructions:
- Interface consists of black and white board pieces, solve button, clear button, and shape button.
- Left click "Solve" button to solve the current board configuration. Pieces will appear to fill the board. Clicking the button more times will cycle through several solutions.
- Left click "Clear" button to remove colored pieces from the board.
- Left click the "Shape" button to cycle through the possible board shapes given in the Quadrillion Instructions. Shapes are numberred 1-11 and the current shape number is displayed next to "shape".
- Left click on any of the four black and white board pieces to highlight. A board piece cannot be highlighted when there are pieces filling the board, so the user needs to press "Clear" before highlighting the board.
- Once highlighted, a board piece can be rotated with the arrow keys, or flipped between the white and black sides by pressing the "F" key.
- A highlighted black and white board piece can also have its position swapped with any other piece by clicking on the other piece.
- To deselect a piece, right click anywhere.


*Solver algorithm summary given in "algorithm.md"*
