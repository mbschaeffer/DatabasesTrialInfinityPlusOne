package com.kiskiarea.databasestrialinfinityplusone;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Melissa on 11/15/2015.
 */
public class MyCustomAdapter extends BaseAdapter {
    private ArrayList<String> mListItems;
    private LayoutInflater mLayoutInflater;

    public MyCustomAdapter(Context context, ArrayList<String> arrayList)
    {
        mListItems = arrayList;

        //get the layout inflater
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return mListItems.size();
    }

    @Override
    /*get the data of an item from a specific position
     i represents the position of the item in the list
     */
    public Object getItem(int i)
    { return  null;}


    @Override
    //get the position id of the item from the list
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        // Create a viewHolder reference
        ViewHolder holder;

        //check to see if the reused view is null or not, if it is not null then reuse it
        if(view == null)
        {
            holder = new ViewHolder();

            holder.itemName = (TextView) view.findViewById(R.id.list_item_text_view);

            //the set tag is used to store the data within this view
            view.setTag(holder);

        }else {
            holder = (ViewHolder) view.getTag();
        }

        //get strign item from the position from array list
        String stringItem = mListItems.get(position);
        if(stringItem != null)
        {
            if(holder.itemName != null)
            {
                holder.itemName.setText(stringItem);
            }
        }
        return view;
    }

    private static class ViewHolder{
        protected TextView itemName;
    }
}

