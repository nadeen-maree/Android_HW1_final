package com.CATvsRAT;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learntodroid.androidminesweeper.R;

import java.util.List;

public class GameGridRecyclerAdapter extends RecyclerView.Adapter<GameGridRecyclerAdapter.GameTileViewHolder> {
    private List<Cell> cells;

    public GameGridRecyclerAdapter(List<Cell> cells) {
        this.cells = cells;
    }

    @NonNull
    @Override
    public GameTileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell, parent, false);
        return new GameTileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameTileViewHolder holder, int position) {
        holder.bind(cells.get(position));
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return cells.size();
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
        notifyDataSetChanged();
    }

    class GameTileViewHolder extends RecyclerView.ViewHolder {
        ImageView valueImageView;

        public GameTileViewHolder(@NonNull View itemView) {
            super(itemView);

            valueImageView = itemView.findViewById(R.id.item_cell_value);
        }

        public void bind(final Cell cell) {
            itemView.setBackgroundColor(Color.parseColor("#F5F5DC"));

            if(cell.isCheese()){
                valueImageView.setImageResource(R.drawable.cheese);
            }
            if(cell.isCat()){
                valueImageView.setImageResource(R.drawable.cat);
                switch(cell.getCatRotation()){
                    case "up":
                        itemView.setRotation(-90);
                        break;
                    case "down":
                        itemView.setRotation(90);
                        break;
                    case "right":
                        itemView.setRotationY(0);
                        break;
                    case "left":
                        itemView.setRotationY(180);
                        break;
                    default:
                        break;
                }
            }
            if(cell.isRat()) {
                valueImageView.setImageResource(R.drawable.rat);
                switch (cell.getRatRotation()) {
                    case "up":
                        itemView.setRotation(-90);
                        break;
                    case "down":
                        itemView.setRotation(90);
                        break;
                    case "right":
                        itemView.setRotationY(0);
                        break;
                    case "left":
                        itemView.setRotationY(180);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
