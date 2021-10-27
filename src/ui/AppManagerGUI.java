package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import model.AppManager;
import model.Player;


public class AppManagerGUI {
	private AppManager appManager;
	
	@FXML
	private BorderPane mainPane;
	
	//La tabla principal de los jugadores
    @FXML
    private TableView<Player> tvPlayers;
    
    @FXML
    private TableColumn<Player, String> tcPlayerName;

    @FXML
    private TableColumn<Player, String> tcPlayerTeam;

    @FXML
    private TableColumn<Player, String> tcPlayerAge;

    @FXML
    private TableColumn<Player, String> tcPlayerPoints;

    @FXML
    private TableColumn<Player, String> tcPlayerRebounds;

    @FXML
    private TableColumn<Player, String> tcPlayerBlocks;

    @FXML
    private TableColumn<Player, String> tcPlayerAssists;

    @FXML
    private TableColumn<Player, String> tcPlayerSteals;
    
	public AppManagerGUI(AppManager app) {
		appManager = app;
		try {
			appManager.importPlayerDataBase();
			appManager.callCreatBinarySearchTreeThread();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//TODO There's a little problem, tho. Initialize is called everytime I change panes.
	// Therefore, we're reloading the controller
	@FXML
	public void initialize() {
		
		/*try {
			//loadList(null);
		} catch (IOException e) {
			//TODO Don't mind this
			//System.out.println("Nah, its okay bro");
			//e.printStackTrace();
		}*/
	}
	//TODO we could end up needing it.
	@FXML
	public void exitApp(ActionEvent event) {
		Platform.exit();
	}
	
	@FXML
	public void loadList(ActionEvent event) throws IOException {
		loadPage("List.fxml");
		loadPlayerListValues();
	}
	
	public void loadPlayerListValues() {
		ObservableList<Player> observableList;
		observableList = FXCollections.observableArrayList(appManager.getPlayers());
		tvPlayers.setItems(observableList);
		tcPlayerName.setCellValueFactory(new PropertyValueFactory<Player,String>("name"));
		tcPlayerAge.setCellValueFactory(new PropertyValueFactory<Player,String>("age"));
		tcPlayerAssists.setCellValueFactory(new PropertyValueFactory<Player,String>("assists"));
		tcPlayerPoints.setCellValueFactory(new PropertyValueFactory<Player,String>("points"));
		tcPlayerRebounds.setCellValueFactory(new PropertyValueFactory<Player,String>("reBounds"));
		tcPlayerSteals.setCellValueFactory(new PropertyValueFactory<Player,String>("steals"));
		tcPlayerTeam.setCellValueFactory(new PropertyValueFactory<Player,String>("team"));
	}
	
	@FXML
	public void test(ActionEvent event) throws IOException {
		//Test de busqueda de todos con el mismo valor en el arbol
		System.out.println(appManager.getBinarySearchTrees().get(0).getRoot());
		
		
		
		/*
		System.out.println("Fair enough!");
		BinaryTree<Integer, Player> bt = appManager.getBinarySearchTrees().get(0);
		System.out.println(bt.getRoot().getPlayer().toString());
		System.out.println(bt.delete(32, null).getPlayer().toString());
		*/
	}
	
	@FXML
    private ComboBox<String> cbSearchParameter;

    @FXML
    private TextField txtSearchValue;

    @FXML
    private ImageView imgPlayerCharacter;

    @FXML
    private TableView<Player> tvSimilarPlayers;
    
    @FXML
    private TableColumn<Player, String> tcPlayerData;
	
	@FXML
	public void loadSearch(ActionEvent event) throws IOException {
		loadPage("Search.fxml");
		loadComboBox();
	}
	
	public void loadComboBox() {
		ObservableList<String> observableList;
		ArrayList<String> values = new ArrayList<String>();
		values.add("Name");
		values.add("Age");
		values.add("Assists");
		values.add("Points");
		values.add("Rebounds");
		values.add("Steals");
		values.add("Team");
		observableList = FXCollections.observableArrayList(values);
		cbSearchParameter.setItems(observableList);
	}
	//TODO This method is so the appManager doesn't returns warnings as we're not using it. "Remove Later".
	public AppManager returnAppManager() {
		return appManager;
	}
    
    public void loadPage(String page) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(page));
		fxmlLoader.setController(this);
		Parent pane = fxmlLoader.load();
		mainPane.setCenter(pane);
    }
    
    @FXML
    private Label labPlayerName;

    @FXML
    private Label labPlayerAge;

