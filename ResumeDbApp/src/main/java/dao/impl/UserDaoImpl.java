
package dao.impl;

import entity.Country;
import entity.User;
import dao.inter.AbstractDAO;
import dao.inter.UserDaoInter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl extends AbstractDAO implements UserDaoInter{

    private User getUser(ResultSet rs) throws Exception{
                int id= rs.getInt("id");
                String name= rs.getString("name");
                String surname= rs.getString("surname");
                String phone= rs.getString("phone");
                String email= rs.getString("email");
                String profileDesc=rs.getString("profileDescription");
                String adress=rs.getString("adress");
                int nationalityId=rs.getInt("nationality_id");
                int birthplaceId=rs.getInt("birthplace_id");
                String nationalityStr=rs.getString("nationality");
                String birthPlaceStr=rs.getString("birthplace");
                Date birthDate=rs.getDate("birthdate");
                
                Country nationality= new Country(nationalityId, null, nationalityStr);//burda bize nationality lazimdi deye country-sini yeniki doguldugu yeri null qoyuruq
                Country birthPlace= new Country(birthplaceId, birthPlaceStr, null);//burda ise eksine birthplace lazimdi deye nationality null edirik

               return new User(id, name, surname, phone,email,profileDesc,adress, birthDate, nationality, birthPlace); 
       
}
    
    @Override
    public List<User> getAll() {
        List<User> result= new ArrayList<>();
        try(Connection c= connect()) {//autoclose oldugu ucun try with resource
            
            Statement stmt= c.createStatement();//qayiq yaranir
            stmt.execute("SELECT" +
"	u.*," +
"	n.nationality," +
"	c.name AS birthplace " +
"FROM" +
"	USER u" +
"	Left JOIN country n ON u.nationality_id = n.id" +
"	Left JOIN country c ON u.birthplace_id = c.id");
            ResultSet rs=stmt.getResultSet();//bazadan netice qayidir onu ist etmek ucun menimsedirik rs-e
            while(rs.next()){//her setirde id name surname filan olur, onlari yazdiqca novbeti setre kecir. eger novbeti setir yoxdusa dayanir
                User u= getUser(rs);
                result.add(u); 
            } } catch (Exception ex) {
            ex.printStackTrace();
        }return result;
        }

    @Override
    public boolean updateUser(User u) {
        try(Connection c= connect()) {
            PreparedStatement stmt= c.prepareStatement("update user set name=?, surname=?,  phone=?,email=?, profileDescription=?,adress=?,birthdate=?,birthplace_id=? where id=?");//qayiq yaranir
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getPhone());
            stmt.setString(4, u.getEmail());
            stmt.setString(5, u.getProfileDesc());
            stmt.setString(6,u.getAdress());
            stmt.setDate(7, u.getBirthDate());
            stmt.setInt(8, u.getBirthPlace().getId());
            stmt.setInt(9, u.getId());
       
           return stmt.execute(); 
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }}

    @Override
    public boolean removeUser(int id) {
       try(Connection c= connect()) {
            
            Statement stmt= c.createStatement();//qayiq yaranir
           return stmt.execute("delete from user where id="+id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public User getById(int userId) {
        User result= null;
        try(Connection c= connect()) {//autoclose oldugu ucun try with resource
            
            Statement stmt= c.createStatement();//qayiq yaranir
            stmt.execute("SELECT" +
"	u.*," +
"	n.nationality," +
"	c.name AS birthplace " +
"       FROM" +
"	USER u" +
"	Left JOIN country n ON u.nationality_id = n.id" +
"	Left JOIN country c ON u.birthplace_id = c.id where U.id="+ userId);
            ResultSet rs=stmt.getResultSet();//bazadan netice qayidir onu ist etmek ucun menimsedirik rs-e
            while(rs.next()){//her setirde id name surname filan olur, onlari yazdiqca novbeti setre kecir. eger novbeti setir yoxdusa dayanir
                
                
               result= getUser(rs);
            }
           
            c.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }return result;
    }
    
    @Override
    public boolean addUser(User u) {
        try(Connection c= connect()) {
            PreparedStatement stmt= c.prepareStatement("insert into user(name,surname,phone,email,profileDescription,adress,birthdate) values(?,?,?,?,?,?)");//qayiq yaranir
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getPhone());
            stmt.setString(4, u.getEmail());
            stmt.setString(5, u.getProfileDesc());
            stmt.setString(6, u.getAdress());
            stmt.setDate(7, u.getBirthDate());
         
            
           return stmt.execute(); 
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }}//bu metod user add edir ardinca

   
}
