import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class GsonTest {

    public static void main(String[] args){

        String jsonString = "{\"name\": \"Forest\", \"age\": 21}";

        /**
         *Step 1: Create Gson object using GsonBuilder
         */
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        /**
         *	Step 2: Deserialize JSON to Object
         */
		/*Use fromJson() method to get the Object from the JSON.
		Pass Json string / source of Json string and object type as parameter.*/
        Student student = gson.fromJson(jsonString, Student.class);
        System.out.println(student);

        /**
         *	Step 3: Serialize Object to JSON
         */

        //Use toJson() method to get the JSON string representation of an object.
        jsonString = gson.toJson(student);
        System.out.println(jsonString);

    }

    public class Student {

        private String name;
        private int age;

        public Student(){
        }

        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return name;
        }

        public void setAge(int age){
            this.age = age;
        }
        public int getAge(){
            return age;
        }

        public String toString(){
            return "Student [ name: "+getName()+" , Age: "+getAge()+" ]";
        }

    }
}
