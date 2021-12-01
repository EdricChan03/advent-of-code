# Advent Of Code

The source code containing solutions for each puzzle in
[Advent Of Code](https://adventofcode.com), written in Kotlin.

## Code Structure

Hugely inspired by
[Edgars Supe's GitHub repository](https://github.com/edgars-supe/advent-of-code),
it leverages a similar project structure.

### Running a puzzle

Each file having a class that extends from
[`Puzzle`](src/main/kotlin/com/edricchan/aoc/Puzzle.kt) should
have a top-level `main` function defined as well, which can then
be run from within an IDE such as
[IntelliJ IDEA](https://www.jetbrains.com/idea/).
