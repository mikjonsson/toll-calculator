package tollcalculator

import (
	"reflect"
	"testing"

	"github.com/mikjonsson/toll-calculator/Golang/tollcalculator"
)

// Is this test realyl useful?
func Test2018Holidays(t *testing.T) {
	var holidays2018 = tollcalculator.Holidays{
		[]int{1, 6, 89, 91, 92, 121, 130, 140, 157, 174, 307, 359, 360},
	}

	holidays := tollcalculator.HolidaysForYear(2018)

	if !reflect.DeepEqual(holidays, holidays2018) {
		t.Errorf("Holliday missmatch")
	}
}

func TestUnknownYearReturnsEmptyHolidays(t *testing.T) {
	holidays := tollcalculator.HolidaysForYear(1975)

	if len(holidays.DaysOfYear) != 0 {
		t.Errorf("Expected holidays with length 0 but got length %d", len(holidays.DaysOfYear))
	}
}

func Test2018Had13Holidays(t *testing.T) {
	holidays := tollcalculator.HolidaysForYear(2018)

	if len(holidays.DaysOfYear) != 13 {
		t.Errorf("Expected holidays with length 13 but got length %d", len(holidays.DaysOfYear))
	}
}
