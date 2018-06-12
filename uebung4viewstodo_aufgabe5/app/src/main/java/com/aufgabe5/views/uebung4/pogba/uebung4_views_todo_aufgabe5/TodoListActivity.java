package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.TodoListAccessor;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.Todo_Simple;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.util.DBHandler;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.util.MyCustomAdapter;

import java.util.*;

public class TodoListActivity extends AppCompatActivity {
    /**
     * the argname with which we will pass the item to the subview
     */
    public static final String ITEM_OBJECT = "itemObject";

    /**
     * the result code that indicates that some item was changed
     */
    public static final int RESPONSE_ITEM_EDITED = 1;

    /**
     * the result code that indicates that the item shall be deleted
     */
    public static final int RESPONSE_ITEM_DELETED = 2;

    /**
     * the constant for the subview request
     */
    public static final int REQUEST_ITEM_DETAILS = 2;

    /**
     * the constant for the new item request
     */
    public static final int REQUEST_ITEM_CREATION = 1;

    private  static final String logger = TodoListActivity.class.getSimpleName();

    private TodoListAccessor listAccessor;

    private List<Todo_Simple> itemList = new ArrayList<>();

    // should usually reside in the activity#
    // we have it here for abstraction reasons, since the activity should be able to swap different IDataItemListAccessor
    // implementations
    private static final int LOADER_ID = 1;

    private SimpleCursorAdapter adapter;

    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    private DBHandler dbHandler;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listView = findViewById(R.id.list);
        Button newitemButton = findViewById(R.id.add_new_todo);

        // Todo aus statischen quelle: strings.xml
        String[] names = getResources().getStringArray(R.array.names);
        String[] description = getResources().getStringArray(R.array.descriptions);
        int[] date = getResources().getIntArray(R.array.date);

        for (int i = 0 ; i < names.length; i++){
            Todo_Simple todoSimple = new Todo_Simple(-1,names[i]
                    ,description[i],false,false,date[i]);
            getItems().add(todoSimple);
        }





        listAccessor = new TodoListAccessor() {

            private ArrayAdapter<Todo_Simple> arrayAdapter ;

            @Override
            public void addItem(Todo_Simple item) {

                dbHandler.addTodoItemToDb(item);
                this.arrayAdapter.add(item);
                sortTodoListByDate();
                Log.i(logger, "TodoListAccessor.addItem(): " + item+"  is added");
            }


            @Override
            public ListAdapter getAdapter() {

                Log.i(logger,"TodoListAccessor.getAdapter()...");

                dbHandler = new DBHandler(TodoListActivity.this);
                dbHandler.initDatabase();

               readAllDBItems();

                //this.arrayAdapter = DataListView.createItemFromAdapter(TodoListActivity.this, itemList);
                this.arrayAdapter = MyCustomAdapter.createDataFromAdapter(TodoListActivity.this,R.layout.custom_layout,itemList);

                this.arrayAdapter.setNotifyOnChange(true);

                //return adapter;
                return this.arrayAdapter;
            }

            private void readAllDBItems() {
                Cursor cursor = dbHandler.getCursor();

                Log.i(logger,"getAdapter(): got a Cursor: "+ cursor);

                cursor.moveToFirst();

                while (!cursor.isAfterLast()){
                    itemList.add(dbHandler.createTodoFromCursor(cursor));
                    cursor.moveToNext();
                }
                Log.i(logger, "readAllDBItems(): read out items: " + itemList);

            }

            @Override
            public void updateItem(Todo_Simple item) {
                Log.i(logger, "TodoListAccessor.updateItem():...");

                dbHandler.updateItemInDb(item);

                findItemFromList(item).updateFrom(item);
                sortTodoListByDate();

                // cursor adapter
               // getLoaderManager().restartLoader(LOADER_ID, null, myCallBacks);
                this.arrayAdapter.notifyDataSetChanged();

                Log.i(logger, "TodoListAccessor.updateItem(): " + item+"  is updated");
            }

            @Override
            public void deleteItem(final Todo_Simple item) {
                Log.i(logger, "TodoListAccessor.deleteItem():...");

                dbHandler.removeItemFromDb(item);

                sortTodoListByDate();

                // cursor adapter
               // getLoaderManager().restartLoader(LOADER_ID, null, myCallBacks);

                this.arrayAdapter.remove(findItemFromList(item));
                Log.i(logger, "TodoListAccessor.deleteItem(): "+item+"  is deleted");
            }



            @Override
            public Todo_Simple getSelectedItem(int itemPosition, long itemId) {
                Log.i(logger,"TodoListAccessor.getSelectedItem(): " + this.arrayAdapter.getItem(itemPosition));
                //return dbHandler.createTodoFromCursor((Cursor)
                //adapter.getItem(itemPosition));
                return this.arrayAdapter.getItem(itemPosition);
            }



            @Override
            public void close() {
                dbHandler.close();
            }



            private Todo_Simple findItemFromList(Todo_Simple item) {
                Log.i(logger,"TodoListAccessor.findItemFromList()...");
                Todo_Simple todoSimple= new Todo_Simple();
                for (Todo_Simple currentItem : itemList) {
                    if (currentItem.getId() == item.getId()){//currentItem.getId() == item.getId()) {
                        todoSimple=currentItem;
                    }
                }
                return todoSimple;
            }
        };




