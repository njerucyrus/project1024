package com.apps.project1024.interfaces;

public interface RecyclerItemClickListener<T> {
    void onItemClicked(T item);

    default T getItemAt(int position){

        return null;
    }

    //used to restore deleted item on swipe to delete functionality.
    // this is mostly when you want to perform the UNDO DELETE.
    default void restoreItem(T item, int position){

    }
}
