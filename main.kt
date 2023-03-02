package minesweeper
import kotlin.random.Random

var minedField = mutableListOf(mutableListOf<Char>())

fun createField(x: Int, y: Int, mines: Int) {
    minedField = MutableList(x) {MutableList(y) {'.'}}
    var mines = mines
    while (mines != 0) {
        minedField[Random.nextInt(0, x)][Random.nextInt(0, y)] = 'X'
        mines--
    }
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
    for (line in minedField) {
        println(line.joinToString(""))
    }
}
