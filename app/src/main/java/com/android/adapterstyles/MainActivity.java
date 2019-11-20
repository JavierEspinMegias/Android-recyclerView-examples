package com.android.adapterstyles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ArrayList<AppUser> users = new ArrayList<AppUser>();
    private ArrayList<AppGroup> groups = new ArrayList<>();

    private SimpleAdapter adapterUsers;
    private SimpleAdapter adapterGroups;

    private RecyclerView recyclerUsers;
    private RecyclerView recyclerGroups;

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
        recyclerUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        recyclerUsers.setLayoutManager(new LinearLayoutManager(this));



        recyclerGroups = (RecyclerView) findViewById(R.id.recyclerViewGroups);
        recyclerGroups.setLayoutManager(new LinearLayoutManager(this));

        for (int i = 0; i < 10; i++) {
            AppUser newUser = new AppUser();
            users.add(newUser);
        }

        for (int i = 0; i < 5; i++) {
            AppGroup newGroup = new AppGroup();
            newGroup.name = "group"+i;
            groups.add(newGroup);
        }


        // Insercion de datos en users y creacion
        adapterUsers = new SimpleAdapter(users);
        recyclerUsers.setAdapter(adapterUsers);


        adapterGroups = new SimpleAdapter(users,false, groups, true);
        recyclerGroups.setAdapter(adapterGroups);


    }









    public void boton1 (View v){
        users.add(new AppUser());
        adapterUsers.notifyItemChanged(users.size());
    }


    public void boton2 (View v){
        if (users.size() > 0){
            users.remove(0);
            adapterUsers.notifyItemRemoved(0);
        }
    }


    public void boton3 (View v){
        recyclerUsers.setLayoutManager(new GridLayoutManager(this, 4));
        adapterUsers = new SimpleAdapter(users, false, groups, false);
        recyclerUsers.setAdapter(adapterUsers);
    }


    public void boton4 (View v){
        if (pos == 0){
            recyclerUsers.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
            adapterUsers = new SimpleAdapter(users, false, groups, false);
            recyclerUsers.setAdapter(adapterUsers);
            pos = 1;

        }else{
            recyclerUsers.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL));
            adapterUsers = new SimpleAdapter(users, false, groups, false);
            recyclerUsers.setAdapter(adapterUsers);
            pos = 0;
        }
    }


    public void boton5 (View v){
        if (pos == 0){
            recyclerUsers.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
            adapterUsers = new SimpleAdapter(users, false, groups, false);
            recyclerUsers.setAdapter(adapterUsers);
            pos = 1;
        }else{
            recyclerUsers.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
            adapterUsers = new SimpleAdapter(users, false, groups, false);
            recyclerUsers.setAdapter(adapterUsers);
            pos = 0;
        }
    }

    public void goRandom (View v){
        Random rnd = new Random();
        recyclerUsers.scrollToPosition(rnd.nextInt(users.size()));
    }
}
