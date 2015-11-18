package com.kiskiarea.databasestrialinfinityplusone;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {

    TextView resultsView;

    EditText nameBoxFind;
    TextView elementNameBox;
    TextView atomicNumberBox;
    TextView atomicWeightBox;
    TextView symbolBox;
    TextView meltingBox;
    TextView boilingBox;
    TextView densityBox;
    TextView phaseBox;
    SearchView elementSearchV;


    /*---Used for searchable list view */
    private ListView myList;
    private ArrayList<String> nameList;
    MyCustomAdapter defaultAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        resultsView = (TextView) findViewById(R.id.txtResults);
        nameBoxFind = (EditText) findViewById(R.id.txtFindElement);
        elementNameBox = (TextView) findViewById(R.id.txtName);
        atomicNumberBox = (TextView) findViewById(R.id.txtAtomicNumber);
        atomicWeightBox = (TextView) findViewById(R.id.txtAtomicWeight);
        symbolBox = (TextView) findViewById(R.id.txtSymbol);
        meltingBox = (TextView) findViewById(R.id.txtMelting);
        boilingBox = (TextView) findViewById(R.id.txtBoiling);
        densityBox = (TextView) findViewById(R.id.txtDensity);
        phaseBox = (TextView) findViewById(R.id.txtPhase);
        elementSearchV = (SearchView) findViewById(R.id.searchView);

        DBAdapter db = new DBAdapter(this);

        try {
            String destPath = "/data/data/" + getPackageName() + "/databases/";

            File f = new File(destPath);

            if (!f.exists()) {

                Toast.makeText(this, "File does not exist", Toast.LENGTH_LONG).show();


                f.mkdirs();
                f.createNewFile();


                CopyDB(getBaseContext().getAssets().open("myPeriodicTableDB"), new FileOutputStream(destPath + "/myPeriodicTableDB"));

            }
            Toast.makeText(this, "The file exists!", Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File not found", Toast.LENGTH_LONG).show();

            e.printStackTrace();
        } catch (IOException e) {

            Toast.makeText(this, "IOException issue", Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }


        db.open();
        Cursor c = db.getAllElements();
        Toast.makeText(this, "got the elements", Toast.LENGTH_LONG).show();
        int num = 0;

        String displayAllElements = "";

        if (c.moveToFirst()) {
            do {
                displayAllElements += DisplayElements(c);
                num++;

            } while (c.moveToNext());
            resultsView.setText(displayAllElements);
        } else {
            Toast.makeText(this, "none here", Toast.LENGTH_LONG).show();
        }

        //toast
        Toast.makeText(this, "there are " + num + " elements.", Toast.LENGTH_LONG).show();
        db.close();
    }



    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException
    {
        //----copy 1k byte at a time
        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer))>0)
        {
            outputStream.write(buffer, 0, length );
        }

        inputStream.close();
        outputStream.close();
    }

    public String DisplayElements(Cursor c)
    {

        String display = "atomic number = " + c.getString(0) + "\n" +
                "atomic weight = " + c.getString(1) + "\n" +
                "name = " + c.getString(2) + "\n" +
                "symbol = " + c.getString(3) + "\n" +
                "melting point = " + c.getString(4) + "\n" +
                "boiling point = " + c.getString(5) + "\n" +
                "density = " + c.getString(6) + "\n" +
                "phase = " + c.getString(7) + "\n" ;

       // Toast.makeText(this, "atomic number = " + c.getString(0) + "\n" +
             //   "atomic weight = " + c.getString(1) + "\n" +
             //   "name = " + c.getString(2) + "\n" +
               // "symbol = " + c.getString(3) + "\n" , Toast.LENGTH_SHORT).show();


        return display;

    }

    public void lookupElement(View v)
    {

        Toast.makeText(this, "Looking up Element", Toast.LENGTH_LONG).show();

        DBAdapter dbHandler = new DBAdapter(this);





        Element element = dbHandler.findElement(nameBoxFind.getText().toString());

        if(element!=null)
        {
            String results = "Atomic Number: " + String.valueOf(element.get_atomic_number()) + "\n"
                    + "Symbol: " + String.valueOf(element.get_symbol())+ "\n"
                    + "Name: " + String.valueOf(element.get_name()) + "\n"
                    + "Atomic Weight: " + String.valueOf(element.get_atomic_weight());


            resultsView.setText(results);

            atomicNumberBox.setText(String.valueOf(element.get_atomic_number()));
        }
        else
        {
            resultsView.setText("No Match Found");
        }

    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
