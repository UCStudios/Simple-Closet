package com.ucstudios.wardrobe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ItemVisualDialog extends Dialog implements View.OnClickListener {

    private Integer[] Icons = {
            R.drawable.nobrand,
            R.drawable.ic_nike,
            R.drawable.ic_adidas,
            R.drawable.ic_aber,
            R.drawable.ic_armani,
            R.drawable.ic_asics,
            R.drawable.ic_diadora,
            R.drawable.ic_eddiebauer,
            R.drawable.ic_bershka,
            R.drawable.ic_fila,
            R.drawable.ic_gaudi,
            R.drawable.ic_hollister,
            R.drawable.ic_kappa,
            R.drawable.ic_lacoste,
            R.drawable.ic_oakley,
            R.drawable.ic_pullbear,
            R.drawable.ic_seiko,
            R.drawable.ic_americaneagle,
            R.drawable.ic_billabong,
            R.drawable.ic_burton,
            R.drawable.ic_ck,
            R.drawable.ic_celio,
            R.drawable.ic_champions,
            R.drawable.ic_chanel,
            R.drawable.ic_coach,
            R.drawable.ic_columbia,
            R.drawable.ic_dior,
            R.drawable.ic_gap,
            R.drawable.ic_gucci,
            R.drawable.ic_hm,
            R.drawable.ic_hermes,
            R.drawable.ic_boss,
            R.drawable.ic_lee,
            R.drawable.ic_levis,
            R.drawable.ic_lv,
            R.drawable.ic_mk,
            R.drawable.ic_napa,
            R.drawable.ic_nb,
            R.drawable.ic_puma,
            R.drawable.ic_quicksilver,
            R.drawable.ic_rayban,
            R.drawable.ic_stradivarius,
            R.drawable.ic_supreme,
            R.drawable.ic_northface,
            R.drawable.ic_trasher,
            R.drawable.ic_timberland,
            R.drawable.ic_under,
            R.drawable.ic_vans,
            R.drawable.ic_volcom,
            R.drawable.ic_wrangler,
            R.drawable.ic_zara,





    };
    public Integer alieno;
    public EditText name;
    public EditText size;
    public EditText brand;
    public EditText value;
    public Spinner spinner;
    public Button okbutton;
    public ImageView icons;
    public ImageView image;
    public Button binbutton;
    ItemCreatedInterface mItemCreation;
    CustomIconPickerDialogItems dialogItems;
    CameraActivation mCameraActivation;
    Integer iconvalue;
    String[] itemdatas;
    ArrayList<byte[]> tech;
    DatabaseHelper mDatabaseHelper;
    MainActivity mainActivity;
    String table;
    String olditem;



    public ItemVisualDialog(Context context, Integer mistico, ArrayList<String> itemdata,ArrayList<byte[]> tech,String table){
        super(context);


        this.alieno=mistico;
        this.itemdatas= itemdata.toArray(new String[0]);
        this.tech = tech;
        this.table=table;

    }



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item_visual_dialog);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        name = findViewById(R.id.name);
        size = findViewById(R.id.size);
        brand = findViewById(R.id.brand);
        value = findViewById(R.id.value);
        spinner = findViewById(R.id.currencyspinner);
        okbutton = findViewById(R.id.button3);
        icons = findViewById(R.id.icon);
        image = findViewById(R.id.image);
        binbutton = findViewById(R.id.button4);
        dialogItems = new CustomIconPickerDialogItems(getContext());
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),R.array.currencies,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        okbutton.setOnClickListener(this);
        icons.setOnClickListener(this);
        image.setOnClickListener(this);
        binbutton.setOnClickListener(this);
        iconvalue=0;
        mDatabaseHelper=new DatabaseHelper(getContext());
        mainActivity = new MainActivity();

      if(alieno==1){
            name.setText(itemdatas[0]);
            size.setText(itemdatas[1]);
            brand.setText(itemdatas[2]);
            value.setText(itemdatas[3]);
            image.setImageBitmap(Utils.getImage(tech.get(0)));
            binbutton.setVisibility(View.VISIBLE);
           olditem=itemdatas[0];

        }



}




    @Override
    public void onClick(View v) {
        if(alieno==0){

        switch (v.getId()){
                case R.id.icon:
                dialogItems.show();
                dialogItems.setIconResult(new CustomIconPickerDialogItems.OnIconSelected() {
                    @Override
                    public void finish(int icon) {
                        iconvalue = icon;
                        icons.setImageResource(Icons[icon]);
                    }
                });
                break;
            case R.id.button3:
                if(String.valueOf(name.getText()).equals("")||String.valueOf(size.getText()).equals("")||String.valueOf(brand.getText()).equals("")||String.valueOf(value.getText()).equals("")){
                    Toast.makeText(getContext(),"Something is still empty  :( ", Toast.LENGTH_SHORT).show();
                }

                else {
                    mItemCreation.finish(String.valueOf(name.getText()), String.valueOf(size.getText()), String.valueOf(brand.getText()), Integer.parseInt(String.valueOf(value.getText())), spinner.getSelectedItemPosition(), iconvalue,olditem);
                }

            break;
            case R.id.image:
                mCameraActivation.activation(1);
                image.setImageResource(R.drawable.ic_tick_orange);
                break;


        }}
        if(alieno==1){
            switch (v.getId()){

               case R.id.icon:
                    dialogItems.show();
                    dialogItems.setIconResult(new CustomIconPickerDialogItems.OnIconSelected() {
                        @Override
                        public void finish(int icon) {
                            iconvalue = icon;
                            icons.setImageResource(Icons[icon]);
                        }
                    });
                    break;



                case R.id.button3:

                    if(String.valueOf(name.getText()).equals("")||String.valueOf(size.getText()).equals("")||String.valueOf(brand.getText()).equals("")||String.valueOf(value.getText()).equals("")){
                        Toast.makeText(getContext(),"Something is still empty  :( ", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        mItemCreation.finish(String.valueOf(name.getText()), String.valueOf(size.getText()), String.valueOf(brand.getText()), Integer.parseInt(String.valueOf(value.getText())), spinner.getSelectedItemPosition(), iconvalue,olditem);
                        Toast.makeText(getContext(), "Item modified!", Toast.LENGTH_SHORT).show();
                    }
                    break;

                    case R.id.image:
                    mCameraActivation.activation(2);
                    break;

                case R.id.button4:

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {

                        }
                    });
                    builder.setTitle("Are you sure?");
                    final TextView sex = new TextView(getContext());
                    sex.setText("Sure about deleting this?");
                    sex.setGravity(Gravity.CENTER);


                    builder.setView(sex);
                    builder.setPositiveButton("OK", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDatabaseHelper.delete4(table,itemdatas[0]);
                            mDatabaseHelper.DeleteIteminOutfitafteredit(table,olditem);
                            dismiss();
                        }
                    });
                    builder.show();


                    break;




            }

        }





    }




    public void ItemCreation(ItemCreatedInterface itemcreated){

        mItemCreation = itemcreated;
    }

    public interface ItemCreatedInterface{

        void finish(String name,String size,String brand,Integer value,Integer currency,Integer icon,String olditemname);
    }

    public void CameraActivation(CameraActivation activation){
        mCameraActivation = activation;

    }

    public interface CameraActivation {

        void activation (int a);
    }






}
