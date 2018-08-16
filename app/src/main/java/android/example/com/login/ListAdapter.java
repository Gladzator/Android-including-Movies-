package android.example.com.login;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import data.LoginContract;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.GuestViewHolder> {

    // Holds on to the cursor to display the waitlist
    private Cursor mCursor;
    private Context mContext;

    /**
     * Constructor for the CustomCursorAdapter that initializes the Context.
     *
     * @param mContext the current Context
     */
    public ListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        View view = LayoutInflater.from(mContext)
                   .inflate(R.layout.list_item, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {

        int nameIndex = mCursor.getColumnIndex(LoginContract.WaitlistEntry.COLUMN_USER_NAME);
        int passwordIndex =mCursor.getColumnIndex(LoginContract.WaitlistEntry.COLUMN_PASSWORD);
        int idIndex = mCursor.getColumnIndex(LoginContract.WaitlistEntry._ID);

        mCursor.moveToPosition(position);


        final int id = mCursor.getInt(idIndex);
        String username = mCursor.getString(nameIndex);
        String password = mCursor.getString(passwordIndex);


        holder.nameTextView.setText(username);
        holder.passwordTextView.setText(password);
        holder.itemView.setTag(id);

    }


    @Override
    public int getItemCount() {
        if(mCursor==null) {
            return 0;
        }
        return mCursor.getCount();
    }

    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }
    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class GuestViewHolder extends RecyclerView.ViewHolder {

        // Will display the guest name
        TextView nameTextView;
        // Will display the party size number
        TextView passwordTextView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         */
        public GuestViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            passwordTextView = (TextView) itemView.findViewById(R.id.password_text_view);
        }

    }
}
