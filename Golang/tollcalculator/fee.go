package tollcalculator

import (
	"time"
)

// SEK - Swedish Kronor currency
type SEK float32

const (
	// MaxDailyFee is the most to be charged in a day in SEK
	MaxDailyFee SEK = 60
	// MaxChargeFrequencyMinutes is the minimum minutes between new charges
	MaxChargeFrequencyMinutes = 60
)

// FeeForVehicle returns current fee for given vehicle after, if applicable, adding new fee
func FeeForVehicle(v *Vehicle, t time.Time) SEK {
	if IsTollFreeVehicle(*v) || isTollFreeDay(t) {
		return 0
	}

	currentFee := FeeForTime(t)

	if t.Sub(v.LastCharged).Minutes() <= MaxChargeFrequencyMinutes {
		if v.LastFee < currentFee {
			v.CurrentDailyFee += (currentFee - v.LastFee)
			v.LastFee = currentFee
		}
	} else {
		v.LastCharged = t
		v.LastFee = currentFee
		v.CurrentDailyFee += currentFee
	}

	if v.CurrentDailyFee > MaxDailyFee {
		v.CurrentDailyFee = MaxDailyFee
	}

	return v.CurrentDailyFee
}

// FeeForTime returns fee for given time
func FeeForTime(t time.Time) SEK {
	if isTollFreeDay(t) {
		return 0
	}

	switch hour, minute := t.Hour(), t.Minute(); {
	case hour == 6:
		if minute < 30 {
			return 8
		}
		return 13
	case hour == 7:
		return 18
	case hour == 8:
		if minute < 30 {
			return 13
		}
		return 8
	case hour >= 9 && hour <= 14:
		return 8
	case hour == 15:
		if minute < 30 {
			return 13
		}
		return 18
	case hour == 16:
		return 18
	case hour == 17:
		return 13
	case hour == 18:
		if minute < 30 {
			return 8
		}
		return 0

	default:
		return 0
	}
}

func isWeekend(day time.Weekday) bool {
	if day == 0 || day == 6 {
		return true
	}
	return false
}

func isTollFreeDay(t time.Time) bool {
	if isWeekend(t.Weekday()) {
		return true
	}

	if IsHolliday(t.YearDay(), t.Year()) {
		return true
	}
	return false
}
