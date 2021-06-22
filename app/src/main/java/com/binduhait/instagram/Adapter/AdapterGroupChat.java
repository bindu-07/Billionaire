package com.binduhait.instagram.Adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.binduhait.instagram.Model.ModelGroupChats;
import com.binduhait.instagram.PhotoViewer;
import com.binduhait.instagram.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hendraanggrian.appcompat.widget.SocialTextView;
import com.hendraanggrian.appcompat.widget.SocialView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AdapterGroupChat extends RecyclerView.Adapter<AdapterGroupChat.Myholder> {
    private static final int MSG_TYPE_LEFT=0;
    private static final int MSG_TYPR_RIGHT=1;
    Context context;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    public AdapterGroupChat(Context context, ArrayList<ModelGroupChats> modelGroupChats) {
        this.context = context;
        this.modelGroupChats = modelGroupChats;
        firebaseAuth=FirebaseAuth.getInstance();
    }

    ArrayList<ModelGroupChats> modelGroupChats;

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==MSG_TYPE_LEFT){
            View view= LayoutInflater.from(context).inflate(R.layout.row_groupchat_left,parent,false);
            return new Myholder(view);
        }
        else{
            View view= LayoutInflater.from(context).inflate(R.layout.row_groupchat_right,parent,false);
            return new Myholder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {

        ModelGroupChats chats=modelGroupChats.get(position);
        String message=chats.getMessage();
        String sender=chats.getSender();
        String timestamp=chats.getTimestamp();
        String type=chats.getType();
        Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(timestamp));
        String timedate= DateFormat.format("d MMM 'at' h:mm a",calendar).toString();

        setUserName(chats,holder);
        holder.time.setText(timedate);
        if(type.equals("text")){
            holder.message.setVisibility(View.VISIBLE);
            holder.image.setVisibility(View.GONE);
            holder.message.setText(message);

            holder.message.setOnHyperlinkClickListener(new SocialView.OnClickListener() {
                @Override
                public void onClick(@NonNull SocialView view, @NonNull CharSequence text) {
                    Toast.makeText(context, "Clicked URL", Toast.LENGTH_SHORT).show();

                    String url = String.valueOf(text);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (url.startsWith("www")) {
                        i.setData(Uri.parse("http://" + url));
                    } else {
                        i.setData(Uri.parse(url));
                    }

                    context.startActivity(i);

                }
            });

            holder.message.setOnHashtagClickListener(new SocialView.OnClickListener() {
                @Override
                public void onClick(@NonNull SocialView view, @NonNull CharSequence text) {
                    Toast.makeText(context, "Clicked HashTag", Toast.LENGTH_SHORT).show();
                }
            });

            holder.message.setOnMentionClickListener(new SocialView.OnClickListener() {
                @Override
                public void onClick(@NonNull SocialView view, @NonNull CharSequence text) {
                    Toast.makeText(context, "Clicked mention", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else {
            holder.message.setVisibility(View.GONE);
            holder.image.setVisibility(View.VISIBLE);
            Glide.with(context).load(message).into(holder.image);

            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PhotoViewer.class);
                    intent.putExtra("image", message);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelGroupChats.size();
    }
    private void setUserName(ModelGroupChats groupChats, final Myholder holder){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.orderByChild("id").equalTo(groupChats.getSender())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            String names=""+ds.child("fullname").getValue();
                            holder.name.setText(names);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
    @Override
    public int getItemViewType(int position) {

        if(modelGroupChats.get(position).getSender().equals(firebaseAuth.getUid())){
            return MSG_TYPR_RIGHT;
        }
        else {
            return MSG_TYPE_LEFT;
        }
    }
    class Myholder extends RecyclerView.ViewHolder{

        TextView name, time;
        SocialTextView message;
        ImageView image;
        public Myholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.sedername);
            image=itemView.findViewById(R.id.imagegrp);
            message=itemView.findViewById(R.id.sendermsg);
            time=itemView.findViewById(R.id.timegrp);
        }
    }
}