    @FXML
    private Label labPlayerTeam;

    @FXML
    private Label labPlayerPoints;

    @FXML
    private Label labPlayerRebounds;

    @FXML
    private Label labPlayerBlocks;

    @FXML
    private Label labPlayerAssists;

    @FXML
    private Label labPlayerSteals;
    
    @FXML
    private TextField txtBegRange;

    @FXML
    private TextField txtEndRange;

    @FXML
    private Label labWarning;

    @FXML
    void searchPlayers(ActionEvent event) {
    	
    	if(!txtSearchValue.getText().equals("") && cbSearchParameter.getValue() != null && txtBegRange.getText().equals("") && txtEndRange.getText().equals("")) {
    		
    	}
    	//linearSearch
    	if(!txtSearchValue.getText().equals("") && cbSearchParameter.getValue() != null) {
    		ObservableList<Player> observableList;
    		String parameter = cbSearchParameter.getValue().toLowerCase();
    		if (parameter.equals("rebounds")) {
    			parameter = "reBounds";
    		}
<<<<<<< HEAD
    		observableList = FXCollections.observableArrayList(appManager.linearSearch(parameter,txtSearchValue.getText()));
=======
    		/*System.out.println(parameter);
    		System.out.println(txtSearchValue.getText());
    		System.out.println("1");*/
    		observableList = FXCollections.observableArrayList(appManager.searchWithTree(parameter,txtSearchValue.getText()));
    		//System.out.println(appManager.linearSearch(cbSearchParameter.getValue(),txtSearchValue.getText()));
>>>>>>> 9a90cb5df85a0913ec645148a5a060d5e5224954
    		tvSimilarPlayers.setItems(observableList);
    		tcPlayerData.setCellValueFactory(new PropertyValueFactory<Player,String>("name"));
    		
    		if (observableList.size() > 0) {
    			labWarning.setText("");
    			Player player = observableList.get(0);
    			
    			labPlayerName.setText("Name: " + player.getName());
    			labPlayerAge.setText("Age: " + player.getAge());
    			labPlayerTeam.setText("Team: " + player.getTeam());
    			labPlayerPoints.setText("Points: " + player.getPoints());
    			labPlayerRebounds.setText("Rebounds: " + player.getReBounds());
    			labPlayerBlocks.setText("Blocks: " + player.getBlocks());
    			labPlayerAssists.setText("Assists: " + player.getAssists());
    			labPlayerSteals.setText("Steals: " + player.getSteals());
    			
    			changeImage();
    		} else {
    			labWarning.setText("No se ha encontrado ning�n jugador con ese parametro!");
    		}
    		
    	}else {
    		System.out.println("a");
    	}
    }
    
    @FXML
    void showPlayerInfo(MouseEvent event) {
    	if(tvSimilarPlayers.getSelectionModel().getSelectedItem() != null) {
			Player player = tvSimilarPlayers.getSelectionModel().getSelectedItem();
			
			labPlayerName.setText("Name: " + player.getName());
			labPlayerAge.setText("Age: " + player.getAge());
			labPlayerTeam.setText("Team: " + player.getTeam());
			labPlayerPoints.setText("Points: " + player.getPoints());
			labPlayerRebounds.setText("Rebounds: " + player.getReBounds());
			labPlayerBlocks.setText("Blocks: " + player.getBlocks());
			labPlayerAssists.setText("Assists: " + player.getAssists());
			labPlayerSteals.setText("Steals: " + player.getSteals());
			
			changeImage();
		}
    }
    
    public void changeImage(){
    	int x = randNum(1, 100);
    	if(x > 0 && x <= 33) {
    		imgPlayerCharacter.setImage(new Image("file:Data\\images\\basketball_player_1.png"));
    	}else if(x > 33 && x <= 66) {
    		imgPlayerCharacter.setImage(new Image("file:Data\\images\\basketball_player_2.png"));
    	}else if(x > 66 && x <= 99) {
    		imgPlayerCharacter.setImage(new Image("file:Data\\images\\basketball_player_3.png"));
    	}else {
    		imgPlayerCharacter.setImage(new Image("file:Data\\images\\sape.png"));
    	}
    }
    /*public ArrayList<String> playersNamesFilter(ArrayList<Player> players){
    	ArrayList<String> names = new ArrayList<String>();
    	for(int i = 0; i < players.size(); i++) {
    		names.add(players.get(i).getName());
    	}
    	return names;
    }*/
    
    public int randNum(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

    
}
