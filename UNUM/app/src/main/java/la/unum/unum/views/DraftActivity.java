package la.unum.unum.views;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import la.unum.unum.R;
import la.unum.unum.views.draft_fragment.MainDraftFragment;
import la.unum.unum.views.draft_fragment.SecondaryFragment;

public class DraftActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_drafts);
        setupViewPager(viewPager);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main_menuview);
        navigationView.setNavigationItemSelectedListener(this);
        ImageButton imageButton = (ImageButton)findViewById(R.id.btn_main_menu);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(DraftActivity.this, "Clicked Menu Button", Toast.LENGTH_SHORT).show();
                drawer.openDrawer(Gravity.LEFT);
            }
        });
    }
    private void setupViewPager(ViewPager viewPager) {
        DraftsAdapter adapter = new DraftsAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainDraftFragment(), "MainDraft");
        adapter.addFragment(new SecondaryFragment(), "SecondaryDraft");

        viewPager.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_feedback:
                Toast.makeText(DraftActivity.this,"Clicked Feedback", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_legal:
                Toast.makeText(DraftActivity.this,"Clicked LEGAL", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_logout:
                Toast.makeText(DraftActivity.this,"Clicked LOGOUT", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_settings:
                Toast.makeText(DraftActivity.this,"Clicked SETTINGS", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_tips:
                Toast.makeText(DraftActivity.this,"Clicked TIPS", Toast.LENGTH_SHORT).show();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    static class DraftsAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public DraftsAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
