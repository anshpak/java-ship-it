package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();
    private static List<Trackable> allTrackableParcels = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    trackParcels();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Отследить хрупкие посылки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        System.out.println("Введите описание посылки:");
        String description = scanner.nextLine();
        System.out.println("Введите вес посылки:");
        double weight = scanner.nextDouble();
        System.out.println("Введите адрес посылки:");
        scanner.nextLine();
        String deliveryAddress = scanner.nextLine();
        System.out.println("Введите день отправки посылки:");
        byte sendDay = scanner.nextByte();

        System.out.println("Выберите тип посылки:");
        System.out.println("1 — Стандартная посылка.");
        System.out.println("2 — Скоропортящаяся посылка.");
        System.out.println("3 — Хрупкая посылка");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                allParcels.add(new StandardParcel(description, weight, deliveryAddress, sendDay));
                break;
            case 2:
                System.out.println("Введите срок годности посылки:");
                byte timeToLive = scanner.nextByte();
                scanner.nextLine();
                allParcels.add(new PerishableParcel(description, weight, deliveryAddress, sendDay,
                        timeToLive));
                break;
            case 3:
                allTrackableParcels.add(new FragileParcel(description, weight, deliveryAddress, sendDay));
                break;
            default:
                System.out.println("Выбрали неверный тип посылки. Попробуйте ещё раз.");
        }
    }

    private static void sendParcels() {
        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            parcel.deliver();
        }
        for (Trackable trackableParcel : allTrackableParcels) {
            ((Parcel)trackableParcel).packageItem();
            ((Parcel)trackableParcel).deliver();
        }
    }

    private static void calculateCosts() {
        double sum = 0;
        for (Parcel parcel : allParcels) {
            sum += parcel.calculateDeliveryCost();
        }
        for (Trackable trackableParcel : allTrackableParcels) {
            sum += ((Parcel)trackableParcel).calculateDeliveryCost();
        }
        System.out.println("Общая стоимость всех доставок: " + sum + ".");
    }

    private static void trackParcels() {
        for (Trackable trackableParcel : allTrackableParcels) {
            System.out.println("Введите новое местоположение для посылки "
                    + ((Parcel) trackableParcel).getDescription() + ":");
            String newLocation = scanner.nextLine();
            trackableParcel.reportStatus(newLocation);
        }
    }

}

