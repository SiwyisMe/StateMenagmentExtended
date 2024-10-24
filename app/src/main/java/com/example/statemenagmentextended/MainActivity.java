package com.example.statemenagmentextended;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModel;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {
    private TextView counterTextView;
    private TextView optionalTextView;
    private EditText userInput;
    private CheckBox checkBox;
    private Switch themeSwitch;
    private Button incrementButton;
    private LinearLayout Layout;
    private int counter = 0;
    private String userInputText = "";
    private boolean checkBoxState = false;
    private boolean switchState = false;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        sharedPreferences = getSharedPreferences("AppState", MODE_PRIVATE);
        initializeViews();
        loadState();
        setupListeners();
        updateViews();
    }

    private void loadState() {
        counter = sharedPreferences.getInt("counter", 0);
        userInputText = sharedPreferences.getString("userInputText", "");
        checkBoxState = sharedPreferences.getBoolean("checkBoxState", false);
        switchState = sharedPreferences.getBoolean("switchState", false);
    }

    private void saveState() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("counter", counter);
        editor.putString("userInputText", userInputText);
        editor.putBoolean("checkBoxState", checkBoxState);
        editor.putBoolean("switchState", switchState);
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    private void initializeViews() {
        counterTextView = findViewById(R.id.counterTextView);
        optionalTextView = findViewById(R.id.optionalTextView);
        userInput = findViewById(R.id.userInput);
        checkBox = findViewById(R.id.checkBox);
        themeSwitch = findViewById(R.id.themeSwitch);
        incrementButton = findViewById(R.id.incrementButton);
        Layout = findViewById(R.id.layout);
    }

    private void setupListeners() {
        incrementButton.setOnClickListener(v -> {
            counter++;
            counterTextView.setText(String.valueOf(counter));
            saveState();
        });

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkBoxState = isChecked;
            optionalTextView.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            saveState();
        });

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            switchState = isChecked;
            updateTheme(isChecked);
            saveState();
        });

        userInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                userInputText = s.toString();
                saveState();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });    
    }

    private void updateViews() {
        counterTextView.setText(String.valueOf(counter));
        userInput.setText(userInputText);
        checkBox.setChecked(checkBoxState);
        optionalTextView.setVisibility(checkBoxState ? View.VISIBLE : View.GONE);
        themeSwitch.setChecked(switchState);
        updateTheme(switchState);
    }

    private void updateTheme(boolean isDarkMode) {
        if (isDarkMode) {
            Layout.setBackgroundColor(getResources().getColor(android.R.color.black));
            counterTextView.setTextColor(getResources().getColor(android.R.color.white));
            optionalTextView.setTextColor(getResources().getColor(android.R.color.white));
            userInput.setTextColor(getResources().getColor(android.R.color.white));
            userInput.setHintTextColor(getResources().getColor(android.R.color.white));
            checkBox.setTextColor(getResources().getColor(android.R.color.white));
            themeSwitch.setTextColor(getResources().getColor(android.R.color.white));
        } else {
            Layout.setBackgroundColor(getResources().getColor(android.R.color.white));
            counterTextView.setTextColor(getResources().getColor(android.R.color.black));
            optionalTextView.setTextColor(getResources().getColor(android.R.color.black));
            userInput.setTextColor(getResources().getColor(android.R.color.black));
            userInput.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
            checkBox.setTextColor(getResources().getColor(android.R.color.black));
            themeSwitch.setTextColor(getResources().getColor(android.R.color.black));
        }
    }
}
