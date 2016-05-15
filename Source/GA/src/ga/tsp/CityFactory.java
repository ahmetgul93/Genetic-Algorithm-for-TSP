package ga.tsp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CityFactory {

  public class City {

    private double x;

    private double y;

    private City() {
      this.setX(CityFactory.this.rand.nextDouble() * Util.RAND_FACTOR);
      this.setY(CityFactory.this.rand.nextDouble() * Util.RAND_FACTOR);
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj instanceof City) {
        final CityFactory.City other = (City) obj;
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

  private static List<City> listOfCity;

  private static CityFactory instance;

  public static CityFactory getInstance() {
    if (CityFactory.instance == null) {
      CityFactory.instance = new CityFactory();
    }
    return CityFactory.instance;
  }

  private final Random rand = new Random();

  public List<City> generateCities() {
    if (CityFactory.listOfCity == null) {
      CityFactory.listOfCity = new ArrayList<>();
      for (int i = 0; i < Util.CHROMOSOME_LENGTH; i++) {
        CityFactory.listOfCity.add(new City());
      }
    }
    return CityFactory.listOfCity;
  }

  public double getDistance(final City c1, final City c2) {
    return Math.sqrt(Math.pow(c1.getX() - c2.getX(), 2) + Math.pow(c1.getY() - c2.getY(), 2));
  }
}
