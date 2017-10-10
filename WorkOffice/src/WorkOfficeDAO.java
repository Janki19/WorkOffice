import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class WorkOfficeDAO
{

	private String protocol = "jdbc:derby:";
	private String dbName="C:/WorkOfficeDB";
	private Connection conn=null;
	private Statement stm=null;
	
		
	
	public WorkOfficeDAO(){
		
//Connection to DataBase        
		
		try
		{		
			conn=DriverManager.getConnection(protocol+dbName+";create=true");
			System.out.println("Connected");
		} 
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.toString());
			printSQLException(e);
			
		}	
	}
	
//------------------------------------------------FAMILIES METHODS---------------------------------------------------------
	
//Method to check if Families tables exist;
	
	public void ifTablesExist(){
		try
		{
			DatabaseMetaData dbmd=conn.getMetaData();
			ResultSet res=dbmd.getTables(null, null, "FAMILIES", null);
			if(res.next()){
				System.out.println("tables exists");
				
			}
			else{
				createTables();
				System.out.println("Tables has been created");
			}
			res.close();
		} catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.toString());
			printSQLException(e);
		}
		
	}
	
	
//Method to creating Families Table into Database;
	
	public void createTables(){
		String createFamiliesT="create table Families("
				+ "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
				+ "Name VARCHAR(20) NOT NULL,"
				+ "Surname VARCHAR(20) NOT NULL,"
				+ "Birth_Date VARCHAR(20) NOT NULL,"
				+ "Phone VARCHAR(20) NOT NULL,"
				+ "City VARCHAR(20) NOT NULL,"
				+ "Post_Code VARCHAR(20) NOT NULL,"
				+ "Street VARCHAR(20) NOT NULL,"
				+ "HousNr VARCHAR(20) NOT NULL,"
				+ "FlatNr VARCHAR(20) NOT NULL,"
				+ "FamilyPhone VARCHAR(20) NOT NULL,"
				+ "Physical_Fit VARCHAR(20) NOT NULL,"
				+ "Rate VARCHAR(20) NOT NULL,"
				+ "Info VARCHAR(100) NOT NULL,"
				+ "LanguageLvl VARCHAR(20) NOT NULL,"
				+ "Experience VARCHAR(20) NOT NULL,"
				+ "Physical_Work VARCHAR(20) NOT NULL,"
				+ "Employee_Age VARCHAR(20) NOT NULL"
				+ ")";	
			
		try
		{
			stm=conn.createStatement();
			stm.execute(createFamiliesT);
			stm.close();
			
		} catch (SQLException e)
		{
			System.out.println("Problem with created tables Families");
			JOptionPane.showMessageDialog(null, e.toString());
			printSQLException(e);
		}
	
	}
	
