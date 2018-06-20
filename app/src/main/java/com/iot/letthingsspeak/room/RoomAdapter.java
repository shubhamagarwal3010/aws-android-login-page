package com.iot.letthingsspeak.room;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iot.letthingsspeak.R;
import com.iot.letthingsspeak.constants.Constants;
import com.iot.letthingsspeak.device.DeviceActivity;

import java.util.List;


public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    public int ROOM_DETAILS_REQUEST_CODE = 9283;
    private Context mContext;
    private List<RoomDetails> roomDetails;

    public RoomAdapter(Context mContext, List<RoomDetails> roomDetails) {
        this.mContext = mContext;
        this.roomDetails = roomDetails;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_home, parent, false);
        RoomViewHolder roomViewHolder = new RoomViewHolder(view);
        return roomViewHolder;
    }

    @Override
    public void onBindViewHolder(final RoomViewHolder holder, final int position) {
        RoomDetails roomDetails = this.roomDetails.get(position);
        holder.roomTitle.setText(roomDetails.getRoomType());


        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }


    @Override
    public int getItemCount() {
        return roomDetails.size();
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView roomImage, overflow;
        TextView roomTitle;

        public RoomViewHolder(View itemView) {
            super(itemView);
            roomImage = itemView.findViewById(R.id.card_news_image);
            roomTitle = itemView.findViewById(R.id.room_category);
            overflow = itemView.findViewById(R.id.overflow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context.getApplicationContext(), DeviceActivity.class);
            RoomDetails roomDetail = roomDetails.get(getAdapterPosition());
            intent.putExtra(Constants.TITLE_KEY, roomDetail.getRoomType());
            ((Activity) context).startActivityForResult(intent, ROOM_DETAILS_REQUEST_CODE);

        }
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.room_settings:
                    Toast.makeText(mContext, "Settings", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}