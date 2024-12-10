package com.example.prjtraveltrovesprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.Card;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.User;
import com.example.prjtraveltrovesprint.model.UserSession;
import com.example.prjtraveltrovesprint.model.factory.CardFactory;
import com.example.prjtraveltrovesprint.ui.account.AccountFragment;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;
import com.example.prjtraveltrovesprint.utils.LayoutUtils;

public class SavedDestinationsActivity extends AppCompatActivity implements ActivityEssentials {

    HorizontalScrollView hsvFavorites;
    LinearLayout favoritesContainer;
    Button btnReturn;

    User user = null;

    private static final ActivityName ACTIVITY_NAME = ActivityName.SAVED_DESTINATIONS;
    private static final String LOG_TAG = ACTIVITY_NAME + " ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_saved_destinations);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.saved_destinations), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initialize();
    }


    @Override
    public void initialize() {
        hsvFavorites = findViewById(R.id.hsv_favorites);
        favoritesContainer = findViewById(R.id.favorites_container);
        btnReturn = findViewById(R.id.favorites_return_btn);
        user = UserSession.getInstance().getUser();

        generateFavoritesCards();

        btnReturn.setOnClickListener(v -> ActivitiesUtils.returnToPreviousView(this));
    }

    private void generateFavoritesCards() {

        for (Destination destination: user.getDestinations()) {
            Card card = CardFactory.getCard(destination.getDestinationType());
            CardView cardView = LayoutUtils.generateCard(this.getBaseContext(), null,
                    favoritesContainer, card);

            if (cardView == null) {
                Log.e(LOG_TAG + " - generateFavoritesCards()", "cardView is null");
                return;
            }

            cardView.setOnClickListener(v ->
                    launchDestination(destination)
            );
        }

    }

    private void launchDestination(Destination currentDestination) {
        Intent intent = new Intent(SavedDestinationsActivity.this, DestinationActivity.class);
        intent.putExtra("destination_details", currentDestination);
        intent.putExtra("last_activity", "SavedDestinationsActivity");
        startActivity(intent);
        finish();
    }

}