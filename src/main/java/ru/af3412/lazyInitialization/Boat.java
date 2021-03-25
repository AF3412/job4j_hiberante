package ru.af3412.lazyInitialization;

import javax.persistence.*;

@Entity
@Table(name = "boats")
public class Boat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "boatProducer_id")
    private BoatProducer boatProducer;

    public static Boat of(String name, BoatProducer producer) {
        Boat boat = new Boat();
        boat.name = name;
        boat.boatProducer = producer;
        return boat;
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

    public BoatProducer getBoatProducer() {
        return boatProducer;
    }

    public void setBoatProducer(BoatProducer producer) {
        this.boatProducer = producer;
    }

    @Override
    public String toString() {
        return "Boat{" +
                "name='" + name + '\'' +
                '}';
    }
}
