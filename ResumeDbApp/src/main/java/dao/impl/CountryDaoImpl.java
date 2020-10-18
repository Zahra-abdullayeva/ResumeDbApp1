
package dao.impl;

import dao.inter.AbstractDAO;
import dao.inter.CountryDaoInter;
import entity.Country;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CountryDaoImpl extends AbstractDAO implements CountryDaoInter{

    @Override
    public List<Country> getAllCountry() {
        List<Country>result=new ArrayList<>();
        Connection c;
        try {
            c= connect();
            Statement stmt = c.createStatement();
            stmt.execute("select * from country");
            ResultSet rs= stmt.getResultSet();
            while(rs.next()){
            Country country=getCountry(rs);
            result.add(country);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       return result;
    }
    
    private Country getCountry(ResultSet rs) throws Exception{
        int id=rs.getInt("id");
        String name=rs.getString("name");
        String nationality= rs.getString("nationality");
        return new Country(id, name, nationality);
    }
            
    @Override
    public Country getById(int id) {
        Country country=null;
        Connection c;
        try {
            c=connect();
            PreparedStatement stmt=c.prepareStatement("select * from country where id=?");
            stmt.setInt(1, id);
            stmt.execute();
            ResultSet rs= stmt.getResultSet();
            while(rs.next()){
                country=getCountry(rs);}
        } catch (Exception e) {}
        return country;
    }

    @Override
    public boolean updateCountry(Country country) {
      Connection c;
        boolean b=true;
        try {
            c= connect();
            PreparedStatement stmt=c.prepareStatement("update country set name=?, nationality=? where id=?");
            stmt.setString(1, country.getName());
            stmt.setString(2, country.getNationality());
            stmt.setInt(3, country.getId());
            b=stmt.execute();
        } catch (Exception e) { b=false;}
        return b;
    }

    @Override
    public boolean removeCountry(int id) {
        Connection c;
        boolean b=true;
        try {
            c=connect();
            Statement stmt= c.createStatement();
            stmt.execute("delete from country where id="+id);
            
            
        } catch (Exception e) { b=false; }
        return b;
    }
    
    
    
}
