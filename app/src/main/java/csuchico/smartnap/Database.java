package csuchico.smartnap;


import com.orm.SugarRecord;

/**
 * Created by Penny on 9/27/2017.
 */

public class Database extends SugarRecord<Class>
{
    String name;
    public Database()
    {}

    public Database(String getName)
    {
        this.name=getName;
    }

    public String toString()
    {
        return name;
    }
}