package project.yen.yichun.todoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<String> items = new ArrayList<>();
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    private EditText editText;
    private String fileName = "todo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItems();
        lvItems = (ListView) findViewById(R.id.activity_main_list_view);
        editText = (EditText) findViewById(R.id.activity_main_et);
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        initListener();
    }

    private void initListener() {
        lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Tell the user the item was removed from the list permanently.
                showToast('"' + items.get(position) + '"'
                        + " " + getString(R.string.activity_main_msg_remove_item));
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }


    /**
     * Click the add item button to add the new task to local.
     *
     * @param view
     */
    public void onAddItem(View view) {
        // Check the input value.
        String strItem = editText.getText().toString().trim();
        if (strItem.length() > 0) {
            itemsAdapter.add(strItem);
            editText.setText("");
            lvItems.smoothScrollToPosition(items.size() - 1);
            writeItems();
        }
    }

    private void readItems() {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, fileName);
        try {
            items = new ArrayList<>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, fileName);
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
