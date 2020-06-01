package converter

import java.util.*

fun main() {

    println("Enter a number and a measure of length: ")
    val scanner = Scanner(System.`in`)
    val n = scanner.nextDouble()
    var unit = scanner.next().toLowerCase()
    var res = 0.0

    fun plural(n: Double): String {
        return if (n == 1.0) "" else "s"
    }

    fun displayResult(n: Double, unit: String, res: Double): String {
        return "$n $unit${plural(n)} is $res meter${plural(res)}"
    }

    when (unit) {
        "m", "meter", "meters" -> {
            res = n
            unit = "meter"
        }
        "km", "kilometer", "kilometers" -> {
            res = n * 1000
            unit = "kilometer"
        }
        "cm", "centimeter", "centimeters" -> {
            res = n / 100
            unit = "centimeter"
        }
        "mm", "millimeter", "millimeters" -> {
            res = n / 1000
            unit = "millimeter"
        }
        "mi", "mile", "miles" -> {
            res = n * 1609.35
            unit = "mile"
        }
        "yd", "yard", "yards" -> {
            res = n * 0.9144
            unit = "yard"
        }
        "ft", "foot", "feet" -> {
            res = n * 0.3048
            unit = "feet"
        }
        "in", "inch", "inches" -> {
            res = n * 0.0254
            unit = "inches"
        }
    }

    if (unit == "feet") {
        when {
            n == 1.0 -> {
                println("$n foot is $res meters")
            }
            res == 1.0 -> {
                println("$n feet is $res meter")
            }
            else -> println("$n feet is $res meters")
        }
    } else if (unit == "inches") {
        when {
            n == 1.0 -> {
                println("$n inch is $res meters")
            }
            res == 1.0 -> {
                println("$n inches is $res meter")
            }
            else -> println("$n inches is $res meters")
        }
    } else if (unit != "feet" || unit != "inches") {
        println(displayResult(n, unit, res))
    }
}
