import rules.TollRules
import vehicle.Vehicle
import java.time.LocalDateTime

class TollCalculator {

    private val tollRules = TollRules()

    private fun shouldChargeVehicle(vehicle: Vehicle, time: LocalDateTime): Boolean {
        if (!tollRules.isBillableVehicle(vehicle)) {
            return false
        }
        if (vehicle.currentDailyFee >= tollRules.MAX_DAILY_FEE) {
            return false
        }
        return time > vehicle.lastBilled.plusMinutes(tollRules.MAX_CHARGE_FREQUENCY.toLong())
    }

    fun calculateDailyFee(vehicle: Vehicle, time: LocalDateTime): Int {
        if (!shouldChargeVehicle(vehicle, time)) {
            return vehicle.currentDailyFee
        }

        val fee = tollRules.feeForDateTime(time)
        val newDailyFee = vehicle.currentDailyFee + fee

        return if (newDailyFee > tollRules.MAX_DAILY_FEE) {
            tollRules.MAX_DAILY_FEE
        } else {
            newDailyFee
        }
    }

}