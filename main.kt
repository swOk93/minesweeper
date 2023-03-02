package minesweeper

var minedField = mutableListOf(mutableListOf<Char>())

fun createField(x: Int, y: Int, mines: Int) {
    minedField = MutableList(x) {MutableList(y) {'.'}}
}

fun main() {
    createField(9, 9, 10)
    for (line in minedField) {
        println(line.joinToString(""))
    }
}
