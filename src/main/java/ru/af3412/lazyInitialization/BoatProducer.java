package ru.af3412.lazyInitialization;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boat_producers")
public class BoatProducer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "boatProducer")
    List<Boat> boats = new ArrayList<>();

    public static BoatProducer of(String name) {
        BoatProducer producer = new BoatProducer();
        producer.name = name;
        return producer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Boat> getBoats() {
        return boats;
    }

    public void setBoats(List<Boat> listModels) {
        this.boats = listModels;
    }
}
