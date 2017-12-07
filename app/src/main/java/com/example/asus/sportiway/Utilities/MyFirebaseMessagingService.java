package com.example.asus.sportiway.Utilities;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.example.asus.sportiway.Activity.MainActivity;
import com.example.asus.sportiway.Activity.MergeEventActivity;
import com.example.asus.sportiway.Activity.ProfileActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Asus on 06/09/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String uid, eventRequestId, eventTargetId;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String type = remoteMessage.getData().get("type");
        switch (type) {
            case "join":
                uid = remoteMessage.getData().get("uid");
                Intent intent = new Intent(MainActivity.mContext, ProfileActivity.class);
                intent.putExtra("uid", uid);
                MainActivity.mContext.startActivity(intent);
                break;
            case "requestMerge":
                eventRequestId = remoteMessage.getData().get("eventRequestId");
                eventTargetId = remoteMessage.getData().get("eventTargetId");
                intent = new Intent(MainActivity.mContext, MergeEventActivity.class);
                intent.putExtra("eventRequestId", eventRequestId);
                intent.putExtra("eventTargetId", eventTargetId);
                MainActivity.mContext.startActivity(intent);
                break;
            default:
                break;
        }
    }
}
