package ch.lebo_apps.contactme;

/**
 * Created by miki-ubuntu on 15.1.18..
 */

        import java.io.IOException;
        import java.io.InputStream;
        import java.util.List;
        import android.app.Activity;
        import android.content.Context;
        import android.graphics.drawable.Drawable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;
        import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


public class ProductListAdapter extends ArrayAdapter<Product> implements StickyListHeadersAdapter {

    private Context context;
    private List<Product> products;
    private SharedPreference sharedPreference;
    private LayoutInflater inflater;
    public ProductListAdapter(Context context, List<Product> products) {
        super(context, R.layout.product_list_item, products);
        this.context = context;
        this.products = products;
        sharedPreference = new SharedPreference();
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder {
        TextView productNameTxt;
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
            convertView = inflater.inflate(R.layout.product_list_item, null);
            holder = new ViewHolder();
            holder.productNameTxt = (TextView) convertView
                    .findViewById(R.id.txt_pdt_name);
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

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.listview_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.header_text);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        String headerText = "" + products.get(position).getName().subSequence(0, 1).charAt(0);
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return products.get(position).getName().subSequence(0, 1).charAt(0);
    }

    class HeaderViewHolder {
        TextView text;
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
    public void add(Product product) {
        super.add(product);
        products.add(product);
        notifyDataSetChanged();
    }

    @Override
    public void remove(Product product) {
        super.remove(product);
        products.remove(product);
        notifyDataSetChanged();
    }
}