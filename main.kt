package minesweeper
import kotlin.random.Random

var minedField = mutableListOf(mutableListOf<Char>())
var OpenField = mutableListOf(mutableListOf<Char>())

fun createOpenField() {
    for (i in minedField.indices) { // i is a rows
        for (j in minedField[i].indices) { // j is a columns
            if (minedField[i][j] == 'X') {
                OpenField[i][j] = 'X'
                continue
            }
            var countOfMines = 0
            for (x in i-1..i+1) { // x is rows from previous to next
                for (y in j-1..j+1) {  // y is columns from previous to next
                    if ((x >= 0 && y >= 0) && (x < minedField.size && y < minedField.size)) {
                        if (minedField[x][y] == 'X') {
//                            print("mine!")
                            countOfMines = countOfMines + 1
                        }
                    }
                }
            }
            if (countOfMines > 0) OpenField[i][j] = countOfMines.digitToChar()
        }
    }
}

fun createField(x: Int, y: Int, mines: Int) {
    minedField = MutableList(x) {MutableList(y) {'.'}}
    OpenField = MutableList(x) {MutableList(y) {'.'}}
    var mines = mines
    while (mines != 0) {
        var (x0, y0) = List(2) {Random.nextInt(0, x); Random.nextInt(0, y)}
        if (minedField[x0][y0] != 'X') {
            minedField[x0][y0] = 'X'
            mines--
        }
    }
    createOpenField()
}

// fun random(n: Int): List<Int> { // tried to create a randomizer function, maybe will finish in future
//     var res = mutableListOf<Int>() // I just don't know, how to get seconds of current time
//     var x = 8
//     for (i in 0..n) {
//         x = (7 * x + 8) % 11
//         res.add(x)
//     }
//     return res
// }
    
fun main() {
    println("How many mines do you want on the field?")
    createField(9, 9, readln().toInt())
    for (line in OpenField) {
        println(line.joinToString(""))
    }
}