          /*  class MyLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

                @Override
                public Loader<Cursor> onCreateLoader(int aId, Bundle aArgs) {
                    return new CursorLoaderIMPL(TodoListActivity.this, dbHandler);
                }

                @Override
                public void onLoadFinished(Loader<Cursor> aLoader, Cursor aCursor) {
                    if (aLoader.getId() == LOADER_ID) {
                        adapter.swapCursor(aCursor);
                    }
                }

                @Override
                public void onLoaderReset(Loader<Cursor> aArg0) {
                    adapter.swapCursor(null);
                }

            }

        listAccessor = new TodoListAccessor() {
            @Override
            public void addItem(Todo_Simple item) {

                dbHandler.addTodoItemToDb(item);
                TodoListActivity.this.getLoaderManager().restartLoader(LOADER_ID, null, mCallbacks);

            }

            @Override
            public ListAdapter getAdapter() {

                dbHandler = new DBHandler(TodoListActivity.this);
                dbHandler.initDatabase();

                LoaderManager loaderManager = TodoListActivity.this.getLoaderManager();
                mCallbacks = new MyLoaderCallbacks();
                loaderManager.initLoader(LOADER_ID, null, mCallbacks);

                *//*
                 * create a cursor adapter that maps the "name" column in the db to the
                 * itemName element in the view
                 *
                 * (i.e. using this adapter there is no need to create DataItem objects
                 * for all items that are contained in the db)
                 *//*
                adapter = new SimpleCursorAdapter(TodoListActivity.this, R.layout.custom_layout,
                        null,
                        new String[]{dbHandler.COL_NAME},
                        new int[]{R.id.todo_name}, 0);

                return adapter;
            }

            @Override
            public void updateItem(Todo_Simple item) {
                dbHandler.updateItemInDb(item);
                TodoListActivity.this.getLoaderManager().restartLoader(LOADER_ID, null, mCallbacks);
            }

            @Override
            public void deleteItem(Todo_Simple item) {
                dbHandler.removeItemFromDb(item);
                TodoListActivity.this.getLoaderManager().restartLoader(LOADER_ID, null, mCallbacks);
            }

            @Override
            public Todo_Simple getSelectedItem(int itemPosition, long itemId) {
                return dbHandler.createTodoFromCursor((Cursor) adapter.getItem(itemPosition));
            }

            @Override
            public void close() {
                dbHandler.close();
            }
        };*/
        final ListAdapter adapter = listAccessor.getAdapter();

