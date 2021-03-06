package com.ucstudios.wardrobe;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.CellSignalStrength;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
//RISOLVERE PROBLEMA POSITION SWAPS
import java.util.ArrayList;
import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    String secca;
    DatabaseHelper mDatabaseHelper1;
    MainActivity mMainActivity;
    private RecyclerView mRecyclerView;
    public ArrayList<String> canecazzo = new ArrayList<>();
    FloatingActionButton floatingActionButton1;
    int tac;
    byte[] alien2o;
    static final int REQUEST_IMAGE_CAPTURE=1;
    int magianera;
    final ArrayList<byte[]> tech = new ArrayList<>();
    final ArrayList<byte[]> tecca = new ArrayList<>();
    Button gridbutton;
    ArrayList<String> ItemsInBasket = new ArrayList<>();
    int controllodivider;
    ImageView emptylist;
    TextView emptylisttext;
    ImageView IconAtTop;
    GlobalBoolean CategoryClicked;

    private Integer[] Icons = {
            R.drawable.ic_sweater,
            R.drawable.ic_jeans,
            R.drawable.ic_hoodie,
            R.drawable.ic_shoes,
            R.drawable.ic_backpack,
            R.drawable.ic_denim,
            R.drawable.ic_shirt,
            R.drawable.ic_watch,
            R.drawable.ic_basketballjersey,
            R.drawable.ic_bathrobe,
            R.drawable.ic_belt,
            R.drawable.ic_blouse,
            R.drawable.ic_boot,
            R.drawable.ic_boot2,
            R.drawable.ic_bowtie,
            R.drawable.ic_bra,
            R.drawable.ic_cap,
            R.drawable.ic_coat,
            R.drawable.ic_dress,
            R.drawable.ic_glasses,
            R.drawable.ic_gloves,
            R.drawable.ic_bag,
            R.drawable.ic_hat,
            R.drawable.ic_heels,
            R.drawable.ic_jacket,
            R.drawable.ic_pimuno,
            R.drawable.ic_jacket2,
            R.drawable.ic_necklace,
            R.drawable.ic_salopette,
            R.drawable.ic_mutandefemmina,
            R.drawable.ic_cargo,
            R.drawable.ic_polo,
            R.drawable.ic_24h,
            R.drawable.ic_purse,
            R.drawable.ic_scarf,
            R.drawable.ic_tee,
            R.drawable.ic_top,
            R.drawable.ic_mocasso,
            R.drawable.ic_shorts,
            R.drawable.ic_papere,
            R.drawable.ic_skirt,
            R.drawable.ic_slippers,
            R.drawable.ic_socks,
            R.drawable.ic_tie,
            R.drawable.ic_trench,
            R.drawable.ic_underwear,
            R.drawable.ic_vest,
            R.drawable.ic_wallet,
            R.drawable.ic_winterhat,




    };




    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {

            android.Manifest.permission.CAMERA
    };


    RecyclerAdapterItems recyclerAdapter;


    public ListFragment() {

    }


    public static ListFragment newInstance(String secca) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, secca);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            secca = getArguments().getString(ARG_PARAM1);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        CategoryClicked = ((GlobalBoolean) Objects.requireNonNull(this.getActivity()).getApplicationContext());
        floatingActionButton1 = view.findViewById(R.id.floating_action_button2);
        floatingActionButton1.setOnClickListener(this);
        IconAtTop = view.findViewById(R.id.iconattop);
        mDatabaseHelper1 = new DatabaseHelper(getActivity());
        gridbutton = view.findViewById(R.id.buttongrid);
        gridbutton.setOnClickListener(this);
        mRecyclerView = view.findViewById(R.id.spezzaossa2);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mMainActivity = (MainActivity) getActivity();
        TextView mTextView = view.findViewById(R.id.textView);
        mTextView.setText(CategoryClicked.getClickedCategory());
        mTextView.setTypeface(mTextView.getTypeface(), Typeface.BOLD);
        mDatabaseHelper1.getData();
        controllodivider=0;
        emptylist = view.findViewById(R.id.imageViewListavuota);
        emptylisttext = view.findViewById(R.id.textViewlistavuota);
        populateTopIcon();
        try {
            populateItems();
        }catch (Exception ignored){

        }





        return view;
    }



    public void AddData1(String name, String size, String brand, Integer value, Integer currency, Integer icon, byte[] alien2o) {
        boolean insertData = mDatabaseHelper1.addData1(CategoryClicked.getClickedCategory(), name,size,icon,brand,value,currency,alien2o);
        toastMessage("New Item Created!");

    }

    public void GetItemData(int position, String tablename){
        mDatabaseHelper1.GetItemData(position,tablename);
    }

    private void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }

    public  void Replace(String Table, String item,String size,String brand,Integer value,Integer currency,Integer icon,byte[] alien2o, int i){
        mDatabaseHelper1.ReplaceItem(Table, item, size, brand,value,currency,icon,alien2o,i);


    }

    public void Delete(String MainActName, String item){
        mDatabaseHelper1.delete4(MainActName, item);
    }


    private void populateTopIcon(){
        int puppaicona = 0;
        Cursor data = mDatabaseHelper1.getCategoryIcon(CategoryClicked.getClickedCategory());
        while(data.moveToNext()){
                puppaicona = data.getInt(0);
        }
        IconAtTop.setImageResource(Icons[puppaicona]);

    }




    private void populateItems() {

        final Cursor data = mDatabaseHelper1.getData1(CategoryClicked.getClickedCategory());
        final ArrayList<String> listData = new ArrayList<>();
        final ArrayList<Integer> position = new ArrayList<>();
        final ArrayList<Integer> icons = new ArrayList<>();
        int controlloperempty=0;
        while (data.moveToNext()) {
            listData.add(data.getString(0));
            controlloperempty++;
            canecazzo.add(data.getString(0));
            position.add(data.getInt(2));
            icons.add(data.getInt(1));
            tech.add(data.getBlob(7));
        }
        if(controlloperempty==0){
            emptylist.setVisibility(View.VISIBLE);
            emptylisttext.setVisibility(View.VISIBLE);
        }
        else{
            emptylist.setVisibility(View.GONE);
            emptylisttext.setVisibility(View.GONE);
        }
        int tecnicissimo=0;
        final Cursor iconanecessaria = mDatabaseHelper1.getCategoryIcon(CategoryClicked.getClickedCategory());
        while (iconanecessaria.moveToNext()){
            tecnicissimo=iconanecessaria.getInt(0);

        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerAdapter = new RecyclerAdapterItems(getContext(),position,listData,icons,tech,CategoryClicked.getClickedCategory(),tecnicissimo);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(recyclerAdapter);
        if(controllodivider==0){
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        controllodivider=1;
        }
        boolean risultato = longClickListener.onLongClick(mRecyclerView);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(recyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
        recyclerAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mRecyclerView.indexOfChild(v);
                final Cursor magiabianca = mDatabaseHelper1.GetItemData(pos+1,CategoryClicked.getClickedCategory());
                final ArrayList<String> itemdata2 = new ArrayList<>();
                final ArrayList<byte[]> negromatto = new ArrayList<>();
                while (magiabianca.moveToNext()){
                    itemdata2.add(magiabianca.getString(0));
                    itemdata2.add(magiabianca.getString(4));
                    itemdata2.add(String.valueOf(magiabianca.getInt(5)));
                    negromatto.add(magiabianca.getBlob(7));
                }
              final VisualDialogItemDialogTrue  dialogItemDialogTrue = new VisualDialogItemDialogTrue(getActivity(),itemdata2,negromatto);
                dialogItemDialogTrue.show();
                dialogItemDialogTrue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Log.i("msg","Traffica"+itemdata2.get(1));
            }
        });
        //dragListener.onDrag(recyclerView, risultato);


    }





    private View.OnLongClickListener longClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            ClipData merda = ClipData.newPlainText("", "");
            View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(merda, myShadowBuilder, v, 0);
            v.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    Log.i("msg","PICCHIASPORHI");
                    return false;
                }
            });
            return true;
        }
    };

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction(); //aspetta
            switch(dragEvent) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.i("msg","PICCHIASPORHI");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.i("msg","PICCHIASPORHI");
                    break;
                case DragEvent.ACTION_DROP:
                    Log.i("msg","PICCHIASPORHI");
                    break;
            }
            return true;
        }
    };


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }


        @SuppressLint("SetTextI18n")
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();
            tac = position;

            switch (direction) {


                case ItemTouchHelper.RIGHT:{
                    final Cursor C = mDatabaseHelper1.GetItemData(position+1,CategoryClicked.getClickedCategory());
                    final ArrayList<Integer> itemdata = new ArrayList<>();


                    while (C.moveToNext()){
                        itemdata.add(C.getInt(2));


                    }

                    if(itemdata.get(0)==1){
                        Toast.makeText(getContext(),"Item is already in the basket!",Toast.LENGTH_SHORT).show();
                        try {
                            populateItems();
                        }catch (Exception ignored){

                        }
                    }
                    else if(itemdata.get(0)==2){
                        Toast.makeText(getContext(),"Item is in the laundry!",Toast.LENGTH_SHORT).show();
                        try {
                            populateItems();
                        }catch (Exception ignored){

                        }
                        }

                    else if(itemdata.get(0)==3){
                        Toast.makeText(getContext(),"Item is getting washed right now!",Toast.LENGTH_SHORT).show();
                        try {
                            populateItems();
                        }catch (Exception ignored){

                        }
                    }
                    else{

                            mDatabaseHelper1.toBasket(CategoryClicked.getClickedCategory(), position+1);
                            try{
                                populateItems();
                            }
                            catch(Exception ignored){

                            }
                            Snackbar.make(mRecyclerView, CategoryClicked.getClickedCategory(), 2000).setAction(
                                    "Put item back", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDatabaseHelper1.toWardrobemodif(CategoryClicked.getClickedCategory(), position+1);
                                    try {
                                        populateItems();
                                    }
                                    catch (Exception ignored){

                                    }
                                }
                            }).show();
                    }
            break;}




                case ItemTouchHelper.LEFT:
                    final Cursor C = mDatabaseHelper1.GetItemData(position+1,CategoryClicked.getClickedCategory());
                    final ArrayList<String> itemdata = new ArrayList<>();


                    while (C.moveToNext()){
                        itemdata.add(C.getString(0));
                        itemdata.add(C.getString(3));
                        itemdata.add(C.getString(4));
                        itemdata.add(String.valueOf(C.getInt(5)));
                        itemdata.add(String.valueOf(C.getInt(2)));
                        tecca.add(C.getBlob(7));

                    }
                    final ItemVisualDialog dialog = new ItemVisualDialog(getActivity(),1,itemdata,tecca,CategoryClicked.getClickedCategory());
                    dialog.show();
                    dialog.CameraActivation(new ItemVisualDialog.CameraActivation() {
                        @Override
                        public void activation(int a) {
                            if (a==2){

                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if(intent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager())!=null) {
                                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                                }
                            }
                        }
                    });
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            try{
                            populateItems();}
                            catch (Exception ignored){

                            }
                        }
                    });
                    dialog.ItemCreation(new ItemVisualDialog.ItemCreatedInterface() {
                        @Override
                        public void finish(String name, String size, String brand, Integer value, Integer currency, Integer icon, String olditemname) {
                            Cursor totaleCateg = mDatabaseHelper1.getData();
                            int control = 0;
                            final ArrayList<String> totalecateg = new ArrayList<>();
                            while (totaleCateg.moveToNext()){
                                totalecateg.add(totaleCateg.getString(0));
                            }
                            for(int is=0;is<totalecateg.size();is++){
                                Cursor c = mDatabaseHelper1.getData1(totalecateg.get(is));
                                final ArrayList<String> UniquenessControl = new ArrayList<>();

                                while (c.moveToNext()) {
                                    UniquenessControl.add(c.getString(0));
                                }
                                for (int i = 0; i < UniquenessControl.size(); i++) {
                                    if (name.equals(UniquenessControl.get(i))) {
                                        if(name.equals(itemdata.get(0))){}
                                        else{
                                        control++;}
                                    }
                                }}

                           if(control==0){


                            if(alien2o!=null){
                                Replace(CategoryClicked.getClickedCategory(),name,size,brand,value,currency,icon,alien2o,position+1);

                            }
                            else{
                                mDatabaseHelper1.ReplaceItemnoPic(CategoryClicked.getClickedCategory(),name,size,brand,value,currency,icon,position+1);

                            }
                            mDatabaseHelper1.ReplaceIteminOutfitTable(name,CategoryClicked.getClickedCategory(),olditemname);
                            dialog.dismiss();
                            try {
                                populateItems();
                            }catch (Exception e){

                            }

                            }


                        else {
                            Toast.makeText(getContext(),"An item with this name already exists!",Toast.LENGTH_SHORT).show();
                           }}

                    });

                    break;
                        }



            }




        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorAccent))
                    .addSwipeLeftActionIcon(R.drawable.ic_editimsto)
                    .create()
                    .decorate();
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(),R.color.bluetattico))
                    .addSwipeRightActionIcon(R.drawable.ic_basket24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };








    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }




            @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.floating_action_button2:

                final ArrayList<String> crack = new ArrayList<>();
                final ArrayList<byte[]> technon = new ArrayList<>();
                final ItemVisualDialog dialog = new ItemVisualDialog(getActivity(),0,crack,technon,CategoryClicked.getClickedCategory());

                if (!hasPermissions(getContext(), PERMISSIONS)) {
                    ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), PERMISSIONS, PERMISSION_ALL);
                }

                if (hasPermissions(getContext(), PERMISSIONS)) {
                    dialog.show();
                    dialog.CameraActivation(new ItemVisualDialog.CameraActivation() {
                        @Override
                        public void activation(int a) {
                            if (a == 1) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (intent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {

                                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                                }
                            }
                        }
                    });
                    dialog.ItemCreation(new ItemVisualDialog.ItemCreatedInterface() {


                        @Override
                        public void finish(String name, String size, String brand, Integer value, Integer currency, Integer icon, String olditemname) {
                            if (magianera == 1) {
                                Cursor totaleCateg = mDatabaseHelper1.getData();
                                int control = 0;
                                final ArrayList<String> totalecateg = new ArrayList<>();
                                while (totaleCateg.moveToNext()){
                                    totalecateg.add(totaleCateg.getString(0));
                                }
                               for(int is=0;is<totalecateg.size();is++){
                                Cursor c = mDatabaseHelper1.getData1(totalecateg.get(is));
                                final ArrayList<String> UniquenessControl = new ArrayList<>();

                                while (c.moveToNext()) {
                                    UniquenessControl.add(c.getString(0));
                                }
                                for (int i = 0; i < UniquenessControl.size(); i++) {
                                    if (name.equals(UniquenessControl.get(i))) {
                                        control++;
                                    }
                                }}
                                if (control == 0) {
                                    AddData1(name, size, brand, value, currency, icon, alien2o);
                                    mDatabaseHelper1.AddPictureItem(CategoryClicked.getClickedCategory(), alien2o, tac);
                                    dialog.dismiss();
                                    try {
                                        populateItems();
                                    }
                                    catch (Exception ignored){

                                    }
                                } else {
                                    Toast.makeText(getContext(), "Item doppio!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Pic missing!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(), "The permissions were requested", Toast.LENGTH_LONG).show();
                }

                break;




            case R.id.buttongrid:
                Log.i("msg","cliccato");

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container,new GridViewListFragment());
                transaction.commit();

                break;
        }


                    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode,resultCode,data);
            if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==-1) {
                Bundle extras = data.getExtras();
                assert extras != null;
                Bitmap photo = (Bitmap) extras.get("data");
                assert photo != null;
                alien2o = Utils.getBytes(photo);
                magianera = 1;

            }






            }




}






