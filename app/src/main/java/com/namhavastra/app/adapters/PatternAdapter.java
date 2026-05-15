package com.namhavastra.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.namhavastra.app.R;
import com.namhavastra.app.models.Pattern;
import java.util.List;

public class PatternAdapter extends RecyclerView.Adapter<PatternAdapter.ViewHolder> {
    private Context context;
    private List<Pattern> patterns;

    public PatternAdapter(Context context, List<Pattern> patterns) {
        this.context = context;
        this.patterns = patterns;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pattern, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pattern pattern = patterns.get(position);
        holder.ivIcon.setImageResource(pattern.iconRes);
        holder.tvPatternName.setText(pattern.name);
        holder.tvDescription.setText(pattern.description);
    }

    @Override
    public int getItemCount() {
        return patterns.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvPatternName, tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvPatternName = itemView.findViewById(R.id.tvPatternName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
}
