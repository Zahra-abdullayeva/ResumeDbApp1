
package entity;

public class UserSkill {
    private Integer id;
    private User user;//burda ona gore User ve Skill obyektlerini yazmishiq ki userskill userin id-si filan ist edende bura user id user name bir bir yazmaqdansa bir basha obyektini yaziriq ki sonra da userin lazim gelende konstruktorundan ist edib yaza bilerik
    private Skill skill;//obyektin icinde obyekt deyirler buna. cunki sabahsi gun skillde nese artsa update olsa, hem skillin oz icinde hemde burda birde onu yazmiriq ancaq skille qeyd edirik artimi ve burda da onun obyekti vasitesile ist edeceyik
    private int power;

    public UserSkill() {
    }

    public UserSkill(Integer id, User user, Skill skill, int power) {
        this.id = id;
        this.user = user;
        this.skill = skill;
        this.power = power;
    }

    @Override
    public String toString() {
        return "UserSkill{" + "id=" + id + ", user=" + user + ", skill=" + skill + ", power=" + power + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

   
    
}
