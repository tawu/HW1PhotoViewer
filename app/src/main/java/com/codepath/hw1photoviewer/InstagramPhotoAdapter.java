package com.codepath.hw1photoviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by tawu on 10/25/15.
 */
public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto>
{
    //what data do we need from the activity, and allow us to map the data into listview
    //Context: context
    //Data Source: object
    public InstagramPhotoAdapter(Context context, List<InstagramPhoto> objects)
    {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    //what our item looks like
    //use the template to display each photo
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        InstagramPhoto photo = getItem(position);
        // check if we're using a recycled view, if not we need to inflate
        //if the view has been created but not show in screen, then we don't have to recreate it again
        if (convertView == null)
        {
            //not a recycled view, create a new view from template
            //inflate is taking a XML template file and turn it into a view
            //here, template is item_photo.xml, container within is its parent, and not to attach to its parent yet
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        //lookup the views for populating the data (image, caption),
        // since convertView is using item_photo as template, so all attributes of item_photo should be listed below
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        //Insert the model into each of the view items
        //Insert caption
        tvCaption.setText(photo.caption);
        //clear out the imageview
        ivPhoto.setImageResource(0);
        //insert the image to ivPhoto by using picasso
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
        //Return the created items as a view
        return convertView;
    }
}
