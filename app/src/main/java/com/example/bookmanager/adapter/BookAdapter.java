package com.example.bookmanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookmanager.R;
import com.example.bookmanager.dao.SachDao;
import com.example.bookmanager.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends BaseAdapter implements Filterable {
    List<Sach> arrSach;
    List<Sach> arrSortSach;
    private Filter sachFiler;
    public Activity context;
    public LayoutInflater inflater;
    SachDao sachDao;

    public BookAdapter(Activity context, List<Sach> arraySach) {
        super();
        this.context = context;
        this.arrSach = arraySach;
        this.arrSortSach = arraySach;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sachDao = new SachDao(context);
    }

    @Override
    public int getCount() {
        return arrSach.size();
    }

    @Override
    public Object getItem(int position) {
        return arrSach.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder{
        ImageView img;
        TextView txtBookName;
        TextView txtBookPrice;
        TextView txtSoLuong;
        TextView txtTheLoai;
        ImageView imgDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_book, null);
            holder.img = (ImageView) convertView.findViewById(R.id.ivIcon);
            holder.txtBookName = (TextView) convertView.findViewById(R.id.tvBookName);
            holder.txtBookPrice = (TextView) convertView.findViewById(R.id.tvBookPrice);
            holder.txtSoLuong = (TextView) convertView.findViewById(R.id.tvSoLuong);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.ivDelete);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sachDao.deleteSachByID(arrSach.get(position).getMaSach());
                    arrSach.remove(position);
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(holder);
        }else
            holder = (ViewHolder)convertView.getTag();
        Sach _entry = (Sach) arrSach.get(position);
        holder.img.setImageResource(R.drawable.bookicon);
        holder.txtBookName.setText("Mã sách: "+_entry.getMaSach());
        holder.txtSoLuong.setText("Số lượng: "+_entry.getSoLuong());
        holder.txtBookPrice.setText("Giá: "+_entry.getGiaBia()+"");

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<Sach> items){
        this.arrSach = items;
        notifyDataSetChanged();
    }

    public void resetData(){
        arrSach = arrSortSach;
    }

    public Filter getFilter(){
        if (sachFiler == null){
            sachFiler = new CustomFilter();
        }
        return sachFiler;
    }

    private class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0){
                results.values = arrSortSach;
                results.count = arrSortSach.size();
            }else {
                List<Sach> lsSach = new ArrayList<Sach>();
                for (Sach p : arrSach){
                    if (p.getMaSach().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        lsSach.add(p);
                }
                results.values = lsSach;
                results.count = lsSach.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0){
                notifyDataSetInvalidated();
            }else {
                arrSach = (List<Sach>) results.values;
                notifyDataSetChanged();
            }
        }
    }


}
