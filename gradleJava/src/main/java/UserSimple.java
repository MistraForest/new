import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSimple {

   @SerializedName(value = "fullName", alternate = "name")
   @Expose()
    private  String name;

    @Expose(serialize = true, deserialize = false)
    private String email;

    @Expose(serialize = false, deserialize = true)
    private int age;

    @Expose(serialize = false, deserialize = false)
    private boolean isDevelopper;

    public UserSimple(String name, String email, int age, boolean isDevelopper) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.isDevelopper = isDevelopper;
    }


}
