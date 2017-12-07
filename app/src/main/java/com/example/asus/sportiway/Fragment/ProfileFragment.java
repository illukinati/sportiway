package com.example.asus.sportiway.Fragment;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.sportiway.Activity.AgendaActivity;
import com.example.asus.sportiway.Activity.EditProfileActivity;
import com.example.asus.sportiway.Activity.HistoryActivity;
import com.example.asus.sportiway.Activity.LoginActivity;
import com.example.asus.sportiway.Activity.SuggestionActivity;
import com.example.asus.sportiway.Activity.TopupActivity;
import com.example.asus.sportiway.Model.History;
import com.example.asus.sportiway.Model.Users;
import com.example.asus.sportiway.Preferences.SessionManager;
import com.example.asus.sportiway.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    ImageView icMenu, imgCharacter;
    TextView txtNama, txtReputation, txtMakeEvent, txtJoinEvent,
            txtEmail, txtPhone, txtAddress, txtHobby;
    Button btnTopup;

    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    SessionManager sessionManager;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        icMenu = (ImageView) view.findViewById(R.id.ic_menu);
        imgCharacter = (ImageView) view.findViewById(R.id.img_character);
        icMenu.setOnClickListener(this);

        txtNama = (TextView) view.findViewById(R.id.txt_nama);
        txtReputation = (TextView) view.findViewById(R.id.txt_reputation);
        txtMakeEvent = (TextView) view.findViewById(R.id.txt_make_event);
        txtJoinEvent = (TextView) view.findViewById(R.id.txt_join_event);
        txtEmail = (TextView) view.findViewById(R.id.txt_email);
        txtPhone = (TextView) view.findViewById(R.id.txt_phone);
        txtAddress = (TextView) view.findViewById(R.id.txt_address);
        txtHobby = (TextView) view.findViewById(R.id.txt_hobby);
        btnTopup = (Button) view.findViewById(R.id.btn_topup);
        btnTopup.setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users/" + mAuth.getCurrentUser().getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                txtNama.setText(users.name);
                txtEmail.setText(users.email);
                txtHobby.setText(users.sport_type);
                txtAddress.setText(users.address);
                txtJoinEvent.setText(Integer.toString(users.event_joined));
                txtMakeEvent.setText(Integer.toString(users.event_made));
                txtReputation.setText(Integer.toString(users.reputation));
                txtPhone.setText(users.phone);
                switch (users.chara.avatar){
                    case "satu":
                        imgCharacter.setImageResource(R.drawable.lvl1);
                        break;
                    case "dua":
                        imgCharacter.setImageResource(R.drawable.lvl10);
                        break;
                    case "tiga":
                        imgCharacter.setImageResource(R.drawable.lvl30);
                        break;
                    default:
                        imgCharacter.setImageResource(R.drawable.lvl1);
                        break;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(valueEventListener);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == icMenu) {
            showPopup(view);
        } else if(view == btnTopup){
            Intent intent = new Intent(getActivity(), TopupActivity.class);
            getActivity().startActivity(intent);
        }
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.more_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_agenda) {
                    Intent intent = new Intent(getActivity(), AgendaActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.menu_edit_profile) {
                    Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.menu_history) {
                    Intent intent = new Intent(getActivity(), HistoryActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.menu_logout) {
                    mAuth.signOut();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else if (item.getItemId() == R.id.menu_suggestion) {
                    sessionManager = new SessionManager(getContext());
                    if (sessionManager != null) {
                        HashMap<String, String> user = sessionManager.getUserDetails();
                        if(user.get(SessionManager.KEY_EVENT_ID) == null){
                            Toast.makeText(getContext(), "You have no event yet to be held !", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(getActivity(), SuggestionActivity.class);
                            intent.putExtra("eventId", user.get(SessionManager.KEY_EVENT_ID));
                            getActivity().startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getContext(), "error no session", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.more_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
