/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import dao.inter.AbstractDAO;
import dao.inter.SkillDaoInter;
import entity.Skill;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class SkillDaoImpl extends AbstractDAO implements SkillDaoInter{

    @Override
    public List<Skill> getAllSkill() {
        List<Skill> result= new ArrayList<>();
        Connection c;
        try {
             c= connect();
            Statement stmt = c.createStatement();
            stmt.execute("select * from skill");//qayiqla sorgu gonderdik
           ResultSet rs=stmt.getResultSet();//ordan gelen netice oturdu rs-e
            while(rs.next()){//ne qeder ki neticede adamlar varsa
               Skill skill=getskill(rs);//bazadaki id oturur burdaki id-ya name oturur burdaki name-e
               result.add(skill); // ve bu skill obyektini atiriq liste sonra da onu return edirik cap olunur(tostring-e esasen,cunki onu overrride etmishik)
            } } catch (Exception ex) {
            ex.printStackTrace();
        }return result;
        
    } 
    
       
    @Override
    public Skill getById(int id) {
        Skill skill=null;
        Connection c;
        try {
            c = connect();

            PreparedStatement stmt = c.prepareStatement("SELECT * FROM skill WHERE ID = ?");//id-ye gore skillden hershey cekilir
            stmt.setInt(1, id);//sen axtardigin insert etdiyin id ile ozunkunu uygunlashdirir tapir
            stmt.execute();
             ResultSet rs=stmt.getResultSet();//bazadan ordan qayidir sene meselen 1ci adamin detailleri
            while(rs.next()){
                skill=getskill(rs);//onu uygunlashriir sende olan user deatillere ve return edir
            }
           
        }catch (Exception ex) {
            ex.printStackTrace();
        }return skill;}//sen tostringi override tdiyin ucun psvm-de bu metodu cagirandan sonra u-nu cap edirsen hamsi gelir. amma tostring override olmasaydi gelmiyecekdi detailler ve list lazim olacaqdi sene getbyname kimi. ama burda sene list lazim olmur

    @Override
    public boolean updateSkill(Skill skill) {
         Connection c;
        try {
            c = connect();//meselen psvm-de setname edib ad deyishirem sonra update metodun cagiriram
            PreparedStatement stmt = c.prepareStatement("update skill set name=? where id=?");//burda deyishdiyim skillin ad name-ne esasen update gedir
            stmt.setString(1, skill.getName());//bazadaki skill name-e burdaki update olunmush yeni adin getname-i oturulur
            stmt.setInt(2,skill.getId());//id-si de uygunlashdirilir.
            return stmt.execute();
         }catch (Exception ex) {
            ex.printStackTrace();
        }return false;}

    @Override
    public boolean insertSkill(Skill skill) {
        Connection c;
        boolean b = true;
        try {
            c=connect();//evvel psvmde yeni bir skill yaradirsan yeni name verirsen konstruktor ile, sonra bu metod cagrilir
            PreparedStatement stmt=c.prepareStatement("insert skill (name) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, skill.getName());//teze skill-in name oturulur bazaya, yeni skill ve yeni id yaradir orda
            b=stmt.execute();
            ResultSet rs= stmt.getGeneratedKeys();//bazadan netice qayidir.
            while (rs.next()) {
                skill.setId(rs.getInt(1));//bazadki yeni id, burdaki add etdiyimiz yenice skill-e set olunur. cunki biz name vermishdik id ise bazada yaranir artan sira ile(navicatda)
            } } 
        catch (Exception e) {
            b=false; }
        return b;
    }
          private Skill getskill(ResultSet rs) throws Exception{
        int id=rs.getInt("id");
        String name=rs.getString("name");
        return new Skill(id, name);
    }

    @Override
    public boolean removeSkill(int id) {
        Connection c;
        boolean b=true;
        try {
            c=connect();
            Statement stmt=c.createStatement();
            stmt.execute("delete from skill where id="+id);
        } catch (Exception e) {
        b=false;} return b;
          }

    @Override
    public List<Skill> getByName(String name) {//ada gore sene detailleri qaytarir
        List<Skill>result=new ArrayList<>();
        Skill skill=null;
        Connection c;
        try {
            c=connect();
            PreparedStatement stmt= c.prepareStatement("select * from skill where name=?");
            stmt.setString(1, name);//senin daxil etdiyin (search) adi uygunlashirir ozunki ile tapir
            stmt.execute();
            ResultSet rs= stmt.getResultSet();//sene return edir 
            while(rs.next()){
                skill=getskill(rs);//sendeki adamla tapdigi detailleri uygunlashdirir
                result.add(skill);//detailleri de add edir liste ve return edir
            }
        } catch (Exception e) {
        }return result;
          }
    
    
    
    
}
