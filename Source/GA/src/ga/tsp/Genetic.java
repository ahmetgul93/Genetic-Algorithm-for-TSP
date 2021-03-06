package ga.tsp;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import ga.tsp.CityManager.City;
import ga.tsp.exception.InvalidSelectionStateException;

public class Genetic {

  private Population pop;

  private void bubbleSort(final Tour[] tours) {
    boolean flag = true;
    Tour temp = Tour.createEmptyTour();
    while (flag) {
      flag = false;
      for (int i = 0; i < tours.length - 1; i++) {
        if (tours[i].getFitnessValue() > tours[i + 1].getFitnessValue()) {
          temp = tours[i];
          tours[i] = tours[i + 1];
          tours[i + 1] = temp;
          flag = true;
        }
      }
    }
  }

  private int[] createSegment(final int bound) {
    final Random rand = new Random();
    int cutPoint1 = rand.nextInt(bound);
    int cutPoint2 = rand.nextInt(bound);
    while (cutPoint2 == cutPoint1) {
      cutPoint2 = rand.nextInt(bound);
    }
    if (cutPoint1 > cutPoint2) { // make sure cutPoint1 < cutPoint2
      final int temp = cutPoint1;
      cutPoint1 = cutPoint2;
      cutPoint2 = temp;
    }

    return new int[] {cutPoint1, cutPoint2};
  }

  /**
   * Cycle crossover, for details:
   * http://www.rubicite.com/Tutorials/GeneticAlgorithms/CrossoverOperators/CycleCrossoverOperator.
   * aspx
   *
   * @param parent1
   * @param parent2
   * @return
   */
  private Tour cxCrossover(final Tour parent1, final Tour parent2) {
    final Tour child = Tour.createEmptyTour();
    int index = 0;
    while (!child.contains(parent2.getCity(index))) {
      child.setCity(index, parent1.getCity(index));
      final int position = this.getPosition(parent1, parent2.getCity(index));
      child.setCity(position, parent2.getCity(index));
      index = position;
    }

    for (int i = 0; i < child.getSize(); i++) {
      if (child.getCity(i).getX() == 0.0 && child.getCity(i).getY() == 0.0) {
        child.setCity(i, parent2.getCity(i));
      }
    }

    return child;
  }

  private Population evalPopulation() {
    final Population newPop = new Population(false);
    if (Util.ELITISM) {
      // newPop.addEliteTours(this.pop.getBestOnes());
      newPop.addEliteTours(Arrays.asList(this.pop.getBest()));
    }

    for (int i = Util.ELITISM_COUNT; i < Util.POPULATION_SIZE; i += 2) {
      Tour parent1 = null, parent2 = null, child1 = null, child2 = null;

      try {
        parent1 = this.wheelRouletteSelection();
        parent2 = this.wheelRouletteSelection();
      } catch (final InvalidSelectionStateException e) {
        e.printStackTrace();
      }

      if (Util.CROSSOVER_TYPE.toUpperCase().equals("CX")) {
        child1 = this.cxCrossover(parent1, parent2);
        child2 = this.cxCrossover(parent2, parent1);
      } else if (Util.CROSSOVER_TYPE.toUpperCase().equals("PMX")) {
        child1 = this.pmxCrossover(parent1, parent2);
        child2 = this.pmxCrossover(parent2, parent1);
      }

      this.mutate(child1);
      this.mutate(child2);

      final Tour[] best = this.runSteadyState(parent1, parent2, child1, child2);

      newPop.addTour(i, best[0]);
      newPop.addTour(i + 1, best[1]);
    }

    return newPop;
  }

  private int getPosition(final Tour parent1, final City city) {
    int position = 0;
    for (int i = 0; i < parent1.getSize(); i++) {
      if (parent1.getCity(i).equals(city)) {
        position = i;
        break;
      }
    }
    return position;
  }

  private void mutate(final Tour tour) {
    final Random r = new Random();
    for (int i = 0; i < tour.getSize(); i++) {
      if (r.nextFloat() < Util.MUTATION_PROBABILITY) {
        final int j = r.nextInt(tour.getSize());
        Collections.swap(tour.getCityList(), i, j);
      }
    }
  }

  /**
   * Partially-Mapped Crossover, for details:
   * http://www.rubicite.com/Tutorials/GeneticAlgorithms/CrossoverOperators/PMXCrossoverOperator.
   * aspx
   *
   * @param parent1
   * @param parent2
   * @return
   */
  private Tour pmxCrossover(final Tour parent1, final Tour parent2) {
    final Tour child = Tour.createEmptyTour();
    final int[] segments = this.createSegment(parent1.getSize() - 1);
    for (int i = segments[0]; i <= segments[1]; i++) {
      child.setCity(i, parent1.getCity(i));
    }

    for (int i = segments[0]; i <= segments[1]; i++) {
      final City selectedCity = parent2.getCity(i);
      if (!child.contains(selectedCity)) {
        int index = i;
        do {
          final City city = parent1.getCity(index);
          index = parent2.getIndexOfCity(city);
        } while (index >= segments[0] && index <= segments[1]);

        child.setCity(index, selectedCity);
      }
    }

    for (int i = 0; i < parent1.getSize(); i++) {
      final City city = child.getCity(i);
      if (city.getX() == 0.0 && city.getY() == 0.0) {
        child.setCity(i, parent2.getCity(i));
      }
    }

    return child;
  }

  // private Tour rankSelection() throws InvalidSelectionStateException {
  // final Tour[] tours = (Tour[]) this.pop.getTours().toArray();
  // this.bubbleSort(tours);
  // final int sum = tours.length * (tours.length + 1) / 2;
  // final Random rand = new Random();
  // final float randomN = rand.nextFloat() * sum;
  // float partSum = 0;
  // for (int i = 1; i <= Util.POPULATION_SIZE; i++) {
  // partSum += i;
  // if (partSum >= randomN) {
  // return tours[i - 1];
  // }
  // }
  // throw new InvalidSelectionStateException();
  // }

  public Population run(final Population pop) {
    this.pop = pop;
    return this.evalPopulation();
  }

  /**
   * This algorithm uses bubble sort to take best 2 tour.
   *
   * @param parent1
   * @param parent2
   * @param child1
   * @param child2
   * @return
   */
  private Tour[] runSteadyState(final Tour parent1, final Tour parent2, final Tour child1,
      final Tour child2) {
    final Tour[] tours = new Tour[] {parent1, parent2, child1, child2};
    this.bubbleSort(tours);

    return new Tour[] {tours[tours.length - 2], tours[tours.length - 1]};
  }

  private Tour wheelRouletteSelection() throws InvalidSelectionStateException {
    float sum = 0;
    for (int i = 0; i < Util.POPULATION_SIZE; i++) {
      sum += this.pop.getTour(i).getFitnessValue();
    }
    final Random rand = new Random();
    final float randomN = rand.nextFloat() * sum;
    float partSum = 0;
    for (int i = 0; i < Util.POPULATION_SIZE; i++) {
      partSum += this.pop.getTour(i).getFitnessValue();
      if (partSum >= randomN) {
        return this.pop.getTour(i);
      }
    }
    throw new InvalidSelectionStateException();
  }
}
