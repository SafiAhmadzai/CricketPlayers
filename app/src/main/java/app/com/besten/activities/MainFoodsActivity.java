package app.com.besten.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import app.com.besten.R;



public class MainFoodsActivity extends AppCompatActivity {

    RecyclerView rvMahliFoods;

    FirebaseFirestore firebaseFirestore;

    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_foods);

        rvMahliFoods = findViewById(R.id.rvMahaliFoods);

        firebaseFirestore = FirebaseFirestore.getInstance();

//Query
        Query query = firebaseFirestore.collection("MahaliFoods");

        FirestoreRecyclerOptions<Foods> data=new FirestoreRecyclerOptions.Builder<Foods>().setQuery(query,Foods.class).build();


        //Work with RecyclerAdapter

        adapter=new FirestoreRecyclerAdapter<Foods,FoodsViewHolder>(data) {
            @NonNull
            @Override
            public FoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item,parent,false);

                return new FoodsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FoodsViewHolder holder, int position, @NonNull Foods model) {


                holder.tvPrice.setText(model.getPrice());
                holder.tvDescription.setText(model.getDesc());
                holder.tvName.setText(model.getName());

                Glide
                        .with(MainFoodsActivity.this)
                        .load(model.getPhoto())
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(holder.ivPhoto);

            }
        };

        rvMahliFoods.setHasFixedSize(true);
        rvMahliFoods.setLayoutManager(new LinearLayoutManager(MainFoodsActivity.this,LinearLayoutManager.HORIZONTAL,false));

        rvMahliFoods.setAdapter(adapter);


    }
        public class FoodsViewHolder extends  RecyclerView.ViewHolder{


        ImageView ivPhoto;
        TextView tvName;
        TextView tvDescription;
        TextView tvPrice;


            public FoodsViewHolder(@NonNull View itemView) {
                super(itemView);

                ivPhoto=itemView.findViewById(R.id.ivImageView);
                tvName=itemView.findViewById(R.id.tvName);
                tvDescription=itemView.findViewById(R.id.tvDescription);
                tvPrice=itemView.findViewById(R.id.tvPrice);

            }
        }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}

