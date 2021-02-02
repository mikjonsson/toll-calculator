package rules

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import io.kotest.property.forAll
import vehicle.Vehicle
import java.time.DayOfWeek


class TollRulesTest : StringSpec({
    val tollRules = TollRules()

    // TODO: I couldn't find a smooth way of using Property Based testing for the vehicle types... Investigate further
    "Cars should be billable" {
        tollRules.isBillableVehicle(Vehicle.Car()) shouldBe true
    }
    "UnknownVehicle should not be billable" {
        tollRules.isBillableVehicle(Vehicle.UnknownVehicle()) shouldNotBe true
    }
    "Motorbike should not be billable" {
        tollRules.isBillableVehicle(Vehicle.Motorbike()) shouldNotBe true
    }
    "Tractor should not be billable" {
        tollRules.isBillableVehicle(Vehicle.Tractor()) shouldNotBe true
    }
    "Emergency should not be billable" {
        tollRules.isBillableVehicle(Vehicle.Emergency()) shouldNotBe true
    }
    "Diplomat should not be billable" {
        tollRules.isBillableVehicle(Vehicle.Diplomat()) shouldNotBe true
    }
    "Foreign should not be billable" {
        tollRules.isBillableVehicle(Vehicle.Foreign()) shouldNotBe true
    }
    "Military should not be billable" {
        tollRules.isBillableVehicle(Vehicle.Military()) shouldNotBe true
    }

    "Weekends should be free" {
        forAll(Arb.localDateTime(2018, 2018).filter {
            it.dayOfWeek == DayOfWeek.SATURDAY || it.dayOfWeek == DayOfWeek.SUNDAY
        }) { date -> tollRules.isTollFreeDate(date) }
    }

    "Holidays should be free" {
        forAll(Arb.localDateTime(2018, 2018).filter {
            it.dayOfYear in tollRules.HOLIDAYS_2018
        }) { date -> tollRules.isTollFreeDate(date) }
    }

    // TODO: Tests for feeForDateTime goes here...

})