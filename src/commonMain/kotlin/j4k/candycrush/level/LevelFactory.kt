package j4k.candycrush.level

import j4k.candycrush.model.*
import j4k.candycrush.model.Level.TileObjective

class LevelFactory {

    fun isPartOfThreeInARow(board: List<List<Tile>>, row: Int, col: Int): Boolean {
        val candy = board[row][col]
        val rowMatches = listOf(-2, -1, 0, 1, 2).count { board.getOrNull(row + it)?.getOrNull(col) == candy } >= 3
        val colMatches = listOf(-2, -1, 0, 1, 2).count { board.getOrNull(row)?.getOrNull(col + it) == candy } >= 3
        return rowMatches || colMatches
    }
    fun generateValidRandomBoard(): List<List<Tile>> {
        val board = MutableList(9) { MutableList(5) { Tile.A } }  // Inicializa el tablero con cualquier caramelo
        for (row in 0 until 9) {
            for (col in 0 until 5) {
                var candy: Tile
                do {
                    candy = Tile.randomTile()
                    board[row][col] = candy
                } while (isPartOfThreeInARow(board, row, col))
            }
        }
        return board
    }

    fun boardToString(board: List<List<Tile>>): String {
        return buildString {
            for (row in board) {
                append("[")
                for ((index, tile) in row.withIndex()) {
                    append(tile.name)
                    if (index < row.size - 1) {
                        append(",")
                    }
                }
                append("]\n")
            }
        }
    }

    fun createLevel(): Level {

        val reserveData = """
            |[E,C,B,A,D]
            |[D,A,E,C,B]
            """.trimMargin()
        val board = generateValidRandomBoard()
        val levelData = boardToString(board)

        return Level(
            levelData, reserveData, maxMoves = 42,
            tileObjectives = listOf(
                TileObjective(Tile.A, 18),
                TileObjective(Tile.C, 22)
            )
        )
    }
}
