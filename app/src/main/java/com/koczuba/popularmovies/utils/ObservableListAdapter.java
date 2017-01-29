package com.koczuba.popularmovies.utils;

import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 *
 * List adapter synced with ObservableList
 *
 * @param <T> List item class
 * @param <V> List item ViewHolder
 */

public abstract class ObservableListAdapter<T,V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    protected ObservableList<T> list;

    private ObservableList.OnListChangedCallback<ObservableList<T>> callback = new ObservableList.OnListChangedCallback<ObservableList<T>>() {
        @Override
        public void onChanged(ObservableList<T> sender) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
            notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
            notifyItemRangeRemoved(fromPosition, itemCount);
            notifyItemRangeInserted(toPosition, itemCount);
        }

        @Override
        public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart, itemCount);
        }
    };

    public ObservableListAdapter(@NonNull ObservableList<T> list) {
        this.list = list;
        this.list.addOnListChangedCallback(callback);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}