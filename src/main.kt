import java.util.*

enum class MeasureGroup {
    Length, Weight, Temperature, Unknown
}

enum class Measure {
    Meter, Kilometer, Centimeter, Millimeter, Mile, Yard, Foot, Inch,
    Gram, Kilogram, Milligram, Pound, Ounce,
    Kelvin, Celsius, Fahrenheit, Unknown;

    fun getGroup() = when (this) {
        Meter, Kilometer, Centimeter, Millimeter, Mile, Yard, Foot, Inch -> MeasureGroup.Length
        Gram, Kilogram, Milligram, Pound, Ounce -> MeasureGroup.Weight
        Kelvin, Celsius, Fahrenheit -> MeasureGroup.Temperature
        Unknown -> MeasureGroup.Unknown
    }

    fun getCorrectName(value: Double) = when(this) {
        Meter -> if (value == 1.0) "meter" else "meters"
        Kilometer -> if (value == 1.0) "kilometer" else "kilometers"
        Centimeter -> if (value == 1.0) "centimeter" else "centimeters"
        Millimeter -> if (value == 1.0) "millimeter" else "millimeters"
        Mile -> if (value == 1.0) "mile" else "miles"
        Yard -> if (value == 1.0) "yard" else "yards"
        Foot -> if (value == 1.0) "foot" else "feet"
        Inch -> if (value == 1.0) "inch" else "inches"
        Gram -> if (value == 1.0) "gram" else "grams"
        Kilogram -> if (value == 1.0) "kilogram" else "kilograms"
        Milligram -> if (value == 1.0) "milligram" else "milligrams"
        Pound -> if (value == 1.0) "pound" else "pounds"
        Ounce -> if (value == 1.0) "ounce" else "ounces"
        Kelvin -> if (value == 1.0) "Kelvin" else "Kelvins"
        Celsius -> if (value == 1.0) "degree Celsius" else "degrees Celsius"
        Fahrenheit -> if (value == 1.0) "degree Fahrenheit" else "degrees Fahrenheit"
        Unknown -> "???"
    }

    fun getCoefficient() = when(this) {
        Meter -> 1.0
        Kilometer -> 1000.0
        Centimeter -> 0.01
        Millimeter -> 0.001
        Mile -> 1609.35
        Yard -> 0.9144
        Foot -> 0.3048
        Inch -> 0.0254
        Gram -> 1.0
        Kilogram -> 1000.0
        Milligram -> 0.001
        Pound -> 453.592
        Ounce -> 28.3495
        else -> 0.0
    }
}

fun getMeasure(scanner: Scanner): Measure {

    var measure = scanner.next()
    if (measure.toLowerCase() == "degree" || measure.toLowerCase() == "degrees") {
        measure += " " + scanner.next()
    }

    return when(measure.toLowerCase()) {
        "meters", "meter", "m" -> Measure.Meter
        "kilometers", "kilometer", "km" ->Measure.Kilometer
        "centimeters", "centimeter", "cm" -> Measure.Centimeter
        "millimeters", "millimeter", "mm" -> Measure.Millimeter
        "miles", "mile", "mi" -> Measure.Mile
        "yards", "yard", "yd" -> Measure.Yard
        "feet", "foot", "ft" -> Measure.Foot
        "inches", "inch", "in" -> Measure.Inch
        "grams", "gram", "g" -> Measure.Gram
        "kilograms", "kilogram", "kg" -> Measure.Kilogram
        "milligrams", "milligram", "mg" -> Measure.Milligram
        "pounds", "pound", "lb" -> Measure.Pound
        "ounces", "ounce", "oz" -> Measure.Ounce
        "kelvins", "kelvin", "k" -> Measure.Kelvin
        "degrees celsius", "degree celsius", "celsius", "dc", "c" -> Measure.Celsius
        "degrees fahrenheit", "degree fahrenheit", "fahrenheit", "df", "f" -> Measure.Fahrenheit
        else -> Measure.Unknown
    }
}

fun calculate(value: Double, measureFrom: Measure, measureTo: Measure): Double {
    return if (measureFrom == measureTo) value
    else when(Pair(measureFrom, measureTo)) {
        Pair(Measure.Fahrenheit, Measure.Celsius) -> (value - 32) * 5 / 9
        Pair(Measure.Celsius, Measure.Fahrenheit) -> value * 9 / 5 + 32
        Pair(Measure.Celsius, Measure.Kelvin) -> value + 273.15
        Pair(Measure.Kelvin, Measure.Celsius) -> value - 273.15
        Pair(Measure.Kelvin, Measure.Fahrenheit) -> value * 9 / 5 - 459.67
        Pair(Measure.Fahrenheit, Measure.Kelvin) -> (value + 459.67) * 5 / 9
        else -> value * measureFrom.getCoefficient() / measureTo.getCoefficient()
    }
}

fun main(args: Array<String>) {

    val scanner = Scanner(System.`in`)

    while (true) {

        print("Enter what you want to convert (or exit): ")

        val strValue = scanner.next()
        if (strValue == "exit") {
            break
        }

        val value = strValue.toDouble()
        val measureFrom = getMeasure(scanner)
        scanner.next()
        val measureTo = getMeasure(scanner)

        if (measureTo.getGroup() != measureFrom.getGroup() || measureTo == Measure.Unknown) {
            println("Conversion from ${measureFrom.getCorrectName(2.0)} " +
                    "to ${measureTo.getCorrectName(2.0)} is impossible.")
            continue
        }
        if (measureFrom.getGroup() != MeasureGroup.Temperature && value < 0) {
            println("${measureFrom.getGroup().name} shouldn't be negative.")
            continue
        }


        val result = calculate(value, measureFrom, measureTo)
        println("$value ${measureFrom.getCorrectName(value)} is $result ${measureTo.getCorrectName(result)}")
    }
}
