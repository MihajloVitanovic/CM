package ch.lebo_apps.contactme;

/**
 * Created by miki-ubuntu on 15.1.18..
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class FavoriteListAdapter extends ArrayAdapter<Product> {

    private Context context;
    private List<Product> products;
    private SharedPreference sharedPreference;
    private LayoutInflater inflater;
    public FavoriteListAdapter(Context context, List<Product> products) {
        super(context, R.layout.product_list_item2, products);
        this.context = context;
        this.products = products;
        sharedPreference = new SharedPreference();
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder {
        TextView productNameTxt;
        TextView productDescTxt;
        ImageView profilePic;
        ImageView favoriteImg;
    }




    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return products.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.product_list_item2, null);
            holder = new ViewHolder();
            holder.productNameTxt = (TextView) convertView
                    .findViewById(R.id.txt_pdt_name);
            holder.productDescTxt = (TextView) convertView
                    .findViewById(R.id.txt_pdt_desc);
            holder.profilePic = (ImageView) convertView
                    .findViewById(R.id.contact_picture);
            holder.favoriteImg = (ImageView) convertView
                    .findViewById(R.id.imgbtn_favorite);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        holder.productNameTxt.setText(product.getName());
        holder.productDescTxt.setText(product.getNumber());
        InputStream is = null;
        try {
            is = getContext().getAssets().open(product.getImage());

        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable drawable;
        drawable = Drawable.createFromStream(is, null);
            holder.profilePic.setImageDrawable(drawable);

		/*If a product exists in shared preferences then set heart_red drawable
		 * and set a tag*/
        if (checkFavoriteItem(product)) {
            holder.favoriteImg.setImageResource(R.drawable.heart_red);
            holder.favoriteImg.setTag("red");
        } else {
            holder.favoriteImg.setImageResource(R.drawable.heart_grey);
            holder.favoriteImg.setTag("grey");
        }

        return convertView;
    }


    /*Checks whether a particular product exists in SharedPreferences*/
    public boolean checkFavoriteItem(Product checkProduct) {
        boolean check = false;
        List<Product> favorites = sharedPreference.getFavorites(context);
        if (favorites != null) {
            for (Product product : favorites) {
                if (product.equals(checkProduct)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    @Override
    public void remove(Product product) {
        super.remove(product);
        products.remove(product);
        notifyDataSetChanged();
    }
}