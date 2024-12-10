package com.example.prjtraveltrovesprint.model;

import androidx.annotation.NonNull;

import com.example.prjtraveltrovesprint.utils.DataUtils;

import java.io.Serializable;
import java.util.Random;

public class Room implements Serializable {

    public enum RoomType {
        SINGLE, DOUBLE, TWIN, DELUXE, KING, QUEEN
    }


    private RoomType roomType;

    private int availableRooms, cost;

    public Room() {

    }

    public Room(RoomType roomType) {
        Random random = new Random();
        final int AVAILABLE_ROOMS = 20;
        this.roomType = roomType;
        this.availableRooms = random.nextInt(AVAILABLE_ROOMS);
        this.cost = random.nextInt(AVAILABLE_ROOMS + 1) * DataUtils.getRoomsRates(this.roomType);
    }

    public Room(RoomType roomType, int availableRooms, int cost) {
        this.roomType = roomType;
        this.availableRooms = availableRooms;
        this.cost = cost;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @NonNull
    @Override
    public String toString() {
        return "Room{" + "roomType=" + this.roomType + ", availableRooms='" + this.availableRooms + '\'' + '}';
    }
}
