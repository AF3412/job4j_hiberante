package ru.af3412.hql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "base_vacancies")
public class BaseVacanies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<Vacancy> vacancies = new ArrayList<>();

    public BaseVacanies() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void addVacancy(Vacancy vacancy) {
        this.vacancies.add(vacancy);
    }

    @Override
    public String toString() {
        return "BaseVacanies{" +
                "id=" + id +
                ", vacancies=" + vacancies +
                '}';
    }
}
