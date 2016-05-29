package reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ga.tsp.CityManager;
import ga.tsp.exception.InvalidInstanceException;

public class CityReader {

  private final CityManager cityManager;
  private String instance;

  public CityReader() throws InvalidInstanceException {
    this.cityManager = CityManager.getInstance();

    System.out.println("Please write 1 for berlin52 or 2 for burma14");
    int selection = 0;
    final BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
    try {
      selection = Integer.valueOf(bufferRead.readLine());
    } catch (NumberFormatException | IOException e) {
      e.printStackTrace();
    }
    if (selection == 1) {
      this.instance = "berlin52.txt";
    } else if (selection == 2) {
      this.instance = "burma14.txt";
    } else {
      throw new InvalidInstanceException();
    }
  }

  public void fetchCities() {
    try {
      final InputStream resourceAsStream =
          this.getClass().getResourceAsStream("/instances/" + this.instance);
      final BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
      String line;
      while ((line = reader.readLine()) != null) {
        final String[] coords = line.split("\t\t");
        this.cityManager.createCity(Double.valueOf(coords[0]), Double.valueOf(coords[1]));
      }
      reader.close();
    } catch (final FileNotFoundException e) {
      e.printStackTrace();
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }
}
