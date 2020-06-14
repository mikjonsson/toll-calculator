package tollcalculator

import (
	"github.com/sirupsen/logrus"
)

var consoleLogger = logrus.New()

func init() {
	consoleLogger.SetFormatter(&logrus.TextFormatter{
		FullTimestamp: true,
	})

	consoleLogger.SetReportCaller(true)
}
