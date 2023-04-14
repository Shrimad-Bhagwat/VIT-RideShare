package com.shrimadbhagwat.vitrideshare;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<DataClass> dataList;
    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name_tv.setText(dataList.get(position).getName());
        holder.from_tv.setText(dataList.get(position).getFrom_location());
        holder.to_tv.setText(dataList.get(position).getTo_location());
        holder.date_tv.setText(dataList.get(position).getDate());
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra("Name", dataList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("To", dataList.get(holder.getAdapterPosition()).getTo_location());
                intent.putExtra("From",dataList.get(holder.getAdapterPosition()).getFrom_location());
                intent.putExtra("Date", dataList.get(holder.getAdapterPosition()).getDate());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void searchDataList(ArrayList<DataClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{

    TextView name_tv, from_tv, to_tv, date_tv;
    Button contact_btn;
    CardView recCard;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        name_tv = itemView.findViewById(R.id.name_tv);
        recCard = itemView.findViewById(R.id.recCard);
        from_tv = itemView.findViewById(R.id.from_tv);
        to_tv = itemView.findViewById(R.id.to_tv);
        date_tv = itemView.findViewById(R.id.date_tv);
        contact_btn = itemView.findViewById(R.id.contact_button);
    }
}