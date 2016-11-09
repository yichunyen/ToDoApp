package project.yen.yichun.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        initViews();
    }

    private void initViews() {
        editText = (EditText) findViewById(R.id.activity_edit_item_et);
        if (getIntent().getStringExtra(MainActivity.TASK_NAME) != null) {
            editText.setText(getIntent().getStringExtra(MainActivity.TASK_NAME));
        }
    }

    /**
     * Save the edit text changes and finish the EditItemActivity.
     *
     * @param view
     */
    public void onSave(View view) {
        if (editText.getText().toString().trim().length() > 0) {
            saveChanges();
        }
    }

    private void saveChanges() {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.TASK_NAME, editText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
