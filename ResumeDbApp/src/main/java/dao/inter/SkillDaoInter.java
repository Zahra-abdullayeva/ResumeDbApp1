/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.inter;

import entity.Skill;
import java.util.List;


public interface SkillDaoInter {
     public List<Skill>getAllSkill();
     public Skill getById(int userId);
     public boolean updateSkill(Skill skill);
     public boolean insertSkill(Skill skill);
     public boolean removeSkill(int id);
     public List<Skill> getByName(String sname);
}
