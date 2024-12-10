package com.example.prjtraveltrovesprint.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.prjtraveltrovesprint.DestinationActivity;
import com.example.prjtraveltrovesprint.model.factory.DestinationFactory;
import com.example.prjtraveltrovesprint.utils.FileUtils;
import com.example.prjtraveltrovesprint.utils.LayoutUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class Search implements TextWatcher {

    Context context;
    private EditText searchBar;
    private LinearLayout searchResultContainer;

    public Search() {
    }

    public Search(Context context, EditText searchBar, LinearLayout searchResultContainer) {

        this.context = context;
        this.searchBar = searchBar;
        this.searchResultContainer = searchResultContainer;

        searchResultContainer.setVisibility(View.GONE);

        searchBar.addTextChangedListener(this);
    }


    public EditText getSearchBar() {
        return searchBar;
    }

    public void setSearchBar(EditText searchBar) {
        this.searchBar = searchBar;
    }

    private void launchDestinationView(Destination.DestinationType destinationType) {
        Booking booking = new Booking();
        Intent intent = new Intent(context, DestinationActivity.class);
        Destination destination = DestinationFactory.getDestination(destinationType);
        booking.setCurrentDestination(destination);
        intent.putExtra("booking", booking);
        intent.putExtra("last_activity", "BookView");
        context.startActivity(intent);
    }

    private CardView generateCardsWithQuery(String query) {

        HashMap<Destination.DestinationType, Card> cards = FileUtils.readFile(context
                , "destinations.txt", query);
        CardView cardView = null;

        for (HashMap.Entry<Destination.DestinationType, Card> entry : cards.entrySet()) {
            Destination.DestinationType destinationType = entry.getKey();
            Card card = entry.getValue();


            cardView = LayoutUtils.generateCard(context, null, searchResultContainer, card);
            cardView.setOnClickListener(v -> launchDestinationView(destinationType));
        }

        return cardView;

    }

    private void destroyGeneratedCardViews(LinearLayout container) { container.removeAllViews(); }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        destroyGeneratedCardViews(searchResultContainer);
        generateCardsWithQuery(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (searchResultContainer.getVisibility() != View.VISIBLE)
            searchResultContainer.setVisibility(View.VISIBLE);
    }
}
