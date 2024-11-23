package com.example.prjtraveltrovesprint.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prjtraveltrovesprint.DestinationPackageActivity;
import com.example.prjtraveltrovesprint.databinding.FragmentHomeBinding;
import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.factory.DestinationFactory;
import com.example.prjtraveltrovesprint.utils.LayoutUtils;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements ActivityEssentials {

    private FragmentHomeBinding binding;
    private HorizontalScrollView popularHSV;
    private CardView parisCard, tokyoCard, nyCard;
    private LinearLayout popularCardsContainer;
    private ArrayList<CardView> popularCardsCollection;

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

        // Popular destination cards
        parisCard = binding.parisPopularCard;
        tokyoCard = binding.tokyoPopularCard;
        nyCard = binding.nyPopularCard;

        // Reference for the parent/container of all popular cards
        popularCardsContainer = binding.popularCardsContainer;

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
        intent.putExtra("destination_details", destination);
        intent.putExtra("last_activity", this.getClass().getName());
        startActivity(intent);
    }
}