public class Restaurant {

    String name;

    RestaurantOwner owner;
    RestaurantStaff cook;
    RestaurantStaff waiter;

    private class RestaurantOwner {
        String name;
        UserAdresse addresse;
    }

    private class RestaurantStaff {
        int age;
        int salary;
        String name;
    }
}
