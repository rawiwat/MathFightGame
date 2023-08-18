package com.example.krmathfight.utility

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
        val number = Random.nextInt(-10, 10)
        var numberAsString = number.toString()
        if (number < 0) {
            numberAsString = "($numberAsString)"
        }
        listOfNumbers.add(numberAsString)
    }
    val symbols = listOf(" + "," - "," × "," ÷ ")
    val listOfSymbolsUses = mutableListOf<String>()
    repeat(numberOfNumbers - 1) {
        listOfSymbolsUses.add(symbols[Random.nextInt(symbols.size)])
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
    return if (answer <= 100 && answer >= -100) {
        Random.nextInt(-100,100)
    } else {
        val lowerBound = (answer * 0.8).toInt()
        val upperBound = (answer * 1.2).toInt()

        if (lowerBound < upperBound) {
            var wrongChoice = Random.nextInt(lowerBound, upperBound)
            while (wrongChoice == answer) {
                wrongChoice = Random.nextInt(lowerBound, upperBound)
            }
            wrongChoice
        } else {
            var wrongChoice = Random.nextInt(upperBound, lowerBound)
            while (wrongChoice == answer) {
                wrongChoice = Random.nextInt(upperBound, lowerBound)
            }
            wrongChoice
        }
    }
}


fun evaluateExpression(expression: List<String>): Int {
    val operators = setOf(" + ", " - ", " × ", " ÷ ")
    val answer = mutableListOf<Int>()
    var currentOperator = " + " // start with plus to handle the first number

    for (element in expression) {
        if (element in operators) {
            currentOperator = element
        } else {
            val num = element.removeSurrounding("(", ")").toInt()
            when (currentOperator) {
                " + " -> answer.add(num)
                " - " -> answer.add(-num)
                " × " -> answer[answer.lastIndex] *= num
                " ÷ " -> answer[answer.lastIndex] /= num
            }
        }
    }
    return answer.sum()
}
