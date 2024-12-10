package com.example.prjtraveltrovesprint.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prjtraveltrovesprint.DestinationActivity;
import com.example.prjtraveltrovesprint.DestinationPackageActivity;
import com.example.prjtraveltrovesprint.databinding.FragmentHomeBinding;
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

public class HomeFragment extends Fragment implements ActivityEssentials{

    private FragmentHomeBinding binding;
    EditText searchBar;
    private HorizontalScrollView popularHSV;
    private CardView parisCard, tokyoCard, nyCard;
    private LinearLayout popularCardsContainer, searchResultContainer;
    private ArrayList<CardView> popularCardsCollection;

    private Search search;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initialize();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void initialize() {
        // hsv of popular cards
        popularHSV = binding.popularPackagesHorizontalView;

        searchBar = binding.searchBar;

        // Popular destination cards
        parisCard = binding.parisPopularCard;
        tokyoCard = binding.tokyoPopularCard;
        nyCard = binding.nyPopularCard;

        // Reference for the parent/container of all popular cards
        popularCardsContainer = binding.popularCardsContainer;

        searchResultContainer = binding.searchResultContainer;

        search = new Search(this.getContext(), searchBar, searchResultContainer);

        // To save cards
        popularCardsCollection = new ArrayList<CardView>();

        // Save cards
        popularCardsCollection.add(parisCard);
        popularCardsCollection.add(tokyoCard);
        popularCardsCollection.add(nyCard);

        // Order cards randomly since we don't have an actual user base
        LayoutUtils.reorderCards(popularHSV, popularCardsContainer,popularCardsCollection);

        parisCard.setOnClickListener(v ->
                launchPackageDetailView(Destination.DestinationType.PARIS)
                );
        tokyoCard.setOnClickListener(v ->
                launchPackageDetailView(Destination.DestinationType.TOKYO)
                );
        nyCard.setOnClickListener(v ->
                launchPackageDetailView(Destination.DestinationType.NEW_YORK)
                );


    }

    private void launchPackageDetailView(Destination.DestinationType destinationType) {
        Intent intent = new Intent(getActivity(), DestinationPackageActivity.class);
        Destination destination = DestinationFactory.getDestination(destinationType);
        Booking booking = new Booking();
        booking.setCurrentDestination(destination);
        intent.putExtra("booking", booking);
        intent.putExtra("last_activity", this.getClass().getName());
        startActivity(intent);
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