package ga.tsp;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import ga.tsp.CityManager.City;
import ga.tsp.exception.InvalidSelectionStateException;

public class Genetic {

  private Population pop;

  // public int breakUp(final Tour parent1) // ayrılacağı noktayı belirliyor !
  // {
  // final Random r = new Random();
  //
  // int breakingPoint = 0;
  // breakingPoint = r.nextInt(parent1.getSize());
  //
  //
  // return breakingPoint;
  //
  // }

  /*
   * private Tour oxCrossover(final Tour parent1, final Tour parent2) {
   *
   *
   * }
   */

  // private void cxCrossover(final Tour male, final Tour female, final Tour child1,
  // final Tour child2) {
  //
  //
  // /*
  // * final Tour child = new Tour(true); int index = 0; while
  // * (!child.contains(parent2.getCity(index))) { child.setCity(index, parent1.getCity(index));
  // * final int position = this.getPosition(parent1, parent2.getCity(index));
  // * child.setCity(position, parent2.getCity(index)); index = position; }
  // *
  // * for (int i = 0; i < child.getSize(); i++) { if (child.getCity(i) == null) { child.setCity(i,
  // * parent2.getCity(i)); } }
  // *
  // * return child;
  // */
  //
  // // abi mantıken bir child değil iki children dönmesi gerekli değil mi ?
  // // O yüzden parametre ile gonderdim void'e aldım kodu.
  // // Ama yine de sen bilirsin tabi
  //
  // final int breakingPoint = this.breakUp(male);
  //
  //
  //
  // int i = 0;
  //
  // while (i != breakingPoint) // 1. kromozom icin
  // {
  // child1.setCity(i, male.getCity(i));
  // i++;
  // }
  //
  // int j = i;
  //
  // while (i != female.getSize()) // 2. kromozom icin
  // {
  // final int x = female.getSize();
  // child1.setCity(i, female.getCity(i));
  // i++;
  // }
  //
  // while (j != male.getSize()) // 1.kromozom devami
  // {
  // child2.setCity(j, male.getCity(j));
  // j++;
  // }
  //
  // int t = 0;
  //
  // while (t != breakingPoint) // 2. kromozom devami
  // {
  // child2.setCity(t, female.getCity(t));
  // t++;
  // }
  //
  //
  //
  // }

  private Tour cxCrossover(final Tour parent1, final Tour parent2) {
    final Tour child = new Tour(true);
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

      if (Util.CROSSOVER_TYPE.equals("CX")) {

        // child1 = new Tour(true);
        //
        // child2 = new Tour(true);
        //
        // this.cxCrossover(parent1, parent2, child1, child2);

        child1 = this.cxCrossover(parent1, parent2);
        child2 = this.cxCrossover(parent2, parent1);

      } else if (Util.CROSSOVER_TYPE.equals("OX")) {
        // child1 = this.oxCrossover();
        // child2 = this.oxCrossover();
      }

      newPop.setTour(i, child1);
      newPop.setTour(i + 1, child2);
    }

    for (int i = Util.ELITISM_COUNT; i < Util.POPULATION_SIZE; i++) {
      this.mutate(newPop.getTour(i));
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
    Collections.swap(tour.getCityList(), r.nextInt(tour.getSize()), r.nextInt(tour.getSize()));
  }

  // private Tour oxCrossover() {
  //
  // }

  public Population run(final Population pop) {
    this.pop = pop;
    return this.evalPopulation();
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
