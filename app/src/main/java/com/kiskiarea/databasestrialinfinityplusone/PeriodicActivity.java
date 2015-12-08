package com.kiskiarea.databasestrialinfinityplusone;

/**
 * Created by madison.troup on 12/8/2015.
 */
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

public class PeriodicActivity  extends AppCompatActivity implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener
{
    TextView resultsView;
    EditText nameBoxFind;
    SearchView elementSearchV;

    /*---Used for searchable list view */
    private ListView myList;
    private ArrayList<String> nameList;
    MyCustomAdapter defaultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.periodic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        resultsView = (TextView) findViewById(R.id.txtResults);
        nameBoxFind = (EditText) findViewById(R.id.txtFindElement);
        elementSearchV = (SearchView) findViewById(R.id.searchView);

        Log.w("MainActivity", "I GOT HERE");

        DBAdapter db = new DBAdapter(this);

        try {
            String destPath = "/data/data/" + getPackageName() + "/databases/";

            File f = new File(destPath);

            if (!f.exists()) {

                Log.w("MainActivity", "-----The file does not exists!");
                Toast.makeText(this, "File does not exist", Toast.LENGTH_LONG).show();

                f.mkdirs();

                f.createNewFile();

                CopyDB(getBaseContext().getAssets().open("MyPeriodicDB2"),
                        new FileOutputStream(destPath + "/MyPeriodicDB2"));
            }

            Toast.makeText(this, "The file exists!", Toast.LENGTH_LONG).show();

            Log.w("MainActivity", "-----The file exists!");

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File not found", Toast.LENGTH_LONG).show();
            Log.w("MainActivity", "-----File Not Found!");

            e.printStackTrace();
        } catch (IOException e) {

            Toast.makeText(this, "IOException issue", Toast.LENGTH_LONG).show();

            Log.w("MainActivity", "-----IOException issue");

            e.printStackTrace();
        }
        Log.w("MainActivity", "Trying to open the database");

        db.open();

        Log.w("MainActivity", "-----The database is open!");

        Cursor c = db.getAllElements();

        Log.w("MainActivity", "I GOT HERE");

        Toast.makeText(this, "got the elements", Toast.LENGTH_LONG).show();
        int num = 0;

        Log.w("MainActivity", "I GOT HERE");

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

        //Toast.makeText(this, "there are " + num + " elements.", Toast.LENGTH_LONG).show();
        db.close();
    }

    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
        //----copy 1k byte at a time
        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }

    public String DisplayElements(Cursor c) {
        String display = "name = " + c.getString(0) + "\n" +
                "number = " + c.getString(1) + "\n" +
                "mass = " + c.getString(2) + "\n" +
                "symbol = " + c.getString(3) + "\n" +
                "boiling point = " + c.getString(4) + "\n" +
                "melting point = " + c.getString(5) + "\n" +
                "density = " + c.getString(6) + "\n" +
                "phase = " + c.getString(7) + "\n";
        return display;
    }

    public void lookupElement(View v) {
        Toast.makeText(this, "Looking up Element", Toast.LENGTH_LONG).show();

        DBAdapter dbHandler = new DBAdapter(this);

        Element element = dbHandler.findElement(nameBoxFind.getText().toString());

        if (element != null) {
            String results = "Atomic Name: " + String.valueOf(element.get_name()) + "\n"
                    + "Atomic Number: " + String.valueOf(element.get_atomic_number()) + "\n"
                    + "Atomic Weight: " + String.valueOf(element.get_atomic_weight()) + "\n"
                    + "Atomic Symbol: " + String.valueOf(element.get_symbol()) + "\n"
                    + "Boiling Point: " + String.valueOf(element.get_boiling_point()) + "\n"
                    + "Melting Point: " + String.valueOf(element.get_melting_point()) + "\n"
                    + "Density: " + String.valueOf(element.get_density()) + "\n"
                    + "Phase: " + String.valueOf(element.get_phase());
            resultsView.setText(results);
        } else {
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

