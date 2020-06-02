package converter

import java.util.*
import kotlin.system.exitProcess

enum class Unit(val baseUnit: Double, private val plural: String) {
    GRAM(1.0, "grams"),
    KILOGRAM(1000.0, "kilograms"),
    MILLIGRAM(0.001, "milligrams"),
    POUND(453.592, "pounds"),
    OUNCE(28.3495, "ounces"),
    METER(1.0, "meters"),
    KILOMETER(1000.0, "kilometers"),
    CENTIMETER(0.01, "centimeters"),
    MILLIMETER(0.001, "millimeters"),
    MILE(1609.35, "miles"),
    YARD(0.9144, "yards"),
    FOOT(0.3048, "feet"),
    INCH(0.0254, "inches"),
    NULL(0.0, "");

    fun getName(n: Double): String {
        return if (n == 1.0) name.toLowerCase() else plural
    }

    companion object {
        fun getUnit(unit: String): Unit {
            return when (unit) {
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

private const val EOP = "exit"

fun main() {
    val scanner = Scanner(System.`in`)
    do {
        println("Enter what you want to convert (or exit): ")
        val input = scanner.nextLine()
        val args = input.split(" ")
        if (args.size == 4) {
            var n = args[0].toDouble()
            var inUnit = Unit.getUnit(args[1].toLowerCase())
            var outUnit = Unit.getUnit(args[3].toLowerCase())
            val converted = n * inUnit.baseUnit / outUnit.baseUnit
            println("$n ${inUnit.getName(n)} is $converted ${outUnit.getName(converted)}")
        }
    } while (input != EOP)
    exitProcess(0)
}