       /* List<Todo_Simple> list = new ArrayList<>();

        Todo_Simple item = new Todo_Simple();
        item.setName("Football");
        item.setDescription("Mannschaftsport mit 11 Spieler pro Team");
        item.setIsDone(false);
        item.setIsFavourite(true);
        item.setDueDate(12255L);
        //listAccessor.addItem(item);

        Todo_Simple item1 = new Todo_Simple();
        item1.setName("Handball");
        item1.setDescription("Mannschaftsport mit 7 Spieler pro Team");
        item1.setIsDone(false);
        item1.setIsFavourite(false);
        item1.setDueDate(12255L);
        //listAccessor.addItem(item1);



       // String[] todos = getResources().getStringArray(R.array.todos);
//        CustomAdapter customAdapter = new CustomAdapter(this, list);
        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.custom_layout, list);

       // list = Arrays.asList(todos);
        customAdapter.add(new Todo_Simple(1,"Name1","some desc", true,false,12566666L));
        customAdapter.add(new Todo_Simple(2,"Name2","some desc", true,false,12566666L));
        customAdapter.add(new Todo_Simple(3,"Name3","some desc", true,false,12566666L));
        customAdapter.add(new Todo_Simple(4,"Name4","some desc", true,false,12566666L));
        customAdapter.add(item);
        customAdapter.add(item1);
*/
        //Log.i(logger, "List "+ list.get(0));
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,todos);

        //listView.setAdapter(customAdapter);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {

                Todo_Simple todoSimple = listAccessor.getSelectedItem(position, id);
                Log.i(logger, "onItemClick: Position is " + position
                        + " id ist " + id );
                processItemSelection(todoSimple);
            }
        });

        listView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        newitemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processNewItemRequest();
            }
        });
    }
    private void sortTodoListByStatus() {
        Collections.sort(itemList, new Comparator<Todo_Simple>() {
            @Override
            public int compare(Todo_Simple o1, Todo_Simple o2) {
                return Boolean.compare(o1.getIsDone(), o2.getIsDone());
            }
        });
    }

    private void sortTodoListByDate() {
        Log.i(logger, "sortTodoListByDate()....");
        Collections.sort(itemList, new Comparator<Todo_Simple>() {
            @Override
            public int compare(Todo_Simple o1, Todo_Simple o2) {
                return Long.compare(o1.getDueDate(), o2.getDueDate());
            }
        });
    }

    /**
     * get the items
     */
    protected List<Todo_Simple> getItems() {
        sortTodoListByDate();
        return itemList;
    }



    private void processNewItemRequest() {
        Log.i(logger, "processNewItemRequest(): ");

                Intent intent = new Intent(TodoListActivity.this
                        , TodoDetailsActivity.class);
        // start the details activity with the intent
        startActivityForResult(intent, REQUEST_ITEM_CREATION);
    }

    private void processItemSelection(Todo_Simple todoSimple) {
        Log.i(logger, "processItemSelection() on item: " + todoSimple);
        // create an intent for opening the details view
        Intent intent = new Intent(TodoListActivity.this
                                    ,TodoDetailsActivity.class);
        // pass the item to the intent
        intent.putExtra(ITEM_OBJECT, todoSimple);

        // start the details activity with the intent
        startActivityForResult(intent, REQUEST_ITEM_DETAILS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(logger, "onActivityResult(): " + data);

        Todo_Simple todoSimple = data != null ? (Todo_Simple)data
                .getSerializableExtra(ITEM_OBJECT) : null;

        if (requestCode == REQUEST_ITEM_DETAILS){
            if (resultCode == RESPONSE_ITEM_EDITED){
                Log.i(logger, "onActivityResult(): updating the edited item");
                this.listAccessor.updateItem(todoSimple);
            }else if (resultCode == RESPONSE_ITEM_DELETED){
                this.listAccessor.deleteItem(todoSimple);
                Log.i(logger, "onActivityResult(): deleting the item: "+todoSimple);
            }
        }else if (requestCode == REQUEST_ITEM_CREATION && resultCode == RESPONSE_ITEM_EDITED){
            this.listAccessor.addItem(todoSimple);
            Log.i(logger, "onActivityResult(): adding the created item");
        }
    }

    class  CustomAdapter extends ArrayAdapter<Todo_Simple>{

        private List<Todo_Simple> todos;
        private Map<Integer, Boolean> checkStates = new HashMap<>();
        private int resource;
        private Context context;


        public CustomAdapter(final Context context, int resource, List<Todo_Simple> todos) {
            super(context, resource, todos);
            this.context = context;
            this.resource = resource;
            this.todos = todos;
        }
        private class ViewHolder {
            ImageView imageView;
            CheckBox checkBox;
            TextView textView;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            Todo_Simple todo = todos.get(position);
            ViewHolder holder;
            Log.i(logger, "getView() "+ convertView+ " onPosition " +position+ " TODO: "+todo);
            View view = convertView;
            if (view == null){
                holder = new ViewHolder();
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(resource, parent, false);

                holder.imageView = view.findViewById(R.id.todo_image);
                // Log.i(logger, "ImageView: "+imageView);
                holder.checkBox = view.findViewById(R.id.check_it);
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        int pos = (int) buttonView.getTag();
                        checkStates.put(pos, isChecked);
                    }
                });
                //  Log.i(logger, "checkBox: "+checkBox);
                holder.textView = view.findViewById(R.id.todo_name);
                //  Log.i(logger, "textView: "+textView);
                view.setTag(holder);
                Log.i(logger, "getView:View ist "+view);
            } else{
                holder = (ViewHolder)view.getTag();

            }

            holder.imageView.setImageResource(R.mipmap.star_grey);
            //holder.checkBox.setActivated(true);
            holder.textView.setText(todo.getName());
            holder.checkBox.setTag(position);
            holder.checkBox.setChecked(checkStates.containsKey(position) ? checkStates.get(position) : false);


            return view;
        }
    }

  /*  private  class  CustomAdapter extends ArrayAdapter<String>{

        private List<String> todos;
        private Map<Integer, Boolean> checkStates = new HashMap<>();
        private int resource;
        private Context context;


        public CustomAdapter(final Context context, int resource, List<String> todos) {
            super(context, resource, todos);
            this.context = context;
            this.resource = resource;
            this.todos = todos;
        }
        private class ViewHolder {
            ImageView imageView;
            CheckBox checkBox;
            TextView textView;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            String todo = todos.get(position);
            ViewHolder holder;
            Log.i(logger, "getView() "+ convertView+ " onPosition " +position+ " TODO: "+todo);
           View view = convertView;
            if (view == null){
                holder = new ViewHolder();
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(resource, parent, false);

                holder.imageView = view.findViewById(R.id.todo_image);
               // Log.i(logger, "ImageView: "+imageView);
                holder.checkBox = view.findViewById(R.id.check_it);
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        int pos = (int) buttonView.getTag();
                        checkStates.put(pos, isChecked);
                    }
                });
              //  Log.i(logger, "checkBox: "+checkBox);
                holder.textView = view.findViewById(R.id.todo_name);
              //  Log.i(logger, "textView: "+textView);
                view.setTag(holder);
                Log.i(logger, "getView:View ist "+view);
            } else{
                holder = (ViewHolder)view.getTag();

            }

            holder.imageView.setImageResource(R.mipmap.star_grey);
            //holder.checkBox.setActivated(true);
            holder.textView.setText(todo);
            holder.checkBox.setTag(position);
            holder.checkBox.setChecked(checkStates.containsKey(position) ? checkStates.get(position) : false);


            return view;
        }
    }

  /*  private  class  CustomAdapter extends ArrayAdapter<String>{


        public CustomAdapter(final Context context, List<String> todos ) {
            super(context, R.layout.custom_layout, todos);
        }
        private class ViewHolder {
            ImageView imageView;
            CheckBox checkBox;
            TextView textView;;
        }

        @Override
        public View getView(int position,  View convertView, ViewGroup parent) {

            String todo = getItem(position);
            final ViewHolder holder;
            Log.i(logger, "getView() "+ convertView+ " onPosition " +position+ " TODO: "+todo);
            if (convertView == null){
                holder = new ViewHolder();
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                convertView = layoutInflater.inflate(R.layout.custom_layout, parent, false);

                holder.imageView = convertView.findViewById(R.id.todo_image);
                // Log.i(logger, "ImageView: "+imageView);
                holder.checkBox = convertView.findViewById(R.id.check_it);
                //  Log.i(logger, "checkBox: "+checkBox);
                holder.textView = convertView.findViewById(R.id.todo_name);
                //  Log.i(logger, "textView: "+textView);
                convertView.setTag(holder);
                holder.textView.setText(todo);
                holder.imageView.setImageResource(R.mipmap.cailloux);
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            holder.imageView.setImageResource(R.mipmap.star_yellow);
                        }else holder.imageView.setImageResource(R.mipmap.star_grey);
                    }
                });
                Log.i(logger, "getView:View ist "+convertView);
            } else{
                holder = (ViewHolder)convertView.getTag();
                holder.textView.setText(todo);
                holder.imageView.setImageResource(R.mipmap.cailloux);
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            holder.imageView.setImageResource(R.mipmap.star_yellow);
                        }else holder.imageView.setImageResource(R.mipmap.star_grey);
                    }
                });
            }



            return convertView;
        }
    }*/
    /**
     * if we stop, we signal this to the accessor (which is necessary in order to avoid trouble when operating on dbs)
     */
    /*@Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(logger,"onDestroy(): will signal finalisation to the accessors");
        this.listAccessor.close();

        super.onStop();
    }*/


}
