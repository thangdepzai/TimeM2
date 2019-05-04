package hust.edu.vn.timem.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ImageListAdapter extends BaseAdapter
{
    private Context context;
    private List<String> imgPic;
    public ImageListAdapter(Context c, List<String> thePic)
    {
        context = c;
        imgPic = thePic;
    }
    public int getCount() {
        if(imgPic != null)
            return imgPic.size();
        else
            return 0;
    }

    //---returns the ID of an item---
    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    //---returns an ImageView view---
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;
        BitmapFactory.Options bfOptions=new BitmapFactory.Options();
        bfOptions.inDither=false;                     //Disable Dithering mode
        bfOptions.inPurgeable=true;                   //Tell to gc that whether it needs free memory, the Bitmap can be cleared
        bfOptions.inInputShareable=true;              //Which kind of reference will be used to recover the Bitmap data after being clear, when it will be used in the future
        bfOptions.inTempStorage=new byte[32 * 1024];
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }
        FileInputStream fs = null;
        Bitmap bm;
        try {
            fs = new FileInputStream(new File(imgPic.get(position).toString()));

            if(fs!=null) {
                bm=BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
                imageView.setImageBitmap(bm);
                imageView.setId(position);
                imageView.setLayoutParams(new GridView.LayoutParams(200, 160));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(fs!=null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return imageView;
    }
}
