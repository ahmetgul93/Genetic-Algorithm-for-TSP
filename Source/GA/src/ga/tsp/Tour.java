package ga.tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ga.tsp.CityManager.City;

public class Tour {

  public static Tour createEmptyTour() {
    return new Tour(true);
  }

  private double fitnessValue;

  private double distance;

  private final List<City> cityList;

  public Tour(final boolean empty) {
    this.cityList = new ArrayList<City>(CityManager.getInstance().getSize());
    for (int i = 0; i < CityManager.getInstance().getSize(); i++) {
      this.cityList.add(CityManager.getInstance().createEmptyCity());
    }

    if (!empty) {
      this.generateTour();
    }
  }

  public boolean contains(final City city) {
    return this.cityList.contains(city);
  }

  private void generateTour() {
    this.cityList.clear();
    this.cityList.addAll(CityManager.getInstance().getCities());
    Collections.shuffle(this.cityList); // ?
    this.fitnessValue = 0;
    this.distance = 0;
  }

  public City getCity(final int index) {
    return this.cityList.get(index);
  }

  public List<City> getCityList() {
    return this.cityList;
  }

  public double getDistance() {
    if (this.distance == 0) {
      for (int i = 0; i < this.getSize() - 1; i++) {
        this.distance +=
            CityManager.getInstance().getDistance(this.cityList.get(i), this.cityList.get(i + 1));
      }
    }
    return this.distance;
  }

  public double getFitnessValue() {
    if (this.fitnessValue == 0) {
      this.fitnessValue = 1 / this.getDistance();
    }
    return this.fitnessValue;
  }

  public int getIndexOfCity(final City city) {
    for (int i = 0; i < this.cityList.size(); i++) {
      if (this.cityList.get(i).equals(city)) {
        return i;
      }
    }
    return -1; // city is not found
  }

  public int getSize() {
    return this.cityList.size();
  }

  public void setCity(final int index, final City city) {
    this.cityList.set(index, city);
    this.fitnessValue = 0;
    this.distance = 0;
  }

  @Override
  public String toString() {
    String result = "";
    for (int i = 0; i < this.getSize(); i++) {
      result += this.getCity(i) + "|";
    }

    return result;
  }
}
