package applicationcontroller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.DatePicker;

public class FormularioVendasController implements Initializable{
	@FXML
	private TextField textFVenda;
	@FXML
	private TextField textFTipoPagamento;
	@FXML
	private DatePicker datePickerVenda;
	@FXML
	private Button voltarMenu;
	@FXML
	private Label labelEditarVenda;
	@FXML
	private Label labelNovaVenda;
	@FXML
	private Button novaVenda;
	@FXML
	private Button editarVenda;

	// Event Listener on Button[#voltarMenu].onAction
	@FXML
	public void acaoVoltarMenu(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#novaVenda].onAction
	@FXML
	public void acaoAddVenda(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#editarVenda].onAction
	@FXML
	public void acaoEditVenda(ActionEvent event) {
		// TODO Autogenerated
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}