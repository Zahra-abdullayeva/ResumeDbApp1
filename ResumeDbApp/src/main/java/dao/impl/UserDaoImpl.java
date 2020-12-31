
package dao.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
    private User getUserSimple(ResultSet rs) throws Exception{
        int id= rs.getInt("id");
        String name= rs.getString("name");
        String surname= rs.getString("surname");
        String phone= rs.getString("phone");
        String email= rs.getString("email");
        String profileDesc=rs.getString("profileDescription");
        String adress=rs.getString("adress");
        int nationalityId=rs.getInt("nationality_id");
        int birthplaceId=rs.getInt("birthplace_id");
       Date birthDate=rs.getDate("birthdate");

        User user=new User(id, name, surname, phone,email,profileDesc,adress, birthDate,null,null);
        user.setPassword(rs.getString("password"));
        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user=null;
        try(Connection c=connect()){
            PreparedStatement stmt= c.prepareStatement("select * from user where email=?");
            stmt.setString(1, email);
            ResultSet rs= stmt.executeQuery();
            while (rs.next()){
                user=getUserSimple(rs);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }


    @Override
    public User findByEmailAndPassword(String email, String password) {
        User user=null;
        try(Connection c=connect()){
        PreparedStatement stmt= c.prepareStatement("select * from user where email=? and password=?");
        stmt.setString(1, email);
        stmt.setString(2, password);
        ResultSet rs= stmt.executeQuery();
        while (rs.next()){
            user=getUserSimple(rs);
        }
    }catch (Exception ex) {
            ex.printStackTrace();
    }
    return user;
    }

    @Override
    public List<User> getAll(String name, String surname, Integer nationalityId) {
        List<User> result= new ArrayList<>();
        try(Connection c= connect()) {//autoclose oldugu ucun try with resource
            String sql= "SELECT" +
                    "	u.*," +
                    "	n.nationality," +
                    "	c.name AS birthplace " +
                    "FROM" +
                    "	USER u" +
                    "	Left JOIN country n ON u.nationality_id = n.id" +
                    "	Left JOIN country c ON u.birthplace_id = c.id where 1=1";
            if (name!=null && !name.trim().isEmpty()){//nuldan ferqli ve "" bele olmasa, cunki "" null kimi tanimir bu null demek deyil eslinde. ona gore ayrica yaziriq onun ucun sherti. istemirik ne null olsun nedeki "" bele
                //saytin url hissede name= yazib ardinca hecne yazmasaq bu "" bele hesab olunur, name buttonda ise hecne yazmasaq null hesab olunur
                sql+=" and u.name=? ";//SQL+= ona gore yazdim ki yuxaridaki sql + bu iflerden biri ile veya 2-3u ile birlikde toplanib query getsin
            }
            if (surname!=null && !surname.trim().isEmpty()){
                sql+=" and u.surname=? ";
            }
            if (nationalityId!=null){
                sql+=" and u.nationality_id=? ";
            }
            PreparedStatement stmt= c.prepareStatement(sql);
            int i=1;
            if (name!=null && !name.trim().isEmpty()){
                stmt.setString(i,name);
                i++;
            }if (surname!=null && !surname.trim().isEmpty()){
                stmt.setString(i,surname);
                i++;
            }if (nationalityId!=null){
                stmt.setInt(i,nationalityId);
            }
            stmt.execute();
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
    private  BCrypt.Hasher crypt= BCrypt.withDefaults();
    @Override
    public boolean addUser(User u) {
        try(Connection c= connect()) {
            PreparedStatement stmt= c.prepareStatement("insert into user(name,surname,phone,email,password,profileDescription,adress,birthdate) values(?,?,?,?,?,?,?,?)");//qayiq yaranir
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getPhone());
            stmt.setString(4, u.getEmail());
            stmt.setString(5, crypt.hashToString(4,u.getPassword().toCharArray()));
            stmt.setString(6, u.getProfileDesc());
            stmt.setString(7, u.getAdress());
            stmt.setDate(8, u.getBirthDate());
            
           return stmt.execute(); 
        } catch (Exception ex) {
            ex.printStackTrace();

        }return false;}//bu metod user add edir ardinca

    public static void main(String[] args) {
        User u= new User(0,"test", "test","test", "test",null,null,null,null,null);
        u.setPassword("12345");
        new UserDaoImpl().addUser(u);
    }
   
}
