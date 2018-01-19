package ch.lebo_apps.contactme;

/**
 * Created by miki-ubuntu on 15.1.18..
 */

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.Comparator;
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
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.Toast;

        import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class ProductListFragment extends Fragment implements
        OnItemClickListener {

    public static final String ARG_ITEM_ID = "product_list";
    protected Fragment contentFragment;
    Activity activity;
    StickyListHeadersListView productListView;
    List<Product> products;
    ProductListAdapter productListAdapter;
    ImageButton b1;
    FavoriteListFragment  favListFragment;
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

        b1=(ImageButton)view.findViewById(R.id.favButton);
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
        return view;
    }

    private void setProducts() {
        Product product1 = new Product(1, "Shelly B. Johnson", "620-688-8204", "pic1.png");
        Product product2 = new Product(2, "Ronald M. Strout",
                "713-513-7638", "pic2.png");
        Product product3 = new Product(5, "Ashley C. Dale",
                "770-273-7513", "pic4.png");
        Product product4 = new Product(6, "Wanda E. Barcenas", "770-273-7513", "pic2.png");
        Product product5 = new Product(7, "James M. Pfister",
                "620-688-8204", "pic3.png");
        Product product6 = new Product(8, "Mark C. Davies",
                "770-273-7513", "pic4.png");
        Product product7 = new Product(9, "John C. Wysong",
                "770-273-7513",  "pic1.png");
        Product product8 = new Product(10, "Ruby J. Lombard",
                "620-688-8204", "pic3.png");
        Product product9 = new Product(10, "Samuel N. Andrews",
                "337-294-5048", "pic4.png");
        Product product10 = new Product(10, "Christine G. Ford",
                "678-463-4837", "pic2.png");

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

        Collections.sort(products, new Comparator<Product>(){
            public int compare(Product d1, Product d2){
                return d1.getName().compareTo(d2.getName());
            }
        });
    }

    private void findViewsById(View view) {
        productListView = (StickyListHeadersListView) view.findViewById(R.id.list_product);
    }



    @Override
    public void onItemClick(AdapterView<?> arg0, View view,
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