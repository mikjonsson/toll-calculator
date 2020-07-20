package tollcalculator

import "time"

// SEK - Swedish Kronor currency
type SEK float32

const (
	// MaxDailyFee - The most to be charged in a day in SEK
	MaxDailyFee SEK = 60
	// MaxChargeFrequencytMinutes - Minimum minutes between new charges
	MaxChargeFrequencytMinutes = 60
)

// FeeForTime returns fee for given time
func FeeForTime(t time.Time) SEK {
	if t.Hour() == 6 && t.Minute() >= 0 && t.Minute() <= 29 {
		return 8
	} else if t.Hour() == 6 && t.Minute() >= 30 && t.Minute() <= 59 {
		return 13
	} else if t.Hour() == 7 && t.Minute() >= 0 && t.Minute() <= 59 {
		return 18
	} else if t.Hour() == 8 && t.Minute() >= 0 && t.Minute() <= 29 {
		return 13
	} else if t.Hour() >= 8 && t.Hour() <= 14 && t.Minute() >= 30 && t.Minute() <= 59 {
		return 8
	} else if t.Hour() == 15 && t.Minute() >= 0 && t.Minute() <= 29 {
		return 13
	} else if t.Hour() == 15 && t.Minute() >= 0 || t.Hour() == 16 && t.Minute() <= 59 {
		return 18
	} else if t.Hour() == 17 && t.Minute() >= 0 && t.Minute() <= 59 {
		return 13
	} else if t.Hour() == 18 && t.Minute() >= 0 && t.Minute() <= 29 {
		return 8
	} else {
		return 0
	}
}
