package models;

public class admin {
    private int admin_id;
    private String name;
    private String password;
    public admin()
    {

    }

    public admin(int admin_id,String name,String password)
    {
        this.admin_id=admin_id;
        this.name=name;
        this.password=password;
    }

    public void setadmin_id(int admin_id)
    {
        this.admin_id=admin_id;
    }

    public int getAdmin_id()
    {
        return admin_id;
    }

    public void setname(String name)
    {
        this.name=name;
    }

    public String getname()
    {
        return name;
    }

    public void setpassword(String password)
    {
        this.password=password;
    }

    public String getPassword()
    {
        return password;
    }


}
