package ru.yandex.practicum.delivery;

public abstract class Parcel {

    private final String description;
    private final int weight;
    private final String deliveryAddress;
    // byte, т. к. по условию посылки доставляются в том же месяце.
    private final byte sendDay;

    public Parcel(String description, int weight, String deliveryAddress, byte sendDay) {
        this.description = description;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.sendDay = sendDay;
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public byte getSendDay() {
        return sendDay;
    }

    public void packageItem() {
        System.out.println("Посылка " + description + " упакована.");
    }

    public void deliver() {
        System.out.println("Посылка " + description + " доставлена по адресу " + deliveryAddress + ".");
    }

    public abstract int getBaseDeliveryCost();

    public double calculateDeliveryCost() {
        return weight * getBaseDeliveryCost();
    }

}
