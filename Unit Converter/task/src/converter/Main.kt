package converter

import java.util.*

enum class UNIT(val meters: Double, private val plural: String) {
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
        fun getUnit(unit: String): UNIT {
            return when (unit) {
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

fun main() {

    println("Enter a number and a measure of length: ")
    val scanner = Scanner(System.`in`)
    val n = scanner.nextDouble()
    val unit = scanner.next().toLowerCase()
    val newUnit = UNIT.getUnit(unit)
    val newValue = n * newUnit.meters

    println("$n ${newUnit.getName(n)} is $newValue ${UNIT.METER.getName(newValue)}")
}
