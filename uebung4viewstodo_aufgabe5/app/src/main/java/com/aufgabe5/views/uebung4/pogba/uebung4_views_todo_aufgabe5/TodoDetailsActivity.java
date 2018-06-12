package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.TodoDetailAccessor;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.Todo_Simple;

public class TodoDetailsActivity extends AppCompatActivity {

    private TodoDetailAccessor accessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_details);

        final EditText todoName = findViewById(R.id.item_name);
        final EditText itemDescription = findViewById(R.id.item_description);
       // final Switch isdone = findViewById(R.id.status_id);
        Button saveButton = findViewById(R.id.saveButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        accessor = new TodoDetailAccessor() {
            private Todo_Simple todoData;

            @Override
            public Todo_Simple readItem() {
                if (todoData == null){
                    this.todoData = (Todo_Simple) getIntent()
                            .getSerializableExtra(TodoListActivity.ITEM_OBJECT);
                }
                return this.todoData;
            }

            @Override
            public void writeItem() {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(TodoListActivity.ITEM_OBJECT
                        , this.todoData);

                setResult(TodoListActivity.RESPONSE_ITEM_EDITED
                        , returnIntent);
            }

            @Override
            public boolean hasItem() {
                return readItem() != null;
            }

            @Override
            public void createItem() {
                this.todoData = new Todo_Simple(-1,"Volleyball"
                        ,"on the Beach", false
                        ,false,288288L);
            }

            @Override
            public void deleteItem() {
                // and return to the calling activity
                Intent returnIntent = new Intent();

                // set the item
                returnIntent.putExtra(TodoListActivity.ITEM_OBJECT, this.todoData);

                // set the result code
                setResult(TodoListActivity.RESPONSE_ITEM_DELETED,
                        returnIntent);
            }
        };

        // if we do not have an item, we assume we need to create a new one
        if (accessor.hasItem()) {
            // set name and description
            todoName.setText(accessor.readItem().getName());
            itemDescription.setText(accessor.readItem().getDescription());
           // isdone.setChecked(accessor.readItem().getIsDone());
        } else {
            accessor.createItem();
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processItemSave(accessor, todoName,itemDescription);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processItemDelete(accessor);
            }
        });
    }
    protected void processItemSave(TodoDetailAccessor accessor, EditText name,
                                   EditText description) {
        // re-set the fields of the item
        accessor.readItem().setName(name.getText().toString());
        accessor.readItem().setDescription(description.getText().toString());
       // accessor.readItem().setIsDone(isDone.isChecked());

        // save the item
        accessor.writeItem();

        // and finish
        finish();
    }
    protected void processItemDelete(TodoDetailAccessor accessor) {
        // delete the item
        accessor.deleteItem();

        // and finish
        finish();
    }
}
