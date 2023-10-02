package ru.itmo.p3131.student18.interim.objectclasses;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HumanBeing implements Comparable<HumanBeing>, Serializable {

    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate = LocalDate.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private boolean realHero;
    private boolean hasToothpick;
    private float impactSpeed; //Значение поля должно быть больше -163
    private WeaponType weaponType;
    private Mood mood;
    private Car car;
    private String user;

    public HumanBeing(int id, String name, Coordinates coordinates, LocalDate creationDate, boolean realHero, boolean hasToothPick, float impactSpeed, WeaponType weaponType, Mood mood, Car car, String user) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.realHero = realHero;
        this.hasToothpick = hasToothPick;
        this.impactSpeed = impactSpeed;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
        this.user = user;
    }

    public HumanBeing(String name, Coordinates coordinates, boolean realHero, boolean hasToothPick, float impactSpeed, WeaponType weaponType, Mood mood, Car car, String user) {
        this.name = name;
        this.coordinates = coordinates;
        this.realHero = realHero;
        this.hasToothpick = hasToothPick;
        this.impactSpeed = impactSpeed;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
        this.user = user;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public boolean isRealHero() {
        return realHero;
    }

    public boolean isHasToothpick() {
        return hasToothpick;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Mood getMood() { return mood; }

    public Car getCar() { return car; }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public float getImpactSpeed() {
        return impactSpeed;
    }

    public void setImpactSpeed(float impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    public String getUser() {
        return user;
    }

    public byte[] getBytes() throws IOException {
        byte[] serializedObj;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        new ObjectOutputStream(byteArrayOutputStream).writeObject(this);
        serializedObj = byteArrayOutputStream.toByteArray();
        return serializedObj;
    }

    @Override
    public String toString() {
        /*return "\nid: " + id + "\n\t Name: " + name + "\n\t Coordinates: " + coordinates + "\n\t Date: " +
                creationDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n\t Real hero? " + realHero + "\n\t Has toothpick? " + hasToothpick
                + "\n\t Impact speed: " + impactSpeed + "\n\t Weapon type: " + weaponType + "\n\t Mood: "
                + mood + "\n\t Car: " + car + "\n\t" + "User: " + user + "\n- - - - - - - - - - - - - - - - - - - - -";*/
        return id + "," + name + "," + coordinates.getX() + "," + coordinates.getY() + "," + creationDate.toString() + ","
                + realHero + "," + hasToothpick + "," + impactSpeed + "," + weaponType.toString() + "," + mood.toString() + "," + car.isCool() + "," + user;
    }

    @Override
    public int compareTo(HumanBeing human) {
        return id.compareTo(human.getId());
    }

}





