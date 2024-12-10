package com.example.prjtraveltrovesprint.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.prjtraveltrovesprint.model.Card;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.factory.CardFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class FileUtils {

    public static HashMap<Destination.DestinationType, Card> readFile(Context context, String filename, String query) {

        AssetManager assetMan = context.getResources().getAssets();
        HashMap<Destination.DestinationType, Card> cardsList = new HashMap<>();

        try {

            InputStreamReader isr = new InputStreamReader(assetMan.open(filename));

            BufferedReader br = new BufferedReader(isr);
            String oneLine = br.readLine();

            while (oneLine != null) {
                StringTokenizer st = new StringTokenizer(oneLine, ",");
                String destinationName = st.nextToken();
                if (query != null && !query.isEmpty()) {
                    if (destinationName.toLowerCase().contains(query.toLowerCase())) {
                        Card destinationCard = CardFactory.getCard(Destination.DestinationType
                                .valueOf(destinationName.toUpperCase()));
                        cardsList.put(Destination.DestinationType
                                .valueOf(destinationName.toUpperCase()), destinationCard);
                    }
                }
                oneLine = br.readLine();
            }

            br.close();
            isr.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return  cardsList;
    }
}
