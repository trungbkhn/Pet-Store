package com.example.animalfood.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import com.example.animalfood.databinding.ActivityInviteFriendBinding;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

public class InviteFriendActivity extends AppCompatActivity {
    private ActivityInviteFriendBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInviteFriendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackInviteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.linearLayoutSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Use to get a discount on your first order on the Pet Store app. https://www.facebook.com/2k.trung/";
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("sms:")); // This ensures only SMS apps respond
                smsIntent.putExtra("sms_body", message);
                startActivity(smsIntent);
            }
        });

        binding.linearLayoutSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = "Invite to Pet Store";
                String body = "Use this link to get a discount on your first order on the Pet Store app. https://www.facebook.com/2k.trung/";
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(emailIntent);
            }
        });

        binding.linearLayoutCopyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = "https://www.facebook.com/2k.trung/";
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied Link", link);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(InviteFriendActivity.this, "Link copied succeed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
