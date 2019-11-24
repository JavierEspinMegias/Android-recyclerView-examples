package com.android.adapterstyles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ArrayList<AppUser> users = new ArrayList<AppUser>();
    private ArrayList<AppGroup> groups = new ArrayList<>();

    private GroupAdapter adapterGroups;
    private RecyclerView recyclerGroups;

    private SimpleAdapter adapterUsers;
    private RecyclerView recyclerUsers;


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
        recyclerGroups = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerGroups.setLayoutManager(new LinearLayoutManager(this));

        /// Identificacion y disenyo de adaptador
        recyclerUsers = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerUsers.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));



        for (int i = 0; i < 5; i++) {
            AppGroup newGroup = new AppGroup();
            newGroup.name = "group "+i;
            ArrayList<AppUser> newUserList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                AppUser newUser = new AppUser();
                newUser.setName("User "+newGroup.getName());
                newUserList.add(newUser);
            }
            newGroup.setUsers(newUserList);
            groups.add(newGroup);
        }


        // Insercion de datos en users y creacion
//        adapterUsers = new SimpleAdapter(users);
//        recyclerUsers.setAdapter(adapterUsers);


        adapterUsers = new SimpleAdapter(users);
        recyclerUsers.setAdapter(adapterUsers);


        // Adaptacion para listener
        adapterGroups = new GroupAdapter(this,groups, new GroupAdapter.GroupsListener() {
            @Override
            public void onClicked(AppGroup group) {
                // Insercion de datos en users y creacionusers = group.getUsers();
                users = group.getUsers();
                adapterUsers = new SimpleAdapter(users);
                adapterUsers.notifyItemRangeChanged(0,users.size());
                recyclerUsers.setAdapter(adapterUsers);
                Toast.makeText(MainActivity.this, "Has seleccionado "+group.getUsers().size()+" usuarios del grupo "+group.getName(), Toast.LENGTH_SHORT).show();

            }
        });

        recyclerGroups.setAdapter(adapterGroups);


    }


    public void boton1 (View v){
        users.add(new AppUser());
        adapterGroups.notifyItemChanged(users.size());
    }


    public void boton2 (View v){
        if (users.size() > 0){
            users.remove(0);
            adapterGroups.notifyItemRemoved(0);
        }
    }


    public void boton3 (View v){
        recyclerGroups.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerGroups.setAdapter(adapterGroups);
    }


    public void boton4 (View v){
        if (pos == 0){
            recyclerGroups.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
            recyclerGroups.setAdapter(adapterGroups);
            pos = 1;

        }else{
            recyclerGroups.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL));
            recyclerGroups.setAdapter(adapterGroups);
            pos = 0;
        }
    }


    public void boton5 (View v){
        if (pos == 0){
            recyclerGroups.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
            recyclerGroups.setAdapter(adapterGroups);
            pos = 1;
        }else{
            recyclerGroups.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
            recyclerGroups.setAdapter(adapterGroups);
            pos = 0;
        }
    }

    public void goRandom (View v){
        Random rnd = new Random();
        recyclerGroups.scrollToPosition(rnd.nextInt(groups.size()));
    }

}
