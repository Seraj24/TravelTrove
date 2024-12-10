package com.example.prjtraveltrovesprint;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Debug;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.User;
import com.example.prjtraveltrovesprint.model.UserSession;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProfileManagementActivity extends AppCompatActivity implements ActivityEssentials,
        TextWatcher, OnSuccessListener, OnFailureListener {

    public enum InformationType {
        FN,LN,EMAIL,PASSWORD
    }

    private static final ActivityName ACTIVITY_NAME = ActivityName.PROFILE_MANAGEMENT;
    private static final String LOG_TAG = ACTIVITY_NAME + " ACTIVITY";

    EditText edTextFN, edTextLN, edTextEmail, edTextPassword;
    Button btnEdit, btnSave, btnCancel, btnReturn;
    User user;
    ArrayList<EditText> editTexts;
    ArrayList<Button> dynamicButtons;
    InformationType currentClicked = null;
    HashMap<InformationType, EditText> modifiedInformation = new HashMap<>();
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("user");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.account_information), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initialize();

    }


    @Override
    public void initialize() {

        user = UserSession.getInstance().getUser();
        edTextFN = findViewById(R.id.ed_text_FN_AI);
        edTextLN = findViewById(R.id.ed_text_LN_AI);
        edTextEmail = findViewById(R.id.ed_text_email_AI);
        edTextPassword = findViewById(R.id.ed_text_password_AI);
        btnEdit = findViewById(R.id.btn_edit_AI);
        btnSave = findViewById(R.id.btn_save_AI);
        btnCancel = findViewById(R.id.btn_cancel_AI);
        btnReturn = findViewById(R.id.btn_return_AI);

        edTextFN.setText(user.getFirstName());
        edTextLN.setText(user.getLastName());
        edTextEmail.setText(user.getEmail());
        edTextPassword.setText(user.getPassword());

        editTexts = new ArrayList<>(Arrays.asList(edTextFN, edTextLN, edTextEmail, edTextPassword));
        dynamicButtons = new ArrayList<>(Arrays.asList(btnCancel, btnSave));

        // Attach listeners to determine which kind of information field
        // the user interacting with like FN, LN... and the ability to retrieve user input from the field
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(this);
            editText.setOnFocusChangeListener((v, hasFocus) ->
                    {
                        currentClicked = getInformationType(editText);
                    }

                    );
        }

        btnEdit.setOnClickListener(v -> handleBtnEdit());
        btnCancel.setOnClickListener(v -> handleBtnCancel());
        btnReturn.setOnClickListener(v -> ActivitiesUtils.returnToPreviousView(this));
        btnSave.setOnClickListener(v -> handleBtnSave());
    }

    private void updateDatabase() {

        for (Map.Entry<InformationType, EditText> element : modifiedInformation.entrySet()) {
            String elementValue = element.getValue().getText().toString();
            switch (element.getKey()) {

                case FN:
                    user.setFirstName(elementValue);
                    break;
                case LN:
                    user.setLastName(elementValue);
                    break;
                case EMAIL:
                    user.setEmail(elementValue);
                    break;
                case PASSWORD:
                    break;
                default:
                    break;
            }
        }

        userRef.child(String.valueOf(user.getId())).setValue(user).addOnSuccessListener(this)
                .addOnFailureListener(this);

    }

    private void handleBtnEdit() {

        final String btnEditLogTag = LOG_TAG + " - BTN EDIT";

        try {
            enableEditTextViews(editTexts);
            configureButton(btnCancel, false);
            configureButton(btnEdit, true);
        }
        catch (NullPointerException n) {
            Log.e(btnEditLogTag,
                    "Edit Button could not continue the task since a null value occurred "
                            + n.getMessage());
        }
        catch (Resources.NotFoundException r) {
            Log.e(btnEditLogTag,
                    "Edit Button could not continue the task, some resources do note exist "
                            + r.getMessage());
        }
        catch (Exception e) {
            Log.e(btnEditLogTag,
                    "Edit Button could not continue the task, an unexpected error occurred "
                            + e.getMessage());
        }

    }

    private void handleBtnCancel() {

        final String btnCancelLogTag = LOG_TAG + " - BTN CANCEL";

        try {
            revertEditTextViews();
            disableEditTextViews(editTexts);
            configureButton(btnEdit, false);
            configureButtons(dynamicButtons, true);
            currentClicked = null;
            modifiedInformation.clear();
        }
        catch (NullPointerException n) {
            Log.e(btnCancelLogTag,
                    "Cancel Button could not continue the task since a null value occurred "
                            + n.getMessage());
        }
        catch (Resources.NotFoundException r) {
            Log.e(btnCancelLogTag,
                    "Cancel Button could not continue the task, some resources do note exist "
                            + r.getMessage());
        }
        catch (Exception e) {
            Log.e(btnCancelLogTag,
                    "Cancel Button could not continue the task, an unexpected error occurred "
                            + e.getMessage());
        }

    }

    private void handleBtnSave() {

        try {
            updateDatabase();
            disableEditTextViews(editTexts);
            configureButtons(dynamicButtons, true);
            configureButton(btnEdit, false);
        }
        catch (Exception e) {

        }


    }

    private void enableEditTextViews(ArrayList<EditText> editTexts) {
        for (EditText editText : editTexts) {
            editText.setEnabled(true);
        }
    }

    private void configureButtons(ArrayList<Button> btnList, boolean revert) {
        for (Button btn : btnList) {
            configureButton(btn, revert);

        }
    }

    private void configureButton(Button btn, boolean revert) {
        if (btn == null) {
            Log.e(LOG_TAG, "Provided null button - configureButton function");
            return;
        }

        int color = revert ? R.color.light_gray :
                (btn.getId() == R.id.btn_save_AI) ? R.color.green :
                        (btn.getId() == R.id.btn_cancel_AI) ? R.color.red : R.color.black;

        btn.setEnabled(!revert);
        btn.setBackgroundColor(getResources().getColor(color));
    }

    private void disableEditTextViews(ArrayList<EditText> editTexts) {
        for (EditText editText : editTexts) {
            editText.setEnabled(false);
        }
    }

    /* Will only revert a edit text if it was changed */
    private void revertEditTextViews() {
        if (modifiedInformation == null || !changesOccurred()) return;

        for (Map.Entry<InformationType, EditText> entry : modifiedInformation.entrySet()) {
            switch (entry.getKey()) {
                case FN:
                    entry.getValue().setText(user.getFirstName());
                    break;
                case LN:
                    entry.getValue().setText(user.getLastName());
                    break;
                case EMAIL:
                    entry.getValue().setText(user.getEmail());
                    break;
                case PASSWORD:
                    entry.getValue().setText(user.getPassword());
                    break;
                default:
                    break;
            }
        }
    }


    private boolean changesOccurred() {
        if (modifiedInformation == null) {
            throw new NullPointerException("Can't revert the text to original since modified information hash map is null");
        }
        return !modifiedInformation.isEmpty();
    }

    private InformationType getInformationType(EditText editText) {

        if (editText == null) {
            Log.e(LOG_TAG, "editText is null in getInformationType()");
            return null;
        }
        InformationType informationType = null;

        int editTextId = editText.getId();

        if (editTextId == R.id.ed_text_FN_AI) {
            informationType = InformationType.FN;
        }
        else if (editTextId == R.id.ed_text_LN_AI) {
            informationType = InformationType.LN;
        }
        else if (editTextId == R.id.ed_text_email_AI) {
            informationType = InformationType.EMAIL;
        }
        else if (editTextId == R.id.ed_text_password_AI) {
            informationType = InformationType.PASSWORD;

        }

        return informationType;

    }

    private EditText getEditText(InformationType it) {

        if (it == null) {
            Log.e(LOG_TAG, "it (InformationType) is null getEditText()");
            return null;
        }

        EditText editText = null;
        switch (it) {
            case FN:
                editText = edTextFN;
                break;
            case LN:
                editText = edTextLN;
                break;
            case EMAIL:
                editText = edTextEmail;
                break;
            case PASSWORD:
                editText = edTextPassword;
                break;
            default:
                break;
        }

        return editText;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        configureButton(btnSave, false);
        Log.d("TEST DEBUG", "Current Clicked: " + currentClicked);
    }

    @Override
    public void afterTextChanged(Editable editable) {

        if (editable != null && currentClicked != null) {


            if (modifiedInformation.containsKey(currentClicked)) {
                modifiedInformation.remove(currentClicked);
            }

            Log.d("HASH MAP ADDED", "added current element to hash map");
            // Only add if the key doesn't already exist
            modifiedInformation.put(currentClicked, getEditText(currentClicked));
        }

    }

    @Override
    public void onSuccess(Object o) {
        Toast.makeText(this, "The information has been updated successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(this, "Failed to update the information!", Toast.LENGTH_SHORT).show();
    }
}