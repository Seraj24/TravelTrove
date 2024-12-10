package com.example.prjtraveltrovesprint.ui.book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prjtraveltrovesprint.DestinationActivity;
import com.example.prjtraveltrovesprint.databinding.FragmentBookBinding;
import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.Booking;
import com.example.prjtraveltrovesprint.model.Card;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.Search;
import com.example.prjtraveltrovesprint.model.factory.DestinationFactory;
import com.example.prjtraveltrovesprint.utils.FileUtils;
import com.example.prjtraveltrovesprint.utils.LayoutUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookFragment extends Fragment implements ActivityEssentials {

    private FragmentBookBinding binding;

    CardView parisPC, tokyoPC, nyPC, romePC, sydneyPC,barcelonaPC, londonPC, nyFC, parisFC,
            tokyoFC, nyPersonalized, parisPersonalized, tokyoPersonalized;
    EditText searchBar;
    private LinearLayout searchResultContainer;

    private Search search;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BookViewModel bookViewModel =
                new ViewModelProvider(this).get(BookViewModel.class);

        binding = FragmentBookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initialize();

        return root;
    }

    public void initialize() {
        parisPC = binding.parisDestinationCard;
        tokyoPC = binding.tokyoDestinationCard;
        nyPC  = binding.newYorkDestinationCard;
        romePC = binding.romeDestinationCard;
        sydneyPC = binding.sydneyDestinationCard;
        barcelonaPC = binding.barcelonaDestinationCard;
        londonPC = binding.londonDestinationCard;
        nyFC = binding.nyFeaturedDestinationCard;
        parisFC = binding.parisFeaturedDestinationCard;
        tokyoFC = binding.tokyoFeaturedDestinationCard;
        nyPersonalized = binding.nyPersonalizedDestinationCard;
        parisPersonalized = binding.parisPersonalizedDestinationCard;
        tokyoPersonalized = binding.tokyoPersonalizedDestinationCard;
        searchBar = binding.searchBarBook;
        searchResultContainer = binding.searchResultContainerBook;

        search = new Search(this.getContext(), searchBar, searchResultContainer);

        HashMap<Destination.DestinationType, CardView> destinationCards = new HashMap<>();
        HashMap<Destination.DestinationType, CardView> featuredDestinationsCards = new HashMap<>();
        HashMap<Destination.DestinationType, CardView> personalizedRecommendationsDC
                = new HashMap<>();
        destinationCards.put(Destination.DestinationType.PARIS, parisPC);
        destinationCards.put(Destination.DestinationType.TOKYO, tokyoPC);
        destinationCards.put(Destination.DestinationType.NEW_YORK, nyPC);
        destinationCards.put(Destination.DestinationType.ROME, romePC);
        destinationCards.put(Destination.DestinationType.SYDNEY, sydneyPC);
        destinationCards.put(Destination.DestinationType.BARCELONA, barcelonaPC);
        destinationCards.put(Destination.DestinationType.LONDON, londonPC);

        featuredDestinationsCards.put(Destination.DestinationType.PARIS, parisFC);
        featuredDestinationsCards.put(Destination.DestinationType.NEW_YORK, nyFC);
        featuredDestinationsCards.put(Destination.DestinationType.TOKYO, tokyoFC);

        personalizedRecommendationsDC.put(Destination.DestinationType.PARIS, parisFC);
        personalizedRecommendationsDC.put(Destination.DestinationType.NEW_YORK, nyFC);
        personalizedRecommendationsDC.put(Destination.DestinationType.TOKYO, tokyoFC);



        initializeCardListeners(destinationCards);
        initializeCardListeners(featuredDestinationsCards);
        initializeCardListeners(personalizedRecommendationsDC);


    }

    private void initializeCardListeners(HashMap<Destination.DestinationType, CardView> cards) {
        for (Map.Entry<Destination.DestinationType, CardView> entry : cards.entrySet()) {
            Destination.DestinationType destinationType = entry.getKey();
            CardView cardView = entry.getValue();

            cardView.setOnClickListener(v -> launchDestinationView(destinationType));
        }
    }

    private void launchDestinationView(Destination.DestinationType destinationType) {
        Booking booking = new Booking();
        Intent intent = new Intent(getActivity(), DestinationActivity.class);
        Destination destination = DestinationFactory.getDestination(destinationType);
        booking.setCurrentDestination(destination);
        intent.putExtra("booking", booking);
        intent.putExtra("last_activity", "BookView");
        startActivity(intent);
    }


}