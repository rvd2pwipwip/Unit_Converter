package converter

import java.util.*
import kotlin.system.exitProcess

enum class Unit(val baseUnit: Double, private val plural: String, val type: String) {
    GRAM(1.0, "grams", "w"),
    KILOGRAM(1000.0, "kilograms", "w"),
    MILLIGRAM(0.001, "milligrams", "w"),
    POUND(453.592, "pounds", "w"),
    OUNCE(28.3495, "ounces","w"),
    METER(1.0, "meters", "d"),
    KILOMETER(1000.0, "kilometers", "d"),
    CENTIMETER(0.01, "centimeters", "d"),
    MILLIMETER(0.001, "millimeters", "d"),
    MILE(1609.35, "miles","d"),
    YARD(0.9144, "yards", "d"),
    FOOT(0.3048, "feet","d"),
    INCH(0.0254, "inches", "d"),
    NULL(0.0, "???","null");

    fun getName(n: Double): String {
        return if (n == 1.0) name.toLowerCase() else plural
    }

    companion object {
        fun getUnit(unit: String): Unit {
            return when (unit.toLowerCase()) {
                "g", "gram", "grams" -> GRAM
                "kg", "kilogram", "kilograms" -> KILOGRAM
                "mg", "milligram", "milligrams" -> MILLIGRAM
                "lb", "pound", "pounds" -> POUND
                "oz", "ounce", "ounces" -> OUNCE
                "m", "meter", "meters" -> METER
                "km", "kilometer", "kilometers" -> KILOMETER
                "cm", "centimeter", "centimeters" -> CENTIMETER
                "mm", "millimeter", "millimeters" -> MILLIMETER
                "mi", "mile", "miles" -> MILE
                "yd", "yard", "yards" -> YARD
                "ft", "foot", "feet" -> FOOT
                "in", "inch", "inches" -> INCH
                else -> NULL
            }
        }
    }
}

enum class Temperature(private val longName: String, private val pluralName: String, val type: String) {
    C("degree Celsius", "degrees Celsius", "t"),
    F("degree Fahrenheit", "degrees Fahrenheit", "t"),
    K("Kelvin", "Kelvins", "t"),
    NULL("", "", "null");

    fun getName(n: Double): String {
        return if (n == 1.0) longName else pluralName
    }

    companion object {
        fun getUnit(unit: String): Temperature {
            return when (unit.toLowerCase()) {
                "degree celsius", "degrees celsius", "celsius", "dc", "c" -> C
                "degree fahrenheit", "degrees fahrenheit", "fahrenheit", "df", "f" -> F
                "kelvin", "kelvins", "k" -> K
                else -> NULL
            }
        }
        fun cToF(c: Double): Double {
            return c * 9 / 5 + 32
        }
        fun cToK(c: Double): Double {
            return c + 273.15
        }
        fun fToC(f: Double): Double {
            return (f - 32) * 5 / 9
        }
        fun fToK(f: Double): Double {
            return (f + 459.67) * 5 / 9
        }
        fun kToC(k: Double): Double {
            return k - 273.15
        }
        fun kToF(k: Double): Double {
            return k * 9 / 5 - 459.67
        }
    }

}

private const val EOP = "exit"

fun main() {
    val scanner = Scanner(System.`in`)
    do {
        println("Enter what you want to convert (or exit): ")
        val input = scanner.nextLine()
        val args = input.split(" ")
        parseInput(args)
    } while (input != EOP)
    exitProcess(0)
}

fun parseInput(input: List<String>) {
    try {
        val n = input[0].toDouble()

        var inUnit = if (input[1].toLowerCase() == "degree" || input[1].toLowerCase() == "degrees") {
            input[1] + " " + input[2]
        } else input[1]
        var outUnit = if (input[input.size - 2].toLowerCase() == "degree" || input[input.size - 2].toLowerCase() == "degrees") {
            input[input.size - 2] + " " + input[input.size - 1]
        } else input[input.size - 1]

        when {
            Temperature.getUnit(inUnit).type == "t" && Temperature.getUnit(outUnit).type == "t" -> {
                val inTemperature = Temperature.getUnit(inUnit)
                val outTemperature = Temperature.getUnit(outUnit)
                val converted = when (inTemperature) {
                    Temperature.C -> when (outTemperature) {
                        Temperature.C -> n
                        Temperature.F -> Temperature.cToF(n)
                        Temperature.K -> Temperature.cToK(n)
                        else -> 0.0
                    }
                    Temperature.F -> when (outTemperature) {
                        Temperature.F -> n
                        Temperature.C -> Temperature.fToC(n)
                        Temperature.K -> Temperature.fToK(n)
                        else -> 0.0
                    }
                    Temperature.K -> when (outTemperature) {
                        Temperature.K -> n
                        Temperature.C -> Temperature.kToC(n)
                        Temperature.F -> Temperature.kToF(n)
                        else -> 0.0
                    }
                    else -> 0.0
                }
                println("$n ${inTemperature.getName(n)} is $converted ${outTemperature.getName(converted)}")
            }
            Unit.getUnit(inUnit).type == Unit.getUnit(outUnit).type && Unit.getUnit(inUnit).type != "null" && Unit.getUnit(outUnit).type != "null" -> {
                val inMeasure = Unit.getUnit(input[1].toLowerCase())
                val outMeasure = Unit.getUnit(input[3].toLowerCase())
                if (n > 0) {
                    val converted = n * inMeasure.baseUnit / outMeasure.baseUnit
                    println("$n ${inMeasure.getName(n)} is $converted ${outMeasure.getName(converted)}")
                } else when (inMeasure.type) {
                    "w"-> println("Weight shouldn't be negative")
                    "d" -> println("Length shouldn't be negative")
                }
            }
            else -> {
                if (Unit.getUnit(inUnit.toLowerCase()) == Unit.NULL && Temperature.getUnit(inUnit) == Temperature.NULL) inUnit = "???"
                if (Unit.getUnit(outUnit.toLowerCase()) == Unit.NULL && Temperature.getUnit(outUnit) == Temperature.NULL) outUnit = "???"
                if (Temperature.getUnit(inUnit) != Temperature.NULL) inUnit = Temperature.getUnit(inUnit).getName(2.0)
                if (Temperature.getUnit(outUnit) != Temperature.NULL) outUnit = Temperature.getUnit(outUnit).getName(2.0)
                if (Unit.getUnit(inUnit.toLowerCase()) != Unit.NULL) inUnit = Unit.getUnit(inUnit.toLowerCase()).getName(2.0)
                if (Unit.getUnit(outUnit.toLowerCase()) != Unit.NULL) outUnit = Unit.getUnit(outUnit.toLowerCase()).getName(2.0)
                println("Conversion from $inUnit to $outUnit is impossible")
            }
        }
    } catch (error: Throwable) {
        if (input[0] != "exit")
        println("Parse error")
    }
}
