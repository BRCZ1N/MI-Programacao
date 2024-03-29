package applicationcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import applicationexeceptions.CamposNulosException;
import applicationexeceptions.EntidadeComValoresNegativoException;
import applicationmain.Main;
import applicationmodel.Ingredientes;
import applicationmodel.Pratos;
import applicationmodel.Produtos;
import applicationmodeldao.DaoFacade;
import applicationmodeldao.DaoProdutos;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

/**
 * Classe controlador do formulario de pratos
 * 
 * @author Bruno Campos de Oliveira Rocha
 * @author Alex da Fonseca Dantas Junior
 * @version 1.0
 * @since 2022
 */

public class FormularioPratosController implements Initializable {

	@FXML
	private Button adicionarProdutoPrato;
	@FXML
	private Button removerProdutoPrato;
	@FXML
	private TextField textFNome;
	@FXML
	private TextField textFPreco;
	@FXML
	private Button voltarMenu;
	@FXML
	private TableView<Ingredientes> tabelaCarrinho;
	@FXML
	private TableView<Produtos> tabelaProdutos;
	@FXML
	private TextField textFCategoria;
	@FXML
	private Button salvarPratoBotao;
	@FXML
	private TextField textFDescricao;
	@FXML
	private TableColumn<Ingredientes, String> columnCarrinhoProdutoId;
	@FXML
	private TableColumn<Ingredientes, Double> columnCarrinhoProdutoQtd;
	@FXML
	private TableColumn<Produtos, String> columnSistemaProdutoId;
	@FXML
	private TableColumn<Produtos, String> columnSistemaProdutoNome;
	@FXML
	private TableColumn<Produtos, Double> columnSistemaProdutoQtd;

	private ObservableList<Produtos> observableProdutoSistema;

	private ObservableList<Ingredientes> observableProdutoCarrinho;

	private ArrayList<Ingredientes> listaProdutosCarrinho = new ArrayList<Ingredientes>();

	private static Pratos pratoAtual;

	private Optional<String> input;

	/**
	 * M�todo para retorno do conteudo do prato selecionado.
	 * 
	 * @return Pratos pratoAtual
	 */
	public static Pratos getPratoAtual() {
		return pratoAtual;
	}

	/**
	 * M�todo para setar o conteudo do Prato selecionado.
	 * 
	 * @param pratoAtual Pratos
	 */
	public static void setPratoAtual(Pratos pratoAtual) {
		FormularioPratosController.pratoAtual = pratoAtual;
	}

	/**
	 * M�todo para retornar ao gerenciamento de Pratos.
	 * 
	 * @param event ActionEvent
	 * @throws IOException
	 */
	@FXML
	public void voltarMenuAcao(ActionEvent event) throws IOException {

		mudarJanela("/applicationviewcssfxml/GerenciamentoPratos.fxml");
		limparPrato();

	}

