
import MeasureType.*
import java.io.*
import java.util.*


enum class MeasureType {
    Length, Weight;

    fun of(short: String,
           normal: String,
           plural: String,
           multiplier: Double) = Measure(this, short, normal, plural, multiplier)
}

data class Measure(
        val type: MeasureType,
        val short: String,
        val normal: String,
        val plural: String,
        val multiplier: Double
) {
    fun name(amount: Double) = if (amount == 1.0) normal else plural
}


fun solveAuthors(sin: Scanner, sout: PrintStream) {

    val measures = listOf(
            Length.of("m", "meter", "meters", 1.0),
            Length.of("km", "kilometer", "kilometers", 1000.0),
            Length.of("cm", "centimeter", "centimeters", 0.01),
            Length.of("mm", "millimeter", "millimeters", 0.001),
            Length.of("mi", "mile", "miles", 1609.35),
            Length.of("yd", "yard", "yards", 0.9144),
            Length.of("ft", "foot", "feet", 0.3048),
            Length.of("in", "inch", "inches", 0.0254),

            Weight.of("g", "gram", "grams", 1.0),
            Weight.of("kg", "kilogram", "kilograms", 1000.0),
            Weight.of("mg", "milligram", "milligrams", 0.001),
            Weight.of("lb", "pound", "pounds", 453.592),
            Weight.of("oz", "ounce", "ounces", 28.3495)
    )

    val namesToMeasures = measures
            .flatMap { m ->
                listOf(m.short, m.normal, m.plural).map { name -> name to m }
            }.toMap()


    while (true) {
        sout.print("Enter what you want to convert (or exit): ")
        val valueStr = sin.next()
        if (valueStr == "exit") {
            break
        }

        val value = valueStr.toDouble()

        // read measures:
        val m1Str = sin.next().toLowerCase()
        val m1 = namesToMeasures[m1Str]!!
        sin.next() // unknown word like to or in
        val m2Str = sin.next().toLowerCase()
        val m2 = namesToMeasures[m2Str]!!

        val converted = value * m1.multiplier / m2.multiplier
        sout.println("$value ${m1.name(value)} is $converted ${m2.name(converted)}")
    }
}


fun main(args: Array<String>) {
    solveAuthors(Scanner(System.`in`), System.out)
}
