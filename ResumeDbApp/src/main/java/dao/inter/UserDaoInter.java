
package dao.inter;
import entity.User;
import entity.UserSkill;
import java.util.List;

public interface UserDaoInter {
    public List<User> getAll();//geriye user obyektinden ibaret list return eden metod
     public User getById(int id);
    public boolean updateUser(User u);
     public boolean addUser(User u);
    public boolean removeUser(int id);
    
}

//biz neye gore userdaointerface ve userdaoimplement classlari ist etdik.
//bizim bir ayaz classimiz ola bilerdi ve icinde 
//public static userdaointer a(){
//return userdaoimpl}
//kimi bir metod ola bilerdi ve biz main classda userdaointer userdao= new userdaoimpl evezne
//userdaointer userdao= ayaz.a(); yaza bilerdik
// ama bunun pis ceheti odur ki bu metodun ici deyishe biler return eder bashqa birseyi ve biz burda baxanda goormuruk ki eslinde 
//userdaoimpl geri return edir. yeniki classlarin birbasha elaqesi yoxdur. factory patterndur bu
