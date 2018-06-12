import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {

    public static void main(String[] args){

        serializeUserSimple();
        deserializeUserSimple();
    }

    private static void serializeUserSimple() {

        UserSimple user = new UserSimple("Forest", "me@forest.com", 31, true);

        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        builder.serializeNulls();

        Gson gson = builder.create();

        String json = gson.toJson(user);

       // UserAdresse adresse = new UserAdresse("Kurfürstenstrasse","148B", "Berlin","Germany");

       // UserNested userNested = new UserNested("Forest", "me@forest.com", 31, true, adresse);

        //String jason = new Gson().toJson(userNested);

    }


    private static void deserializeUserSimple() {

        String userJson = "{'age' : 31,'email':'me@forest.com','isDevelopper':true,'name':'Forest'}";

        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        UserSimple userSimple = gson.fromJson(userJson, UserSimple.class);

       // String restaurantJson = "{'name': 'Future Studio Steak House','owner':{'name':'Forest', 'addresse': {'street': 'Kurfürstenstrasse','houseNumber': '148B','city': 'Berlin','country': 'Germany'} },'cook': {'age': 24,'name': 'Solo','salary': 1500},'waiter': {'age': 26,'name': 'Fabian','salary': 1200 }}";

      //  Restaurant restaurant = new Gson().fromJson(restaurantJson, Restaurant.class);
    }

}
