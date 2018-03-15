package com.evolvetechnology;

import java.time.LocalTime;
import java.util.Collection;
import java.util.LinkedList;

/**
 * This class manages linear intervals during a day with associated costs
 * <p>
 * I.e: 6:00 - 7:00 = 8
 * 7:00 - 9:30 = 10
 * 9:30 - 14:30 = 21
 */
public class TimeCostManager {
  private Collection<CostInterval> costIntervals = new LinkedList<>();

  /**
   * Method to find interval for time and associated cost
   *
   * @param localTime
   * @return cost for interval
   */
  public int getCostFor(LocalTime localTime) {
    for (CostInterval costInterval : costIntervals) {
      if (isInTimeInterval(localTime, costInterval.getStart(), costInterval.getEnd())) {
        return costInterval.getCost();
      }
    }
    return 0;
  }

  public void addAll(Collection<CostInterval> costIntervals) {
    this.costIntervals.addAll(costIntervals);
  }


  public void addIntervalCost(LocalTime start, LocalTime end, int cost) {
    verifyInterval(start, end);
    costIntervals.add(new CostInterval(start, end, cost));
  }

  private boolean isInTimeInterval(LocalTime localTime, LocalTime start, LocalTime end) {
    return localTime.compareTo(start) >= 0 && localTime.compareTo(end) < 0;
  }

  private void verifyInterval(LocalTime start, LocalTime end) {
    verifyLogicInterval(start, end);
    verifyNoOverlappingIntervals(start, end);
  }

  private void verifyLogicInterval(LocalTime start, LocalTime end) {
    if (start.compareTo(end) > 0) {
      throw new IllegalArgumentException("Start of interval (" + start + ") is later than end (" + end + ")");
    }
  }

  private void verifyNoOverlappingIntervals(LocalTime start, LocalTime end) {
    for (CostInterval costInterval : costIntervals) {
      if (isOverLapping(start, end, costInterval.getStart(), costInterval.getEnd())) {
        throw new IllegalArgumentException("Overlapping interval (" + start + " -- " + end + ") with existing (" +
                costInterval.getStart() + " -- " + costInterval.getEnd() + ")");
      }
    }
  }

  private boolean isOverLapping(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
    boolean endIsOverlapping = start1.compareTo(start2) < 0 && end1.compareTo(start2) > 0;
    boolean startIsOverlapping = start1.compareTo(end2) < 0 && end1.compareTo(end2) > 0;
    return endIsOverlapping || startIsOverlapping;
  }

}