package la.unum.unum.views.draft_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import la.unum.unum.R;

/**
 * Created by franklin-pierce on 07/07/16.
 */
public class SecondaryFragment extends Fragment {
    private GridView gridView;
    private SecondaryGridViewAdapter gridAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frament_secondary, container, false);
        gridView = (GridView)view.findViewById(R.id.gridView_secondarydraft);
        gridAdapter = new SecondaryGridViewAdapter(this.getContext(), R.layout.secondarydraft_grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("Tag","Secondary draft Clicked~~~~~~~~~~~~~" + String.valueOf(position));
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
