package com.example.perds.andrew_perdec_a3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    // Declare elements
    EditText edtSocialMediaName;
    EditText edtUserName;
    EditText edtPassword;
    private static String my_string = "socialMedia";
    private static String delete_string = "delete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Initialize variables with elements
        edtSocialMediaName = (EditText) findViewById(R.id.edtSiteName);
        edtUserName = (EditText) findViewById(R.id.edtUserId);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        // Intent to grab the passed object
        Intent intent = getIntent();

        SocialMedia socialMedia = intent.getParcelableExtra(my_string);

        //
        edtSocialMediaName.setText(socialMedia.getName());
        if (socialMedia.getUserName() != null) {
            edtUserName.setText(socialMedia.getUserName());
        }
        if (socialMedia.getPassword() != null) {
            edtPassword.setText(socialMedia.getPassword());
        }

    }

    public void removeAccount(View view) {
        // If cancelled return a delete phrase
        Intent intent = new Intent();

        intent.putExtra(delete_string, true);

        setResult(RESULT_OK, intent);

        finish();
    }

    public void updateAccount(View view) {

        // If saved create a social media object with the new data
        SocialMedia socialMedia = new SocialMedia(edtSocialMediaName.getText().toString(), edtUserName.getText().toString(), edtPassword.getText().toString());

        // Intent created and send back with an ok result
        Intent intent = new Intent();

        intent.putExtra(delete_string, false);

        intent.putExtra(my_string, socialMedia);

        setResult(RESULT_OK, intent);

        finish();
    }
}
