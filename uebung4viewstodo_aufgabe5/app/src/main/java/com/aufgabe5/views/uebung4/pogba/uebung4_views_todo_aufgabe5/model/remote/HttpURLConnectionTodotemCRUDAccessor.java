package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.remote;

import android.util.Log;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.Todo_Simple;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpURLConnectionTodotemCRUDAccessor implements TodoItemCRUDAccessor {


    protected  static String logger = HttpURLConnectionTodotemCRUDAccessor.class
            .getSimpleName();

    private String baseUrl;

    private ObjectMapper mObjectMapper = new ObjectMapper();

    public HttpURLConnectionTodotemCRUDAccessor(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    @Override
    public List<Todo_Simple> readAllItems() {

        Log.i(logger, "readAllItems()");

        try {

            // obtain a http url connection from the base url
            HttpURLConnection con = (HttpURLConnection) (new URL(baseUrl))
                    .openConnection();
            Log.d(logger, "readAllItems(): got connection: " + con);

            // set the request method (GET is default anyway...)
            con.setRequestMethod("GET");

            // then initiate sending the request...
            InputStream is = con.getInputStream();

            // check the response code
            if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
                final  List<Todo_Simple> items = mObjectMapper.readValue(is,
                        new TypeReference<List<Todo_Simple>>(){} );
                Log.i(logger, "readAllItems(): " + items);

                return items;
            }else {
                Log.e(logger,
                        "readAllItems(): got response code: "
                                + con.getResponseCode());
            }

        }catch (Exception e){
            Log.e(logger, "readAllItems(): got exception: " + e);
        }
        return new ArrayList<>();
    }

    @Override
    public Todo_Simple readItem(long id) {
        return null;
    }

    @Override
    public Todo_Simple createItem(Todo_Simple item) {
        Log.i(logger, "createItem(): " + item);

        try {
            // obtain a http url connection from the base url
            HttpURLConnection con = (HttpURLConnection) (new URL(baseUrl))
                    .openConnection();
            Log.d(logger, "createItem(): got connection: " + con);

            // set the request method
            con.setRequestMethod("POST");

            // indicate that we want to send a request body
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            // obtain the output stream and write the item as json object to it
            OutputStream os = con.getOutputStream();
            os.write(mObjectMapper.writeValueAsString(item).getBytes());

            // then initiate sending the request...
            InputStream is = con.getInputStream();

            // check the response code
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Todo_Simple ret = mObjectMapper.readValue(is, Todo_Simple.class);
                Log.i(logger, "createItem(): " + ret);

                return ret;
            } else {
                Log.e(logger,
                        "createItem(): got response code: "
                                + con.getResponseCode());
            }
        } catch (Exception e) {
            Log.e(logger, "createItem(): got exception: " + e);
        }

        return null;
    }

    @Override
    public boolean deleteItem(long itemId) {

        Log.i(logger, "deleteItem(): " + itemId);

        try {
            // obtain a http url connection from the base url
            HttpURLConnection con = (HttpURLConnection) (new URL(baseUrl + "/"
                    + itemId)).openConnection();
            Log.d(logger, "deleteItem(): got connection: " + con);

            // set the request method
            con.setRequestMethod("DELETE");
            con.setRequestProperty("Content-Type", "application/json");

            // then initiate sending the request...
            InputStream is = con.getInputStream();

            // check the response code
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {

                return mObjectMapper.readValue(is, Boolean.class);
            } else {
                Log.e(logger,
                        "deleteItem(): got response code: "
                                + con.getResponseCode());
            }
        } catch (Exception e) {
            Log.e(logger, "deleteItem(): got exception: " + e);
        }

        return false;
    }

    @Override
    public Todo_Simple updateItem(Todo_Simple item) {

        Log.i(logger, "updateItem(): " + item);

        try {
            // obtain a http url connection from the base url
            HttpURLConnection con = (HttpURLConnection) (new URL(baseUrl))
                    .openConnection();
            Log.d(logger, "updateItem(): got connection: " + con);
            // set the request method
            con.setRequestMethod("PUT");
            // indicate that we want to send a request body
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            // obtain the output stream and write the item as json object to it
            OutputStream os = con.getOutputStream();
            os.write(mObjectMapper.writeValueAsString(item).getBytes());
            // then initiate sending the request...
            InputStream is = con.getInputStream();
            // check the response code
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                final Todo_Simple ret = mObjectMapper.readValue(is, Todo_Simple.class);

                Log.i(logger, "updateItem(): " + ret);

                return ret;
            } else {
                Log.e(logger,
                        "updateItem(): got response code: "
                                + con.getResponseCode());
            }
        } catch (Exception e) {
            Log.e(logger, "updateItem(): got exception: " + e);
        }

        return null;
    }
}
