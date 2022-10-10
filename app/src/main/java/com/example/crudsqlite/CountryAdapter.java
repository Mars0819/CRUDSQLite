package com.example.crudsqlite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
 private List<CountryModel> data;
 private PopupDialogClickListener clickListener;



    public CountryAdapter(List<CountryModel> data,PopupDialogClickListener clickListener){
        this.data = data;
        this.clickListener =clickListener;
    }

    public void updateAndRefreshData(List<CountryModel> newData){
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
    LayoutInflater layouInflater = LayoutInflater.from(parent.getContext());
        View theView =layouInflater.inflate(R.layout.country_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(theView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
    CountryModel selectedCountry= data.get(position);
    holder.tvId.setText(String.valueOf(selectedCountry.getId()));
    holder.tvName.setText(String.valueOf(selectedCountry.getName()));
    holder.tvPopulation.setText(String.valueOf(selectedCountry.getPopulation()));

    holder.btnEdit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        clickListener.onEdit(position);
        }
    });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            clickListener.onDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data ==null)return 0;
        else return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvId,tvId2;
        public TextView tvName,tvName2;
        public TextView tvPopulation,tvPopulation2;
        public Button btnEdit;
        public Button btnDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvCountry);
            tvId2 = itemView.findViewById(R.id.tvCountryID);
            tvName = itemView.findViewById(R.id.tvCountryName);
            tvName2 = itemView.findViewById(R.id.tvCountryName1);
            tvPopulation = itemView.findViewById(R.id.tvCountryPopulation);
            tvPopulation2 = itemView.findViewById(R.id.tvCountryPopulation1);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
    public void removeItem(int position){
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount() - position);
    }
}
