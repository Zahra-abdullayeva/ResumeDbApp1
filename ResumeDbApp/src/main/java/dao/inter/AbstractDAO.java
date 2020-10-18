
package dao.inter;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class AbstractDAO {
     public Connection connect() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");// bu bir driverdir, yeniki bir classdir
        // bu class default olaraq bizim projectimizde yoxdu, dependenciye qoshacayiq
        String url= "jdbc:mysql://localhost:3306/resume";//bazanin adini mutleq qeyd et
        String username= "root";
        String password= "120795";
        Connection c= DriverManager.getConnection(url,username,password);
        return c;
    }
}
