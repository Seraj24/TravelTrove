package com.example.prjtraveltrovesprint.model.factory;

import com.example.prjtraveltrovesprint.R;
import com.example.prjtraveltrovesprint.model.Card;
import com.example.prjtraveltrovesprint.model.Destination;

public class CardFactory {

    public static Card getCard(Destination.DestinationType destinationType) {
        Card chosenCard = null;

        switch (destinationType) {
            case ROME:
                chosenCard = new Card(R.drawable.shot_stash_colosseum_rome_1, "Rome, Italy",
                        "Rome Image");
                break;
            case PARIS:
                chosenCard = new Card(R.drawable.paris_card, "Paris, France",
                        "Paris Image");
                break;
            case TOKYO:
                chosenCard = new Card(R.drawable.tokyo_card, "Tokyo, Japan",
                        "Tokyo Image");
                break;
            case NEW_YORK:
                chosenCard = new Card(R.drawable.ny_card, "New York, US", "NY Image");
                break;
            case LONDON:
                chosenCard = new Card(R.drawable.london_view, "London", "London Image");
                break;
            case SYDNEY:
                chosenCard = new Card(R.drawable.sydney, "Sydney", "Sydney Image");
                break;
            case BARCELONA:
                chosenCard = new Card(R.drawable.barcelona_view, "Barcelona", "Barcelona");
                break;
            default:
                break;

        }

        return chosenCard;
    }
}
