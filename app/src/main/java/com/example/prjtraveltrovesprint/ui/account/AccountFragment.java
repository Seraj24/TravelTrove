package com.example.prjtraveltrovesprint.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prjtraveltrovesprint.AdditionalServicesActivity;
import com.example.prjtraveltrovesprint.AirlinesActivity;
import com.example.prjtraveltrovesprint.BookingHistoryActivity;
import com.example.prjtraveltrovesprint.ProfileManagementActivity;
import com.example.prjtraveltrovesprint.SavedDestinationsActivity;
import com.example.prjtraveltrovesprint.SignInActivity;
import com.example.prjtraveltrovesprint.databinding.FragmentAccountBinding;
import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.User;
import com.example.prjtraveltrovesprint.model.UserSession;

public class AccountFragment extends Fragment implements ActivityEssentials {

    private FragmentAccountBinding binding;
    Button btnBookingHistory, btnSavedDestinations, btnAccountSettings, btnLogOut;
    User user;
    TextView greetingTitle;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initialize();

        return root;
    }

    public void initialize() {

        btnBookingHistory = binding.bookingHistoryButton;
        btnSavedDestinations = binding.savedDestinationsButton;
        btnAccountSettings = binding.accountSettingsButton;
        btnLogOut = binding.logoutButton;

        String greetingTitleGreet = "Hello, ";
        greetingTitle = binding.userName;

        UserSession userSession = UserSession.getInstance();

        user = userSession.getUser();

        greetingTitleGreet += user.getFirstName();

        greetingTitle.setText(greetingTitleGreet);

        btnBookingHistory.setOnClickListener(v ->
                launchBookingHistory()
                );

        btnSavedDestinations.setOnClickListener(v ->
                launchSavedDestinationsActivity()
                );

        btnAccountSettings.setOnClickListener(v ->
                launchAccountSettingsActivity()
                );

        btnLogOut.setOnClickListener(v ->
                logOut()
                );
    }

    private void launchAccountSettingsActivity() {
        Intent intent = new Intent(getActivity(), ProfileManagementActivity.class);
        startActivity(intent);
    }

    private void launchBookingHistory() {
        Intent intent = new Intent(getActivity(), BookingHistoryActivity.class);
        startActivity(intent);
    }

    private void logOut() {
        user = null;
        UserSession.getInstance().setUser(user);
        launchSignInActivity();

    }

    private void launchSignInActivity() {
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void launchSavedDestinationsActivity() {
        Intent intent = new Intent(getActivity(), SavedDestinationsActivity.class);
        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}