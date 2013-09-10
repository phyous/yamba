package com.twitter.android.yamba;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StatusActivity extends Activity {
    private final String TAG = StatusActivity.class.getName();
    private EditText mStatusEditText;
    private TextView mStatusCharCount;
    private Button mStatusSubmitButton;

    private static boolean mStatusCleared = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Created!");
        }

        mStatusEditText = (EditText) findViewById(R.id.status_edit_text);
        mStatusCharCount = (TextView) findViewById(R.id.status_char_count_text);
        mStatusSubmitButton = (Button) findViewById(R.id.status_submit_button);

        setTextCountListener();
        setTextClearListener();
    }

    /**
     * Clears the text in the status text input box when user clicks.
     */
    private void setTextClearListener() {
        mStatusEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mStatusCleared) {
                    mStatusEditText.setText("");
                    mStatusCleared = true;
                }
            }
        });
    }

    /**
     * Set listener to update the character count as the user types
     */
    private void setTextCountListener() {
        mStatusEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateCount(s);
            }
        });
    }

    /**
     * Update the character count and color based on input
     *
     * @param s Text entered in to the EditTextbox
     */
    private void updateCount(Editable s) {
        Resources rez = getResources();
        int maxCharacters = rez.getInteger(R.integer.max_characters);
        int warnCharacter = rez.getInteger(R.integer.warn_characters);
        int errorCharacters = rez.getInteger(R.integer.err_characters);
        int redColor = rez.getColor(R.color.red);
        int orangeColor = rez.getColor(R.color.orange);

        int charCount = s == null ? maxCharacters : maxCharacters - s.length();
        mStatusCharCount.setText(String.format("%d", charCount));

        if (charCount <= warnCharacter && charCount >= errorCharacters) { // warn
            mStatusCharCount.setTextColor(orangeColor);
            mStatusSubmitButton.setEnabled(true);
        } else if (charCount < 0) { // error
            mStatusCharCount.setTextColor(redColor);
            mStatusSubmitButton.setEnabled(false);
        } else { // OK
            mStatusSubmitButton.setEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.status, menu);
        return true;
    }
}
