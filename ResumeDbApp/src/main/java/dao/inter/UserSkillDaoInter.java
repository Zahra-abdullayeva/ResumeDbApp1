
package dao.inter;

import entity.UserSkill;
import java.util.List;


public interface UserSkillDaoInter {
    
     public List<UserSkill> getAllSkillByUserId(int userId);

}
