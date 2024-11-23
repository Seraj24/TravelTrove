package com.example.prjtraveltrovesprint.model.factory;

import com.example.prjtraveltrovesprint.R;
import com.example.prjtraveltrovesprint.model.Destination;

public class DestinationFactory {

    public static Destination getDestination(Destination.DestinationType destinationType) {
        Destination chosenDestination = null;

        switch (destinationType) {
            case ROME:
                chosenDestination = new Destination(R.drawable.shot_stash_colosseum_rome_1, "Rome", destinationType);
                break;
            case PARIS:
                chosenDestination = new Destination(R.drawable.paris_card, "Paris", destinationType);
                break;
            case TOKYO:
                chosenDestination = new Destination(R.drawable.tokyo_card, "Tokyo", destinationType);
                break;
            case NEW_YORK:
                chosenDestination = new Destination(R.drawable.ny_card, "New York", destinationType);
                break;
            case LONDON:
                chosenDestination = new Destination(R.drawable.london_view, "London", destinationType);
                break;
            case SYDNEY:
                chosenDestination = new Destination(R.drawable.sydney, "Sydney", destinationType);
                break;
            case BARCELONA:
                chosenDestination = new Destination(R.drawable.barcelona_view, "Barcelona", destinationType);
                break;
            default:
                break;
        }
        return chosenDestination;
    }
}
