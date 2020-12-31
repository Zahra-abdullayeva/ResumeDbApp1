
package main;

import dao.inter.EmploymentHistoryDaoInter;
import dao.inter.SkillDaoInter;
import dao.inter.UserDaoInter;
import entity.Skill;
import entity.User;
import java.util.List;



public class Main {
    public static void main(String[] args) {
        //getbyname metodu skillde olan listsiz yoxla bidene
        UserDaoInter userDao= Context.instanceUserDao();
     

//       u.setName("Azad");
//        System.out.println(userDao.updateUser(u));
//   
}}
//biz burda User u= userDao.getById(2); evezine User u= new User(); yazib sonra u.setId(2); deseydim teze userin tekce adini set
//etmish olacaqdim qalan parametrleri null olacaqdi. ama biz var olan useri update etdik. yeni ki. hersheyi var sadece 
//ad set edib gonderdik update-e