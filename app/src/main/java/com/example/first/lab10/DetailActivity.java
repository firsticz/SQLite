package com.example.first.lab10;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.first.lab10.Model.Friend;
import com.example.first.lab10.utils.DBHelper;


public class DetailActivity extends Activity {
    DBHelper mHelper;

    private TextView mFirstName;
    private TextView mLastName;
    private TextView mTel;
    private TextView mEmail;
    private TextView mDescription;
    private String id = "";

    private Button mButtonEdit;
    private Button mButtonDelete;

    private Friend mFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getActionBar().setDisplayHomeAsUpEnabled(true);

        mHelper = new DBHelper(this);

        Bundle bundle  = getIntent().getExtras();

        if (bundle != null) {
            id = bundle.getString(Friend.Column.ID);
        }

        setContentView(R.layout.activity_detail);

        mFirstName = (TextView) findViewById(R.id.detail_first_name);
        mLastName = (TextView) findViewById(R.id.detail_last_name);
        mTel = (TextView) findViewById(R.id.detail_tel);
        mEmail = (TextView) findViewById(R.id.detail_email);
        mDescription = (TextView) findViewById(R.id.detail_description);
        mButtonDelete = (Button) findViewById(R.id.button_delete);
        mButtonEdit = (Button) findViewById(R.id.button_edit);

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle(getString(R.string.alert_title));
                builder.setMessage(getString(R.string.alert_message));

                builder.setPositiveButton(getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mHelper.deleteFriend(id);

                                Toast.makeText(getApplication(),
                                        "Deleted", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });

                builder.setNegativeButton(getString(android.R.string.cancel), null);

                builder.show();

            }
        });

        mButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(DetailActivity.this,
                        AddFriendActivity.class);

                updateIntent.putExtra(Friend.Column.ID, mFriend.getId());
                updateIntent.putExtra(Friend.Column.FIRST_NAME, mFriend.getFirstName());
                updateIntent.putExtra(Friend.Column.LAST_NAME, mFriend.getLastName());
                updateIntent.putExtra(Friend.Column.TEL, mFriend.getTel());
                updateIntent.putExtra(Friend.Column.EMAIL, mFriend.getEmail());
                updateIntent.putExtra(Friend.Column.DESCRIPTION, mFriend.getDescription());

                startActivity(updateIntent);
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
        });
        initialData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initialData();
    }

    private void initialData() {
        mFriend = mHelper.getFriend(id);
        mFirstName.setText(mFriend.getFirstName());
        mLastName.setText(mFriend.getLastName());
        mTel.setText(mFriend.getTel());
        mEmail.setText(mFriend.getEmail());
        mDescription.setText(mFriend.getDescription());
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