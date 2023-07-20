package com.example.project_prm392.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.project_prm392.R;
import com.example.project_prm392.adapters.UserAdapter;
import com.example.project_prm392.databinding.ActivityListUserBinding;
import com.example.project_prm392.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListUserActivity extends AppCompatActivity {
    ActivityListUserBinding binding;
    ArrayList<User> list = new ArrayList<>();
    FirebaseDatabase database;
    UserAdapter userAdapter;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userAdapter = new UserAdapter(list,this);
        binding.chatRecyclerView.setAdapter(userAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                boolean isAdmin = false;
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    user.setUserId(dataSnapshot.getKey());
                    if(user.getUserId().equals(mAuth.getUid())){
                        if(user.getIsAdmin().equals("true")){
                            isAdmin = true;
                        }else{
                            isAdmin = false;
                        }
                    }else if(isAdmin){
                            list.add(user);
                    }else {
                        if(user.getIsAdmin().equals("true")){
                            list.add(user);
                        }
                    }
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout){
            mAuth.signOut();
            startActivity(new Intent(ListUserActivity.this, LoginActivity.class));
            finish();
            return true;
        }
        return false;
    }
}