package com.example.task;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DataViewHolder> implements Filterable {
    private List<DataAdapter> exampleList;
    private List<DataAdapter> exampleListFull;

    class DataViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textViewName;
        public TextView textViewPrice;
        public TextView textViewChange;

        DataViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.textViewName = itemView.findViewById(R.id.textViewName);
            this.textViewPrice = itemView.findViewById(R.id.textViewPrice);
            this.textViewChange = itemView.findViewById(R.id.textViewChange);
        }
    }

    RecyclerViewAdapter(List<DataAdapter> exampleList) {
        this.exampleList = exampleList;
       exampleListFull = new ArrayList<>(exampleList);
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView;
        LayoutInflater inflater=(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView=inflater.inflate(R.layout.recyclerview_layout, parent,false);
        return new DataViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        DecimalFormat df = new DecimalFormat("#.##");
        DataAdapter currentItem = exampleList.get(position);
        holder.imageView.setImageBitmap(textAsBitmap(currentItem.getImageArray(),30,R.color.red));
        holder.textViewName.setText(currentItem.getNameArray());
        holder.textViewPrice.setText("$" + df.format(currentItem.getPriceArray().doubleValue()));
        holder.textViewChange.setText("$" + df.format(currentItem.getChangeArray().doubleValue())+"%");
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DataAdapter> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (DataAdapter item : exampleListFull) {
                    if (item.getNameArray().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    public Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
}