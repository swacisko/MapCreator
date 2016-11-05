
package inz.mapcreator;

import org.onebusaway.gtfs.impl.*;
import org.onebusaway.csv_entities.*;
import org.onebusaway.gtfs.serialization.*;
import org.onebusaway.gtfs.model.*;

import java.io.File;
import java.io.IOException;

public class GtfsReaderExampleMain {

  public void TEST() throws IOException {


    GtfsReader reader = new GtfsReader();
    reader.setInputLocation(new File( "./GTFS/routes.txt" ));

    /**
     * You can register an entity handler that listens for new objects as they
     * are read
     */
    reader.addEntityHandler(new GtfsEntityHandler());

    /**
     * Or you can use the internal entity store, which has references to all the
     * loaded entities
     */
    GtfsDaoImpl store = new GtfsDaoImpl();
    reader.setEntityStore(store);

    reader.run();

    // Access entities through the store
    for (Route route : store.getAllRoutes()) {
      System.out.println("route: " + route.getShortName());
    }
  }

  private static class GtfsEntityHandler implements EntityHandler {

    public void handleEntity(Object bean) {
      if (bean instanceof Stop) {
        Stop stop = (Stop) bean;
        System.out.println("stop: " + stop.getName());
      }
    }
  }
}
