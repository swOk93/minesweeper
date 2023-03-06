package minesweeper
import kotlin.random.Random

var openField = mutableListOf(mutableListOf<Char>())
var closedField = mutableListOf(mutableListOf<Char>())
var countOfMines = 0
var mines = 0
var miss = 0

fun createFields(x: Int, y: Int) {
    openField = MutableList(x) {MutableList(y) {'.'}}
    closedField = MutableList(x) {MutableList(y) {'.'}}
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
    // for (line in openField.indices) {
    //     closedField[line].addAll(openField[line])
    //     for (char in closedField[line].indices) {
    //         if (closedField[line][char] == 'X') closedField[line][char] = '.'
    //     }
    // }
}

fun printField(list: MutableList<MutableList<Char>>) {
    println(" │123456789│\n" +
            "—│—————————│")
    for (line in list.indices) {
        print("$line|")
        print(list[line].joinToString(""))
        println("|")
    }
    println("—│—————————│")
}

fun marks(x: Int, y: Int) {
    if (closedField[x][y].code in 49..57) {
        println("There is a number here!")
        return
    }
    if (closedField[x][y] == '*') {
        closedField[x][y] = '.'
        if (openField[x][y] == 'X') mines++ else miss--
    }
    else if (openField[x][y] == 'X') {
        closedField[x][y] = '*'
        mines--
    }
    else {
        closedField[x][y] = '*'
        miss++
    }
    printField(closedField)
}

fun checkAround(x: Int, y: Int) {
    for (i in x-1..x+1) { // i is rows from previous to next
        for (j in y-1..y+1) {  // j is columns from previous to next
            if ((i >= 0 && j >= 0) && (i < openField.size && j < openField.size)) {
                if (openField[i][j].code in 49..57) closedField[i][j] = openField[i][j]
                else if (openField[i][j] == '.' && closedField[i][j] != '/') {
                    closedField[i][j] = '/'
                    checkAround(i, j)
                }
            }
        }
    }
}

fun check(x: Int, y: Int): Boolean {
    if (openField[x][y] == 'X') return true
    else if (openField[x][y].code in 49..57) closedField[x][y] = openField[x][y]
    else if (openField[x][y] == '.') checkAround(x, y)
    printField(closedField)
    return false
}

fun play() {
    mines = countOfMines
    var gameOver = false
    while (miss != 0 || mines != 0) {
        println("Set/delete mine marks (x and y coordinates):")
        val (y, x, action) = readln().split(" ").map { it }.toList() // x is rows, y is columns // there are need to fix for case if player write less data, then need to
        if (x.toInt() > 9 || y.toInt() > 9) {
            println("Wrong coordinates!")
            continue
        }
        when (action) {
            "mine" -> marks(x.toInt() - 1, y.toInt() - 1)
            "free" -> gameOver = check(x.toInt() - 1, y.toInt() - 1)
            else -> println("Please, write 'free' or 'mine' after coordinates")
        }
        if (gameOver) {
            println("You stepped on a mine and failed!")
            printField(openField)
            return
        }
        // printField()
//        println("You miss $miss times")
//        println("$mines mines left")
    }
//    println("(mines != 0 || miss == 0) is ${(mines != 0 && miss == 0)}")
    println("Congratulations! You found all the mines!")
}

fun main() {
    println("How many mines do you want on the field?")
    countOfMines = readln().toInt()
    createFields(9, 9)
    printField(closedField)
    play()
}
