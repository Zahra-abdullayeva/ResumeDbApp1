
package entity;


public class Country {
    private int id;
    private String name;//haralisan
    private String nationality;//olke adi

    public Country(int id, String name, String nationality) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
    }


    public Country() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "Country{" + "id=" + id + ", name=" + name + ", nationality=" + nationality + '}';
    }

   
    
}