	/**
	 * M�todo para adicionar o id dos produtos na lista do prato a qual eles fazem
	 * parte
	 * 
	 * @param event ActionEvent
	 */
	@FXML
	void acaoAdicionarProdutoPrato(ActionEvent event) throws IOException {

		TextInputDialog textInput = new TextInputDialog();
		textInput.getDialogPane().setContentText("A quantidade do produto");
		textInput.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\f*"))
				textInput.getEditor().setText(newValue.replaceAll("\\f+", ""));
		});

		input = textInput.showAndWait();
		double qtdProdutoPrato = 0;

		try {

			if (input.isPresent()) {

				qtdProdutoPrato = Double.parseDouble(input.get());
				Ingredientes ingrediente = new Ingredientes(
						tabelaProdutos.getSelectionModel().getSelectedItem().getId(), qtdProdutoPrato);
				listaProdutosCarrinho.add(ingrediente);
				observableProdutoCarrinho = FXCollections.observableArrayList(listaProdutosCarrinho);
				refreshCarrinho();

			}

		} catch (NumberFormatException e) {

			Alertas.erro("Preencha o campo de dado com uma quantidade válida");

		}

	}

	/**
	 * M�todo para atualizar o listView do carrinho de produtos presentes em um
	 * prato
	 */
	public void refreshCarrinho() {

		observableProdutoCarrinho = FXCollections.observableArrayList(listaProdutosCarrinho);
		tabelaCarrinho.setItems(observableProdutoCarrinho);

		columnCarrinhoProdutoId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnCarrinhoProdutoQtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));
		columnCarrinhoProdutoQtd.setCellFactory(colum -> {
			return new TableCell<Ingredientes, Double>() {
				@Override
				public void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(String.format("%.2f", item));
					}
				}

			};

		});

	}

	/**
	 * M�todo para atualizar o listView dos produtos presentes no sistema
	 */
	public void refreshSistema() {

		observableProdutoSistema = FXCollections.observableArrayList(DaoProdutos.getListaProdutos());
		tabelaProdutos.setItems(observableProdutoSistema);

		columnSistemaProdutoId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnSistemaProdutoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		columnSistemaProdutoQtd.setCellValueFactory(new PropertyValueFactory<>("qtdProduto"));
		columnSistemaProdutoQtd.setCellFactory(column -> {
			return new TableCell<Produtos, Double>() {
				@Override
				public void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(String.format("%.2f", item));
					}
				}

			};

		});

	}

	/**
	 * M�todo para remover o id dos produtos na lista do prato a qual eles fazem
	 * parte
	 * 
	 * @param event ActionEvent
	 */
	@FXML
	void acaoRemoverProdutoPrato(ActionEvent event) {

		for (Ingredientes produtoExcluir : tabelaCarrinho.getSelectionModel().getSelectedItems()) {

			listaProdutosCarrinho.remove(produtoExcluir);

		}
		refreshCarrinho();

	}

	/**
	 * M�todo para salvar o Prato apos a confirmação.
	 * 
	 * @param event ActionEvent
	 * @throws IOException
	 */
	@FXML
	public void salvarPratoAcao(ActionEvent event) throws IOException {

		try {

			if (pratoAtual == null) {

				DaoFacade.addEditPratos(null, textFNome.getText(), textFDescricao.getText(),
						Double.parseDouble(textFPreco.getText()), textFCategoria.getText(), listaProdutosCarrinho);
				// DaoPratos.addEditDados(pratoNovo, null);

			} else {

				DaoFacade.addEditPratos(pratoAtual.getId(), textFNome.getText(), textFDescricao.getText(),
						Double.parseDouble(textFPreco.getText()), textFCategoria.getText(), listaProdutosCarrinho);
				// DaoPratos.addEditDados(pratoNovo, pratoAtual.getId());

			}

			mudarJanela("/applicationviewcssfxml/GerenciamentoPratos.fxml");
			limparPrato();

		} catch (EntidadeComValoresNegativoException | CamposNulosException | NumberFormatException e) {

			if (e.getMessage().equals("empty String")) {

				Alertas.erro("Preencha todos os campos de dados corretamente");

			} else {

				Alertas.erro(e.getMessage());

			}

		}

	}

	/**
	 * M�todo para inicializar o gerenciamento de pratos
	 * 
	 * @param arg0 URL
	 * @param arg1 ResourceBundle
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		textFPreco.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (!newValue.isEmpty()) {

					try {

						Double.parseDouble(newValue);

					} catch (NumberFormatException e) {

						Alertas.erro("Preencha todos os campos de dados corretamente");
						textFPreco.setText(newValue.replace(newValue, ""));

					}

				}

			}

		});

		if (pratoAtual != null) {

			textFNome.setText(pratoAtual.getNome());
			textFDescricao.setText(pratoAtual.getDescricao());
			textFPreco.setText(Double.toString(pratoAtual.getPreco()));
			textFCategoria.setText(pratoAtual.getCategoria());
			listaProdutosCarrinho = pratoAtual.getComposicaoPrato();
			refreshCarrinho();

		}

		refreshSistema();
		tabelaCarrinho.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	}

	/**
	 * Metodo para setar o fornecedor atual como nulo
	 */
	public void limparPrato() {

		pratoAtual = null;

	}

	/**
	 * M�todo para mudar para a janela determinada.
	 * 
	 * @param urlScene String
	 * @throws IOException
	 */
	public void mudarJanela(String urlScene) throws IOException {

		Main.getStage().setScene(novaCena(urlScene));

	}

	/**
	 * M�todo para criar uma nova janela determinada
	 * 
	 * @param urlScene String
	 * @throws IOException
	 */
	public Scene novaCena(String urlScene) throws IOException {

		FXMLLoader fxml = new FXMLLoader(getClass().getResource(urlScene));
		Parent root = fxml.load();
		Scene scene = new Scene(root);

		return scene;

	}
}
