package com.example.perds.andrew_perdec_a3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declare variables for the ui elements
    private ArrayList<SocialMedia> socialMediaList;
    private ListView lstSocialMedia;
    private int arrayLocation;
    private static String my_string = "socialMedia";
    private static String delete_string = "delete";
    private static String state_string = "saveMe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the variables
        socialMediaList = new ArrayList<SocialMedia>();
        lstSocialMedia = (ListView) findViewById(R.id.lstSocialNetworks);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            socialMediaList = savedInstanceState.getParcelableArrayList(state_string);
            setList();
        }

        // Add a click listener to the listview so that when an item is selected
        // it will open the edit activity
        lstSocialMedia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // When an item is clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String listItem = ((TextView) view).getText().toString();
                SocialMedia listItemSM = null;

                // Search the array to find which item has been clicked
                for (int i = 0; i < socialMediaList.size(); i++) {
                    // When the item name matches the name of the item in the array
                    if (socialMediaList.get(i).getName().equals(listItem)) {
                        // set a temporary social media object
                        listItemSM = socialMediaList.get(i);
                        arrayLocation = i;
                    }
                }

                // Create an intent to send the data
                Intent intent = new Intent(MainActivity.this, EditActivity.class);

                //Fill the intent with the social media object
                intent.putExtra(my_string, listItemSM);

                //Start activity and wait for result
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inserting the menu
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Build the dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Create the edit text component for the dialog box
        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT);

        // Fill the dialog box with all the required elements
        builder.setTitle("New Social Media").setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Add item to the social media array
                        socialMediaList.add(new SocialMedia(input.getText().toString()));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();

        // Call function to populate the listview
        setList();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check to see if the data coming back is the correct requestcode and result code
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Boolean delete = data.getBooleanExtra(delete_string, false);
                if (delete) {
                    // If deleted the item will be removed
                    socialMediaList.remove(arrayLocation);
                } else {
                    // Add the modified social media object back to the list
                    SocialMedia socialMedia = data.getParcelableExtra(my_string);
                    socialMediaList.get(arrayLocation).update(socialMedia);
                }
            }
            // If back is pressed do nothing
            // Set the list view with the new elements
            setList();
        }
    }

    public void setList() {
        // Array adapter to will with the social media list
        ArrayAdapter<SocialMedia> myArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, socialMediaList);

        lstSocialMedia.setAdapter(myArrayAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Add the arrayList to the savedInstanceState
        savedInstanceState.putParcelableArrayList(state_string, socialMediaList);

        super.onSaveInstanceState(savedInstanceState);
    }
}