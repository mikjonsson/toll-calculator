package vehicle

import java.time.LocalDateTime
import java.util.UUID

sealed class Vehicle(_lastBilledEvent: LocalDateTime, _currentDailyFee: Int) {
    var lastBilled = _lastBilledEvent
    private set
    var currentDailyFee = _currentDailyFee
    private set

    fun setLastBilled(dateTime: LocalDateTime) {
        lastBilled = dateTime
    }

    fun updateCurrentDailyFee(fee: Int) {
        currentDailyFee += fee
    }

    data class UnknownVehicle(val id: UUID = UUID.randomUUID()): Vehicle(LocalDateTime.MIN, 0)
    data class Car(val id: UUID = UUID.randomUUID()): Vehicle(LocalDateTime.MIN, 0)
    data class Motorbike(val id: UUID = UUID.randomUUID()): Vehicle(LocalDateTime.MIN, 0)
    data class Tractor(val id: UUID = UUID.randomUUID()): Vehicle(LocalDateTime.MIN, 0)
    data class Emergency(val id: UUID = UUID.randomUUID()): Vehicle(LocalDateTime.MIN, 0)
    data class Diplomat(val id: UUID = UUID.randomUUID()): Vehicle(LocalDateTime.MIN, 0)
    data class Foreign(val id: UUID = UUID.randomUUID()): Vehicle(LocalDateTime.MIN, 0)
    data class Military(val id: UUID = UUID.randomUUID()): Vehicle(LocalDateTime.MIN, 0)
}

