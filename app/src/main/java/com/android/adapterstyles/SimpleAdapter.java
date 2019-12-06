package com.android.adapterstyles;

import android.animation.LayoutTransition;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder>{



    private int lastPosition = -1;

    private ArrayList<AppUser> users;
    private ArrayList<AppGroup> groups;

    public boolean isIcon = false;
    public boolean isGroup = false;
    public int[]  animations = new int[]{
            R.anim.user_down_to_up,
            R.anim.user_left_to_right,
            R.anim.user_left_to_right_big_to_small,
            R.anim.user_left_to_right_from_big_x,
            R.anim.user_left_to_right_from_big_y,
            R.anim.user_left_to_right_from_small_y,
            R.anim.user_left_to_right_small_to_big,
            R.anim.user_right_to_left,
            R.anim.user_right_to_left_from_small_x,
            R.anim.user_up_to_down
    };

    public SimpleAdapter(ArrayList<AppUser> users) {
        this.users = users;
    }

    //El constructor deberá enlazar los datos del modelos con los del controlador
    public SimpleAdapter(ArrayList<AppUser> users, boolean isAnIcon, ArrayList<AppGroup> groups, boolean isGroup) {
        this.users = users;
        isIcon = isAnIcon;

        this.groups = groups;
        this.isGroup = isGroup;
    }



    //En un adaptador es obligatorio definir una clase que herede de RecyclerView.ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        //La clase ViewHolder hará referencia a los elementos de la vista creada para el recycler view
        public TextView name;
        public TextView id;
        private Button deleteUser, openUser, closeUser;
        public LinearLayout background, titleBackground, openCloseBackground, text;

        public RelativeLayout relativeSingleLayout;
        public ImageView imageUser;

        //Su constructor debera enlazar las variables del controlador con la vista
        public ViewHolder(final View itemView) {
            super(itemView);

            this.name = (TextView) itemView.findViewById(R.id.user_name);
            this.id = (TextView) itemView.findViewById(R.id.user_id);
            this.deleteUser=(Button)itemView.findViewById(R.id.custom_buttom_1);
            this.openUser=(Button)itemView.findViewById(R.id.custom_buttom_2);
            this.closeUser=(Button)itemView.findViewById(R.id.custom_buttom_3);
            this.imageUser = (ImageView)itemView.findViewById(R.id.image_view_adapter);
            this.background = (LinearLayout)itemView.findViewById(R.id.adapter_linear_layout);
            this.titleBackground = (LinearLayout)itemView.findViewById(R.id.linear_title);
            this.openCloseBackground = (LinearLayout)itemView.findViewById(R.id.linear_open_close);
            this.text = (LinearLayout)itemView.findViewById(R.id.textLorem);
            this.relativeSingleLayout = (RelativeLayout)itemView.findViewById(R.id.relative_single_user);
        }
    }

    @NonNull
    @Override
    public SimpleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //Especificamos el fichero XML que se utilizará como vista
        View contactView = inflater.inflate(R.layout.adapter_users, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);

        // Comprobamos si la vista es horizontal o vertical
        if (viewType == 0) {

        } else if (viewType == 1) {

        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final SimpleAdapter.ViewHolder holder, final int position) {


        if (this.isGroup){
            final  AppGroup group = this.groups.get(position);
            holder.name.setText(group.getName()+position);
            holder.name.setTextSize(24);
            holder.background.setBackgroundColor(getRandomColor());
            holder.deleteUser.setVisibility(View.GONE);
        }else{
            //Vamos obteniendo mail por mail
            final AppUser user = this.users.get(position);
            //Enlazamos los elementos de la vista con el modelo
            holder.name.setText(""+user.name+position);
            holder.id.setText("-id-+position");
            holder.deleteUser.setText("Eliminar"+position);

            holder.deleteUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (users.size() > 0){
                        users.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(0, users.size());
                    }
                }
            });

            holder.text.setVisibility(View.GONE);
            holder.openUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expand_layout(holder.background.findViewById(R.id.adapter_linear_layout),1000,800);
                    holder.text.setAnimation(fadeInAnimation());
                    holder.text.setVisibility(View.VISIBLE);
                }
            });

            holder.closeUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.text.setAnimation(fadeOutAnimation());
                    collapse_layout(holder.background.findViewById(R.id.adapter_linear_layout),1000,400);
                }
            });




            holder.relativeSingleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goSingleUser = new Intent(holder.imageUser.getContext(), ViewSingleUser.class);

                    Pair[] parejas = new Pair[1];
                    parejas[0]= new Pair<View,String>(holder.imageUser,"imageTransition");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) holder.imageUser.getContext(), parejas);
                    holder.imageUser.getContext().startActivity(goSingleUser, options.toBundle());
                }
            });


            // Aplicamos la animacion una vez determinada cual va a ser la vista de cada elemento
            setAnimation(holder.itemView, position);
            lastPosition = position;
        }

    }



    @Override
    public int getItemCount() {
        if (isGroup){
            return groups.size();
        }else{
            return users.size();
        }
    }



    private void setAnimation(View viewToAnimate, int position){
        if (position > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.user_up_to_down);

            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    // LinearLayout ANIMATION
    public static void expand_layout(final View v, int duration, int targetHeight) {

        int prevHeight  = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }
    public static void collapse_layout(final View v, int duration, int targetHeight) {
        int prevHeight  = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    // Text ANIMATION
    public Animation fadeInAnimation(){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setStartOffset(200);
        fadeIn.setDuration(1000);
        return fadeIn;
    }
    public Animation fadeOutAnimation(){
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(0);
        fadeOut.setDuration(800);
        return fadeOut;
    }



    public int getRandomColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return color;
    }
    public boolean isColorDark(int color){
        double darkness = 1-(0.299*Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        if(darkness<0.5){
            return false;
        }else{
            return true;
        }
    }
    private void openAnimation(View viewToAnimate, int position){
        Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.open_user);

        viewToAnimate.startAnimation(animation);
        lastPosition = position;

    }
}