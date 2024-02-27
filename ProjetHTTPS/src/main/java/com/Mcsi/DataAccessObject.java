package com.Mcsi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataAccessObject {
	//Cette class sert a gerer tout operation qui necessite la communication avec la base de donne
	public static Connection getConnection() {
		//methode static pour la creation de la connection avec la base de donne
		Connection con = null;
		//initialisation des parametres de la connection
		String url = "jdbc:mysql://localhost:3306/university";
		String user = "root";
		String password = "";
		try {
			//chargement du provider
			Class.forName("com.mysql.cj.jdbc.Driver");
			//creation de la connection
			con = DriverManager.getConnection(url, user,  password);
		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}
	
	public static int authenticate(UserBean l) {
		//methode static pour l'authentification des utilisateurs
		int status = 1;
		try {
			//connection avec base de donne
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			Statement st = con.createStatement();
			String sqlAuth = "SELECT * FROM users WHERE username = '" + l.getUsername() + "' AND password = '" + l.getPassword() + "';";
			//execution de la requete
			ResultSet rs = st.executeQuery(sqlAuth);
			if (rs.next()) {
				//si l'utilisateur existe on doit lui affecter tout ses proprietes
				status = 0;
				String role = rs.getString(4);
				l.setId(rs.getInt(1));
				l.setRole(role);
				if (role.equals("student")) {
					((Student)l).setPoints(rs.getInt(6));
				} else if (role.equals("teacher")) {
					((Teacher)l).setUnit_name(rs.getString(5));
				}
				
				
			}
			//fermeture de la connection pour eviter les fuites de memoire 
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}
	
	public static List<Student> getStudents() {
		//methode static pour la recuperation des etudiants
		//initialisation de la liste des utilisateurs
		List<Student> users = new ArrayList<Student>();
			try {
				//creation de la connection avec la base de donnees
				Connection con = DataAccessObject.getConnection();
				//creation et initialisation de la requete sql
				Statement st = con.createStatement();
				String sql = "SELECT * FROM users WHERE role = 'student';";
				//execution de la requete
				ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					//pour chaque resulta on va definir un nouveau utilisateur a qui on va affecter les proprietes recuperees
					Student user = new Student(rs.getString(2), rs.getString(3), rs.getInt(6));
					user.setId(rs.getInt(1));
					//ajout de l'utilisateur au liste des utilisateur qu'on va retourner
					users.add(user);
				}
				//fermeture de la connection
				rs.close();
				st.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			return users;
	}
	
	public static List<Teacher> getTeachers() {
		//methode static pour la recuperation des professeurs
		//initialisation de la liste des utilisateurs
		List<Teacher> users = new ArrayList<Teacher>();
		try {
			//creation de la connection avec la base de donnees
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			Statement st = con.createStatement();
			String sql = "SELECT * FROM users WHERE role = 'teacher';";
			//execution de la requete
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				//pour chaque resulta on va definir un nouveau utilisateur a qui on va affecter les proprietes recuperees
				Teacher user = new Teacher(rs.getString(2), rs.getString(3), rs.getString(5));
				user.setId(rs.getInt(1));
				//ajout de l'utilisateur au liste des utilisateur qu'on va retourner
				users.add(user);
			}
			//fermeture de la connection
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return users;
			
	}
	
	public static void addUser(String username, String password, String role, String unit_name, int points) {
		//methode pour l'ajout d'un utilisateur
		try {
			//creation de la connection avec la base de donnees
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			Statement st = con.createStatement();
			String sql = "INSERT INTO users (username, password, role, unit_name, points) VALUES ('" + username + "', '" + password + "', '" + role + "', '" + unit_name + "', " + points + ");";
			//execution de la requete
			st.executeUpdate(sql);
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void modifyUser(int id, String username, String password, String unit_name, int points) {
		//methode pour la modification d'un utilisateur
		try {
			//creation de la connection avec la base de donnees
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			Statement st = con.createStatement();
			String sql = "UPDATE users SET username = '" + username + "', password = '" + password + "', unit_name = '" + unit_name + "', points = '" + points +"' WHERE id = '" + id + "';";
			//execution de la requete
			st.executeUpdate(sql);
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteUser(int id) {
		//methode pour la suppression d'un utilisateur
		try {
			//creation de la connection avec la base de donnees
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			Statement st = con.createStatement();
			String sql = "DELETE FROM users WHERE id='" + id + "';";
			//execution de la requete
			st.executeUpdate(sql);
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
