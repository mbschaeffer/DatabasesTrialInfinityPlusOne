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
    TextView nameBox;
    TextView atomicNumberBox;
    TextView symbolBox;
    TextView atomicWeightBox;
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
        nameBox = (TextView) findViewById(R.id.txtName);
        atomicNumberBox = (TextView) findViewById(R.id.txtAtomicNumber);
        atomicWeightBox = (TextView) findViewById(R.id.txtAtomicWeight);
        symbolBox = (TextView) findViewById(R.id.txtSymbol);
        elementSearchV = (SearchView) findViewById(R.id.searchView);

        nameList = new ArrayList<String>();

        for(int i = 0; i<20 ; i++)
        {
            nameList.add("Element" + i);
        }

        myList = (ListView) findViewById(R.id.list);

        defaultAdapter = new MyCustomAdapter(MainActivity.this,nameList);

  /*      myList.setAdapter(defaultAdapter);


        elementSearchV.setIconifiedByDefault(false);

        elementSearchV.setOnQueryTextListener(this);

        elementSearchV.setOnCloseListener(this);
*/









        DBAdapter db = new DBAdapter(this);

        try{
            String destPath = "/data/data/" + getPackageName() + "/databases/";

            File f = new File(destPath);

            if(!f.exists()){

                Toast.makeText(this, "File does not exist" , Toast.LENGTH_LONG).show();



                f.mkdirs();
                f.createNewFile();


                CopyDB(getBaseContext().getAssets().open("myPeriodicTableDB"), new FileOutputStream(destPath + "/myPeriodicTableDB"));

            }
            Toast.makeText(this, "The file exists!" , Toast.LENGTH_LONG).show();

        }catch (FileNotFoundException e) {
            Toast.makeText(this, "File not found" , Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }catch (IOException e){

            Toast.makeText(this, "IOException issue" , Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }


        db.open();
        Cursor c = db.getAllElements();
        Toast.makeText(this, "got the elements" , Toast.LENGTH_LONG).show();
        int num = 0;

        if(c.moveToFirst())
        {
            do{
                DisplayElement(c);
                num++;

            } while (c.moveToNext());
        }
        else
        {
            Toast.makeText(this, "none here" , Toast.LENGTH_LONG).show();
        }

        Toast.makeText(this, "there are " + num + " elements." , Toast.LENGTH_LONG).show();
        db.close();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    public void DisplayElement(Cursor c)
    {


        Toast.makeText(this, "atomic number = " + c.getString(0) + "\n" +
                "atomic weight = " + c.getString(1) + "\n" +
                "name = " + c.getString(2) + "\n" +
                "symbol = " + c.getString(3) + "\n" , Toast.LENGTH_SHORT).show();

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
