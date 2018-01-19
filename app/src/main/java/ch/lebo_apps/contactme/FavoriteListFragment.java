package ch.lebo_apps.contactme;

/**
 * Created by miki-ubuntu on 15.1.18..
 */

        import java.util.Collections;
        import java.util.Comparator;
        import java.util.List;
        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.GridView;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.Toast;

public class FavoriteListFragment extends Fragment {

    public static final String ARG_ITEM_ID = "favorite_list";
    protected Fragment contentFragment;
    GridView favoriteList;
    SharedPreference sharedPreference;
    List<Product> favorites;
    Activity activity;
    FavoriteListAdapter favoriteListAdapter;
    ProductListFragment  productListFragment;
    ImageButton b1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list2, container,
                false);
        // Get favorite items from SharedPreferences.
        sharedPreference = new SharedPreference();
        favorites = sharedPreference.getFavorites(activity);

        b1=(ImageButton)view.findViewById(R.id.favButton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productListFragment = new ProductListFragment();
                switchContent(productListFragment, ProductListFragment.ARG_ITEM_ID);
            }
        });

        Collections.sort(favorites, new Comparator<Product>(){
            public int compare(Product d1, Product d2){
                return d1.getName().compareTo(d2.getName());
            }
        });

        if (favorites == null) {
            showAlert(getResources().getString(R.string.no_favorites_items),
                    getResources().getString(R.string.no_favorites_msg));
        } else {

            if (favorites.size() == 0) {
                showAlert(
                        getResources().getString(R.string.no_favorites_items),
                        getResources().getString(R.string.no_favorites_msg));
            }

            favoriteList = (GridView) view.findViewById(R.id.list_product2);
            if (favorites != null) {
                favoriteListAdapter = new FavoriteListAdapter(activity, favorites);
                favoriteList.setAdapter(favoriteListAdapter);

                favoriteList.setOnItemClickListener(new OnItemClickListener() {

                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long arg3) {

                        ImageView button = (ImageView) view.findViewById(R.id.imgbtn_favorite);

                            sharedPreference.removeFavorite(activity, favorites.get(position));
                            button.setTag("grey");
                            button.setImageResource(R.drawable.heart_grey);
                            favoriteListAdapter.remove(favorites.get(position));
                            Toast.makeText(activity,
                                    activity.getResources().getString(R.string.remove_favr),
                                    Toast.LENGTH_SHORT).show();

                    }
                });


            }
        }

        return view;
    }


    public void showAlert(String title, String message) {
        if (activity != null && !activity.isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(activity)
                    .create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);

            // setting OK Button
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // activity.finish();
                            getFragmentManager().popBackStackImmediate();
                        }
                    });
            alertDialog.show();
        }
    }

    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getFragmentManager();
        while (fragmentManager.popBackStackImmediate());

        if (fragment != null) {
            android.support.v4.app.FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_frame, fragment, tag);
            //Only FavoriteListFragment is added to the back stack.
            if (!(fragment instanceof ProductListFragment)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentFragment = fragment;
        }
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.favorites);
        super.onResume();
    }
}