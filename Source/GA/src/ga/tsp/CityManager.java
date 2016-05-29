package ga.tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CityManager {

  public class City {

    private double x;

    private double y;

    public City() {}

    public City(final double x, final double y) {
      this.setX(x);
      this.setY(y);
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj instanceof City) {
        final City other = (City) obj;
        if (other.getX() == this.getX() && other.getY() == this.getY()) {
          return true;
        }
      }
      return false;
    }

    public double getX() {
      return this.x;
    }

    public double getY() {
      return this.y;
    }

    public void setX(final double x) {
      this.x = x;
    }

    public void setY(final double y) {
      this.y = y;
    }

    @Override
    public String toString() {
      return "(" + this.getX() + "," + this.getY() + ")";
    }

  }

  private static CityManager instance;

  public static CityManager getInstance() {
    if (instance == null) {
      instance = new CityManager();
    }

    return instance;
  }

  private final List<City> cities = new ArrayList<City>();

  public void createCity(final double x, final double y) {
    this.cities.add(new City(x, y));
  }

  public City createEmptyCity() {
    return new City();
  }

  public List<City> getCities() {
    return Collections.unmodifiableList(this.cities);
  }

  public double getDistance(final City c1, final City c2) {
    return Math.sqrt(Math.pow(c1.getX() - c2.getX(), 2) + Math.pow(c1.getY() - c2.getY(), 2));
  }

  public int getSize() {
    return this.cities.size();
  }
}
