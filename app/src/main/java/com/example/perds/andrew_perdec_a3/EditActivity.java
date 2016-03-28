package com.example.perds.andrew_perdec_a3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    // Declare elements
    EditText edtSocialMediaName;
    EditText edtUserName;
    EditText edtPassword;

    // Declare state strings
    private static String my_string = "socialMedia";
    private static String delete_string = "delete";
    private static String alert_dialog_state_string = "saveMe";

    // Alert Dialog state
    private boolean alertDialogOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Initialize variables with elements
        edtSocialMediaName = (EditText) findViewById(R.id.edtSiteName);
        edtUserName = (EditText) findViewById(R.id.edtUserId);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            alertDialogOpen = savedInstanceState.getBoolean(alert_dialog_state_string);

            // Open the alert dialog if it were already open
            if (alertDialogOpen) {
                confirmAccountRemoval();
            }
        }

        // Intent to grab the passed object
        Intent intent = getIntent();

        SocialMedia socialMedia = intent.getParcelableExtra(my_string);

        // Set the Edit text boxs
        edtSocialMediaName.setText(socialMedia.getName());
        if (socialMedia.getUserName() != null) {
            edtUserName.setText(socialMedia.getUserName());
        }
        if (socialMedia.getPassword() != null) {
            edtPassword.setText(socialMedia.getPassword());
        }

    }

    public void removeAccount(View view) {
        confirmAccountRemoval();
    }

    public void confirmAccountRemoval() {
        alertDialogOpen = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Fill the dialog box with all the required elements and display
        builder.setTitle("Confirm").setMessage("Are you sure you want to delete this?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If cancelled return a delete phrase
                        Intent intent = new Intent();

                        intent.putExtra(delete_string, true);

                        setResult(RESULT_OK, intent);

                        alertDialogOpen = false;

                        finish();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialogOpen = false;

                dialog.cancel();
            }
        }).show();
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // If the alert dialog box is open save the state and text
        if (alertDialogOpen) {
            savedInstanceState.putBoolean(alert_dialog_state_string, alertDialogOpen);
        }

        super.onSaveInstanceState(savedInstanceState);
    }
}
