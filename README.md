# Peg Solitaire

Peg Solitaire is a variation of Solitaire in which the player has to configure their peg board layout to where there is only one peg left.

# How The Game Works

* A peg can only move by jumping over another peg into an empty hole in the board
  * This means the jump can only be 2 spaces, over another peg and into the empty hole
* The peg that is jumped over is then removed from the board
* The game ends in two ways
  * There is only one peg left in the board (WIN)
  * There are no more possible moves to be made (LOSE)
* Note that pegs are displayed as @s, empty holes are displayed as -s, and extra blank positions that are neither pegs nor holes within each rectangular array are displayed as #s.

# How To Run The Game
```
javac PegSolitaireGame.java
java PegSolitaireGame
```

# Sample Game Demo




https://github.com/rsambangi/Peg-Solitaire/assets/143136941/18e590ba-3079-48da-bf40-af89c2b814bd

