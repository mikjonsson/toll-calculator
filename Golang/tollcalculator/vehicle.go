package tollcalculator

import (
	"time"

	"github.com/google/uuid"
)

// VehicleType identifier
type VehicleType int

// Vehicle types
const (
	UnknownVehicle VehicleType = -1
	Car            VehicleType = iota
	Motorbike
	Tractor
	Emergency
	Diplomat
	Foreign
	Military
)

// Vehicle defintion
type Vehicle struct {
	id              uuid.UUID
	vehicleType     VehicleType
	tollFree        bool
	lastCharged     time.Time
	currentDailyFee int
}

func isTollFreeVehicleType(vehicleType VehicleType) bool {
	return vehicleType != Car
}
