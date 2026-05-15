package com.namhavastra.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.namhavastra.app.R;
import com.namhavastra.app.db.GalleryEntity;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private Context context;
    private List<GalleryEntity> items;

    public GalleryAdapter(Context context, List<GalleryEntity> items) {
        this.context = context;
        this.items = items;
    }

    public void updateData(List<GalleryEntity> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gallery_saree, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GalleryEntity item = items.get(position);
        holder.tvTitle.setText(item.description);
        holder.tvMaterialBadge.setText(item.material);
        holder.tvPrice.setText("₹" + item.price);

        if (item.imagePath != null && !item.imagePath.isEmpty()) {
            holder.ivSareeImage.setImageURI(Uri.parse(item.imagePath));
        } else {
            holder.ivSareeImage.setImageResource(android.R.color.darker_gray);
        }

        holder.btnWhatsapp.setOnClickListener(v -> {
            String msg = Uri.encode("I am interested in your " + item.material + " saree priced at ₹" + item.price);
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/919876543210?text=" + msg));
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSareeImage;
        TextView tvTitle, tvMaterialBadge, tvPrice;
        Button btnWhatsapp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSareeImage = itemView.findViewById(R.id.ivSareeImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvMaterialBadge = itemView.findViewById(R.id.tvMaterialBadge);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnWhatsapp = itemView.findViewById(R.id.btnWhatsapp);
        }
    }
}
