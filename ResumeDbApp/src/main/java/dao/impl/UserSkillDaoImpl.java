
package dao.impl;

import entity.Skill;
import entity.User;
import entity.UserSkill;
import dao.inter.AbstractDAO;
import dao.inter.UserSkillDaoInter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserSkillDaoImpl extends AbstractDAO implements UserSkillDaoInter{

   
    @Override
    public List<UserSkill> getAllSkillByUserId(int userId) {
        List<UserSkill> result= new ArrayList<>();
        try(Connection c= connect()) {//autoclose oldugu ucun try with resource
            
            PreparedStatement stmt= c.prepareStatement("SELECT " + "us.id as userSkillId,"+
"	u.*," +
"	us.skill_id," +
"	s.NAME AS skill_name," +
"	us.power " +
"FROM" +
"	user_skill us" +
"	LEFT JOIN USER u ON us.user_id = u.id" +
"	LEFT JOIN skill s ON us.skill_id = s.id " +
"WHERE" +
"	us.user_id =?");
            stmt.setInt(1, userId);
            stmt.execute();//userin id-sine esasen skilli gelecek
            ResultSet rs=stmt.getResultSet();
            while(rs.next()){
                UserSkill u= getUserSkill(rs);
                result.add(u); 
            } } catch (Exception ex) {
            ex.printStackTrace();
        }return result;
        
    }
     private UserSkill getUserSkill(ResultSet rs) throws Exception{
    int userSkillId=rs.getInt("userSkillId");
    int userId=rs.getInt("id");
    int skillId=rs.getInt("skill_id");
    String skillName=rs.getString("skill_name");
    int power=rs.getInt("power");
    return new UserSkill(userSkillId,new User(userId), new Skill(skillId, skillName), power);
    }
     
    @Override
    public boolean insertUserSkill(UserSkill u) {
    try(Connection c= connect()) {
    PreparedStatement stmt=c.prepareStatement("insert into user_skill(skill_id,user_id,power) values(?,?,?)");
    stmt.setInt(1, u.getSkill().getId());
    stmt.setInt(2, u.getUser().getId());
    stmt.setInt(3, u.getPower());
    return stmt.execute();
    }   catch (Exception ex) {}
   return false;
}
    
    @Override
    public boolean updateUserSkill(UserSkill u) {
        try(Connection c= connect()){
            PreparedStatement stmt = c.prepareStatement("UPDATE user_skill SET skill_id = ? , user_id =? ,power =?  WHERE id = ? ;");

            stmt.setInt(1, u.getSkill().getId());
            stmt.setInt(2, u.getUser().getId());
            stmt.setInt(3, u.getPower());
            stmt.setInt(4, u.getId());

            return stmt.execute();

        } catch (Exception ex) {
            System.err.println(ex);
           
        }
        return false;
    }
    @Override
    public boolean removeUserSkill(int id) {
        Connection conn;
        try(Connection c= connect()){

            PreparedStatement stmt = c.prepareStatement("DELETE FROM user_skill WHERE ID=?");
            stmt.setInt(1, id);
            System.out.println("id :" + String.valueOf(id));
            return stmt.execute();

        } catch (Exception ex) {
            System.out.println(ex);
        }return false;
    }
    
}