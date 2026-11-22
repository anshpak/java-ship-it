package ru.yandex.practicum.delivery;

public class PerishableParcel extends Parcel{

    private final byte timeToLive;
    public static final int BASE_DELIVERY_COST = 3;

    public PerishableParcel(String description, int weight, String deliveryAddress, byte sendDay,
                          byte timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    @Override
    public int getBaseDeliveryCost() {
        return BASE_DELIVERY_COST;
    }

    public boolean isExpired(byte currentDay) {
        return timeToLive + getSendDay() < currentDay;
    }
}
