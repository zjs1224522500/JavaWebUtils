package tech.shunzi.security;

public class JWTTokenModel
{
    private String userName;
    private String email;
    private long exp;
    private String zid;

    public String getZid()
    {
    
        return zid;
    }

    public void setZid(String zid)
    {
        this.zid = zid;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public long getExp()
    {
        return exp;
    }

    public void setExp(long exp)
    {
        this.exp = exp;
    }
}
