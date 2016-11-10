package project.yen.yichun.todoapp;

import android.content.Intent;
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
    /* Activity */
    public static final String TASK_NAME = "taskName";
    private final String TAG = MainActivity.class.getSimpleName();
    private final int ACTIVITY_RESULT = 0;
    /* Params */
    private ArrayList<String> items = new ArrayList<>();
    private ArrayAdapter<String> itemsAdapter;
    private String fileName = "todo.txt";
    private int editedPosition = 0;
    /* UI */
    private ListView lvItems;
    private EditText editText;

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

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                editedPosition = position;
                launchEditItemActivity(itemsAdapter.getItem(position));
            }
        });
    }

    private void launchEditItemActivity(String taskName) {
        Intent intent = new Intent();
        intent.putExtra(TASK_NAME, taskName);
        intent.setClass(this, EditItemActivity.class);
        startActivityForResult(intent, ACTIVITY_RESULT);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_RESULT) {
            if (resultCode == RESULT_OK) {
                String strChanges = data.getStringExtra(TASK_NAME);
                if (strChanges != null && strChanges.length() > 0) {
                    items.set(editedPosition, strChanges);
                    itemsAdapter.notifyDataSetChanged();
                    showToast('"' + items.get(editedPosition) + '"'
                            + " " + getString(R.string.activity_main_msg_edit_item));
                    writeItems();
                }
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
