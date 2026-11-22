package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ru.yandex.practicum.delivery.FragileParcel;
import ru.yandex.practicum.delivery.ParcelBox;
import ru.yandex.practicum.delivery.PerishableParcel;
import ru.yandex.practicum.delivery.StandardParcel;

public class DeliveryCostTest {

    private final StandardParcel standardCementParcel = new StandardParcel("Цемент", 30,
            "г. Санкт-Петербург, Лиговский проспект, д. 150, кв. 5", (byte) 1);
    private final StandardParcel standardNailsParcel = new StandardParcel("Гвозди", 5,
            "г. Санкт-Петербург, Лиговский проспект, д. 150, кв. 5", (byte) 1);
    private final StandardParcel standardPaintParcel = new StandardParcel("Краска", 5,
            "г. Санкт-Петербург, Лиговский проспект, д. 150, кв. 5", (byte) 1);
    private final StandardParcel standardWoodParcel = new StandardParcel("Дерево", 28.8,
            "г. Санкт-Петербург, Лиговский проспект, д. 150, кв. 5", (byte) 1);

    private final PerishableParcel perishableParcel = new PerishableParcel("Творог", 1.25,
            "г. Санкт-Петербург, ул. Рубинштейна, д. 24, кв. 17", (byte) 2, (byte) 10);
    private final FragileParcel fragileParcel = new FragileParcel("Набор фарфоровых тарелок",3.75,
            "г. Санкт-Петербург, ул. Савушкина, д. 87, корп. 2, кв. 33", (byte) 3);

    private final double MAX_PARCELS_WEIGHT_FOR_BOX = 40;
    private ParcelBox<StandardParcel> box = new ParcelBox<>(MAX_PARCELS_WEIGHT_FOR_BOX);

    @BeforeEach
    public void beforeEach() {
        box.getAllParcels().clear();
    }

    // Вычисление стоимости посылки для стандартного типа посылок.
    @Test
    public void calculateDeliveryCostForStandardParcelShouldBeOk() {
        assertEquals(standardCementParcel.calculateDeliveryCost(),
                standardCementParcel.getWeight() * StandardParcel.BASE_DELIVERY_COST);
    }

    // Вычисление стоимости посылки для скоропортящегося типа посылок.
    @Test
    public void calculateDeliveryCostForPerishableParcelShouldBeOk() {
        assertEquals(perishableParcel.calculateDeliveryCost(),
                perishableParcel.getWeight() * PerishableParcel.BASE_DELIVERY_COST);
    }

    // Вычисление стоимости посылки для хрупкого типа посылок.
    @Test
    public void calculateDeliveryCostForFragileParcelShouldBeOk() {
        assertEquals(fragileParcel.calculateDeliveryCost(),
                fragileParcel.getWeight() * FragileParcel.BASE_DELIVERY_COST);
    }

    // Проверка isExpired значением даты на день меньше.
    @Test
    public void isExpiredCheckShouldReturnFalseOnDayBeforeExpiration() {
        assertFalse(perishableParcel.isExpired((byte)(perishableParcel.getTimeToLive()
                + perishableParcel.getSendDay() - 1)));
    }

    // Проверка isExpired датой окончания срока годности.
    @Test
    public void isExpiredCheckShouldReturnFalseOnExpirationDay() {
        assertFalse(perishableParcel.isExpired((byte)(perishableParcel.getTimeToLive()
                + perishableParcel.getSendDay())));
    }

    // Проверка isExpired значением даты на день позже.
    @Test
    public void isExpiredCheckShouldReturnFalseOnDayAfterExpirationDay() {
        assertTrue(perishableParcel.isExpired((byte)(perishableParcel.getTimeToLive()
                + perishableParcel.getSendDay() + 1)));
    }

    // Проверка добавления в коробку посылки весом не превышающим максимально возможный.
    @Test
    public void shouldAddStandardParcelToBoxWhenWeightIsLessThanMaxWeightForBox() {
        box.addParcel(standardCementParcel);
        assertEquals(box.getAvailableWeight(), MAX_PARCELS_WEIGHT_FOR_BOX - standardCementParcel.getWeight());
    }

    // Проверка добавления в коробку посылок весом равным максимально возможному.
    @Test
    public void shouldAddStandardParcelToBoxWhenWeightEqualsMaxWeightForBox() {
        box.addParcel(standardCementParcel);
        box.addParcel(standardNailsParcel);
        box.addParcel(standardPaintParcel);
        assertEquals(box.getAvailableWeight(), MAX_PARCELS_WEIGHT_FOR_BOX - standardCementParcel.getWeight()
                - standardNailsParcel.getWeight() - standardPaintParcel.getWeight());
    }

    // Проверка добавления в коробку посылок весом превышающим максимально возможный.
    @Test
    public void shouldAddStandardParcelToBoxWhenWeightIsMoreThanMaxWeightForBox() {
        box.addParcel(standardCementParcel);
        box.addParcel(standardNailsParcel);
        box.addParcel(standardWoodParcel);
        assertEquals(box.getAvailableWeight(), MAX_PARCELS_WEIGHT_FOR_BOX - standardCementParcel.getWeight()
                - standardNailsParcel.getWeight());
    }
}
