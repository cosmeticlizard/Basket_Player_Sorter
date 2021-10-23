package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Threads.CreateBinarySearchTreeThread;
import Threads.ImportDataBaseThread;
import Threads.LinearSearchThread;
import Threads.LinearSearchWithRange;
import dataStructureTrees.BinaryTree;

public class AppManager {

	private List<Player> players;
	private ImportDataBaseThread importDataBaseThread;
	private LinearSearchThread linearSearchThread;
	private LinearSearchWithRange linearSearchWithRange;
	private List<BinaryTree<Integer, Player>> binarySearchTrees;
	private CreateBinarySearchTreeThread createBinarySearchTree;

	public AppManager(){
		players=new ArrayList<>();
		importDataBaseThread= new ImportDataBaseThread(this);
		binarySearchTrees= new ArrayList<>();

	}

	public void callImportDataBase() {
		importDataBaseThread.start();
	}

	public void importPlayerDataBase() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Data/DataBase.csv"));
		String line= br.readLine();
		while(line!=null) {
			String[] parts= line.split(";");
			Player currentPlayer= new Player(parts[0], parts[2], Integer.parseInt(parts[1]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6]), Integer.parseInt(parts[7]));
			players.add(currentPlayer);
			line=br.readLine();
		}
		br.close();
	}



	public ArrayList<Player> callLinearSearch(String search, String searchedFor){
		linearSearchThread= new LinearSearchThread(this, search,  searchedFor);
		linearSearchThread.start();
		//TODO toca que ver que podemos hacer para sacar este while
		while(linearSearchThread.isAlive()) {}
		return linearSearchThread.getPlayers();
	}

	public ArrayList<Player> callLinearSearchWithRange(int min, int max,String search){
		linearSearchWithRange = new LinearSearchWithRange(this, min, max, search);
		linearSearchWithRange.start();
		//TODO toca que ver que podemos hacer para sacar este while
		while(linearSearchWithRange.isAlive()) {}
		return linearSearchWithRange.getPlayers();
	}


	//Todos los jugadores con el mismo dato (Aka, devuelve todos los jugadores con el mismo nombre)
	public ArrayList<Player> linearSearch(String search, String searchedFor){
		ArrayList<Player> searchedPlayers= new ArrayList<>();
		for(int c=0;c< players.size();c++) {
			if(players.get(c).get(search).equalsIgnoreCase(searchedFor)) {
				searchedPlayers.add(players.get(c));
			}
		}
		return searchedPlayers;
	}

	//Todos los jugadores que se encuentren en el rango (Aka, devuelve todos los jugadores de la edad desde 18 a 20 [teniendo en cuenta 20])
	public ArrayList<Player> linearSearchWithRange(int min, int max,String search) {
		ArrayList<Player> searchedPlayers= new ArrayList<>();
		for(int c=0;c< players.size();c++) {
			if(Integer.parseInt(players.get(c).get(search))>=min && Integer.parseInt(players.get(c).get(search))<=max) {
				searchedPlayers.add(players.get(c));
			}
		}
		return searchedPlayers;
	}

	//Retorna el primer jugador que encuentre con un dato especifico 
	public Player linearSearchForFirstPlayerWithValue(String search,String searchedFor) {
		boolean found=false;
		Player playerFound=null;
		for(int c=0;c< players.size()&& !found;c++) {
			if(players.get(c).get(search).equalsIgnoreCase(searchedFor)) {
				found=true;
				playerFound=players.get(c);
			}
		}
		return playerFound;
	}

	public Player searchWithTree(String search, int searchedFor) {
		int index = 0;
		switch (search) {
		case "age":
			index = 0;
			break;
		case "points":
			index = 1;
			break;
		case "reBounds":
			index = 2;
			break;
		case "blocks":
			index = 3;
			break;
		default:
			index = -1;
			break;
		}
		Player pl = null;
		if (index != -1) {
			pl = binarySearchTrees.get(index).search(searchedFor, null).getPlayer();
		}else {
			pl = linearSearchForFirstPlayerWithValue(search, search);
		}
		return pl;
	}
	//indice de el player que se va a cambiar
	//los nuevos datos del player (observe que son los mismos datos del constructor) 
	public void modify(int index,String name,String team, int age, int points,int reBounds, int blocks, int assists, int steals ) {

		Player modifiedPlayer=players.get(index);
		Player newPlayer= new Player(name, team, age, points, reBounds, blocks, assists, steals);
		players.remove(index);
		players.add(index, newPlayer);
		//Hasta aqui, solo funciona para la arraylist xd
	}


	public void creatBinarySearchTree() {
		int i=0;
		String[] values= {"age","points","reBounds","blocks"};
		double first = System.nanoTime();
		while(i<4) {
			binarySearchTrees.add(new BinaryTree<Integer, Player>());
			for(int c=0;c<players.size();c++) {
				binarySearchTrees.get(i).insert(Integer.parseInt(players.get(c).get(values[i])), players.get(c));
			}

			i++;
		}
		double second = System.nanoTime();
		System.out.println((second - first)/1000000000 + "-- Creation time for the 4 trees (Seconds)");
		double searchOne = System.nanoTime();
		System.out.println(binarySearchTrees.get(1).search(98, null).getPlayer().getName());
		double searchTwo = System.nanoTime();
		System.out.println((searchTwo - searchOne)/1000000000 + " -- Search Time (Seconds)");
		//System.out.println(searchWithTree("age", 20));
	}

	public void callCreatBinarySearchTreeThread() {
		createBinarySearchTree= new CreateBinarySearchTreeThread(this);
		createBinarySearchTree.start();
	}
	
	public List<BinaryTree<Integer, Player>> getBinarySearchTrees() {
		return binarySearchTrees;
	}
	
	public List<Player> getPlayers(){
		return players;
	}
}

