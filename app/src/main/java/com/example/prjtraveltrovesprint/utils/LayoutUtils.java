package com.example.prjtraveltrovesprint.utils;

import static android.provider.Settings.System.getString;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.LogPrinter;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.prjtraveltrovesprint.R;
import com.example.prjtraveltrovesprint.model.Card;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* Layout utility class to generate/reorder cards */
public class LayoutUtils {


    /* *To apply builder design pattern*
        This function responsible of creating a new card and assign it to the hsv
   */
    public static CardView generateCard(Context activity,
                                    HorizontalScrollView horizontalScrollView,
                                    LinearLayout container, Card card) {

        // Create the CardView
        CardView cardView = new CardView(activity);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(convertToDp(activity, 200), convertToDp(activity, 250));
        cardParams.setMarginEnd(convertToDp(activity, 12));
        cardView.setLayoutParams(cardParams);
        cardView.setCardBackgroundColor(Color.WHITE);
        cardView.setRadius(convertToDp(activity, 12));
        cardView.setCardElevation(convertToDp(activity, 6));

        // Create a LinearLayout for the CardView content
        LinearLayout cardContentLayout = new LinearLayout(activity);
        cardContentLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        cardContentLayout.setOrientation(LinearLayout.VERTICAL);
        int cardLayoutPadding = convertToDp(activity, 12);
        cardContentLayout.setPadding(cardLayoutPadding, cardLayoutPadding, cardLayoutPadding, cardLayoutPadding);

        // Create an ImageView for the image inside the CardView
        ImageView imageView = new ImageView(activity);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, convertToDp(activity, 150)));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageDrawable(ContextCompat.getDrawable(activity, card.getImage()));
        imageView.setContentDescription(card.getContentDescription());

        // Create a TextView for the title below the image
        TextView textView = new TextView(activity);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.topMargin = convertToDp(activity, 8);
        textView.setLayoutParams(textParams);
        textView.setText(card.getTitle());
        textView.setTextSize(16);
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);

        // Add the ImageView and TextView to the LinearLayout
        cardContentLayout.addView(imageView);
        cardContentLayout.addView(textView);

        // Add the LinearLayout to the CardView
        cardView.addView(cardContentLayout);

        if (container != null) {
            // Add the CardView to the LinearLayout
            container.addView(cardView);
        }
        else {
            // Add the LinearLayout to the HorizontalScrollView
            horizontalScrollView.addView(cardView);
        }

        return cardView;

    }

    /* My logic to re-order cards by: removing the children from cards parent container->
        Removing parent container of all cards from hsv->adding them again with random order to parent
        -> add the parent to the hsv
     */
    public static void reorderCards(HorizontalScrollView horizontalScrollView,
                                    LinearLayout parentOfCards,ArrayList<CardView> views) {
        Random random = new Random();
        parentOfCards.removeAllViews();
        horizontalScrollView.removeAllViews();
        ArrayList<Integer> tempHold = new ArrayList<>(views.size());
        for (int i = 0; i < views.size(); i++) {
            int generatedIndex = random.nextInt(views.size());

            // Ensures that we don't add the same view multiple times
            while (tempHold.contains(generatedIndex)) {
                generatedIndex = random.nextInt(views.size());
            }

            parentOfCards.addView(views.get(generatedIndex));

            tempHold.add(generatedIndex);
        }

        // Apply the new parent to the hsv
        horizontalScrollView.addView(parentOfCards);
    }

    private static int convertToDp(Context context, int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }
}
