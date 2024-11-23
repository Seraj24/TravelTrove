package com.example.prjtraveltrovesprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.Card;
import com.example.prjtraveltrovesprint.model.TripPackage;
import com.example.prjtraveltrovesprint.model.User;
import com.example.prjtraveltrovesprint.model.UserSession;
import com.example.prjtraveltrovesprint.model.factory.CardFactory;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;
import com.example.prjtraveltrovesprint.utils.LayoutUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class DestinationActivity extends AppCompatActivity implements ActivityEssentials, OnSuccessListener, OnFailureListener {

    Destination.DestinationType destinationType;
    Destination currentDestination;
    TextView destinationTitle, addToFavoritesText;
    Button returnButton;
    LinearLayout packagesParentContainer, addToFavoritesContainer;
    CardView allHotelsCard;
    ImageView destinationBanner, addToFavoritesIcon;

    boolean isFavorite;

    String lastActivity;

    User user = UserSession.getInstance().getUser();
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("user");

    private static final String LOG_TAG = "DESTINATION ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_destination);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.destination), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            currentDestination = ActivitiesUtils.retrieveCurrentDestination(this);

            lastActivity = getIntent().getStringExtra("last_activity");
            if (lastActivity == null) {
                Log.e(LOG_TAG, "Last Activity is null");
                finish();
            }

            destinationType = currentDestination.getDestinationType();

            initialize();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error while initializing destination activity: " + e.getMessage(), e);
            finish();
        }




    }


    public void initialize() {
        returnButton = findViewById(R.id.destination_return_button);
        allHotelsCard = findViewById(R.id.all_hotels_card);
        packagesParentContainer = findViewById(R.id.packages_container);
        destinationTitle = findViewById(R.id.destination_title);
        destinationBanner = findViewById(R.id.destination_banner);
        addToFavoritesContainer = findViewById(R.id.add_to_favorites_destination);
        addToFavoritesText = findViewById(R.id.add_to_favorites_text);
        addToFavoritesIcon = findViewById(R.id.add_to_favorites_icon);

        String title = "Explore " + currentDestination.getTitle();
        destinationTitle.setText(title) ;
        destinationBanner.setImageResource(currentDestination.getImage());

        isFavorite = isAlreadyFavoriteDestination();

        setupAddToFavorites();

        generatePackageCards();

        returnButton.setOnClickListener(v ->
                    returnToPreviousActivity()
                );

        allHotelsCard.setOnClickListener(v ->
                launchAllHotelsView()
                );
    }


    private void launchAllHotelsView() {
        Intent intent = new Intent(DestinationActivity.this, DestinationActivity.class);
        intent.putExtra("destination_details", currentDestination);
        startActivity(intent);
    }

    private void returnToPreviousActivity() {
        Intent intent;
        if (Objects.equals(lastActivity, "SavedDestinationsActivity")) {
            intent = new Intent(DestinationActivity.this, SavedDestinationsActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        finish();

    }

    private void generatePackageCards() {
        // ArrayList<CardView> cardViews;
        Card card = CardFactory.getCard(destinationType);
        CardView cardView = LayoutUtils.generateCard(this.getBaseContext(), null,
                packagesParentContainer, card);

        if (cardView == null) {
            Log.e("generatePackageCards", "cardView is null");
            return;
        }

        cardView.setOnClickListener(v ->
                launchCardView()
        );


    }

    private void launchCardView() {
        Intent intent = new Intent(DestinationActivity.this, DestinationPackageActivity.class);
        intent.putExtra("destination_details", currentDestination);
        intent.putExtra("last_activity", this.getClass().getName());
        startActivity(intent);
    }

    private boolean isAlreadyFavoriteDestination() {

        if (user == null) {
            Log.e(LOG_TAG + " - isAlreadyFavoriteDestination()", "Can't determine " +
                    "user destinations since user is null");

        }

        if (user.getDestinations() == null) {
            Log.d(LOG_TAG + " - isAlreadyFavoriteDestination()", "Can't determine " +
                    "user destinations since user destinations list is null");
            return false;
        }

        for (Destination destination : user.getDestinations()) {
            if (destination.getDestinationType() == currentDestination.getDestinationType()) {
                return true;
            }
        }

        return false;
    }

    private void setupAddToFavorites() {

        if (addToFavoritesContainer == null || addToFavoritesIcon == null || addToFavoritesText == null) {
            Log.e(LOG_TAG + " - setupAddToFavorites()", "Failed to setup up add to favorites" +
                    ", null values used");
        }

        if (isFavorite) {
            addToFavoritesContainer.setOnClickListener(null);
            addToFavoritesContainer.setOnClickListener(v ->
                    removeFromFavorites()
            );
            addToFavoritesContainer.setBackgroundColor(getResources().getColor(R.color.light_gray));
            addToFavoritesText.setText("Favorite");
            addToFavoritesIcon.setImageResource(R.drawable.check_green_success_icon);
        }
        else {
            addToFavoritesContainer.setOnClickListener(v ->
                    addToFavorites()
            );
            addToFavoritesText.setText(R.string.add_to_your_favorites);
            addToFavoritesIcon.setImageResource(R.drawable.favorite_icon);
        }
    }


    private void addToFavorites() {
        int childIndex =  user.getDestinations().size();
        userRef.child(String.valueOf(user.getId())).child("destinations")
                .child("" + childIndex)
                .setValue(currentDestination).addOnSuccessListener(this).addOnFailureListener(this);

    }

    private void removeFromFavorites() {
        int childIndex = -1;
        int indexCounter = 0;
        for (Destination destination: user.getDestinations()) {
            if (destination.getDestinationType() == currentDestination.getDestinationType()) {
                childIndex = indexCounter;
                break;
            }
            indexCounter++;
        }

        if (childIndex < 0) {
            Log.e(LOG_TAG, "Error: Can't find the destination in the user list ");
            Toast.makeText(this, "Failed to remove destination from your favorites!"
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        user.getDestinations().remove(childIndex);

        userRef.child(String.valueOf(user.getId())).child("destinations").child("" + childIndex)
                .removeValue()
                .addOnSuccessListener(this)
                .addOnFailureListener(this);

    }

    @Override
    public void onSuccess(Object o) {

        if (isFavorite) {
            Toast.makeText(this, "Destination has been removed from your favorites successfully!"
                    , Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this, "Destination has been added to your favorites successfully!"
                    , Toast.LENGTH_SHORT).show();
            // refresh add to favorites state to added by updating current user session
            // and updating the container
            user.getDestinations().add(currentDestination);
        }

        isFavorite = !isFavorite;


        setupAddToFavorites();


    }

    @Override
    public void onFailure(@NonNull Exception e) {

        if (isFavorite) {
            Toast.makeText(this, "Failed to remove destination from your favorites!"
                    , Toast.LENGTH_SHORT).show();

            Log.e(LOG_TAG, "Failed to remove destination from user's favorites " + e.getMessage(), e);

        }
        else {
            Toast.makeText(this, "Failed to add destination to your favorites!"
                    , Toast.LENGTH_SHORT).show();

            Log.e(LOG_TAG, "Failed to add destination to user's favorites " + e.getMessage(), e);
        }

    }


}