package ru.kaznacheev.library.model;

public class Person {

    private int id;
    private String FIO;
    private int year;

    public Person() {
    }

    public Person(int id, String FIO, int year) {
        this.id = id;
        this.FIO = FIO;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