// Insert data to family table into Database.
	
	public boolean insertData(String name,String surname,String birthdate,String phone,String city,String postcode,
			String street,String housnr,String flatnr,String familyphone,String physicalfit,String rate,String info,
			String languagelvl,String experiencce,String physicalwork,String employeeage){

		try
		{

			PreparedStatement prst=conn.prepareStatement(
					"insert into FAMILIES(Name,Surname,Birth_Date,Phone,City,Post_Code,Street,HousNr,FlatNr,"
			      + "FamilyPhone,Physical_Fit,Rate,Info,LanguageLvl,Experience,Physical_Work,Employee_Age) "
				  + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			prst.setString(1, name);
			prst.setString(2, surname);
			prst.setString(3, birthdate);
			prst.setString(4, phone);
			prst.setString(5, city);
			prst.setString(6, postcode);
			prst.setString(7, street);
			prst.setString(8, housnr);
			prst.setString(9, flatnr);
			prst.setString(10, familyphone);
			prst.setString(11, physicalfit);
			prst.setString(12, rate);
			prst.setString(13, info);
			prst.setString(14, languagelvl);
			prst.setString(15, experiencce);
			prst.setString(16, physicalwork);
			prst.setString(17, employeeage);
			prst.execute();
			prst.close();
			
			System.out.print("Data inserted");	
			prst.close();
			return true;
		} catch (SQLException e)
		{
			System.out.print("Problem with insert data.");
			JOptionPane.showMessageDialog(null, e.toString());
			printSQLException(e);
			return false;
		}
	}
	
//Update data to DataBase
	public void updateData(int id,String name,String surname,String birthdate,String phone,String city,String postcode,
			String street,String housnr,String flatnr,String familyphone,String physicalfit,String rate,String info,
			String languagelvl,String experiencce,String physicalwork,String employeeage)
	{
		try
		{
			PreparedStatement prst=conn.prepareStatement("UPDATE FAMILIES SET Name=?,Surname=?,Birth_Date=?,Phone=?,City=?,"
					+ "Post_Code=?,Street=?,HousNr=?,FlatNr=?,FamilyPhone=?,Physical_Fit=?,Rate=?,Info=?,LanguageLvl=?,"
					+ "Experience=?,Physical_Work=?,Employee_Age=? WHERE id=?");
				
			prst.setString(1, name);
			prst.setString(2, surname);
			prst.setString(3, birthdate);
			prst.setString(4, phone);
			prst.setString(5, city);
			prst.setString(6, postcode);
			prst.setString(7, street);
			prst.setString(8, housnr);
			prst.setString(9, flatnr);
			prst.setString(10, familyphone);
			prst.setString(11, physicalfit);
			prst.setString(12, rate);
			prst.setString(13, info);
			prst.setString(14, languagelvl);
			prst.setString(15, experiencce);
			prst.setString(16, physicalwork);
			prst.setString(17, employeeage);
			prst.setInt(18, id);
			
			prst.executeUpdate();
			
			prst.close();
			conn.close();
			
			System.out.println("Data updated");
			JOptionPane.showMessageDialog(null, "Data updated");
			
			
		} catch (SQLException e)
		{
			System.out.println("Error updateData");
			
			printSQLException(e);		
		}
	}
	
// Delete family from DataBase
	
	public void deleteData(int id){
		try {
			PreparedStatement prst=conn.prepareStatement("DELETE FROM FAMILIES WHERE id=?");
			prst.setInt(1, id);
			prst.executeUpdate();
			prst.close();
			System.out.println("Pozycja usunieta");
		} catch (SQLException e) {
			printSQLException(e);
		}
		
	}
	
// Loading data from Database to Family Model.

	public ArrayList<FamilyModel>getFamilyList(){
		
		ArrayList<FamilyModel>familyList=new ArrayList<>();
			String sqlList="select * FROM Families";
			try
			{	
				ResultSet res;
				stm = conn.createStatement();
				res=stm.executeQuery(sqlList);
				FamilyModel person;
				while(res.next()){
					person=new FamilyModel(res.getInt("id"),res.getString("Name"),res.getString("Surname"),res.getString("Birth_Date"),
							res.getString("Phone"),res.getString("City"),res.getString("Post_Code"),res.getString("Street"),
							res.getString("HousNr"),res.getString("FlatNr"),res.getString("FamilyPhone"),res.getString("Physical_Fit"),
							res.getString("Rate"),res.getString("Info"),res.getString("LanguageLvl"),res.getString("Experience"),
							res.getString("Physical_Work"),res.getString("Employee_Age"));
					
					
					familyList.add(person);
				} 
				
				 stm.close();   
				 conn.close();
				 res.close();
				 
				 System.out.println("DB loaded to Family List");
			}
				
			catch (SQLException e)
			{
			
				e.printStackTrace();
				System.out.println("Problem z wczytaniem listy");
			}
			return familyList;
			
		}


//Method to print table.
	
	public void showTable(){
	 String sqlSelect="select * from Families";
	 
		try
		{
			System.out.println("Wyswietlam liste");
			stm=conn.createStatement();
			ResultSet res=stm.executeQuery(sqlSelect);
			ResultSetMetaData rsmd=res.getMetaData();
			int columnCount=rsmd.getColumnCount();
			System.out.println("");
			for(int i=1; i<=columnCount; i++){
				System.out.format("%20s", rsmd.getColumnName(i)+"|");
			}
			System.out.println("");
			
			while(res.next()){
				for(int i=1; i<=columnCount; i++){
					System.out.format("%20s", res.getString(i)+"|");
				}
				System.out.println("");
			}
			res.close();
			conn.close();
		} catch (SQLException e)
		{
			printSQLException(e);
		}
	}
	
	
	
//------------------------------------EMPLOYEE METHODS-----------------------------------------------------------
	
//Method to check if Families tables exist
	
		public void ifEmpTablesExist(){
			try
			{
				DatabaseMetaData dbmd=conn.getMetaData();
				ResultSet res=dbmd.getTables(null, null, "EMPLOYEE", null);
				if(res.next()){
					System.out.println("EMPLOYEE table exists");
					
				}
				else{
					createEmployeeTables();
					System.out.println("EMPLOYEE table has been created");
				}
				res.close();
			} catch (SQLException e)
			{
				JOptionPane.showMessageDialog(null, e.toString());
				printSQLException(e);
			}
			
		}
	
//Create a Employee table.
	
	public void createEmployeeTables(){
		String createEmployeeT="create table EMPLOYEE("
				+ "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
				+ "Name VARCHAR(20) NOT NULL,"
				+ "Surname VARCHAR(20) NOT NULL,"
				+ "Birth_Date VARCHAR(20) NOT NULL,"
				+ "Phone VARCHAR(20) NOT NULL,"
				+ "City VARCHAR(20) NOT NULL,"
				+ "Post_Code VARCHAR(20) NOT NULL,"
				+ "Street VARCHAR(20) NOT NULL,"
				+ "HousNr VARCHAR(20) NOT NULL,"
				+ "FlatNr VARCHAR(20) NOT NULL,"
				+ "LanguageLvl VARCHAR(20) NOT NULL,"
				+ "Experience VARCHAR(20) NOT NULL,"
				+ "Physical_Work VARCHAR(20) NOT NULL,"
				+ "Married VARCHAR(20) NOT NULL,"
				+ "Availability VARCHAR(20) NOT NULL"
				+ ")";		
		try
		{
			stm=conn.createStatement();
			stm.execute(createEmployeeT);
			stm.close();
			
		} catch (SQLException e)
		{
			System.out.println("Problem with created tables Employee");
			JOptionPane.showMessageDialog(null, e.toString());
			printSQLException(e);
		}
	
	}
	
	
// Insert data to Employee table into Database.
	
	public boolean insertDataEmp(String name,String surname,String birthdate,String phone,String city,
				String postcode,String street,String nrhouse,String nrflat,String language,
				String experience,String physicalwork,String married,String availability){
			try
			{
				PreparedStatement prst=conn.prepareStatement(
						"insert into EMPLOYEE(Name,Surname,Birth_Date,Phone,City,Post_Code,Street,HousNr,FlatNr,"
				      + "LanguageLvl,Experience,Physical_Work,Married,Availability) "
					  + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
				prst.setString(1, name);
				prst.setString(2, surname);
				prst.setString(3, birthdate);
				prst.setString(4, phone);
				prst.setString(5, city);
				prst.setString(6, postcode);
				prst.setString(7, street);
				prst.setString(8, nrhouse);
				prst.setString(9, nrflat);
				prst.setString(10, language);
				prst.setString(11, experience);
				prst.setString(12, physicalwork);
				prst.setString(13, married);
				prst.setString(14, availability);
				prst.execute();
				prst.close();
				
				System.out.print("EMPLOYEE data inserted");
				return true;		
			} catch (SQLException e)
			 {
				System.out.print("Problem with insert data to EMPLOYEE.");
				JOptionPane.showMessageDialog(null, e.toString());
				printSQLException(e);
				return false;
			}
		}
	
	// Loading data from Database to Employee Model.

		public ArrayList<EmployeeModel>getEmployeeListList(){
			
			ArrayList<EmployeeModel>employeeList=new ArrayList<>();
				String sqlList="select * FROM EMPLOYEE";
				try
				{	
					ResultSet res;
					stm = conn.createStatement();
					res=stm.executeQuery(sqlList);
					EmployeeModel person;
					while(res.next()){
						person=new EmployeeModel(res.getInt("id"),res.getString("Name"),res.getString("Surname"),res.getString("Birth_Date"),
								res.getString("Phone"),res.getString("City"),res.getString("Post_Code"),res.getString("Street"),
								res.getString("HousNr"),res.getString("FlatNr"),res.getString("LanguageLvl"),res.getString("Experience"),
								res.getString("Physical_Work"),res.getString("Married"),res.getString("Availability"));
						
						
						employeeList.add(person);
					} 
					
					 stm.close();   
					 conn.close();
					 res.close();
					 
					 System.out.println("DB loaded to Employee List");
				}
					
				catch (SQLException e)
				{
				
					e.printStackTrace();
					System.out.println("Problem z wczytaniem listy employee");
				}
				return employeeList;
				
			}
	
		//Update data to DataBase
		public void updateEmployeeData(int id,String name,String surname,String birthdate,String phone,String city,String postcode,
				String street,String housnr,String flatnr,String languagelvl,String experiencce,String physicalwork,
				String married,String availability)
		{
			try
			{
				PreparedStatement prst=conn.prepareStatement("UPDATE EMPLOYEE SET Name=?,Surname=?,Birth_Date=?,Phone=?,City=?,"
						+ "Post_Code=?,Street=?,HousNr=?,FlatNr=?,LanguageLvl=?,"
						+ "Experience=?,Physical_Work=?,Married=?,Availability=? WHERE id=?");
					
				prst.setString(1, name);
				prst.setString(2, surname);
				prst.setString(3, birthdate);
				prst.setString(4, phone);
				prst.setString(5, city);
				prst.setString(6, postcode);
				prst.setString(7, street);
				prst.setString(8, housnr);
				prst.setString(9, flatnr);
				prst.setString(10, languagelvl);
				prst.setString(11, experiencce);
				prst.setString(12, physicalwork);
				prst.setString(13, married);
				prst.setString(14, availability);
				prst.setInt(15, id);
				
				prst.executeUpdate();
				
				prst.close();
				conn.close();
				
				System.out.println("EMPLOYEE Data updated");
				JOptionPane.showMessageDialog(null, "EMPLOYEE Data updated");
				
				
			} catch (SQLException e)
			{
				System.out.println("Error updateData EMPLOYEE");
				
				printSQLException(e);		
			}
		}
	
	//Method to print table.
	
		public void showEmployeeTable(){
		 String sqlSelect="select * from EMPLOYEE";
		 
			try
			{
				stm=conn.createStatement();
				ResultSet res=stm.executeQuery(sqlSelect);
				ResultSetMetaData rsmd=res.getMetaData();
				int columnCount=rsmd.getColumnCount();
				System.out.println("");
				for(int i=1; i<=columnCount; i++){
					System.out.format("%20s", rsmd.getColumnName(i)+"|");
				}
				System.out.println("");
				
				while(res.next()){
					for(int i=1; i<=columnCount; i++){
						System.out.format("%20s", res.getString(i)+"|");
					}
					System.out.println("");
				}
				res.close();
				conn.close();
			} catch (SQLException e)
			{
				printSQLException(e);
			}
		}
		
// Delete Employee from DataBase
		
		public void deleteEmployeeData(int id){
			try {
				PreparedStatement prst=conn.prepareStatement("DELETE FROM EMPLOYEE WHERE id=?");
				prst.setInt(1, id);
				prst.executeUpdate();
				prst.close();
				System.out.println("Pozycja usunieta z EMPLOYEE");
			} catch (SQLException e) {
				printSQLException(e);
			}
			
		}
	
//=============================================================================================================================================================================
		
//Method to close connection
	public void closeConnection(){
		try
		{
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		
		} 
		catch (SQLException e)
		{
			if (( (e.getErrorCode() == 50000) && ("XJ015".equals(e.getSQLState()) ))) {
                System.out.println("Derby shut down normally");
            } 
			else 
			{
				printSQLException(e);
                System.err.println("Derby did not shut down normally");
                
            }
		}
		
	}
			
//Print SQL exception	
	private static void printSQLException(SQLException e)
    {
      
        while (e != null)
        {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            e = e.getNextException();
        }
    }
	
	
}
