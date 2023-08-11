package com.example.mathwarrior

import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.random.Random

data class Problem(
    val text:String,
    val answer:Int,
    val wrongChoice1:Int,
    val wrongChoice2: Int,
    val wrongChoice3: Int
)

fun getProblem(): Problem {
    val numberOfNumbers = Random.nextInt(2, 5)
    val listOfNumbers = mutableListOf<String>()
    repeat(numberOfNumbers) {
        val number = Random.nextInt(-100, 100)
        var numberAsString = number.toString()
        if (number < 0) {
            numberAsString = "($numberAsString)"
        }
        listOfNumbers.add(numberAsString)
    }
    val symbols = listOf(" + "," - "," × "," ÷ ")
    val listOfSymbolsUses = mutableListOf<String>()
    repeat(numberOfNumbers - 1) {
        listOfSymbolsUses.add(symbols[Random.nextInt(3)])
    }

    val expression = mutableListOf<String>()
    expression.add(listOfNumbers.removeAt(0))
    for (symbol in listOfSymbolsUses) {
        expression.add(symbol)
        expression.add(listOfNumbers.removeAt(0))
    }
    val equation = expression.joinToString("")
    val answer = evaluateExpression(expression)
    println(equation)
    var wrongChoice1 = generateWrongChoice(answer)
    var wrongChoice2 = generateWrongChoice(answer)
    var wrongChoice3 = generateWrongChoice(answer)
    while (answer == wrongChoice1 || answer == wrongChoice2 || answer == wrongChoice3 || wrongChoice1 == wrongChoice2 || wrongChoice3 == wrongChoice2 || wrongChoice1 == wrongChoice3) {
        wrongChoice1 = generateWrongChoice(answer)
        wrongChoice2 = generateWrongChoice(answer)
        wrongChoice3 = generateWrongChoice(answer)
    }
    return Problem(equation, answer, wrongChoice1, wrongChoice2, wrongChoice3)
}

fun generateWrongChoice(answer: Int): Int {
    val lowerBound = max((answer * 0.8).toInt(), -100)
    val upperBound = min((answer * 1.2).toInt(), 100)

    if (lowerBound >= upperBound) {
        return Random.nextInt(-100, 100)  // If range is invalid, generate a random number between -100 and 100
    }

    var wrongChoice = Random.nextInt(lowerBound, upperBound + 1)
    while (wrongChoice == answer) {
        wrongChoice = Random.nextInt(lowerBound, upperBound + 1)
    }
    return wrongChoice
}


fun evaluateExpression(expression: List<String>): Int {
    val operators = setOf(" + ", " - ", " × ", " ÷ ")
    val stack = mutableListOf<Int>()
    var currentOperator = " + " // Initialize with addition to handle the first number

    for (element in expression) {
        if (element in operators) {
            currentOperator = element
        } else {
            val num = element.removeSurrounding("(", ")").toInt()
            when (currentOperator) {
                " + " -> stack.add(num)
                " - " -> stack.add(-num)
                " × " -> stack[stack.lastIndex] *= num
                " ÷ " -> stack[stack.lastIndex] /= num
            }
        }
    }

    return stack.sum()
}
