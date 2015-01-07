package flirtalert.me.flirtalert;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private Button mSelectContactsButton;
    private final int PICK = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSelectContactsButton = (Button)findViewById(R.id.select_contacts_button);
        mSelectContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK);
            }
        });


//        mSelectContactsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, SelectContacts.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {

        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK):
                if (resultCode == Activity.RESULT_OK) {

                    String phoneNumber = null;

                    Uri contactData = data.getData();
                    ContentResolver contentResolver = getContentResolver();
                    Cursor cursor = contentResolver.query(contactData, null, null, null, null);

                    if (cursor.moveToFirst()) {
                        String _ID = ContactsContract.Contacts._ID;
                        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
                        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
                        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

                        String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                        String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
                        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
                        int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

                        if (hasPhoneNumber > 0) {
//                            Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);
//                            new AlertDialog.Builder(this).setTitle("Argh").setMessage(name).setNeutralButton("Close", null).show();
//                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                            new AlertDialog.Builder(this).setTitle(contact_id).setMessage(name).setNeutralButton("Close", null).show();

                        }
//                        String name = cursor.getString(cursor.getColumnIndexOrThrow(Contacts.People.NAME));

                        // TODO Whatever you want to do with the selected contact
                    }
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
