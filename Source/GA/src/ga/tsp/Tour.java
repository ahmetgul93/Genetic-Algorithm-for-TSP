package ga.tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ga.tsp.CityFactory.City;

public class Tour {

  private double fitnessValue;

  private double distance;

  private List<City> cityList;

  public Tour(final boolean empty) {
    this.cityList = new ArrayList<City>(Util.CHROMOSOME_LENGTH);
    if (!empty) {
      this.generateTour();
    }
  }

  public boolean contains(final City city) {
    return this.cityList.contains(city);
  }

  private void generateTour() {
    this.cityList = CityFactory.getInstance().generateCities();
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
            CityFactory.getInstance().getDistance(this.cityList.get(i), this.cityList.get(i + 1));
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

  public int getSize() {
    return this.cityList.size();
  }

  public void setCity(final int index, final City city) {
    if (this.cityList.size() == 0) {
      this.cityList.add(city);
    } else {
      this.cityList.set(index, city);
    }
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
