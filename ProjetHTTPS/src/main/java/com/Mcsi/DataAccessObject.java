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
			String sqlAuth = "SELECT * FROM users WHERE username = '" + l.getUsername() + "' AND password = '" + l.getPassword() + "'";
			//execution de la requete
			ResultSet rs = st.executeQuery(sqlAuth);
			if (rs.next()) {
				//si l'utilisateur existe on doit lui affecter tout ses proprietes
				status = 0;
				l.setRole(rs.getString(4));
				l.setPoints(rs.getInt(6));
				l.setId(rs.getInt(1));
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
	
	public static List<UserBean> getUsers(String role) {
		//methode static pour la recuperation des utilisateurs d'un role preci
		//initialisation de la liste des utilisateurs
		List<UserBean> users = new ArrayList<UserBean>();
		try {
			//creation de la connection avec la base de donnees
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			Statement st = con.createStatement();
			String sql = "SELECT * FROM users WHERE role = '" + role + "'";
			//execution de la requete
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				//pour chaque resulta on va definir un nouveau utilisateur a qui on va affecter les proprietes recuperees
				UserBean user = new UserBean(rs.getString(2), rs.getString(3));
				user.setId(rs.getInt(1));
				user.setPoints(rs.getInt(6));
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
	
	public static void addUser(String username, String password, String role, int points) {
		//methode pour l'ajout d'un utilisateur
		try {
			//creation de la connection avec la base de donnees
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			Statement st = con.createStatement();
			String sql = "INSERT INTO users (username, password, role, points) VALUES ('" + username + "', '" + password + "', '" + role + "', " + points + ")";
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
			String sql = "DELETE FROM users WHERE id='" + id + "'";
			//execution de la requete
			st.executeUpdate(sql);
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
