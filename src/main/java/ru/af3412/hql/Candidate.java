package ru.af3412.hql;

import javax.persistence.*;

@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int experience;
    private float salary;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BaseVacanies baseVacanies;

    public Candidate() {
    }

    public Candidate(String name, int experience, float salary, BaseVacanies baseVacanies) {
        this.name = name;
        this.experience = experience;
        this.salary = salary;
        this.baseVacanies = baseVacanies;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public BaseVacanies getBaseVacanies() {
        return baseVacanies;
    }

    public void setBaseVacanies(BaseVacanies baseVacanies) {
        this.baseVacanies = baseVacanies;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", experience=" + experience +
                ", salary=" + salary +
                ", baseVacanies=" + baseVacanies +
                '}';
    }
}
