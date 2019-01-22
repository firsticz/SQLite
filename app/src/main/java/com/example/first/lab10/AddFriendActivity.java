package com.example.first.lab10;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.first.lab10.Model.Friend;
import com.example.first.lab10.utils.DBHelper;


public class AddFriendActivity extends Activity {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mTel;
    private EditText mEmail;
    private EditText mDescription;
    private Button mButtonOK;
    private DBHelper mHelper;

    private int ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getActionBar().setDisplayHomeAsUpEnabled(true);

        mHelper = new DBHelper(this);

        setContentView(R.layout.activity_addfriend);

        mFirstName = (EditText) findViewById(R.id.add_first_name);
        mLastName = (EditText) findViewById(R.id.add_last_name);
        mTel = (EditText) findViewById(R.id.add_tel);
        mEmail = (EditText) findViewById(R.id.add_email);
        mDescription = (EditText) findViewById(R.id.add_description);
        mButtonOK = (Button) findViewById(R.id.button_submit);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            ID = bundle.getInt(Friend.Column.ID);
            String firstName = bundle.getString(Friend.Column.FIRST_NAME);
            String lastName = bundle.getString(Friend.Column.LAST_NAME);
            String tel = bundle.getString(Friend.Column.TEL);
            String email = bundle.getString(Friend.Column.EMAIL);
            String description = bundle.getString(Friend.Column.DESCRIPTION);

            mFirstName.setText(firstName);
            mLastName.setText(lastName);
            mTel.setText(tel);
            mEmail.setText(email);
            mDescription.setText(description);
        }


        mButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(AddFriendActivity.this);
                builder.setTitle(getString(R.string.add_data_title));
                builder.setMessage(getString(R.string.add_data_message));

                builder.setPositiveButton(getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Friend friend = new Friend();
                                friend.setFirstName(mFirstName.getText().toString());
                                friend.setLastName(mLastName.getText().toString());
                                friend.setTel(mTel.getText().toString());
                                friend.setEmail(mEmail.getText().toString());
                                friend.setDescription(mDescription.getText().toString());

                                if (ID == -1) {
                                    mHelper.addFriend(friend);
                                } else {
                                    friend.setId(ID);
                                    mHelper.updateFriend(friend);
                                }
                                finish();
                            }
                        });

                builder.setNegativeButton(getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });


                builder.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }
}
