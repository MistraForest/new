import com.google.gson.annotations.Expose;

public class UserNested {

    @Expose()
    private  String name;

    @Expose(serialize = true, deserialize = false)
    private String email;

    @Expose(serialize = false, deserialize = true)
    private int age;

    @Expose(serialize = false, deserialize = false)
    private boolean isDevelopper;

    private UserAdresse userAdresse;

    public UserNested(String name, String email, int age, boolean isDevelopper, UserAdresse userAdresse) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.isDevelopper = isDevelopper;
        this.userAdresse = userAdresse;
    }
}
