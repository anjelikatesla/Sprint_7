package com.example;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;
    static private String randomString = RandomStringUtils.randomAlphabetic(10);
    static private int randomInt = RandomUtils.nextInt(1, 11);
    static private String randomDate = LocalDate.now().plusDays(RandomUtils.nextInt(1, 31)).toString();

    public Order(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public Order(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
    }

    public Order(String[] color) {
        this.color = color;
    }

    public Order() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public String[] getColor() {
        return color;
    }

    public void setUpFieldsForRequest() {
        this.firstName = randomString;
        this.lastName = randomString;
        this.address = randomString;
        this.metroStation = randomString;
        this.phone = randomString;
        this.rentTime = randomInt;
        this.deliveryDate = randomDate;
        this.comment = randomString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return rentTime == order.rentTime && firstName.equals(order.firstName) && lastName.equals(order.lastName) && address.equals(order.address) && metroStation.equals(order.metroStation) && phone.equals(order.phone) && deliveryDate.equals(order.deliveryDate) && comment.equals(order.comment) && Arrays.equals(color, order.color);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
        result = 31 * result + Arrays.hashCode(color);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", metroStation='" + metroStation + '\'' +
                ", phone='" + phone + '\'' +
                ", rentTime=" + rentTime +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", comment='" + comment + '\'' +
                ", color=" + Arrays.toString(color) +
                '}';
    }
}
