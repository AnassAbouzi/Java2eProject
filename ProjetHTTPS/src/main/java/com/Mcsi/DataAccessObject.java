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
			//dans la requete on groupe les trois tableaux users, students et teachers avec LEFT JOIN et on utilise la fonction COALESCE qui retourne la premiere valeur non null (dans ses arguments) pour eviter les champs null.
			String sql = "SELECT users.id, username, password, role, COALESCE(students.points, 0) AS points, COALESCE(teachers.unit_name, 'N/A') AS unit_name FROM users LEFT JOIN students ON users.id = students.user_id LEFT JOIN teachers ON users.id = teachers.user_id WHERE username = '" + l.getUsername() + "' AND password = '" + l.getPassword() + "';";
			//execution de la requete
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				//si l'utilisateur existe on doit lui affecter tout ses proprietes
				status = 0;
				String role = rs.getString(4);
				l.setId(rs.getInt(1));
				l.setRole(role);
				if (role.equals("student")) {
					((Student)l).setPoints(rs.getInt(5));
				} else if (role.equals("teacher")) {
					((Teacher)l).setUnit_name(rs.getString(6));
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
				String sql = "SELECT users.id, username, password, points FROM users JOIN students ON  users.id = students.user_id WHERE users.role = 'student';";
				//execution de la requete
				ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					//pour chaque resulta on va definir un nouveau utilisateur a qui on va affecter les proprietes recuperees
					Student user = new Student(rs.getString(2), rs.getString(3), rs.getInt(4));
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
			String sql = "SELECT users.id, username, password, unit_name FROM users JOIN teachers ON users.id = teachers.user_id WHERE users.role = 'teacher';";
			//execution de la requete
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				//pour chaque resulta on va definir un nouveau utilisateur a qui on va affecter les proprietes recuperees
				Teacher user = new Teacher(rs.getString(2), rs.getString(3), rs.getString(4));
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
			String sql = "INSERT INTO users (username, password, role) VALUES ('" + username + "', '" + password + "', '" + role + "');";
			//execution de la requete
			st.executeUpdate(sql);
			sql = "Select id FROM users WHERE username = '" + username + "';";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			int id = rs.getInt(1);
			if (role.equals("student")) {
				sql = "INSERT INTO students (user_id, points) VALUES (" + id + ", " + points + ");";
				st.executeUpdate(sql);
			} else if (role.equals("teacher")) {
				sql = "INSERT INTO teachers (user_id, unit_name) VALUES (" + id + ", '" + unit_name + "');";
				st.executeUpdate(sql);
			}
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void modifyUser(int id, String username, String password, String role, String unit_name, int points) {
		//methode pour la modification d'un utilisateur
		try {
			//creation de la connection avec la base de donnees
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			Statement st = con.createStatement();
			String sql = "UPDATE users SET username = '" + username + "', password = '" + password + "' WHERE id = '" + id + "';";
			//execution de la requete
			st.executeUpdate(sql);
			if (role.equals("student")) {
				sql = "UPDATE students SET points = " + points + " WHERE user_id = " + id +";";
				st.executeUpdate(sql);
			} else if (role.equals("teacher")) {
				sql = "UPDATE teachers SET unit_name = '" + unit_name + "' WHERE user_id = " + id + ";";
				st.executeUpdate(sql);
			}
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
	
	public static int verify(String username) {
		//methode pour verifier l'existence d'un utilisateur dans la base de donnees.
		//initialisation de la variable a retourner indiquant l'existance d'un utilisateur avec le nom d'utilisateur username
		int status = 0;
		try {
			//creation de la connection avec la base de donnees
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			Statement st = con.createStatement();
			String sql = "SELECT * FROM users WHERE username = '" + username + "';";
			ResultSet rs = st.executeQuery(sql);
			//s'il existe un utilisateur avec le meme nom d'utilisateur on retourne 1
			if (rs.next()) {
				status = 1;
			}
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static void createUser(String username, String password) {
		//methode pour la creation d'un nouveau utilisateur
		try {
			//creation de la connection avec la base de donnees
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			Statement st = con.createStatement();
			String sql = "INSERT INTO users (username, password, role) VALUES ('" + username + "', '" + password + "', 'student');";
			st.executeUpdate(sql);
			sql = "SELECT id FROM users WHERE username = '" + username + "';";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			int id = rs.getInt(1);
			sql = "INSERT INTO students (user_id, points) VALUES (" + id + ", 0);";
			st.executeUpdate(sql);
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
