package com.android.adapterstyles;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder>{


    private ArrayList<AppUser> users;
    private ArrayList<AppGroup> groups;
    private RecyclerView recyUsers;
    private SimpleAdapter adapUsers;
    private Context context;

    private final GroupsListener groupsListener;

    public interface GroupsListener {
        void onClicked(AppGroup group);
    }



    public GroupAdapter(Context activity, ArrayList<AppGroup> groups, GroupsListener groupsListener) {
        this.groupsListener = groupsListener;
        this.groups = groups;
        this.context=activity;

    }

    //El constructor deberá enlazar los datos del modelos con los del controlador
    public GroupAdapter(Context activity, ArrayList<AppGroup> groups, ArrayList<AppUser> users, GroupsListener groupsListener) {
        this.groupsListener = groupsListener;
        this.users = users;
        this.groups = groups;
    }



    //En un adaptador es obligatorio definir una clase que herede de RecyclerView.ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        //La clase ViewHolder hará referencia a los elementos de la vista creada para el recycler view
        public TextView name;
        public TextView id;
        private Button buttonGroup;
        public ImageView imageGroup;
        public LinearLayout background;

        //Su constructor debera enlazar las variables del controlador con la vista
        public ViewHolder(final View itemView) {
            super(itemView);

            this.name = (TextView) itemView.findViewById(R.id.user_name);
            this.id = (TextView) itemView.findViewById(R.id.user_id);
            this.buttonGroup=(Button)itemView.findViewById(R.id.custom_buttom_1);
            this.imageGroup = (ImageView)itemView.findViewById(R.id.image_view_adapter);
            this.background = (LinearLayout)itemView.findViewById(R.id.adapter_linear_layout);

        }
    }

    @NonNull
    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //Especificamos el fichero XML que se utilizará como vista
        View contactView = inflater.inflate(R.layout.adapter_groups, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(GroupAdapter.ViewHolder holder, final int position) {
        final  AppGroup group = this.groups.get(position);
        holder.name.setText(group.getName());
        holder.name.setTextSize(24);


        holder.buttonGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupsListener.onClicked(groups.get(position));
            }
        });

    }



    @Override
    public int getItemCount() {
        return groups.size();
    }
}