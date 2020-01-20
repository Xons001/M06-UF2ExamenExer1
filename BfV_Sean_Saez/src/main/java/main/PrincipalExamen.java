package main;

import java.util.List;
import java.util.Scanner;

import javax.persistence.StoredProcedureQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Bfplayer;
import model.Weapon;

public class PrincipalExamen {

	public static void main(String[] args) {
		
		Scanner lector = new Scanner(System.in);

		boolean salir = false;
		while (salir  == false) {
			System.out.println("------------------------------------------------");
			System.out.println("Menu");
			System.out.println("======================");
			System.out.println("1.-Consulta ratio kill/death");
			System.out.println("2.-Insertar Weapon");
			System.out.println("3.-Insertar Player");
			System.out.println("4.-Eliminar Players con class id menor que 3");
			System.out.println("5.-Fin");
			System.out.println("=====================");

			int pos = lector.nextInt();
			switch (pos) {
			case 1:
				consultaRatio();
				break;

			case 2:
				insertarArma();
				break;

			case 3:
				insertarPlayer();
				break;

			case 4:
				eliminarPlayerClassId();
				break;
			case 5:
				System.out.println("Fin");
				salir=true;
				break;

			default:
				System.out.println("Escribe bien el numero");
				break;
			}
		}
	}

	public static void consultaRatio() {

		Session session = sessionFactoryUtil.getSessionFactory().openSession(); 

		List<Bfplayer> player = session.createQuery("from Bfplayer").list();

		double ratio = 0;
		for (Bfplayer p : player) {
			ratio = Double.valueOf(p.getKills())/Double.valueOf(p.getDeads());
			System.out.println("Player " + p.getUserId() + ": kills = " + p.getKills() + ", deaths =" + p.getDeads() + ", ratio es " + ratio);
		}
		System.out.println();
	}
	
	public static void insertarArma() {
		Session session = sessionFactoryUtil.getSessionFactory().openSession(); 
		Transaction tx = session.beginTransaction();

		Weapon weapon = new Weapon();
		weapon.setWeaponId(10);
		weapon.setName("KE7");
		weapon.setType("Light Machine Gun");
		weapon.setDamage(23);
		weapon.setAccuracy(65);
		
		session.save(weapon);
		tx.commit();

		System.out.println("Arma insertada");
		System.out.println(weapon.toString());


		session.close();
	}

	public static void insertarPlayer() {
		Session session = sessionFactoryUtil.getSessionFactory().openSession(); 
		Transaction tx = session.beginTransaction();

		Bfplayer player = new Bfplayer();
		player.setUserId("Xons001");
		player.setClassId(2);
		player.setPrimaryWeapon(10);
		player.setDevice1(4);
		player.setKills(150);
		player.setDeads(100);
		
		session.save(player);
		tx.commit();

		System.out.println("Player insertado");
		System.out.println(player.toString());


		session.close();
	}
	
	public static void eliminarPlayerClassId() {
		Session session = sessionFactoryUtil.getSessionFactory().openSession(); 
		System.out.println(":::: Eliminamos jugadores de clase id 2 para abajo ::::");
		Transaction tx = session.beginTransaction();
		
        StoredProcedureQuery allPlayersId = session.createStoredProcedureQuery("eliminarPlayerClassId", Bfplayer.class);
 
        allPlayersId.executeUpdate();
        tx.commit();
        
        System.out.println("Player eliminados");
        session.close();
	}
}
