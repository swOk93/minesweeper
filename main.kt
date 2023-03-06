package minesweeper
import kotlin.random.Random

var openField = mutableListOf(mutableListOf<Char>())
var closedField = mutableListOf(mutableListOf<Char>())
var countOfMines = 0

fun createFields(x: Int, y: Int) {
    openField = MutableList(x) {MutableList(y) {'.'}}
    closedField = MutableList(x) {mutableListOf()}
    var mines = countOfMines
    while (mines != 0) {
        val (x0, y0) = List(2) {Random.nextInt(0, x); Random.nextInt(0, y)}
        if (openField[x0][y0] != 'X') {
            for (i in x0-1..x0+1) { // i is rows from previous to next
                for (j in y0-1..y0+1) {  // j is columns from previous to next
                    if ((i >= 0 && j >= 0) && (i < openField.size && j < openField.size)) {
                        if (openField[i][j] == '.') openField[i][j] = '1'
                        else if (openField[i][j] != 'X') openField[i][j] = (openField[i][j].digitToInt() + 1).digitToChar()
                    }
                }
            }
            openField[x0][y0] = 'X'
            mines--
        }
    }
    println()
    for (line in openField.indices) {
        closedField[line].addAll(openField[line])
        for (char in closedField[line].indices) {
            if (closedField[line][char] == 'X') closedField[line][char] = '.'
        }
    }
}

fun printField() {
    println(" │123456789│\n" +
            "—│—————————│")
    for (line in closedField.indices) {
        print("$line|")
        print(closedField[line].joinToString(""))
        println("|")
    }
    println("—│—————————│")
}

fun play() {
    var mines = countOfMines
    var miss = 0
    while (miss != 0 || mines != 0) {
        println("Set/delete mine marks (x and y coordinates):")
        val (y, x) = readln().split(" ").map { it.toInt() - 1 }.toList() // x is rows, y is columns
        if (openField[x][y].code in 49..57) {
            println("There is a number here!")
            continue
        }
        if (closedField[x][y] == '*') {
            println(1)
            closedField[x][y] = '.'
            if (openField[x][y] == 'X') mines++ else miss--
        }
        else if (openField[x][y] == 'X') {
            println(2)
            closedField[x][y] = '*'
            mines--
        }
        else {
            println(3)
            closedField[x][y] = '*'
            miss++
        }
        printField()
    println("Congratulations! You found all the mines!")
}

fun main() {
    println("How many mines do you want on the field?")
    countOfMines = readln().toInt()
    createFields(9, 9)
    printField()
    play()
}
