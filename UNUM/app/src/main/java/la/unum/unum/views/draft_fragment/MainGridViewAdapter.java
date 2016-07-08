package la.unum.unum.views.draft_fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import la.unum.unum.R;

/**
 * Created by franklin-pierce on 07/07/16.
 */
public class MainGridViewAdapter extends ArrayAdapter<ImageView> {

    private Context context;
    private int layoutResourceId;
    private boolean b_selectedItem[];
    private ArrayList<ImageView> data = new ArrayList<ImageView>();

    public MainGridViewAdapter(Context context, int layoutResourceId, ArrayList<ImageView> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        b_selectedItem = new boolean[18];
        for (int i = 0; i < 18; i++)
            b_selectedItem[i] = false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder;

            if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.maindraft_image);
            row.setTag(holder);
            } else {
            holder = (ViewHolder) row.getTag();
            }

//        int padding = (position == selectedItemPos) ? 5 : 0;
        int padding = (b_selectedItem[position] == true) ? 7 : 0;
        row.setPadding(padding, padding, padding, padding);



            ImageView item = data.get(position);
            if (item == null)
                holder.image.setBackgroundColor(Color.BLACK);
            return row;
            }

    public void setItemSelected(int position) {
        if (b_selectedItem[position] == false){
            b_selectedItem[position] = true;
        }
        else
            b_selectedItem[position] = false;
        this.notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView image;
    }
}