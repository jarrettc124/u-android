package la.unum.unum.views.draft_fragment;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import la.unum.unum.R;

/**
 * Created by franklin-pierce on 07/07/16.
 */
public class MainDraftFragment extends Fragment {
    private GridView gridView;
    private MainGridViewAdapter gridAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maindraft, container, false);
        gridView = (GridView)view.findViewById(R.id.gridView_maindraft);
        gridAdapter = new MainGridViewAdapter(this.getContext(), R.layout.maindraft_grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("Tag","Clicked~~~~~~~~~~~~~" + String.valueOf(position));
                gridAdapter.setItemSelected(position);
            }
        });
        return view;
    }

    public ArrayList<ImageView> getData(){
        final ArrayList<ImageView> imageItems = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            imageItems.add(null);
        }
        return imageItems;
    }
}
