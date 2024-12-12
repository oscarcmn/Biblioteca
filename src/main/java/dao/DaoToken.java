package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexiones.Conexion;
import entidades.Token;

public class DaoToken {

	public DaoToken() {
		super();
	}

	public void addToken(Token t) throws SQLException, Exception{
		Connection con=null;
        PreparedStatement st=null;
        try {
            Conexion miconex = new Conexion();
            con = miconex.getConexion();
            con.setAutoCommit(false);
            String ordenSQL = "INSERT INTO TOKEN VALUES(?,?,?,?,?)";
            st = con.prepareStatement(ordenSQL);
            st.setString(1, t.getEmail());
            st.setString(2, t.getClave());
            st.setString(3, t.getValue());
            st.setString(4, t.getTelefono());
            st.setTimestamp(5, t.getFecha());
            st.executeUpdate();
            con.commit();
            st.close();
            con.close();
        } catch (SQLException se) {
            throw se;
        } catch (Exception e) {
            throw e;
        }
        finally{
         	if(st!=null)
                st.close();
         	if(con!=null)
                con.close();
        }
	}
	
	public Token findTokenByEmail(String email) throws SQLException, Exception{
		Token t=null;
        Connection con=null;
        PreparedStatement st=null;
        ResultSet rs = null;
        try {
            Conexion miconex = new Conexion();
            con = miconex.getConexion();
            con.setAutoCommit(false);
            String ordenSQL = "SELECT * FROM TOKEN"+
            				  " WHERE EMAIL=?";
            st = con.prepareStatement(ordenSQL);
            st.setString(1, email);
            rs=st.executeQuery();  
            if(rs.next()) {        
            	t=new Token();
            	t.setEmail(rs.getString("email"));
            	t.setClave(rs.getString("clave"));
            	t.setValue(rs.getString("value"));
            	t.setTelefono(rs.getString("telefono"));
            	t.setFecha(rs.getTimestamp("FECHA_INICIO"));
            }
        } catch (SQLException se) {
            throw se;
        } catch (Exception e) {
            throw e;
        }
        finally{
         	if(st!=null)
                st.close();
         	if(con!=null)
                con.close();
        }
        return t;
	}
}
