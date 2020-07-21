package tollcalculator

import (
	"testing"
	"time"

	"github.com/google/uuid"
	"github.com/mikjonsson/toll-calculator/Golang/tollcalculator"
)

// TODO : More tests
// TODO : We may want to assert that all fees for any given time is correct

// Fees for specific Time
func TestNoFeeForOffHours(t *testing.T) {
	tollFreeTime, _ := time.Parse(time.RFC3339, "2018-01-05T05:59:00+00:00")
	if fee := tollcalculator.FeeForTime(tollFreeTime); fee != 0 {
		t.Errorf("%s should be free but was %f", tollFreeTime, fee)
	}

	tollFreeTime, _ = time.Parse(time.RFC3339, "2018-01-05T18:30:00+00:00")
	if fee := tollcalculator.FeeForTime(tollFreeTime); fee != 0 {
		t.Errorf("%s should be free but was %f", tollFreeTime, fee)
	}
}
func TestFeeIs8(t *testing.T) {
	tollTime, _ := time.Parse(time.RFC3339, "2018-01-05T06:19:00+00:00")
	if fee := tollcalculator.FeeForTime(tollTime); fee != 8 {
		t.Errorf("%s should be free but was %f", tollTime, fee)
	}
	tollTime, _ = time.Parse(time.RFC3339, "2018-01-05T13:30:00+00:00")
	if fee := tollcalculator.FeeForTime(tollTime); fee != 8 {
		t.Errorf("%s should be free but was %f", tollTime, fee)
	}
}

// Fees related to recently charged
func TestCharged8WithinMaxChargeFrequencyMinutes(t *testing.T) {
	lastTollTime, _ := time.Parse(time.RFC3339, "2018-01-05T06:09:00+00:00")

	car := tollcalculator.Vehicle{
		ID:              uuid.New(),
		VehicleType:     tollcalculator.Car,
		LastCharged:     lastTollTime,
		LastFee:         8,
		CurrentDailyFee: 8,
	}

	newTime := lastTollTime.Add(time.Minute * 5) // within same fee range within MaxChargeFrequencyMinutes
	_ = tollcalculator.FeeForVehicle(&car, newTime)
	if car.CurrentDailyFee != 8 {
		t.Errorf("Daily fee should be %f but is %f", 8.0, car.CurrentDailyFee)
	}

	newTime = lastTollTime.Add(time.Minute * 55) // 18 SEK fee range within MaxChargeFrequencyMinutes
	_ = tollcalculator.FeeForVehicle(&car, newTime)
	if car.CurrentDailyFee != 18 {
		t.Errorf("Daily fee should be %f but is %f", 18.0, car.CurrentDailyFee)
	}
}
func TestCharged8OutsideMaxChargeFrequencyMinutes(t *testing.T) {
	lastTollTime, _ := time.Parse(time.RFC3339, "2018-01-05T06:09:00+00:00")

	car := tollcalculator.Vehicle{
		ID:              uuid.New(),
		VehicleType:     tollcalculator.Car,
		LastCharged:     lastTollTime,
		LastFee:         8,
		CurrentDailyFee: 8,
	}

	newTime := lastTollTime.Add(time.Minute * 65) // 18 SEK fee range outside MaxChargeFrequencyMinutes
	_ = tollcalculator.FeeForVehicle(&car, newTime)
	if car.CurrentDailyFee != 26 {
		t.Errorf("Daily fee should be %f but is %f", 26.0, car.CurrentDailyFee)
	}
}

// Fees related to MaxDailyFee
func TestMaxDailyFeeReached(t *testing.T) {
	lastTollTime, _ := time.Parse(time.RFC3339, "2018-01-05T06:09:00+00:00")

	car := tollcalculator.Vehicle{
		ID:              uuid.New(),
		VehicleType:     tollcalculator.Car,
		LastCharged:     lastTollTime,
		LastFee:         8,
		CurrentDailyFee: 60,
	}

	newTime := lastTollTime.Add(time.Minute * 65) // 18 SEK fee range outside MaxChargeFrequencyMinutes
	_ = tollcalculator.FeeForVehicle(&car, newTime)
	if car.CurrentDailyFee != tollcalculator.MaxDailyFee {
		t.Errorf("Daily fee should be %f but is %f",
			tollcalculator.MaxDailyFee,
			car.CurrentDailyFee)
	}

	car.CurrentDailyFee = 70
	_ = tollcalculator.FeeForVehicle(&car, newTime)
	if car.CurrentDailyFee != tollcalculator.MaxDailyFee {
		t.Errorf("Daily fee should be %f but is %f",
			tollcalculator.MaxDailyFee,
			car.CurrentDailyFee)
	}
}
func TestScenarioToMax(t *testing.T) {
	lastTollTime, _ := time.Parse(time.RFC3339, "2018-01-05T06:05:00+00:00")

	car := tollcalculator.Vehicle{
		ID:              uuid.New(),
		VehicleType:     tollcalculator.Car,
		LastCharged:     lastTollTime,
		LastFee:         8,
		CurrentDailyFee: 8,
	}

	newTime := lastTollTime.Add(time.Minute * 5)    // within same fee range within MaxChargeFrequencyMinutes
	_ = tollcalculator.FeeForVehicle(&car, newTime) // Still 8 CurrentDailyFee
	if car.CurrentDailyFee != 8 {
		t.Errorf("Daily fee should be %d but is %f", 8, car.CurrentDailyFee)
	}

	newTime = lastTollTime.Add(time.Minute * 55)    // 18 SEK fee range within MaxChargeFrequencyMinutes
	_ = tollcalculator.FeeForVehicle(&car, newTime) // 18 CurrentDailyFee
	if car.CurrentDailyFee != 18 {
		t.Errorf("Daily fee should be %d but is %f", 18, car.CurrentDailyFee)
	}

	newTime = lastTollTime.Add(time.Minute * 65)    // 18 SEK fee range
	_ = tollcalculator.FeeForVehicle(&car, newTime) // 36 CurrentDailyFee
	if car.CurrentDailyFee != 36 {
		t.Errorf("Daily fee should be %d but is %f", 36, car.CurrentDailyFee)
	}

	newTime = newTime.Add(time.Minute * 65)         // 13 SEK fee range
	_ = tollcalculator.FeeForVehicle(&car, newTime) // 49 CurrentDailyFee
	if car.CurrentDailyFee != 49 {
		t.Errorf("Daily fee should be %d but is %f", 49, car.CurrentDailyFee)
	}

	newTime = newTime.Add(time.Minute * 65)         // 8 SEK fee range
	_ = tollcalculator.FeeForVehicle(&car, newTime) // 57 CurrentDailyFee
	if car.CurrentDailyFee != 57 {
		t.Errorf("Daily fee should be %d but is %f", 57, car.CurrentDailyFee)
	}

	newTime = newTime.Add(time.Minute * 65)         // 8 SEK fee range
	_ = tollcalculator.FeeForVehicle(&car, newTime) // 65 (60) CurrentDailyFee
	if car.CurrentDailyFee != tollcalculator.MaxDailyFee {
		t.Errorf("Daily fee should be %f but is %f",
			tollcalculator.MaxDailyFee,
			car.CurrentDailyFee)
	}
}
