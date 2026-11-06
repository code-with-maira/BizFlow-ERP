package com.example.bizzfloww;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductionAdapter extends RecyclerView.Adapter<ProductionAdapter.ViewHolder> {

    private final List<ProductionItem> itemList;

    public ProductionAdapter(List<ProductionItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_production, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductionItem item = itemList.get(position);
        holder.tvName.setText(item.getProductName());
        holder.tvDate.setText("Date: " + item.getProductionDate());
        holder.tvQty.setText("+" + item.getProductionQuantity() + " Produced");
        holder.tvWastage.setText("-" + item.getWastageQuantity() + " Wastage");
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate, tvQty, tvWastage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_prod_name);
            tvDate = itemView.findViewById(R.id.tv_prod_date);
            tvQty = itemView.findViewById(R.id.tv_prod_qty);
            tvWastage = itemView.findViewById(R.id.tv_wastage_qty);
        }
    }
}
