
package marcajeangels;

import java.sql.*;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
/**
 *
 * @author it
 */
public class BDPool {
    public static DataSource getDatasource(){
    
    BasicDataSource ds = new BasicDataSource();
    ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
    ds.setUsername("marcajea");
    ds.setPassword("Coast@cm");
    //ds.setUrl("jdbc:mysql://192.168.196.46:3306/marcaje?useTimezone=true&serverTimezone=UTC");
    ds.setUrl("jdbc:mysql://140.84.178.126:3306/marcaje?useTimezone=true&serverTimezone=UTC");
    ds.setInitialSize(5);
    return ds;
    }
       public static Connection getConexion() throws SQLException{
       return getDatasource().getConnection();
       }
    }
    

