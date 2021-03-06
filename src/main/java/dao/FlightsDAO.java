package dao;

import models.Airline;
import models.Airport;
import models.Flights;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;


public class FlightsDAO  implements DAO<Flights> {

    private static List<Flights> flights = new ArrayList<>();
    private  File file = new File("flightsFile.txt");

    @Override
    public Collection<Flights> getAll() {
        try (ObjectInputStream ois = new ObjectInputStream( new BufferedInputStream(new FileInputStream(file)))) {
            return (List<Flights>) ois.readObject();
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void create(Flights flight) {
        flights.add(flight);
        write();
    }

    @Override
    public void delete(int id) {
        flights.remove(id);
        write();
    }

    @Override
    public void write() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))){
            Random random = new Random();
            for (int i = 1; i <= 100; i++) {
                Month month=Month.values()[random.nextInt(Month.values().length)];
                Airport from = Airport.valueOf("KIEV");
                Airport to = Airport.values()[random.nextInt(Airport.values().length)];
                if (!from.equals(to)){
                    flights.add(new Flights(i,
                            Airline.values()[random.nextInt(Airline.values().length)],
                            from,
                            to,
                            LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth(),random.nextInt(23),random.nextInt(59)),
                            (random.nextInt(150-70+1)+70)));
                } else {
                    i--;
                }
            }
            oos.writeObject(flights);
        } catch (IOException i){
            throw new RuntimeException("FlightsDAO:write:IOException", i);
        }
    }
}
