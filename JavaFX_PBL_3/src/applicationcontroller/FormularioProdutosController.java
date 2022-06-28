package applicationcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import applicationexeceptions.EntidadeComValoresNegativoException;
import applicationmain.Main;
import applicationmodel.Produtos;
import applicationmodel.UnidadeMedida;
import applicationmodeldao.DaoProdutos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.DatePicker;

public class FormularioProdutosController implements Initializable {

	@FXML
	private ComboBox<String> comboBoxUMedida;
	@FXML
	private TextField textFNome;
	@FXML
	private TextField textFPreco;
	@FXML
	private TextField textFQtd;
	@FXML
	private DatePicker datePickerProduto;
	@FXML
	private Button voltarMenu;
	@FXML
	private Button salvarProdutoBotao;

	private ArrayList<String> arrayListComboBox = new ArrayList<String>();

	private ObservableList<String> observableComboBox;

	private static Produtos produtoAtual;
	/**
	 *M�todo para retorno do conteudo do produto selecionado.
	 *@return Produtos produtoAtual
	 */
	public static Produtos getProdutoAtual() {
		return produtoAtual;
	}
	/**
	 *M�todo para setar o conteudo do produto selecionado.
	 *@param produtoAtual Produtos 
	 */
	public static void setProdutoAtual(Produtos produtoAtual) {
		FormularioProdutosController.produtoAtual = produtoAtual;
	}

	/**
   	 *M�todo para retornar ao gerenciamento de Produtos.
   	 *@param  event ActionEvent
   	 *@throws IOException
   	 */
	@FXML
	public void voltarMenuAcao(ActionEvent event) throws IOException {

		mudarJanela("/applicationviewcssfxml/GerenciamentoProdutos.fxml");
		limparUsuario();

	}

	/**
   	 *M�todo para salvar o produto apos a confirmação.
   	 *@param  event ActionEvent
   	 *@throws IOException
   	 */
	@FXML
	public void salvarProdutoAcao(ActionEvent event) throws IOException {

		Produtos produtoNovo = new Produtos(textFNome.getText(), datePickerProduto.getValue(),
				Double.parseDouble(textFPreco.getText()), Double.parseDouble(textFQtd.getText()), comboBoxUMedida.getValue());

		try {

			if (produtoAtual == null) {
				boolean retorno = Alertas.confirmar("produto");
				
				if (retorno) {
					DaoProdutos.addEditDados(produtoNovo, null);
				}

			} else {

				DaoProdutos.addEditDados(produtoNovo, produtoAtual.getId());

			}
		} catch (EntidadeComValoresNegativoException e) {
			Alertas.erro(e.getMessage());
		}

		mudarJanela("/applicationviewcssfxml/GerenciamentoProdutos.fxml");
		limparUsuario();

	}
	/**
   	 *M�todo para inicializar o gerenciamento de Fornecedores
   	 *@param arg0 URL
   	 *@param arg1 ResourceBundle
   	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		if (produtoAtual != null) {

			textFNome.setText(produtoAtual.getNome());
			textFPreco.setText(Double.toString(produtoAtual.getPreco()));
			textFQtd.setText(Double.toString(produtoAtual.getQtdProduto()));
			comboBoxUMedida.setValue(produtoAtual.getTipoQtd());
			datePickerProduto.setValue(produtoAtual.getValidade());

		}
		
		
		inicializarComboBox();

	}
	/**
   	 *Metodo para inicializar o comboBox da unidade de medida do produto
   	 */
	public void inicializarComboBox() {

		arrayListComboBox.add(UnidadeMedida.getTipoDeUnidade1());
		arrayListComboBox.add(UnidadeMedida.getTipoDeUnidade2());
		observableComboBox = FXCollections.observableArrayList(arrayListComboBox);

		comboBoxUMedida.setItems(observableComboBox);

	}
	/**
   	 *Metodo para setar o produto atual como nulo
   	 */
	public void limparUsuario() {

		produtoAtual = null;

	}
	/**
   	 *M�todo para mudar para a janela determinada.
   	 *@param urlScene String
   	 *@throws IOException
   	 */
	public void mudarJanela(String urlScene) throws IOException {

		Main.getStage().setScene(novaCena(urlScene));

	}
	/**
   	 *M�todo para mudar para a janela determinada.
   	 *@param urlScene String
   	 *@throws IOException
   	 */
	public Scene novaCena(String urlScene) throws IOException {

		FXMLLoader fxml = new FXMLLoader(getClass().getResource(urlScene));
		Parent root = fxml.load();
		Scene scene = new Scene(root);

		return scene;

	}
}
