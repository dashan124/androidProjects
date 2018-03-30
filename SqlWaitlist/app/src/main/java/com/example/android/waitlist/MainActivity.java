package com.example.android.waitlist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;

import com.example.android.waitlist.data.TestUtils;
import com.example.android.waitlist.data.WaitlistContract;
import com.example.android.waitlist.data.WaitlistDbHelper;


public class MainActivity extends AppCompatActivity {

    private GuestListAdapter mAdapter;

    private SQLiteDatabase mDb;
    private EditText mNewGuestNameEditText;
    private EditText mNewPartySizeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView waitlistRecyclerView;

        // Set local attributes to corresponding views
        waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);
        mNewGuestNameEditText=(EditText)findViewById(R.id.person_name_edit_text);
        mNewPartySizeEditText=(EditText) findViewById(R.id.party_count_edit_text);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Create an adapter for that cursor to display the data
       // mAdapter = new GuestListAdapter(this);
        WaitlistDbHelper dbHelper=new WaitlistDbHelper(this);

        mDb=dbHelper.getWritableDatabase();
        //TestUtils.insertFakeData(mDb);
        Cursor cursor=getAllGuests();
        mAdapter=new GuestListAdapter(this,cursor);

        // Link the adapter to the RecyclerView
        waitlistRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //return false;
                long id=(long) viewHolder.itemView.getTag();
                removeGuest(id);
                mAdapter.swapCursor(getAllGuests());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id=(long) viewHolder.itemView.getTag();
                removeGuest(id);
                mAdapter.swapCursor(getAllGuests());
            }
        }).attachToRecyclerView(waitlistRecyclerView);

    }

    public void addToWaitlist(View view) {
        if(mNewGuestNameEditText.getText().length()==0||
                mNewPartySizeEditText.getText().length()==0){
            return;
        }
        int PartySize=1;
        try {
            PartySize= Integer.parseInt(mNewPartySizeEditText.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        addNewGuest(mNewGuestNameEditText.getText().toString(),PartySize);
        mAdapter.swapCursor(getAllGuests());
        mNewPartySizeEditText.clearFocus();
        mNewGuestNameEditText.getText().clear();
        mNewPartySizeEditText.getText().clear();

    }

    private Cursor getAllGuests(){
       return mDb.query(
                WaitlistContract.WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP
        );
    }
    private long addNewGuest(String name,int partySize){
        ContentValues contentValues=new ContentValues();
        contentValues.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME,name);
        contentValues.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE,partySize);
        return mDb.insert(WaitlistContract.WaitlistEntry.TABLE_NAME,null,contentValues);
    }
    private boolean removeGuest(long id){
        return mDb.delete(WaitlistContract.WaitlistEntry.TABLE_NAME,
                WaitlistContract.WaitlistEntry._ID + "=" + id,null)>0;
    }


}
