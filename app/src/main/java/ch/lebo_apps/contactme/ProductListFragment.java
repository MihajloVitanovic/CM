package ch.lebo_apps.contactme;

/**
 * Created by miki-ubuntu on 15.1.18..
 */

        import java.util.ArrayList;
        import java.util.List;
        import android.app.Activity;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.AdapterView.OnItemLongClickListener;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.Toast;

public class ProductListFragment extends Fragment implements
        OnItemClickListener, OnItemLongClickListener {

    public static final String ARG_ITEM_ID = "product_list";
    private Fragment contentFragment;
    Activity activity;
    ListView productListView;
    List<Product> products;
    ProductListAdapter productListAdapter;
    Button b1;
    FavoriteListFragment  favListFragment;
    public String[] stockArr;
    SharedPreference sharedPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        sharedPreference = new SharedPreference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container,
                false);
        findViewsById(view);
        setProducts();

        b1=(Button)view.findViewById(R.id.favButton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favListFragment = new FavoriteListFragment();
                switchContent(favListFragment, FavoriteListFragment.ARG_ITEM_ID);
            }
        });

        productListAdapter = new ProductListAdapter(activity, products);
        productListView.setAdapter(productListAdapter);
        productListView.setOnItemClickListener(this);
        productListView.setOnItemLongClickListener(this);
        return view;
    }

    private void setProducts() {

        Product product1 = new Product(1, "a Dell XPS", "Dell XPS Laptop", 60000);
        Product product2 = new Product(2, "a HP Pavilion G6-2014TX",
                "HP Pavilion G6-2014TX Laptop", 50000);
        Product product3 = new Product(3, "b ProBook HP 4540",
                "ProBook HP 4540 Laptop", 45000);
        Product product4 = new Product(4, "b HP Envy 4-1025TX",
                "HP Envy 4-1025TX Laptop", 46000);
        Product product5 = new Product(5, "b Dell Inspiron",
                "Dell Inspiron Laptop", 48000);
        Product product6 = new Product(6, "c Dell Vostro", "Dell Vostro Laptop",
                50000);
        Product product7 = new Product(7, "c IdeaPad Z Series",
                "Lenovo IdeaPad Z Series Laptop", 40000);
        Product product8 = new Product(8, "c ThinkPad X Series",
                "Lenovo ThinkPad X Series Laptop", 38000);
        Product product9 = new Product(9, "d VAIO S Series",
                "Sony VAIO S Series Laptop", 39000);
        Product product10 = new Product(10, "d Series 5",
                "Samsung Series 5 Laptop", 50000);

        products = new ArrayList<Product>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);
        products.add(product7);
        products.add(product8);
        products.add(product9);
        products.add(product10);
    }

    private void findViewsById(View view) {
        productListView = (ListView) view.findViewById(R.id.list_product);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Product product = (Product) parent.getItemAtPosition(position);
        Toast.makeText(activity, product.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                   int position, long arg3) {
        ImageView button = (ImageView) view.findViewById(R.id.imgbtn_favorite);

        String tag = button.getTag().toString();
        if (tag.equalsIgnoreCase("grey")) {
            sharedPreference.addFavorite(activity, products.get(position));
            Toast.makeText(activity,
                    activity.getResources().getString(R.string.add_favr),
                    Toast.LENGTH_SHORT).show();

            button.setTag("red");
            button.setImageResource(R.drawable.heart_red);
        } else {
            sharedPreference.removeFavorite(activity, products.get(position));
            button.setTag("grey");
            button.setImageResource(R.drawable.heart_grey);
            Toast.makeText(activity,
                    activity.getResources().getString(R.string.remove_favr),
                    Toast.LENGTH_SHORT).show();
        }

        return true;
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
        super.onResume();
    }
}