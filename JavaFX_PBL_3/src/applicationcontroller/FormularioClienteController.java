package applicationcontroller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

public class FormularioClienteController implements Initializable {
	@FXML
	private TextField textFNome;
	@FXML
	private TextField textFTelefone;
	@FXML
	private TextField textFEmail;
	@FXML
	private TextField textFCpf;
	@FXML
	private Button voltarMenu;
	@FXML
	private Label labelNovoCliente;
	@FXML
	private Label labelEditarCliente;
	@FXML
	private Button novoCliente;
	@FXML
	private Button editarCliente;

	// Event Listener on Button[#voltarMenu].onAction
	@FXML
	public void acaoVoltarMenu(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#novoCliente].onAction
	@FXML
	public void acaoAddCliente(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#editarCliente].onAction
	@FXML
	public void acaoEditCliente(ActionEvent event) {
		// TODO Autogenerated
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}