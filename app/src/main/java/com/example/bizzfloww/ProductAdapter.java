package com.example.bizzfloww;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable {

    private final List<Product> originalList;
    private List<Product> filteredList;
    private final OnProductClickListener listener;

    public interface OnProductClickListener {
        void onItemClick(Product product, int position);
        void onDeleteClick(Product product, int position);
        void onVerifyChanged(Product product, boolean isChecked);
    }

    public ProductAdapter(List<Product> productList, OnProductClickListener listener) {
        this.originalList = productList;
        this.filteredList = new ArrayList<>(productList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = filteredList.get(position);

        holder.tvName.setText(product.getName());
        holder.tvId.setText("ID: " + product.getProductId());
        holder.tvCategory.setText(product.getCategory());
        holder.tvQty.setText(product.getQuantity() + " Units");

        // Set CheckBox without triggering listener recursively
        holder.cbVerify.setOnCheckedChangeListener(null);
        holder.cbVerify.setChecked(product.isVerified());

        holder.cbVerify.setOnCheckedChangeListener((buttonView, isChecked) -> {
            product.setVerified(isChecked);
            if (listener != null) {
                listener.onVerifyChanged(product, isChecked);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(product, holder.getAdapterPosition());
            }
        });

        holder.btnInfo.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(product, holder.getAdapterPosition());
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(product, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void updateList(List<Product> newList) {
        this.filteredList = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint == null ? "" : constraint.toString().toLowerCase().trim();
                List<Product> results = new ArrayList<>();

                if (query.isEmpty()) {
                    results.addAll(originalList);
                } else {
                    for (Product item : originalList) {
                        if (item.getName().toLowerCase().contains(query) ||
                                item.getProductId().toLowerCase().contains(query) ||
                                item.getCategory().toLowerCase().contains(query)) {
                            results.add(item);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = results;
                filterResults.count = results.size();
                return filterResults;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                if (results.values != null) {
                    filteredList.addAll((List<Product>) results.values);
                }
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvId, tvCategory, tvQty;
        CheckBox cbVerify;
        ImageButton btnInfo, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_product_name);
            tvId = itemView.findViewById(R.id.tv_product_id);
            tvCategory = itemView.findViewById(R.id.tv_product_category);
            tvQty = itemView.findViewById(R.id.tv_product_qty);
            cbVerify = itemView.findViewById(R.id.cb_verify);
            btnInfo = itemView.findViewById(R.id.btn_item_info);
            btnDelete = itemView.findViewById(R.id.btn_item_delete);
        }
    }
}
