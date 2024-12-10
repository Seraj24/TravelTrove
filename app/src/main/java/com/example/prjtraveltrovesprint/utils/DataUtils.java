package com.example.prjtraveltrovesprint.utils;

import com.example.prjtraveltrovesprint.R;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.Hotel;
import com.example.prjtraveltrovesprint.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class DataUtils {

    public static String[] getAirlinesList() {
        String[] airlines = {"Air Canada", "Air France", "British Airlines"};
        return airlines;
    }

    public static ArrayList<Hotel> getHotelsList(Destination.DestinationType destinationType) {
        ArrayList<Hotel> hotels = new ArrayList<>();

        switch (destinationType) {
            case ROME:
                hotels.add(new Hotel("Hotel Hassler", getRandomRooms()));
                hotels.add(new Hotel("Hotel de Russie", getRandomRooms()));
                hotels.add(new Hotel("The St. Regis Rome", getRandomRooms()));
                hotels.add(new Hotel("Hotel Artemide", getRandomRooms()));
                hotels.add(new Hotel("Palazzo Manfredi", getRandomRooms()));
                break;

            case PARIS:
                hotels.add(new Hotel("Hotel La Comtesse", getRandomRooms()));
                hotels.add(new Hotel("Hotel Ritz", getRandomRooms()));
                hotels.add(new Hotel("Grand Hotel Central", getRandomRooms()));
                hotels.add(new Hotel("The Plaza", getRandomRooms()));
                hotels.add(new Hotel("Hotel California", getRandomRooms()));
                break;

            case TOKYO:
                hotels.add(new Hotel("Shinjuku Granbell Hotel", getRandomRooms()));
                hotels.add(new Hotel("The Park Hyatt Tokyo", getRandomRooms()));
                hotels.add(new Hotel("Shangri-La Hotel Tokyo", getRandomRooms()));
                hotels.add(new Hotel("Hotel Gracery Shinjuku", getRandomRooms()));
                hotels.add(new Hotel("Hotel New Otani Tokyo", getRandomRooms()));
                break;

            case NEW_YORK:
                hotels.add(new Hotel("The Ritz-Carlton New York", getRandomRooms()));
                hotels.add(new Hotel("Hotel 50 Bowery", getRandomRooms()));
                hotels.add(new Hotel("The Standard High Line", getRandomRooms()));
                hotels.add(new Hotel("The Greenwich Hotel", getRandomRooms()));
                hotels.add(new Hotel("The NoMad Hotel", getRandomRooms()));
                break;

            case LONDON:
                hotels.add(new Hotel("The Savoy", getRandomRooms()));
                hotels.add(new Hotel("The Langham", getRandomRooms()));
                hotels.add(new Hotel("The Connaught", getRandomRooms()));
                hotels.add(new Hotel("The Dorchester", getRandomRooms()));
                hotels.add(new Hotel("Shangri-La Hotel at The Shard", getRandomRooms()));
                break;

            case SYDNEY:
                hotels.add(new Hotel("Sydney Harbour Marriott", getRandomRooms()));
                hotels.add(new Hotel("The Fullerton Hotel Sydney", getRandomRooms()));
                hotels.add(new Hotel("Park Hyatt Sydney", getRandomRooms()));
                hotels.add(new Hotel("Shangri-La Sydney", getRandomRooms()));
                hotels.add(new Hotel("Hilton Sydney", getRandomRooms()));
                break;

            case BARCELONA:
                hotels.add(new Hotel("Hotel Arts Barcelona", getRandomRooms()));
                hotels.add(new Hotel("Majestic Hotel & Spa Barcelona", getRandomRooms()));
                hotels.add(new Hotel("W Barcelona", getRandomRooms()));
                hotels.add(new Hotel("Mandarin Oriental Barcelona", getRandomRooms()));
                hotels.add(new Hotel("Hotel Casa Fuster", getRandomRooms()));
                break;

            default:
                break;
        }

        return hotels;
    }

    public static ArrayList<Room> getRandomRooms() {

        ArrayList<Room> rooms = new ArrayList<>();
        Room.RoomType[] roomTypes = {Room.RoomType.SINGLE, Room.RoomType.DOUBLE, Room.RoomType.TWIN,
                Room.RoomType.DELUXE, Room.RoomType.KING, Room.RoomType.QUEEN};
        final int MAX_ROOMS = roomTypes.length;

        Random random = new Random();

        int toGenerate = random.nextInt(MAX_ROOMS);
        int roomsToGenerate =  toGenerate == 0 ? 1 : toGenerate;

        for (int i = 0; i <= roomsToGenerate - 1; i++) {
            rooms.add(new Room(roomTypes[i]));
        }

        return rooms;
    }

    public static int getRoomsRates(Room.RoomType roomType) {

        int roomRate = -1;

        switch (roomType) {
            case SINGLE:
                roomRate = 2;
                break;
            case DOUBLE:
                roomRate = 4;
                break;
            case TWIN:
                roomRate = 4;
                break;
            case DELUXE:
                roomRate = 5;
                break;
            case KING:
                roomRate = 7;
                break;
            case QUEEN:
                roomRate = 6;
                break;
            default:
                break;
        }

        return roomRate;
    }

}
