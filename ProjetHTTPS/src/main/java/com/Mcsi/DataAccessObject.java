package com.Mcsi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;


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
		//recuperation du nom d'utilisateur et mot de passe 
		String username = l.getUsername();
		String password = l.getPassword();
		try {
			//connection avec base de donne
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			//recuperation des grain de sel
			String sql = "SELECT salt FROM users WHERE username = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String salt = rs.getString(1);
			//hashage du mot de passe
			String hashedPassword = BCrypt.hashpw(password, salt);
			//dans la requete on groupe les trois tableaux users, students et teachers avec LEFT JOIN et on utilise la fonction COALESCE qui retourne la premiere valeur non null (dans ses arguments) pour eviter les champs null.
			sql = "SELECT users.id, username, password, role, COALESCE(students.points, 0) AS points, COALESCE(teachers.unit_name, 'N/A') AS unit_name FROM users LEFT JOIN students ON users.id = students.user_id LEFT JOIN teachers ON users.id = teachers.user_id WHERE username = ? AND password = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, hashedPassword);
			//execution de la requete
			rs = ps.executeQuery();
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
			ps.close();
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
		//generation des grain des sel
		String salt = BCrypt.gensalt();
		//hashage de mot de passe
		String hashedPassword = BCrypt.hashpw(password, salt);
		try {
			//creation de la connection avec la base de donnees
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			String sql = "INSERT INTO users (username, password, role, salt) VALUES (?, ?, ?, ?);";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, hashedPassword); 
			ps.setString(3, role); 
			ps.setString(4, salt); 
			//execution de la requete
			ps.executeUpdate();
			sql = "Select id FROM users WHERE username = ?;";
			ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int id = rs.getInt(1);
			if (role.equals("student")) {
				sql = "INSERT INTO students (user_id, points) VALUES (?, ?);";
				ps = con.prepareStatement(sql);
				ps.setInt(1, id);
				ps.setInt(2, points);
				ps.executeUpdate();
			} else if (role.equals("teacher")) {
				sql = "INSERT INTO teachers (user_id, unit_name) VALUES (?, ?);";
				ps = con.prepareStatement(sql);
				ps.setInt(1, id);
				ps.setString(2, unit_name);
				ps.executeUpdate();
				sql = "INSERT INTO units (unit_name, teacher_id) VALUES (?, (SELECT id FROM teachers WHERE teachers.unit_name = ?));";
				ps = con.prepareStatement(sql);
				ps.setString(1, unit_name);
				ps.setString(2, unit_name);
				ps.executeUpdate();
			}
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void modifyUser(int id, String username, String password, String role, String unit_name, int points) {
		//methode pour la modification d'un utilisateur
		//generation des grain de sel 
		String salt = BCrypt.gensalt();
		//hashage du mot de passe
		String hashedPassword = BCrypt.hashpw(password, salt);
		try {
			//creation de la connection avec la base de donnees
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			String sql = "UPDATE users SET username = ?, password = ?, salt = ? WHERE id = ?;";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, hashedPassword);
			ps.setString(3, salt);
			ps.setInt(4, id);
			//execution de la requete
			ps.executeUpdate();
			if (role.equals("student")) {
				sql = "UPDATE students SET points = ? WHERE user_id = ?;";
				ps = con.prepareStatement(sql);
				ps.setInt(1, points);
				ps.setInt(2, id);
				ps.executeUpdate();
			} else if (role.equals("teacher")) {
				sql = "UPDATE teachers SET unit_name = ? WHERE user_id = ?;";
				ps = con.prepareStatement(sql);
				ps.setString(1, unit_name);
				ps.setInt(2, id);
				ps.executeUpdate();
			}
			ps.close();
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
			String sql = "DELETE FROM users WHERE id=?;";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			//execution de la requete
			ps.executeUpdate();
			ps.close();
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
			String sql = "SELECT * FROM users WHERE username = ?;";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			//s'il existe un utilisateur avec le meme nom d'utilisateur on retourne 1
			if (rs.next()) {
				status = 1;
			}
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static void createUser(String username, String password) {
		//methode pour la creation d'un nouveau utilisateur
		//generation des grain de sel
		String salt = BCrypt.gensalt();
		//hashage du mot de passe
		String hashedPassword = BCrypt.hashpw(password, salt);
		try {
			//creation de la connection avec la base de donnees
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			String sql = "INSERT INTO users (username, password, salt, role) VALUES (?, ?, ?, 'student');";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, hashedPassword);
			ps.setString(3, salt);
			ps.executeUpdate();
			sql = "SELECT id FROM users WHERE username = ?;";
			ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int id = rs.getInt(1);
			sql = "INSERT INTO students (user_id, points) VALUES (?, 0);";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<String[]> getUnits(int id) {
		//methode pour la recuperation des modules et professeurs
		List<String[]> units_teachers = new ArrayList<>();
		try {
			//creation de la connection avec la base de donnees
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			String sql = "SELECT users.username, teachers.unit_name FROM users JOIN teachers ON teachers.user_id = users.id WHERE teachers.unit_name in ( SELECT units.unit_name FROM units JOIN student_unit ON student_unit.unit_name = units.unit_name JOIN students ON students.id = student_unit.student_id JOIN users ON users.id = students.user_id WHERE users.id = ? );";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				units_teachers.add(new String[]{rs.getString(1), rs.getString(2)});
			}
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return units_teachers;
	}
	
	public static int sendPoints(Student s, int id, int points) {
		//methode pour effectuer un virement
		int status = 0;
		Connection con = null;
		int student_id = s.getId();
		try {
			//creation de la connection avec la base de donnees
			con = DataAccessObject.getConnection();
			//On commence la transaction en desactivant le mode auto-commit
			con.setAutoCommit(false);
			//creation et initialisation de la requete sql
			String sql = "Select points FROM students WHERE user_id = ?;";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, student_id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) >= points) {
					sql = "UPDATE students SET points = points + ? WHERE user_id = ?;";
					ps = con.prepareStatement(sql);
					ps.setInt(1, points);
					ps.setInt(2, id);
					String sql2 = "UPDATE students SET points = points - ? WHERE user_id = ?;";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setInt(1, points);
					ps2.setInt(2, student_id);
					if ((ps.executeUpdate() != 0) && (ps.executeUpdate() != 0)) {
						status = 1;
						con.commit();
					}
				} else {
					status = -2;
				}
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			try {
	            if (con != null) {
	                con.rollback(); // Rollback transaction on error
	            }
	        } catch (SQLException rollbackException) {
	            rollbackException.printStackTrace();
	        }
	        e.printStackTrace();
		} finally {
	        try {
	            if (con != null) {
	                con.setAutoCommit(true); // Restore auto-commit mode
	                con.close();
	            }
	        } catch (SQLException closeException) {
	            closeException.printStackTrace();
	        }
	    }
		return status;
	}
	
	public static List<Student> getStudentsInUnit(String unitName) {
		//methode pour la recuperation des etudiants etudions le module specifie
		List<Student> students = new ArrayList<Student>();
		try {
			//creation de la connection avec la base de donnees
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			String sql = "SELECT users.id, users.username, students.points FROM users JOIN students ON students.user_id = users.id JOIN student_unit ON student_unit.student_id = students.id JOIN units ON units.unit_name = student_unit.unit_name WHERE units.unit_name = ?;";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, unitName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Student student = new Student(rs.getString(2), "", rs.getInt(3));
				student.setId(rs.getInt(1));
				students.add(student);
			}
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return students;
	}
	
	public static int addPoints(int id, int points) {
		//methode pour l'ajout des points a un etudiant
		int status = 0;
		try {
			//creation de la connection avec la base de donnees
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			String sql = "Update students SET points = points + ? WHERE user_id = ?;";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, points);
			ps.setInt(2, id);
			if (ps.executeUpdate() != 0) {
				status = 1;
			}
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static int addStudentToUnit(int id, String unit_name) {
		//methode pour l'ajout d'un etudiant a un module
		int status = 0;
		try {
			//creation de la connection avec la base de donnees
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			String sql = "INSERT INTO student_unit (student_id, unit_name) VALUES ((SELECT students.id FROM students JOIN users ON users.id = students.user_id WHERE users.id = ?), ?);";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, unit_name);
			if (ps.executeUpdate() != 0) {
				status = 1;
			}
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static void deleteStudentFromUnit(int teacherId, int studentId) {
		//methode pour retirer un etudiant d'un module
		try {
			//creation de la connection avec la base de donnees
			Connection con = DataAccessObject.getConnection();
			//creation et initialisation de la requete sql
			String sql = "DELETE FROM student_unit WHERE student_id = (SELECT students.id FROM students JOIN users ON users.id = students.user_id WHERE users.id = ? ) AND unit_name = (SELECT unit_name FROM teachers WHERE teachers.user_id = ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, studentId);
			ps.setInt(2, teacherId);
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
