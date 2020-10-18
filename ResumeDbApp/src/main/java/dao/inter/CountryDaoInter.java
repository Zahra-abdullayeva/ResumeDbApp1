/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.inter;

import entity.Country;
import java.util.List;

/**
 *
 * @author Zahra
 */
public interface CountryDaoInter {
    List<Country> getAllCountry();
     
    public Country getById(int id);
     
    boolean updateCountry(Country c);
     
    boolean removeCountry(int id);
}
