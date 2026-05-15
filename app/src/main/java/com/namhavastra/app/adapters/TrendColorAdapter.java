package com.namhavastra.app.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.namhavastra.app.R;
import com.namhavastra.app.models.TrendColor;
import java.util.List;

public class TrendColorAdapter extends RecyclerView.Adapter<TrendColorAdapter.ViewHolder> {
    private Context context;
    private List<TrendColor> colors;

    public TrendColorAdapter(Context context, List<TrendColor> colors) {
        this.context = context;
        this.colors = colors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trend_color, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrendColor color = colors.get(position);
        holder.colorView.setBackgroundColor(Color.parseColor(color.hexCode));
        holder.tvColorName.setText(color.name);
        holder.tvStatusBadge.setText(color.status);

        if ("HOT".equals(color.status)) {
            holder.tvStatusBadge.setBackgroundColor(context.getResources().getColor(R.color.red_price));
        } else if ("RISING".equals(color.status)) {
            holder.tvStatusBadge.setBackgroundColor(context.getResources().getColor(R.color.amber));
        } else {
            holder.tvStatusBadge.setBackgroundColor(context.getResources().getColor(R.color.teal_accent));
        }
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View colorView;
        TextView tvColorName, tvStatusBadge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            colorView = itemView.findViewById(R.id.colorView);
            tvColorName = itemView.findViewById(R.id.tvColorName);
            tvStatusBadge = itemView.findViewById(R.id.tvStatusBadge);
        }
    }
}
