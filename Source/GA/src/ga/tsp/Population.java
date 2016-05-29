package ga.tsp;

import java.util.ArrayList;
import java.util.List;

public class Population {

  // private class TourFitnessComparator implements Comparator<Tour> {
  //
  // @Override
  // public int compare(final Tour t1, final Tour t2) {
  // if (t1.getFitnessValue() > t2.getFitnessValue()) {
  // return 1;
  // } else if (t1.getFitnessValue() < t2.getFitnessValue()) {
  // return -1;
  // } else {
  // return 0;
  // }
  // }
  // }

  private final List<Tour> tours;

  // private final PriorityQueue<Tour> queue;

  public Population(final boolean init) {
    this.tours = new ArrayList<Tour>(Util.POPULATION_SIZE);
    // this.queue = new PriorityQueue<>(new TourFitnessComparator());

    if (init) {
      for (int i = 0; i < Util.POPULATION_SIZE; i++) {
        final Tour tour = new Tour(false);
        this.tours.add(tour);
        // this.queue.add(tour);
      }
    }
  }

  public void addEliteTours(final List<Tour> elites) {
    this.tours.addAll(elites);
  }

  public Tour getBest() {
    Tour best = this.tours.get(0);
    for (int i = 1; i < this.tours.size(); i++) {
      if (this.tours.get(i).getFitnessValue() > best.getFitnessValue()) {
        best = this.tours.get(i);
      }
    }
    return best;
  }

  // public List<Tour> getBestOnes() {
  // final List<Tour> bestOnes = new ArrayList<>(Util.ELITISM_COUNT);
  // for (int i = 0; i < Util.ELITISM_COUNT; i++) {
  // bestOnes.add(this.queue.remove());
  // }
  // return bestOnes;
  // }

  public Tour getTour(final int i) {
    return this.tours.get(i);
  }

  public void setTour(final int index, final Tour tour) {
    this.tours.add(index, tour);
  }
}
