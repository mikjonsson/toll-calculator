package tollcalculator

import (
	"testing"

	"github.com/google/uuid"
	"github.com/mikjonsson/toll-calculator/Golang/tollcalculator"
)

func TestCarIsNotTollFree(t *testing.T) {
	car := tollcalculator.Vehicle{
		ID:          uuid.New(),
		VehicleType: tollcalculator.Car,
	}

	if tollcalculator.IsTollFreeVehicle(car) {
		t.Errorf("Cars should not be toll free")
	}
}

func TestUnknownVehicleIsTollFree(t *testing.T) {
	vehicle := tollcalculator.Vehicle{
		ID:          uuid.New(),
		VehicleType: tollcalculator.UnknownVehicle,
	}

	if !tollcalculator.IsTollFreeVehicle(vehicle) {
		t.Errorf("UnknownVehicle should be toll free")
	}
}

func TestMotorbikeVehicleIsTollFree(t *testing.T) {
	vehicle := tollcalculator.Vehicle{
		ID:          uuid.New(),
		VehicleType: tollcalculator.Motorbike,
	}

	if !tollcalculator.IsTollFreeVehicle(vehicle) {
		t.Errorf("Motorbike should be toll free")
	}
}

func TestTractorVehicleIsTollFree(t *testing.T) {
	vehicle := tollcalculator.Vehicle{
		ID:          uuid.New(),
		VehicleType: tollcalculator.Tractor,
	}

	if !tollcalculator.IsTollFreeVehicle(vehicle) {
		t.Errorf("Tractor should be toll free")
	}
}

func TestEmergencyVehicleIsTollFree(t *testing.T) {
	vehicle := tollcalculator.Vehicle{
		ID:          uuid.New(),
		VehicleType: tollcalculator.Emergency,
	}

	if !tollcalculator.IsTollFreeVehicle(vehicle) {
		t.Errorf("Emergency should be toll free")
	}
}

func TestDiplomatbikeVehicleIsTollFree(t *testing.T) {
	vehicle := tollcalculator.Vehicle{
		ID:          uuid.New(),
		VehicleType: tollcalculator.Diplomat,
	}

	if !tollcalculator.IsTollFreeVehicle(vehicle) {
		t.Errorf("Diplomat should be toll free")
	}
}

func TestForeignVehicleIsTollFree(t *testing.T) {
	vehicle := tollcalculator.Vehicle{
		ID:          uuid.New(),
		VehicleType: tollcalculator.Foreign,
	}

	if !tollcalculator.IsTollFreeVehicle(vehicle) {
		t.Errorf("Foreign should be toll free")
	}
}

func TestMilitaryVehicleIsTollFree(t *testing.T) {
	vehicle := tollcalculator.Vehicle{
		ID:          uuid.New(),
		VehicleType: tollcalculator.Military,
	}

	if !tollcalculator.IsTollFreeVehicle(vehicle) {
		t.Errorf("Military should be toll free")
	}
}

func TestVehiclesHaveUniqueIDs(t *testing.T) {
	vehicle1 := tollcalculator.Vehicle{
		ID: uuid.New(),
	}

	vehicle2 := tollcalculator.Vehicle{
		ID: uuid.New(),
	}

	if vehicle1.ID == vehicle2.ID {
		t.Errorf("IDs match and they shouldn't")
	}
}
