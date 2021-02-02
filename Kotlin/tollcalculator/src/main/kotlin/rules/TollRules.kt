package rules

import vehicle.Vehicle
import java.time.DayOfWeek
import java.time.LocalDateTime

class TollRules {

    val HOLIDAYS_2018 = intArrayOf(1, 6, 89, 91, 92, 121, 130, 140, 157, 174, 307, 359, 360)
    val MAX_DAILY_FEE = 60
    val MAX_CHARGE_FREQUENCY = 60

    fun isBillableVehicle(vehicle: Vehicle): Boolean {
        return when(vehicle) {
            is Vehicle.Car -> true
            else -> false
        }
    }

    fun isTollFreeDate(date: LocalDateTime): Boolean {
        return when (date.dayOfWeek) {
            DayOfWeek.SATURDAY, DayOfWeek.SUNDAY -> true
            else -> isHoliday(date)
        }
    }

    private fun isHoliday(date: LocalDateTime): Boolean {
        if (date.year != 2018) {
            TODO("Support for other years than 2018 is to be implemented, ${date.year} is unsupported!")
        }

        return date.dayOfYear in HOLIDAYS_2018
    }

    fun feeForDateTime(dateTime: LocalDateTime): Int {
        if (isTollFreeDate(dateTime)) {
            return 0
        }
        val hour = dateTime.toLocalTime().hour
        val minute = dateTime.toLocalTime().minute
        return when (hour) {
            6 -> if(minute < 30) 8 else 13
            7 -> 18
            8 -> if(minute < 30) 13 else 8
            in 9..14 -> 8
            15 -> if(minute < 30) 13 else 18
            16 -> 18
            17 -> 13
            18 -> if(minute < 30) 8 else 0
            else -> 0
        }
    }

}
