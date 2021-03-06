package ro.pub.cs.systems.eim.lab03.contactsmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.showHideFields) {
                if ((findViewById(R.id.hiddenContainerLayout)).getVisibility() == View.VISIBLE) {
                    (findViewById(R.id.hiddenContainerLayout)).setVisibility(View.GONE);
                    ((Button)view).setText(R.string.show_additional_fields_main);
                } else if ((findViewById(R.id.hiddenContainerLayout)).getVisibility() == View.GONE) {
                    (findViewById(R.id.hiddenContainerLayout)).setVisibility(View.VISIBLE);
                    ((Button)view).setText(R.string.hide_additional_fields_main);
                }
            } else if (view.getId() == R.id.saveButton) {
                String name = ((EditText)findViewById(R.id.contactNameEditText))
                                .getText().toString();

                String phone = ((EditText)findViewById(R.id.contactPhoneNumberEditText))
                        .getText().toString();

                String email = ((EditText)findViewById(R.id.contactEmailEditText))
                        .getText().toString();

                String address = ((EditText)findViewById(R.id.contactAddressEditText))
                        .getText().toString();

                String jobTitle = ((EditText)findViewById(R.id.contactJobTitleEditText))
                        .getText().toString();

                String company = ((EditText)findViewById(R.id.contactCompanyEditText))
                        .getText().toString();

                String website = ((EditText)findViewById(R.id.contactWebsiteEditText))
                        .getText().toString();

                String im = ((EditText)findViewById(R.id.contactIMEditText))
                        .getText().toString();

                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                if (!name.isEmpty()) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                }
                if (!phone.isEmpty()) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                }
                if (!email.isEmpty()) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                }
                if (!address.isEmpty()) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                }
                if (!jobTitle.isEmpty()) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                }
                if (!company.isEmpty()) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                }
                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                if (!website.isEmpty()) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                    contactData.add(websiteRow);
                }
                if (!im.isEmpty()) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                    contactData.add(imRow);
                }
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                startActivityForResult(intent, 1234);
            } else if (view.getId() == R.id.cancelButton) {
                setResult(Activity.RESULT_CANCELED, new Intent());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1234) {
            setResult(resultCode, new Intent());
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button saveButton = (Button)findViewById(R.id.saveButton);
        Button cancelButton = (Button)findViewById(R.id.cancelButton);
        Button showHideButton = (Button)findViewById(R.id.showHideFields);

        EditText phoneNumberText = (EditText)findViewById(R.id.contactPhoneNumberEditText);
        phoneNumberText.setEnabled(false);

        ButtonListener btnListener = new ButtonListener();
        saveButton.setOnClickListener(btnListener);
        cancelButton.setOnClickListener(btnListener);
        showHideButton.setOnClickListener(btnListener);

        Intent intent = getIntent();
        if (intent != null) {
            String phoneNumber = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phoneNumber != null) {
                phoneNumberText.setText(phoneNumber);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }
    }
}