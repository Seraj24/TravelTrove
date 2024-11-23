package com.example.prjtraveltrovesprint.ui.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prjtraveltrovesprint.DestinationActivity;
import com.example.prjtraveltrovesprint.databinding.FragmentBookBinding;
import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.factory.DestinationFactory;

import java.util.HashMap;
import java.util.Map;

public class BookFragment extends Fragment implements ActivityEssentials {

    private FragmentBookBinding binding;

    CardView parisPC, tokyoPC, nyPC, romePC, sydneyPC,barcelonaPC, londonPC;

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

        HashMap<Destination.DestinationType, CardView> destinationCards = new HashMap<>();
        destinationCards.put(Destination.DestinationType.PARIS, parisPC);
        destinationCards.put(Destination.DestinationType.TOKYO, tokyoPC);
        destinationCards.put(Destination.DestinationType.NEW_YORK, nyPC);
        destinationCards.put(Destination.DestinationType.ROME, romePC);
        destinationCards.put(Destination.DestinationType.SYDNEY, sydneyPC);
        destinationCards.put(Destination.DestinationType.BARCELONA, barcelonaPC);
        destinationCards.put(Destination.DestinationType.LONDON, londonPC);

        for (Map.Entry<Destination.DestinationType, CardView> entry : destinationCards.entrySet()) {
            entry.getValue().setOnClickListener(v ->
                    launchDestinationView(entry.getKey())
            );
        }
    }

    private void launchDestinationView(Destination.DestinationType destinationType) {
        Intent intent = new Intent(getActivity(), DestinationActivity.class);
        Destination destination = DestinationFactory.getDestination(destinationType);
        intent.putExtra("destination_details", destination);
        intent.putExtra("last_activity", "BookView");
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}