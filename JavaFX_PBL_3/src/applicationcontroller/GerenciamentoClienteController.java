package applicationcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class GerenciamentoClienteController implements Initializable{
	@FXML
	private TextField pesquisarCliente;
	@FXML
	private TableView<?> tabelaProdutos;
	@FXML
	private TableColumn<?, ?> columnId;
	@FXML
	private TableColumn<?, ?> columnNome;
	@FXML
	private TableColumn<?, ?> columnTelefone;
	@FXML
	private TableColumn<?, ?> columnEmail;
	@FXML
	private TableColumn<?, ?> columnCpf;
	@FXML
	private TableColumn<?, ?> columnAcoes;
	@FXML
	private Button voltarMenu;
	@FXML
	private Button novoCliente;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
