package com.android.adapterstyles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<AppUser> users = new ArrayList<AppUser>();
    private SimpleAdapter adapter;
    private RecyclerView adapterUsers;
    private Button boton1, boton2, boton3, boton5, boton4;
    private int pos, posLay;
    private LinearLayout layoutPadre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.boton1 = (Button)findViewById(R.id.boton1);
        this.boton2 = (Button)findViewById(R.id.boton2);
        this.boton3 = (Button)findViewById(R.id.boton3);
        this.boton4 = (Button)findViewById(R.id.boton4);
        this.boton5 = (Button)findViewById(R.id.boton5);
        this.pos = 0;
        this.posLay = 0;
        this.layoutPadre = (LinearLayout)findViewById(R.id.adapter_linear_layout);


        /// Identificacion y disenyo de adaptador
        adapterUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        adapterUsers.setLayoutManager(new LinearLayoutManager(this));

        for (int i = 0; i < 10; i++) {
            AppUser newUser = new AppUser();
            users.add(newUser);
        }


        // Insercion de datos en users y creacion
        adapter = new SimpleAdapter(users, false);
        adapterUsers.setAdapter(adapter);

    }


    public void boton1 (View v){
        users.add(new AppUser());
        adapter.notifyItemChanged(users.size());
    }


    public void boton2 (View v){
        users.remove(0);
        adapter.notifyItemRemoved(0);
    }


    public void boton3 (View v){
        adapterUsers.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new SimpleAdapter(users, false);
        adapterUsers.setAdapter(adapter);
    }


    public void boton4 (View v){
        if (pos == 0){
            adapterUsers.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
            adapter = new SimpleAdapter(users, false);
            adapterUsers.setAdapter(adapter);
            pos = 1;

        }else{
            adapterUsers.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL));
            adapter = new SimpleAdapter(users, false);
            adapterUsers.setAdapter(adapter);
            pos = 0;
        }
    }


    public void boton5 (View v){
        if (pos == 0){
            adapterUsers.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
            adapter = new SimpleAdapter(users, false);
            adapterUsers.setAdapter(adapter);
            pos = 1;
        }else{
            adapterUsers.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
            adapter = new SimpleAdapter(users, false);
            adapterUsers.setAdapter(adapter);
            pos = 0;
        }
    }
}
