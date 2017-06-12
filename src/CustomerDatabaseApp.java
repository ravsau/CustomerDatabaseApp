
public class CustomerDatabaseApp {


    public static void main(String[] args) throws Exception {
    	
    	// notice how the main method only calls the class
    	// and the class does all the work. This is a good design.
        MySQLHelper db = new MySQLHelper();
        db.readDataBase();
    }

}


