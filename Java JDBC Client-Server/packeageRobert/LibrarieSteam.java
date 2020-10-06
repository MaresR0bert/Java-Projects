package packeageRobert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibrarieSteam {
    public String dbURL = "jdbc:derby:SteamLibrary;create=true";
    public String dbUser = "MeDerby";
    public String dbPassowrd = "MeDerby";
    public String dbName = "Steam Libray";
    public String tableName = "Jocuri";
    public Connection conn;
    public Statement sqlStmt;
    public static int conunter = 0;


    public LibrarieSteam(){
        this.tableName="Jocuri"+ ++conunter;
    }

    public void createConnection(){
        try {
            conn= DriverManager.getConnection(dbURL,dbUser,dbPassowrd);
            System.out.println("Connection Succeded to Database\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //NU AM REUSIT SA O FAC SA MEARGA, VA ROG FEEDBACK
    public boolean checkIfTableExists(){
        try {
            sqlStmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = sqlStmt.executeQuery("select st.tablename from sys.systables st LEFT OUTER join sys.sysschemas ss on (st.schemaid = ss.schemaid) where ss.schemaname ='"+tableName+"'");
            resultSet.last();
            int NrInreg = resultSet.getRow();
            if(NrInreg==1) return true;
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createTable(){
        try {
            sqlStmt = conn.createStatement();
            sqlStmt.execute("create table "+tableName+"( idJoc int primary key, denumire varchar(20), dimensiune int, tipJoc varchar(20) )");
            System.out.println("Table was created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTable(){
        try {
            sqlStmt = conn.createStatement();
            sqlStmt.execute("drop table "+tableName);
            System.out.println("Tabel was dropped");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertJoc(Jocuri joc){
        try {
            sqlStmt = conn.createStatement();
            sqlStmt.execute("insert into "+dbUser+"."+tableName+" values("+joc.getIdJoc()+", '"+ joc.getDenumire()+"', "+joc.getDimensiune()+", '"+joc.getTipJoc().toString()+"')");
            //System.out.println("Game inserted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateJoc(String denNew,int newDim){
        try {
            sqlStmt = conn.createStatement();
            sqlStmt.executeUpdate("update "+tableName+ " set dimensiune = "+newDim+" where denumire ='"+denNew+"'");
            //System.out.println("Game "+newId+" was updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteJoc(String newDen){
        try {
            sqlStmt = conn.createStatement();
            sqlStmt.executeUpdate("delete from "+tableName+ " where denumire = '"+newDen+"'");
            //System.out.println("Game "+newId+" was deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int noOfJocuri(){
        try {
            sqlStmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = sqlStmt.executeQuery("select * from "+tableName);
            resultSet.last();
            return resultSet.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String[] selectJocuri(){
        try {
            sqlStmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = sqlStmt.executeQuery("select * from "+tableName);
            resultSet.last();
            int nrJoc = resultSet.getRow();
            String[] str = new String[nrJoc+1];
            str[0]="\n";
            System.out.println("Au fost selectate "+nrJoc+" din "+tableName);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int nrColoane = metaData.getColumnCount();
            for(int i=1; i<=nrColoane; i++){
                System.out.print(metaData.getColumnName(i)+"\t\t");
                str[0]+=metaData.getColumnName(i)+"\t\t";
            }
            str[0].replace("null","");
            System.out.println();
            List<Jocuri> jocuriList = new ArrayList<Jocuri>();
            resultSet.beforeFirst();
            int i=1;
            while(resultSet.next()){
                Jocuri jocLocal = new Jocuri(1);
                jocLocal.setIdJoc(resultSet.getInt("idJoc"));
                jocLocal.setDenumire(resultSet.getString(2));
                jocLocal.setDimensiune(resultSet.getInt(3));
                jocLocal.setTipJoc(EnumTipJoc.valueOf(resultSet.getString(4)));

                jocuriList.add(jocLocal);
                //return jocuriList;
                System.out.println(jocLocal.toTableString());
                str[i]=jocLocal.toTableString();
                i++;
            }
            //return jocuriList;
            return str;
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        List<Jocuri> listLocal = new ArrayList<Jocuri>();
//        return listLocal;
        return null;
    }
}
